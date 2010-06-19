package com.iil.util.web;



/**
 * �ṩһЩ���õ�LinkFilter
 * ����HTMLPageLinkFilter, ImageLinkFilter�ȵȡ�
 * @author Ferry
 * @version 1.0
 */
public class LinkFilters{
	
	/**
	 * ��ȡһ��HTML��ҳ���ӵĹ�����
	 * @return ����һ��HTML��ҳ���ӵĹ�����
	 * @see LinkFilters
	 */
	public static LinkFilter getHTMLPageLinkFilter() {
		return new SuffixLinkFilter(HTML_SUFFIX);
	}
	
	/**
	 * ��ȡһ��Image��ҳ���ӵĹ�����
	 * @return ����һ��Image��ҳ���ӵĹ�����
	 */
	public static LinkFilter getImageLinkFilter() {
		return new SuffixLinkFilter(IMG_SUFFIX);
	}
	
	/**
	 * ��ȡһ�����ڹؼ��ֵ���ҳ���ӹ�����
	 * @param key ��Ҫʹ�õĹؼ���
	 * @return ����һ�����ڹؼ��ֵ���ҳ���ӹ�����
	 */
	public static LinkFilter getWordBasedLinkFilter(String key) {
		return new WordBasedLinkFilter(key);
	}
	
	/**
	 * ��������Ҫʹ�õ�HTML��ʶ
	 */
	public static String[] HTML_SUFFIX = {"", "htm", "html", "php", "asp", "jsp", "cgi", "shtml"};
	
	/**
	 * ��������Ҫʹ�õ�Image��ʶ
	 */
	public static String[] IMG_SUFFIX = {"jpg", "gif"};
}
