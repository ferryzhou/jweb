/*
 * Created on 2004-12-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.io.*;
import java.text.*;
import java.util.*;

import org.w3c.dom.*;

import com.iil.ie.xmlbased.*;
import com.iil.ie.*;
import com.iil.util.Filter;
import com.iil.util.xml.*;
/**
 * @author ferry zhou
 *
 * List Extractor Config
 * LEConfig:
 * 1. xsl for each page
 * 2. item filter. based on field and keywords. if empty or null then no filter. 
 * 3. next page link. word of "…œ“ª“≥" or number.
 * 4. Stop condition. based on number of extracted items or number of pages or time.
 * 
 * <leconfig>
 *   <xsl>filename</xsl>
 *   <filter>
 *     <field>fieldname</field>
 *     <keywords>filename</keywords>
 *   </filter>
 *   <nextlink>
 *     <keyword>xxx</keyword>
 *   </nextlink>
 *   <stop>
 *     <size>xx</size>
 *   <stop>
 * </leconfig>
 */
public class LEConfig {

	public void load(String filename) throws IOException, ParseException, InformationExtractionException{
		Document doc = DOMBuilder.build(filename);
		load(doc.getDocumentElement(), null);
	}

	public void loadFromDir(String dir) throws IOException, ParseException, InformationExtractionException{
		System.out.println("dir: " + dir);
		Document doc = DOMBuilder.build(dir + "/lec.txt");
		load(doc.getDocumentElement(), dir);
	}
	
	public void load(Element e, String dir) throws ParseException, InformationExtractionException{
		assumeNodeName(e, "leconfig");
		
		String xsl = DOMInfoExtractor.extractString(e, "xsl");
		if (xsl == null) throw new ParseException("no xsl!");
		if (dir != null) xsl = dir + "/" + xsl;
		xie = new XInformationExtractor(xsl);
		
		Element filterElement = getFirstElement(e, "filter");
		if (filterElement != null) filter = (new ItemFilterFactory()).load(filterElement);
		
		Element ne = getFirstElement(e, "nextlink");
		if (ne != null) npue = (new NextPageURLExtractorFactory()).load(ne);
		
		Element se = getFirstElement(e, "stop");
		if (se != null) stoper = (new StoperFactory()).load(se);

		String rssxsl = DOMInfoExtractor.extractString(e, "rssxsl");
		if (rssxsl == null) throw new ParseException("no rssxsl!");
		if (dir != null) rssxsl = dir + "/" + rssxsl.trim();
		rssXie = new XInformationExtractor(rssxsl);
		
		String dateformat = DOMInfoExtractor.extractString(e, "dateformat");
		String locale = DOMInfoExtractor.extractString(e, "dateformat/@locale");
		String timezone = DOMInfoExtractor.extractString(e, "dateformat/@timezone");
		String function_id = DOMInfoExtractor.extractString(e, "dateformat/@function_id");
		System.out.println("function_id: "+ function_id);
		
		if (dateformat != null)	{
			if (function_id == null) {
				dateConverter = new SimpleFormatDateConverter(rss2OutDateFormat, dateformat, locale);
				if (timezone != null) dateConverter.setTimeZone(timezone);
			} else {
				if (function_id.equals("sina_url")) dateConverter = new SinaURLDateConverter(rss2OutDateFormat);
			}
		}
		System.out.println("dateConverter: "+ dateConverter);
		
	}
	
	private Locale getLocale(String s) {
		if (s == null) return Locale.ENGLISH;
		if (s.equals("en")) return Locale.ENGLISH;
		if (s.equals("cn")) return Locale.CHINESE;
		return Locale.ENGLISH;
	}
	
	/**
	 * get child element by name
	 * @param e
	 * @param name
	 * @return first element if found. null if no one found.
	 */
	private Element getFirstElement(Element e, String name) {
		NodeList nl = e.getElementsByTagName(name);
		if (nl.getLength() == 0) return null;
		else return (Element)nl.item(0);
	}
	
	private void assumeNodeName(Element e, String name) {
		if (!e.getNodeName().equals(name)) throw new IllegalArgumentException("not " + name);
	}
	/**
	 * @return Returns the itemFilter.
	 */
	public Filter getItemFilter() {
		return filter;
	}
	/**
	 * @return Returns the npue.
	 */
	public NextPageURLExtractor getNpue() {
		return npue;
	}
	/**
	 * @return Returns the stoper.
	 */
	public Stoper getStoper() {
		return stoper;
	}
	/**
	 * @return Returns the xie.
	 */
	public XInformationExtractor getXie() {
		return xie;
	}
	
	public DateConverter getDateConverter() {
		return dateConverter;
	}
	
	public boolean reverse() {
		return reverse;
	}
	
	public XInformationExtractor getRssXie() {
		return rssXie;
	}
	
	public static DateFormat getOutDateFormat() {
		return rss2OutDateFormat;
	}
	
	private boolean reverse = false;
	private XInformationExtractor xie;
	private Filter filter;
	private NextPageURLExtractor npue;
	private Stoper stoper;
	
	private XInformationExtractor rssXie;
	
	private DateConverter dateConverter;
	private static SimpleDateFormat rss2OutDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	
	static {
		rss2OutDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}
}
