package com.jel.tech.learn.ch07;

import java.util.Iterator;

/**
 * An interface for positional lists.
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public interface PositionList<E> extends Iterable<E> {

	int size();
	boolean isEmpty();
	Position<E> first();
	Position<E> last();
	Position<E> before(Position<E> p) throws IllegalArgumentException;
	Position<E> after(Position<E> p) throws IllegalArgumentException;
	Position<E> addFirst(E e);
	Position<E> addLast(E e);
	Position<E> addBefore(Position<E> p, E e);
	Position<E> addAfter(Position<E> p, E e);
	E set(Position<E> p, E e) throws IllegalArgumentException;
	E remove(Position<E> p) throws IllegalArgumentException;

	/**
	 * Returns an iterator of the elements stored in the list.
	 *
	 * @return iterator of the list's elements
	 */
	Iterator<E> iterator();

	/**
	 * Returns the positions of the list in iterable form from first to last.
	 *
	 * @return iterable collection of the list's positions
	 */
	Iterable<Position<E>> positions();
}
