package com.iil.word.impl;

import java.util.*;

/**
 * �ʵ䡣
 */
public class Dict{
	
	public Dict() {
	}
	
	/**
     * ���ʵ����Ƿ��������ʡ�
     * @return true ������������򷵻�false;
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
