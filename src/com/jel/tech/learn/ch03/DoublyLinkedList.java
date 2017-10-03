package com.jel.tech.learn.ch03;
/**
 * 双向链表，这里使用sentiels-哨兵，即在链表元素前后增加了header和trailer
 * 2个虚拟节点，它们的内容为null,分别指向(next)第一个元素和最后一个元素(prev),
 * 在一个空的链表中，它们互相指向对方
 * @author jelex.xu
 * @date 2017年10月3日
 * @param <E>
 */
public class DoublyLinkedList<E> {

	private static class Node<E> {

		private E element;
		private Node<E> prev;
		private Node<E> next;

		public Node<E> getPrev() {
			return prev;
		}
		public void setPrev(Node<E> prev) {
			this.prev = prev;
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

		public Node(E element, Node<E> prev, Node<E> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}

	}

	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;

	/*
	 * 初始化header和trailer以及它们之间的关联关系
	 */
	public DoublyLinkedList() {
		header = new Node<E>(null, null, null);
		trailer = new Node<E>(null, header, null);
		header.setNext(trailer);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if(isEmpty()) return null;
		return header.getNext().getElement();
	}

	/*
	 * 我想，不加empty判断是不是也可，
	 * 因为trailer前一个元素是header，它的element也是为null
	 */
	public E last() {
		if(isEmpty()) return null;
		return trailer.getPrev().getElement();
	}

	public void addFirst(E e) {
		addBetween(e, header, header.getNext());
	}
	public void addLast(E e) {
		addBetween(e, trailer.getPrev(), trailer);
	}

	public E removeFirst() {
		if(isEmpty()) return null;
		return remove(header.getNext());
	}
	public E removeLast() {
		if(isEmpty()) return null;
		return remove(trailer.getPrev());
	}

	/*
	 * 删除元素的内部调用核心方法
	 */
	private E remove(Node<E> node) {
		Node<E> predecessor = node.getPrev();
		Node<E> successor = node.getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		return node.getElement();
	}

	/*
	 * add元素的内部调用核心方法
	 */
	private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> newest = new Node<E>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
	}
}
