package androidx.core.graphics;

import android.os.ParcelFileDescriptor;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;

@RequiresApi(21)
@RestrictTo({Scope.LIBRARY_GROUP})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            if (OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return new File(path);
            }
            return null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0051, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0052, code lost:
        r10 = r7;
        r7 = r6;
        r6 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0061, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0062, code lost:
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x006a, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x006b, code lost:
        if (r3 != null) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x006d, code lost:
        if (r7 != null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0073, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0074, code lost:
        r7.addSuppressed(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0078, code lost:
        r3.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0061 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:7:0x0019] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r12, android.os.CancellationSignal r13, @androidx.annotation.NonNull androidx.core.provider.FontsContractCompat.FontInfo[] r14, int r15) {
        /*
            r11 = this;
            int r6 = r14.length
            r7 = 1
            if (r6 >= r7) goto L_0x0006
            r6 = 0
        L_0x0005:
            return r6
        L_0x0006:
            android.support.v4.provider.FontsContractCompat$FontInfo r0 = r11.findBestInfo(r14, r15)
            android.content.ContentResolver r5 = r12.getContentResolver()
            android.net.Uri r6 = r0.getUri()     // Catch:{ IOException -> 0x0047 }
            java.lang.String r7 = "r"
            android.os.ParcelFileDescriptor r4 = r5.openFileDescriptor(r6, r7, r13)     // Catch:{ IOException -> 0x0047 }
            r8 = 0
            java.io.File r2 = r11.getFile(r4)     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            if (r2 == 0) goto L_0x0025
            boolean r6 = r2.canRead()     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            if (r6 != 0) goto L_0x007c
        L_0x0025:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            java.io.FileDescriptor r6 = r4.getFileDescriptor()     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            r7 = 0
            android.graphics.Typeface r6 = super.createFromInputStream(r12, r3)     // Catch:{ Throwable -> 0x0068 }
            if (r3 == 0) goto L_0x003a
            if (r7 == 0) goto L_0x005d
            r3.close()     // Catch:{ Throwable -> 0x004a, all -> 0x0061 }
        L_0x003a:
            if (r4 == 0) goto L_0x0005
            if (r8 == 0) goto L_0x0064
            r4.close()     // Catch:{ Throwable -> 0x0042 }
            goto L_0x0005
        L_0x0042:
            r7 = move-exception
            r8.addSuppressed(r7)     // Catch:{ IOException -> 0x0047 }
            goto L_0x0005
        L_0x0047:
            r1 = move-exception
            r6 = 0
            goto L_0x0005
        L_0x004a:
            r9 = move-exception
            r7.addSuppressed(r9)     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            goto L_0x003a
        L_0x004f:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0051 }
        L_0x0051:
            r7 = move-exception
            r10 = r7
            r7 = r6
            r6 = r10
        L_0x0055:
            if (r4 == 0) goto L_0x005c
            if (r7 == 0) goto L_0x0099
            r4.close()     // Catch:{ Throwable -> 0x0094 }
        L_0x005c:
            throw r6     // Catch:{ IOException -> 0x0047 }
        L_0x005d:
            r3.close()     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            goto L_0x003a
        L_0x0061:
            r6 = move-exception
            r7 = r8
            goto L_0x0055
        L_0x0064:
            r4.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x0005
        L_0x0068:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x006a }
        L_0x006a:
            r6 = move-exception
            if (r3 == 0) goto L_0x0072
            if (r7 == 0) goto L_0x0078
            r3.close()     // Catch:{ Throwable -> 0x0073, all -> 0x0061 }
        L_0x0072:
            throw r6     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
        L_0x0073:
            r9 = move-exception
            r7.addSuppressed(r9)     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            goto L_0x0072
        L_0x0078:
            r3.close()     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            goto L_0x0072
        L_0x007c:
            android.graphics.Typeface r6 = android.graphics.Typeface.createFromFile(r2)     // Catch:{ Throwable -> 0x004f, all -> 0x0061 }
            if (r4 == 0) goto L_0x0005
            if (r8 == 0) goto L_0x008f
            r4.close()     // Catch:{ Throwable -> 0x0089 }
            goto L_0x0005
        L_0x0089:
            r7 = move-exception
            r8.addSuppressed(r7)     // Catch:{ IOException -> 0x0047 }
            goto L_0x0005
        L_0x008f:
            r4.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x0005
        L_0x0094:
            r8 = move-exception
            r7.addSuppressed(r8)     // Catch:{ IOException -> 0x0047 }
            goto L_0x005c
        L_0x0099:
            r4.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }
}
