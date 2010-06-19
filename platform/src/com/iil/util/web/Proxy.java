package com.iil.util.web;

import java.net.*;
import java.io.*;

/**
 * ���ô�����
 * @author Ferry
 * @version 1.0
 */
public class Proxy {
	
    /**
     * ���ô�����
	 * @param host ����������ĵ�ַ
	 * @param port ʹ�õĶ˿�
	 * @throws IOException ��������ù����г��ִ�����׳����쳣
	 */
	public static void setProxy(String host, int port) throws IOException{
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", host);
		System.setProperty("http.proxyPort", port+"");
		URL url = new URL("http://www.sina.com.cn");
		URLConnection c = url.openConnection();
		c.connect();
		//InputStream is = c.getInputStream();
		//System.out.println (is.read());
		//is.close();		
    }
}