package com.jel.tech.learn.ch06;

/**
 * 基于环状数组实现的queue
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月5日
 */
public class ArrayQueue<E> implements Queue<E> {

    public static final int CAPACITY = 1000;

    private E[] data;
    private int f = 0; //cursor pointing at first element
    private int size = 0; //元素个数

    public ArrayQueue() {
        this(CAPACITY);
    }

    public ArrayQueue(int capacity) {
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
    public void enqueue(E e) throws IllegalArgumentException {
        if (size == data.length) {
            throw new IllegalArgumentException("queue is full.");
        }
        data[(f + size) % data.length] = e;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        E answer = data[f];
        data[f] = null; //help GC
        f = (f + 1) % data.length;
        size--;
        return answer;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return data[f];
    }

}
