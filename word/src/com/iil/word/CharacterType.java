package com.iil.word;

/**
 * [Letters] a-z and A-Z
 * [Digits] 0-9
 * [Chinese Letters] a-z: 0xFF41-0xFF5A, A-Z: 0xFF21-0xFF3A
 * [Chinese Digits] 0-9: 0xFF10-0xFF19 
 * [Chinese Characters] 0x4E00-0x9FA5
 * [Chinese Number ] ��һ�����������߰˾�ʮ��ǧ����
 * [Calender] ���������ʱ
 */
 
public enum CharacterType {
	
	ENGLISH_LETTER {
		public boolean eval (char c) {
			return (c >= 'a' && c <='z') || (c >='A' && c <= 'Z');
		}
	},
	
	ENGLISH_DIGIT {
		public boolean eval (char c) {
			return (c >= '0' && c <='9');
		}
	},
	
	CHINESE_LETTER {
		public boolean eval (char c) {
			return (c >= 0xFF41 && c <=0xFF5A) || (c >= 0xFF21 && c <= 0xFF3A);
		}
	},

	CHINESE_DIGIT {
		public boolean eval (char c) {
			return (c >= 0xFF10 && c <= 0xFF19);
		}
	},
	
	CHINESE_NUMBER_METRIC {
		public boolean eval (char c) {
			return "��һ�����������߰˾�ʮ��ǧ����".indexOf(c) != -1;
		}
	},

	CHINESE_CALENDER {
		public boolean eval (char c) {
			return "������ʱ����".indexOf(c) != -1;
		}
	},
	
	//Order is critical
	CHINESE_CHARACTER {
		public boolean eval (char c) {
			return (c >= 0x4E00 && c <= 0x9FA5);
		}
	},
	
	CONCATATION_CARACTER  {
		public boolean eval (char c) {
			return ".-".indexOf(c) != -1;
		}
	},
	
	UNKNOWN_TYPE  {
		public boolean eval (char c) {
			return true;
		}
	};

	public abstract boolean eval(char c);

	public static CharacterType getType(char c) {
		for (CharacterType ct : CharacterType.values()) {
			if (ct.eval(c)) return ct;
		}
		return UNKNOWN_TYPE;
	}
	
	public static void main(String[] args) {
    	CharacterType ct = CharacterType.getType('ʮ');
    	System.out.println (ct);
    }
}
