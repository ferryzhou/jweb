/*
 * Created on 2006-2-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SinaURLDateConverter  extends DateConverter{
	
	public SinaURLDateConverter(SimpleDateFormat outDateFormat) {
		super(outDateFormat);
	}
	
	public void setTimeZone(String timezone) {
	}
	
	public String convert(String date) {
		try {
			if (date == null || date.trim().length() == 0) return outDateFormat.format(new Date());
			int i0 = date.lastIndexOf("/");
			int i1 = date.lastIndexOf("/", i0 - 1);
			//System.out.print("i0 - i1: " + (i0- i1));
			SimpleDateFormat dfm = dateFormat1;
			String ds = date.substring(i0 - 10, i0 + 5); // yyyy-MM-dd/HHmm
			if (i0 - i1 < 11) {
				dfm = dateFormat2;
				ds = date.substring(i0 - 8, i0 + 5);
			}
			Date newdate = dfm.parse(ds);
        	String s = outDateFormat.format(newdate);
        	return s;
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}
	
	private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd/HHmm");
	private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd/HHmm");
	static {
		dateFormat1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		dateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}	
}
