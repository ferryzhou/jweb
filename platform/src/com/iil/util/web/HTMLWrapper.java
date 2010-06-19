package com.iil.util.web;

import java.util.*;
import java.io.*;
import java.net.*;

import org.w3c.dom.*;

import com.iil.util.IO;
import com.iil.util.Lists;
import com.iil.util.xml.*;


/**
 * HTTPWrapper��
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
	 * HTMLWrapper�Ĺ��캯��
	 * @param url ��Ҫ��ȡ����ҳ��url��ַ
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
     * ���ܰ����ظ������ӡ���Ϊ�����ı����ܲ�ͬ��
     * ���ؾ���·����������ȥ���ض���ê������ӡ�
	 * @return �������е������б�
	 */
	public List getAllLinks() {
		List links = new LinkedList();
		List elements = DOMInfoExtractor.extractNodesByAttribute(doc, "href");
		Iterator iter = elements.iterator();
		while (iter.hasNext()) {
			Link l = getLink((Element)iter.next(), newURL);//�����ض����ˡ�
			if (l != null) links.add(l); 
		}
		return links;
	}
	
	/**
	 * ��ȡ���е�html����ҳ����
	 * @return ������ҳ�б�
	 */
	public List getAllHTMLPageLinks() {
		return Lists.filter(getAllLinks(), LinkFilters.getHTMLPageLinkFilter());
	}
	
	/**
	 * ��ȡ���е�html����ҳurl
	 * @return ������ҳurl�б�
	 */
	public List getAllHTMLPageURLs() {
		return getURLs(getAllHTMLPageLinks());
	}
	
	/**
	 * ��ȡ������ͬվ��Ķ���html��ҳ����
	 * @return ���ӵ��б�
	 * @exception MalformedURLException ��ȡʱ���ִ�����׳����쳣
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
	 * �ж��Ƿ�������ͬ��վ��
	 * @param host ��Ҫ�жϵ�host 
	 * @param url ��Ҫ�жϵ�url
	 * @return ����Ƿ���true������񷵻�false
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
	 * ��ȡ��ҳ���������Լ�������ͬվ���������ҳ������
	 * @return ���������б�
	 */
	public List getAllSameSiteHTMLPageURLs() {
		return getURLs(getAllSameSiteHTMLPageLinks());
	}
	/*===========================================================*/
	
	/**
	 * ��ȡ��ҳ����
	 * @return ����newURL����
	 */
	public String getURL() {
		return newURL;
	}

	/**
	 * ��ȡ��ҳ����վ����Ϣ
	 * @return host����
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
	 * ��ȡdom
	 * @return ����doc����
	 */
	public Document getDOM() {
		return doc;
	}
	/**
     * ��links����ת��Ϊurl����
	 * @param links ���ӵ��б�
	 * @return ����url���ӵ��б�
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
	 * ��a�ڵ��ȡLink
     * �е�a������text�������Ĺ�档
     * a href: http://bj.classad.sina.com.cn/hairdressing/index.shtml
     * ����href =" javascript:xxx"
	 * @param e ��Ҫ������ӵ�Ԫ��
	 * @param rootURL ����ҳ�ڵ�
	 * @return ��Ӧ������
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
	 * ��ȡ����ͼƬ����
	 * @return null
	 */
	public List getAllImageLinks() {
		return null;
	}
	
	public static int downloads = 0;
	/**
	 * ��ҳ����վ��
	 */
	private String host;
	
	
	private String newURL;
	
	private HttpClient httpClient;
	/**
	 * ��ҳ��dom
	 */
	private Document doc;
}