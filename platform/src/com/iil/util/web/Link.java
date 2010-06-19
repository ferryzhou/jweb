package com.iil.util.web;

/**
 * 用于记录页面中的url和对应的文字
 * @author Ferry
 * @version 1.0
 */ 
public class Link
{
	/**
	 * Link的构造函数
	 * @param url 需要保存的页面链接对应URL
	 * @param text 需要保存的页面链接对应的文字
	 */
	public Link(String url,String text)
	{
		this.text=text;
		this.url=url;
	}
	
	/**
	 * 获取页面链接对应的文字
	 * @return 返回页面链接对应的文字
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * 获取页面链接对应URL
	 * @return 返回页面链接对应URL
	 */
	public String getURL()
	{
		return url;
	}
	
	/**
	 * 设置页面链接对应URL
	 * @param url 需要设置的页面链接对应URL
	 */
	public void setURL(String url)
	{
		this.url = url;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Link) {
			Link ol = (Link)o;
			return (URLUtil.isEqual(this.url, ol.url));
		}
		return false;
	}
	
	public String toString()
	{
		return url + "---" + text;
	}
	
	/**
	 * 页面链接对应的文字
	 */
	private String text;
	
	/**
	 * 页面链接对应URL
	 */
	private String url;
}