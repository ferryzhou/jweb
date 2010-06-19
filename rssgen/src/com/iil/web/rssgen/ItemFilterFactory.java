/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.io.IOException;
import java.util.*;

import org.w3c.dom.Element;

import com.iil.util.Filter;
import com.iil.util.xml.DOMInfoExtractor;
/**
 * @author ferry zhou
 *
 *   <filter>
 *     <field>fieldname</field>
 *     <keywords>filename</keywords>
 *   </filter>
 */
public class ItemFilterFactory {
	
	public Filter load(Element e) throws ParseException{
		String field = DOMInfoExtractor.extractString(e, "field");
		try {
			String filename = DOMInfoExtractor.extractString(e, "keywords");
			Set keywords = getKeywords(filename);
			if (field != null && keywords.size() != 0) return new KeywordsBasedItemFilter(field, keywords);
			else return null;
		}catch (IOException ex) {
			throw new ParseException(ex.getMessage());
		}
	}

	private Set getKeywords(String filename) throws IOException {
		String content = com.iil.util.IO.getContent(filename);
		Set keywords = new HashSet();
		StringTokenizer st = new StringTokenizer(content);
		while (st.hasMoreTokens()) {
			keywords.add(st.nextToken());
		}
		return keywords;
	}
}
