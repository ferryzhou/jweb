package com.iil.util.web;

/**
 * ���ڹؼ��ֵ���ҳ���ӹ�����
 * @author Ferry
 * @version 1.0
 */
public class WordBasedLinkFilter implements LinkFilter {
	
	/**
	 * WordBasedLinkFilter�Ĺ��캯��
	 * @param key ��Ҫʹ�õĹؼ���
	 */
	public WordBasedLinkFilter(String key) {
		this.key = key;
	}
	
	public boolean accept(Object o) {
		if (!(o instanceof Link)) throw new IllegalArgumentException();
		Link l = (Link)o;
		if (l.getText().equals(key)) return true;
		return false;
	}
	
	/**
	 * ��Ҫʹ�õĹؼ���
	 */
	private String key;
}