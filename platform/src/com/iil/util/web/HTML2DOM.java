package com.iil.util.web;

import org.w3c.dom.*;
import java.io.*;
import org.w3c.tidy.Tidy;
import org.w3c.tidy.Configuration;

import com.iil.util.IO;
import com.iil.util.xml.*;

/**
 * ��ȡHTML��DOM�ṹ��
 * ʹ��jTidy��
 * ����Ҫ������ת��Ϊunicode���롣
 * @author Ferry
 * @version 1.0
 */
public class HTML2DOM {
	
	/**
	 * ��Ҫ��ȡdom������ҳurl��ַ
	 * @param url ��Ҫ��ȡ��ҳ��url��ַ
	 * @return ����dom��
	 * @throws IOException
	 */
	public static Document getDOM(String url) throws IOException{
		HttpClient hc = new JavaHttpClient();
		hc.connect(url);
		System.out.println ("HTML2DOM connect " + url + " ...");
		String enc = hc.getEncoding();
		return getDOM(hc.getInputStream(), enc);
	}
	
	/**
	 * ���������������Ϣ����dom���Ľṹ
	 * @param in ��Ҫת����������
	 * @return ����dom��
	 * @throws IOException
	 */
	public static Document getDOM(InputStream in) throws IOException{
		return getDOM(in, "gb2312");
	}
	
	/**
	 * ���������������Ϣ������dom���Ľṹ
	 * @param in ��Ҫת����������
	 * @param inenc ��Ҫ�Ĳ�����Ϣ 
	 * @return ����dom��
	 * @throws IOException
	 * @see Tidy
	 */
	public static Document getDOM(InputStream in, String inenc) throws IOException{
		Tidy tidy = new Tidy();
		//tidy.setXmlOut(true);
		tidy.setCharEncoding(Configuration.UTF8);
		//tidy.setErrfile("err.txt");
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		//File file = new File("temp" + (i++) + ".xml");
		OutputStream os = null;//new FileOutputStream(file);
		Document doc = (new DOMFilter()).trimDocument(tidy.parseDOM(IO.inputStreamBridge(in, inenc, "utf-8"), os));
		//copy to xerces impl
		Element root = doc.getDocumentElement();
		if (root == null) throw new IOException("html document is empty!");
		Document newdoc = DocumentFactory.createDocument(root.getNodeName());
		Node croot = DOMUtil.importNode(newdoc, root, true);
		Element old = newdoc.getDocumentElement();
		old.getParentNode().replaceChild(croot, old);
		newdoc.normalize();
		//file.delete();
		return newdoc;
	}
	
	private static int i;
}
