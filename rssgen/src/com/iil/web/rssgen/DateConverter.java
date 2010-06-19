/*
 * Created on 2006-2-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.text.*;
import java.util.*;

/**
 * @author ferry zhou
 *
 * src string -> dst string
 * 1) simply parse, from one format to another format
 * 2) may need specific extraction.
 */
public abstract class DateConverter {
	
	public DateConverter(SimpleDateFormat outDateFormat) {
		this.outDateFormat = outDateFormat;
	}
	
	public abstract void setTimeZone(String s);
	
	public abstract String convert(String src);
	
	protected SimpleDateFormat outDateFormat;
}
