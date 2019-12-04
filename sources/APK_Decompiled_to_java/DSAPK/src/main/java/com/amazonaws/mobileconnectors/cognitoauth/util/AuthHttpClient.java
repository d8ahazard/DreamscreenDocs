package com.amazonaws.mobileconnectors.cognitoauth.util;

public final class AuthHttpClient {
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String httpPost(java.net.URL r23, java.util.Map<java.lang.String, java.lang.String> r24, java.util.Map<java.lang.String, java.lang.String> r25) throws java.lang.Exception {
        /*
            r22 = this;
            if (r23 == 0) goto L_0x0010
            if (r25 == 0) goto L_0x0010
            int r18 = r25.size()
            r19 = 1
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x0018
        L_0x0010:
            com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthClientException r18 = new com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthClientException
            java.lang.String r19 = "Invalid http request parameters"
            r18.<init>(r19)
            throw r18
        L_0x0018:
            java.net.URLConnection r8 = r23.openConnection()
            javax.net.ssl.HttpsURLConnection r8 = (javax.net.ssl.HttpsURLConnection) r8
            r6 = 0
            r2 = 0
            java.lang.String r18 = "POST"
            r0 = r18
            r8.setRequestMethod(r0)     // Catch:{ Exception -> 0x0056 }
            r18 = 1
            r0 = r18
            r8.setDoOutput(r0)     // Catch:{ Exception -> 0x0056 }
            java.util.Set r18 = r24.entrySet()     // Catch:{ Exception -> 0x0056 }
            java.util.Iterator r20 = r18.iterator()     // Catch:{ Exception -> 0x0056 }
        L_0x0036:
            boolean r18 = r20.hasNext()     // Catch:{ Exception -> 0x0056 }
            if (r18 == 0) goto L_0x0064
            java.lang.Object r12 = r20.next()     // Catch:{ Exception -> 0x0056 }
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12     // Catch:{ Exception -> 0x0056 }
            java.lang.Object r18 = r12.getKey()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r18 = (java.lang.String) r18     // Catch:{ Exception -> 0x0056 }
            java.lang.Object r19 = r12.getValue()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r19 = (java.lang.String) r19     // Catch:{ Exception -> 0x0056 }
            r0 = r18
            r1 = r19
            r8.addRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0036
        L_0x0056:
            r4 = move-exception
        L_0x0057:
            throw r4     // Catch:{ all -> 0x0058 }
        L_0x0058:
            r18 = move-exception
        L_0x0059:
            if (r6 == 0) goto L_0x005e
            r6.close()
        L_0x005e:
            if (r2 == 0) goto L_0x0063
            r2.close()
        L_0x0063:
            throw r18
        L_0x0064:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0056 }
            r13.<init>()     // Catch:{ Exception -> 0x0056 }
            int r9 = r25.size()     // Catch:{ Exception -> 0x0056 }
            java.util.Set r18 = r25.entrySet()     // Catch:{ Exception -> 0x0056 }
            java.util.Iterator r19 = r18.iterator()     // Catch:{ Exception -> 0x0056 }
            r10 = r9
        L_0x0076:
            boolean r18 = r19.hasNext()     // Catch:{ Exception -> 0x0056 }
            if (r18 == 0) goto L_0x00ca
            java.lang.Object r12 = r19.next()     // Catch:{ Exception -> 0x0056 }
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12     // Catch:{ Exception -> 0x0056 }
            java.lang.Object r18 = r12.getKey()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r18 = (java.lang.String) r18     // Catch:{ Exception -> 0x0056 }
            java.lang.String r20 = "UTF-8"
            r0 = r18
            r1 = r20
            java.lang.String r18 = java.net.URLEncoder.encode(r0, r1)     // Catch:{ Exception -> 0x0056 }
            r0 = r18
            java.lang.StringBuilder r18 = r13.append(r0)     // Catch:{ Exception -> 0x0056 }
            r20 = 61
            r0 = r18
            r1 = r20
            java.lang.StringBuilder r20 = r0.append(r1)     // Catch:{ Exception -> 0x0056 }
            java.lang.Object r18 = r12.getValue()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r18 = (java.lang.String) r18     // Catch:{ Exception -> 0x0056 }
            java.lang.String r21 = "UTF-8"
            r0 = r18
            r1 = r21
            java.lang.String r18 = java.net.URLEncoder.encode(r0, r1)     // Catch:{ Exception -> 0x0056 }
            r0 = r20
            r1 = r18
            r0.append(r1)     // Catch:{ Exception -> 0x0056 }
            int r9 = r10 + -1
            r18 = 1
            r0 = r18
            if (r10 <= r0) goto L_0x00c8
            r18 = 38
            r0 = r18
            r13.append(r0)     // Catch:{ Exception -> 0x0056 }
        L_0x00c8:
            r10 = r9
            goto L_0x0076
        L_0x00ca:
            java.lang.String r14 = r13.toString()     // Catch:{ Exception -> 0x0056 }
            java.io.DataOutputStream r7 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0056 }
            java.io.OutputStream r18 = r8.getOutputStream()     // Catch:{ Exception -> 0x0056 }
            r0 = r18
            r7.<init>(r0)     // Catch:{ Exception -> 0x0056 }
            r7.writeBytes(r14)     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            r7.flush()     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            java.util.Map r15 = r8.getHeaderFields()     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            int r16 = r8.getResponseCode()     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            r18 = 200(0xc8, float:2.8E-43)
            r0 = r16
            r1 = r18
            if (r0 < r1) goto L_0x013d
            r18 = 500(0x1f4, float:7.0E-43)
            r0 = r16
            r1 = r18
            if (r0 >= r1) goto L_0x013d
            r18 = 400(0x190, float:5.6E-43)
            r0 = r16
            r1 = r18
            if (r0 >= r1) goto L_0x0129
            java.io.InputStream r5 = r8.getInputStream()     // Catch:{ Exception -> 0x0147, all -> 0x014b }
        L_0x0103:
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            java.io.InputStreamReader r18 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            r0 = r18
            r0.<init>(r5)     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            r0 = r18
            r3.<init>(r0)     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            java.lang.String r11 = ""
            java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0124, all -> 0x014f }
            r17.<init>()     // Catch:{ Exception -> 0x0124, all -> 0x014f }
        L_0x0118:
            java.lang.String r11 = r3.readLine()     // Catch:{ Exception -> 0x0124, all -> 0x014f }
            if (r11 == 0) goto L_0x012e
            r0 = r17
            r0.append(r11)     // Catch:{ Exception -> 0x0124, all -> 0x014f }
            goto L_0x0118
        L_0x0124:
            r4 = move-exception
            r2 = r3
            r6 = r7
            goto L_0x0057
        L_0x0129:
            java.io.InputStream r5 = r8.getErrorStream()     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            goto L_0x0103
        L_0x012e:
            java.lang.String r18 = r17.toString()     // Catch:{ Exception -> 0x0124, all -> 0x014f }
            if (r7 == 0) goto L_0x0137
            r7.close()
        L_0x0137:
            if (r3 == 0) goto L_0x013c
            r3.close()
        L_0x013c:
            return r18
        L_0x013d:
            com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthServiceException r18 = new com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthServiceException     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            java.lang.String r19 = r8.getResponseMessage()     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            r18.<init>(r19)     // Catch:{ Exception -> 0x0147, all -> 0x014b }
            throw r18     // Catch:{ Exception -> 0x0147, all -> 0x014b }
        L_0x0147:
            r4 = move-exception
            r6 = r7
            goto L_0x0057
        L_0x014b:
            r18 = move-exception
            r6 = r7
            goto L_0x0059
        L_0x014f:
            r18 = move-exception
            r2 = r3
            r6 = r7
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.mobileconnectors.cognitoauth.util.AuthHttpClient.httpPost(java.net.URL, java.util.Map, java.util.Map):java.lang.String");
    }
}
