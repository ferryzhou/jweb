package com.iil.util.web;

import java.net.*;

/**
 * ����url�Ĺ�����
 * isEqual����bug��http://aa.com/ != http://aa.com
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
	 * ɾ��"../"��
	 * @param url ��Ҫ�����url��ַ
	 * @return ������url
	 */
	private static String filterOutDot(String url) {
		String dotString = "../";
		int i = url.indexOf(dotString);
		if (i != -1) {
			return filterOutDot(url.substring(0, i) + url.substring(i + dotString.length()));
		} else return url;
	}
	
	/**
	 * ɾ����׺
	 * @param url ��Ҫ�����url��ַ
	 * @return ɾ����ĵ�ַ�ַ���
	 */
	public static String trimSuffix(String url) {
		int dotIndex = url.lastIndexOf(".");
		if (dotIndex == -1) return url;
		else return url.substring(0, dotIndex);
	}
	
	/**
     * ��ȡ��׺����
	 * @param url ��Ҫ���غ�׺��url��ַ
	 * @return ���غ�׺�������û�к�׺���򷵻ؿա�
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
	 * ��ȡurl���������ж�һ������ַ����������Ƿ���һ���Ϸ���url��ַ
	 * @param url ��Ҫ�жϵ�url��ַ
	 * @throws MalformedURLException ���ִ�����׳����쳣
	 */
	public static void checkURL(String url) throws MalformedURLException{
		URL u = new URL(url);
	}
	
	/**
	 * �ж�childURL�ǲ���parentURL��������
	 * @param parentURL ��Ҫ�жϵĸ��ڵ�
	 * @param childURL ��Ҫ�жϵ��ӽڵ�
	 * @return ������ϸ��ӹ�ϵ������true�����򷵻�false
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
	 * �ж��ǲ���ͬһ�����ڵ���ӽڵ�
	 * @param oneURL ��Ҫ�жϵ�һ��url
	 * @param anotherURL ��Ҫ�жϵ���һ��url
	 * @return �ǵĻ�����true�����򷵻�false
	 */
	public static boolean isSibling(String oneURL, String anotherURL) {
		if (oneURL.equals(anotherURL)) return false;
		if (isRoot(oneURL) && isRoot(anotherURL)) return isEqual(oneURL, anotherURL);
		if (isRoot(oneURL) || isRoot(anotherURL)) return false;
		return getParent(oneURL).equals(getParent(anotherURL));
	}
	
	/**
	 * ��Ҫ�ж��Ƿ�������url��Ӧ����ҳ�Ƿ���ͬ����վ��
	 * @param oneURL ��Ҫ�ȶԵ�һ��url
	 * @param anotherURL ��Ҫ�ȶԵ���һ��url
	 * @return ���һ�£�����ture�����򷵻�false
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
	 * ȡ��url���ļ�����
	 * @param url ��Ҫ�����url
	 * @return �����ļ������ַ���
	 */
	public static String getFilename(String url) {
		if (isRoot(url)) return "index.htm";
		int i = url.lastIndexOf("/");
		if (i == url.length() - 1) return "index.htm";
		if (i != -1) return url.substring(i + 1);
		return "error.htm";
	}
	
	/**
     * ��ȡ�����ӡ�
	 * @param url ��Ҫ��ȡ�����ӵ���ҳurl
	 * @return ��url�ĸ�����
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
     * �Ƿ��Ǹ����ӡ�����http://www.sina.com.cn/
	 * @param url ��Ҫ�жϵ���ҳ��ַ
	 * @return �Ǹ���㷵��true�����򷵻�false
	 */
	public static boolean isRoot(String url) {
		String[] ss = trimHttp(url).split("/");
		if (ss.length == 1) return true;
		else return false;
	}
	
	/**
     * ������ǰ���http://ȥ����
	 * @param url ��Ҫ�����url�ַ���
	 * @return ������url�ַ���
	 */
	private static String trimHttp(String url) {	
		String httpStr = "http://";
		int i = url.indexOf(httpStr);
		if (i != -1) return url.substring(httpStr.length());
		else return url;
	}
	
	/**
	 * ��������˵
	 */
	public static boolean isEqual(String url1, String url2) {
		return trim(url1).equals(trim(url2));
	}
	
	/**
     * ȥ���ض���ȥ��#���֡�
     * ȥ������"/"
	 * @param url ��Ҫ�����url�ַ���
	 * @return ������url�ַ���
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
     * �Ѻ����#ȥ����
	 * @param url ��Ҫ�����url�ַ���
	 * @return ������url�ַ���
	 */
	private static String trimAnchor(String url) {
		int i = url.indexOf("#");
		if (i == -1) return url;
		else return url.substring(0, i);
	}
	
	/**
     * ���������ģ�http://rw.search.sina.com.cn/dir/http://www.greast.com/
     * ��ǰ���http����ȥ����
	 * @param url ��Ҫ�����url�ַ���
	 * @return ������url�ַ���
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
	 * ��ȡurl�еģ�ǰ��Ĳ���
	 * @param url ��Ҫ�����url�ַ���
	 * @return ������url�ַ���
	 */
	private static String trimQuery(String url) {
		int i = url.indexOf("?");
		if (i == -1) return url;
		else return url.substring(0, i);		
	}
	
}
