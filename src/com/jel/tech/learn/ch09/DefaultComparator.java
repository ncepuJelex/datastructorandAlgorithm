package com.jel.tech.learn.ch09;

import java.util.Comparator;

/**
 * 默认的Priority Queue 中的 Key比较器，如果构造方法中没有传递比较器的话，
 * 它根据自然顺序排序（前提是：Key是Comparable类型）
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月9日
 */
public class DefaultComparator<E> implements Comparator<E> {

    @SuppressWarnings("unchecked")
    @Override
    public int compare(E o1, E o2) throws ClassCastException {
        return ((Comparable<E>) o1).compareTo(o2);
    }

}
