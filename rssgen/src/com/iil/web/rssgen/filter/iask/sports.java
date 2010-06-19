/*
 * Created on 2006-5-12
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
public class sports implements Filter {
	
	public boolean accept(Object o) {
		if (o instanceof Element) {
			String s = DOMInfoExtractor.extractString((Element)o, "url");
			if (s != null) {
				if (s.contains("f1")) return false;
				return s.contains("sports");
			}
			else return false;
		} else return false;
	}
	
}
