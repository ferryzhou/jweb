package com.iil.word.impl_seg;

import java.util.*;

class Block {
	
	Block(EntityStringSegment entity) {
		this.entity = entity;
	}
	
	private List<Block> subBlocks = new ArrayList<Block>(); //pattern
	private EntityStringSegment entity = null;
}

class EntityBlock {
	
	EntityBlock(char c) {
		//this.entity = new 
	}
	
	EntityBlock(EntityStringSegment entity) {
		this.entity = entity;
	}
	
	private EntityStringSegment entity = null;
}