package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
	
	/** 動作環境、OS */
	OS(1, "OS","OS"),
	/** プログラミング言語 */
	LANGUAGE(2, "language","プログラミング言語"),
	/** フレームワーク */
	FRAMEWORK(3, "framework","フレームワーク"),
	/** ライブラリ */
	LIBRARY(4, "library","ライブラリ"),
	/** ミドルウェア */
	MIDDLEWARE(5, "middleware","ミドルウェア"),
	/** ツール、その他 */
	TOOL(6, "tool","ツール"),
	/** 担当開発工程 */
	PROCESS(7, "process","担当開発工程")
	;
	
	/** key値 */
	private final Integer key;
	/** カテゴリーを表す言葉 */
	private final String content;
	/**カテゴリ名*/
	private final String name;
	
	/**
	 * key値からEnumを取得するメソッド.
	 * 
	 * @param key key値
	 * @return 渡されたkeyを含むEnum
	 */
	public static Category of(Integer key) {
		for(Category category: Category.values()) {
			if(category.key == key) {
				return category;
			}
		}
		throw new IndexOutOfBoundsException("The value of enum doesn't exist.");
	}
	
}
