package com.iil.web.ontopof;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
//import java.net.URI;

public class Test_ApacheHttpClient {
  
//  private static String url = "http://blogsearch.google.com/blogsearch_feeds?hl=en&q=%E7%99%BE%E5%BA%A6&ie=utf-8&num=10&output=rss";

  public static void main(String[] args) throws Exception{

		//String url = "http://news.google.com/news?hl=zh-CN&ned=cn&q=%E7%99%BE%E5%BA%A6+%E6%90%9C%E7%8B%90&btnG=%E6%90%9C%E7%B4%A2%E8%B5%84%E8%AE%AF";
		String url = "http://news.google.com/news?hl=zh-CN&ned=cn&q=百度";
		URI uri = new URI("http://news.google.com/news?hl=zh-CN&ned=cn&q=百度", false);
		System.out.println ("escaped: " + uri);
    // Create an instance of HttpClient.
    HttpClient client = new HttpClient();

    // Create a method instance.
    GetMethod method = new GetMethod(uri.toString());
    //GetMethod method = new GetMethod(url);
    
    // Provide custom retry handler is necessary
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
    		new DefaultHttpMethodRetryHandler(3, false));

	System.out.println ("params: " + method.getParams().getContentCharset());
	System.out.println ("params: " + method.getParams().getHttpElementCharset());
	System.out.println ("params: " + method.getParams().getCredentialCharset());
	System.out.println ("params: " + method.getRequestCharSet());
	System.out.println ("params: " + method.getRequestHeader("Accept-Charset"));
	HttpMethodParams p = method.getParams();
	
	Header[] h = method.getRequestHeaders();
	for (int i = 0; i < h.length; i++) {
		System.out.println ("headers: " + h[i].toString());
	}
	
	method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	System.out.println ("params: " + method.getParams().getContentCharset());
	//method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
	method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.5) Gecko/20041107 Firefox/1.0");
	method.setRequestHeader("Accept-Language", "en-us,en;q=0.5");
	method.setRequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
	
	
    try {
      // Execute the method.
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] responseBody = method.getResponseBody();

      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data
      System.out.println(new String(responseBody));
      System.out.println (method.getResponseBodyAsString());
      System.out.println (method.getResponseCharSet());

    } catch (HttpException e) {
      System.err.println("Fatal protocol violation: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Fatal transport error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Release the connection.
      method.releaseConnection();
    }  
  
  }
}