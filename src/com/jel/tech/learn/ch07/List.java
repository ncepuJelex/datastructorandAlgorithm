package com.jel.tech.learn.ch07;

/**
 * chapter07,从你开始！
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public interface List<E> {

	int size();
	boolean isEmpty();
	E get(int i) throws IndexOutOfBoundsException;
	E set(int i, E e) throws IndexOutOfBoundsException;
	void add(int i, E e) throws IndexOutOfBoundsException;
	//在最后添加该元素
	void add(E e);
	E remove(int i) throws IndexOutOfBoundsException;
}
