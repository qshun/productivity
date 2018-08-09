package com.seriousplay.productivity.mybatis.grid;

/**
 * @author changqingshun
 */
public class DTColumn {
	private String data;
	private String name;
	private boolean orderable;
	private DTSearch search;
	private boolean searchable;
	public String getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public DTSearch getSearch() {
		return search;
	}

	public boolean isOrderable() {
		return orderable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	public void setSearch(DTSearch search) {
		this.search = search;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
}
