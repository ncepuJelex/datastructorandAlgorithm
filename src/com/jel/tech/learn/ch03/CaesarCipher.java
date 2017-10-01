package com.jel.tech.learn.ch03;

/**
 * 凯撒密码：通过移位达到掩人耳目的效果！
 * @author jelex.xu
 * @date 2017年10月1日
 */
public class CaesarCipher {
	/*
	 * 加密/解密数组“尺子”
	 */
	protected char[] encoder = new char[26];
	protected char[] decoder = new char[26];

	/*
	 * rotation:移位的大小，加解密移位一正一反！
	 */
	public CaesarCipher(int rotation) {
		for(int i=0; i<26; i++) {
			encoder[i] = (char) ('A' + (i+rotation) % 26);
			//加上26是为了不会产生负数
			decoder[i] = (char) ('A' + (i-rotation + 26) % 26);
		}
	}

	/*
	 * 加密
	 */
	public String encrypt(String msg) {
		return transform(msg, encoder);
	}
	/*
	 * 解密
	 */
	public String decrypt(String secret) {
		return transform(secret, decoder);
	}
	/*
	 * 加解密处理的核心处理方法
	 */
	private String transform(String msg, char[] coder) {
		char[] cs = msg.toCharArray();
		for(int k=0; k<cs.length; k++) {
			//这个小工具只处理大写字母信息
			if(Character.isUpperCase(cs[k])) {
				//算出这个字符在“尺子”中的坐标，然后对号取出相应的字符即可！
				int j = cs[k] - 'A';
				cs[k] = coder[j];
			}
		}
		return new String(cs);
	}

	public static void main(String[] args) {
		CaesarCipher cipher = new CaesarCipher(3);
		System.out.println("Encryption code = " + new String(cipher.encoder));
		System.out.println("Decryption code = " + new String(cipher.decoder));
		String message = "THE EAGLE IS IN PLAY; MEET AT JOE'S.";
		String coded = cipher.encrypt(message);
		System.out.println("Secret: " + coded);
		String answer = cipher.decrypt(coded);
		System.out.println("Message: " + answer);
		/*
		 * running result:
		    Encryption code = DEFGHIJKLMNOPQRSTUVWXYZABC
			Decryption code = XYZABCDEFGHIJKLMNOPQRSTUVW
			Secret: WKH HDJOH LV LQ SODB; PHHW DW MRH'V.
			Message: THE EAGLE IS IN PLAY; MEET AT JOE'S.
		 */
	}
}
