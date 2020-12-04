package com.jel.tech.learn.ch09;

/**
 * 增加了 删除任意元素，更新key和value功能接口
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月15日
 */
public interface AdaptablePriorityQueue<K, V> extends PriorityQueue<K, V> {

    void remove(Entry<K, V> entry) throws IllegalArgumentException;

    void replaceKey(Entry<K, V> entry, K k) throws IllegalArgumentException;

    void replaceValue(Entry<K, V> entry, V v) throws IllegalArgumentException;
}
