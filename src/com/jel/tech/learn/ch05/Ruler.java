package com.jel.tech.learn.ch05;

/**
 * 使用递归画一个尺子
 * @author jelex.xu
 * @date 2017年10月3日
 */
public class Ruler {

	/*
	 * 尺子的长度(寸) 和 最长刻度长度
	 */
	public static void drawRule(int nInches, int majorLength) {
		//先画出第一行：刻度为0的那一行
		drawLine(majorLength, 0);
		//遍历要画出的尺子的长度
		for(int i=1; i<=nInches; i++) {
			//这里是递归调用的核心
			drawInterval(majorLength);
			//画出一个inch范围内的最后一行
			drawLine(majorLength, i);
		}
	}
	/*
	 * 递归调用的核心方法
	 */
	private static void drawInterval(int centralLength) {
		//大于0才画出来，不然怎么出跳出递归啊！
		if(centralLength > 0) {
			//画上半部分
			drawInterval(centralLength-1);
			//画中间那一行,不需要刻画中尺度大小出来，所以给-1
			drawLine(centralLength-1, -1);
			//画下半部分，它和上半部分是一样一样的！
			drawInterval(centralLength-1);
		}
	}
	/*
	 * 画出一行，tickLength 为画出dash的长度，
	 * label 为刻度后面显示的尺寸大小（不小于0才显示出来）
	 */
	private static void drawLine(int tickLength, int label) {

		for(int i=0; i<tickLength; i++) {
			System.out.print("-");
		}
		if(label >= 0) {
			System.out.print(" " + label);
		}
		System.out.print("\n");
	}

	public static void main(String[] args) {
		drawRule(1, 5);
		/*
		 * running result:
		  	----- 0
			-
			--
			-
			---
			-
			--
			-
			----
			-
			--
			-
			---
			-
			--
			-
			----- 1
		 */
		drawRule(2, 4);
		/*
		 * running result:
			---- 0
			-
			--
			-
			---
			-
			--
			-
			---- 1
			-
			--
			-
			---
			-
			--
			-
			---- 2
		 */
		drawRule(3,3);
		/*
		 * running result:
		 	--- 0
			-
			--
			-
			--- 1
			-
			--
			-
			--- 2
			-
			--
			-
			--- 3
		 */
	}
}
