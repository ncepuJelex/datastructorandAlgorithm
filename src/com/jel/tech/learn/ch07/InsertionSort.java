package com.jel.tech.learn.ch07;


/**
 * 对PositionList做插入排序处理
 *
 *
 * 	  walk 		   pivot
15 22 25 29 	36 		23 53 11 42
			marker
 *
 *marker：指向最后已经排好序的元素，此时指向36，
 *walk：从marker处往左移动，直到左边没有元素比pivot指向节点的元素大了
 *pivot：指向下一个待排序的元素，此时指向23
 *
 * @author jelex.xu
 * @date 2017年10月6日
 */
public class InsertionSort<E> {

	/*
	 * 插入排序，这里 面向过程编程，排序后的结果就是入参:list
	 */
	public static void insertionSort(PositionList<Integer> list) {

		//准备排序了，刚开始指向第一个元素
		Position<Integer> marker = list.first();
		//当marker指向最后一个元素了，表示排好序
		while(marker != list.last()) {
			Position<Integer> pivot = list.after(marker);
			int value = pivot.getElement();
			//pivot指向的元素不比marker指向的元素小，不用排序，marker往右移就行：即pivot成为新的marker!
			if(value >= marker.getElement()) {
				marker = pivot;
			}
			else {
				Position<Integer> walk = marker; //walk从marker处往左移动
				/*
				 * 一直往左找！直到找到一个元素比pivot小为止！如果没找到，那walk指向第
				 * 一个元素，就不再找了，此时pivot最小，插在第一个位置(first)就好了！
				 * 使用list.before(walk)而不是直接使用walk省掉了第一次的比较,yeah~
				 */
				while(walk != list.first() && list.before(walk).getElement() > pivot.getElement()) {
					walk = list.before(walk); //walk就一直往左移啊移！
				}
				/*
				 * 此时，要么walk指向第一个元素，要么指向在往左移动过程中最后一个大于pivot的元素，
				 * 再往左一个就小于pivot了，把pivot拽过来插入到相应位置
				 */
				list.remove(pivot);
				list.addBefore(walk, value);
			}
		}
	}

	public static void main(String[] args) {
		int[][] tests = { {}, { 1 }, { 1, 2 }, { 2, 1 }, { 3, 5, 2, 4, 1, 9, 10, 12, 11, 8, 7, 6 }, };

		for (int[] raw : tests) {
			PositionList<Integer> data = new LinkedPositionList<>();
			for (int c : raw)
				data.addLast(c);
			System.out.println("Before: " + data);
			insertionSort(data);
			System.out.println("After:  " + data);
			System.out.println();
		}
	}
}
