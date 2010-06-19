package com.iil.util.web;

import java.net.*;

/**
 * 处理url的工作包
 * isEqual还有bug。http://aa.com/ != http://aa.com
 * @author Ferry
 * @version 1.0
 */
public class URLUtil {

	/**
	 * @param contextURL
	 * @param relativeURL
	 * @return
	 */
	public static String toAbsoluteURL(String contextURL, String relativeURL){
		try {
			URL url = new URL(new URL(contextURL), relativeURL);
			return filterOutDot(url.toString());
		}catch(MalformedURLException e) {
			//System.out.println (e);
			//System.out.println (contextURL + " " + relativeURL);
			return null;
		}
	}
	
	/**
	 * 删除"../"，
	 * @param url 需要处理的url地址
	 * @return 处理后的url
	 */
	private static String filterOutDot(String url) {
		String dotString = "../";
		int i = url.indexOf(dotString);
		if (i != -1) {
			return filterOutDot(url.substring(0, i) + url.substring(i + dotString.length()));
		} else return url;
	}
	
	/**
	 * 删除后缀
	 * @param url 需要处理的url地址
	 * @return 删除后的地址字符串
	 */
	public static String trimSuffix(String url) {
		int dotIndex = url.lastIndexOf(".");
		if (dotIndex == -1) return url;
		else return url.substring(0, dotIndex);
	}
	
	/**
     * 获取后缀名称
	 * @param url 需要返回后缀的url地址
	 * @return 返回后缀名，如果没有后缀，则返回空。
	 * @throws MalformedURLException
	 * @see URLUtil
	 */
	public static String getSuffix(String url) throws MalformedURLException{
		URL u = new URL(url);
		String file = u.getFile();
		int dotIndex = file.lastIndexOf(".");
		if (dotIndex == -1) return "";
		else return trimQuery(file.substring(dotIndex + 1));
	}
	
	/**
	 * 获取url，还可以判断一下这个字符串的内容是否是一个合法的url地址
	 * @param url 需要判断的url地址
	 * @throws MalformedURLException 出现错误就抛出此异常
	 */
	public static void checkURL(String url) throws MalformedURLException{
		URL u = new URL(url);
	}
	
	/**
	 * 判断childURL是不是parentURL的子链接
	 * @param parentURL 需要判断的父节点
	 * @param childURL 需要判断的子节点
	 * @return 如果符合父子关系，返回true；否则返回false
	 */
	public static boolean isChild(String parentURL, String childURL) {
		int i = childURL.indexOf(parentURL);
		if (i == -1) return false;
		String sub = childURL.substring(i + parentURL.length(), childURL.length());
		String[] subs = sub.split("/");
		if (subs.length != 1) return false;
		return true;
	}
	
	/**
	 * 判断是不是同一个父节点的子节点
	 * @param oneURL 需要判断的一个url
	 * @param anotherURL 需要判断的另一个url
	 * @return 是的话返回true，否则返回false
	 */
	public static boolean isSibling(String oneURL, String anotherURL) {
		if (oneURL.equals(anotherURL)) return false;
		if (isRoot(oneURL) && isRoot(anotherURL)) return isEqual(oneURL, anotherURL);
		if (isRoot(oneURL) || isRoot(anotherURL)) return false;
		return getParent(oneURL).equals(getParent(anotherURL));
	}
	
	/**
	 * 需要判断是否是两个url对应的网页是否是同样的站点
	 * @param oneURL 需要比对的一个url
	 * @param anotherURL 需要比对的另一个url
	 * @return 如果一致，返回ture，否则返回false
	 */
	public static boolean isAtSameHost(String oneURL, String anotherURL) {
		try {
			String host1 = (new URL(oneURL)).getHost();
			String host2 = (new URL(anotherURL)).getHost();
			if (host1.equals(host2)) return true;
			else return false;
		}catch(MalformedURLException e) {
			System.out.println (e);
			return false;
		}
	}
	
	/**
	 * 取出url中文件名称
	 * @param url 需要处理的url
	 * @return 所求文件名称字符串
	 */
	public static String getFilename(String url) {
		if (isRoot(url)) return "index.htm";
		int i = url.lastIndexOf("/");
		if (i == url.length() - 1) return "index.htm";
		if (i != -1) return url.substring(i + 1);
		return "error.htm";
	}
	
	/**
     * 获取父链接。
	 * @param url 需要获取父链接的网页url
	 * @return 该url的父链接
	 */
	public static String getParent(String url) {
		if (isRoot(url)) return url;
		int i = url.lastIndexOf("/");
		if (i == (url.length() - 1)) {
			return getParent(url.substring(0, url.length() - 1));
		}
		return url.substring(0, i + 1);
	}
	
	/**
     * 是否是根链接。比如http://www.sina.com.cn/
	 * @param url 需要判断的网页地址
	 * @return 是根结点返回true，否则返回false
	 */
	public static boolean isRoot(String url) {
		String[] ss = trimHttp(url).split("/");
		if (ss.length == 1) return true;
		else return false;
	}
	
	/**
     * 将链接前面的http://去掉。
	 * @param url 需要处理的url字符串
	 * @return 处理后的url字符串
	 */
	private static String trimHttp(String url) {	
		String httpStr = "http://";
		int i = url.indexOf(httpStr);
		if (i != -1) return url.substring(httpStr.length());
		else return url;
	}
	
	/**
	 * 有问题再说
	 */
	public static boolean isEqual(String url1, String url2) {
		return trim(url1).equals(trim(url2));
	}
	
	/**
     * 去掉重定向，去掉#部分。
     * 去掉最后的"/"
	 * @param url 需要处理的url字符串
	 * @return 处理后的url字符串
	 */
	public static String trim(String url) {
		return trimSlash(trimAnchor(trimRedirect(url)));
	}
	
	private static String trimSlash(String url) {
		int i = url.lastIndexOf("/");
		if (i != url.length() - 1) return url;
		else return url.substring(0, i);
	}	

	/**
     * 把后面的#去掉。
	 * @param url 需要处理的url字符串
	 * @return 处理后的url字符串
	 */
	private static String trimAnchor(String url) {
		int i = url.indexOf("#");
		if (i == -1) return url;
		else return url.substring(0, i);
	}
	
	/**
     * 对于这样的：http://rw.search.sina.com.cn/dir/http://www.greast.com/
     * 把前面的http部分去掉。
	 * @param url 需要处理的url字符串
	 * @return 处理后的url字符串
	 */
	private static String trimRedirect (String url) {
		String httpStr = "http://";
		int i = url.lastIndexOf(httpStr);
		if (i != -1) return url.substring(i);
		else return url;
/*		int pre = -httpStr.length();
		int i = 0;
		while ((i = url.indexOf(httpStr, pre+httpStr.length())) != -1) {
			pre = i;
		}
		if (pre > 0) return url.substring(pre);
		else return url;
*/	}
	
	/**
	 * 抽取url中的？前面的部分
	 * @param url 需要处理的url字符串
	 * @return 处理后的url字符串
	 */
	private static String trimQuery(String url) {
		int i = url.indexOf("?");
		if (i == -1) return url;
		else return url.substring(0, i);		
	}
	
}
