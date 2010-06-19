package com.iil.util.xml;

import java.io.*;


import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;

/**
 * 对指定的URI对应的网页建立相应的DOM树
 * @author Ferry
 * @version 1.0
 */
public class DOMBuilder {
	
	/**
	 * 建立DOM树的函数
	 * @param uri 需要建立DOM树的网页相应的URI地址
	 * @return 返回包含DOM树结果的Document结构
	 * @throws IOException 在生成DOM树的过程中发生任何错误就抛出此异常
	 * @see DOMParser
	 */
	public static Document build(String uri) throws IOException{
        DOMParser parser = new DOMParser();
        try {
            //parser.setFeature("http://xml.org/sax/features/validation", false);
            //parser.setFeature("http://xml.org/sax/features/namespaces", false);
            //parser.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);
            //parser.setFeature("whitespace-in-element-content", false);

            parser.parse(uri);
            Document doc = parser.getDocument();
            return doc;
            
        } catch (org.xml.sax.SAXException e) {
            //e.printStackTrace();
            System.out.println("Error in parsing: " + e.getMessage());
            //return null;
            throw new IOException("Error in parsing: " + e.getMessage());
        }
		
	}

}