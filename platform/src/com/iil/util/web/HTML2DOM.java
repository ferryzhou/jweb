package com.iil.util.web;

import org.w3c.dom.*;
import java.io.*;
import org.w3c.tidy.Tidy;
import org.w3c.tidy.Configuration;

import com.iil.util.IO;
import com.iil.util.xml.*;

/**
 * 获取HTML的DOM结构。
 * 使用jTidy。
 * 首先要将编码转换为unicode编码。
 * @author Ferry
 * @version 1.0
 */
public class HTML2DOM {
	
	/**
	 * 需要获取dom树的网页url地址
	 * @param url 需要获取网页的url地址
	 * @return 返回dom树
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
	 * 将输入流输入的信息构成dom树的结构
	 * @param in 需要转化的输入流
	 * @return 返回dom树
	 * @throws IOException
	 */
	public static Document getDOM(InputStream in) throws IOException{
		return getDOM(in, "gb2312");
	}
	
	/**
	 * 将输入流输入的信息构建成dom树的结构
	 * @param in 需要转化的输入流
	 * @param inenc 需要的参数信息 
	 * @return 返回dom树
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
