package com.iil.word.impl_seg;


/**
 * WordDict
 * RuleSet
 */
class BlockRepository {
	
	//return matched end
	int scan (List<Block> blockString, int index) {
		Block b = blockString.get(index);
		List<List<Block>> matches = map.get(b);
		if (matches == null) return index;
		List<Block> target = new ArrayList<Block>();
		target.add(b);

		for (int i = 2; i < blockString.length() - index; i++) {
			target = blockString.subSequence(index, index + i);
			matches = searchMatches(target, matches);
			if (matches.length() == 0) {
				return target.length() + index - 1;
			}
		}
		return target.length() + index - 1;
	}
	
	List<List<Block>> searchMatches(List<Block> target, List<List<Block>> patterns) {
		List<List<Block>> matches = new ArrayList<ArrayList<Block>>();
		for (List<Block> pattern : patterns) {
			if (contains(target, pattern)) {
				matches.add(pattern);
			}
		}
		return matches;
	}
	
	//pattern contains target?
	boolean contains(List<Block> target, List<Block> pattern) {
		if (target.length() > pattern.length()) return false;
		for (int i = 0; i < target.length(); i++) {
			if (!target.get(i).equals(pattern.get(i))) return false;
		}
		return true;
	}
	
	Map<Block, List<List<Block>>> map = new HashMap<Block, List<List<Block>>>();
	
	public void readWordList(List<String> s) {
		
	}
	
	public void readPatternList() {
		
	}
}
