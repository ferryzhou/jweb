package com.iil.ie.freetext;

/**
 * [pattern][ :：\r\n]?[text][^letter]
 * 
 * <p>
 * The information is located by the string before it.<br/> 
 * For example, as to the string: "地址：北京市西城区 ", we can locate the address 
 * data by "地址", which is just before the data and seems to be not change. 
 * </p>
 * 
 * 
 */
public class PreEPattern extends EPattern {
	public PreEPattern(EPattern pattern)
	{
		this.p=pattern;
	}
	
    public boolean doFind(int start) {
		boolean find = p.find(start);
		if (find) group = nextToken(p.end());
		else group = null;
		return find;
    }

	public void resetInput(CharSequence cs) {
		//this.cs = cs;
		super.resetInput(cs);
		p.resetInput(cs);
	}
	
	
	/**
     * start from index, look for the next token.
     */
	private String nextToken(int index) {
		//System.out.println ("index: " + index);
		from = nextNonDelimiter(index);
		to = nextDelimiter(from);
		int len = super.getCharSequence().length();
		if (from == len) return null;
		return super.getCharSequence().subSequence(from, to).toString();
	}
	
	private int nextNonDelimiter(int index) {
		int i = index;
		while (i < super.getCharSequence().length() && isDelimiter(super.getCharSequence().charAt(i))) {
			i++;
		}
		return i;
	}
	
	private int nextDelimiter(int index) {
		int i = index;
		while (i < super.getCharSequence().length() && isLetter((int)super.getCharSequence().charAt(i))) {
			i++;
		}
		return i;
	}

	private boolean isDelimiter(char c) {
		for (int i = 0; i<delimiters.length(); i++) {
			if (delimiters.charAt(i) == c) return true;
		}
		return false;
	}
	
	private boolean isLetter(int c) {
		return Character.isLetter(c);
	}
	
	private EPattern p;
	private static final String delimiters = " :：\r\n";
}