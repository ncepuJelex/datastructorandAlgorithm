package com.jel.tech.learn.ch06;

import java.util.Arrays;

import com.jel.tech.learn.ch03.SinglyLinkedList;

/**
 * 基于链表存储实现的stack,
 * The Adapter Pattern:
 * One general way to apply the adapter pattern is to
 * define a new class in such a way that it contains an instance
 * of the existing class as a hidden field, and then to implement
 * each method of the new class using methods of this hidden
 * instance variable
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月4日
 */
public class LinkedStack<E> implements Stack<E> {

    private SinglyLinkedList<E> list = new SinglyLinkedList<E>();

    public LinkedStack() {
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }

    @Override
    public E top() {
        return list.first();
    }

    public static <E> void reverse(E[] data) {
        Stack<E> buf = new ArrayStack<E>(data.length);
        for (int i = 0; i < data.length; i++) {
            buf.push(data[i]);
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = buf.pop();
        }
    }

    /*
     * 利用stack的特性来reverse一个数组内的元素
     */
    public static void main(String[] args) {

        Integer[] a = {4, 8, 15, 16, 23, 42}; // auto boxing allows this
        String[] s = {"Jack", "Kate", "Hurley", "Jin", "Michael"};
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("s = " + Arrays.toString(s));
        System.out.println("Reversing...");
        reverse(a);
        reverse(s);
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("s = " + Arrays.toString(s));
		/*
		 * running result:
		  a = [4, 8, 15, 16, 23, 42]
			s = [Jack, Kate, Hurley, Jin, Michael]
			Reversing...
			a = [42, 23, 16, 15, 8, 4]
			s = [Michael, Jin, Hurley, Kate, Jack]
		 */
    }

}
