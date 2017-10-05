package com.jel.tech.learn.ch06;

/**
 * FIFO 队列
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public interface Queue<E> {

	int size();
	boolean isEmpty();
	void enqueue(E e);
	E dequeue();
	E first();
}
