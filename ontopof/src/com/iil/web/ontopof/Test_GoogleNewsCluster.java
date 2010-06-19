package com.iil.web.ontopof;

import java.io.*;

import com.iil.util.web.HTMLWrapper;

class Test_GoogleNewsCluster {
	
	public static void main(String[] args) throws Exception {
		String url = "http://news.google.com/news?ned=cn&topic=t";
		HTMLWrapper hw = new HTMLWrapper(new ApacheHttpClient(), url);
		String content = hw.getContent();
		
		
    }
}
