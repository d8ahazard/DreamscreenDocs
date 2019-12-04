package com.amazonaws.mobileconnectors.cognitoauth.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.amazonaws.mobileconnectors.cognitoauth.AuthUserSession;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.AccessToken;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.IdToken;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.RefreshToken;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class LocalDataManager {
    static final String TAG = LocalDataManager.class.getSimpleName();

    public static String getLastAuthUser(Context context, String clientId) {
        boolean z = false;
        if (context == null || clientId == null) {
            throw new InvalidParameterException("Application context, and application domain cannot be null");
        }
        try {
            return context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0).getString(String.format(Locale.US, "%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, ClientConstants.APP_LAST_AUTH_USER}), null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read from SharedPreferences", e);
            return z;
        }
    }

    public static AuthUserSession getCachedSession(Context context, String clientId, String username, Set<String> scopes) {
        AuthUserSession session = new AuthUserSession(null, null, null);
        if (username != null) {
            if (context == null || clientId == null || clientId.isEmpty()) {
                throw new InvalidParameterException("Application context, and application domain cannot be null");
            }
            String cachedIdTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_ID});
            String cachedAccessTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_ACCESS});
            String cachedRefreshTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_REFRESH});
            String cachedTokenScopes = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_KEY_SCOPES});
            try {
                SharedPreferences localCache = context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0);
                if (!localCache.getStringSet(cachedTokenScopes, new HashSet()).equals(scopes)) {
                    return session;
                }
                session = new AuthUserSession(new IdToken(localCache.getString(cachedIdTokenKey, null)), new AccessToken(localCache.getString(cachedAccessTokenKey, null)), new RefreshToken(localCache.getString(cachedRefreshTokenKey, null)));
            } catch (Exception e) {
                Log.e(TAG, "Failed to read from SharedPreferences", e);
            }
        }
        return session;
    }

    public static void cacheSession(Context context, String clientId, String username, AuthUserSession session, Set<String> scopes) {
        if (context == null || clientId == null || clientId.isEmpty() || username == null || session == null) {
            Log.e(TAG, "Application context, and application domain cannot be null");
            return;
        }
        String cachedIdTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_ID});
        String cachedAccessTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_ACCESS});
        String cachedRefreshTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_REFRESH});
        String cachedTokenTypeKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_KEY_TYPE});
        String cachedTokenScopes = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_KEY_SCOPES});
        String lastAuthUserKey = String.format(Locale.US, "%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, ClientConstants.APP_LAST_AUTH_USER});
        try {
            Editor localCacheEditor = context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0).edit();
            localCacheEditor.putString(cachedTokenTypeKey, ClientConstants.SESSION_TYPE_JWT);
            localCacheEditor.putString(cachedIdTokenKey, session.getIdToken().getJWTToken());
            localCacheEditor.putString(cachedAccessTokenKey, session.getAccessToken().getJWTToken());
            localCacheEditor.putString(cachedRefreshTokenKey, session.getRefreshToken().getToken());
            if (scopes != null && scopes.size() > 0) {
                localCacheEditor.putStringSet(cachedTokenScopes, scopes);
            }
            localCacheEditor.putString(lastAuthUserKey, username).apply();
        } catch (Exception e) {
            Log.e(TAG, "Failed while writing to SharedPreferences", e);
        }
    }

    public static void cacheState(Context context, String key, String proofKey, Set<String> scopes) {
        try {
            Editor localCacheEditor = context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0).edit();
            localCacheEditor.putString(key + ClientConstants.DOMAIN_QUERY_PARAM_CODE_CHALLENGE, proofKey);
            localCacheEditor.putStringSet(key + ClientConstants.DOMAIN_QUERY_PARAM_SCOPES, scopes).apply();
        } catch (Exception e) {
            Log.e(TAG, "Failed while writing to SharedPreferences", e);
        }
    }

    public static String getCachedProofKey(Context context, String key) {
        boolean z = false;
        try {
            return context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0).getString(key + ClientConstants.DOMAIN_QUERY_PARAM_CODE_CHALLENGE, null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read from SharedPreferences", e);
            return z;
        }
    }

    public static Set<String> getCachedScopes(Context context, String key) {
        Set<String> cachedSet = new HashSet<>();
        try {
            return context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0).getStringSet(key + ClientConstants.DOMAIN_QUERY_PARAM_SCOPES, cachedSet);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read from SharedPreferences", e);
            return cachedSet;
        }
    }

    public static void clearCache(Context context, String clientId, String username) {
        if (username != null) {
            String cachedIdTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_ID});
            String cachedAccessTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_ACCESS});
            String cachedRefreshTokenKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_TYPE_REFRESH});
            String cachedTokenTypeKey = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_KEY_TYPE});
            String cachedTokenScopes = String.format(Locale.US, "%s.%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, username, ClientConstants.TOKEN_KEY_SCOPES});
            String lastAuthUserKey = String.format(Locale.US, "%s.%s.%s", new Object[]{ClientConstants.APP_LOCAL_CACHE_KEY_PREFIX, clientId, ClientConstants.APP_LAST_AUTH_USER});
            try {
                Editor localCacheEditor = context.getSharedPreferences(ClientConstants.APP_LOCAL_CACHE, 0).edit();
                localCacheEditor.remove(cachedIdTokenKey);
                localCacheEditor.remove(cachedAccessTokenKey);
                localCacheEditor.remove(cachedRefreshTokenKey);
                localCacheEditor.remove(cachedTokenTypeKey);
                localCacheEditor.remove(cachedTokenScopes);
                localCacheEditor.remove(lastAuthUserKey).apply();
            } catch (Exception e) {
                Log.e(TAG, "Failed while writing to SharedPreferences", e);
            }
        }
    }
}
