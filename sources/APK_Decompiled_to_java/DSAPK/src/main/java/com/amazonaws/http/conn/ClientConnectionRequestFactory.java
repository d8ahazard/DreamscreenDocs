package com.amazonaws.http.conn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionRequest;

class ClientConnectionRequestFactory {
    private static final Class<?>[] INTERFACES = {ClientConnectionRequest.class, Wrapped.class};
    /* access modifiers changed from: private */
    public static final Log log = LogFactory.getLog(ClientConnectionRequestFactory.class);

    private static class Handler implements InvocationHandler {
        private final ClientConnectionRequest orig;

        Handler(ClientConnectionRequest orig2) {
            this.orig = orig2;
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object invoke(java.lang.Object r6, java.lang.reflect.Method r7, java.lang.Object[] r8) throws java.lang.Throwable {
            /*
                r5 = this;
                java.lang.String r2 = "getConnection"
                java.lang.String r3 = r7.getName()     // Catch:{ InvocationTargetException -> 0x0032 }
                boolean r2 = r2.equals(r3)     // Catch:{ InvocationTargetException -> 0x0032 }
                if (r2 == 0) goto L_0x0041
                com.amazonaws.metrics.ServiceLatencyProvider r1 = new com.amazonaws.metrics.ServiceLatencyProvider     // Catch:{ InvocationTargetException -> 0x0032 }
                com.amazonaws.util.AWSServiceMetrics r2 = com.amazonaws.util.AWSServiceMetrics.HttpClientGetConnectionTime     // Catch:{ InvocationTargetException -> 0x0032 }
                r1.<init>(r2)     // Catch:{ InvocationTargetException -> 0x0032 }
                org.apache.http.conn.ClientConnectionRequest r2 = r5.orig     // Catch:{ all -> 0x0025 }
                java.lang.Object r2 = r7.invoke(r2, r8)     // Catch:{ all -> 0x0025 }
                com.amazonaws.metrics.ServiceMetricCollector r3 = com.amazonaws.metrics.AwsSdkMetrics.getServiceMetricCollector()     // Catch:{ InvocationTargetException -> 0x0032 }
                com.amazonaws.metrics.ServiceLatencyProvider r4 = r1.endTiming()     // Catch:{ InvocationTargetException -> 0x0032 }
                r3.collectLatency(r4)     // Catch:{ InvocationTargetException -> 0x0032 }
            L_0x0024:
                return r2
            L_0x0025:
                r2 = move-exception
                com.amazonaws.metrics.ServiceMetricCollector r3 = com.amazonaws.metrics.AwsSdkMetrics.getServiceMetricCollector()     // Catch:{ InvocationTargetException -> 0x0032 }
                com.amazonaws.metrics.ServiceLatencyProvider r4 = r1.endTiming()     // Catch:{ InvocationTargetException -> 0x0032 }
                r3.collectLatency(r4)     // Catch:{ InvocationTargetException -> 0x0032 }
                throw r2     // Catch:{ InvocationTargetException -> 0x0032 }
            L_0x0032:
                r0 = move-exception
                org.apache.commons.logging.Log r2 = com.amazonaws.http.conn.ClientConnectionRequestFactory.log
                java.lang.String r3 = ""
                r2.debug(r3, r0)
                java.lang.Throwable r2 = r0.getCause()
                throw r2
            L_0x0041:
                org.apache.http.conn.ClientConnectionRequest r2 = r5.orig     // Catch:{ InvocationTargetException -> 0x0032 }
                java.lang.Object r2 = r7.invoke(r2, r8)     // Catch:{ InvocationTargetException -> 0x0032 }
                goto L_0x0024
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.http.conn.ClientConnectionRequestFactory.Handler.invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[]):java.lang.Object");
        }
    }

    ClientConnectionRequestFactory() {
    }

    static ClientConnectionRequest wrap(ClientConnectionRequest orig) {
        if (!(orig instanceof Wrapped)) {
            return (ClientConnectionRequest) Proxy.newProxyInstance(ClientConnectionRequestFactory.class.getClassLoader(), INTERFACES, new Handler(orig));
        }
        throw new IllegalArgumentException();
    }
}
