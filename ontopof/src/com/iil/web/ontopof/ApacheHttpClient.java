package com.iil.web.ontopof;

//import com.iil.util.web.HttpClient;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

import com.iil.util.web.HTMLWrapper;
import com.iil.util.xml.DOMTreeView;

class ApacheHttpClient implements com.iil.util.web.HttpClient {
	
	public void connect(String httpURL) throws IOException {
		GetMethod method = new GetMethod(httpURL);
	method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.5) Gecko/20041107 Firefox/1.0");
	method.setRequestHeader("Accept-Language", "en-us,en;q=0.5");
	method.setRequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException("Method failed: " + method.getStatusLine());
			}

			responseBody = method.getResponseBody();

			//System.out.println(new String(responseBody));
			encoding = method.getResponseCharSet();
			
			finalURL = method.getURI().toString();

		} catch (HttpException e) {
			throw new IOException("Fatal protocol violation: " + e.getMessage());
		}
	}
		
	public void connect(String httpURL, String postData) throws IOException {
		connect(httpURL);
	}
	
	public String getURL() {
		return finalURL;
	}

	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(responseBody);
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	private HttpClient client = new HttpClient();
	private String encoding = "GB2312"; //default
	private String finalURL = null;
	private byte[] responseBody;
	
	public static void main(String[] args) {
		try {
 	    	String url = "http://news.google.com/news?ned=cn&topic=t";
    		HTMLWrapper hw = new HTMLWrapper(new ApacheHttpClient(), url);
    		System.out.println (hw.getContent());
   			org.w3c.dom.Document dom = hw.getDOM();
	   		DOMTreeView.view(dom);
   		}catch (IOException e) {
   			e.printStackTrace();
   		}

    }
}
