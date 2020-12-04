package com.jel.tech.learn.ch06;

/**
 * 栈，它和java.util.Stack不一样，体现在empty时对pop和top时的处理，
 * 不会抛出异常，只是返回null.
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月4日
 */
public interface Stack<E> {

    int size();

    boolean isEmpty();

    void push(E e);

    E pop();

    /**
     * 没有出栈操作
     */
    E top();
}
