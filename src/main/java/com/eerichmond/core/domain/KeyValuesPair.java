package com.eerichmond.core.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * A key/values pair similar to Map.Entry except the values is a collection of items.
 * @param <K>
 * @param <V>
 */
public final class KeyValuesPair<K, V> extends BaseObject<BusinessKey> {
	
	private static final long serialVersionUID = 1L;
	private final K key;
	private final Collection<V> values;
	
	private KeyValuesPair(K key, Collection<V> values) {
		super();
		
		this.key = key;
		this.values = values;
	}
	
	@BusinessKey
	@JsonProperty
	public K getKey() { return key; }
	
	@BusinessKey
	@JsonProperty
	public Collection<V> getValues() { return values; }
	
	public static <K, V> KeyValuesPair<K, V> create(K key, Collection<V> values) {
		return new KeyValuesPair<K, V>(key, values);
	}
}
