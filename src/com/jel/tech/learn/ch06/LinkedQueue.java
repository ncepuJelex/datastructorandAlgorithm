package com.jel.tech.learn.ch06;

import com.jel.tech.learn.ch03.SinglyLinkedList;

/**
 * 基于链表实现的queue
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public class LinkedQueue<E> implements Queue<E> {

	private SinglyLinkedList<E> list = new SinglyLinkedList<>();

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void enqueue(E e) {
		list.addLast(e);
	}

	@Override
	public E dequeue() {
		return list.removeFirst();
	}

	@Override
	public E first() {
		return list.first();
	}

}
