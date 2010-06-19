package com.iil.ie.freetext.test;

import java.io.*;
import java.util.*;

import org.w3c.dom.Element;

import com.iil.util.web.*;
import com.iil.util.xml.*;
import com.iil.ie.freetext.*;

public class Test_EPattern {
	
	public void test() throws IOException {
		String url = "http://www.zsi.com.cn/aboutus6.asp";
		//String url = "http://sports.sina.com.cn/g/p/2004-04-19/1043852579.shtml";
		HTMLWrapper hw = new HTMLWrapper(url);
		String s = (new HTMLDOMPrinter()).print(hw.getDOM());
		List eps = new ArrayList();
		eps.add(PatternFactory.OBSOLETECHINESESTRING_EPATTERN);
		//eps.add(PatternFactory.CHINESESTRING_EPATTERN);
		//eps.add(new PreEPattern(new RegexEPattern("邮箱")));
		OREPattern ep = new OREPattern(eps);
		testEP(ep, s);
	}
	
	public static String getText(String url){
		try {
			//String url = "http://sports.sina.com.cn/g/p/2004-04-19/1043852579.shtml";
			HTMLWrapper hw = new HTMLWrapper(url);
			String s = (new HTMLDOMPrinter()).print(hw.getDOM());
			return s;
		}catch(IOException e) {
			System.out.println (e);
			return "";
		}
	}

	public static String getText(){
		return getText("http://www.bayi.com.cn/htm/bywh/byzp.htm");
	}
	
	public void test2() {
		EPattern ep = new PreEPattern(new RegexEPattern("地址"));
		String s = "地址：北京市 地址：上海市 地址天津市，()";
		testEP(ep, s);
	}
	
	public void test3() {
		EPattern ep = new RegexEPattern("地址");
		String s = "地址：北京市上海市 地址：北京市";
		testEP(ep, s);
	}
	
	public void testOR() {
		List eps = new ArrayList();
		eps.add(new RegexEPattern("北京市"));
		eps.add(new RegexEPattern("上海市"));
		eps.add(new RegexEPattern(".市"));
		EPattern ep = new OREPattern(eps);
		ep = new RegexEPattern("北.市|上海市");
		String s = "地址：北京市上海市 地址：北京市";
		testEP(ep, s);
	}
	
	public void testDict() {
		try {
			EPatternConstructor epc = new DictEPC("dict.txt");
			EPattern ep = epc.getEPattern();
			testEP(ep, null);
		}catch(IOException e) {
			System.out.println (e);
		}
	}
	
	public void testContain() {
		EPattern master = PatternFactory.OBSOLETECHINESESTRING_EPATTERN;
		List eps = new ArrayList();
		eps.add(new RegexEPattern("程序员"));
		eps.add(new RegexEPattern("主管"));
		eps.add(new RegexEPattern("工程师"));
		eps.add(new RegexEPattern("经理"));
		OREPattern orep = new OREPattern(eps);
		EPattern ep = new ContainEPattern(master, orep);
		testEP(ep, getText());
	}
	
	public EPattern getContainEPattern() {
		EPattern master = PatternFactory.OBSOLETECHINESESTRING_EPATTERN;
		List eps = new ArrayList();
		eps.add(new RegexEPattern("程序员"));
		eps.add(new RegexEPattern("主管"));
		eps.add(new RegexEPattern("工程师"));
		eps.add(new RegexEPattern("经理"));
		OREPattern orep = new OREPattern(eps);
		EPattern ep = new ContainEPattern(master, orep);
		return ep;
	}
	
	public EPattern getEndWithEPattern() {
		List eps = new ArrayList();
		eps.add(new RegexEPattern("程序员"));
		eps.add(new RegexEPattern("主管"));
		eps.add(new RegexEPattern("工程师"));
		eps.add(new RegexEPattern("经理"));
		OREPattern orep = new OREPattern(eps);
		EPattern ep = new EndWithEPattern(orep);
		return ep;
	}

