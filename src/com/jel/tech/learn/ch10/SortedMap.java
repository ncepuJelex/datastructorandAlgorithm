package com.jel.tech.learn.ch10;

import com.jel.tech.learn.ch09.Entry;

/**
 * 排好序的map接口
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月28日
 */
public interface SortedMap<K, V> extends Map<K, V> {

    Entry<K, V> firstEntry();

    Entry<K, V> lastEntry();

    /**
     * 返回最小的、key大于等于k的Entry
     */
    Entry<K, V> ceilingEntry(K k) throws IllegalArgumentException;

    /**
     * 返回最大的、key小于等于k的Entry
     */
    Entry<K, V> floorEntry(K k) throws IllegalArgumentException;

    /**
     * 返回最大的、key小于k的Entry
     */
    Entry<K, V> lowerEntry(K k) throws IllegalArgumentException;

    /**
     * 返回最小的、key大于k的Entry
     */
    Entry<K, V> higherEntry(K k) throws IllegalArgumentException;

    /**
     * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
     *
     * @param from
     * @param to
     * @return
     * @throws IllegalArgumentException
     */
    Iterable<Entry<K, V>> subMap(K from, K to) throws IllegalArgumentException;
}
