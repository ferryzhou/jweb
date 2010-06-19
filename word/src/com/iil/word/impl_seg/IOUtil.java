package com.iil.word.impl_seg;

import java.util.*;
import java.io.*;

class IOUtil {
	
	public static void outEntries(String filename, List<Map.Entry> entries) {
		try {
	    	BufferedWriter bw = new BufferedWriter (new FileWriter(filename));
	    	for (Map.Entry entry : entries) {
	    		bw.write(entry.getKey() + " : " + entry.getValue());
	    		bw.newLine();
	    	}
	    	bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
