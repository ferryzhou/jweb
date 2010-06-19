package com.iil.util;

import java.util.*;
import java.io.*;

import org.w3c.dom.*;

import com.iil.util.xml.*;

/**
 * 基于xml的Properties。
 * 
 * 格式为
 * <?xml version = "1.0" encoding="xx">
 * <Properties>
 *   <propertykey>propertyvalue</propertykey>
 *   ...   
 * </Properties>
 * @author Ferry
 * @version 1.0
 */
public class Properties {
	
	/**
	 * 载入函数，将给定的XML文件载入，转化成Properties对象
	 * @param xmlfile 需要载入的XML文件名称
	 * @throws IOException 如果一个文件不是合法的XML文件就抛出此异常
	 * @see DOMInfoExtractor
	 */
	public void load(String xmlfile) throws IOException{
		Document doc = DOMBuilder.build(xmlfile);
		Element root = doc.getDocumentElement();
		NodeList elements = root.getElementsByTagName("*");
		for (int i = 0; i<elements.getLength(); i++) {
			Element e = (Element)elements.item(i);
			String key = e.getNodeName();
			String value = DOMInfoExtractor.getNodeString(e);
			properties.put(key, value);
		}
	}
	
	/**
	 * 获取key对应的属性值
	 * @param key 需要获取属性值的属性名称
	 * @return 返回属性值，如果没有找到key名称的属性就返回null
	 */
	/**
     * @return null if not contains key
     */
	public String getProperty(String key) {
		return (String)properties.get(key);
	}

	/**
	 * 属性表
	 */
	private Map properties = new HashMap();
}
