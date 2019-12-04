package androidx.core.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.fragment.app.FragmentManagerNonConfig;

final class FragmentState implements Parcelable {
    public static final Creator<androidx.fragment.app.FragmentState> CREATOR = new Creator<androidx.fragment.app.FragmentState>() {
        public androidx.fragment.app.FragmentState createFromParcel(Parcel in) {
            return new androidx.fragment.app.FragmentState(in);
        }

        public androidx.fragment.app.FragmentState[] newArray(int size) {
            return new androidx.fragment.app.FragmentState[size];
        }
    };
    final Bundle mArguments;
    final String mClassName;
    final int mContainerId;
    final boolean mDetached;
    final int mFragmentId;
    final boolean mFromLayout;
    final boolean mHidden;
    final int mIndex;
    Fragment mInstance;
    final boolean mRetainInstance;
    Bundle mSavedFragmentState;
    final String mTag;

    FragmentState(Fragment frag) {
        this.mClassName = frag.getClass().getName();
        this.mIndex = frag.mIndex;
        this.mFromLayout = frag.mFromLayout;
        this.mFragmentId = frag.mFragmentId;
        this.mContainerId = frag.mContainerId;
        this.mTag = frag.mTag;
        this.mRetainInstance = frag.mRetainInstance;
        this.mDetached = frag.mDetached;
        this.mArguments = frag.mArguments;
        this.mHidden = frag.mHidden;
    }

    FragmentState(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.mClassName = in.readString();
        this.mIndex = in.readInt();
        this.mFromLayout = in.readInt() != 0;
        this.mFragmentId = in.readInt();
        this.mContainerId = in.readInt();
        this.mTag = in.readString();
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mRetainInstance = z;
        if (in.readInt() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mDetached = z2;
        this.mArguments = in.readBundle();
        if (in.readInt() == 0) {
            z3 = false;
        }
        this.mHidden = z3;
        this.mSavedFragmentState = in.readBundle();
    }

    public Fragment instantiate(FragmentHostCallback host, FragmentContainer container, Fragment parent, FragmentManagerNonConfig childNonConfig) {
        if (this.mInstance == null) {
            Context context = host.getContext();
            if (this.mArguments != null) {
                this.mArguments.setClassLoader(context.getClassLoader());
            }
            if (container != null) {
                this.mInstance = container.instantiate(context, this.mClassName, this.mArguments);
            } else {
                this.mInstance = Fragment.instantiate(context, this.mClassName, this.mArguments);
            }
            if (this.mSavedFragmentState != null) {
                this.mSavedFragmentState.setClassLoader(context.getClassLoader());
                this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
            }
            this.mInstance.setIndex(this.mIndex, parent);
            this.mInstance.mFromLayout = this.mFromLayout;
            this.mInstance.mRestored = true;
            this.mInstance.mFragmentId = this.mFragmentId;
            this.mInstance.mContainerId = this.mContainerId;
            this.mInstance.mTag = this.mTag;
            this.mInstance.mRetainInstance = this.mRetainInstance;
            this.mInstance.mDetached = this.mDetached;
            this.mInstance.mHidden = this.mHidden;
            this.mInstance.mFragmentManager = host.mFragmentManager;
            if (FragmentManagerImpl.DEBUG) {
                Log.v("FragmentManager", "Instantiated fragment " + this.mInstance);
            }
        }
        this.mInstance.mChildNonConfig = childNonConfig;
        return this.mInstance;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2;
        int i3 = 1;
        dest.writeString(this.mClassName);
        dest.writeInt(this.mIndex);
        dest.writeInt(this.mFromLayout ? 1 : 0);
        dest.writeInt(this.mFragmentId);
        dest.writeInt(this.mContainerId);
        dest.writeString(this.mTag);
        if (this.mRetainInstance) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.mDetached) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        dest.writeInt(i2);
        dest.writeBundle(this.mArguments);
        if (!this.mHidden) {
            i3 = 0;
        }
        dest.writeInt(i3);
        dest.writeBundle(this.mSavedFragmentState);
    }
}
