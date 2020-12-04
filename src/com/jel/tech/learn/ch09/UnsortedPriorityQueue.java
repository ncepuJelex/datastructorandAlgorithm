package com.jel.tech.learn.ch09;

import java.util.Comparator;

import com.jel.tech.learn.ch07.LinkedPositionList;
import com.jel.tech.learn.ch07.Position;
import com.jel.tech.learn.ch07.PositionList;

/**
 * 无序的一个实现，效率看图 unsorted-priorityqueue.jpg
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月10日
 */
public class UnsortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    /*
     * 内部使用PositionList<>作为存储数据结构
     */
    private PositionList<Entry<K, V>> list = new LinkedPositionList<>();

    public UnsortedPriorityQueue() {
        super();
    }

    public UnsortedPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Entry<K, V> insert(K k, V v) throws IllegalArgumentException {
        /*
         * 可能会抛出异常！
         */
        checkKey(k);
        Entry<K, V> pqEntry = new PQEntry<K, V>(k, v);
        list.addLast(pqEntry);
        return pqEntry;
    }

    @Override
    public Entry<K, V> min() {
        if (isEmpty()) {
            return null;
        }
        return findMin().getElement();
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) {
            return null;
        }
        return list.remove(findMin());
    }

    /*
     * 内部辅助方法，用来查找最小entry的position
     */
    private Position<Entry<K, V>> findMin() {
        Position<Entry<K, V>> small = list.first();
        for (Position<Entry<K, V>> p : list.positions()) {
            if (compare(p.getElement(), small.getElement()) < 0) {
                small = p;
            }
        }
        return small;
    }

}
