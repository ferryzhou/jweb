/*
 * Created on 2005-2-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.task;

import com.iil.web.rssgen.*;
import com.iil.util.web.*;
import java.util.*;
import java.io.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaskExecutor {

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
	
	public void run(String name, String lecfile, String url, String outfile){
		try {
			RSSGenerator rssg = new RSSGenerator(name, lecfile, url, outfile);
			rssg.run();
			uploader.upload("rss/", outfile);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(String tskfile) {
		try {
			//String file = "task_lib/" + tskfile;
			RSSGenConfig rgc = new RSSGenConfig();
			rgc.load(tskfile);
			RSSGenerator rssg = new RSSGenerator(rgc.getTitle(), rgc.getLecDir(), rgc.getURL(), "out/" + rgc.getOutFile());
			rssg.run();
			String outfile = rgc.getOutFile();
			uploader.upload("rss/", "out/", outfile);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test(String tskfile) {
		try {
			RSSGenConfig rgc = new RSSGenConfig();
			rgc.load(tskfile);
			RSSGenerator rssg = new RSSGenerator(rgc.getTitle(), rgc.getLecDir(), rgc.getURL(), "out/" + rgc.getOutFile());
			rssg.run();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runLib() {
		try {
			List fileList = loadAllTasks();
			Iterator iter = fileList.iterator();
			while (iter.hasNext()) {
				String filename = (String)iter.next();
				System.out.println("run " + filename + " ......");
				run(filename);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List loadAllTasks() throws IOException{
		List fileList = new ArrayList();
		List dirList = new ArrayList();
		File root = new File("task_lib");
		dirList.add(root);
		while (!dirList.isEmpty()) {
			File file = (File)dirList.remove(0);
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					dirList.add(files[i]);
				} else {
					fileList.add(files[i].getAbsolutePath());
				}
			}
		}
		return fileList;
	}
	
	public void run() throws Exception {
/*		run("9710bbs", "bbs.lec", "http://bbs.ustc.edu.cn/cgi/bbsdoc?board=AUTO", "9710bbs2.xml");

		run("Google", "sinasearch.lec", getSinaSearchURL("Google", true), "sina_google2.xml");
		run("IBM", "sinasearch.lec", getSinaSearchURL("IBM", true), "sina_ibm2.xml");
		run("SUN", "sinasearch.lec", getSinaSearchURL("SUN", true), "sina_sun2.xml");
		run("Microsoft", "sinasearch.lec", getSinaSearchURL("微软", true), "sina_microsoft2.xml");
		run("新闻周刊", "sinasearch.lec", getSinaSearchURL("新闻周刊", true), "sina_newsweek2.xml");
		run("商业周刊", "sinasearch.lec", getSinaSearchURL("商业周刊", true), "sina_bussinessweek2.xml");
		run("互联网周刊", "sinasearch.lec", getSinaSearchURL("互联网周刊", true), "sina_internetweek2.xml");
		run("惠普", "sinasearch.lec", getSinaSearchURL("惠普", true), "sina_hp2.xml");
*/		
/*		run("Google", "lec.txt", getIAskSearchURL("Google", true), "sina_google201.xml");
		run("IBM", "lec.txt", getIAskSearchURL("IBM", true), "iask_ibm2.xml");
		run("SUN", "lec.txt", getIAskSearchURL("SUN", true), "iask_sun2.xml");
		run("Microsoft", "lec.txt", getIAskSearchURL("微软", true), "iask_microsoft2.xml");
		run("新闻周刊", "lec.txt", getIAskSearchURL("新闻周刊", true), "iask_newsweek2.xml");
		run("商业周刊", "lec.txt", getIAskSearchURL("商业周刊", true), "iask_bussinessweek2.xml");
		run("互联网周刊", "lec.txt", getIAskSearchURL("互联网周刊", true), "iask_internetweek2.xml");
		run("21世纪经济报道", "lec.txt", getIAskSearchURL("21世纪经济报道", true), "iask_21centuryfinance2.xml");
		run("新京报", "lec.txt", getIAskSearchURL("新京报", true), "iask_xinjingbao2.xml");
*/
		//run("iask_title/Google.tsk");
		
		//run("task_lib/iask_title/Google.tsk");
		test("task_lib/sina/famous_media/ccw.tsk");
		//runLib();
		//test("task_lib/sina/famous_media/zbwz.tsk");
		//run("task_lib/sina/focus/focus.tsk");
	}
	
	public String getIAskSearchURL(String keyword, boolean title) {
		String t = title ? "title" : "";
		return "http://iask.com/n?k=" + keyword + "&t=" + t;
	}
	
	public String getSinaSearchURL(String keyword, boolean title) {
		String t = title ? "title" : "";
		return "http://chanews.sina.com.cn/s.cgi?k=" + keyword + "&t=" + t;
	}
	
	public static void main(String[] args) throws Exception{
		TaskExecutor te = new TaskExecutor();
		te.init();
		te.run();
		te.close();
	}
	
	
}
