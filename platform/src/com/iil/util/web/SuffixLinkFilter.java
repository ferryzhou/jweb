package com.iil.util.web;

import java.net.MalformedURLException;

/**
 * ����ָ����Ϣ����ҳ���ӹ�����
 * @author Ferry
 * @version 1.0
 */
public class SuffixLinkFilter implements LinkFilter {

	/**
	 * ��Ҫ�������ҳ���ӹ��˹ؼ���
	 * @param keys
	 */
	public SuffixLinkFilter(String[] keys) {
		this.keys = keys;
	}

	public boolean accept(Object o) {
		if (!(o instanceof Link))
			throw new IllegalArgumentException();
		Link l = (Link) o;
		try {
			String suffix = URLUtil.getSuffix(l.getURL());
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].equals(suffix))
					return true;
			}
			return false;
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	/**
	 * �ؼ����ַ�������
	 */
	private String[] keys;
}