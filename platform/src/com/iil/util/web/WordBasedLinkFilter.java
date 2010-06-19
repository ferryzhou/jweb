package com.iil.util.web;

/**
 * 基于关键字的网页链接过滤器
 * @author Ferry
 * @version 1.0
 */
public class WordBasedLinkFilter implements LinkFilter {
	
	/**
	 * WordBasedLinkFilter的构造函数
	 * @param key 需要使用的关键字
	 */
	public WordBasedLinkFilter(String key) {
		this.key = key;
	}
	
	public boolean accept(Object o) {
		if (!(o instanceof Link)) throw new IllegalArgumentException();
		Link l = (Link)o;
		if (l.getText().equals(key)) return true;
		return false;
	}
	
	/**
	 * 需要使用的关键字
	 */
	private String key;
}