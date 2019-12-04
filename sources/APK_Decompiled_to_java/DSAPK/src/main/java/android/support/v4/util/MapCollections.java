package androidx.core.util;

import androidx.collection.ContainerHelpers;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

abstract class MapCollections<K, V> {
    EntrySet mEntrySet;
    KeySet mKeySet;
    ValuesCollection mValues;

    final class ArrayIterator<T> implements Iterator<T> {
        boolean mCanRemove = false;
        int mIndex;
        final int mOffset;
        int mSize;

        ArrayIterator(int offset) {
            this.mOffset = offset;
            this.mSize = androidx.collection.MapCollections.this.colGetSize();
        }

        public boolean hasNext() {
            return this.mIndex < this.mSize;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Object res = androidx.collection.MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
            this.mIndex++;
            this.mCanRemove = true;
            return res;
        }

        public void remove() {
            if (!this.mCanRemove) {
                throw new IllegalStateException();
            }
            this.mIndex--;
            this.mSize--;
            this.mCanRemove = false;
            androidx.collection.MapCollections.this.colRemoveAt(this.mIndex);
        }
    }

    final class EntrySet implements Set<Entry<K, V>> {
        EntrySet() {
        }

        public boolean add(Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends Entry<K, V>> collection) {
            int oldSize = androidx.collection.MapCollections.this.colGetSize();
            for (Entry<K, V> entry : collection) {
                androidx.collection.MapCollections.this.colPut(entry.getKey(), entry.getValue());
            }
            return oldSize != androidx.collection.MapCollections.this.colGetSize();
        }

        public void clear() {
            androidx.collection.MapCollections.this.colClear();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<?, ?> e = (Entry) o;
            int index = androidx.collection.MapCollections.this.colIndexOfKey(e.getKey());
            if (index >= 0) {
                return ContainerHelpers.equal(androidx.collection.MapCollections.this.colGetEntry(index, 1), e.getValue());
            }
            return false;
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return androidx.collection.MapCollections.this.colGetSize() == 0;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new MapIterator();
        }

        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return androidx.collection.MapCollections.this.colGetSize();
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object object) {
            return androidx.collection.MapCollections.equalsSetHelper(this, object);
        }

