package com.iil.word.impl;

import java.util.*;

import com.iil.util.ObjectPrinter;
/**
 * 单词的属性。比如
 * 频度 = 5；
 * 词性 = 名词；
 */
public class WordProperties {
	
	public WordProperties(WordPropertyKey key, Object value) {
		put(key, value);
	}
	
	public void put(WordPropertyKey key, Object value) {
		props.put(key, value);
	}
	
	public Object getValue(WordPropertyKey key) {
		return props.get(key);
	}
	
	public String toString() {
		return ObjectPrinter.print(props);
	}
	
	private Map props = new HashMap();
}