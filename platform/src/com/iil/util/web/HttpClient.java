package com.iil.util.web;

import java.io.*;

/**
 * Http客户端。负责连接站点，获取网页。
 */
public interface HttpClient {	

	public void connect(String httpURL) throws IOException;
		
	public void connect(String httpURL, String postData) throws IOException;
	
	public String getURL();

	public InputStream getInputStream() throws IOException;
	
	public String getEncoding();
	
}
