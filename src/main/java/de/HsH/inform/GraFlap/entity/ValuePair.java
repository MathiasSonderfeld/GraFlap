package de.HsH.inform.GraFlap.entity;

/**
 * Helper entity to create a key-value object.
 * @author Benjamin Held (04-24-2016)
 * @since 04-24-2016
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class ValuePair<K,V> {
    private final K key;
    private final V value;

    public ValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
