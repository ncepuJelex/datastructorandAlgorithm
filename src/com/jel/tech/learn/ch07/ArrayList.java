package com.jel.tech.learn.ch07;

/**
 * 基于数组实现的list
 * @author jelex.xu
 * @date 2017年10月5日
 * @param <E>
 */
public class ArrayList<E> implements List<E> {

	public static final int CAPACITY = 16;
	private E[] data; //装载元素的数组
	private int size = 0; //元素个数

	public ArrayList() {
		this(CAPACITY);
	}
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		data = (E[]) new Object[capacity];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		return data[i];
	}

	/*
	 * 校验index
	 */
	private void checkIndex(int i, int size) {
		if(i<0 || i>=size) {
			throw new IndexOutOfBoundsException("非法index: " + i);
		}
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E tmp = data[i];
		data[i] = e;
		return tmp;
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i, size+1);
		/*这种方式不可取了，为什么不扩展呢！
		 * if(size == data.length) {
			throw new IllegalArgumentException("Array is full");
		}*/
		if(size == data.length) {
			resize(2*data.length);
		}
		//插入元素后面的部分需要右移，腾出空间给新元素
		for(int k=size-1; k>=i; k--) {
			data[k+1] = data[k];
		}
		data[i] = e;
		//更新元素个数
		size++;
	}

	@Override
	public void add(E e) {
		this.add(size, e);
	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E tmp = data[i];
		//耗时也！
		for(int k=i; k<size-1; k++) {
			data[k] = data[k+1];
		}
		//更新元素个数
		size--;
		data[size-1] = null; //help GC
		return tmp;
	}

	/*
	 * 动态的list
	 */
	protected void resize(int capacity) {
		E[] temp = (E[]) new Object[capacity];
		for(int i=0; i<size; i++) {
			temp[i] = data[i];
		}
		//让data重新指向新的数组，原来的没引用指向它就会被GC
		data = temp;
	}
}
