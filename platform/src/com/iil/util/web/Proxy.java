package com.iil.util.web;

import java.net.*;
import java.io.*;

/**
 * 设置代理类
 * @author Ferry
 * @version 1.0
 */
public class Proxy {
	
    /**
     * 设置代理函数
	 * @param host 代理服务器的地址
	 * @param port 使用的端口
	 * @throws IOException 如果在设置过程中出现错误就抛出此异常
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