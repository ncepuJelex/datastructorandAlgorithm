package com.jel.tech.learn.ch07;

/**
 * move-to-front,我们希望用户访问了一种资源后很快会再次访问它，所以我们
 * 把刚访问的资源元素直接提到最前面去，这种启发式的方式，更新排名时简单多了！
 * 只是在要查找前k名的时候，费点劲，特别是找到前25%的元素时候，这里采用的方法是：
 * 复制一份元素列表，放进临时list中，然后重复搜索k遍，每次找出一个访问次数最大的那个，
 * 再删除它以便下次接着找
 *
 * @author jelex.xu
 * @date 2017年10月6日
 */
public class FavoritesListMTF<E> extends FavoritesList<E> {

    /*
     * 因为是直接提到最前面，所以重写moveUp()方法
     */
    @Override
    protected void moveUp(Position<Item<E>> p) {
        if (p != list.first()) {
            list.addFirst(list.remove(p));
        }
    }

    /*
     * 复杂在这里了，如类开始所述
     */
    @Override
    public Iterable<E> getFavorites(int k) throws IllegalArgumentException {
        if (k < 0 || k > size()) {
            throw new IllegalArgumentException("invalid: " + k);
        }
        /*
         * 复制到临时列表中，再遍历查找
         */
        PositionList<Item<E>> temp = new LinkedPositionList<Item<E>>();
        for (Item<E> item : list) {
            temp.addLast(item);
        }
        //保存结果
        PositionList<E> result = new LinkedPositionList<>();
        /*
         * 找出最大的那个，保存它，再从临时列表中删除，如此循环k次
         */
        for (int j = 0; j < k; j++) {
            Position<Item<E>> highPos = temp.first();
            Position<Item<E>> walk = temp.after(highPos);
            while (walk != null) {
                if (count(walk) > count(highPos)) {
                    highPos = walk;
                }
                walk = temp.after(walk);
            }
            /*
             * 此时highPos是最大的元素
             */
            result.addLast(value(highPos));
            //
            temp.remove(highPos);
        }
        return result;
    }

    // test usage
    public static void main(String[] args) {
        test(new FavoritesListMTF<Character>());
    }
}
