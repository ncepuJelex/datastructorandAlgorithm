package com.jel.tech.learn.ch07;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 场景：排队/吃饭，人来人走，等待空位，等不及走人……
 * 不管人员如何变动，一个人的位置虽然跟着变动，但是还是找到这个人！
 * 这个数据结构模型应运而生。。。
 * @author jelex.xu
 * @date 2017年10月6日
 * @param <E>
 */
public class LinkedPositionList<E> implements PositionList<E> {

	private static class Node<E> implements Position<E> {

		private E element; //该节点中所包含的元素
		private Node<E> prev; //前一个元素节点
		private Node<E> next; //后一个元素节点

		public Node(E e, Node<E> prev, Node<E> next) {
			this.element = e;
			this.prev = prev;
			this.next = next;
		}

		@Override
		public E getElement() throws IllegalStateException {
			//这是一个约定！
			if(next == null) {
				throw new IllegalStateException("该position不再有效！");
			}
			return element;
		}

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
		public void setElement(E element) {
			this.element = element;
		}

	}
	//=====Node code end======
	private Node<E> header; //头哨兵
	private Node<E> trailer; //尾哨兵
	private int size = 0; //元素个数

	//构造方法初始化头/尾哨兵，头next指向尾，尾prev指向头
	public LinkedPositionList() {
		header = new Node<E>(null, null, null);
		trailer = new Node<E>(null, header, null);
		header.setNext(trailer);
	}

	@Override
	public int size() {
		return size;
	}
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	/*
	 * 不想暴露哨兵节点,所以如果是空链表，直接返回null
	 */
	@Override
	public Position<E> first() {
		return position(header.getNext());
	}

	/*
	 * 目的是不暴露哨兵节点，这里作出处理
	 */
	private Position<E> position(Node<E> node) {
		if(node == header || node == trailer) {
			return null;
		}
		return node;
	}

	@Override
	public Position<E> last() {
		return position(trailer.getPrev());
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		//先对p作校验
		Node<E> n = validate(p);
		return position(n.getPrev());
	}

	/*
	 * 校验Position,校验成功后，转换成Node形式返回
	 */
	private Node<E> validate(Position<E> p) {
		if(!(p instanceof Node)) {
			throw new IllegalArgumentException("Invalid p: " + p);
		}
		Node<E> n = (Node<E>) p;
		//尾哨兵节点和无效节点的状态,不让用户知道哨兵节点
		if(n.getNext() == null) {
			throw new IllegalArgumentException("p不在列表中");
		}
		return n;
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		/*
		 * 校验p,如果p是头哨兵，能通过校验，
		 * 此时本方法最终返回的是：
		 * 	1.如果链表为空：null;
		 *  2.链表不为空：第一个元素节点，没毛病！
		 */
		Node<E> n = validate(p);
		return position(n.getNext());
	}


	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header, header.getNext());
	}

	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, trailer.getPrev(), trailer);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) {
		Node<E> n = validate(p);
		return addBetween(e, n.getPrev(), n);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) {
		Node<E> n = validate(p);
		return addBetween(e, n, n.getNext());
	}

	/*
	 * 添加节点的核心内部调用方法
	 */
	private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> newest = new Node<E>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
		return newest;
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> n = validate(p);
		E answer = n.getElement();
		n.setElement(e);
		return answer;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> n = validate(p);
		Node<E> prev = n.getPrev();
		Node<E> next = n.getNext();
		prev.setNext(next);
		next.setPrev(prev);
		size--;
		E answer = n.getElement();
		/*
		 * help GC,以及设置无效节点的标志！
		 */
		n.setElement(null);
		n.setPrev(null);
		n.setNext(null);

		return answer;
	}

	/*
	 * 这里名堂依赖下面
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

	/**
	 * 这里面名堂多啊！
	 */
	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}

	/**
	 * 核心内部类：遍历返回Position<E>,遍历返回E可以直接借助这个类实现
	 * @author jelex.xu
	 * @date 2017年10月6日
	 */
	private class PositionIterator implements Iterator<Position<E>> {

		private Position<E> cursor = first(); //游标位置，指向第一个元素
		private Position<E> recent = null; //用于remove操作

		/*
		 * 如果到达尾哨兵，返回null,如果一开始cursor为null,那就说明链表本身就是空的
		 */
		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public Position<E> next() {
			if(cursor == null) throw new NoSuchElementException("nothing left.");
			recent = cursor; //当前position变成最近访问的了
			cursor = after(cursor); //往前一步走！
			return recent;
		}

		@Override
		public void remove() {
			if(recent == null) {
				throw new IllegalStateException("nothing to remove.");
			}
			//remove方法会处理好前后关联关系！
			LinkedPositionList.this.remove(recent);
			recent = null; // help GC，并且不能再删除了，还没调用next遍历呢！
		}

	}

	/**
	 * PositionIterable 遍历返回Position<E>
	 */
	private class PositionIterable implements Iterable<Position<E>> {
		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		}
	}

	/**
	 * 内部类遍历返回Element，而不是上面那样的，返回Position<E>,
	 * 不过可以直接借助上面返回Position<E>的那个类来实现本类
	 */
	private class ElementIterator implements Iterator<E> {
		Iterator<Position<E>> positionIterator = new PositionIterator();

		@Override
		public boolean hasNext() {
			return positionIterator.hasNext();
		}

		@Override
		public E next() {
			return positionIterator.next().getElement();
		}

		@Override
		public void remove() {
			positionIterator.remove();
		}

	}

}
