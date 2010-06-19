package com.iil.word.impl_seg;


class EntityStringSegment extends StringSegment{

	public static enum EntityType {
		UNKNOWN_CHARACTER,
		ENGLISH_WORD,
		CALENDER,
		NUMBER,
		CHINESE_WORD,
		CHINESE_NON_WORD_STRING
	}
	
	public EntityStringSegment(int pos, String s, EntityType entityType) {
		super(pos, s);
		this.entityType = entityType;
	}

	public static EntityStringSegment UnknownCharacterEntity(int pos, String s) {
		return new EntityStringSegment(pos, s, EntityType.UNKNOWN_CHARACTER);
	}
	
	public static EntityStringSegment EnglishWordEntity(int pos, String s) {
		return new EntityStringSegment(pos, s, EntityType.ENGLISH_WORD);
	}
	
	public static EntityStringSegment NumberEntity(int pos, String s) {
		return new EntityStringSegment(pos, s, EntityType.NUMBER);
	}
	
	public static EntityStringSegment ChineseWordEntity(int pos, String s) {
		return new EntityStringSegment(pos, s, EntityType.CHINESE_WORD);
	}
	
	public static EntityStringSegment ChineseNonWordStringEntity(int pos, String s) {
		return new EntityStringSegment(pos, s, EntityType.CHINESE_NON_WORD_STRING);
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	/**
     * => do nothing
     */
	public void splitOnce() {
	}
	
	public String toString() {
		return "[ENTITY]" + entityType + " " + position + " : " + str;
	}
	private EntityType entityType;
}