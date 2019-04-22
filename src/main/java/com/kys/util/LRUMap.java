package com.kys.util;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class LRUMap<K, V> extends LinkedHashMap<K, V> {

	private static final int DEFAULT_MAX_ENTRY = 50;
	/**
	 * 최대 캐싱 수
	 */
	private int maxEntry = 0;
	
	public LRUMap(int maxEntry){
		this.maxEntry = maxEntry;
	}
	
	public LRUMap() {
		this.maxEntry = DEFAULT_MAX_ENTRY;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest){
		return size() > maxEntry;
	}
}
