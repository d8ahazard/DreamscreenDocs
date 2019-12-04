package androidx.core.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;

import androidx.documentfile.provider.DocumentsContractApi19;
import androidx.documentfile.provider.RawDocumentFile;
import androidx.documentfile.provider.SingleDocumentFile;
import androidx.documentfile.provider.TreeDocumentFile;

import java.io.File;

public abstract class DocumentFile {
    static final String TAG = "DocumentFile";
    private final androidx.documentfile.provider.DocumentFile mParent;

    public abstract boolean canRead();

    public abstract boolean canWrite();

    public abstract androidx.documentfile.provider.DocumentFile createDirectory(String str);

    public abstract androidx.documentfile.provider.DocumentFile createFile(String str, String str2);

    public abstract boolean delete();

    public abstract boolean exists();

    public abstract String getName();

    public abstract String getType();

    public abstract Uri getUri();

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract boolean isVirtual();

    public abstract long lastModified();

    public abstract long length();

    public abstract androidx.documentfile.provider.DocumentFile[] listFiles();

    public abstract boolean renameTo(String str);

    DocumentFile(androidx.documentfile.provider.DocumentFile parent) {
        this.mParent = parent;
    }

    public static androidx.documentfile.provider.DocumentFile fromFile(File file) {
        return new RawDocumentFile(null, file);
    }

    public static androidx.documentfile.provider.DocumentFile fromSingleUri(Context context, Uri singleUri) {
        if (VERSION.SDK_INT >= 19) {
            return new SingleDocumentFile(null, context, singleUri);
        }
        return null;
    }

    public static androidx.documentfile.provider.DocumentFile fromTreeUri(Context context, Uri treeUri) {
        if (VERSION.SDK_INT >= 21) {
            return new TreeDocumentFile(null, context, DocumentsContract.buildDocumentUriUsingTree(treeUri, DocumentsContract.getTreeDocumentId(treeUri)));
        }
        return null;
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        if (VERSION.SDK_INT >= 19) {
            return DocumentsContractApi19.isDocumentUri(context, uri);
        }
        return false;
    }

    public androidx.documentfile.provider.DocumentFile getParentFile() {
        return this.mParent;
    }

    public androidx.documentfile.provider.DocumentFile findFile(String displayName) {
        androidx.documentfile.provider.DocumentFile[] listFiles;
        for (androidx.documentfile.provider.DocumentFile doc : listFiles()) {
            if (displayName.equals(doc.getName())) {
                return doc;
            }
        }
        return null;
    }
}
