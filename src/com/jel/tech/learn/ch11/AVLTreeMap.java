package com.jel.tech.learn.ch11;

import java.util.Comparator;

import com.jel.tech.learn.ch07.Position;
import com.jel.tech.learn.ch09.Entry;

public class AVLTreeMap<K, V> extends TreeMap<K, V> {

    public AVLTreeMap() {
        super();
    }

    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /*
     * 计算tree中某position的高度（借助了aux属性值）
     */
    protected int height(Position<Entry<K, V>> p) {
        return tree.getAux(p);
    }

    /*
     * 重新计算position的高度,递归地定义计算
     */
    protected void recomputeHeight(Position<Entry<K, V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    /*
     * 判断是不是平衡position:2个孩子的position相差不超过1
     */
    protected boolean isBalanced(Position<Entry<K, V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    /*
     *找到height更大的孩子的position
     */
    protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
        //左孩子节点更高
        if (height(left(p)) > height(right(p))) {
            return left(p);
        }
        //右孩子更高
        if (height(left(p)) < height(right(p))) {
            return right(p);
        }
        //那这步了，如果p是根节点，返回任意孩子节点都无所谓了
        if (isRoot(p)) {
            return left(p);
        }
        /*
         * 为了能在restructure操作中少rotate一次，所以“故意”
         * 造成只需要rotate一次的结果
         */
        if (p == left(parent(p))) {
            return left(p);
        } else {
            return right(p);
        }
    }

    /**
     * Utility used to rebalance after an insert or removal operation. This traverses the
     * path upward from p, performing a trinode restructuring when imbalance is found,
     * continuing until balance is restored.
     * <p>
     * We perform a trinode restructuring operation if an imbalanced position is reached.
     * The upward march from p continues until we reach an ancestor with height
     * that was unchanged by the map operation, or with height that was restored
     * to its previous value by a trinode restructuring operation,
     * or until reaching the root of the tree.
     * </p>
     */
    protected void rebalance(Position<Entry<K, V>> p) {
        int oldHeight, newHeight;
        do {
            oldHeight = height(p); //restructure之前的高度
            if (!isBalanced(p)) {
                //记得xyz吗，分别代表孩子、父亲、祖父，操作是从孩子节点位置(x)开始的
                //返回的子树根节点position
                p = this.restructure(tallerChild(tallerChild(p)));
                //重新计算p的孩子节点的高度
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            //此时以p为根节点的子树是平衡状态，并且它的孩子节点的高度是重新计算过的，再重新计算它自己的高度
            recomputeHeight(p);
            //经过重新计算后，得到它的新高度
            newHeight = height(p);
            //往上走，因为一个插入和删除操作影响的可能是tree中的整条线路，都需要修复
            p = parent(p);
        } while (oldHeight != newHeight && p != null); //修复后p的高度和原来一样了(说明影响的范围不大)提前结束了，或者到根节点了也就自然彻底修复了
    }

    @Override
    protected void rebalanceAInsert(Position<Entry<K, V>> p) {
        this.rebalance(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        //如果只有最后一个节点(root)，还修复个毛啊！根本不用修复了！
        if (!isRoot(p)) {
            this.rebalance(p);
        }
    }


}
