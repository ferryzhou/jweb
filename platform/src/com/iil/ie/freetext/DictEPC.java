package com.iil.ie.freetext;

import java.io.*;
import java.util.*;

/**
 * every line is a regex.
 */
public class DictEPC implements EPatternConstructor{
	
	public DictEPC(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		List patterns = new ArrayList();
		while ((line = br.readLine()) != null) {
			patterns.add(new RegexEPattern(line));
		}
		orep = new OREPattern(patterns);
	}
	
	public EPattern getEPattern() {
		return orep;
	}
	
	private OREPattern orep;
}
