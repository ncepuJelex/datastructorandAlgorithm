package com.jel.tech.learn.ch06;

/**
 * 在首尾两端都能操作的queue,
 * The extra generality can be useful in some applications.
 * For example, we described a restaurant using a queue to
 * maintain a wait list. Occasionally, the first person might
 * be removed from the queue only to find that a table was
 * not available; typically, the restaurant will reinsert
 * the person at the first position in the queue. It may also
 * be that a customer at the end of the queue may grow impatient
 * and leave the restaurant
 * Because the deque requires insertion and removal at both ends,
 * a doubly linked list is most appropriate for implementing all
 * operations efficiently,第3章中的DoublyLinkedList其实已经实现了该接口！
 *
 * @param <E>
 * @author jelex.xu
 * @date 2017年10月5日
 */
public interface Dequeue<E> {

    int size();

    boolean isEmpty();

    void addFirst(E e);

    void addLast(E e);

    E first();

    E last();

    E removeFirst();

    E removeLast();
}
