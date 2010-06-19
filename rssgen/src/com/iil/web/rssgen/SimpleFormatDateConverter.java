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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimpleFormatDateConverter extends DateConverter{
	
	public SimpleFormatDateConverter(SimpleDateFormat outDateFormat, String formatString, String locale) {
		super(outDateFormat);
		srcDateFormat = new SimpleDateFormat(formatString, getLocale(locale));
	}
	
	public void setTimeZone(String timezone) {
		if (timezone != null) srcDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
	}
	
	public String convert(String date) {
		try {
			if (date == null || date.trim().length() == 0) return "";
			//System.out.println("date: " + date);
			Date newdate = (Date)srcDateFormat.parseObject(date);
			//System.out.println(newdate.getYear());
			if (newdate.getYear() == 70) {
				Date d = new Date();
				if (newdate.getMonth() <= d.getMonth())	newdate.setYear(d.getYear());
				else newdate.setYear(d.getYear()-1);
			}
        	String s = outDateFormat.format(newdate);
        	//s += " GMT";
        	return s;
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}
	
	private Locale getLocale(String s) {
		if (s == null) return Locale.ENGLISH;
		if (s.equals("en")) return Locale.ENGLISH;
		if (s.equals("cn")) return Locale.CHINESE;
		return Locale.ENGLISH;
	}
	
	private SimpleDateFormat srcDateFormat = null;
}
