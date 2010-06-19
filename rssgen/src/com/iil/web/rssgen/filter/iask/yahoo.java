/*
 * Created on 2006-5-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen.filter.iask;

import org.w3c.dom.Element;

import com.iil.util.Filter;
import com.iil.util.xml.DOMInfoExtractor;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class yahoo implements Filter {
	
	public boolean accept(Object o) {
		if (o instanceof Element) {
			String s = DOMInfoExtractor.extractString((Element)o, "author");
			if (s != null) return !(s.trim().equals("ÑÅ»¢"));
			else return false;
		} else return false;
	}
	
}
