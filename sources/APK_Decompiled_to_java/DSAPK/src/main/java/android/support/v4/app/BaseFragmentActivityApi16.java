package androidx.core.app;

import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.fragment.app.BaseFragmentActivityApi14;

@RestrictTo({Scope.LIBRARY_GROUP})
abstract class BaseFragmentActivityApi16 extends BaseFragmentActivityApi14 {
    boolean mStartedActivityFromFragment;

    BaseFragmentActivityApi16() {
    }

    @RequiresApi(16)
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (!this.mStartedActivityFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startActivityForResult(intent, requestCode, options);
    }

    @RequiresApi(16)
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }
}
