package com.iil.word;

/**
 * ���ʷָ�����
 * ��һ���ַ����ָ�Ϊ���ʡ�
 */
public interface WordSplitter {
	
	/**
     * ��ȡ���ָ���ַ�����
     */
	public void readString(String s);
	
	public boolean hasMoreWords();
	
	public String nextWord();
}
