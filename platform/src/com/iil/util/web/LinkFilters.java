package com.iil.util.web;



/**
 * 提供一些常用的LinkFilter
 * 比如HTMLPageLinkFilter, ImageLinkFilter等等。
 * @author Ferry
 * @version 1.0
 */
public class LinkFilters{
	
	/**
	 * 获取一个HTML网页链接的过滤器
	 * @return 返回一个HTML网页链接的过滤器
	 * @see LinkFilters
	 */
	public static LinkFilter getHTMLPageLinkFilter() {
		return new SuffixLinkFilter(HTML_SUFFIX);
	}
	
	/**
	 * 获取一个Image网页链接的过滤器
	 * @return 返回一个Image网页链接的过滤器
	 */
	public static LinkFilter getImageLinkFilter() {
		return new SuffixLinkFilter(IMG_SUFFIX);
	}
	
	/**
	 * 获取一个基于关键字的网页链接过滤器
	 * @param key 需要使用的关键字
	 * @return 返回一个基于关键字的网页链接过滤器
	 */
	public static LinkFilter getWordBasedLinkFilter(String key) {
		return new WordBasedLinkFilter(key);
	}
	
	/**
	 * 过滤器需要使用的HTML标识
	 */
	public static String[] HTML_SUFFIX = {"", "htm", "html", "php", "asp", "jsp", "cgi", "shtml"};
	
	/**
	 * 过滤器需要使用的Image标识
	 */
	public static String[] IMG_SUFFIX = {"jpg", "gif"};
}
