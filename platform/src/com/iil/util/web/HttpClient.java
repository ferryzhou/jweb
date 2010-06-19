package com.iil.util.web;

import java.io.*;

/**
 * Http�ͻ��ˡ���������վ�㣬��ȡ��ҳ��
 */
public interface HttpClient {	

	public void connect(String httpURL) throws IOException;
		
	public void connect(String httpURL, String postData) throws IOException;
	
	public String getURL();

	public InputStream getInputStream() throws IOException;
	
	public String getEncoding();
	
}
