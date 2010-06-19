package com.iil.util.web;

import java.io.*;
import java.net.*;

import com.iil.util.IO;

public class Downloader {
	
	public static void downloadFile(String url, String out) throws IOException {
		URL curl = new URL(url);
		URLConnection con = curl.openConnection();
		con.connect();
		InputStream is = con.getInputStream();
		OutputStream os = new FileOutputStream(out);
		IO.readWriteAll(is, os);
		is.close();
		os.close();		
	}

	/**
     * @return filename of the image
     */
	private String download(String url, String outpath) throws IOException{
		URL curl = new URL(url);
		URLConnection con = curl.openConnection();
		con.connect();
		InputStream is = con.getInputStream();
		String filename = URLUtil.getFilename(url.toString());
		String out;
		if (outpath != null) out = outpath + filename;
		else out = filename;
		OutputStream os = new FileOutputStream(out);
		IO.readWriteAll(is, os);
		is.close();
		os.close();
		return filename;
	}
}
