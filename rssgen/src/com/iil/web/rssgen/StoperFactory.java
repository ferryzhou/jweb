/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import org.w3c.dom.Element;

import com.iil.util.xml.*;
/**
 * @author ferry zhou
 *
 * <stop>
 *   size based:
 *   <size>20</size>
 *   date based:
 *   <date>
 * <stop>
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StoperFactory {
	
	public Stoper load(Element e) throws ParseException{
		String size = DOMInfoExtractor.extractString(e, "size");
		if (size != null) {
			try {
				int n = Integer.parseInt(size);
				return new SizeBasedStoper(n);
			}catch(NumberFormatException ex) {
				throw new ParseException(ex.getMessage());
			}
		}
		throw new ParseException("no stoper found");
	}

}
