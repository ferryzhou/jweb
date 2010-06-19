package com.iil.ie.freetext;

/**
 * masterEP, containEP
 * <p>find string which match masterEP and contain containEP</p>
 * 
 */
public class ContainEPattern extends EPattern{
	
	public ContainEPattern(EPattern masterEP, EPattern containEP) {
		this.masterEP = masterEP;
		this.containEP = containEP;
	}

	public void resetInput(CharSequence cs) {
		super.resetInput(cs);
		masterEP.resetInput(cs);
	}

	public boolean doFind(int start) {
		int s = start;
		while (masterEP.find(s)) {
			CharSequence cs = masterEP.group().toString();
			//System.out.println (cs);
			containEP.resetInput(cs);
			if (containEP.find()) {
				//System.out.println("..." + containEP.group());
				from = masterEP.start();
				to = masterEP.end();
				group = masterEP.group();
				return true;
			}
			s = masterEP.end();
		}
		return false;
	}
	
	private EPattern masterEP;
	private EPattern containEP;
	
}