	public EPattern getPositionEPattern() {
		EPattern master = PatternFactory.OBSOLETECHINESESTRING_EPATTERN;
		EPattern ep = new ContainEPattern(master, getEndWithEPattern());
		return ep;
	}

	public EPattern getStructEPattern() {
		List eps = new ArrayList();
		eps.add(getContainEPattern());
		eps.add(new RegexEPattern("(" + PatternFactory.NUMBER_REGEX + ")人", 1));
		return new StructEPattern(eps);
	}
	
	public EPattern getContactEPattern() {
		List addressor = new ArrayList();
		addressor.add(new RegexEPattern("地址"));
		EPattern addressep = new PreEPattern(new OREPattern(addressor));
		
		List zipor = new ArrayList();
		zipor.add(new RegexEPattern("邮编"));
		zipor.add(new RegexEPattern("邮政编码"));
		zipor.add(PatternFactory.POSTALCODE_EPATTERN);
		EPattern zipep = new PreEPattern(new OREPattern(zipor));
		
		List phoneor = new ArrayList();
		phoneor.add(new RegexEPattern("电话"));
		EPattern phoneep = new PreEPattern(new OREPattern(phoneor));
		
		List faxor = new ArrayList();
		faxor.add(new RegexEPattern("传真"));
		EPattern faxep = new PreEPattern(new OREPattern(faxor));

		EPattern emailep = PatternFactory.EMAIL_EPATTERN;
		
		List eps = new ArrayList();
		eps.add(addressep);
		eps.add(zipep);
		eps.add(phoneep);
		eps.add(faxep);
		eps.add(emailep);
		return new StructEPattern(eps);
	}
	
	public EPattern getFaxPreEPattern() {
		return ((new SeperatedLiteralStringEPC("传真")).getEPattern());
	}

	public EPattern getNamedEPattern() {
		//return new NamedEPattern(new RegexEPattern("地址"), new RegexEPattern("[^\\s]+"));
		return new NamedEPattern(new RegexEPattern("地址"), new SingleStringEPattern());
	}
	
	public static void testEP(EPattern ep, String s) {
		String str = s;
		if (str == null) str = "地址：北京市上海市 地址：北京市";
		ep.resetInput(str);
		//ep.find();
		while (ep.find()) {
			System.out.println ("start: " + ep.start());
			System.out.println ("end: " + ep.end());
			Object group = ep.group();
			if (group instanceof Element) {
				System.out.println ((new SimpleDOMPrinter()).print((Element)group));
			}
			else System.out.println (group);
		}
	}

	public static void testEPOnURL(EPattern ep, String url) {
		String str = getText(url);
		testEP(ep, str);
	}
	

	public static void main(String[] args) throws IOException{
    	Test_EPattern test = new Test_EPattern();
    	//System.out.printf("%x", (int)'：');
    	//test.testEP(test.getContactEPattern(), test.getText());
    	//test.testEP(test.getStructEPattern(), test.getText());
    	//test.testEP(test.getContainEPattern(), test.getText());
    	//test.testEP(test.getPositionEPattern(), test.getText());
    	//Test_EPattern.testEP(test.getNamedEPattern(), "地址：北京市上海市 地址：北京市");
    	testEP(test.getFaxPreEPattern(), "传 真");
    	//test.test2();
    	//test.testEP(PatternFactory.POSTALCODE_EPATTERN, "230027");
    	//test.testEP(PatternFactory.EMAIL_EPATTERN, "ferryzhou@sina.com");
    	//testEP(PatternFactory.EMAIL_EPATTERN, "365go@365go.com.cn咨询");
    	//test.testOR();
/*    	System.out.println(Character.isLetter((int)'中'));
    	System.out.println(Character.isLetter((int)','));
    	System.out.println(Character.isLetter((int)')'));
    	System.out.println(Character.isLetter((int)'（'));
    	System.out.println(Character.isLetter((int)'，'));
    	System.out.println(Character.isLetter((int)'：'));
*/
    }
}
