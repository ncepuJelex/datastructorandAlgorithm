package com.jel.tech.learn.ch08;

import com.jel.tech.learn.ch07.Position;

/**
 * 二叉树的实现，用大腿想，用链表实现好
 * @author jelex.xu
 * @date 2017年10月7日
 * @param <E>
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

	protected static class Node<E> implements Position<E> {

		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;

		public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
			this.element = element;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		@Override
		public E getElement() {
			return element;
		}

		public Node<E> getParent() {
			return parent;
		}
		public void setParent(Node<E> parent) {
			this.parent = parent;
		}
		public Node<E> getLeft() {
			return left;
		}
		public void setLeft(Node<E> left) {
			this.left = left;
		}
		public Node<E> getRight() {
			return right;
		}
		public void setRight(Node<E> right) {
			this.right = right;
		}
		public void setElement(E element) {
			this.element = element;
		}

	}
	/*
	 * factory method to create a node
	 */
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
		return new Node<E>(e, parent, left, right);
	}
	//protected修饰，因为后面会有子类继承
	protected Node<E> root = null; //根节点
	private int size = 0; //元素个数

	public LinkedBinaryTree() {
	}

	/*
	 * 工具校验方法
	 */
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		//如果p是null,也能过滤掉，直接抛出异常！
		if(!(p instanceof Node)) {
			throw new IllegalArgumentException("not a valid Position type.");
		}
		Node<E> n = (Node<E>) p;
		/*
		 * 约定：删除了的/无效的节点，让它的parent指向自身
		 */
		if(n.getParent() == n) {
			throw new IllegalArgumentException("p is no longer in the tree.");
		}
		return n;
	}

	public int size() {
		return size;
	}

	public Position<E> root() {
		return root;
	}

	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> n = validate(p);
		return n.getParent();
	}

	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> n = validate(p);
		return n.getLeft();
	}

	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> n = validate(p);
		return n.getRight();
	}

	/*
	 * 创建根节点，此时tree必须是空的，不然占人家的窝可不行！
	 */
	public Position<E> addRoot(E e) {
		if(!isEmpty()) throw new IllegalStateException("tree is not empty.");
		root = createNode(e, null, null, null);
		size = 1;
		return root;
	}

	/*
	 * 给p增加左节点，内容为e
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getLeft() != null) {
			throw new IllegalArgumentException("p already has a left child.");
		}
		Node<E> child = createNode(e, parent, null, null);
		parent.setLeft(child); //父节点关联子节点
		size++; //更新元素个数
		return child;
	}

	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getRight() != null) {
			throw new IllegalArgumentException("p already has a right child.");
		}
		Node<E> child = createNode(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}

	/*
	 * 更新Position的内容为e
	 */
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> n = validate(p);
		E answer = n.getElement();
		n.setElement(e);
		return answer;
	}

	/*
	 * 把2个链表树分别作为position的左右子树
	 */
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
			throws IllegalArgumentException {
		//先校验下p
		Node<E> n = validate(p);
		//非叶子节点不能这样搞！
		if(isInternal(p)) {
			throw new IllegalArgumentException("p must be a leaf.");
		}
		//更新元素大小
		size += t1.size + t2.size;
		//
		if(!t1.isEmpty()) {
			t1.root.setParent(n); //往上关联
			n.setLeft(t1.root); //往下关联
			t1.size = 0; //t1不再是一棵独立的tree了，没有size可言
			t1.root = null; //理由同上！
		}
		if(!t2.isEmpty()) {
			t2.root.setParent(n);
			n.setRight(t2.root);
			t2.size = 0;
			t2.root = null;
		}
	}
	/*
	 * Removes the node at Position p and replaces it with its child, if any.
	 */
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> n = validate(p);
		/*
		 * 有2个孩子节点的position不能remove，要不然你移除哪个！
		 */
		if(numChildren(p) == 2) {
			throw new IllegalArgumentException("p has two children, you can't remove.");
		}
		Node<E> child = n.getLeft() != null ? n.getLeft() : n.getRight();
		/*
		 * p节点被删除了，它的唯一的孩子节点顶上（如果有孩子的话）
		 */
		if(child != null) {
			child.setParent(n.getParent());
		}
		/*
		 * 如果被移除的是root节点，那它的孩子成为了新的root,恭喜啊！
		 */
		if(n == root) {
			child = root;
		}
		else { //这里就麻烦了！
			//先找出被移除节点的父节点
			Node<E> parent = n.getParent();
			//如果被移除节点是它父节点的左孩子
			if(n == left(parent)) {
				parent.setLeft(child);
			} else {
				parent.setRight(child);
			}
		}
		//更新元素大小
		size--;
		/*
		 * help GC并返回被移除position内的元素内容
		 */
		E answer = n.getElement();
		n.setElement(null);
		n.setLeft(null);
		n.setRight(null);
		n.setParent(n); // 记得约定吗？让它的parent指向它自身,这是invalid的标识
		return answer;
	}
}
