package com.iil.util.web;

import java.util.*;
import java.io.*;
import java.net.*;

import org.w3c.dom.*;

import com.iil.util.IO;
import com.iil.util.Lists;
import com.iil.util.xml.*;


/**
 * HTTPWrapper包
 * @author Ferry
 * @version 1.0
 */
public class HTMLWrapper {
	
	public HTMLWrapper(File file) throws IOException{
		doc = loadDocument(new FileInputStream(file));
	}
	
	public HTMLWrapper(HttpClient httpClient, String url) throws IOException{
		load(httpClient, url, null);
	}

	/**
	 * HTMLWrapper的构造函数
	 * @param url 需要获取的网页的url地址
	 * @throws IOException
	 */
	public HTMLWrapper(String url) throws IOException{
		load(null, url, null);
	}
	
	public HTMLWrapper(String url, String postData) throws IOException{
		load(null, url, postData);
	}

	public HTMLWrapper(HttpClient hc, String url, String postData) throws IOException{
		load(hc, url, postData);
	}

	public static Document getDOM(String url, boolean toAbsoluteURL) throws IOException {
		HTMLWrapper hw = new HTMLWrapper(url);
		Document dom = hw.getDOM();
		if (toAbsoluteURL) {
			DataCleaner.toAbsoluteURL(dom, hw.getURL());
		}
		return dom;
	}
	
	public static Document getDOM(String url) throws IOException {
		return getDOM(url, true);
	}
	
	public static Document getDOM(File file) throws IOException {
		HTMLWrapper hw = new HTMLWrapper(file);
		return hw.getDOM();
	}
	

	private void load(HttpClient httpClient, String url, String postData) throws IOException {
		//System.out.println ("connect " + url + " ......");
		HttpClient hc = new JavaHttpClient();
		if (httpClient != null) hc = httpClient;
		this.httpClient = hc;
		hc.connect(url, postData);
		newURL = hc.getURL();
		doc = loadDocument(hc.getInputStream());
		host = (new URL(url)).getHost();
		downloads++;
		//System.out.println ("connect num................" + downloads);		
	}
	
	private Document loadDocument(InputStream in) throws IOException{
		//cache first
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		IO.readWriteAll(in, buffer);
		
		InputStream is = new ByteArrayInputStream(buffer.toByteArray());
		Document doc = HTML2DOM.getDOM(is);
		is.close();
		
		String enc = getEncoding(doc);
		if (enc == null) enc = httpClient.getEncoding();
		if (enc != null && !enc.toLowerCase().equals("gb2312") && !enc.toLowerCase().equals("iso-8859-1")) {
			//System.out.println("html encoding: " + enc);
			try {
				is = new ByteArrayInputStream(buffer.toByteArray());
				doc = HTML2DOM.getDOM(is, enc);
				is.close();
			}catch(UnsupportedEncodingException e) {
				System.out.println (e);
			}catch(IOException e) {
				System.out.println (e);
			}
		}
		return doc;
	}
	
	private String getEncoding(Document doc) {
		Node n = DOMInfoExtractor.locateNode(doc, "//meta[contains(@content, 'charset')]");
		if (n == null) return null;
		String s = DOMInfoExtractor.extractString(n, "@content");
		AttributeList al = AttributeList.getAttributeList(s, ";", "=");
		String value = al.get("charset");
		return value;
	}
	
	public HttpClient getHttpClient() {
		return this.httpClient;
	}
	
	/*===========================================================*/
	/**
     * 可能包含重复的链接。因为链接文本可能不同。
     * 返回绝对路径。并且是去掉重定向、锚后的链接。
	 * @return 返回所有的链接列表
	 */
	public List getAllLinks() {
		List links = new LinkedList();
		List elements = DOMInfoExtractor.extractNodesByAttribute(doc, "href");
		Iterator iter = elements.iterator();
		while (iter.hasNext()) {
			Link l = getLink((Element)iter.next(), newURL);//可能重定向了。
			if (l != null) links.add(l); 
		}
		return links;
	}
	
