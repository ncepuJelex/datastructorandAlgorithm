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


}
