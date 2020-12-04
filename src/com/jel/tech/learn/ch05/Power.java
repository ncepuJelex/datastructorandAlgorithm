package com.jel.tech.learn.ch05;

public class Power {

    /*
     * 这种方式效率太低了！
     */
    public static double powerSlow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        return x * powerSlow(x, n - 1);
    }

    public static double power(double x, int n) {
        if (n == 0) return 1;
        else {
            double result = power(x, n / 2) * power(x, n / 2);
            if (n % 2 == 1) {
                result *= x;
            }
            return result;
        }
    }

    public static void main(String[] args) {

        final double EPSILON = 0.0000000001;
        final int BASE = 3;

        int answer = 1;
        for (int n = 0; n < 20; n++) {
            if (Math.abs(answer - powerSlow(BASE, n)) > EPSILON)
                System.out.println("Problem with slow power(" + BASE + "," + n + ")");
            if (Math.abs(answer - Power.power(BASE, n)) > EPSILON)
                System.out.println("Problem with fast power(" + BASE + "," + n + ")");
            answer *= BASE;
        }
    }
}
