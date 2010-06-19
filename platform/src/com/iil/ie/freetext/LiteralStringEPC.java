package com.iil.ie.freetext;

public class LiteralStringEPC implements EPatternConstructor{
	
	public LiteralStringEPC(String string) {
		this.regex = "\\Q" + string + "\\E";
	}
	
	public EPattern getEPattern() {
		return new RegexEPattern(regex);
	}

	public String regex;
}
