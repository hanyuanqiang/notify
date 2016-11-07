package com.app.utils;

public class PageBean {

/*
	private int page; // 第几页
	private int pageSize; // 每页记录数
	private int start;  // 起始页
*/

	private int currentPage;	//第几页
	private int pageSize;	//每页的记录数
	private int start;	//起始页


	public PageBean(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (currentPage-1)*pageSize;
	}
}
