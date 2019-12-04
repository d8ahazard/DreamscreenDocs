package com.amazonaws.http.conn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;

public class ClientConnectionManagerFactory {
    /* access modifiers changed from: private */
    public static final Log log = LogFactory.getLog(ClientConnectionManagerFactory.class);

    private static class Handler implements InvocationHandler {
        private final ClientConnectionManager orig;

        Handler(ClientConnectionManager real) {
            this.orig = real;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                Object ret = method.invoke(this.orig, args);
                if (ret instanceof ClientConnectionRequest) {
                    return ClientConnectionRequestFactory.wrap((ClientConnectionRequest) ret);
                }
                return ret;
            } catch (InvocationTargetException e) {
                ClientConnectionManagerFactory.log.debug("", e);
                throw e.getCause();
            }
        }
    }

    public static ClientConnectionManager wrap(ClientConnectionManager orig) {
        if (orig instanceof Wrapped) {
            throw new IllegalArgumentException();
        }
        return (ClientConnectionManager) Proxy.newProxyInstance(ClientConnectionManagerFactory.class.getClassLoader(), new Class[]{ClientConnectionManager.class, Wrapped.class}, new Handler(orig));
    }
}
