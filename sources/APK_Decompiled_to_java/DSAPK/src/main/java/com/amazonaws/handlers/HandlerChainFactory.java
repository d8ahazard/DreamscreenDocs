package com.amazonaws.handlers;

import java.util.List;

public class HandlerChainFactory {
    public List<RequestHandler2> newRequestHandlerChain(String resource) {
        return createRequestHandlerChain(resource, RequestHandler.class);
    }

    public List<RequestHandler2> newRequestHandler2Chain(String resource) {
        return createRequestHandlerChain(resource, RequestHandler2.class);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0084 A[SYNTHETIC, Splitter:B:32:0x0084] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.amazonaws.handlers.RequestHandler2> createRequestHandlerChain(java.lang.String r14, java.lang.Class<?> r15) {
        /*
            r13 = this;
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r5 = 0
            java.lang.Class r10 = r13.getClass()     // Catch:{ Exception -> 0x00d2 }
            java.io.InputStream r4 = r10.getResourceAsStream(r14)     // Catch:{ Exception -> 0x00d2 }
            if (r4 != 0) goto L_0x0016
            if (r5 == 0) goto L_0x0015
            r5.close()     // Catch:{ IOException -> 0x00ca }
        L_0x0015:
            return r3
        L_0x0016:
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00d2 }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00d2 }
            java.nio.charset.Charset r11 = com.amazonaws.util.StringUtils.UTF8     // Catch:{ Exception -> 0x00d2 }
            r10.<init>(r4, r11)     // Catch:{ Exception -> 0x00d2 }
            r6.<init>(r10)     // Catch:{ Exception -> 0x00d2 }
        L_0x0022:
            java.lang.String r8 = r6.readLine()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            if (r8 != 0) goto L_0x002f
            if (r6 == 0) goto L_0x002d
            r6.close()     // Catch:{ IOException -> 0x00cd }
        L_0x002d:
            r5 = r6
            goto L_0x0015
        L_0x002f:
            java.lang.String r8 = r8.trim()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.String r10 = ""
            boolean r10 = r10.equals(r8)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            if (r10 != 0) goto L_0x0022
            r10 = 2
            java.lang.Class[] r10 = new java.lang.Class[r10]     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r11 = 0
            r10[r11] = r15     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r11 = 1
            java.lang.Class r12 = r13.getClass()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r10[r11] = r12     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.Class r7 = com.amazonaws.util.ClassLoaderHelper.loadClass(r8, r10)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.Object r9 = r7.newInstance()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            boolean r10 = r15.isInstance(r9)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            if (r10 == 0) goto L_0x00a1
            java.lang.Class<com.amazonaws.handlers.RequestHandler2> r10 = com.amazonaws.handlers.RequestHandler2.class
            if (r15 != r10) goto L_0x0088
            r0 = r9
            com.amazonaws.handlers.RequestHandler2 r0 = (com.amazonaws.handlers.RequestHandler2) r0     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r2 = r0
            r3.add(r2)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            goto L_0x0022
        L_0x0062:
            r1 = move-exception
            r5 = r6
        L_0x0064:
            com.amazonaws.AmazonClientException r10 = new com.amazonaws.AmazonClientException     // Catch:{ all -> 0x0081 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0081 }
            r11.<init>()     // Catch:{ all -> 0x0081 }
            java.lang.String r12 = "Unable to instantiate request handler chain for client: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0081 }
            java.lang.String r12 = r1.getMessage()     // Catch:{ all -> 0x0081 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0081 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0081 }
            r10.<init>(r11, r1)     // Catch:{ all -> 0x0081 }
            throw r10     // Catch:{ all -> 0x0081 }
        L_0x0081:
            r10 = move-exception
        L_0x0082:
            if (r5 == 0) goto L_0x0087
            r5.close()     // Catch:{ IOException -> 0x00d0 }
        L_0x0087:
            throw r10
        L_0x0088:
            java.lang.Class<com.amazonaws.handlers.RequestHandler> r10 = com.amazonaws.handlers.RequestHandler.class
            if (r15 != r10) goto L_0x009b
            r0 = r9
            com.amazonaws.handlers.RequestHandler r0 = (com.amazonaws.handlers.RequestHandler) r0     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r2 = r0
            com.amazonaws.handlers.RequestHandler2 r10 = com.amazonaws.handlers.RequestHandler2.adapt(r2)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r3.add(r10)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            goto L_0x0022
        L_0x0098:
            r10 = move-exception
            r5 = r6
            goto L_0x0082
        L_0x009b:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r10.<init>()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            throw r10     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
        L_0x00a1:
            com.amazonaws.AmazonClientException r10 = new com.amazonaws.AmazonClientException     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r11.<init>()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.String r12 = "Unable to instantiate request handler chain for client.  Listed request handler ('"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.StringBuilder r11 = r11.append(r8)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.String r12 = "') does not implement the "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.StringBuilder r11 = r11.append(r15)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.String r12 = " API."
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
            throw r10     // Catch:{ Exception -> 0x0062, all -> 0x0098 }
        L_0x00ca:
            r10 = move-exception
            goto L_0x0015
        L_0x00cd:
            r10 = move-exception
            goto L_0x002d
        L_0x00d0:
            r11 = move-exception
            goto L_0x0087
        L_0x00d2:
            r1 = move-exception
            goto L_0x0064
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.handlers.HandlerChainFactory.createRequestHandlerChain(java.lang.String, java.lang.Class):java.util.List");
    }
}
