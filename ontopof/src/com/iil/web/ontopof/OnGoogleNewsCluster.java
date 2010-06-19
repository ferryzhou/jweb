/*
 * Created on 2006-7-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.ontopof;

import java.io.*;
import java.util.*;
import java.text.*;

import org.w3c.dom.*;

import com.iil.util.web.*;
import com.iil.ie.xmlbased.*;
import com.iil.ie.*;
import com.iil.util.*;
import com.iil.util.xml.*;

import com.iil.word.impl_seg.*;

import org.apache.commons.httpclient.*;

import com.iil.web.jrssreader.*;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OnGoogleNewsCluster {

	public void run(String url) throws Exception {

		HTMLWrapper hw = new HTMLWrapper(new ApacheHttpClient(), url);
		String content = hw.getContent();
		
		System.out.println (content);

		WordDict dict = WordDict.getInstance();
		String dictfile = "I:\\backup\\dell laptop\\zj\\eclipseprojects\\word\\dict\\dict1.txt";
		dict.load(dictfile);
		
		OneSegmenter segmenter = new OneSegmenter(dict);
		
		ContentAnalysis ca = new ContentAnalysis(segmenter, 5);
		List<Map.Entry> wordCounts = ca.getWordCount(new StringReader(content));
		ObjectNumberList wordTopicRelativeness = new ObjectNumberList();
    	for (Map.Entry entry : wordCounts) {
    		String key = (String)entry.getKey();
    		int totalFreq = GoogleFrequenceExtractor.getNewsFrequency(key);
    		if (totalFreq == 0) continue;
    		int freq = ((WordWatcher.Int)entry.getValue()).getValue();
    		double after = (double)freq / totalFreq * 100;
    		wordTopicRelativeness.addPair(key, after);
    		System.out.println(entry.getKey() + " : " + entry.getValue() + " " + totalFreq + " " + after);
    	}
    	
    	List<ObjectNumberList.ObjectNumberPair> spairs = wordTopicRelativeness.getSortPairs();
    	for (ObjectNumberList.ObjectNumberPair pair : spairs) {
    		System.out.println (pair);
    	}
    	
    	//=====================================
    	
    	String outfile = "out2.htm";
    	String rssurl = url + "&output=rss";
    	RSSFeedReader fr = new RSSFeedReader();
    	JRSS jrss = fr.getJRSS(rssurl);
    	String title = jrss.title;
    	String newsContent = ((NewsItem)jrss.items.get(0)).description;
    	
    	List<String> links = new ArrayList<String>();
    	links.add(((NewsItem)jrss.items.get(0)).url);
    	
    	String blogContent = "";
    	
    	int i = 0;
    	for (ObjectNumberList.ObjectNumberPair pair : spairs) {
    		System.out.println (pair);
    		String wordURL = "http://news.google.com/news?hl=zh-CN&ned=&ie=UTF-8&q=" + pair.key+ "&output=rss";
    		wordURL = (new URI(wordURL, false)).toString();
	    	JRSS jrss2 = fr.getJRSS(wordURL);
	    	System.out.println (wordURL);
	    	System.out.println (jrss2);
	    	for (int j = 0; j < 3; j++) {
	    		NewsItem item = (NewsItem)jrss2.items.get(j);
	    		if (!links.contains(item.url)) {
		    		newsContent += item.description;
		    		links.add(item.url);
	    		}
	    	}

    		i++;
    		if (i > 10) break;
    	}
    	
    	i = 0;
    	for (ObjectNumberList.ObjectNumberPair pair : spairs) {
    		String wordBlogURL = "http://blogsearch.google.com/blogsearch_feeds?hl=en&q=" + pair.key+ "&ie=utf-8&num=10&output=rss";
    		wordBlogURL = (new URI(wordBlogURL, false)).toString();
	    	JRSS jrss2 = fr.getJRSS(wordBlogURL);
	    	System.out.println (wordBlogURL);
	    	System.out.println (jrss2);
	    	for (int j = 0; j < 3; j++) {
	    		NewsItem item = (NewsItem)jrss2.items.get(j);
	    		if (!links.contains(item.url)) {
		    		blogContent += blogItemToHTML(item);
		    		links.add(item.url);
	    		}
	    	}
    		i++;
    		if (i > 7) break;
    	}
    		
		String patternFile = "../out_tmpl/html_tmpl.htm";
		try {
			String pattern = IO.getContent(patternFile).trim();
			//System.out.println(pattern);
			pattern = pattern.replaceAll("==Title==", title);
			pattern = pattern.replaceAll("==UpdateTime==", dateFormat.format(new Date()));
			pattern = pattern.replaceAll("==NewsItems==", newsContent);
			pattern = pattern.replaceAll("==NewsItems2==", blogContent);
			System.out.println(pattern);
			FileWriter fos = new FileWriter(outfile);
			fos.write(pattern);
			fos.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
    	
	}

	String blogItemToHTML(NewsItem item) {
		return "<br><table><tr><td>" 
			+ "<br/>" 
			+ "<a href = '" + item.url + "'>" + item.title + "</a>"
			+ "<br/>"
			+ "<p>" + item.description + "</p>"
			+" </td></tr></table>";
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd ");
	
	public static void main(String[] args) throws Exception {
		//String url = "http://news.google.cn/news?ned=ccn&ncl=http://sports.sohu.com/20060717/n244285928.shtml&hl=zh-CN";
		//String url = "http://news.baidu.com/n?cmd=2&page=%68%74%74%70%3a%2f%2f%73%70%6f%72%74%73%2e%71%71%2e%63%6f%6d%2f%61%2f%32%30%30%36%30%37%31%37%2f%30%30%30%30%33%38%2e%68%74%6d&pn=1&clk=crel&cls=sportnews&where=focuspage";
		//String url = "http://news.baidu.com/ns?word=Google&ie=gb2312&bs=Google&sr=0&cl=2&rn=20&tn=news&ct=1&clk=sortbyrel";
		//String url = "http://news.google.com/news?ned=cn&ncl=http://www.ce.cn/cysc/ceit/ityj/200607/18/t20060718_7780886.shtml&hl=zh-CN";
		//String url = "http://news.google.com/news?hl=zh-CN&ned=cn&q=%E7%99%BE%E5%BA%A6+%E6%90%9C%E7%8B%90&btnG=%E6%90%9C%E7%B4%A2%E8%B5%84%E8%AE%AF";
		//String url = "http://news.google.com/news?ned=us&ncl=http://www.bloomberg.com/apps/news%3Fpid%3D20601087%26sid%3Da56GrpkOnwrU%26refer%3Dhome&hl=en";
		//String url = "http://blogsearch.qihoo.com/?kw=%BA%CB%CA%D4%D1%E9&type=";

		OnGoogleNewsCluster t = new OnGoogleNewsCluster();
		t.run(args[0]);
	}
	
	
	public static String[] stopList = {"...", "Сʱ"};
}
