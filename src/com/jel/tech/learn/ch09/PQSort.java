package com.jel.tech.learn.ch09;

import com.jel.tech.learn.ch07.PositionList;

/**
 * 堆排序，只能说是一种方案，而不是一种算法，因为heap priority
 * 没有具体指定使用哪种数据模型实现
 * @author jelex.xu
 * @date 2017年10月15日
 */
public class PQSort {

	/*
	 * list中元素为待排序内容，pq为空的priority queue
	 */
	public static <E> void pqSort(PositionList<E> list, PriorityQueue<E, ?> pq) {
		int size = list.size();
		/*
		 * 先把list中元素转移到priority queue中，
		 * 当pq是无序数据结构时，每一次操作需要O(1)时间，所以总的O(n)时间，挺快！
		 * 当pq是有序数据结构时，
		 */
		for(int j=0; j<size; j++) {
			E e = list.remove(list.first());
			pq.insert(e, null);
		}
		/*
		 * 对priority queue中的元素排序，
		 * 当使用无序数据结构实现该pq时，效率瓶颈在这里！这就引申出selection sort(选择排序)，
		 * 每次从pq中找出最小的元素，每一次需要o(n)，第二次需要o(n-1),
		 * ... ,最后一次需要 o(1)，总的加起来是o(n*(n+1)/2),即o(n的平方)，
		 * 这可不好！
		 * 当我们使用有序数据结构实现该pq时，效率就变成了o(n)！
		 * 但与此同时，第一个阶段效率就变慢了！瓶颈点在第一个阶段，由此引申出插入排序的概念！
		 * 此时，第一阶段效率像pq无序时的第二阶段那样，需要 o(n的平方),也不好！
		 * 所以heap sort，堆排序要出场了！通过改变第一阶段的算法，让最终效率转变成o(I+n),
		 * I:inverse，表示第一阶段插入时，需要转换的元素个数，o(n)就此第二阶段需要的时间，
		 * 具体来说：During Phase 1, the i th insert operation takes O(log i) time,
		 * since the heap has i entries after the operation is performed.
		 * Therefore, this phase takes O(n log n) time. (It could be improved to
		 * O(n) with the bottom-up heap construction.
		 * During the second phase of method pqSort, the j th removeMin operation
		 * runs in O(log(n − j + 1)), since the heap has n − j + 1 entries at the
		 * time the operation is performed. Summing over all j, this phase takes
		 * O(nlogn) time, so the entire priority-queue sorting algorithm runs in
		 * O(nlogn) time when we use a heap to implement the priority queue.
		 */
		for(int j=0; j<size; j++) {
			list.addLast(pq.removeMin().getKey());
		}

	}
}
