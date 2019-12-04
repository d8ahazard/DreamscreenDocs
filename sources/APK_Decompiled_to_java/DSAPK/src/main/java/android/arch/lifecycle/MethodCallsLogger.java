package androidx.lifecycle;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MethodCallsLogger {
    private Map<String, Integer> mCalledMethods = new HashMap();

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean approveCall(String name, int type) {
        int mask;
        boolean wasCalled;
        Integer nullableMask = (Integer) this.mCalledMethods.get(name);
        if (nullableMask != null) {
            mask = nullableMask.intValue();
        } else {
            mask = 0;
        }
        if ((mask & type) != 0) {
            wasCalled = true;
        } else {
            wasCalled = false;
        }
        this.mCalledMethods.put(name, Integer.valueOf(mask | type));
        if (!wasCalled) {
            return true;
        }
        return false;
    }
}
