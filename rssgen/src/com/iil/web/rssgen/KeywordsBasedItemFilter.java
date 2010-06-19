/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.util.*;
import org.w3c.dom.*;

import com.iil.util.Filter;
import com.iil.util.xml.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KeywordsBasedItemFilter implements Filter{

	public KeywordsBasedItemFilter(String field, Set keywords) {
		this.field = field;
		this.keywords = keywords;
	}
	//if e.field is in keywords
	public boolean accept(Object o) {
		if (o instanceof Element) {
			String s = DOMInfoExtractor.extractString((Element)o, field);
			if (s != null) return keywords.contains(s);
			else return false;
		} else return false;
	}
	
	private String field;
	//private String keywordsFile;
	
	private Set keywords;
}
