package android.support.design.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationPresenter implements MenuPresenter {
    private int mId;
    private MenuBuilder mMenu;
    private BottomNavigationMenuView mMenuView;
    private boolean mUpdateSuspended = false;

    static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int selectedItemId;

        SavedState() {
        }

        SavedState(Parcel in) {
            this.selectedItemId = in.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeInt(this.selectedItemId);
        }
    }

    public void setBottomNavigationMenuView(BottomNavigationMenuView menuView) {
        this.mMenuView = menuView;
    }

    public void initForMenu(Context context, MenuBuilder menu) {
        this.mMenuView.initialize(this.mMenu);
        this.mMenu = menu;
    }

    public MenuView getMenuView(ViewGroup root) {
        return this.mMenuView;
    }

    public void updateMenuView(boolean cleared) {
        if (!this.mUpdateSuspended) {
            if (cleared) {
                this.mMenuView.buildMenuView();
            } else {
                this.mMenuView.updateMenuView();
            }
        }
    }

    public void setCallback(Callback cb) {
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.selectedItemId = this.mMenuView.getSelectedItemId();
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            this.mMenuView.tryRestoreSelectedItemId(((SavedState) state).selectedItemId);
        }
    }

    public void setUpdateSuspended(boolean updateSuspended) {
        this.mUpdateSuspended = updateSuspended;
    }
}
