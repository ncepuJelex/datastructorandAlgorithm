package com.jel.tech.learn.ch03;

/**
 * 单向链表，所以不提供removeLast功能，因为需要从head遍历到tail前一个元素，后面
 * 会有另外一种数据结构来处理removeLast功能.
 * 这里：head指向的就是第一个元素，而不是哨兵节点！
 * @author jelex.xu
 * @date 2017年10月1日
 * @param <E>
 */
public class SinglyLinkedList<E> {

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

	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0; //链表元素个数

	public SinglyLinkedList() {
	}

	/*
	 * 判断链表是否为空！
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/*
	 * 获得第一个元素，但是不移除元素
	 */
	public E first() {
		if(isEmpty()) return null;
		return head.getElement();
	}
	/*
	 * 获得最后一个元素,但是不从链表中移除它
	 */
	public E last() {
		if(isEmpty()) return null;
		return tail.getElement();
	}
	/*
	 * 在头部新增一个元素
	 */
	public void addFirst(E e) {
		//一箭双雕！，新建一个Node元素，让它的next指向当前head,
		//然后更新当前head指向该新建节点
		head = new Node<E>(e, head);
		//照顾下tail节点，考虑该新增节点是链表中第一个节点的情况
		if(size == 0) {
			tail = head;
		}
		//链表中元素个数加1
		size++;
	}

	/*
	 * 在链表结尾插入一个元素
	 */
	public void addLast(E e) {
		Node<E> n = new Node<E>(e, null);
		//同样考虑到该元素是第一个存放到链表中的情况，
		//此时head和tail都是null
		if(size == 0) {
			head = n;
		} else {
			tail.setNext(n);
		}
		//让tail指向该新增的元素
		tail = n;
		//更新链表中元素数量
		size++;
	}
	/*
	 * 移除第一个元素
	 */
	public E removeFirst() {
		if(isEmpty()) return null;
		E e = head.getElement();
		//让head指向下一个元素
		head = head.getNext();
		//更新链表中元素个数
		size--;
		//这里关键了！如果在移除前只有一个元素，那么现在
		//需要让tail指向null了，因为已经没有元素了！
		//tail原来指向的元素是脏数据
		if(size == 0) {
			tail = null;
		}
		return e;
	}

}
