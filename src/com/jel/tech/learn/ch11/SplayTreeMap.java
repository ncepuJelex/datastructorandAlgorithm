package com.jel.tech.learn.ch11;

import java.util.Comparator;

import com.jel.tech.learn.ch07.Position;
import com.jel.tech.learn.ch09.Entry;

/**
 * a splay operation causes more frequently accessed elements to
 * remain nearer to the root, thereby reducing the typical search times.
 * <p>
 * A splaying step consists of repeating these restructurings at x
 * until x becomes the root of T.
 * </p>
 * <p>
 * The rules that dictate when splaying is performed are as follows:
 * <pre>
 * • When searching for key k, if k is found at position p, we splay p,
 * else we splay the parent of the leaf position at which
 * the search terminates unsuccessfully.
 * • When inserting key k, we splay the newly created internal node where k
 * gets inserted.
 * • When deleting a key k, we splay the position p that is the parent
 * of the removed node;
 * </pre>
 * </p>
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年11月5日
 */
public class SplayTreeMap<K, V> extends TreeMap<K, V> {

    public SplayTreeMap() {
        super();
    }

    public SplayTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /*
     * 关键调用内部方法:流程可参看splay-*.jpg图片理解
     */
    private void splay(Position<Entry<K, V>> p) {
        //直到root
        while (!isRoot(p)) {
            Position<Entry<K, V>> parent = parent(p);
            Position<Entry<K, V>> grand = parent(parent);
            //1.zag case
            if (grand == null) {
                rotate(p);
            }
            //2.zig-zig case
            else if ((parent == left(grand)) == (p == left(parent))) {
                // move PARENT upward
                rotate(parent);
                //then move p upward
                rotate(p);
            }
            //3.zig-zag case
            else {
                //move p upward
                rotate(p);
                //move p upward again
                rotate(p);
            }
        }
    }

    @Override
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        if (isExternal(p)) {
            p = parent(p);
        }
        if (p != null) {
            splay(p);
        }
    }

    @Override
    protected void rebalanceAInsert(Position<Entry<K, V>> p) {
        splay(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        //如果删除的是根节点，那就空了，还旋转个毛线啊！
        if (!isRoot(p)) {
            splay(parent(p));
        }
    }

}
