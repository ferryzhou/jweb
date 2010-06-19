package com.iil.word;

/**
 * һ���򵥵ĵ��ʷָ���������������ĸ����һ�����ʡ��������ַ���Ϊ���ʡ�
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
