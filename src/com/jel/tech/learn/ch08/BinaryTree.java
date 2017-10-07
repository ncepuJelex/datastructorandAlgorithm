package com.jel.tech.learn.ch08;

import com.jel.tech.learn.ch07.Position;

/**
 * 二叉树
 * @author jelex.xu
 * @date 2017年10月7日
 * @param <E>
 */
public interface BinaryTree<E> extends Tree<E> {

	Position<E> left(Position<E> p) throws IllegalArgumentException;
	Position<E> right(Position<E> p) throws IllegalArgumentException;
	Position<E> sibling(Position<E> p) throws IllegalArgumentException;
}
