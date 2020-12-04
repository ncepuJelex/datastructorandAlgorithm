package com.jel.tech.learn.ch06;

/**
 * 基于数组实现的stack，固定大小容量是它的硬伤！
 * 下面看基于链表的stack实现，还有7.2.1节使用的方式。
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月4日
 */
public class ArrayStack<E> implements Stack<E> {
    //默认能保存1000个元素
    public static final int CAPACITY = 1000;
    //底层支撑的数据结构模型
    private E[] data;
    //当data[0-t]有元素时，大小为t+1,当栈为空时，栈的大小为t+1==0,符合规则
    private int t = -1;

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public ArrayStack() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public void push(E e) throws IllegalArgumentException {
        if (size() == data.length) {
            throw new IllegalArgumentException("栈满了！");
        }
        data[++t] = e;
    }

    @Override
    public E pop() {
        if (isEmpty()) return null;
        E answer = data[t];
        data[t] = null; //help GC
        t--;
        return answer;
    }

    @Override
    public E top() {
        if (isEmpty()) return null;
        return data[t];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder('(');
        for (int i = t; i >= 0; i++) {
            sb.append(data[i]);
            if (i > 0) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(')').toString();
    }

    /**
     * Demonstrates sample usage of a stack.
     */
    public static void main(String[] args) {
        Stack<Integer> S = new ArrayStack<>(); // contents: ()
        S.push(5); // contents: (5)
        S.push(3); // contents: (5, 3)
        System.out.println(S.size()); // contents: (5, 3) outputs 2
        System.out.println(S.pop()); // contents: (5) outputs 3
        System.out.println(S.isEmpty()); // contents: (5) outputs false
        System.out.println(S.pop()); // contents: () outputs 5
        System.out.println(S.isEmpty()); // contents: () outputs true
        System.out.println(S.pop()); // contents: () outputs null
        S.push(7); // contents: (7)
        S.push(9); // contents: (7, 9)
        System.out.println(S.top()); // contents: (7, 9) outputs 9
        S.push(4); // contents: (7, 9, 4)
        System.out.println(S.size()); // contents: (7, 9, 4) outputs 3
        System.out.println(S.pop()); // contents: (7, 9) outputs 4
        S.push(6); // contents: (7, 9, 6)
        S.push(8); // contents: (7, 9, 6, 8)
        System.out.println(S.pop()); // contents: (7, 9, 6) outputs 8
    }
}
