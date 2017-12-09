package com.zhinang.iborrow.util;

import java.security.MessageDigest;

public class MD5Util {

	public static void main(String[] args) {
		System.out.println(encoder("TYczjykj2017"));
	}

	public static String encoder(String s) {
		return encoder(s, "UTF-8");
	}

	public static String encoder(String s, String charset) {
		try {
			byte[] btInput = s.getBytes(charset);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
