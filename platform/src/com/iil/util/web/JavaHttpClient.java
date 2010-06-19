package com.iil.util.web;

import java.net.*;
import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import com.iil.util.xml.DOMInfoExtractor;
import com.iil.util.IO;

/**
 * Http客户端。负责连接站点，获取网页。
 *
 * 特性：
 * 支持Cookie
 * 支持Referer
 * 支持POST和GET
 * 
 * 不支持验证
 * @author Ferry
 * @version 1.0
 */
public class JavaHttpClient implements HttpClient{	
	
	public JavaHttpClient() {
	}

	public void connect(String httpURL) throws IOException{
		connect(httpURL, null);
	}
	
	/**
     * 处理连接
     * 分析头，获取Cookie
     * 设置referer
	 * @param httpURL
	 * @throws IOException
	 * @see HttpClient.java
	 * @see HttpClient
	 * 20:58:04 write this comment 
	 */
	public void connect(String httpURL, String postData) throws IOException{
		
		URL url = new URL(httpURL);
		con = (HttpURLConnection)url.openConnection();

		if (postData != null) {
			con.setDoOutput(true);
			con.setRequestMethod("POST");
		}
		referer = "http://music.ustc.edu.cn/album.php";
		if (referer != null) {
			con.setRequestProperty("Referer", referer);
		}
		
		if (cookies.size() != 0) {
			con.setRequestProperty("Cookie", cookies.marshal(";", "="));
		}
		
		//con.setFollowRedirects(false);
		con.setInstanceFollowRedirects(false);
		con.connect();
		
		if (postData != null) {			
			OutputStream out = con.getOutputStream();
			PrintWriter pw = new PrintWriter(out);
			pw.write(postData);
			pw.flush();
			pw.close();
		}

		referer = httpURL;
		cache();
		getCookies();

		String redirect = getRedirect(httpURL);
		//System.out.println ("redirect: " + redirect);
		if (redirect != null && !referer.equals(redirect)) {
			referer = redirect;
			connect(referer);
			return;
		}
		//System.out.println ("referer: " + referer);
		//System.out.println ("contentType: " + con.getContentType());
		//System.out.println (com.iil.util.ObjectPrinter.print(con.getHeaderFields()));
		
	}
	
	private String getRedirect(String httpURL) throws IOException{
		String redirect = URLUtil.toAbsoluteURL(httpURL, con.getHeaderField("Location"));
		if (redirect != null) return redirect;
		InputStream is = getInputStream();
		Document doc = HTML2DOM.getDOM(is);
		List metas = DOMInfoExtractor.extractNodesByName(doc, "meta");
		Iterator iter = metas.iterator();
		while (iter.hasNext()) {
			Element meta = (Element)iter.next();
			String httpequiv = meta.getAttribute("http-equiv");
			if (httpequiv != null && httpequiv.toLowerCase().equals("refresh")) {
				String content = meta.getAttribute("content");
				int i = content.indexOf("http://");
				if (i != -1) return content.substring(i);
				i = content.indexOf("url");
				if (i != -1) {
					i = content.indexOf("=");
					String url = content.substring(i + 1).trim();
					return (new URL(new URL(getURL()), url)).toString();
				}
			}
		}
		is.close();
		return null;
	}
	
	public String getURL() {
		return referer;
	}
	
	private void getCookies() {
		String key;
		for (int i = 1; (key = con.getHeaderFieldKey(i)) != null; i++) {
			if (key.equals("Set-Cookie")) {
				String value = con.getHeaderField(i);
				cookies.addList(AttributeList.getAttributeList(value, ";", "="));
			}
		}
		getCookiesFromContent();
	}
	
	private void getCookiesFromContent() {
		String content = getContent();
		String COOKIESTRING = "cookie";
		int index = 0;
		while ((index = content.indexOf(COOKIESTRING, index)) != -1) {
			index += COOKIESTRING.length();
			int singleIndex = content.indexOf("'", index);
			int doubleIndex = content.indexOf("\"", index);
			if (singleIndex != -1 ) {
				if (doubleIndex != -1) {
					if (singleIndex < doubleIndex) {     //cookies are in '';
						if (singleIndex > index + 4) continue;
						int next = content.indexOf("'", singleIndex + 1);
						String value = content.substring(singleIndex + 1, next);
						cookies.addList(AttributeList.getAttributeList(value, ";", "="));
					} else {                             //cookies are in ""
						if (doubleIndex > index + 4) continue;
						int next = content.indexOf("'", doubleIndex + 1);
						String value = content.substring(doubleIndex + 1, next);
						cookies.addList(AttributeList.getAttributeList(value, ";", "="));
					}
				} else {
						if (singleIndex > index + 4) continue;
						int next = content.indexOf("'", singleIndex + 1);
						String value = content.substring(singleIndex + 1, next);
						cookies.addList(AttributeList.getAttributeList(value, ";", "="));
				}
			} else {
				if (doubleIndex != -1) {
					if (doubleIndex > index + 4) continue;
					int next = content.indexOf("'", doubleIndex + 1);
					String value = content.substring(doubleIndex + 1, next);
					cookies.addList(AttributeList.getAttributeList(value, ";", "="));
				}
			}
		}
	}
	
	private void cache() throws IOException {
		if (buffer != null) buffer.close();
		buffer = new ByteArrayOutputStream();
		IO.readWriteAll(con.getInputStream(), buffer);
		//System.out.println (buffer);
	}
	
	public String getContent() {
		return buffer.toString();
	}
	
	public InputStream getInputStream() throws IOException{
		return new ByteArrayInputStream(buffer.toByteArray());
	}
	
	/**
     * 待完成
     * encoding可以从两处得知。一个是回应头的信息。一个是网页中的信息。网页中的信息优先。
     * 如果取过网页中的信息后，需要重新连接一遍。
     */
	public String getEncoding() {
		
		return "gb2312";
	}
	
	private String guessEncoding() {
		return null;
	}
	
	HttpURLConnection con;
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private String encoding;
	private String referer;
	private AttributeList cookies = new AttributeList();
}

/**
 * ieee的header中有两个Set-Cookie！
 * 
 */