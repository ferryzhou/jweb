/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.iil.util.web.Uploader;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUploader {
	
	private Uploader uploader;
	private boolean closed = false;
	
	public void init() throws Exception{
		uploader = new Uploader("www.myjavaserver.com", 21, "ferryzhou", "jk9710");
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				System.out.println ("shut down.....");
				try {
					if (!closed) close();
				}catch(Exception e) {
					System.out.println (e);
				}
				System.out.println ("end...............");
			}
		});
		System.out.println("ftp connect success...");
	}
	
	public void close() throws Exception {
		uploader.close();
		closed = true;
	}

	public void run() {
		try {
			List<File> fileList = new ArrayList<File>();
			List<File> dirList = new ArrayList<File>();
			File root = new File("out");
			dirList.add(root);
			while (!dirList.isEmpty()) {
				File dir = dirList.remove(0);
				File[] files = dir.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						dirList.add(files[i]);
					} else {
						String dirs = dir.getPath();
						String dst = dirs.substring(4);
						System.out.println(dirs + " - " + files[i].getName());
						uploader.upload(dst, dirs, files[i].getName());
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		FileUploader fu = new FileUploader();
		fu.init();
		fu.run();
		fu.close();
	}
}
