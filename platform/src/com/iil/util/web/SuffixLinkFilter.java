package com.iil.util.web;

import java.net.MalformedURLException;

/**
 * 基于指定信息的网页链接过滤器
 * @author Ferry
 * @version 1.0
 */
public class SuffixLinkFilter implements LinkFilter {

	/**
	 * 需要输入的网页链接过滤关键字
	 * @param keys
	 */
	public SuffixLinkFilter(String[] keys) {
		this.keys = keys;
	}

	public boolean accept(Object o) {
		if (!(o instanceof Link))
			throw new IllegalArgumentException();
		Link l = (Link) o;
		try {
			String suffix = URLUtil.getSuffix(l.getURL());
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].equals(suffix))
					return true;
			}
			return false;
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	/**
	 * 关键字字符串数组
	 */
	private String[] keys;
}