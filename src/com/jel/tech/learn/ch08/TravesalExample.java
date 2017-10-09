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

	/*
	 * 计算文件树的大小，之前研究过的那个，
	 * 这个也算是一个后序遍历问题，但是这里的postorder不适用，
	 * 因为需要children返回值给parent,而postorder方法中children没有
	 * 返回值给parent
	 */
	public static int diskSpace(Tree<Integer> t, Position<Integer> p) {
		int subTotal = p.getElement();
		for(Position<Integer> child : t.children(p)) {
			subTotal += diskSpace(t, child);
		}
		return subTotal;
	}

	/*
	 * P(T)= p.getElement()+"("+P(T1)+", "+ ··· +", "+P(Tk)+")"
	 * 具体一个例子是（无换行）：
	 * Electronics R’Us (R&D, Sales (Domestic, International (Canada, S. America,
	 * Overseas (Africa, Europe, Asia, Australia))),
	 * Purchasing, Manufacturing (TV, CD, Tuner))
	 */
	public static <E> void parenthesize(Tree<E> t, Position<E> p) {

		System.out.print(p.getElement());
		if(t.isInternal(p)) {
			boolean firstTime = true;
			for(Position<E> child : t.children(p)) {
				System.out.print(firstTime ? "(" : ", ");
				firstTime = true;
				parenthesize(t, child);
			}
			System.out.print(")");
		}
	}

	/*
	 * To maintain an accurate value for the x-coordinate as the traversal
	 * proceeds, the method must be provided with the value of x that should
	 * be assigned to the leftmost node of the current subtree,
	 * and it must return to its parent a revised value of x that is appropriate
	 * for the first node drawn to the right of the subtree.
	 * 参照chapter08.jpg图片理解，二叉树才有哦！
	 */
	public static <E extends Geometric> int layout(BinaryTree<E> t, Position<E> p, int d, int x) {
		if(t.left(p) != null) {
			x = layout(t, t.left(p), d+1, x); //深度要加1，已访问过的数量不变
		}
		/*
		 * setX()之后再递增x,表示下一个访问p的x值会加1,
		 * 想想，也是，因为是inorder遍历的，先左孩子，再父亲，再右孩子，所以
		 * x的值是先经过左孩子计算出来的，然后才是当前节点，然后是右孩子，
		 * 对右孩子(具体是在以右孩子为根节点的子树中，最左边的那个节点)而言，
		 * x值应该比它的父亲的x属性值大1
		 */
		p.getElement().setX(x++);
		p.getElement().setY(d); //深度在自己这层是不会变的，只是在孩子节点上会变（加1）
		if(t.right(p) != null) {
			x = layout(t, t.right(p), d+1, x);
		}
		return x;
	}

	// fake interface for geometric layout problem
	public interface Geometric {

		public void setX(int x);
		public void setY(int y);
	}
}
