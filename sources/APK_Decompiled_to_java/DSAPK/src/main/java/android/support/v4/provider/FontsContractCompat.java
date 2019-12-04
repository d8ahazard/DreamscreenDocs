package androidx.core.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.SelfDestructiveThread.ReplyCallback;
import androidx.collection.LruCache;
import androidx.core.util.Preconditions;
import androidx.collection.SimpleArrayMap;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontsContractCompat {
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String PARCEL_FONT_RESULTS = "font_results";
    @RestrictTo({Scope.LIBRARY_GROUP})
    static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    @RestrictTo({Scope.LIBRARY_GROUP})
    static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    private static final String TAG = "FontsContractCompat";
    private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS);
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() {
        public int compare(byte[] l, byte[] r) {
            if (l.length != r.length) {
                return l.length - r.length;
            }
            for (int i = 0; i < l.length; i++) {
                if (l[i] != r[i]) {
                    return l[i] - r[i];
                }
            }
            return 0;
        }
    };
    /* access modifiers changed from: private */
    public static final Object sLock = new Object();
    /* access modifiers changed from: private */
    @GuardedBy("sLock")
    public static final SimpleArrayMap<String, ArrayList<ReplyCallback<TypefaceResult>>> sPendingReplies = new SimpleArrayMap<>();
    /* access modifiers changed from: private */
    public static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);

    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        @interface FontResultStatus {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public FontFamilyResult(int statusCode, @Nullable FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        @RestrictTo({Scope.LIBRARY_GROUP})
        public FontInfo(@NonNull Uri uri, @IntRange(from = 0) int ttcIndex, @IntRange(from = 1, to = 1000) int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
        }

        @NonNull
        public Uri getUri() {
            return this.mUri;
        }

        @IntRange(from = 0)
        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        @IntRange(from = 1, to = 1000)
        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final int RESULT_OK = 0;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface FontRequestFailReason {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int reason) {
        }
    }

    private static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(@Nullable Typeface typeface, int result) {
            this.mTypeface = typeface;
            this.mResult = result;
        }
    }

    private FontsContractCompat() {
    }

    /* access modifiers changed from: private */
    @NonNull
    public static TypefaceResult getFontInternal(Context context, FontRequest request, int style) {
        int resultCode;
        int i = -3;
        try {
            FontFamilyResult result = fetchFonts(context, null, request);
            if (result.getStatusCode() == 0) {
                Typeface typeface = TypefaceCompat.createFromFontInfo(context, null, result.getFonts(), style);
                if (typeface != null) {
                    i = 0;
                }
                return new TypefaceResult(typeface, i);
            }
            if (result.getStatusCode() == 1) {
                resultCode = -2;
            } else {
                resultCode = -3;
            }
            return new TypefaceResult(null, resultCode);
        } catch (NameNotFoundException e) {
            return new TypefaceResult(null, -1);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final void resetCache() {
        sTypefaceCache.evictAll();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0094, code lost:
        sBackgroundThread.postAndReply(r2, new android.support.v4.provider.FontsContractCompat.AnonymousClass3());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return null;
     */
    @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface getFontSync(final android.content.Context r9, final androidx.core.provider.FontRequest r10, @androidx.annotation.Nullable final androidx.core.content.res.ResourcesCompat.FontCallback r11, @androidx.annotation.Nullable final android.os.Handler r12, boolean r13, int r14, final int r15) {
        /*
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = r10.getIdentifier()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "-"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r15)
            java.lang.String r3 = r7.toString()
            android.support.v4.util.LruCache<java.lang.String, android.graphics.Typeface> r7 = sTypefaceCache
            java.lang.Object r0 = r7.get(r3)
            android.graphics.Typeface r0 = (android.graphics.Typeface) r0
            if (r0 == 0) goto L_0x002b
            if (r11 == 0) goto L_0x002a
            r11.onFontRetrieved(r0)
        L_0x002a:
            return r0
        L_0x002b:
            if (r13 == 0) goto L_0x0048
            r7 = -1
            if (r14 != r7) goto L_0x0048
            android.support.v4.provider.FontsContractCompat$TypefaceResult r6 = getFontInternal(r9, r10, r15)
            if (r11 == 0) goto L_0x003f
            int r7 = r6.mResult
            if (r7 != 0) goto L_0x0042
            android.graphics.Typeface r7 = r6.mTypeface
            r11.callbackSuccessAsync(r7, r12)
        L_0x003f:
            android.graphics.Typeface r0 = r6.mTypeface
            goto L_0x002a
        L_0x0042:
            int r7 = r6.mResult
            r11.callbackFailAsync(r7, r12)
            goto L_0x003f
        L_0x0048:
            android.support.v4.provider.FontsContractCompat$1 r2 = new android.support.v4.provider.FontsContractCompat$1
            r2.<init>(r9, r10, r15, r3)
            if (r13 == 0) goto L_0x005d
            android.support.v4.provider.SelfDestructiveThread r7 = sBackgroundThread     // Catch:{ InterruptedException -> 0x005a }
            java.lang.Object r7 = r7.postAndWait(r2, r14)     // Catch:{ InterruptedException -> 0x005a }
            android.support.v4.provider.FontsContractCompat$TypefaceResult r7 = (android.support.v4.provider.FontsContractCompat.TypefaceResult) r7     // Catch:{ InterruptedException -> 0x005a }
            android.graphics.Typeface r0 = r7.mTypeface     // Catch:{ InterruptedException -> 0x005a }
            goto L_0x002a
        L_0x005a:
            r1 = move-exception
            r0 = 0
            goto L_0x002a
        L_0x005d:
            if (r11 != 0) goto L_0x007e
            r5 = 0
        L_0x0060:
            java.lang.Object r8 = sLock
            monitor-enter(r8)
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.util.ArrayList<android.support.v4.provider.SelfDestructiveThread$ReplyCallback<android.support.v4.provider.FontsContractCompat$TypefaceResult>>> r7 = sPendingReplies     // Catch:{ all -> 0x007b }
            boolean r7 = r7.containsKey(r3)     // Catch:{ all -> 0x007b }
            if (r7 == 0) goto L_0x0084
            if (r5 == 0) goto L_0x0078
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.util.ArrayList<android.support.v4.provider.SelfDestructiveThread$ReplyCallback<android.support.v4.provider.FontsContractCompat$TypefaceResult>>> r7 = sPendingReplies     // Catch:{ all -> 0x007b }
            java.lang.Object r7 = r7.get(r3)     // Catch:{ all -> 0x007b }
            java.util.ArrayList r7 = (java.util.ArrayList) r7     // Catch:{ all -> 0x007b }
            r7.add(r5)     // Catch:{ all -> 0x007b }
        L_0x0078:
            r0 = 0
            monitor-exit(r8)     // Catch:{ all -> 0x007b }
            goto L_0x002a
        L_0x007b:
            r7 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x007b }
            throw r7
        L_0x007e:
            android.support.v4.provider.FontsContractCompat$2 r5 = new android.support.v4.provider.FontsContractCompat$2
            r5.<init>(r11, r12)
            goto L_0x0060
        L_0x0084:
            if (r5 == 0) goto L_0x0093
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x007b }
            r4.<init>()     // Catch:{ all -> 0x007b }
            r4.add(r5)     // Catch:{ all -> 0x007b }
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.util.ArrayList<android.support.v4.provider.SelfDestructiveThread$ReplyCallback<android.support.v4.provider.FontsContractCompat$TypefaceResult>>> r7 = sPendingReplies     // Catch:{ all -> 0x007b }
            r7.put(r3, r4)     // Catch:{ all -> 0x007b }
        L_0x0093:
            monitor-exit(r8)     // Catch:{ all -> 0x007b }
            android.support.v4.provider.SelfDestructiveThread r7 = sBackgroundThread
            android.support.v4.provider.FontsContractCompat$3 r8 = new android.support.v4.provider.FontsContractCompat$3
            r8.<init>(r3)
            r7.postAndReply(r2, r8)
            r0 = 0
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.provider.FontsContractCompat.getFontSync(android.content.Context, android.support.v4.provider.FontRequest, android.support.v4.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, int, int):android.graphics.Typeface");
    }

    public static void requestFont(@NonNull final Context context, @NonNull final FontRequest request, @NonNull final FontRequestCallback callback, @NonNull Handler handler) {
        final Handler callerThreadHandler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                try {
                    FontFamilyResult result = FontsContractCompat.fetchFonts(context, null, request);
                    if (result.getStatusCode() != 0) {
                        switch (result.getStatusCode()) {
                            case 1:
                                callerThreadHandler.post(new Runnable() {
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-2);
                                    }
                                });
                                return;
                            case 2:
                                callerThreadHandler.post(new Runnable() {
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-3);
                                    }
                                });
                                return;
                            default:
                                callerThreadHandler.post(new Runnable() {
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-3);
                                    }
                                });
                                return;
                        }
                    } else {
                        FontInfo[] fonts = result.getFonts();
                        if (fonts == null || fonts.length == 0) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(1);
                                }
                            });
                            return;
                        }
                        int length = fonts.length;
                        int i = 0;
                        while (i < length) {
                            FontInfo font = fonts[i];
                            if (font.getResultCode() != 0) {
                                final int resultCode = font.getResultCode();
                                if (resultCode < 0) {
                                    callerThreadHandler.post(new Runnable() {
                                        public void run() {
                                            callback.onTypefaceRequestFailed(-3);
                                        }
                                    });
                                    return;
                                } else {
                                    callerThreadHandler.post(new Runnable() {
                                        public void run() {
                                            callback.onTypefaceRequestFailed(resultCode);
                                        }
                                    });
                                    return;
                                }
                            } else {
                                i++;
                            }
                        }
                        final Typeface typeface = FontsContractCompat.buildTypeface(context, null, fonts);
                        if (typeface == null) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRetrieved(typeface);
                                }
                            });
                        }
                    }
                } catch (NameNotFoundException e) {
                    callerThreadHandler.post(new Runnable() {
                        public void run() {
                            callback.onTypefaceRequestFailed(-1);
                        }
                    });
                }
            }
        });
    }

    @Nullable
    public static Typeface buildTypeface(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fonts, 0);
    }

    @RequiresApi(19)
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    out.put(uri, TypefaceCompatUtil.mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    @NonNull
    public static FontFamilyResult fetchFonts(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontRequest request) throws NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        return new FontFamilyResult(0, getFontFromProvider(context, request, providerInfo.authority, cancellationSignal));
    }

    @VisibleForTesting
    @RestrictTo({Scope.LIBRARY_GROUP})
    @Nullable
    public static ProviderInfo getProvider(@NonNull PackageManager packageManager, @NonNull FontRequest request, @Nullable Resources resources) throws NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (!info.packageName.equals(request.getProviderPackage())) {
            throw new NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        } else {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, 64).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>((Collection) requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals((byte[]) signatures.get(i), (byte[]) requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList<>();
        for (Signature byteArray : signatures) {
            shas.add(byteArray.toByteArray());
        }
        return shas;
    }

    @VisibleForTesting
    @NonNull
    static FontInfo[] getFontFromProvider(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) {
        Uri fileUri;
        ArrayList<FontInfo> result = new ArrayList<>();
        Uri uri = new Builder().scheme("content").authority(authority).build();
        Uri fileBaseUri = new Builder().scheme("content").authority(authority).appendPath("file").build();
        Cursor cursor = null;
        try {
            if (VERSION.SDK_INT > 16) {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, null, cancellationSignal);
            } else {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, null);
            }
            if (cursor != null && cursor.getCount() > 0) {
                int resultCodeColumnIndex = cursor.getColumnIndex(Columns.RESULT_CODE);
                ArrayList<FontInfo> result2 = new ArrayList<>();
                try {
                    int idColumnIndex = cursor.getColumnIndex("_id");
                    int fileIdColumnIndex = cursor.getColumnIndex(Columns.FILE_ID);
                    int ttcIndexColumnIndex = cursor.getColumnIndex(Columns.TTC_INDEX);
                    int weightColumnIndex = cursor.getColumnIndex(Columns.WEIGHT);
                    int italicColumnIndex = cursor.getColumnIndex(Columns.ITALIC);
                    while (cursor.moveToNext()) {
                        int resultCode = resultCodeColumnIndex != -1 ? cursor.getInt(resultCodeColumnIndex) : 0;
                        int ttcIndex = ttcIndexColumnIndex != -1 ? cursor.getInt(ttcIndexColumnIndex) : 0;
                        if (fileIdColumnIndex == -1) {
                            fileUri = ContentUris.withAppendedId(uri, cursor.getLong(idColumnIndex));
                        } else {
                            fileUri = ContentUris.withAppendedId(fileBaseUri, cursor.getLong(fileIdColumnIndex));
                        }
                        result2.add(new FontInfo(fileUri, ttcIndex, weightColumnIndex != -1 ? cursor.getInt(weightColumnIndex) : 400, italicColumnIndex != -1 && cursor.getInt(italicColumnIndex) == 1, resultCode));
                    }
                    result = result2;
                } catch (Throwable th) {
                    th = th;
                    ArrayList arrayList = result2;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return (FontInfo[]) result.toArray(new FontInfo[0]);
        } catch (Throwable th2) {
            th = th2;
        }
        if (cursor != null) {
            cursor.close();
        }
        throw th;
    }
}
