package com.jel.tech.learn.ch12;

import java.util.Arrays;
import java.util.Comparator;

import com.jel.tech.learn.ch06.LinkedQueue;
import com.jel.tech.learn.ch06.Queue;

/**
 * 合并排序
 *
 * @author jelex.xu
 * @date 2017年11月27日
 */
public class MergeSort {

    /*
     * 把s1和s2中的元素合并到s中,这个方法是工具方法，对【数组】合并排序中的最后一步操作
     */
    public static <K> void merge(K[] s1, K[] s2, K[] s, Comparator<K> comp) {
        /*
         * i表示s1中已经copy到s中的元素个数，
         * j表示s2中已经copy到s中的元素个数，
         * i+j就表示当前要往s中存入元素的下标了
         */
        int i = 0, j = 0;
        while (i + j < s.length) {
            /*
             * 情况1：如果s2到尾了，那不用说，直接把s1中所有元素拼接到s后面即可;
             * 情况2：s2没到尾，在s1没到尾时，并且当前s1和s2当前要添加的元素比较大小，s1中的元素要小的话，
             * 那就把s1中的元素存进s中，并且s1中指针往前移
             */
            if (j == s2.length || (i < s1.length && comp.compare(s1[i], s2[j]) < 0)) {
                s[i + j] = s1[i++];
            } else {
                s[i + j] = s2[j++];
            }
        }
    }

    /*
     *一个完整的合并排序算法——对【数组】排序
     */
    public static <K> void mergeSort(K[] s, Comparator<K> comp) {

        int n = s.length;
        //不到2个元素，排个毛线序啊！
        if (n < 2) return;
        /*
         * 步骤1：divide
         */
        int mid = n >>> 1;
        //没想到还有这个方法！复制一部分数组元素
        K[] s1 = Arrays.copyOfRange(s, 0, mid);
        K[] s2 = Arrays.copyOfRange(s, mid, n);
        /*
         * 步骤2：conquer(with recursion)
         */
        mergeSort(s1, comp);
        mergeSort(s2, comp);
        /*
         * 步骤3：merge results
         */
        merge(s1, s2, s, comp);
    }

    //========上面是对数组合并排序，下面对链表数据结构合并排序=====================
    /*
     *合并工具方法，注意和最上面的合并方法的不同
     */
    public static <K> void merge(Queue<K> s1, Queue<K> s2, Queue<K> s, Comparator<K> comp) {
        while (!s1.isEmpty() && !s2.isEmpty()) {
            if (comp.compare(s1.first(), s2.first()) < 0) {
                s.enqueue(s1.dequeue());
            } else {
                s.enqueue(s2.dequeue());
            }
        }
        while (!s1.isEmpty()) {
            s.enqueue(s1.dequeue());
        }
        while (!s2.isEmpty()) {
            s.enqueue(s2.dequeue());
        }
    }

    /*
     * 对链表数据结构的列表数据合并排序
     */
    public static <K> void mergeSort(Queue<K> s, Comparator<K> comp) {
        int n = s.size();
        if (n < 2) return;
        //divide
        Queue<K> s1 = new LinkedQueue<>();
        Queue<K> s2 = new LinkedQueue<>();
        while (s1.size() < n / 2) {
            s1.enqueue(s.dequeue());
        }
        while (!s.isEmpty()) {
            s2.enqueue(s.dequeue());
        }
        //conquer with recursion
        mergeSort(s1, comp);
        mergeSort(s2, comp);
        //merge results
        merge(s1, s2, s, comp);
    }

    /*
     * 自底而上（非递归实现的）合并排序的工具方法：步骤3
     */
    public static <K> void merge(K[] in, K[] out, Comparator<K> comp, int start, int inc) {

        int end1 = Math.min(start + inc, in.length);
        int end2 = Math.min(start + 2 * inc, in.length);
        int x = start;
        int y = start + inc;
        int z = start;
        while (x < end1 && y < end2) {
            if (comp.compare(in[x], in[y]) < 0) {
                out[z++] = in[x++];
            } else {
                out[z++] = in[y++];
            }
        }
        if (x < end1) {
            System.arraycopy(in, x, out, z, end1 - x);
        } else if (y < end2) {
            System.arraycopy(in, y, out, z, end2 - y);
        }
    }

    /*
     * 非递归实现的合并排序
     */
    public static <K> void mergeSortBottomUp(K[] orig, Comparator<K> comp) {

        int n = orig.length;
        K[] src = orig;
        K[] dest = (K[]) new Object[n];
        K[] temp;
        for (int i = 1; i < n; i *= 2) {
            for (int j = 0; j < n; j += 2 * i) {
                merge(src, dest, comp, j, i);
            }
            //交换
            temp = src;
            src = dest;
            dest = temp;
        }
        if (orig != src) {
            System.arraycopy(src, 0, orig, 0, n);
        }
    }

    public static void main(String[] args) {

        Integer[] orig = new Integer[]{19, 2, 5, 59, 11, 20};

        mergeSortBottomUp(orig, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        System.out.println(Arrays.toString(orig));
    }
}
