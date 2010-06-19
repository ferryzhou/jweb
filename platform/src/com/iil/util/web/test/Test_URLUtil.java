package com.iil.util.web.test;

import com.iil.util.web.*;

public class Test_URLUtil {
	
	public void test() throws Exception{
		System.out.println (URLUtil.toAbsoluteURL("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/","index2.html"));
		System.out.println (URLUtil.toAbsoluteURL("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/","./index2.html"));
		System.out.println (URLUtil.toAbsoluteURL("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/","../index2.html"));
		System.out.println (URLUtil.toAbsoluteURL("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/","../../index2.html"));
		System.out.println (URLUtil.toAbsoluteURL("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/","/index2.html"));
		
		String url1 = "http://aa.com/aa.htm#xx";
		String url2 = "http://aa.com/aa.htm";
		System.out.println (URLUtil.trim(url1).equals(url2));
		System.out.println (URLUtil.isEqual(url1, url2));
		System.out.println (URLUtil.trim("http://aa.com/http://xx.com#xx"));
		
		String url3 = "http://aa.com/ab.htm";
		String url4 = "http://aa.com/";
		System.out.println (URLUtil.getParent(url1).equals(url4));
		System.out.println (URLUtil.isSibling(url1, url3));
		System.out.println (!URLUtil.isSibling(url1, url4));
		System.out.println (URLUtil.isSibling(url4, "http://aa.com"));
		
		System.out.println (URLUtil.getSuffix(url1).equals("htm"));
		System.out.println (URLUtil.getSuffix("http://aa.com/aa.asp?job=jobd"));
		System.out.println (URLUtil.getSuffix("http://aa.com/aa.asp?job=jobd").equals("asp"));
		
		System.out.println (URLUtil.isAtSameHost(url3, url4));
		System.out.println (!URLUtil.isAtSameHost(url3, "http://bb.com"));
		
		System.out.println (URLUtil.isEqual("http://aa.com", "http://aa.com/"));
	}
	
	public static void main(String[] args) throws Exception{
		Test_URLUtil t = new Test_URLUtil();
		t.test();
	}
}