	/**
	 * 获取所有的html的网页链接
	 * @return 返回网页列表
	 */
	public List getAllHTMLPageLinks() {
		return Lists.filter(getAllLinks(), LinkFilters.getHTMLPageLinkFilter());
	}
	
	/**
	 * 获取所有的html的网页url
	 * @return 返回网页url列表
	 */
	public List getAllHTMLPageURLs() {
		return getURLs(getAllHTMLPageLinks());
	}
	
	/**
	 * 获取所有相同站点的多有html网页链接
	 * @return 链接的列表
	 * @exception MalformedURLException 获取时出现错误就抛出此异常
	 */
	public List getAllSameSiteHTMLPageLinks() {
		List links = new LinkedList();
		try {
			String host = new URL(getURL()).getHost();
			Iterator iter = getAllHTMLPageLinks().iterator();
			while (iter.hasNext()) {
				Link link = (Link)iter.next();
				if (isAtSameHost(host, link.getURL())) links.add(link);
			}
			return links;
		}catch (MalformedURLException e) {
			System.out.println (e);
			return links;
		}
	}
	
	/**
	 * 判断是否属于相同的站点
	 * @param host 需要判断的host 
	 * @param url 需要判断的url
	 * @return 如果是返回true，如果否返回false
	 */
	private boolean isAtSameHost(String host, String url) {
		try {
			String host2 = (new URL(url)).getHost();
			if (host.equals(host2)) return true;
			else return false;
		}catch(MalformedURLException e) {
			return false;
		}
	}
	
	/**
	 * 获取网页上所有与自己处在相同站点的所有网页的链接
	 * @return 返回链接列表
	 */
	public List getAllSameSiteHTMLPageURLs() {
		return getURLs(getAllSameSiteHTMLPageLinks());
	}
	/*===========================================================*/
	
	/**
	 * 获取网页链接
	 * @return 返回newURL对象
	 */
	public String getURL() {
		return newURL;
	}

	/**
	 * 获取网页所在站点信息
	 * @return host参数
	 */
	public String getHost() {
		return host;
	}
	
	public String getTitle() {
		return DOMInfoExtractor.extractString(getDOM().getDocumentElement(), "//title");
	}
	
	public String getContent() {
		return (new HTMLDOMPrinter()).print(getDOM());
	}
	
	/**
	 * 获取dom
	 * @return 返回doc参数
	 */
	public Document getDOM() {
		return doc;
	}
	/**
     * 将links链表转化为url链表。
	 * @param links 链接的列表
	 * @return 返回url链接的列表
	 */
	private List getURLs(List links) {
		List urls = new LinkedList();
		Iterator iter = links.iterator();
		while (iter.hasNext()) {
			String url = ((Link)iter.next()).getURL();
			if (!urls.contains(url)) urls.add(url);
		}
		return urls;
	}
	
	/**
	 * 从a节点获取Link
     * 有的a不存在text！该死的广告。
     * a href: http://bj.classad.sina.com.cn/hairdressing/index.shtml
     * 还有href =" javascript:xxx"
	 * @param e 需要获得链接的元素
	 * @param rootURL 根网页节点
	 * @return 相应的链接
	 */
	private Link getLink(Element e, String rootURL) {
		String href = e.getAttribute("href");
		if (href == null) return null;
		String absURL = URLUtil.toAbsoluteURL(rootURL, href);
		if (absURL == null) return null;
		String url = URLUtil.trim(absURL);
		NodeList nl = e.getChildNodes();
		if (nl.getLength() == 0) {
			//System.out.println (a.getNodeName() + " href: " + href);
			return new Link(url, "");
		}
		String text = DOMInfoExtractor.extractString(e, ".");
		return new Link(url, text);
	}
	
	/**
	 * 获取所有图片链接
	 * @return null
	 */
	public List getAllImageLinks() {
		return null;
	}
	
	public static int downloads = 0;
	/**
	 * 网页所在站点
	 */
	private String host;
	
	
	private String newURL;
	
	private HttpClient httpClient;
	/**
	 * 网页的dom
	 */
	private Document doc;
}