package com.iil.word.impl;

import java.util.*;

/**
 * 词典。
 */
public class Dict{
	
	public Dict() {
	}
	
	/**
     * 检查词典中是否包含这个词。
     * @return true 如果包含。否则返回false;
     */
	public boolean contain(String word) {
		if (dict.get(word) == null) {	//word not exist
			return false;
		}
		return true;
	}
	
	public WordProperties getProperties(String word) {
		return (WordProperties)dict.get(word);
	}
	
	public void put(String word, WordProperties properties) {
		dict.put(word, properties);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Set set = dict.entrySet();
		Iterator iter = set.iterator();
		int i = 0;
		while (iter.hasNext() && i < 10) {
			Map.Entry entry = (Map.Entry)iter.next();
			sb.append(entry.getKey() + " : " + entry.getValue() + "\r\n");
			i++;
		}
		return sb.toString();
	}
	
	/**
     * key - String; //word
     * value - WordProperties;
     */
	private Map dict = new HashMap();
}
