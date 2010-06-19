/*
 * Created on 2004-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.util.List;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SizeBasedStoper implements Stoper{

	public SizeBasedStoper(int size) {
		this.threshold = size;
	}
	
	//List<Element>
	public boolean check(List es) {
		return es.size() >= threshold;
	}
	
	private int threshold;
}
