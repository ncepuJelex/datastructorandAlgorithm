package com.jel.tech.learn.ch10;

import java.util.Comparator;

import com.jel.tech.learn.ch09.DefaultComparator;
import com.jel.tech.learn.ch09.Entry;

/**
 * 排序map抽象类，提供Comparator比较器
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月28日
 */
public abstract class AbstractSortedMap<K, V> extends AbstractMap<K, V> implements SortedMap<K, V> {

    private Comparator<K> comp;

    protected AbstractSortedMap(Comparator<K> c) {
        comp = c;
    }

    protected AbstractSortedMap() {
        this(new DefaultComparator<>());
    }

    protected int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    protected int compare(K a, Entry<K, V> b) {
        return comp.compare(a, b.getKey());
    }

    protected int compare(Entry<K, V> a, K b) {
        return comp.compare(a.getKey(), b);
    }

    protected int compare(K a, K b) {
        return comp.compare(a, b);
    }

    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return comp.compare(key, key) == 0;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("incompatible key: " + key);
        }
    }
}
