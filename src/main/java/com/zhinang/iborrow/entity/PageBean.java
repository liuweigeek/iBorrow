package com.zhinang.iborrow.entity;

//分页
public class PageBean {
	//页数
	private int page;
	//每页显示数量
	private int pageSize;
	//当前页首项索引
	@SuppressWarnings("unused")
	private int start;

	public PageBean(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return ((this.page - 1) * this.pageSize);
	}
}