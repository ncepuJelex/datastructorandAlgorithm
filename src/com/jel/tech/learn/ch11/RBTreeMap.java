package com.jel.tech.learn.ch11;

import java.util.Comparator;

import com.jel.tech.learn.ch07.Position;
import com.jel.tech.learn.ch09.Entry;

/**
 * 红黑树出场！
 * FYI:上次在这里写代码是10月31号，都快一个月了，哎，
 * 一方面是买了一本书：Spring Boot2 精髓，看了前5章，
 * 然后就没接着学习数据结构和算法，但更重要的原因是：
 * 公司压榨我们，让我们每天加班到很晚，回去就11点了，
 * 加上周末还加班，所以回去根本没精力去学习了。还有我的
 * ukelele买了回来也没时间去学习，公司还美其名曰，每到年底
 * 都要打仗，什么赢的战役，傻不垃圾的，把压榨说得这么好听，臭不要脸的。
 * @author jelex.xu
 * @date 2017年11月26日
 * @param <K>
 * @param <V>
 */
public class RBTreeMap<K, V> extends TreeMap<K, V> {

	public RBTreeMap() {
		super();
	}
	public RBTreeMap(Comparator<K> comp) {
		super(comp);
	}
	/*
	 * 借助 TreeMap类中BalanceableBinaryTree子类的一个属性：aux字段，
	 * 其默认初始值是0，表示黑（这样任何新创建的leaf就是黑色的了，真巧啊！），1表示红
	 */
	private boolean isBlack(Position<Entry<K,V>> p) {
		return tree.getAux(p) == 0;
	}
	private boolean isRed(Position<Entry<K, V>> p) {
		return tree.getAux(p) == 1;
	}
	private void makeBlack(Position<Entry<K,V>> p) {
		tree.setAux(p, 0);
	}
	private void makeRed(Position<Entry<K,V>> p) {
		tree.setAux(p, 1);
	}
	private void setColor(Position<Entry<K,V>> p, boolean toRed) {
		tree.setAux(p, toRed ? 1 : 0);
	}
	/*
	 * 插入新节点时，根据红黑树规则可能需要调整
	 */
	@Override
	protected void rebalanceAInsert(Position<Entry<K, V>> p) {
		//如果新插入的节点是根节点，那好说了，不用调整！不然，哎，搞事情了
		if(!tree.isRoot(p)) {
			//新加入节点刚开始是设置为红色的
			this.makeRed(p);
			//可能有双红色节点问题，调整下
			resolveRed(p);
		}
	}
	/*
	 * 处理双红色节点问题（由新插入节点引起）
	 */
	private void resolveRed(Position<Entry<K, V>> p) {
		Position<Entry<K,V>> parent, uncle, middle, grand;
		parent = parent(p); //父节点
		//如果新插入节点(p)的父节点是黑色的，那万事大吉，不用处理
		//否则……
		if(isRed(parent)) {
			//父节点的兄弟节点，新插入节点(p)的叔叔节点
			uncle = sibling(parent);
			//1.如果uncle是黑色节点
			if(isBlack(uncle)) {
				/*
				 * 对新插入节点重新旋转构造处理下，返回按先序遍历
				 * 中间的那个节点，这里用middle接收,
				 * 把middle调整为黑色，把它的孩子节点调整为红色
				 */
				middle = restructure(p);
				makeBlack(middle);
				makeRed(left(middle));
				makeRed(right(middle));
			} else { //2.uncle是红色
				/*
				 * 把父节点和叔叔节点调整为黑色，
				 * 把祖父节点调整为红色，但是如果祖父节点是根节点（肯定是黑色），
				 * 那就不用管了
				 */
				makeBlack(parent);
				makeBlack(uncle);
				grand = parent(parent);
				if(!isRoot(grand)) {
					makeRed(grand);
					//把祖父调整成红色，可能又是双红色情况，那就
					//往上冒泡，接着调整，还搞不定你！
					resolveRed(grand);
				}
			}
		}
	}
	/*
	 *删除节点后的调整,注意哦，这里的参数p,不是删除的节点，而是
	 *represents the deepest node of the tree that was accessed
	 * during the operation.也就是顶上来的孩子节点
	 */
	@Override
	protected void rebalanceDelete(Position<Entry<K, V>> p) {
		//被删除节点是黑色节点,并且自身是红色(我们可以用逆反命题来验证，
		//如果p是红色节点，那么它的父节点不可能是红色)
		if(isRed(p)) {
			//把自己调整成黑色，这样才是真正顶上去了！(没有让删除破坏红黑树的结构)
			makeBlack(p);
		}
		//如果删除得最后只剩一个节点了，那也没那么多事情了，只可惜……
		else if(!isRoot(p)) {
			//找出领居节点
			Position<Entry<K,V>> sib = sibling(p);
			//如果邻居节点子树下有黑色节点
			if(isInternal(sib) && (isBlack(sib) || isInternal(left(sib)))) {
				//修复双黑色节点问题
				remedyDoubleBlack(p);
			}
		}
	}

	private void remedyDoubleBlack(Position<Entry<K, V>> p) {
		Position<Entry<K,V>> z = parent(p);
		Position<Entry<K,V>> y = sibling(p);
		//如果兄弟节点是黑色
		if(isBlack(y)) {
			/*
			 * case 1:The Sibling y of p is Black and has a Red Child x.
			 * The operation restructure(x) takes the node x, its parent y, and grandparent z,
			 * labels them temporarily left to right as a, b, and c,
			 * and replaces z with the node labeled b, making it the parent of the other two.
			 * We color a and c black, and give b the former color of z.
			 */
			if(isRed(left(y)) || isRed(right(y))) {
				Position<Entry<K,V>> x = isRed(left(y)) ? left(y) : right(y);
				Position<Entry<K,V>> middle = restructure(x);
				setColor(middle, isRed(z));
				makeBlack(left(middle));
				makeBlack(right(middle));
			}
			/*
			 * case 2:The Sibling y of p is Black and Both Children of y are Black,
			 * changing the color of p from double black to black and
			 * the color of y from black to red,
			 * If z is red, we color it black and the problem has been resolved,
			 * If z is black, we color it double black,
			 * thereby propagating the problem higher up the tree.
			 */
			else {
				makeRed(y);
				if(isRed(z)) {
					makeBlack(z);
				} else if(!isRoot(z)) {
					remedyDoubleBlack(z);
				}
			}
		}
		/*
		 * case3:如果兄弟节点是红色,
		 * we perform a rotation about y and z, and then recolor y black and z red,
		 * 然后又变回到case 1或者2了，
		 * Case 1 is always terminal and Case 2 will be terminal
		 * given that the parent of p is now red.
		 */
		else {
			rotate(y);
			makeBlack(y);
			makeRed(z);
			remedyDoubleBlack(p);
		}
	}



}
