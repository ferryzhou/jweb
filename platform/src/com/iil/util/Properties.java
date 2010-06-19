package com.iil.util;

import java.util.*;
import java.io.*;

import org.w3c.dom.*;

import com.iil.util.xml.*;

/**
 * ����xml��Properties��
 * 
 * ��ʽΪ
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
	 * ���뺯������������XML�ļ����룬ת����Properties����
	 * @param xmlfile ��Ҫ�����XML�ļ�����
	 * @throws IOException ���һ���ļ����ǺϷ���XML�ļ����׳����쳣
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
	 * ��ȡkey��Ӧ������ֵ
	 * @param key ��Ҫ��ȡ����ֵ����������
	 * @return ��������ֵ�����û���ҵ�key���Ƶ����Ծͷ���null
	 */
	/**
     * @return null if not contains key
     */
	public String getProperty(String key) {
		return (String)properties.get(key);
	}

	/**
	 * ���Ա�
	 */
	private Map properties = new HashMap();
}
