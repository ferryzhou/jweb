package com.iil.util.web;

import java.io.*;
import java.util.*;

import com.iil.util.*;

/**
 * 网页路由
 * 根据指示路径到达指定位置
 * @author Ferry
 * @version 1.0
 */
public class WebRouter {
	
	/**
	 * 通过路径获取最终需要的网页的URL链接
	 * @param initialURL 初始网页的路径
	 * @param path 需要的路径
	 * @return 返回所求的网页的URL，没有找到就返回null
	 * @throws IOException 出现任何错误就抛出IOException
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
	 * 跳到符合exitname关键字的链接
	 * @param url 入口URL地址字符串
	 * @param exitName
	 * @return 如果没找到出口，返回null，应该返回绝对URL。
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
