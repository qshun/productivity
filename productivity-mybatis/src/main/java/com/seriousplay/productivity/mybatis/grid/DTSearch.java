package com.seriousplay.productivity.mybatis.grid;

/**
 * @author changqingshun
 */
public class DTSearch {
	private boolean regex;
	private String value;
	public String getValue() {
		return value;
	}
	public boolean isRegex() {
		return regex;
	}
	public void setRegex(boolean regex) {
		this.regex = regex;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
