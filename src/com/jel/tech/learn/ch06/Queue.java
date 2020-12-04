package com.jel.tech.learn.ch06;

/**
 * FIFO 队列
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月5日
 */
public interface Queue<E> {

    int size();

    boolean isEmpty();

    void enqueue(E e);

    E dequeue();

    E first();
}
