package com.iil.util.xml;

import java.io.*;


import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;

/**
 * ��ָ����URI��Ӧ����ҳ������Ӧ��DOM��
 * @author Ferry
 * @version 1.0
 */
public class DOMBuilder {
	
	/**
	 * ����DOM���ĺ���
	 * @param uri ��Ҫ����DOM������ҳ��Ӧ��URI��ַ
	 * @return ���ذ���DOM�������Document�ṹ
	 * @throws IOException ������DOM���Ĺ����з����κδ�����׳����쳣
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