package com.amazonaws.mobileconnectors.cognitoauth;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthInvalidGrantException;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthNavigationException;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthServiceException;
import com.amazonaws.mobileconnectors.cognitoauth.handlers.AuthHandler;
import com.amazonaws.mobileconnectors.cognitoauth.util.AuthHttpClient;
import com.amazonaws.mobileconnectors.cognitoauth.util.AuthHttpResponseParser;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;
import com.amazonaws.mobileconnectors.cognitoauth.util.LocalDataManager;
import com.amazonaws.mobileconnectors.cognitoauth.util.Pkce;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AuthClient {
    /* access modifiers changed from: private */
    public final Context context;
    /* access modifiers changed from: private */
    public final CustomTabsCallback customTabsCallback;
    /* access modifiers changed from: private */
    public CustomTabsClient mCustomTabsClient;
    private CustomTabsIntent mCustomTabsIntent;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    /* access modifiers changed from: private */
    public CustomTabsSession mCustomTabsSession;
    /* access modifiers changed from: private */
    public final Auth pool;
    private String proofKey;
    private String proofKeyHash;
    private String state;
    /* access modifiers changed from: private */
    public AuthHandler userHandler;
    /* access modifiers changed from: private */
    public String userId;

    protected AuthClient(Context context2, Auth pool2) {
        this(context2, pool2, null);
    }

    protected AuthClient(Context context2, Auth pool2, String username) {
        this.customTabsCallback = new CustomTabsCallback() {
            public void onNavigationEvent(int navigationEvent, Bundle extras) {
                super.onNavigationEvent(navigationEvent, extras);
                if (navigationEvent == 6) {
                    AuthClient.this.userHandler.onFailure(new AuthNavigationException("user cancelled"));
                }
            }
        };
        this.context = context2;
        this.pool = pool2;
        this.userId = username;
        preWarmChrome();
    }

    /* access modifiers changed from: protected */
    public void setUserHandler(AuthHandler handler) {
        if (handler == null) {
            throw new InvalidParameterException("Callback handler cannot be null");
        }
        this.userHandler = handler;
    }

    /* access modifiers changed from: protected */
    public void setUsername(String username) {
        this.userId = username;
    }

    /* access modifiers changed from: protected */
    public void getSession() {
        try {
            this.proofKey = Pkce.generateRandom();
            this.proofKeyHash = Pkce.generateHash(this.proofKey);
            this.state = Pkce.generateRandom();
        } catch (Exception e) {
            this.userHandler.onFailure(e);
        }
        AuthUserSession session = LocalDataManager.getCachedSession(this.context, this.pool.getAppId(), this.userId, this.pool.getScopes());
        if (session.isValidForThreshold()) {
            this.userHandler.onSuccess(session);
        } else if (session.getRefreshToken() == null || session.getRefreshToken().getToken() == null) {
            launchCognitoAuth(this.pool.getSignInRedirectUri(), this.pool.getScopes());
        } else {
            refreshSession(session, this.pool.getSignInRedirectUri(), this.pool.getScopes(), this.userHandler);
        }
    }

    /* access modifiers changed from: protected */
    public String getUsername() {
        return this.userId;
    }

    public void signOut() {
        LocalDataManager.clearCache(this.context, this.pool.getAppId(), this.userId);
        launchSignOut(this.pool.getSignOutRedirectUri());
    }

    public boolean isAuthenticated() {
        return LocalDataManager.getCachedSession(this.context, this.pool.getAppWebDomain(), this.userId, this.pool.getScopes()).isValidForThreshold();
    }

    public void getTokens(Uri uri) {
        if (uri != null) {
            getTokens(uri, this.userHandler);
        }
    }

    private void getTokens(final Uri uri, final AuthHandler callback) {
        new Thread(new Runnable() {
            final Handler handler = new Handler(AuthClient.this.context.getMainLooper());
            Runnable returnCallback = new Runnable() {
                public void run() {
                    callback.onFailure(new InvalidParameterException());
                }
            };

            public void run() {
                Uri fqdn = new Builder().scheme(ClientConstants.DOMAIN_SCHEME).authority(AuthClient.this.pool.getAppWebDomain()).appendPath(ClientConstants.DOMAIN_PATH_OAUTH2).appendPath(ClientConstants.DOMAIN_PATH_TOKEN_ENDPOINT).build();
                String callbackState = uri.getQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_STATE);
                if (callbackState != null) {
                    Set<String> tokenScopes = LocalDataManager.getCachedScopes(AuthClient.this.context, callbackState);
                    String proofKeyPlain = LocalDataManager.getCachedProofKey(AuthClient.this.context, callbackState);
                    if (proofKeyPlain != null) {
                        final String errorText = uri.getQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_ERROR);
                        if (errorText != null) {
                            this.returnCallback = new Runnable() {
                                public void run() {
                                    callback.onFailure(new AuthServiceException(errorText));
                                }
                            };
                        } else {
                            try {
                                final AuthUserSession session = AuthHttpResponseParser.parseHttpResponse(new AuthHttpClient().httpPost(new URL(fqdn.toString()), AuthClient.this.getHttpHeader(), AuthClient.this.generateTokenExchangeRequest(uri, proofKeyPlain)));
                                AuthClient.this.userId = session.getUsername();
                                LocalDataManager.cacheSession(AuthClient.this.context, AuthClient.this.pool.getAppId(), AuthClient.this.userId, session, tokenScopes);
                                this.returnCallback = new Runnable() {
                                    public void run() {
                                        callback.onSuccess(session);
                                    }
                                };
                            } catch (Exception e) {
                                this.returnCallback = new Runnable() {
                                    public void run() {
                                        callback.onFailure(e);
                                    }
                                };
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    this.returnCallback = new Runnable() {
                        public void run() {
                            callback.onSignout();
                        }
                    };
                }
                this.handler.post(this.returnCallback);
            }
        }).start();
    }

    private void refreshSession(AuthUserSession session, String redirectUri, Set<String> tokenScopes, AuthHandler callback) {
        final String str = redirectUri;
        final Set<String> set = tokenScopes;
        final AuthUserSession authUserSession = session;
        final AuthHandler authHandler = callback;
        new Thread(new Runnable() {
            final Handler handler = new Handler(AuthClient.this.context.getMainLooper());
            Runnable returnCallback = new Runnable() {
                public void run() {
                    AuthClient.this.launchCognitoAuth(str, set);
                }
            };

            public void run() {
                Uri fqdn = new Builder().scheme(ClientConstants.DOMAIN_SCHEME).authority(AuthClient.this.pool.getAppWebDomain()).appendPath(ClientConstants.DOMAIN_PATH_OAUTH2).appendPath(ClientConstants.DOMAIN_PATH_TOKEN_ENDPOINT).build();
                try {
                    AuthUserSession parsedSession = AuthHttpResponseParser.parseHttpResponse(new AuthHttpClient().httpPost(new URL(fqdn.toString()), AuthClient.this.getHttpHeader(), AuthClient.this.generateTokenRefreshRequest(str, authUserSession)));
                    final AuthUserSession refreshedSession = new AuthUserSession(parsedSession.getIdToken(), parsedSession.getAccessToken(), authUserSession.getRefreshToken());
                    LocalDataManager.cacheSession(AuthClient.this.context, AuthClient.this.pool.getAppId(), refreshedSession.getUsername(), refreshedSession, AuthClient.this.pool.getScopes());
                    this.returnCallback = new Runnable() {
                        public void run() {
                            authHandler.onSuccess(refreshedSession);
                        }
                    };
                } catch (AuthInvalidGrantException e) {
                    this.returnCallback = new Runnable() {
                        public void run() {
                            AuthClient.this.launchCognitoAuth(str, set);
                        }
                    };
                } catch (Exception e2) {
                    this.returnCallback = new Runnable() {
                        public void run() {
                            authHandler.onFailure(e2);
                        }
                    };
                }
                this.handler.post(this.returnCallback);
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public Map<String, String> getHttpHeader() {
        Map<String, String> httpHeaderParams = new HashMap<>();
        httpHeaderParams.put("Content-Type", ClientConstants.HTTP_HEADER_PROP_CONTENT_TYPE_DEFAULT);
        if (this.pool.getAppSecret() != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(this.pool.getAppId()).append(":").append(this.pool.getAppSecret());
            httpHeaderParams.put("Authorization", "Basic " + Pkce.encodeBase64(builder.toString()));
        }
        return httpHeaderParams;
    }

    /* access modifiers changed from: private */
    public Map<String, String> generateTokenExchangeRequest(Uri redirectUri, String proofKey2) {
        Map<String, String> httpBodyParams = new HashMap<>();
        httpBodyParams.put(ClientConstants.TOKEN_GRANT_TYPE, ClientConstants.TOKEN_GRANT_TYPE_AUTH_CODE);
        httpBodyParams.put(ClientConstants.DOMAIN_QUERY_PARAM_CLIENT_ID, this.pool.getAppId());
        httpBodyParams.put(ClientConstants.DOMAIN_QUERY_PARAM_REDIRECT_URI, this.pool.getSignInRedirectUri());
        httpBodyParams.put(ClientConstants.DOMAIN_QUERY_PARAM_CODE_VERIFIER, proofKey2);
        httpBodyParams.put("code", redirectUri.getQueryParameter("code"));
        return httpBodyParams;
    }

    /* access modifiers changed from: private */
    public Map<String, String> generateTokenRefreshRequest(String redirectUri, AuthUserSession session) {
        Map<String, String> httpBodyParams = new HashMap<>();
        httpBodyParams.put(ClientConstants.TOKEN_GRANT_TYPE, "refresh_token");
        httpBodyParams.put(ClientConstants.DOMAIN_QUERY_PARAM_REDIRECT_URI, redirectUri);
        httpBodyParams.put(ClientConstants.DOMAIN_QUERY_PARAM_CLIENT_ID, this.pool.getAppId());
        httpBodyParams.put("refresh_token", session.getRefreshToken().getToken());
        return httpBodyParams;
    }

    /* access modifiers changed from: private */
    public void launchCognitoAuth(String redirectUri, Set<String> tokenScopes) {
        Builder builder = new Builder().scheme(ClientConstants.DOMAIN_SCHEME).authority(this.pool.getAppWebDomain()).appendPath(ClientConstants.DOMAIN_PATH_OAUTH2).appendPath(ClientConstants.DOMAIN_PATH_SIGN_IN).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_CLIENT_ID, this.pool.getAppId()).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_REDIRECT_URI, redirectUri).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_RESPONSE_TYPE, "code").appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_CODE_CHALLENGE, this.proofKeyHash).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_CODE_CHALLENGE_METHOD, ClientConstants.DOMAIN_QUERY_PARAM_CODE_CHALLENGE_METHOD_SHA256).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_STATE, this.state);
        int noOfScopes = tokenScopes.size();
        if (noOfScopes > 0) {
            StringBuilder strBuilder = new StringBuilder();
            int index = 0;
            for (String scope : tokenScopes) {
                strBuilder.append(scope);
                int index2 = index + 1;
                if (index < noOfScopes - 1) {
                    strBuilder.append(" ");
                }
                index = index2;
            }
            builder.appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_SCOPES, strBuilder.toString());
        }
        Uri fqdn = builder.build();
        LocalDataManager.cacheState(this.context, this.state, this.proofKey, tokenScopes);
        launchCustomTabs(fqdn);
    }

    private void launchSignOut(String redirectUri) {
        launchCustomTabs(new Builder().scheme(ClientConstants.DOMAIN_SCHEME).authority(this.pool.getAppWebDomain()).appendPath(ClientConstants.DOMAIN_PATH_SIGN_OUT).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_CLIENT_ID, this.pool.getAppId()).appendQueryParameter(ClientConstants.DOMAIN_QUERY_PARAM_LOGOUT_URI, redirectUri).build());
    }

    private void launchCustomTabs(Uri uri) {
        try {
            this.mCustomTabsIntent = new CustomTabsIntent.Builder(this.mCustomTabsSession).build();
            this.mCustomTabsIntent.intent.setPackage(ClientConstants.CHROME_PACKAGE);
            this.mCustomTabsIntent.intent.addFlags(1073741824);
            this.mCustomTabsIntent.intent.addFlags(268435456);
            this.mCustomTabsIntent.launchUrl(this.context, uri);
        } catch (Exception e) {
            this.userHandler.onFailure(e);
        }
    }

    private void preWarmChrome() {
        this.mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                AuthClient.this.mCustomTabsClient = client;
                AuthClient.this.mCustomTabsClient.warmup(0);
                AuthClient.this.mCustomTabsSession = AuthClient.this.mCustomTabsClient.newSession(AuthClient.this.customTabsCallback);
            }

            public void onServiceDisconnected(ComponentName name) {
                AuthClient.this.mCustomTabsClient = null;
            }
        };
        boolean bindCustomTabsService = CustomTabsClient.bindCustomTabsService(this.context, ClientConstants.CHROME_PACKAGE, this.mCustomTabsServiceConnection);
    }
}
