package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Stage {

	/** アクティブ */
	ACTIVE(0, "active", "更新", "承認", "承認"),
	/** 一時保存 */
	TEMPORARY(1, "temporarily saved", "一時保存", "", "一時保存"),
	/** 申請中 */
	REQUESTING(2, "requesting", "申請", "再申請", "申請中"),
	/** 差戻し */
	SENT_BACK(3, "sent back", "", "差し戻し", "差し戻し"),
	/** 仮承認 */
	TMP_APPROVED(4, "tmp approved", "", "仮承認", "仮承認"),
	/** 否認 */
	REJECT(5, "reject", "", "否認", "否認"),
	/** 削除 */
	DELETED(9, "deleted", "削除", "取消", "取消");
	

	/** key値 */
	private final Integer key;
	/** ステージを表す言葉 */
	private final String content;
	/** 編集アラートメッセージ表示用文言 */
	private final String messageForEdit;
	/** 申請画面アラートメッセージ表示用文言 */
	private final String messageForRequest;
	/** 発表会用アラートメッセージ表示用文言 */
	private final String messageForPresentation;
	
	/**
	 * key値からEnumを取得するメソッド.
	 * 
	 * @param key key値
	 * @return 渡されたkeyを含むEnum
	 */
	public static Stage of(Integer key) {
		for(Stage stage: Stage.values()) {
			if(stage.key == key) {
				return stage;
			}
		}
		throw new IndexOutOfBoundsException("The value of enum doesn't exist.");
	}

}
