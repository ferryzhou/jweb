/*
 * Created on 2006-4-26
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
public class NewsSource {

	public int id;
	public String type;
	public String uri;
	public String title;
	public String tags;
	public String language;
	public String creator;
	
	public String toString() {
		return id + " " + title + " " + uri;
	}
}
