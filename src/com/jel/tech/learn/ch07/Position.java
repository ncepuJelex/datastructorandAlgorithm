package com.jel.tech.learn.ch07;

/**
 * We wish to be able to identify the position of an element
 * in a sequence without the use of an integer index.
 * The label “me” represents some abstraction that identifies
 * the position.
 * An interface for a position which is an abstraction for the
 * location at which a single element is stored in a positional
 * container.
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public interface Position<E> {

	/**
	 * @return
	 * @throws IllegalStateException 当position无效时
	 */
	E getElement() throws IllegalStateException;
}
