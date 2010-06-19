package com.iil.word.impl;

import java.util.*;
import java.io.*;

import com.iil.util.IO;

//import test.block.*;

public class WordSegmenterImpl{
	
	public WordSegmenterImpl(Dict dict) {
		this.dict = dict;
	}
	
	public List segmentSentence(String s) {
		List words = new LinkedList();
		List frags = split(s);
		Iterator iter = frags.iterator();
		while (iter.hasNext()) {
			String frag = (String)iter.next();
			//System.out.println(frag);
			if (isChineseChar(frag.charAt(0))) words.addAll(segmentChineseSentence(frag));
			else words.add(frag);
		}
		return words;
	}
	
	/**
     * ��s�ָ�ɵ��ʡ�
     */
	public List segmentChineseSentence(String s) {
		//System.out.println ("sentence: " + s);
		List words = new LinkedList();
		if (s.length() <= 2 && s.length() > 0) {
			words.add(s);
			//System.out.println (".." + s);
			return words;
		}
		int initLen = (MAX_WORD_LEN < s.length()) ? MAX_WORD_LEN : s.length();
		for (int i = initLen; i > 0; i--) {
			String word = scan(s, i);
			//System.out.println ("scan " + word);
			if (word != null) {
				//System.out.println("sentence: " + s);
				//System.out.println("word: " + word);
				int index = s.indexOf(word);
				String pre = s.substring(0, index);
				String post = s.substring(index + word.length());
				words.addAll(segmentSentence(pre));
				words.add(word);
				words.addAll(segmentSentence(post));
				break;
			}
		}
		return words;
	}
	
	/**
     * ��s��Ѱ�ҳ���Ϊlen����Ƶ�����ĵ��ʡ�
     * @return word if find any, else return null;
     */
	private String scan(String s, int len) {
		int maxFreq = -1;
		String mword = null;
		for (int i = 0; i<=s.length() - len; i++) {
			String word = s.substring(i, i + len);
			if (dict.contain(word)) {
				WordProperties wp = dict.getProperties(word);
				String freqs = (String)wp.getValue(WordPropertyKey.Frequnece);
				int freq = Integer.parseInt(freqs);
				if (freq > maxFreq) {
					maxFreq = freq;
					mword = word;
				}
			}
		}
		return mword;
	}
	
	public List segmentText(Reader r) throws IOException{
		String s = IO.getContent(r);
		return segmentSentence(s);
	}
	
	private List split(String s) {
		//System.out.println("new split");
		List l = new ArrayList();
		StringBuffer sb = new StringBuffer();
		char c;
		int from = 0;
		while (from < s.length()) {
			c = s.charAt(from++);
			int type = getType(c);
			//System.out.println("new " + c + " " + type);
			if (type == -1) continue;
			sb.append(c);
			for	(; from < s.length(); from++) {
				char tc = s.charAt(from);
				//System.out.println(tc + " ");
				//System.out.println(getType(tc));
				if (getType(tc) == type) {
					sb.append(tc);
				} else {
					//System.out.println("append " + sb.toString());
					l.add(sb.toString());
					sb = new StringBuffer();
					break;
				}
			}
			if (from == s.length()) l.add(sb.toString());			
		}
		return l;
	}
	
	private int getType(char c) {
		if (c >= 'a' && c <='z' || c >= 'A' && c <= 'Z') return 0;
		if (isChineseChar(c)) return 1;
		return -1;
	}
	
	private boolean isWhiteSpace(int c) {
		char cc = (char)c;
		return cc == ' ' || c == '\r' || c=='\n' || c=='\t';
	}
	
	private boolean isChineseChar(int c) {
		return c >= 0x4E00 && c < 0xFF00;
	}
	
	private static final int MAX_WORD_LEN = 8;
	private Dict dict;
	//private String s;
	//private List toBeSegmentedStrings = new LinkedList();
	//private List words = new LinkedList();
	
	public static void main(String[] args) throws Exception{
/*		System.out.print("load....");
		Dict dict = DictLoader.load("dict.xml");
		System.out.print("load end...");
    	WordSegmenterImpl wsi = new WordSegmenterImpl(dict);
    	List l = wsi.segmentSentence("�ͺñȺڰ��д���ҹ�յ�����");
    	System.out.println (l);
    	System.out.println (wsi.segmentSentence("������ʹ��������ͻ�ֱ�ӿ���java����������д����������˵���顣���"));
    	Reader r = new FileReader("zc.txt");
    	//System.out.println (wsi.split(r));
    	//System.out.println (wsi.segmentText(r));
    	r.close();
*/    	
    	//System.out.println ((new WordSegmenterImpl(null)).split("������ʹ��������ͻ�ֱ�ӿ���java����������д����������˵����"));
    	System.out.println((int)'��');
    	System.out.println((new WordSegmenterImpl(null)).getType('��'));
    	//FileWriter fw = new FileWriter("chinese_characters.txt");
    	for (int i = 0xFF00; i <= 0xFF90; i++) {
    		System.out.println ((char)i + ": " + i + "\r\n");
    	}
    	//fw.close();
    }
}

/**
 * 0x9FA5�����һ������
 * 0x4E00�ǵ�һ������"һ"
 * ���ְ��ʻ�������
 * ���ֱ���ַ���0xFF00��ʼ��
 */