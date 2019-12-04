package com.amazonaws.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class ImmutableMapParameter<K, V> implements Map<K, V> {
    private static final String DUPLICATED_KEY_MESSAGE = "Duplicate keys are provided.";
    private static final String UNMODIFIABLE_MESSAGE = "This is an immutable map.";
    private final Map<K, V> map;

    public static class Builder<K, V> {
        private final Map<K, V> entries = new HashMap();

        public Builder<K, V> put(K key, V value) {
            ImmutableMapParameter.putAndWarnDuplicateKeys(this.entries, key, value);
            return this;
        }

        public ImmutableMapParameter<K, V> build() {
            HashMap<K, V> builtMap = new HashMap<>();
            builtMap.putAll(this.entries);
            return new ImmutableMapParameter<>(builtMap);
        }
    }

    private ImmutableMapParameter(Map<K, V> map2) {
        this.map = map2;
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> ImmutableMapParameter<K, V> of(K k0, V v0) {
        return new ImmutableMapParameter<>(Collections.singletonMap(k0, v0));
    }

    public static <K, V> ImmutableMapParameter<K, V> of(K k0, V v0, K k1, V v1) {
        Map<K, V> map2 = new HashMap<>();
        putAndWarnDuplicateKeys(map2, k0, v0);
        putAndWarnDuplicateKeys(map2, k1, v1);
        return new ImmutableMapParameter<>(map2);
    }

    public static <K, V> ImmutableMapParameter<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2) {
        Map<K, V> map2 = new HashMap<>();
        putAndWarnDuplicateKeys(map2, k0, v0);
        putAndWarnDuplicateKeys(map2, k1, v1);
        putAndWarnDuplicateKeys(map2, k2, v2);
        return new ImmutableMapParameter<>(map2);
    }

    public static <K, V> ImmutableMapParameter<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map2 = new HashMap<>();
        putAndWarnDuplicateKeys(map2, k0, v0);
        putAndWarnDuplicateKeys(map2, k1, v1);
        putAndWarnDuplicateKeys(map2, k2, v2);
        putAndWarnDuplicateKeys(map2, k3, v3);
        return new ImmutableMapParameter<>(map2);
    }

    public static <K, V> ImmutableMapParameter<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map2 = new HashMap<>();
        putAndWarnDuplicateKeys(map2, k0, v0);
        putAndWarnDuplicateKeys(map2, k1, v1);
        putAndWarnDuplicateKeys(map2, k2, v2);
        putAndWarnDuplicateKeys(map2, k3, v3);
        putAndWarnDuplicateKeys(map2, k4, v4);
        return new ImmutableMapParameter<>(map2);
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    public V get(Object key) {
        return this.map.get(key);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public int size() {
        return this.map.size();
    }

    public Collection<V> values() {
        return this.map.values();
    }

    public void clear() {
        throw new UnsupportedOperationException(UNMODIFIABLE_MESSAGE);
    }

    public V put(K k, V v) {
        throw new UnsupportedOperationException(UNMODIFIABLE_MESSAGE);
    }

    public void putAll(Map<? extends K, ? extends V> map2) {
        throw new UnsupportedOperationException(UNMODIFIABLE_MESSAGE);
    }

    public V remove(Object key) {
        throw new UnsupportedOperationException(UNMODIFIABLE_MESSAGE);
    }

    /* access modifiers changed from: private */
    public static <K, V> void putAndWarnDuplicateKeys(Map<K, V> map2, K key, V value) {
        if (map2.containsKey(key)) {
            throw new IllegalArgumentException(DUPLICATED_KEY_MESSAGE);
        }
        map2.put(key, value);
    }
}
