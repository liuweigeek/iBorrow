package com.zhinang.iborrow.util;

/**
 * 分页工具类
 * 
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * 
	 * @param targetUrl
	 *            目标地址
	 * @param totalNum
	 *            总记录数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public static String getPagination(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
		long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		if (totalPage == 0) {
			return "未查询到数据";
		} else {
			StringBuffer pageCode = new StringBuffer();
			if (currentPage == 1) {
				pageCode.append("<li class='page-item disabled'><a class='page-link' tabindex='-1'>首页</a></li>");
			} else {
				pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=1&" + param
						+ "' class='page-link' tabindex='-1'>首页</a></li>");
			}
			if (currentPage == 1) {
				pageCode.append("<li class='page-item disabled'><a class='page-link'>上一页</a></li>");
			} else {
				pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + (currentPage - 1) + "&" + param
						+ "' class='page-link'>上一页</a></li>");
			}
			for (int i = currentPage - 2; i <= currentPage + 2; i++) {
				if (i < 1 || i > totalPage) {
					continue;
				}
				if (i == currentPage) {
					pageCode.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
				} else {
					pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + i + "&" + param
							+ "' class='page-link'>" + i + "</a></li>");
				}
			}
			if (currentPage == totalPage) {
				pageCode.append("<li class='page-item disabled'><a class='page-link'>下一页</a></li>");
			} else {
				pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + (currentPage + 1) + "&" + param
						+ "' class='page-link'>下一页</a></li>");
			}
			if (currentPage == totalPage) {
				pageCode.append("<li class='page-item disabled'><a class='page-link'>尾页</a></li>");
			} else {
				pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + totalPage + "&"
						+ param + "' class='page-link'>尾页</a></li>");
			}
			return pageCode.toString();
		}
	}

	public static String getPaginationNoParam(String targetUrl, long totalNum, int currentPage, int pageSize) {
		long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		if (totalPage == 0) {
			return "未查询到数据";
		} else {
			StringBuffer pageCode = new StringBuffer();
			pageCode.append(
					"<li class='page-item'><a href='" + targetUrl + "?currentPage=1' class='page-link'>首页</a></li>");
			if (currentPage > 1) {
				pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + (currentPage - 1)
						+ "' class='page-link'>上一页</a></li>");
			}
			for (int i = currentPage - 2; i <= currentPage + 2; i++) {
				if (i < 1 || i > totalPage) {
					continue;
				}
				if (i == currentPage) {
					pageCode.append("<li class='page-item'>" + i + "</li>");
				} else {
					pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + i
							+ "' class='page-link'>" + i + "</a></li>");
				}
			}
			if (currentPage < totalPage) {
				pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + (currentPage + 1)
						+ "' class='page-link'>下一页</a></li>");
			}
			pageCode.append("<li class='page-item'><a href='" + targetUrl + "?currentPage=" + totalPage
					+ "' class='page-link'>尾页</a></li>");
			return pageCode.toString();
		}
	}
}
