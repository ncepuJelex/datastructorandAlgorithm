package com.jel.tech.learn.ch06;

import java.util.Scanner;

/**
 * 判断一段html代码是否在标签上合法
 * @author jelex.xu
 * @date 2017年10月5日
 */
public class HTMLMatcher {

	public static boolean isHTMLMatched(String html) {
		Stack<String> buf = new LinkedStack<>();
		int j = html.indexOf('<');
		while(j != -1) {
			int k = html.indexOf('>', j);
			if(k == -1) {
				return false;
			}
			String tag = html.substring(j+1, k);

			//这是开始标签，存进栈
			if(!tag.startsWith("/")) {
				buf.push(tag);
			}
			else {
				if(buf.isEmpty()) {
					return false;
				}
				if(!tag.substring(1).equals(buf.pop())) {
					return false;
				}
			}

			//改变j的位置
			j = html.indexOf('<', k+1);
		}
		//栈空了，才是正常嘀！
		return buf.isEmpty();
	}

	private final static String example = ""
	    + "<body>" + "\n"
	    + "<center>" + "\n"
	    + "<h1> The Little Boat </h1>" + "\n"
	    + "</center>" + "\n"
	    + "<p> The storm tossed the little" + "\n"
	    + "boat like a cheap sneaker in an" + "\n"
	    + "old washing machine.  The three" + "\n"
	    + "drunken fishermen were used to" + "\n"
	    + "such treatment, of course, but" + "\n"
	    + "not the tree salesman, who even as" + "\n"
	    + "a stowaway now felt that he" + "\n"
	    + "had overpaid for the voyage. </p>" + "\n"
	    + "<ol>" + "\n"
	    + "<li> Will the salesman die? </li>" + "\n"
	    + "<li> What color is the boat? </li>" + "\n"
	    + "<li> And what about Naomi? </li>" + "\n"
	    + "</ol>" + "\n"
	    + "</body>";

	  /**
	   * Test the text given as standard input as html.
	   * If command line argument is given, preforms test on example from the book.
	   */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("输入html：");
			Scanner sc = new Scanner(System.in);
			String html = "";
			while (true) {
				String tmp = sc.nextLine();
				if (tmp.equals("exit")) {
					break;
				}
				html.concat(tmp);
			}
			sc.close();
			// String input = new Scanner(System.in).useDelimiter("\\A").next();
			// // read entire input at once
			if (isHTMLMatched(html))
				System.out.println("The input file is a matched HTML document.");
			else
				System.out.println("The input file is not a matched HTML document.");
		} else { // use prepared example
			if (!isHTMLMatched(example)) {
				System.out.println("Error on example");
			} else {
				System.out.println("OK!");
			}
		}
		/*
		 * running result:
		 * 输入html：
			<body>
			<center>
			<h1> The Little Boat </h1> </center>
			<p> The storm tossed the little boat like a cheap sneaker in an old washing machine. The three drunken fishermen were used to such treatment, of course, but
			not the tree salesman, who even as a stowaway now felt that he
			had overpaid for the voyage. </p> <ol>
			<li> Will the salesman die? </li> <li> What color is the boat? </li> <li> And what about Naomi? </li> </ol>
			</body>
			exit
			The input file is a matched HTML document.
		 */
	}
}
