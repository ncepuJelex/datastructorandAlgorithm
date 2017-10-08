package com.jel.tech.learn.ch08;

import java.util.ArrayList;

import com.jel.tech.learn.ch07.Position;

/**
 * 遍历打印，突出tree的层次效果
 * @author jelex.xu
 * @date 2017年10月8日
 */
public class TravesalExample {

	/*
	 * 这种方式打印出来，就是一坨翔一样，没有indent,突出效果
	 */
	public static <E> void printPreorder(AbstractTree<E> t) {
		for(Position<E> p : t.preorder()) {
			System.out.println(p.getElement());
		}
	}
	/*
	 * 这种方式有突出效果，但是效率太TM low了，O(n平方)，谁受得了！
	 */
	public static <E> void printPreorderSlow(AbstractTree<E> t) {
		for(Position<E> p : t.preorder()) {
			System.out.println(space(2*t.depth(p)) + p.getElement());
		}
	}
	/*
	 * 这种方式也有indent效果，效率也可以！^_^,
	 * 开始调用时：printPreorderIndent(t, t.root(), 0)
	 */
	public static <E> void printPreorder(AbstractTree<E> t, Position<E> p, int d) {
		System.out.println(space(2*d) + p.getElement());
		for(Position<E> child : t.children(p)) {
			printPreorder(t, child, d+1);
		}
	}
	/*
	 * 这个就有点略屌了！带标签形式的
	   Electronics R’Us
	     1 R&D
	     2 Sales
	       2.1 Domestic
	       2.2 International
	         2.2.1 Canada
	         2.2.2 S. America
	 */
	public static <E> void printPreorderLabeled(AbstractTree<E> t, Position<E> p, ArrayList<Integer> path) {

		int d = path.size();
		//是print()，不是 println()哦
		System.out.print(space(2*d));
		for(int j=0; j<d; j++) {
			System.out.print(j==d-1 ? " " : ".");
		}
		//终于到你了，元素内容！
		System.out.println(p.getElement());
		/*
		 * 准备处理当前position的children，
		 * 它的孩子节点标签要追加一个数字符号，从1开始,
		 * 采用这种追回方式，而不是new一个list出来可以省好多空间和时间
		 */
		path.add(1);
		for(Position<E> child : t.children(p)) {
			printPreorderLabeled(t, child, path);
			//下一个孩子的标签尾数要加1，不是么！
			path.set(d, path.get(d)+1);
		}
		/*
		 * 遍历完了这一级，把自己擅自在list后面追加的元素去掉，因为遍历
		 * 当前节点的兄弟节点时，是同级关系，不用追加
		 */
		path.remove(d);
	}

	/*
	 * 加几个空格字符前缀，突出层次
	 */
	public static String space(int n) {
		return repeat2(' ', n);
	}
	/*
	 * 工具方法
	 */
	public static String repeat2(char c, int n) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<n; i++) {
			sb.append(c);
		}
		return sb.toString();
	}
}
