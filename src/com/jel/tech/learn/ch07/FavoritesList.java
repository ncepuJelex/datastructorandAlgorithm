package com.jel.tech.learn.ch07;

import java.util.Iterator;

/**
 * Maintains a list of elements ordered according to access frequency
 * 维护一个按用户接触次数排序的列表，比如KTV最爱的前10首歌等等
 *
 * @author jelex.xu
 * @date 2017年10月6日
 */
public class FavoritesList<E> {

    /**
     * 把元素和接触次数绑定在一起，组合设计模式
     */
    protected static class Item<E> {
        private E value;
        private int count = 0;

        public Item(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public int getCount() {
            return count;
        }

        //又有人access了一次该元素，恭喜，递增一下！
        public void increment() {
            count++;
        }

        // debug utility
        public String toString() {
            return "(" + value + ":" + count + ")";
        }
    }

    //注意：generic type是Item<E>,而不是<E>哦！
    PositionList<Item<E>> list = new LinkedPositionList<>();

    /*
     * 工具方法，非公有
     */
    protected E value(Position<Item<E>> p) {
        return p.getElement().getValue();
    }

    protected int count(Position<Item<E>> p) {
        return p.getElement().getCount();
    }

    /*
     * 找到元素的位置
     */
    protected Position<Item<E>> findPosition(E e) {
        Position<Item<E>> walk = list.first();
        while (walk != null && !e.equals(value(walk))) {
            walk = list.after(walk);
        }
        //此时要么找到了元素返回，要么返回null
        return walk;
    }

    /*
     * 往上移动一个排名，恭喜喽！
     * 但是需要更新列表哦！按照排名reorder.
     */
    protected void moveUp(Position<Item<E>> p) {
        int cnt = count(p);
        /*
         * 从当前节点往排名更靠前的几名元素找！
         */
        Position<Item<E>> walk = p;
        while (walk != list.first() && count(list.before(walk)) < cnt) {
            walk = list.before(walk);
        }
        /*
         * 这个判断好！
         */
        if (walk != p) {
            //一箭双雕！
            list.addBefore(walk, list.remove(p));
        }
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /*
     * 有人点击这个元素啦！准备更新排名吧~
     */
    public void access(E e) {
        Position<Item<E>> p = this.findPosition(e);
        //没找到，看来是新人进来，排在最后吧！
        if (p == null) {
            p = list.addLast(new Item<E>(e));
        }
        p.getElement().increment(); //更新访问次数
        moveUp(p); //考虑更新排名
    }

    public void remove(E e) {
        Position<Item<E>> p = findPosition(e);
        if (p != null) {
            list.remove(p);
        }
    }

    /*
     * 获取最喜欢的前几名
     */
    public Iterable<E> getFavorites(int k) throws IllegalArgumentException {
        if (k < 0 || k > size()) {
            throw new IllegalArgumentException("无效：" + k);
        }
        PositionList<E> result = new LinkedPositionList<>();
        Iterator<Item<E>> iterator = list.iterator();
        for (int j = 0; j < k; j++) {
            result.addLast(iterator.next().getValue());
        }
        return result;
    }

    // the remainder of this file is for testing/debugging only
    public String toString() {
        return list.toString();
    }

    protected static void test(FavoritesList<Character> fav) {
        char[] sample = "hello. this is a test of mtf".toCharArray();
        for (char c : sample) {
            fav.access(c);
            int k = Math.min(5, fav.size());
            System.out.println("Entire list: " + fav);
            System.out.println("Top " + k + ": " + fav.getFavorites(k));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        test(new FavoritesList<Character>());
    }
}
