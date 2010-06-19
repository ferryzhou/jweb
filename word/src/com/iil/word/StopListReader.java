package com.iil.word;

import java.io.*;
import java.util.*;

public class StopListReader {
	
	public static List load(String file) throws IOException {
		List l = new LinkedList();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			l.add(line.trim());
		}
		return l;
	}
}
