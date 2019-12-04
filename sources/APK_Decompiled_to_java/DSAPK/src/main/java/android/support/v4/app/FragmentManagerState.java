package androidx.core.app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.BackStackState;
import androidx.fragment.app.FragmentState;

/* compiled from: FragmentManager */
final class FragmentManagerState implements Parcelable {
    public static final Creator<androidx.fragment.app.FragmentManagerState> CREATOR = new Creator<androidx.fragment.app.FragmentManagerState>() {
        public androidx.fragment.app.FragmentManagerState createFromParcel(Parcel in) {
            return new androidx.fragment.app.FragmentManagerState(in);
        }

        public androidx.fragment.app.FragmentManagerState[] newArray(int size) {
            return new androidx.fragment.app.FragmentManagerState[size];
        }
    };
    FragmentState[] mActive;
    int[] mAdded;
    BackStackState[] mBackStack;
    int mNextFragmentIndex;
    int mPrimaryNavActiveIndex = -1;

    public FragmentManagerState() {
    }

    public FragmentManagerState(Parcel in) {
        this.mActive = (FragmentState[]) in.createTypedArray(FragmentState.CREATOR);
        this.mAdded = in.createIntArray();
        this.mBackStack = (BackStackState[]) in.createTypedArray(BackStackState.CREATOR);
        this.mPrimaryNavActiveIndex = in.readInt();
        this.mNextFragmentIndex = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.mActive, flags);
        dest.writeIntArray(this.mAdded);
        dest.writeTypedArray(this.mBackStack, flags);
        dest.writeInt(this.mPrimaryNavActiveIndex);
        dest.writeInt(this.mNextFragmentIndex);
    }
}
