package com.jel.tech.learn.ch06;

/**
 * 利用stack的特性判断(){}[]之间的符号匹配问题
 *
 * @author jelex.xu
 * @date 2017年10月5日
 */
public class MatchDelimiters {
    // 左符号
    private static final String opening = "([{";
    // 右符号,位置和左符号一一对应
    private static final String closing = ")]}";

    public static boolean isMatched(String expression) {
        Stack<Character> buf = new LinkedStack<>();
        /*
         * 遍历表达式字符串
         */
        for (char c : expression.toCharArray()) {
            // 把左符号压栈
            if (opening.indexOf(c) != -1) {
                buf.push(c);
            }
            /*
             * 碰到右符号，此时正常的应该和栈顶字符是一对的
             */
            else if (closing.indexOf(c) != -1) {
                if (buf.isEmpty()) {
                    return false;
                }
                if (closing.indexOf(c) != opening.indexOf(buf.pop())) {
                    return false;
                }
            }
        }
        // 到最后，栈应该是空的，因为左右符号一一匹配上了
        return buf.isEmpty();
    }

    final static String[] valid = {"()(()){([()])}", "( ) ( ( ) ) {( [ ( )  ] ) } ",
            "(3) (3 + (4 - 5) ) {( [ ( )  ] ) } ", "((()(()){([()])}))", "[(5+x)-(y+z)]"};

    final static String[] invalid = {")(()){([()])}", "({[])}", "("};

    public static void main(String[] args) {

        for (String s : valid)
            if (!isMatched(s))
                System.out.println("Error evaluating valid: " + s);

        for (String s : invalid)
            if (isMatched(s))
                System.out.println("Error evaluating invalid: " + s);
        /*
         * running result:
         * 空白的控制台，没消息就是好消息！
         */
    }
}
