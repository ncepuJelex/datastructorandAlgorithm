package com.jel.tech.learn.ch03;

import java.util.Arrays;
import java.util.Random;

/**
 * 使用java.util.Random工具类产生“随机”数，
 * 其它新产生的数是前面那些数经过计算得出的，并不是完全随机，
 * 明显的是，如果使用同一个种子seed，作为开始，那后面所有的随机数
 * 是一样的，所以作者举例说明使用外太空的电波作为seed可能会
 * 真正达到随机的目的，或者把当前时间戳相关的数据作为种子。
 * @author jelex.xu
 * @date 2017年10月1日
 */
public class ArrayTest {

	public static void main(String[] args) {

		int [] data = new int[10];
		Random r = new Random();
		//以当前时间戳作为seed
		r.setSeed(System.currentTimeMillis());
		for(int i=0; i<data.length; i++) {
			data[i] = r.nextInt(100);
		}
		int[] orig = Arrays.copyOf(data, data.length);
		System.out.println("before sorting: " + Arrays.toString(orig));
		Arrays.sort(data);
		System.out.println("after sorting: " + Arrays.toString(data));
		/*
		 * running result:
		    before sorting: [1, 36, 29, 17, 45, 67, 90, 23, 28, 49]
			after sorting: [1, 17, 23, 28, 29, 36, 45, 49, 67, 90]
		 */
	}
}
