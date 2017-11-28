package com.jel.tech.learn.ch12;

import java.util.Comparator;

import com.jel.tech.learn.ch06.LinkedQueue;
import com.jel.tech.learn.ch06.Queue;

/**
 * 快速排序，和 合并排序一样，也有divide，conquer, concate result
 * 3个步骤，但不同的是，快速排序主要的工作量在递归前已经完成了，而
 * 我们知道合并排序主要的工作量是在每个递归中的合并结果过程中，可参看quick-sort.png图片理解
 * @author jelex.xu
 * @date 2017年11月28日
 */
public class QuickSort {

	/*
	 * 1.使用链表数据结构实现的快速排序
	 */
	public static <K> void quickSort(Queue<K> S, Comparator<K> comp) {

		int n = S.size();
		if(n<2) return;
		//比选中为“种子选手”(pivot)小的元素放在这里面
		Queue<K> L = new LinkedQueue<K>();
		//和pivot相等的元素放在这里
		Queue<K> E = new LinkedQueue<K>();
		//比pivot大的元素放在这里
		Queue<K> G = new LinkedQueue<K>();
		K pivot = S.first();

		while(!S.isEmpty()) {
			K element = S.dequeue();
			int c = comp.compare(element, pivot);
			if(c < 0) {
				L.enqueue(element);
			}
			else if(c == 0) {
				E.enqueue(element);
			}
			else {
				G.enqueue(element);
			}
		}
		//2.conquer,递归调用
		quickSort(L, comp);
		quickSort(G, comp);
		//3.拼接结果，因为在递归前已经把整体排好序了，合并结果时直接拼接结果就好
		while(!L.isEmpty()) {
			S.enqueue(L.dequeue());
		}
		while(!E.isEmpty()) {
			S.enqueue(E.dequeue());
		}
		while(!G.isEmpty()) {
			S.enqueue(G.dequeue());
		}
	}
}
