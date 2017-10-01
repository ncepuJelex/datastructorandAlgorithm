package com.jel.tech.learn.ch03;

import java.util.Arrays;

/**
 * 插入排序：
 * The algorithm proceeds by considering one element at a time,
 * placing the element in the correct order relative to those before it.
 * @author jelex.xu
 * @date 2017年10月1日
 */
public class InsertSort {

	public static void insertionSort(char[] data) {
		int n = data.length;
		//从第2个元素开始，第一个元素自己排好序了！
		for(int i=1; i<n; i++) {
			char cur = data[i];
			int j = i;
			/*
			 * 只要current元素前面的那些元素大于它，就让它们后移一个位置
			 */
			while(j > 0 && data[j-1] > cur) {
				data[j] = data[j-1];
				j--;
			}
			/*
			 * 要么j==0了，表示current元素是最小的，所以排在最前面，
			 * 或者此时j>0,但是还有比current元素更小的元素
			 */
			data[j] = cur;
		}
	}

	public static void main(String[] args) {
		char[] a = { 'C', 'E', 'B', 'D', 'A', 'I', 'J', 'L', 'K', 'H', 'G', 'F' };
		System.out.println(java.util.Arrays.toString(a));
		insertionSort(a);
		System.out.println(java.util.Arrays.toString(a));

		char [] x = {'a','b','c'};
		char [] y = {'a','b','c','d'};
		System.out.println(Arrays.equals(x, y)); //false,还得长度一致！
		Arrays.fill(x, 'x');
		System.out.println(Arrays.toString(x)); //[x, x, x]
	}
}
