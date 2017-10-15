package com.jel.tech.learn.ch09;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * heap priority queue,些堆非java内存中的那个堆，另外程序中用到了堆排序
 * @author jelex.xu
 * @date 2017年10月11日
 * @param <K>
 * @param <V>
 */
public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

	/*
	 * We prefer to use the more efficient array-based representation of a tree,
	 * maintaining a Java ArrayList of entry composites.
	 */
	protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

	public HeapPriorityQueue() {
		super();
	}
	public HeapPriorityQueue(Comparator<K> comp) {
		super(comp);
	}

	@Override
	public int size() {
		return heap.size();
	}

	/*
	 * To allow us to formalize our algorithms using tree-like
	 * terminology of parent, left, and right, the class includes
	 * protected utility methods that compute the level numbering
	 * of a parent or child of another position,the “positions”
	 * in this representation are simply integer indices into the array-list.
	 */
	protected int parent(int j) {
		return (j-1)/2;
	}
	protected int left(int j) {
		return 2*j+1;
	}
	protected int right(int j) {
		return 2*j + 2;
	}
	protected boolean hasLeft(int j) {
		return left(j) < heap.size();
	}
	protected boolean hasRight(int j) {
		return right(j) < heap.size();
	}

	/*
	 * 交换heap列表中index i和index j处的数据
	 */
	protected void swap(int i, int j) {
		Entry<K, V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	/*
	 * A new entry is added the end of the array-list,
	 * and then repositioned as needed with upheap
	 */
	protected void upheap(int j) {
		//root的index为0，对应heap中第一个元素
		while(j > 0) {
			int p = parent(j);
			//新增节点数据比父亲节点数据大，不用交换，直接结束！
			if(compare(heap.get(j), heap.get(p)) >= 0) {
				break;
			}
			//坏了，得和父节点交换
			swap(j, p);
			//重新给j赋值，就是原来处理节点的父节点，一级一级往上爬
			j = p;
		}
	}
	/*
	 * To remove the entry with minimal key (which resides at index 0),
	 * we move the last entry of the array-list from index n − 1 to index 0,
	 * and then invoke downheap to reposition it
	 */
	protected void downheap(int j) {
		/*
		 *Complete Binary Tree Property:
		 *	A heap T with height h is a complete binary tree if levels
		 *	 0,1,2,...,h−1 of T have the maximal number of nodes
		 *	possible (namely, level i has 2i nodes, for 0 ≤ i ≤ h − 1)
		 *	and the remaining nodes at level h reside in the
		 *leftmost possible positions at that level.
		 *上面最后一句牛逼了，节点是一层一层摆放的，并且是按最左边先摆放的，
		 *所以下一行代码处判断是否有左孩子节点
		 */
		while(hasLeft(j)) {
			int leftIndex = left(j);
			int smallChildIndex = leftIndex;
			if(hasRight(j)) {
				int rightIndex = right(j);
				//如果左孩子节点比父节点大，就需要再和右孩子节点值比较
				if(compare(heap.get(leftIndex), heap.get(j)) > 0) {
					smallChildIndex = rightIndex;
				}
			}
			//如果父节点比它的两个孩子节点上的值都小，那不用交换，直接结束
			if(compare(heap.get(smallChildIndex), heap.get(j)) >= 0) {
				break;
			}
			//
			swap(smallChildIndex, j);
			//
			j = smallChildIndex;
		}
	}



	@Override
	public Entry<K, V> insert(K k, V v) throws IllegalArgumentException {
		/*
		 * 校验key
		 */
		checkKey(k);
		PQEntry<K, V> entry = new PQEntry<K, V>(k, v);
		//把新增元素放到最后一个位置
		heap.set(heap.size(), entry);
		//修正heap的数据结构
		upheap(heap.size()-1);
		return entry;
	}

	@Override
	public Entry<K, V> min() {
		if(heap.isEmpty()) return null;
		return heap.get(0);
	}

	@Override
	public Entry<K, V> removeMin() {
		if(heap.isEmpty()) return null;
		Entry<K, V> temp = heap.get(0);
		/*
		 * 把最后一个和第一个交换
		 */
		swap(0, heap.size() -1);
		//本来要移除的是下标为0的，现在是最后一个了
		heap.remove(heap.size() -1);
		/*
		 * downheap修正数据结构,下标为0的元素是刚从最后一个元素
		 * 交换过来的，所以从它开始往下走，修复整体结构
		 */
		downheap(0);
		return temp;
	}

	/*
	 *  two coordinate arrays that are presumed to have the same length,
	 *  相比前一种处理方式（run in O(nlogn) time in the worst case），
	 *  here is an alternative bottom-up construction
	 *  method that runs in O(n) time.
	 */
	public HeapPriorityQueue(K[] keys, V[] values) {
		super();
		for(int i=0, size=Math.min(keys.length, values.length); i<size; i++) {
			heap.add(new PQEntry<K, V>(keys[i], values[i]));
		}
		heaplify();
	}
	/*
	 * 假设有n个元素，第一次先排列n/2个元素，你认为有n/2个heap也没错！
	 * 就是把n/2个元素摆放在最下面那一层；
	 * 第二次排列，在倒数第2层放入相应的元素并排序好，以便形成 n/4 个heap，
	 * 最后形成了根节点的左右子heap，加入root形成完整有序的一个heap.
	 */
	protected void heaplify() {
		//从最后一个元素的父节点开始搞事，往前遍历（它们都有孩子节点啊），
		//往下走，以便形成有序的heap
		int startIndex = parent(heap.size()-1);
		for(int j=startIndex; j>=0; j--) {
			downheap(j);
		}
	}

}
