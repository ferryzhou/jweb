package com.iil.util.web.test;

import java.io.*;

import com.iil.util.web.*;
import com.iil.util.IO;

public class Test_HttpClient {
	
	public void test() throws IOException{
		String url = "http://fbbs.ustc.edu.cn/cgi-bin/bbslogin";
		String post = "id=outgoing&pw=abao";
		HttpClient hc = new JavaHttpClient();
		hc.connect(url, post);
		IO.readWriteAll(hc.getInputStream(), System.out);
		url = "http://fbbs.ustc.edu.cn/cgi-bin/bbssnd?board=test";
		post = "title=test&allowre=1&signature=1&text=test";
		hc.connect(url, post);
		hc.connect("http://fbbs.ustc.edu.cn/cgi-bin/bbslogout");
		IO.readWriteAll(hc.getInputStream(), System.out);
		//HTMLWrapper u = new HTMLWrapper(url);
	}
	
	public static void main(String[] args) throws IOException{
		Test_HttpClient t = new Test_HttpClient();
		t.test();
	}
}