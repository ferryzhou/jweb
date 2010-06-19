/*
 * Created on 2005-2-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.io.*;

import org.w3c.dom.*;

import com.iil.ie.xmlbased.*;
import com.iil.ie.*;
import com.iil.util.Filter;
import com.iil.util.IO;
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
 * <rssgenconfig>
 *   <title>Google</title>
 *   <lecdir>iask</lecdir>  //search from lec_lib/
 *   <url>
 *     <patternfile>query_pattern.txt</patternfile>  //search from lecdir
 *     <keyword>Google</keyword>
 *   </url>
 *   <filter type="class">com.iil.web.rssgen.filter.iask.yahoo</filter>
 *   <outfile>iask_Google.xml</outfile>  //from out/
 * </rssgenconfig>
 * 
 * or
 * <url>
 *   <link>xxxx</link>
 * </url>
 */
public class RSSGenConfig {

	public void load(String filename) throws IOException, ParseException, InformationExtractionException{
		Document doc = DOMBuilder.build(filename);
		load(doc.getDocumentElement());
	}
	
	public void load(Element e) throws ParseException, InformationExtractionException{
		assumeNodeName(e, "rssgenconfig");
		
		title = DOMInfoExtractor.extractString(e, "title");
		if (title == null) throw new ParseException("no title!");
		
		lecdir = DOMInfoExtractor.extractString(e, "lecdir");
		if (lecdir == null) throw new ParseException("no lecdir!");
		lecdir = "lec_lib/" + lecdir.trim();

		Element eurl = getFirstElement(e, "url");
		if (eurl == null) throw new ParseException("no url!");
		String link = DOMInfoExtractor.extractString(eurl, "link");
		if (link != null) {
			url = link.trim();
		} else {
			String patternFile = DOMInfoExtractor.extractString(eurl, "patternfile");
			if (patternFile == null) throw new ParseException("no patternfile!");
			patternFile = lecdir + "/" + patternFile.trim();
			try {
				String urlpattern = IO.getContent(patternFile).trim();
				String keyword = DOMInfoExtractor.extractString(eurl, "keyword");
				String keyword2 = DOMInfoExtractor.extractString(eurl, "keyword2");
				url = getURL(urlpattern, keyword, keyword2);
			} catch (IOException ie) {
				throw new ParseException("url pattern file open failed! " + ie.getMessage());
			}
		}
		outfile = DOMInfoExtractor.extractString(e, "outfile");
		if (title == null) throw new ParseException("no outfile!");	
		outfile.trim();
		
		Element efilter = getFirstElement(e, "filter");
		if (efilter != null && efilter.getAttribute("type").equals("class")) {
			String classname = DOMInfoExtractor.extractString(e, "filter");
			if (classname != null) {
				try {
					filter = (Filter)(Class.forName(classname).newInstance());
				} catch (Exception ex) {
					System.out.println("can't find class " + classname);
				}
			}
		}
	}
	
	private void assumeNodeName(Element e, String name) {
		if (!e.getNodeName().equals(name)) throw new IllegalArgumentException("not " + name);
	}

	private Element getFirstElement(Element e, String name) {
		NodeList nl = e.getElementsByTagName(name);
		if (nl.getLength() == 0) return null;
		else return (Element)nl.item(0);
	}
	
	private String getURL(String urlPattern, String keyword, String keyword2) {
		if (keyword == null) return urlPattern;
		else {
			String s = null;
			s = urlPattern.replace("$1".subSequence(0, 2), keyword.subSequence(0, keyword.length()));
			if (keyword2 == null) return s;
			else return s.replace("$2".subSequence(0, 2), keyword2.subSequence(0, keyword2.length()));
		}
	}
	
	public static void main(String[] args) throws Exception{
		//String task = "task_lib/iask_title/Google.tsk";
		String task = "task_lib/iask_title/Yahoo.tsk";
		if (args.length != 0) task = "task_lib/iask_title/" + args[0];
		RSSGenConfig rgc = new RSSGenConfig();
		rgc.load(task);
		RSSGenerator rssg = new RSSGenerator(rgc.title, rgc.lecdir, rgc.url, "test_out/" + rgc.outfile, rgc.filter);
		rssg.run();		
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLecDir() {
		return lecdir;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getOutFile() {
		return outfile;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	private String title;
	private String lecdir;
	private String url;
	private String outfile;
	
	private Filter filter = null;
}
