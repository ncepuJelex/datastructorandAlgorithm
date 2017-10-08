package com.jel.tech.learn.ch08;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jel.tech.learn.ch06.LinkedQueue;
import com.jel.tech.learn.ch06.Queue;
import com.jel.tech.learn.ch07.Position;

public abstract class AbstractTree<E> implements Tree<E> {

	public AbstractTree() {
	}

	@Override
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		int count=0;
	    for (Position<E> child : children(p)) count++;
	    return count;
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
		int count = 0;
		for(Position<E> p : positions()) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

	/*
	 * 默认采用的是先序遍历
	 */
	@Override
	public Iterable<Position<E>> positions() {
		return preorder();
	}

	/*
	 * 先序遍历
	 */
	public Iterable<Position<E>> preorder() {
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()) {
			preorderSubtree(root(), snapshot);
		}
		return snapshot;
	}
	/*
	 * 先序遍历工具内部调用方法
	 */
	private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
		snapshot.add(p);
		/*
		 * 递归遍历孩子节点
		 */
		for(Position<E> c : children(p)) {
			preorderSubtree(c, snapshot);
		}
	}
	/*
	 * 后序遍历
	 */
	public Iterable<Position<E>> postorder() {
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()) {
			postorderSubtree(root(), snapshot);
		}
		return snapshot;
	}
	/*
	 * 后序遍历内部调用方法
	 */
	private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
		for(Position<E> child : children(p)) {
			postorderSubtree(child, snapshot);
		}
		snapshot.add(p);
	}
	/*
	 * bread-first:水平一层层遍历
	 */
	public Iterable<Position<E>> breadFirst() {
		List<Position<E>> snapshot = new ArrayList<>();
		//不为空才去遍历，记得哦！
		if(!isEmpty()) {
			/*
			 * 这里使用queue这种数据结构，把当前遍历节点
			 * 的孩子（仅仅是儿子和女儿）放到queue中，然后从queue
			 * 中取出来遍历处理
			 */
			Queue<Position<E>> fringe = new LinkedQueue<>();
			//把根节点放到queue中，作为起始
			fringe.enqueue(root());
			/*
			 * 从queue取出元素处理，把他们的孩子压入queue，
			 * 直到queue为空
			 */
			while(!fringe.isEmpty()) {
				Position<E> p = fringe.dequeue();
				//处理当前节点
				snapshot.add(p);
				/*
				 * 把当前节点的孩子节点压入queue
				 */
				for(Position<E> child : children(p)) {
					fringe.enqueue(child);
				}
			}
		}
		return snapshot;
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

	/**
	 * 内部类，它依赖positions()方法返回Iterable<Position<E>>来达到遍历
	 * Position<E>中的元素的目的
	 * @author jelex.xu
	 * @date 2017年10月8日
	 */
	private class ElementIterator implements Iterator<E> {

		Iterator<Position<E>> iterator = positions().iterator();

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}
		@Override
		public E next() {
			return iterator.next().getElement();
		}
		@Override
		public void remove() {
			iterator.remove();
		}
	}

}
