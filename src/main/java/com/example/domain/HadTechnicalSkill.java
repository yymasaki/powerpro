package com.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
@Data
public class HadTechnicalSkill {

	/**所有テクニカルスキルid*/
	private Integer hadTechnicalSkillId;
	/**ユーザーid*/
	private Integer userId;

	/**テクニカルスキルid*/
	private Integer technicalSkillId;

	/**使用期間*/
	private Integer usingPeriod;
	
	/**ステージ*/
	private Integer stage;

	/**作成者*/
	private String creator;

	/**作成日*/
	private LocalDateTime createdAt;

	/**アップデータ*/
	private String updater;

	/**アップデート日*/
	private LocalDateTime updatedAt;

	/**テクニカルスキル*/
	private TechnicalSkill technicalSkill;

	/**ユーザー*/
	private User user;

	/**ステータス*/
	private Status status;
	
	/**
	 * 文字列で申請状況を取得する（画面表示用）.
	 * @return 申請状況の文字列
	 */
	public String getStageString() {
		String stageString = "";
		if (stage == 0) {
			stageString = "承認済";
		} else if (stage == 2) {
			stageString = "未承認";
		} else if (stage == 3) {
			stageString = "否認";
		} else if (stage == 9){
			stageString = "削除済";
		}
		return stageString;
	}
	
    /**
     * フォーマットされた申請日を取得（画面表示用）.
     * ユーザーが申請を編集することを考慮し、元データはupdatedAtを採用
     * 
     * @return フォーマットされた申請日
     */
    public String getApplicationDateString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd");
    	return formatter.format(updatedAt);
    }
}