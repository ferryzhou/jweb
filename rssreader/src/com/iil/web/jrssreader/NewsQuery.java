/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.sql.SQLException;

import com.iil.util.IO;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewsQuery {

	public List<NewsCluster> query(String condition) {
		List<NewsCluster> clusters = new ArrayList<NewsCluster>();
		HashMap<Integer, NewsCluster> hashClusters = new HashMap<Integer, NewsCluster>();
		try {
			List<NewsItem> oitems = ArticleStore.getInstance().getAllItemsWhere(condition);
			for (NewsItem item : oitems) {
				NewsCluster cluster = hashClusters.get(item.cluster);
				if (cluster == null) {
					hashClusters.put(item.cluster, new NewsCluster(item));
				} else {
					cluster.addItem(item);
				}
			}
			clusters.addAll(hashClusters.values());
			for (NewsCluster cluster : clusters) {
				cluster.sort();
			}
			Collections.sort(clusters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clusters;
	}
	
	public void toRSS(List<NewsCluster> clusters, String title, String outfile) {
		String patternFile = "out_tmpl/rss2_tmpl.xml";
		try {
			String pattern = IO.getContent(patternFile).trim();
			//System.out.println(pattern);
			pattern = pattern.replaceAll("==Title==", title);
			pattern = pattern.replaceAll("==UpdateTime==", rss2OutDateFormat.format(new Date()));
			pattern = pattern.replaceAll("==NewsItems==", toRSSContent(clusters));
			//System.out.println(pattern);
			FileWriter fos = new FileWriter(outfile);
			fos.write(pattern);
			fos.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}		
	}
	
	public void toHTML(List<NewsCluster> clusters, String title, String outfile) {
		String patternFile = "out_tmpl/html_tmpl.htm";
		try {
			String pattern = IO.getContent(patternFile).trim();
			//System.out.println(pattern);
			pattern = pattern.replaceAll("==Title==", title);
			pattern = pattern.replaceAll("==UpdateTime==", dateFormat.format(new Date()));
			pattern = pattern.replaceAll("==NewsItems==", toHTMLContent(clusters));
			System.out.println(pattern);
			FileWriter fos = new FileWriter(outfile);
			fos.write(pattern);
			fos.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private String toRSSContent(List<NewsCluster> clusters) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (NewsCluster cluster : clusters) {
			if ( i > 19) break;
			NewsItem headItem = cluster.getEarliestItem();
			sb.append("<item>");
			sb.append("<title>" + headItem.title + "</title>");
			sb.append("<link>" + headItem.url + "</link>");
			sb.append("<description><![CDATA[ <p>");
			for (NewsItem child : cluster.getItems()) {
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<A href=" + child.url + " target='_blank'>");
				sb.append(child.title);
				sb.append("</A>&nbsp;<FONT size='-1'><FONT color='#6f6f6f'>");
				sb.append(child.author + " " + dateFormat.format(child.pubDate));
				sb.append("</FONT><BR>");
			}
			sb.append("<p>]]></description>");
			sb.append("<pubDate>" + rss2OutDateFormat.format(headItem.pubDate) + "</pubDate>");
			sb.append("<dc:creator>" + headItem.author + "</dc:creator>");
			sb.append("</item>\r\n");
			i++;
		}
		return sb.toString();
	}
	
	private String toHTMLContent(List<NewsCluster> clusters) {
		StringBuffer sb = new StringBuffer();
		//sb.append("<tr><td>");
		int i = 0;
		for (NewsCluster cluster : clusters) {
			if ( i > 50) break;
			NewsItem headItem = cluster.getEarliestItem();
			//sb.append("<p>");
			sb.append("<tr><td><span>");
			sb.append("<font color=#009700><A href=" + headItem.url + " target='_blank'>");
			sb.append(headItem.title);
			sb.append("</A></font>&nbsp;<FONT size='-1'><FONT color='#6f6f6f'>");
			sb.append(headItem.author + " " + dateFormat.format(headItem.pubDate));
			sb.append("</FONT>");
/*			for (NewsItem child : cluster.getItems()) {
				sb.append("<BR>");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<A href=" + child.url + " target='_blank'>");
				sb.append(child.title);
				sb.append("</A>&nbsp;<FONT size='-1'><FONT color='#6f6f6f'>");
				sb.append(child.author + " " + dateFormat.format(child.pubDate));
				sb.append("</FONT>");
			}
*/			//sb.append("</p>");
			i++;
			sb.append("</span></tr></td>\r\n");
		}
		//sb.append("</td></tr>\r\n");
		return sb.toString();
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd ");
	private static SimpleDateFormat rss2OutDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	static {
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		rss2OutDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}
	
	public static void main(String[] args) throws Exception{
		ArticleStore store = ArticleStore.getInstance();
		store.connect();

		NewsQuery nq = new NewsQuery();
		List<NewsCluster> clusters = nq.query("1");
		for (NewsCluster cluster : clusters) {
			System.out.println(cluster.size() + " : " + cluster.getEarliestItem());
			for (NewsItem child : cluster.getItems()) {
				System.out.println("\t" + child);
			}
			System.out.println();
		}
		
		//nq.toHTML(items, "Google", "test_out/Google.htm");
		//nq.toRSS(items, "Google", "test_out/sina_google201.xml");
		//nq.toHTML(items, "Microsoft", "test_out/Microsoft.htm");
		//nq.toRSS(items, "Microsoft", "test_out/iask_Microsoft.xml");
		nq.toHTML(clusters, "»¥ÁªÍøÏûÏ¢", "test_out/Internet.htm");
		
		store.close();
	}
}

