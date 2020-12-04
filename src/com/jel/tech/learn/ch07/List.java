package com.jel.tech.learn.ch07;

/**
 * chapter07,从你开始！
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月5日
 */
public interface List<E> extends Iterable<E> {

    int size();

    boolean isEmpty();

    E get(int i) throws IndexOutOfBoundsException;

    E set(int i, E e) throws IndexOutOfBoundsException;

    void add(int i, E e) throws IndexOutOfBoundsException;

    //在最后添加该元素
    void add(E e);

    E remove(int i) throws IndexOutOfBoundsException;
}
