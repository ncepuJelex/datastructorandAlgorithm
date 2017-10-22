package com.jel.tech.learn.ch03;

/**
 * 单向链表，所以不提供removeLast功能，因为需要从head遍历到tail前一个元素，后面
 * 会有另外一种数据结构来处理removeLast功能.
 * 这里：head指向的就是第一个元素，而不是哨兵节点！
 * @author jelex.xu
 * @date 2017年10月1日
 * @param <E>
 */
public class SinglyLinkedList<E> implements Cloneable {

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

	public int size() {
		return size;
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
	/*
	 * 重写equals方法
	 */

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		/*
		 * Although our SinglyLinkedList class has a declared
		 * formal type parameter <E>, we cannot detect at
		 * runtime whether the other list has a matching type
		 * 所以不使用SinglyLinkedList<E>,下面walkA和walkB也是因此
		 */
		SinglyLinkedList other = (SinglyLinkedList) obj;
		if(this.size != other.size) {
			return false;
		}
		Node walkA = this.head; //当前链表遍历位置
		Node walkB = other.head; //待比较链表遍历位置
		while(walkA != null) {
			/*
			 * 这是建立在SinglyLinkedList内的元素内容不为空的前提下
			 */
			if(!walkA.getElement().equals(walkB.getElement())) {
				return false;
			}
			walkA = walkA.getNext();
			walkB = walkB.getNext();
		}
		return true;
	}
	/*
	 * 重写hashCode()方法，直到学习第10章hashCode那节才回到这来
	 * 实现该方法，勿见怪！
	 * A variant of the polynomial hash code replaces multiplication by
	 * a with a cyclic shift of a partial sum by a certain number of bits.
	 * For example, a 5-bit cyclic shift of the 32-bit value
		101100101101010100010101000 is achieved by taking the
		leftmost five bits and placing those on the rightmost side of the representation,
		resulting in 10110010110101010001010100000111.
		While this operation has little natural meaning in terms of arithmetic,
		it accomplishes the goal of varying the bits of the calculation.
		In Java, a cyclic shift of bits can be accomplished through careful
		use of the bitwise shift operators.we must wisely choose the amount
		to shift by for each new character. Our choice of a 5-bit shift is
		justified by experiments run on a list of just over 230,000 English words,
		comparing the number of collisions for various shift amounts
	 */
	@Override
	public int hashCode() {
		int h = 0;
		for(Node<E> walk=head; walk!=null; walk=walk.getNext()) {
			h ^= walk.getElement().hashCode();
			h = (h<<5) | (h>>>27);
		}
		return h;
	}

	public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
		//把size和head clone一下,size这样搞没问题，但是head就不行了，除非head==null
		//一般都会调用一下super.clone(),把那些非引用字段可以克隆出来
		SinglyLinkedList other = (SinglyLinkedList) super.clone();
		if(size > 0) {
			//重新处理克隆节点的head元素
			other.head = new Node<E>(head.getElement(), null);
			//克隆节点的最后一个元素，它是实时更新的,初始化为它的头节点
			Node<E> otherTail = other.head;
			Node<E> walk = head.getNext(); //遍历起始位置
			while(walk != null) {
				Node<E> newest = new Node<E>(walk.getElement(), null);
				otherTail.setNext(newest);
				otherTail = newest; //更新克隆节点的tail
				walk = walk.getNext();
			}
		}
		return other;
	}
	/**
	 * Produces a string representation of the contents of the list. This exists
	 * for debugging purposes only.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder('(');
		Node<E> walk = head;
		while (walk != null) {
			sb.append(walk.getElement());
			if (walk != tail) {
				sb.append(',').append(' ');
			}
			walk = walk.getNext();
		}
		return sb.append(')').toString();
	}

}