        public int hashCode() {
            int hashCode;
            int result = 0;
            for (int i = androidx.collection.MapCollections.this.colGetSize() - 1; i >= 0; i--) {
                Object key = androidx.collection.MapCollections.this.colGetEntry(i, 0);
                Object value = androidx.collection.MapCollections.this.colGetEntry(i, 1);
                int hashCode2 = key == null ? 0 : key.hashCode();
                if (value == null) {
                    hashCode = 0;
                } else {
                    hashCode = value.hashCode();
                }
                result += hashCode ^ hashCode2;
            }
            return result;
        }
    }

    final class KeySet implements Set<K> {
        KeySet() {
        }

        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            androidx.collection.MapCollections.this.colClear();
        }

        public boolean contains(Object object) {
            return androidx.collection.MapCollections.this.colIndexOfKey(object) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            return androidx.collection.MapCollections.containsAllHelper(androidx.collection.MapCollections.this.colGetMap(), collection);
        }

        public boolean isEmpty() {
            return androidx.collection.MapCollections.this.colGetSize() == 0;
        }

        public Iterator<K> iterator() {
            return new ArrayIterator(0);
        }

        public boolean remove(Object object) {
            int index = androidx.collection.MapCollections.this.colIndexOfKey(object);
            if (index < 0) {
                return false;
            }
            androidx.collection.MapCollections.this.colRemoveAt(index);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return androidx.collection.MapCollections.removeAllHelper(androidx.collection.MapCollections.this.colGetMap(), collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return androidx.collection.MapCollections.retainAllHelper(androidx.collection.MapCollections.this.colGetMap(), collection);
        }

        public int size() {
            return androidx.collection.MapCollections.this.colGetSize();
        }

        public Object[] toArray() {
            return androidx.collection.MapCollections.this.toArrayHelper(0);
        }

        public <T> T[] toArray(T[] array) {
            return androidx.collection.MapCollections.this.toArrayHelper(array, 0);
        }

        public boolean equals(Object object) {
            return androidx.collection.MapCollections.equalsSetHelper(this, object);
        }

        public int hashCode() {
            int result = 0;
            for (int i = androidx.collection.MapCollections.this.colGetSize() - 1; i >= 0; i--) {
                Object obj = androidx.collection.MapCollections.this.colGetEntry(i, 0);
                result += obj == null ? 0 : obj.hashCode();
            }
            return result;
        }
    }

    final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
        int mEnd;
        boolean mEntryValid = false;
        int mIndex;

        MapIterator() {
            this.mEnd = androidx.collection.MapCollections.this.colGetSize() - 1;
            this.mIndex = -1;
        }

        public boolean hasNext() {
            return this.mIndex < this.mEnd;
        }

        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.mIndex++;
            this.mEntryValid = true;
            return this;
        }

        public void remove() {
            if (!this.mEntryValid) {
                throw new IllegalStateException();
            }
            androidx.collection.MapCollections.this.colRemoveAt(this.mIndex);
            this.mIndex--;
            this.mEnd--;
            this.mEntryValid = false;
        }

        public K getKey() {
            if (this.mEntryValid) {
                return androidx.collection.MapCollections.this.colGetEntry(this.mIndex, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V getValue() {
            if (this.mEntryValid) {
                return androidx.collection.MapCollections.this.colGetEntry(this.mIndex, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V setValue(V object) {
            if (this.mEntryValid) {
                return androidx.collection.MapCollections.this.colSetValue(this.mIndex, object);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final boolean equals(Object o) {
            boolean z = true;
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(o instanceof Entry)) {
                return false;
            } else {
                Entry<?, ?> e = (Entry) o;
                if (!ContainerHelpers.equal(e.getKey(), androidx.collection.MapCollections.this.colGetEntry(this.mIndex, 0)) || !ContainerHelpers.equal(e.getValue(), androidx.collection.MapCollections.this.colGetEntry(this.mIndex, 1))) {
                    z = false;
                }
                return z;
            }
        }

        public final int hashCode() {
            int i = 0;
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            Object key = androidx.collection.MapCollections.this.colGetEntry(this.mIndex, 0);
            Object value = androidx.collection.MapCollections.this.colGetEntry(this.mIndex, 1);
            int hashCode = key == null ? 0 : key.hashCode();
            if (value != null) {
                i = value.hashCode();
            }
            return i ^ hashCode;
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    final class ValuesCollection implements Collection<V> {
        ValuesCollection() {
        }

        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            androidx.collection.MapCollections.this.colClear();
        }

        public boolean contains(Object object) {
            return androidx.collection.MapCollections.this.colIndexOfValue(object) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return androidx.collection.MapCollections.this.colGetSize() == 0;
        }

        public Iterator<V> iterator() {
            return new ArrayIterator(1);
        }

        public boolean remove(Object object) {
            int index = androidx.collection.MapCollections.this.colIndexOfValue(object);
            if (index < 0) {
                return false;
            }
            androidx.collection.MapCollections.this.colRemoveAt(index);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            int N = androidx.collection.MapCollections.this.colGetSize();
            boolean changed = false;
            int i = 0;
            while (i < N) {
                if (collection.contains(androidx.collection.MapCollections.this.colGetEntry(i, 1))) {
                    androidx.collection.MapCollections.this.colRemoveAt(i);
                    i--;
                    N--;
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        public boolean retainAll(Collection<?> collection) {
            int N = androidx.collection.MapCollections.this.colGetSize();
            boolean changed = false;
            int i = 0;
            while (i < N) {
                if (!collection.contains(androidx.collection.MapCollections.this.colGetEntry(i, 1))) {
                    androidx.collection.MapCollections.this.colRemoveAt(i);
                    i--;
                    N--;
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        public int size() {
            return androidx.collection.MapCollections.this.colGetSize();
        }

        public Object[] toArray() {
            return androidx.collection.MapCollections.this.toArrayHelper(1);
        }

        public <T> T[] toArray(T[] array) {
            return androidx.collection.MapCollections.this.toArrayHelper(array, 1);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void colClear();

    /* access modifiers changed from: protected */
    public abstract Object colGetEntry(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract Map<K, V> colGetMap();

    /* access modifiers changed from: protected */
    public abstract int colGetSize();

    /* access modifiers changed from: protected */
    public abstract int colIndexOfKey(Object obj);

    /* access modifiers changed from: protected */
    public abstract int colIndexOfValue(Object obj);

    /* access modifiers changed from: protected */
    public abstract void colPut(K k, V v);

    /* access modifiers changed from: protected */
    public abstract void colRemoveAt(int i);

    /* access modifiers changed from: protected */
    public abstract V colSetValue(int i, V v);

    MapCollections() {
    }

    public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
        for (Object containsKey : collection) {
            if (!map.containsKey(containsKey)) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
        int oldSize = map.size();
        for (Object remove : collection) {
            map.remove(remove);
        }
        return oldSize != map.size();
    }

    public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
        int oldSize = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return oldSize != map.size();
    }

    public Object[] toArrayHelper(int offset) {
        int N = colGetSize();
        Object[] result = new Object[N];
        for (int i = 0; i < N; i++) {
            result[i] = colGetEntry(i, offset);
        }
        return result;
    }

    public <T> T[] toArrayHelper(T[] array, int offset) {
        int N = colGetSize();
        if (array.length < N) {
            array = (Object[]) ((Object[]) Array.newInstance(array.getClass().getComponentType(), N));
        }
        for (int i = 0; i < N; i++) {
            array[i] = colGetEntry(i, offset);
        }
        if (array.length > N) {
            array[N] = null;
        }
        return array;
    }

    public static <T> boolean equalsSetHelper(Set<T> set, Object object) {
        boolean z = true;
        if (set == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set<?> s = (Set) object;
        try {
            if (set.size() != s.size() || !set.containsAll(s)) {
                z = false;
            }
            return z;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    public Set<Entry<K, V>> getEntrySet() {
        if (this.mEntrySet == null) {
            this.mEntrySet = new EntrySet<>();
        }
        return this.mEntrySet;
    }

    public Set<K> getKeySet() {
        if (this.mKeySet == null) {
            this.mKeySet = new KeySet<>();
        }
        return this.mKeySet;
    }

    public Collection<V> getValues() {
        if (this.mValues == null) {
            this.mValues = new ValuesCollection<>();
        }
        return this.mValues;
    }
}
