package com.amazonaws.mobileconnectors.cognitoauth;

import android.content.Context;
import android.net.Uri;
import android.util.Patterns;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthInvalidParameterException;
import com.amazonaws.mobileconnectors.cognitoauth.handlers.AuthHandler;
import com.amazonaws.mobileconnectors.cognitoauth.util.LocalDataManager;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

public final class Auth {
    private final String appId;
    private final String appSecret;
    private final String appWebDomain;
    private final Context context;
    private final Set<String> scopes;
    private final String signInRedirectUri;
    private final String signOutRedirectUri;
    private AuthClient user;

    public static final class Builder {
        private String mAppClientId;
        private Context mAppContext;
        private String mAppSecret;
        private String mAppWebDomain;
        private Set<String> mScopes;
        private String mSignInRedirect;
        private String mSignOutRedirect;
        private AuthHandler mUserHandler;

        public Builder setAppClientId(String mAppClientId2) {
            this.mAppClientId = mAppClientId2;
            return this;
        }

        public Builder setApplicationContext(Context mAppContext2) {
            this.mAppContext = mAppContext2;
            return this;
        }

        public Builder setAppClientSecret(String mAppSecret2) {
            this.mAppSecret = mAppSecret2;
            return this;
        }

        public Builder setAppCognitoWebDomain(String mAppWebDomain2) {
            this.mAppWebDomain = mAppWebDomain2;
            return this;
        }

        public Builder setSignInRedirect(String mSignInRedirect2) {
            this.mSignInRedirect = mSignInRedirect2;
            return this;
        }

        public Builder setSignOutRedirect(String mSignOutRedirect2) {
            this.mSignOutRedirect = mSignOutRedirect2;
            return this;
        }

        public Builder setScopes(Set<String> mScopes2) {
            this.mScopes = mScopes2;
            return this;
        }

        public Builder setAuthHandler(AuthHandler mUserHandler2) {
            this.mUserHandler = mUserHandler2;
            return this;
        }

        public Auth build() {
            validateCognitoAuthParameters();
            return new Auth(this.mAppContext, this.mAppWebDomain, this.mAppClientId, this.mAppSecret, this.mSignInRedirect, this.mSignOutRedirect, this.mScopes, this.mUserHandler);
        }

        private void validateCognitoAuthParameters() {
            new StringBuilder();
            boolean error = false;
            JSONObject errorMessage = new JSONObject();
            try {
                if (this.mAppContext == null) {
                    errorMessage.put("ApplicationContext", "cannot be null");
                    error = true;
                }
                if (this.mAppClientId == null || this.mAppClientId.length() < 1) {
                    errorMessage.put("AppClientId", "invalid");
                    error = true;
                }
                if (!isValidDomain(this.mAppWebDomain)) {
                    errorMessage.put("AppCognitoWebDomain", "invalid");
                    error = true;
                }
                if (this.mSignInRedirect == null) {
                    errorMessage.put("SignInRedirect", "cannot be null");
                    error = true;
                }
                if (this.mSignOutRedirect == null) {
                    errorMessage.put("SignOutRedirect", "cannot be null");
                    error = true;
                }
                if (this.mScopes == null) {
                    this.mScopes = new HashSet();
                }
                if (this.mAppSecret != null && this.mAppSecret.length() < 1) {
                    this.mAppSecret = null;
                }
                if (this.mUserHandler == null) {
                    errorMessage.put("AuthHandler", "cannot be null");
                    error = true;
                }
                if (error) {
                    throw new AuthInvalidParameterException(errorMessage.toString());
                }
            } catch (Exception e) {
                throw new AuthInvalidParameterException("validation failed", e);
            }
        }

        private boolean isValidDomain(String uri) {
            if (uri == null) {
                return false;
            }
            return Patterns.DOMAIN_NAME.matcher(uri).matches();
        }
    }

    private Auth(Context context2, String appWebDomain2, String appId2, String appSecret2, String signInRedirectUri2, String signOutRedirectUri2, Set<String> scopes2, AuthHandler userHandler) {
        this.context = context2;
        this.appWebDomain = appWebDomain2;
        this.appId = appId2;
        this.appSecret = appSecret2;
        this.signInRedirectUri = signInRedirectUri2;
        this.signOutRedirectUri = signOutRedirectUri2;
        this.scopes = scopes2;
        this.user = new AuthClient(context2, this);
        this.user = new AuthClient(context2, this);
        this.user.setUserHandler(userHandler);
        getCurrentUser();
    }

    public Auth getCurrentUser() {
        this.user.setUsername(LocalDataManager.getLastAuthUser(this.context, this.appId));
        return this;
    }

    public String getAppWebDomain() {
        return this.appWebDomain;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public Set<String> getScopes() {
        return this.scopes;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public String getSignInRedirectUri() {
        return this.signInRedirectUri;
    }

    public String getSignOutRedirectUri() {
        return this.signOutRedirectUri;
    }

    public void getSession() {
        this.user.getSession();
    }

    public void signOut() {
        this.user.signOut();
    }

    public boolean isAuthenticated() {
        return this.user.isAuthenticated();
    }

    public void getTokens(Uri uri) {
        this.user.getTokens(uri);
    }

    public Auth setUsername(String username) {
        this.user.setUsername(username);
        return this;
    }
}
