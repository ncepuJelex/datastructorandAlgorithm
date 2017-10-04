package com.jel.tech.learn.ch05;

public class BinarySearch {

	public static boolean binarySearch(int[] data, int target, int low, int high) {
		/*
		 * 没找到target元素
		 */
		if (low > high) {
			return false;
		} else {
			int mid = (low + high) / 2;
			// 正好是中间那个！
			if (target == data[mid]) {
				return true;
			}
			// 左边部分再次查找
			else if (target < data[mid]) {
				return binarySearch(data, target, low, mid - 1);
			}
			// 右边部分再次查找
			else {
				return binarySearch(data, target, mid + 1, high);
			}
		}
	}

	/*
	 * 这才是用户应该调用的方法
	 */
	public static boolean binarySearch(int[] data, int target) {
		return binarySearch(data, target, 0, data.length - 1);
	}

	public static boolean binarySearchIterative(int[] data, int target) {
		int low = 0, high = data.length - 1;
		int mid = 0;
		while (low <= high) {
			mid = (low + high) / 2;
			if (target == data[mid]) {
				return true;
			} else if (target < data[mid]) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return false;
	}

	/** Simple test, assuming valid integer given as command-line argument */
	public static void main(String[] args) {
		final int N = 100;
		int[] data = new int[N];
		for (int j = 0; j < N; j++)
			data[j] = 1 + 2 * j;

		for (int j = 0; j < N; j++) {
			if (!binarySearch(data, 1 + 2 * j))
				System.out.println("Recursive failure to find value " + (1 + 2 * j));
			if (!binarySearchIterative(data, 1 + 2 * j))
				System.out.println("Iterative failure to find value " + (1 + 2 * j));
		}

		for (int j = 0; j <= N; j++) {
			if (binarySearch(data, 2 * j))
				System.out.println("Recursive false-positive on value " + (2 * j));
			if (binarySearchIterative(data, 2 * j))
				System.out.println("Iterative false-positive on value " + (2 * j));
		}
		/*
		 * running result:
		 * 空的控制台
		 */
	}
}
