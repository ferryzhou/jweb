package com.iil.util.web;

import java.io.*;
import java.util.*;

import com.iil.util.*;

/**
 * ��ҳ·��
 * ����ָʾ·������ָ��λ��
 * @author Ferry
 * @version 1.0
 */
public class WebRouter {
	
	/**
	 * ͨ��·����ȡ������Ҫ����ҳ��URL����
	 * @param initialURL ��ʼ��ҳ��·��
	 * @param path ��Ҫ��·��
	 * @return �����������ҳ��URL��û���ҵ��ͷ���null
	 * @throws IOException �����κδ�����׳�IOException
	 */
	public static String locate(String initialURL, String path) throws IOException{
		String[] steps = path.split("/");
		String cur = initialURL;
		for (int i = 0; i<steps.length; i++) {
			cur = hop(cur, steps[i]);
			if (cur == null) return null;
		}
		return cur;
	}
	
	/**
	 * ��������exitname�ؼ��ֵ�����
	 * @param url ���URL��ַ�ַ���
	 * @param exitName
	 * @return ���û�ҵ����ڣ�����null��Ӧ�÷��ؾ���URL��
	 * @throws IOException
	 */
	public static String hop(String url, String exitName) throws IOException{
		System.out.println ("hop " + url + " " + exitName);
		Filter linkFilter = LinkFilters.getWordBasedLinkFilter(exitName);
		HTMLWrapper hw = new HTMLWrapper(url);
		List allLinks = hw.getAllHTMLPageLinks();
		//System.out.println (allLinks);
		List links = Lists.filter(allLinks, linkFilter);
		if (links.size() != 0) {
			String exitURL =  ((Link)links.get(0)).getURL();
			System.out.println ("find exit: " + exitURL);
			return exitURL;
		}
		System.out.println ("null");
		return null;
	}
}
