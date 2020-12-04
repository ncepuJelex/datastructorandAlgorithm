package com.jel.tech.learn.ch05;

public class Fibonacci {

    /*
     * 如方法名所示，这个递归很糟糕！
     */
    public static long fibonacciBad(int n) {
        if (n <= 1)
            return n;
        return fibonacciBad(n - 1) + fibonacciBad(n - 2);
    }

    /*
     * 让{Fn,Fn−1}绑在一起,using the convention F−1(F负1，不是F减1) = 0
     */
    public static long[] fibonacciGood(int n) {
        if (n <= 1) {
            long[] answer = {n, 0};
            return answer;
        }
        long[] temp = fibonacciGood(n - 1);
        long[] answer = {temp[0] + temp[1], temp[0]};
        return answer;
    }

    public static void main(String[] args) {
        final int limit = 50;

        System.out.println("The good...");
        for (int n = 0; n < limit; n++)
            System.out.println(n + ": " + fibonacciGood(n)[0]);

        System.out.println();
        System.out.println("The bad...");
        for (int n = 0; n < limit; n++)
            System.out.println(n + ": " + fibonacciBad(n));
        /*
         * 这下面的输出好慢啊，几秒才打印出一行，而上面的good早就执行完了
         */
    }
}
