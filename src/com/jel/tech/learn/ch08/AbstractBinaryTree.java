package com.jel.tech.learn.ch08;

import java.util.ArrayList;
import java.util.List;

import com.jel.tech.learn.ch07.Position;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

	@Override
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		List<Position<E>> snapshot = new ArrayList<>(2);
		if(left(p) != null) {
			snapshot.add(left(p));
		}
		if(right(p) != null) {
			snapshot.add(right(p));
		}
		return snapshot;
	}

	@Override
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		int count = 0;
		if(left(p) != null) {
			count++;
		}
		if(right(p) != null) {
			count++;
		}
		return count;
	}

	@Override
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		Position<E> parent = parent(p);
		/*
		 * root的parent才是null
		 */
		if (parent == null) {
			return null;
		}
		// 如果是左孩子节点
		if (p == left(parent)) {
			return right(parent);
		} else {
			return left(parent);
		}
	}

	/*
	 * For many applications of binary trees ,
	 * an inorder traversal is the most natural order,
	 * 因为AbstractTree类中实现的默认是先序遍历，而不是inorder顺序，
	 * 所以这里重写父类的这个方法
	 */
	@Override
	public Iterable<Position<E>> positions() {
		return inorder();
	}

	/*
	 * 二叉树独有的遍历方式
	 */
	public Iterable<Position<E>> inorder() {
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()) {
			inorderSubtree(root(), snapshot);
		}
		return snapshot;
	}
	/*
	 * binary tree遍历内部调用工具方法
	 */
	private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
		/*
		 * 按照左中右的顺序分别遍历
		 */
		if(left(p) != null) {
			inorderSubtree(left(p), snapshot);
		}
		snapshot.add(p);
		if(right(p) != null) {
			inorderSubtree(right(p), snapshot);
		}
	}

}
