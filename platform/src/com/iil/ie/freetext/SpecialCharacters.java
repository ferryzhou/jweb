package com.iil.ie.freetext;

public class SpecialCharacters {
	
	public static boolean isSpecialCharacter(char c) {
		return s.indexOf(c) != -1;
	}
	
	private static String s = "([{\\^$|)?*+.";
}