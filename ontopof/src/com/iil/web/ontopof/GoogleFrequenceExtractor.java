package com.iil.web.ontopof;

import org.apache.commons.httpclient.*;

import java.io.*;
import java.util.*;
import java.text.*;

import org.w3c.dom.*;

import com.iil.util.web.*;
import com.iil.ie.xmlbased.*;
import com.iil.ie.*;
import com.iil.util.*;
import com.iil.util.xml.*;

class GoogleFrequenceExtractor {


	public static int getNewsFrequency(String item) throws IOException {
		String q = getCNNewsSearchURL(item);
		HTMLWrapper hw = new HTMLWrapper(new ApacheHttpClient(), q);
		String content = hw.getContent();
		return extractFrequencyFromSuffix(content);
	}

	public static int getSearchFrequency(String item) throws IOException {
		String q = getCNSearchURL(item);
		HTMLWrapper hw = new HTMLWrapper(new ApacheHttpClient(), q);
		String content = hw.getContent();
		return extractFrequencyFromSuffix(content);
	}

	private static int extractFrequencyFromSuffix(String content) {
		int index = content.indexOf(SUFFIX);
		if (index != -1) {
			System.out.println (content.substring(index - 7, index + 10));
			char a;
			int num = 0;
			int mul = 1;
			for (int i = 0; ; i++) {
				a = content.charAt(index - i - 1);
				if (a == ',' || a == '£¬') continue;
				if (a >= '0' && a <= '9') {
					num += (a - '0') * mul;
					mul *= 10;
				} else return num;
			}
		}
		
		return 0;		
	}
	
	private static String getCNNewsSearchURL(String item) throws IOException {
		URI uri = new URI("http://news.google.com/news?hl=zh-CN&ned=cn&q=" + item, false);
		return uri.toString();
	}

	private static String getCNSearchURL(String item) throws IOException {
		URI uri = new URI("http://news.google.com/search?hl=zh-CN&q=" + item, false);
		return uri.toString();
	}
	
	public static void main(String[] args) throws Exception{  
	  	
    	String testItem = "Î¢Èí";
    	System.out.println ("News results: " + GoogleFrequenceExtractor.getNewsFrequency(testItem));
    	System.out.println ("Search results: " + GoogleFrequenceExtractor.getSearchFrequency(testItem));
    }
    
    public static final String SUFFIX = "Ïî·ûºÏ";
}
