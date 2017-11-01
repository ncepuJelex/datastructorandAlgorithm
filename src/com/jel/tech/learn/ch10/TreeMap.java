package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.Comparator;

import com.jel.tech.learn.ch07.Position;
import com.jel.tech.learn.ch08.LinkedBinaryTree;
import com.jel.tech.learn.ch09.Entry;

/**
 * 使用二叉树实现的排序map,从这里开始会慢慢引出平衡树
 * @author jelex.xu
 * @date 2017年10月31日
 * @param <K>
 * @param <V>
 */
public class TreeMap<K, V> extends AbstractSortedMap<K,V> {

	/**
	 * 平衡二叉树，在该TreeMap类还不会使用到它自身的功能，
	 * 只是它所继承的LinkedBinaryTree<Entry<K,V>>中的功能
	 */
	protected static class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>> {

	}
	//底层的数据结构为平衡二叉树
	protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

	public TreeMap() {
		super();
		tree.addRoot(null);
	}
	public TreeMap(Comparator<K> c) {
		super(c);
		//创建根节点
		tree.addRoot(null);
	}

	 // Some notational shorthands for brevity (yet not efficiency)
	 protected Position<Entry<K,V>> root() { return tree.root(); }
	 protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) { return tree.parent(p); }
	 protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) { return tree.left(p); }
	 protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) { return tree.right(p); }
	 protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) { return tree.sibling(p); }
	 protected boolean isRoot(Position<Entry<K,V>> p) { return tree.isRoot(p); }
	 protected boolean isExternal(Position<Entry<K,V>> p) { return tree.isExternal(p); }
	 protected boolean isInternal(Position<Entry<K,V>> p) { return tree.isInternal(p); }
	 protected void set(Position<Entry<K,V>> p, Entry<K,V> e) { tree.set(p, e); }
	 protected Entry<K,V> remove(Position<Entry<K,V>> p) { return tree.remove(p); }

	@Override
	public Entry<K, V> firstEntry() {
		if(isEmpty()) {
			return null;
		}
		return treeMin(root()).getElement();
	}

	/*
	 * 找到最小的元素的位置
	 */
	private Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		Position<Entry<K,V>> walk = p;
		while(isInternal(walk)) {
			walk = this.left(walk);
		}
		return this.parent(walk);
	}
	@Override
	public Entry<K, V> lastEntry() {
		//判空处理
		if(isEmpty()) {
			return null;
		}
		//最后一个元素是最下层的最右边，所以从根节点开始往右孩子方向一直遍历到最后即可！
		return treeMax(root()).getElement();
	}

	@Override
	public Entry<K, V> ceilingEntry(K k) throws IllegalArgumentException {
		checkKey(k);
		Position<Entry<K,V>> p = this.treeSearch(root(), k);
		//查找到了非叶子节点，那正好可以返回！
		if(isInternal(p)) {
			return p.getElement();
		}
		//往上找px，如果px是一个左孩子节点，那它的父节点就是要的结果
		while(!isRoot(p)) {
			if(p == left(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	@Override
	public Entry<K, V> floorEntry(K k) throws IllegalArgumentException {
		checkKey(k);
		Position<Entry<K,V>> p = this.treeSearch(root(), k);
		//如果找到了k对应的元素，那好办！
		if(isInternal(p)) {
			return p.getElement();
		}
		/*
		 * 没找到k对应的元素的话，那就从下往上找（直到根节点为止），
		 * 如果p是父亲节点的右孩子，那这个父节点就是floorEntry值，
		 * 否则再往上找，直到元素是一个右孩子节点，然后返回右孩子的
		 * 父亲节点，因为父节点比它右孩子子树中的所有节点值都要小
		 */
		while(!this.isRoot(p)) {
			if(p == this.right(parent(p))) {
				return this.parent(p).getElement();
			}
			else {
				p = this.parent(p);

			}
 		}
		return null;
	}

	@Override
	public Entry<K, V> lowerEntry(K k) throws IllegalArgumentException {
		checkKey(k);
		Position<Entry<K,V>> p = this.treeSearch(this.root(), k);
		//如果p和它的左孩子节点都是非叶子节点，那它的左孩子就是我们要找的结果！
		if(isInternal(p) && isInternal(left(p))) {
			return left(p).getElement();
		}
		//不管是因为没查找到p还是因为p的左孩子是叶子节点，都得费些工夫喽！
		/*
		 * 从p节点往上找，找到这么一种场景：节点x是一个右孩子节点！
		 * 那它的父节点就是要找的结果！
		 */
		while(!isRoot(p)) {
			if(p == right(parent(p))) {
				return p.getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	@Override
	public Entry<K, V> higherEntry(K k) throws IllegalArgumentException {
		checkKey(k);
		Position<Entry<K,V>> p = this.treeSearch(root(), k);
		//如果p的右孩子节点为非叶子节点，那就是你了，孩子！
		if(isInternal(p) && isInternal(right(p))) {
			return right(p).getElement();
		}
		//往上找，如果px是一个左孩子节点，那它的父节点就是要找的结果
		while(!isRoot(p)) {
			if(p == left(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K from, K to) throws IllegalArgumentException {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
		//起始得小于结束，这是常识！
		if(compare(from, to) < 0) {
			subMapRecurse(from, to, root(), buffer);
		}
		return buffer;
	}
	/*
	 * subMap工具递归方法
	 */
	private void subMapRecurse(K from, K to, Position<Entry<K, V>> p, ArrayList<Entry<K, V>> buffer) {
		//从叶子节点开始查找，疯了！没法搞！必须为非叶子节点！
		if(isInternal(p)) {
			//1.如果起始key比左孩子大，那就从右孩子开始遍历吧！
			if(compare(left(p).getElement(), from) < 0) {
				subMapRecurse(from, to, right(p), buffer);
			}
			//2.否则得去左子树走趟了
			else {
				subMapRecurse(from, to, left(p), buffer);
				//3.如果发现当前遍历节点p上的key比结束key要小，那就加进buffer
				if(compare(p.getElement(), to) < 0) {
					buffer.add(p.getElement());
				}
			}
		}
	}
	/*
	 * 所有的叶子节点都是null，这样做是有道理的！
	 */
	@Override
	public int size() {
		return (tree.size()-1)>>2;
	}

	@Override
	public V get(K key) {
		//校验key
		checkKey(key);
		//从根节点遍历查找key的position
		Position<Entry<K,V>> p = this.treeSearch(root(), key);
		//不解的是：查找遍历怎么破坏平衡树的结构了？ @ TODO
		rebalanceAccess(p);
		//查找返回的是叶子节点，表示没找到，返回空
		if(isExternal(p)) {
			return null;
		}
		//返回位置上元素的值
		return p.getElement().getValue();
	}
	/*
	 * 平衡树被破坏了，需要修复，这里不处理，因为这里不搞平衡树，
	 * 留作勾子方法，放在这，让继承此类的子类去覆盖实现去！
	 */
	protected void rebalanceAccess(Position<Entry<K,V>> p) {
	}
	@Override
	public V put(K key, V value) {
		checkKey(key);
		Entry<K,V> newEntry = new MapEntry<>(key, value);
		Position<Entry<K,V>> p = this.treeSearch(root(), key);
		if(isExternal(p)) {
			//p位置上设置元素为newEntry，并设置p位置上的左右节点为null
			this.expandExternal(p, newEntry);
			//hook for subclass,here没卵用
			this.rebalanceAInsert(p);
			//新插入的，以前此处元素为空，所以返回null
			return null;
		}
		//覆盖之前的value
		else {
			V old = p.getElement().getValue();
			this.set(p, newEntry);
			this.rebalanceAccess(p);
			return old;
		}
	}
	protected void rebalanceAInsert(Position<Entry<K, V>> p) {
	}
	/*
	 * 可对照包中的node with at most one child.png和node having two children.png
	 * 图片理解操作
	 */
	@Override
	public V remove(K key) {
		checkKey(key);
		Position<Entry<K,V>> p = this.treeSearch(root(), key);
		//没找到要删除的key，好办！
		if(isExternal(p)) {
			this.rebalanceAccess(p);
			return null;
		} else {
			V old = p.getElement().getValue(); //这是最后要返回的值，先保存下来
			/*
			 * 如果将要被删除的节点p有2个孩子节点(2个孩子节点都不为空，即都不是叶子节点)，
			 * 从左孩子子树中找出最大的key的位置pmax，
			 * 把pmax上的元素赋值给p位置中，然后删除pmax即可！（用大腿想pmax最多只有1个孩子节点是internal的），
			 * 把问题转换成删除最多只有一个孩子是非叶子节点的pmax就好办多了！
			 */
			if(isInternal(left(p)) && isInternal(right(p))) {
				Position<Entry<K,V>> pmax = this.treeMax(left(p));
				this.set(p, pmax.getElement());
				p = pmax; //p指向了新的待删除的最多只有一个孩子节点的position了
			}
			//找出p（pmax）的孩子（是叶子节点的那个）【其实如果要有非叶子节点的话，只可能是左孩子节点了，不然pmax就是pmax.rightChild了】
			Position<Entry<K,V>> leaf = isExternal(left(p)) ? left(p) : right(p);
			Position<Entry<K,V>> sibling = this.sibling(leaf); //找出兄弟节点
			/*
			 * 删除p节点和它的是叶子节点的孩子节点，
			 * 不过要从下往上删除哦！
			 */
			this.remove(leaf);
			this.remove(p);
			//hook,为什么是对sibling处理呢？ @ TODO
			this.rebalanceDelete(sibling);
			return old;
		}
	}

	protected void rebalanceDelete(Position<Entry<K, V>> p) {
	}
	/*
	 * 以p为根的子树中找出key最大的position
	 */
	private Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		Position<Entry<K,V>> walk = p;
		/*
		 * 只要没到达叶子节点，就一直往右孩子节点下钻
		 */
		while(isInternal(walk)) {
			walk = this.right(walk);
		}
		//到这里，说明walk是叶子节点了，我们要的是它的父节点哦！
		return this.parent(walk);
	}
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		for(Position<Entry<K,V>> p : tree.inorder()) {
			//记得过滤叶子节点
			if(isInternal(p)) {
				buffer.add(p.getElement());
			}
		}
		return buffer;
	}

	/*
	 * 在叶子节点插入新元素时的辅助方法
	 */
	private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
		//设置p位置上的元素为entry
		tree.set(p, entry);
		/*
		 * 设置p位置上元素的左、右孩子节点为null,系统借助了叶子节点为null这种设计，
		 * 叫做 sentinel leaves（哨兵叶子节点）
		 */
		tree.addLeft(p, null);
		tree.addRight(p, null);
	}
	/*
	 * 从树的p位置子树中查找相应key的位置position
	 */
	private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
		//到了叶子节点，没找到，返回和key最相关的position
		if(isExternal(p)) {
			return p;
		}
		//比较
		int comp = compare(key, p.getElement());
		//找到了！！！
		if(comp == 0) {
			return p;
		}
		else if(comp < 0) {
			//左边找
			return treeSearch(left(p), key);
		}
		else {
			//右边找
			return treeSearch(right(p), key);
		}
	}
}
