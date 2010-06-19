/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import org.w3c.dom.Document;

import com.iil.util.xml.DOMInfoExtractor;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KeywordBasedNextPageURLExtractor implements NextPageURLExtractor{

	public KeywordBasedNextPageURLExtractor(String keyword) {
		this.keyword = keyword;
	}
	
	//if can't find, return null;
	public String nextPageURL(Document doc) {
		return DOMInfoExtractor.extractString(doc.getDocumentElement(), "//a[.='…œ“ª“≥'][1]/@href");		
	}
	
	private String keyword;
}
