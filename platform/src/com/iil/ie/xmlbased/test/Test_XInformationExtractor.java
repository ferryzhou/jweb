package com.iil.ie.xmlbased.test;

import java.io.*;
import com.iil.ie.xmlbased.*;

public class Test_XInformationExtractor {
	
/*	public void test() throws Exception{
		InformationExtractor ie = new InformationExtractor("sina.dir.er.xml");
		ie.setBiginURL("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/");
		List dirs = (List)ie.extractObject("directorylist");
		System.out.println (ObjectPrinter.print(dirs));
		do {
			List files = (List)ie.extractObject("companylist");
			System.out.println (ObjectPrinter.print(files));
		} while (ie.goNextURL());
	}
*/	
	public void testsinasportsnews() throws Exception{
		XInformationExtractor xie = new XInformationExtractor("cn.com.sina.sports.china.xsl");
		xie.transformFromURL("http://sports.sina.com.cn/china/", new FileWriter("sinasports.xml"));
		//xie.extract("http://sports.sina.com.cn/china/");
		//Node n = xie.getObject("jiaanewslist");
		//System.out.println (com.iil.util.xml.DOMInfoExtractor.extractString((Element)n, "."));
	}
	
	public void testsinaweather() throws Exception{
		XInformationExtractor xie = new XInformationExtractor("weather2.sina.xsl");
		xie.transformFromURL("http://weather.news.sina.com.cn/index.html", new FileWriter("hefeiweather.xml"));
	}

	public void testsina2() throws Exception{
		XInformationExtractor xie = new XInformationExtractor("links.news.sina.xsl");
		xie.transformFromURL("http://news.sina.com.cn/", new FileWriter("news.sina.xml"));
	}
	
	public void testdate() throws Exception{
		XInformationExtractor xie = new XInformationExtractor("date.xsl");
		xie.transformFromURL("http://www.ustc.edu.cn/", new FileWriter("date.txt"));
	}

	public void testbbs() throws Exception{
		XInformationExtractor xie = new XInformationExtractor("titledlinks.xsl");
		xie.transformFromURL("http://bbs.nju.edu.cn/vd498148/bbsmain", new FileWriter("top10.fbbs.xml"));
	}
	
	public void test(String xslfile, String url, String outfile) throws Exception{
		XInformationExtractor xie = new XInformationExtractor(xslfile);
		xie.transformFromURL(url, new FileWriter( outfile));		
	}
	/**
	 * 测试类主函数
	 * @param args 用户输入参数
	 * @throws Exception 新建对象过程中如果出现错误就抛出Exception
	 */
	public static void main(String[] args) throws Exception{
		Test_XInformationExtractor t = new Test_XInformationExtractor();
		String xslfile = "data/calendar.xsl";
		String url = "http://www.bh8141.com/hl.php";
		String outfile = "data/out.xml";
		t.test(xslfile, url, outfile);
	}
}