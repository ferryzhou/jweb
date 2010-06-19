/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import org.w3c.dom.*;

import com.iil.util.xml.DOMInfoExtractor;
/**
 * @author ferry zhou
 * 
 * <nextlink>
 * KeywordBased:
 * <keyword>xxx</keyword>
 * </nextlink>
 */
public class NextPageURLExtractorFactory {
	
	//dynamic create instance based on e
	public NextPageURLExtractor load(Element e) throws ParseException{
		String keyword = DOMInfoExtractor.extractString(e, "keyword");
		if (keyword != null) return new KeywordBasedNextPageURLExtractor(keyword);
		else return null;
	}
}
