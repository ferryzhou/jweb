package com.iil.word.impl;

/**
 * ���ʵ����Եļ�������Ƶ�ȡ����ԡ�
 * ���Ͱ�ȫö�١�
 */
public class WordPropertyKey {
	
	private WordPropertyKey(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String toString() {
		return name;
	}
	
	public static final WordPropertyKey Frequnece = new WordPropertyKey("Ƶ��");
}
