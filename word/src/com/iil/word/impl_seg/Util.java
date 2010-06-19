package com.iil.word.impl_seg;

import java.util.*;
import java.io.*;

class Util {

	public static interface LineProcessor {
		public void process(String line);
	}	
	
	public static void forEachLineInFile(String filename, LineProcessor lp) {
		try {
    		BufferedReader br = new BufferedReader(new FileReader(filename));
    		String line;
    		while ((line = br.readLine()) != null) {
    			lp.process(line.trim());
    		}
    		br.close();
    		System.out.println ("load finished .....");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
}
