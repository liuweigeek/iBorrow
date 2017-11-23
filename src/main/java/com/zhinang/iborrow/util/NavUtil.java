package com.zhinang.iborrow.util;

public class NavUtil {
	public static String genNavCode(String subName) {
		StringBuffer navCode = new StringBuffer();
		navCode.append("您现在的位置：");
		navCode.append("<a href='index.jsp'>首页</a>&nbsp;");
		navCode.append("&gt; ");
		navCode.append(subName);
		return navCode.toString();
	}
}