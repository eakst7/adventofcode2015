package org.ek.advent;

import org.apache.commons.codec.digest.DigestUtils;

public class Day4_2 {

	public static boolean match(String sum) {
		return sum.startsWith("000000");
	}
	
	public static void main(String[] args) {
		String key = "iwrupvqb";
		
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			if (match(DigestUtils.md5Hex(key + Integer.toString(i)))) {
				System.out.println(i);
				System.exit(1);
			};
		}
	}

}
