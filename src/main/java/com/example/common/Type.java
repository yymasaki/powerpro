package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
	
	/** 長所 */
	STRONG('s', "strong point", "blue"),
	/** 短所 */
	WEEK('w', "week point", "red"),
	/** ニュートラル */
	NEUTRAL('n', "neutral", "green"),
	/** ミックス */
	MIX('m', "mix", "blue&red"),
	;
	
	/** key値 */
	private final Character key;
	/** タイプを表す言葉 */
	private final String content;
	/** 色 */
	private final String color;
	
	/**
	 * key値からEnumを取得するメソッド.
	 * 
	 * @param key key値
	 * @return 渡されたkeyを含むEnum
	 */
	public static Type of(Character key) {
		for(Type type: Type.values()) {
			if(type.key == key) {
				return type;
			}
		}
		throw new IndexOutOfBoundsException("The value of enum doesn't exist.");
	}

}
