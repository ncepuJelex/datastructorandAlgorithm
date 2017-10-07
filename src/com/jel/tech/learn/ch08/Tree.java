package com.jel.tech.learn.ch08;

import java.util.Iterator;

import com.jel.tech.learn.ch07.Position;

/**
 * 树的接口定义
 * @author jelex.xu
 * @date 2017年10月7日
 * @param <E>
 */
public interface Tree<E> extends Iterable<E> {

	Position<E> root();
	Position<E> parent(Position<E> p) throws IllegalArgumentException;
	Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;

	int numChildren(Position<E> p) throws IllegalArgumentException;
	boolean isInternal(Position<E> p) throws IllegalArgumentException;
	boolean isExternal(Position<E> p) throws IllegalArgumentException;
	boolean isRoot(Position<E> p) throws IllegalArgumentException;
	int size();
	boolean isEmpty();
	Iterator<E> iterator();
	Iterable<Position<E>> positions();

}
