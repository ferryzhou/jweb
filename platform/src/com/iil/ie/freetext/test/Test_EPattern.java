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
		//eps.add(new PreEPattern(new RegexEPattern("����")));
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
		EPattern ep = new PreEPattern(new RegexEPattern("��ַ"));
		String s = "��ַ�������� ��ַ���Ϻ��� ��ַ����У�()";
		testEP(ep, s);
	}
	
	public void test3() {
		EPattern ep = new RegexEPattern("��ַ");
		String s = "��ַ���������Ϻ��� ��ַ��������";
		testEP(ep, s);
	}
	
	public void testOR() {
		List eps = new ArrayList();
		eps.add(new RegexEPattern("������"));
		eps.add(new RegexEPattern("�Ϻ���"));
		eps.add(new RegexEPattern(".��"));
		EPattern ep = new OREPattern(eps);
		ep = new RegexEPattern("��.��|�Ϻ���");
		String s = "��ַ���������Ϻ��� ��ַ��������";
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
		eps.add(new RegexEPattern("����Ա"));
		eps.add(new RegexEPattern("����"));
		eps.add(new RegexEPattern("����ʦ"));
		eps.add(new RegexEPattern("����"));
		OREPattern orep = new OREPattern(eps);
		EPattern ep = new ContainEPattern(master, orep);
		testEP(ep, getText());
	}
	
	public EPattern getContainEPattern() {
		EPattern master = PatternFactory.OBSOLETECHINESESTRING_EPATTERN;
		List eps = new ArrayList();
		eps.add(new RegexEPattern("����Ա"));
		eps.add(new RegexEPattern("����"));
		eps.add(new RegexEPattern("����ʦ"));
		eps.add(new RegexEPattern("����"));
		OREPattern orep = new OREPattern(eps);
		EPattern ep = new ContainEPattern(master, orep);
		return ep;
	}
	
	public EPattern getEndWithEPattern() {
		List eps = new ArrayList();
		eps.add(new RegexEPattern("����Ա"));
		eps.add(new RegexEPattern("����"));
		eps.add(new RegexEPattern("����ʦ"));
		eps.add(new RegexEPattern("����"));
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
		eps.add(new RegexEPattern("(" + PatternFactory.NUMBER_REGEX + ")��", 1));
		return new StructEPattern(eps);
	}
	
	public EPattern getContactEPattern() {
		List addressor = new ArrayList();
		addressor.add(new RegexEPattern("��ַ"));
		EPattern addressep = new PreEPattern(new OREPattern(addressor));
		
		List zipor = new ArrayList();
		zipor.add(new RegexEPattern("�ʱ�"));
		zipor.add(new RegexEPattern("��������"));
		zipor.add(PatternFactory.POSTALCODE_EPATTERN);
		EPattern zipep = new PreEPattern(new OREPattern(zipor));
		
		List phoneor = new ArrayList();
		phoneor.add(new RegexEPattern("�绰"));
		EPattern phoneep = new PreEPattern(new OREPattern(phoneor));
		
		List faxor = new ArrayList();
		faxor.add(new RegexEPattern("����"));
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
		return ((new SeperatedLiteralStringEPC("����")).getEPattern());
	}

	public EPattern getNamedEPattern() {
		//return new NamedEPattern(new RegexEPattern("��ַ"), new RegexEPattern("[^\\s]+"));
		return new NamedEPattern(new RegexEPattern("��ַ"), new SingleStringEPattern());
	}
	
	public static void testEP(EPattern ep, String s) {
		String str = s;
		if (str == null) str = "��ַ���������Ϻ��� ��ַ��������";
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
    	//System.out.printf("%x", (int)'��');
    	//test.testEP(test.getContactEPattern(), test.getText());
    	//test.testEP(test.getStructEPattern(), test.getText());
    	//test.testEP(test.getContainEPattern(), test.getText());
    	//test.testEP(test.getPositionEPattern(), test.getText());
    	//Test_EPattern.testEP(test.getNamedEPattern(), "��ַ���������Ϻ��� ��ַ��������");
    	testEP(test.getFaxPreEPattern(), "�� ��");
    	//test.test2();
    	//test.testEP(PatternFactory.POSTALCODE_EPATTERN, "230027");
    	//test.testEP(PatternFactory.EMAIL_EPATTERN, "ferryzhou@sina.com");
    	//testEP(PatternFactory.EMAIL_EPATTERN, "365go@365go.com.cn��ѯ");
    	//test.testOR();
/*    	System.out.println(Character.isLetter((int)'��'));
    	System.out.println(Character.isLetter((int)','));
    	System.out.println(Character.isLetter((int)')'));
    	System.out.println(Character.isLetter((int)'��'));
    	System.out.println(Character.isLetter((int)'��'));
    	System.out.println(Character.isLetter((int)'��'));
*/
    }
}
