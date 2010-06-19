package com.iil.word.impl_seg;

import com.iil.word.CharacterType;

class ArticleStringSegment extends StringSegment{

	public ArticleStringSegment(int pos, String s) {
		super(pos, s);
	}

	/**
     * => sentences, based on unknown characters
     */
	public void splitOnce() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (CharacterType.getType(c) == CharacterType.UNKNOWN_TYPE) {
				if (sb.length() != 0) {
					addSentence(i - sb.length(), sb.toString());
					sb = new StringBuffer();
				}
				addUnknownCharacterEntity(i, c);
			} else {
				sb.append(c);
			}
		}
		if (sb.length() != 0) {
			addSentence(str.length() - sb.length(), sb.toString());
		}

	}
	
	private void addSentence(int pos, String s) {
		subSegments.add(new SentenceStringSegment(pos, s));
	}
	
	private void addUnknownCharacterEntity(int pos, char c) {
		subSegments.add(EntityStringSegment.UnknownCharacterEntity(pos, c + ""));
	}
	
	public static void main(String[] args) throws java.io.IOException{
		String content = com.iil.util.IO.getContent("test.txt");
		System.out.println ("content: " + content);
    	ArticleStringSegment ass = new ArticleStringSegment(0, content);
    	ass.splitOnce();
    	for (StringSegment ss : ass.subSegments) {
    		System.out.println (ss);
    	}
    }
}
