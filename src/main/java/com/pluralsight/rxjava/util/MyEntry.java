package com.pluralsight.rxjava.util;

import java.util.Map.Entry;

/**
 * Represents a map entry which consists of key and value pair.
 * 
 * @author URANWRA
 *
 * @param <K>
 * @param <V>
 */
public final class MyEntry<K, V> implements Entry<K, V> {
	private final K key;
	private V value;

	public MyEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}
}
