package androidx.appcompat.widget;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
public interface WithHint {
    @Nullable
    CharSequence getHint();
}
