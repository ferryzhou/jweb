/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryTask {

	public String type;
	public String title;
	public String condition;
	public String outfile;
	
	public String toString() {
		return title + " " + outfile;
	}
}
