package com.jel.tech.learn.ch12;

import java.util.Comparator;

import com.jel.tech.learn.ch06.LinkedQueue;
import com.jel.tech.learn.ch06.Queue;

/**
 * 快速排序，和 合并排序一样，也有divide，conquer, concate result
 * 3个步骤，但不同的是，快速排序主要的工作量在递归前已经完成了，而
 * 我们知道合并排序主要的工作量是在每个递归中的合并结果过程中，可参看quick-sort.png图片理解,
 * Although quick-sort has very good performance on large data sets,
 * it has rather high overhead on relatively small data sets,
 * In practice, a simple algorithm like insertion- sort (Section 7.6)
 * will execute faster when sorting such a short sequence.
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

	/**
	 * An algorithm is in-place if it uses only a small amount of memory
	 * in addition to that needed for the original input.
	 * 上面的算法不是in place算法，因为还另外引入了G E L3个Queue
	 * @param S
	 * @param comp
	 */
	public static <K> void quickSortInplace(K[] S, Comparator<K> comp) {
		quickSortInplace(S, comp, 0, S.length);
	}
	/*
	 * 可参看quick-sort-in-place.png理解
	 */
	private static <K> void quickSortInplace(K[] s, Comparator<K> comp, int start, int end) {

		if(start >= end) return;

		int left = start;
		int right = end-1;
		//最后一个元素作为 pivot
		K pivot = s[end];
		K temp;
		while(left <= right) {
			/*
			 * scan until reaching value equal or larger than pivot (or right marker)
			 * 往右遍历，直到一个元素大于等于pivot（如果没有这样的元素，那直接遍历到最右边了）
			 */
			while(left<=right && comp.compare(s[left], pivot) < 0) left++;
			/*
			 * 和上面相反
			 */
			while(left<=right && comp.compare(s[right], pivot) > 0) right++;

			if(left<=right) {
				/*
				 * swap
				 */
				temp = s[left];
				s[left] = s[right];
				s[right] = temp;
				/*
				 *缩短范围
				 */
				left++;
				right--;
			}
		}
		/*
		 * 跳出了循环，此时left位置和pivot位置交换，让pivot归位，
		 * left的位置是属于pivot的
		 */
		temp = s[left];
		s[left] = s[end];
		s[end] = temp;
		/*
		 * 递归处理
		 */
		quickSortInplace(s, comp, start, left-1);
		quickSortInplace(s, comp, left+1, end);
	}




}
