package com.jel.tech.learn.ch08;

import java.util.Iterator;

import com.jel.tech.learn.ch07.Position;

public abstract class AbstractTree<E> implements Tree<E> {

	@Override
	public Position<E> root() {
		return null;
	}

	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) > 0;
	}

	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) == 0;
	}

	@Override
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		return p == root();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 计算深度：
	 * If p is the root,then the depth of p is 0.
 		Otherwise, the depth of p is one plus the depth
 		of the parent of p.
	 */
	public int depth(Position<E> p) {
		if(isRoot(p)) return 0;
		return 1 + depth(parent(p));
	}

	/*
	 * 计算高度：
	 * the height of a tree to be equal to the maximum of the depths
	 * of its positions (or zero, if the tree is empty)
	 * 这里private 修饰方法，起名为heightBad，表示效率很低，在具体的实现类中
	 * 会有更高效的实现方式,
	 * algorithm heightBad runs in O(n2) worst-case time
	 */
	private int heightBad() {
		int h = 0;
		for(Position<E> p : positions()) {
			/*
			 * It is easy to see that the position
			 * with maximum depth must be a leaf.
			 */
			if(isExternal(p)) {
				h = Math.max(h, depth(p));
			}
		}
		return h;
	}
	/**
	 * 递归的方式计算树的高度,
	 * Formally, we define the height of a position p in a tree T as follows:
		• If p is a leaf,then the height of p is 0.
		• Otherwise, the height of p is one more than the maximum
			of the heights of p’s children
	 */
	public int height(Position<E> p) {
		int h = 0;
		for(Position<E> c : children(p)) {
			h = Math.max(h, 1+height(c));
		}
		return h;
	}

}
