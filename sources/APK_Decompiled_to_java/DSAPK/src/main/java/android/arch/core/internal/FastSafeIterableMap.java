package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.util.HashMap;
import java.util.Map.Entry;

@RestrictTo({Scope.LIBRARY_GROUP})
public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
    private HashMap<K, Entry<K, V>> mHashMap = new HashMap<>();

    /* access modifiers changed from: protected */
    public Entry<K, V> get(K k) {
        return (Entry) this.mHashMap.get(k);
    }

    public V putIfAbsent(@NonNull K key, @NonNull V v) {
        Entry<K, V> current = get(key);
        if (current != null) {
            return current.mValue;
        }
        this.mHashMap.put(key, put(key, v));
        return null;
    }

    public V remove(@NonNull K key) {
        V removed = super.remove(key);
        this.mHashMap.remove(key);
        return removed;
    }

    public boolean contains(K key) {
        return this.mHashMap.containsKey(key);
    }

    public Entry<K, V> ceil(K k) {
        if (contains(k)) {
            return ((Entry) this.mHashMap.get(k)).mPrevious;
        }
        return null;
    }
}
