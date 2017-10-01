package com.jel.tech.learn.ch03;

/**
 * 首尾相接的链表，Round-Robin Scheduling CPU执行时间片时，循环执行每个任务固定时间片
 *
 * @author jelex.xu
 * @date 2017年10月1日
 */
public class CircularlyLinkedList<E> {

	private static class Node<E> {
		private E element;
		private Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public E getElement() {
			return element;
		}
	}
	/*
	 * 只需要tail，不需要head了，因为tail的next指向的就是head,环状的嘛！
	 */
	private Node<E> tail = null;
	private int size = 0; //元素个数

	public CircularlyLinkedList() {

	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public E first() {
		if(isEmpty()) return null;
		return tail.getNext().getElement();
	}
	public E last() {
		if(isEmpty()) return null;
		return tail.getElement();
	}
	/*
	 * 旋转一下
	 */
	public void rorate() {
		if(tail != null) {
			tail = tail.getNext();
		}
	}
	public void addFirst(E e) {
	/*	Node<E> n = new Node<E>(e, null);
		if(isEmpty()) {
			tail = n;
			tail.setNext(tail);
		} else {
			n.setNext(tail.getNext());
			tail.setNext(n);
		}*/
		if(size == 0) {
			tail = new Node<E>(e, null);
			tail.setNext(tail);
		} else {
			Node<E> newest = new Node<E>(e, tail.getNext());
			tail.setNext(newest);
		}
		size++;
	}

	/*
	 * 想想，addLast就不是相当于addFirst，然后rotate一下(不用判空)！
	 */
	public void addLast(E e) {
		this.addFirst(e);
		tail = tail.getNext();
	}

	public E removeFirst() {
		if(isEmpty()) return null;
		Node<E> head = tail.getNext();
		//如果原本只有一个元素，此时tail和head都指向一个元素
		//删除后，tail指向null
		if(head == tail) {
			tail = null;
		} else {
			tail.setNext(head.getNext());
		}
		size--;
		return head.getElement();
	}
}
