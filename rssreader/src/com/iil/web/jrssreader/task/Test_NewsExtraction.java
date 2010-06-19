/*
 * Created on 2006-5-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import com.iil.web.rssgen.RSSGenConfig;
import com.iil.web.rssgen.RSSGenerator;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Test_NewsExtraction {

	public static void main(String[] args) throws Exception{
		String task = "task_lib/iask_title/Ebay.tsk";
		RSSGenConfig rgc = new RSSGenConfig();
		rgc.load(task);
		RSSGenerator rssg = new RSSGenerator(rgc.getTitle(), rgc.getLecDir(), rgc.getURL(), "test_out/" + rgc.getOutFile(), rgc.getFilter());
		rssg.run();		
	}
}
