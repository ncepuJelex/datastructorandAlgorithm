package com.jel.tech.learn.ch09;

public interface PriorityQueue<K, V> {

    int size();

    boolean isEmpty();

    Entry<K, V> insert(K k, V v) throws IllegalArgumentException;

    /**
     * 不会删除
     *
     * @return
     */
    Entry<K, V> min();

    Entry<K, V> removeMin();
}
