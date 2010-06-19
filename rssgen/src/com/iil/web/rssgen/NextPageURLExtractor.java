/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import org.w3c.dom.Document;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface NextPageURLExtractor {

	/**
	 * 
	 * @param doc current page's document object
	 * @return url of next page. null if can't find.
	 */
	public String nextPageURL(Document doc);
}
