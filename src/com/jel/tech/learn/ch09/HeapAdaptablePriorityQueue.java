package com.jel.tech.learn.ch09;

import java.util.Comparator;

/**
 * 增加可移除任意(位置)元素，更新key和value功能
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月15日
 */
public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {

    protected static class AdaptablePQEntry<K, V> extends PQEntry<K, V> {
        /*
         * 增加一个位置 index 属性，用来帮助删除任意元素操作
         */
        private int index;

        public AdaptablePQEntry(K k, V v, int j) {
            super(k, v);
            index = j;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

    }

    public HeapAdaptablePriorityQueue() {
        super();
    }

    public HeapAdaptablePriorityQueue(Comparator<K> c) {
        super(c);
    }

    /*
     * 校验entry是否合法，删除时候需要调用
     */
    protected AdaptablePQEntry<K, V> validate(Entry<K, V> entry)
            throws IllegalArgumentException {
        if (!(entry instanceof AdaptablePriorityQueue)) {
            throw new IllegalArgumentException("invalid entry->" + entry);
        }
        AdaptablePQEntry<K, V> e = (AdaptablePQEntry<K, V>) entry; //safe cast
        int j = e.getIndex();
        if (j >= heap.size() || heap.get(j) != e) {
            throw new IllegalArgumentException("invalid entry->>" + entry);
        }
        return e;
    }

    /*
     * 重写父类的swap方法，因为还需要处理PQEntry中的index属性字段
     */
    @Override
    protected void swap(int i, int j) {
        super.swap(i, j);
        ((AdaptablePQEntry<K, V>) heap.get(i)).setIndex(i); //由之前的j变成现在的i
        ((AdaptablePQEntry<K, V>) heap.get(j)).setIndex(j);
    }

    /*
     * 冒泡工具方法，当修改key和删除元素时，需要让元素相应地向上或者向下移动
     * 来保存heap的有序数据结构
     */
    protected void bubble(int j) {
        //j>0是可以上移的一个前提，另一个是：当前位置的元素比它的父亲要小（heap设定为递增顺序）
        if (j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0) {
            upheap(j);
        } else {
            downheap(j); //尽管可能无需移动
        }
    }

    /*
     *
     */
    @Override
    public Entry<K, V> insert(K k, V v) throws IllegalArgumentException {
        //校验key,可能会抛出异常
        checkKey(k);
        AdaptablePQEntry<K, V> newEntry = new AdaptablePQEntry<K, V>(k, v, heap.size());
        //添加到heap中最后位置
        heap.add(newEntry);
        //上移调整
        upheap(heap.size() - 1);
        return newEntry;
    }

    @Override
    public void remove(Entry<K, V> entry) throws IllegalArgumentException {
        //校验要删除的entry,可能抛出异常
        AdaptablePQEntry<K, V> locator = validate(entry);
        int j = locator.getIndex();
        //如果恰好是最后一个元素，那移除就方便多了！不用担心破坏了heap结构，不用再调整
        if (j == heap.size() - 1) {
            heap.remove(j);
        } else {
            /*
             * 1.交换此位置元素和最后一个元素
             * 2.删除最后一个元素（相当于删除掉了我们想删的元素）
             * 3.对此位置元素冒泡调整
             */
            swap(j, heap.size() - 1);
            heap.remove(heap.size() - 1);
            bubble(j);
        }
    }

    @Override
    public void replaceKey(Entry<K, V> entry, K k) throws IllegalArgumentException {
        //校验entry
        AdaptablePQEntry<K, V> locator = validate(entry);
        //校验key
        checkKey(k);
        //更新k的值
        locator.setK(k);
        //冒泡调整
        bubble(locator.getIndex());
    }

    @Override
    public void replaceValue(Entry<K, V> entry, V v) throws IllegalArgumentException {
        //校验entry
        AdaptablePQEntry<K, V> locator = validate(entry);
        //不用校验value,直接更新
        locator.setV(v);
        //最后也不用冒泡调整，因为是根据key来排序的，和value无关
    }

}
