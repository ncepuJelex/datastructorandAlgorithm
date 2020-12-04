package com.jel.tech.learn.ch07;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 基于数组实现的list
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月5日
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
        if (i < 0 || i >= size) {
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
        checkIndex(i, size + 1);
		/*这种方式不可取了，为什么不扩展呢！
		 * if(size == data.length) {
			throw new IllegalArgumentException("Array is full");
		}*/
        if (size == data.length) {
            resize(2 * data.length);
        }
        //插入元素后面的部分需要右移，腾出空间给新元素
        for (int k = size - 1; k >= i; k--) {
            data[k + 1] = data[k];
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
        for (int k = i; k < size - 1; k++) {
            data[k] = data[k + 1];
        }
        //更新元素个数
        size--;
        data[size - 1] = null; //help GC
        return tmp;
    }

    /*
     * 动态的list
     */
    protected void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
        //让data重新指向新的数组，原来的没引用指向它就会被GC
        data = temp;
    }

    /*
     * 注意哦，前面不能有<E>标识！
     */
    private class ArrayIterator implements Iterator<E> {

        private int j; //遍历的位置
        private boolean removable = false; //表示当前是否可调用remove方法

        /*
         * 只要还没有遍历到list最后，hasNext()就返回true
         */
        @Override
        public boolean hasNext() {
            return j < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (j == size) {
                throw new NoSuchElementException("没有元素了！");
            }
            removable = true; //此时可remove了！
            return data[j++];
        }

        /*
         * 可选择性的重写的方法
         */
        @Override
        public void remove() {
            if (!removable) {
                throw new IllegalStateException("nothine to remove.");
            }
            //调用remove()之前，next()已经j++了，所以是j-1.
            ArrayList.this.remove(j - 1); //that was the last one returned
            j--; //next element has shifted one cell to the left
            removable = false; //do not allow remove again until next is called
        }

    }

    /*
     * 这是实时的，和list中元素是实时同步的，而不是
     * 初始化时的snapshot,把那个时刻的list元素复制出来，
     * 我们这个是lazy initial.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }
}
