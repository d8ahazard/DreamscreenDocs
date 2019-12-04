package androidx.core.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader.ForceLoadContentObserver;
import androidx.core.os.CancellationSignal;
import androidx.core.os.OperationCanceledException;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader extends AsyncTaskLoader<Cursor> {
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    final ForceLoadContentObserver mObserver = new ForceLoadContentObserver<>();
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public Cursor loadInBackground() {
        Cursor cursor;
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            this.mCancellationSignal = new CancellationSignal();
        }
        try {
            cursor = ContentResolverCompat.query(getContext().getContentResolver(), this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            if (cursor != null) {
                cursor.getCount();
                cursor.registerContentObserver(this.mObserver);
            }
            synchronized (this) {
                this.mCancellationSignal = null;
            }
            return cursor;
        } catch (RuntimeException ex) {
            cursor.close();
            throw ex;
        } catch (Throwable th) {
            synchronized (this) {
                this.mCancellationSignal = null;
                throw th;
            }
        }
    }

    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
        }
    }

    public void deliverResult(Cursor cursor) {
        if (!isReset()) {
            Cursor oldCursor = this.mCursor;
            this.mCursor = cursor;
            if (isStarted()) {
                super.deliverResult(cursor);
            }
            if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
                oldCursor.close();
            }
        } else if (cursor != null) {
            cursor.close();
        }
    }

    public CursorLoader(@NonNull Context context) {
        super(context);
    }

    public CursorLoader(@NonNull Context context, @NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        super(context);
        this.mUri = uri;
        this.mProjection = projection;
        this.mSelection = selection;
        this.mSelectionArgs = selectionArgs;
        this.mSortOrder = sortOrder;
    }

    /* access modifiers changed from: protected */
    public void onStartLoading() {
        if (this.mCursor != null) {
            deliverResult(this.mCursor);
        }
        if (takeContentChanged() || this.mCursor == null) {
            forceLoad();
        }
    }

    /* access modifiers changed from: protected */
    public void onStopLoading() {
        cancelLoad();
    }

    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        super.onReset();
        onStopLoading();
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    @NonNull
    public Uri getUri() {
        return this.mUri;
    }

    public void setUri(@NonNull Uri uri) {
        this.mUri = uri;
    }

    @Nullable
    public String[] getProjection() {
        return this.mProjection;
    }

    public void setProjection(@Nullable String[] projection) {
        this.mProjection = projection;
    }

    @Nullable
    public String getSelection() {
        return this.mSelection;
    }

    public void setSelection(@Nullable String selection) {
        this.mSelection = selection;
    }

    @Nullable
    public String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    public void setSelectionArgs(@Nullable String[] selectionArgs) {
        this.mSelectionArgs = selectionArgs;
    }

    @Nullable
    public String getSortOrder() {
        return this.mSortOrder;
    }

    public void setSortOrder(@Nullable String sortOrder) {
        this.mSortOrder = sortOrder;
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mUri=");
        writer.println(this.mUri);
        writer.print(prefix);
        writer.print("mProjection=");
        writer.println(Arrays.toString(this.mProjection));
        writer.print(prefix);
        writer.print("mSelection=");
        writer.println(this.mSelection);
        writer.print(prefix);
        writer.print("mSelectionArgs=");
        writer.println(Arrays.toString(this.mSelectionArgs));
        writer.print(prefix);
        writer.print("mSortOrder=");
        writer.println(this.mSortOrder);
        writer.print(prefix);
        writer.print("mCursor=");
        writer.println(this.mCursor);
        writer.print(prefix);
        writer.print("mContentChanged=");
        writer.println(this.mContentChanged);
    }
}
