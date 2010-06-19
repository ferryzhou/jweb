package com.iil.ie.freetext;

/**
 * <p>
 * There may be white spaces between the characters of the string.
 * But the num of the white spaces between each two characters is less than 3;
 * </p>
 */
public class SeperatedLiteralStringEPC implements EPatternConstructor{
	
	public SeperatedLiteralStringEPC(String string) {
		this.string = string;
	}
	
	public EPattern getEPattern() {
		return new RegexEPattern(getPatternString());
	}
	
	/**
     * assumpt the string dosn't contain "\E"
     */
	public String getPatternString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<string.length(); i++) {
			if (i != 0) sb.append(WHITESPACES);
			char c = string.charAt(i);
			if (SpecialCharacters.isSpecialCharacter(c)) {
				sb.append("\\" + c);
			}else sb.append(c);
		}
		return sb.toString();
	}
	
	private static String WHITESPACES = "\\s{0,4}";
	private String string;
}
