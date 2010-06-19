package com.iil.web.ontopof;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;

import com.iil.util.web.*;

import java.io.*;

import com.iil.web.rssgen.*;
import com.iil.web.jrssreader.*;

class RSSFeedReader {

	public JRSS getJRSS(String url) throws Exception {
		HttpClient hc = new ApacheHttpClient();
		hc.connect(url);
		
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(hc.getInputStream(), hc.getEncoding()));
        
        JRSS jrss = new JRSS();
        jrss.title = feed.getTitle();
        jrss.description = feed.getDescription();
        jrss.creator = feed.getAuthor();
        jrss.link = feed.getLink();
        jrss.language = feed.getLanguage();
        for(Object entry : feed.getEntries()) {
        	jrss.items.add(entryToNewsItem((SyndEntry)entry));
        }
        return jrss;
	}
	
	private NewsItem entryToNewsItem(SyndEntry entry) {
		NewsItem item = new NewsItem();
		item.author = entry.getAuthor();
		item.description = entry.getDescription().getValue();
		item.title = entry.getTitle();
		item.url = entry.getLink();
		item.pubDate = entry.getPublishedDate();
		return item;
	}
	
    public static void main(String[] args) {
        boolean ok = false;
        if (args.length==1) {
            try {
/*				HTMLWrapper hw = new HTMLWrapper(new ApacheHttpClient(), args[0]);
				String content = hw.getContent();
				
				System.out.println (content);

                URL feedUrl = new URL(args[0]);
                SyndFeedInput input = new SyndFeedInput();

                //SyndFeed feed = input.build(new XmlReader(feedUrl));
                SyndFeed feed = input.build(new XmlReader(hw.getHttpClient().getInputStream(), hw.getHttpClient().getEncoding()));

                System.out.println(feed);
*/
				RSSFeedReader fr = new RSSFeedReader();
				JRSS jrss = fr.getJRSS(args[0]);
				System.out.println (jrss);
				
                ok = true;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }
        }

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }
}
