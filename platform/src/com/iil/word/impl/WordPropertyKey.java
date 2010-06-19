package com.iil.word.impl;

/**
 * 单词的属性的键。比如频度、词性。
 * 类型安全枚举。
 */
public class WordPropertyKey {
	
	private WordPropertyKey(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String toString() {
		return name;
	}
	
	public static final WordPropertyKey Frequnece = new WordPropertyKey("频度");
}
