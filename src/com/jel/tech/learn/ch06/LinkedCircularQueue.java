package com.jel.tech.learn.ch06;

import com.jel.tech.learn.ch03.CircularlyLinkedList;
/**
 * 使用环状首尾相连实现的queue,举例来说，用它来处理约瑟夫环问题。
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public class LinkedCircularQueue<E> implements CircularQueue<E> {

	private CircularlyLinkedList<E> list = new CircularlyLinkedList<E>();

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

	@Override
	public void rorate() {
		list.rorate();
	}

	public String toString() {
		return list.toString();
	}

}
