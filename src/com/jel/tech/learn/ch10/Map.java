package com.jel.tech.learn.ch10;

import com.jel.tech.learn.ch09.Entry;

/**
 * 学习map新篇章，Map接口
 * @author jelex.xu
 * @date 2017年10月15日
 * @param <K>
 * @param <V>
 */
public interface Map<K, V> {

	int size();
	boolean isEmpty();
	V get(K key);
	V put(K key, V value);
	V remove(K key);
	Iterable<K> keySet();
	Iterable<V> values();
	Iterable<Entry<K, V>> entrySet();
}
