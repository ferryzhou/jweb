package com.iil.util.web;

import java.io.*;
import com.iil.util.IO;

/**
 *
 * 
 */
public class DHTMLHttpClient implements HttpClient{	

	public void connect(String httpURL) throws IOException{
		
		String outfile = (i++) + ".htm";
		String lockfile = outfile + ".url";
        String command = execfile + " " + httpURL + "@@" + outfile + "";
        //System.out.println (command);
		Process child = Runtime.getRuntime().exec(command);
		try {
			child.waitFor();
			//System.out.println ("end...");
		}catch(InterruptedException e) {
			System.out.println (e);
		}
		
		IO.readWriteAll(new FileInputStream(outfile), buffer);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		IO.readWriteAll(new FileInputStream(lockfile), bs);
		referer = bs.toString();
		if (referer.length() == 0) referer = httpURL;
	}
	
	public void connect(String httpURL, String postData) throws IOException{
		connect(httpURL);
	}
	
	public String getURL() {
		return referer;
	}
	
	public String getContent() {
		return buffer.toString();
	}
	
	public InputStream getInputStream() throws IOException{
		return new ByteArrayInputStream(buffer.toByteArray());
	}
	
	public String getEncoding() {
		
		return "gb2312";
	}

	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private String referer;
	
	public static String execfile = "F:\\zj\\work\\Project\\GetHtml\\Debug\\GetHtml";
	
	private static int i;
}

