package com.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Data;

@Data
public class Status {

    /** ステータスid */
    private Integer statusId;
    /** ユーザーid */
    private Integer userId;
    /** 使用pc */
    private String usingPc;
    /** 使用端末 */
    private String usingMobile;
    /** ユーザーコメント */
    private String userComment;
    /** 管理者コメント */
    private String adminComment;
    /** 申請段階 */
    private Integer stage;
    /** 作成者 */
    private String creator;
    /** 作成日時 */
    private LocalDateTime createdAt;
    /** 更新者 */
    private String updater;
    /** 更新日時 */
    private LocalDateTime updatedAt;
    /** バージョン */
    private Integer version;
    /** ユーザー */
    private User user;
    /** 所有エンジニアスキルリスト */
    private List<HadEngineerSkill> hadEngineerSkillList;
    /** 所有基本スキルリスト */
    private List<HadBasicSkill> hadBasicSkillList;
    /** 所有性格リスト */
    private List<HadPersonality> hadPersonalityList;
    
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