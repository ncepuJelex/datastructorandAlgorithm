package com.jel.tech.learn.ch06;

/**
 * 约瑟夫环问题
 *
 * @author jelex.xu
 * @date 2017年10月5日
 */
public class Josephus {

    /*
     * 死亡游戏挑战！
     */
    public static <E> E josephus(CircularQueue<E> queue, int k) {
        if (queue.isEmpty()) return null;
        //最后的那一个是胜者，也是生者，残忍吧！
        while (queue.size() > 1) {
            for (int i = 0; i < k - 1; i++) {
                queue.rorate(); //循环数k-1个人，然后下一个出局，斩首
            }
            E e = queue.dequeue();
            System.out.println("  " + e + " be dead!");
        }
        //you are the winner! also the only man alive.
        return queue.dequeue();
    }

    /*
     * 组建环
     */
    public static <E> CircularQueue<E> buildQueue(E[] data) {
        CircularQueue<E> queue = new LinkedCircularQueue<E>();
        for (int i = 0; i < data.length; i++) {
            queue.enqueue(data[i]);
        }
        return queue;
    }

    public static void main(String[] args) {

        String[] a1 = {"Alice", "Bob", "Cindy", "Doug", "Ed", "Fred"};
        String[] a2 = {"Gene", "Hope", "Irene", "Jack", "Kim", "Lance"};
        String[] a3 = {"Mike", "Roberto"};
        System.out.println("First winner is " + josephus(buildQueue(a1), 3));
        System.out.println("Second winner is " + josephus(buildQueue(a2), 10));
        System.out.println("Third winner is " + josephus(buildQueue(a3), 7));
		/*
		 * running result:
		   	  Cindy be dead!
			  Fred be dead!
			  Doug be dead!
			  Bob be dead!
			  Ed be dead!
			First winner is Alice
			  Jack be dead!
			  Irene be dead!
			  Lance be dead!
			  Gene be dead!
			  Kim be dead!
			Second winner is Hope
			  Mike be dead!
			Third winner is Roberto
		 */
    }
}
