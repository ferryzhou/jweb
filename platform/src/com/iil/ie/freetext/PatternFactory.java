package com.iil.ie.freetext;

import java.util.regex.*;

public class PatternFactory {
	
	public static String POSTALCODE_REGEX = "\\b\\d{6}\\b";
	public static String AREACODE_REGEX = "\\b\\d{3}\\b";
	public static String TELECODE_REGEX = "\\b\\d{7,8}\\b";
	public static String NUMBER_REGEX = "\\d+";
	public static String ENGLISHWORD_REGEX = "\\w+";
	public static String ENGLISHWORD1_REGEX = "[\\w_-]+";
	public static String EMAIL_REGEX = ENGLISHWORD1_REGEX + "@" + "(" + ENGLISHWORD1_REGEX + "\\.){1,3}" + ENGLISHWORD1_REGEX;
	public static String CHINESECHAR_REGEX = "[\\u4E00-\\u9FA0]";
	public static String NOTCHINESECHAR_REGEX = "[^\\u4E00-\\u9FA0]";
	public static String CHINESESTRING_REGEX = "[\\w" + CHINESECHAR_REGEX + "]+";

	public static String DATENUMBER_REGEX = "\\d{1,4}";
	public static String DATEDELIMITER_REGEX = "[\\\\[ [/[-[\\.[年[月[日]]]]]]]]";
	public static String DATE_REGEX = DATENUMBER_REGEX + DATEDELIMITER_REGEX + DATENUMBER_REGEX + DATEDELIMITER_REGEX + "(" + DATENUMBER_REGEX + DATEDELIMITER_REGEX + ")?";
	
	public static String TIMENUMBER_REGEX = "\\d{1,2}";
	public static String TIMEDELIMITER_REGEX = "[:[点[时[分[秒]]]]]";
	public static String TIME_REGEX = TIMENUMBER_REGEX + TIMEDELIMITER_REGEX + TIMENUMBER_REGEX + TIMEDELIMITER_REGEX + "?" + "("+ TIMENUMBER_REGEX + TIMEDELIMITER_REGEX + ")?";
	
	public static Pattern POSTALCODE_PATTERN = Pattern.compile(POSTALCODE_REGEX);
	public static Pattern AREACODE_PATTERN = Pattern.compile(AREACODE_REGEX);
	public static Pattern TELECODE_PATTERN = Pattern.compile(TELECODE_REGEX);
	public static Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
	public static Pattern ENGLISHWORD_PATTERN = Pattern.compile(ENGLISHWORD_REGEX);
	public static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	public static Pattern CHINESECHAR_PATTERN = Pattern.compile(CHINESECHAR_REGEX);
	public static Pattern CHINESESTRING_PATTERN = Pattern.compile(CHINESESTRING_REGEX);
	public static Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);
	public static Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);

	public static EPattern POSTALCODE_EPATTERN = new RegexEPattern(POSTALCODE_REGEX);
	public static EPattern AREACODE_EPATTERN = new RegexEPattern(AREACODE_REGEX);
	public static EPattern TELECODE_EPATTERN = new RegexEPattern(TELECODE_REGEX);
	public static EPattern NUMBER_EPATTERN = new RegexEPattern(NUMBER_REGEX);
	public static EPattern ENGLISHWORD_EPATTERN = new RegexEPattern(ENGLISHWORD_REGEX);
	public static EPattern EMAIL_EPATTERN = new RegexEPattern(EMAIL_REGEX);
	public static EPattern CHINESECHAR_EPATTERN = new RegexEPattern(CHINESECHAR_REGEX);
	public static EPattern CHINESESTRING_EPATTERN = new RegexEPattern(CHINESESTRING_REGEX);
	public static EPattern DATE_EPATTERN = new RegexEPattern(DATE_REGEX);
	public static EPattern TIME_EPATTERN = new RegexEPattern(TIME_REGEX);
	
	public static EPattern OBSOLETECHINESESTRING_EPATTERN = new RegexEPattern("[[^，。,.]&&[^\\w\\u4E00-\\u9FA0]]("+CHINESESTRING_REGEX+")[[^，。,.]&&[^\\w\\u4E00-\\u9FA0]]", 1);

}