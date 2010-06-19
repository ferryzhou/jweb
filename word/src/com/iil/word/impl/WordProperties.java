package com.iil.word.impl;

import java.util.*;

import com.iil.util.ObjectPrinter;
/**
 * ���ʵ����ԡ�����
 * Ƶ�� = 5��
 * ���� = ���ʣ�
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