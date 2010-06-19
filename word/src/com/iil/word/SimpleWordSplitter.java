package com.iil.word;

/**
 * 一个简单的单词分割器。将连续的字母视作一个单词。其他的字符作为单词。
 *
 */
public class SimpleWordSplitter implements WordSplitter {
	
	public void readString(String s) {
		this.s = s.trim();
		fromIndex = 0;
	}
	
	public boolean hasMoreWords() {
		return fromIndex < s.length();
	}
	
	public String nextWord() {
		StringBuffer sb = new StringBuffer();
		char c = s.charAt(fromIndex);
		if (!isLetter(c)) {
			fromIndex++;
			return c + "";
		}
		while (isLetter(c)) {
			sb.append(c);
			fromIndex++;
			if (fromIndex < s.length()) {
				c = s.charAt(fromIndex);
			} else c = (char)-1;
		}
		return sb.toString();
	}
	
	public boolean isLetter(char c) {
		return (c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A');
	}
	
	private int fromIndex = 0;
	private String s = "";
}
