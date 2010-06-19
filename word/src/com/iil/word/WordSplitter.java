package com.iil.word;

/**
 * 单词分割器。
 * 将一个字符串分割为单词。
 */
public interface WordSplitter {
	
	/**
     * 读取待分割的字符串。
     */
	public void readString(String s);
	
	public boolean hasMoreWords();
	
	public String nextWord();
}
