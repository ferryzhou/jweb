package com.iil.util.web.test;

import java.util.*;
import java.io.*;

import com.iil.util.web.*;
import com.iil.util.ObjectPrinter;

public class Test_HTMLWrapper {
	
	public void test() throws IOException{
		//DHTMLHttpClient.execfile = "F:\\zj\\work\\Project\\DHTMLEXE\\GetHtml";
		//String url = "http://www.cjol.com/main/jobseeker/SearchResult.asp?sltJobLocation=2008&hdnJobLocation=深圳市&hdnJobLocationCode=2008&BigCode=51&sltJobFunction=5105&hdnJobFunction=总裁/CEO/总经理&hdnJobFunctionCode=5105&sltJobPostingPeriod=30&txtKeyword=&chkJobCategory_FullTime=1&chkJobCategory_PartTime=1&chkJobCategory_Graduate=1";
		//String url = "http://202.38.73.222";
		//HttpClient hc = new DHTMLHttpClient();
		//HttpClient hc = new JavaHttpClient();
		//String url = "http://music.ustc.edu.cn/playdirect.php";
		//String postData = "song_id%5B%5D=6565&song_id%5B%5D=6566&song_id%5B%5D=6567";
		//HTMLWrapper u = new HTMLWrapper(new File("data\\whoweare.html"));
		HTMLWrapper u = new HTMLWrapper("http://www.365go.com.cn/aboutour.htm#5");
		//System.out.println ("url: " + hc.getURL());
		//com.iil.util.IO.readWriteAll(hc.getInputStream(), new FileOutputStream("tt.htm"));
		System.out.println (u.getTitle());
		System.out.println (u.getContent());
/*		List l = u.getAllLinks();
		List hl = u.getAllHTMLPageLinks();
		List ul = u.getAllHTMLPageURLs();
		List sl = u.getAllSameSiteHTMLPageURLs();
		System.out.println ("alllinks" + ObjectPrinter.print(l));
		System.out.println (l.size());
		System.out.println ("html links" + ObjectPrinter.print(hl));
		System.out.println (hl.size());
		System.out.println (ObjectPrinter.print(ul));
		System.out.println (ul.size());
		System.out.println (ObjectPrinter.print(sl));
		System.out.println (sl.size());
*/	}
	
	public static void main(String[] args) throws IOException{
		Test_HTMLWrapper t = new Test_HTMLWrapper();
		t.test();
	}
}