package androidx.core.app;

import android.os.Bundle;
import java.util.Set;

@Deprecated
class RemoteInputCompatBase {

    @Deprecated
    public static abstract class RemoteInput {

        @Deprecated
        public interface Factory {
            RemoteInput build(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set);

            RemoteInput[] newArray(int i);
        }

        /* access modifiers changed from: protected */
        @Deprecated
        public abstract boolean getAllowFreeFormInput();

        /* access modifiers changed from: protected */
        @Deprecated
        public abstract Set<String> getAllowedDataTypes();

        /* access modifiers changed from: protected */
        @Deprecated
        public abstract CharSequence[] getChoices();

        /* access modifiers changed from: protected */
        @Deprecated
        public abstract Bundle getExtras();

        /* access modifiers changed from: protected */
        @Deprecated
        public abstract CharSequence getLabel();

        /* access modifiers changed from: protected */
        @Deprecated
        public abstract String getResultKey();
    }

    RemoteInputCompatBase() {
    }
}
