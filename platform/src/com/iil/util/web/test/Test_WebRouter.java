package com.iil.util.web.test;


import java.io.*;

import com.iil.util.web.*;

public class Test_WebRouter {
	
	public void test() throws IOException{
		String initialURL = "http://www.sina.com.cn/";
		String path = "/��ҵ/��˾/";
		//String qiyeURL = WebRouter
		String endURL = WebRouter.locate(initialURL, path);
		String trueEndURL = "http://dir.sina.com.cn/search_dir/syjj/com/";
		System.out.println ("get: " + endURL);
		System.out.println ("true: " + trueEndURL);
		System.out.println ("is equal? " + endURL.equals(trueEndURL));
	}
	
/*	public void testParsePath() {
		System.out.println (WebRouter.parsePath("/��ҵ/��˾/"));
		System.out.println (WebRouter.parsePath("/��ҵ/��˾"));
		System.out.println (WebRouter.parsePath("��ҵ/��˾"));
	}
*/	
	public static void main(String[] args) throws IOException{
		Test_WebRouter t = new Test_WebRouter();
		//t.testParsePath();
		t.test();
	}
}