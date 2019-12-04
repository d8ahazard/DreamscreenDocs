package androidx.appcompat.view.menu;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import android.widget.ListView;

@RestrictTo({Scope.LIBRARY_GROUP})
public interface ShowableListMenu {
    void dismiss();

    ListView getListView();

    boolean isShowing();

    void show();
}
