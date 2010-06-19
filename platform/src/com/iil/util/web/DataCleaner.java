package com.iil.util.web;

import org.w3c.dom.*;

import com.iil.util.xml.DOMInfoExtractor;

public class DataCleaner {
	
	/**
     * 将dom树中所有url转换成绝对url。
     */
	public static void toAbsoluteURL(Node root, String context) {
		NodeList nl = DOMInfoExtractor.locateNodes(root, ".//@href | .//@src");
		toAbsoluteURL(nl, context);
		//nl = DOMInfoExtractor.locateNodes(root, ".//@src");
		//toAbsoluteURL(nl, context);
	}
	
	private static void toAbsoluteURL(NodeList urls, String context) {
		for (int i = 0; i<urls.getLength(); i++) {
			Node n = urls.item(i);
			String url = URLUtil.toAbsoluteURL(context, n.getNodeValue());
			if (url != null) n.setNodeValue(url);
		}
	}
}