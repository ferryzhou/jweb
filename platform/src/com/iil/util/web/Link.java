package com.iil.util.web;

/**
 * ���ڼ�¼ҳ���е�url�Ͷ�Ӧ������
 * @author Ferry
 * @version 1.0
 */ 
public class Link
{
	/**
	 * Link�Ĺ��캯��
	 * @param url ��Ҫ�����ҳ�����Ӷ�ӦURL
	 * @param text ��Ҫ�����ҳ�����Ӷ�Ӧ������
	 */
	public Link(String url,String text)
	{
		this.text=text;
		this.url=url;
	}
	
	/**
	 * ��ȡҳ�����Ӷ�Ӧ������
	 * @return ����ҳ�����Ӷ�Ӧ������
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * ��ȡҳ�����Ӷ�ӦURL
	 * @return ����ҳ�����Ӷ�ӦURL
	 */
	public String getURL()
	{
		return url;
	}
	
	/**
	 * ����ҳ�����Ӷ�ӦURL
	 * @param url ��Ҫ���õ�ҳ�����Ӷ�ӦURL
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
	 * ҳ�����Ӷ�Ӧ������
	 */
	private String text;
	
	/**
	 * ҳ�����Ӷ�ӦURL
	 */
	private String url;
}