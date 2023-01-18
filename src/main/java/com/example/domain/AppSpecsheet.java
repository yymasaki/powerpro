package com.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.common.Stage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppSpecsheet {
    /** アプリ用スペックシートID */
    private Integer specsheetId;
    /** ユーザーID */
    private Integer userId;
    /** 社員番号 */
    private Integer employeeId;
    /** 年代 */
    private String generation;
    /** 性別 */
    private Character gender;
    /** 最寄駅 */
    private String nearestStation;
    /** 稼働開始日 */
    private String startBusinessDate;
    /** エンジニア歴 */
    private Integer engineerPeriod;
    /** SE歴 */
    private Integer sePeriod;
    /** PG歴 */
    private Integer pgPeriod;
    /** IT歴 */
    private Integer itPeriod;
    /** アピール */
    private String appeal;
    /** 業務以外に取り組んだこと */
    private String effort;
    /** 資格 */
    private String certification;
    /** 前職 */
    private String preJob;
    /** 管理者コメント */
    private String adminComment;
    /** ステージ */
    private Integer stage;
    /** データ作成者 */
    private String creator;
    /** データ作成日時 */
    private LocalDateTime createdAt;
    /** データ更新者 */
    private String updater;
    /** データ更新日時 */
    private LocalDateTime updatedAt;
    /** バージョン */
    private Integer version;
    /** ユーザー */
    private User user;
    /** ステータス */
    private Status status;
    /** 保有テクニカルスキルリスト */
    private List<HadTechnicalSkill> hadTechnicalSkillList;
    /** 開発経験リスト */
    private List<DevExperience> devExperienceList;
    
	/**
	 * 文字列で申請状況を取得する（画面表示用）.
	 * @return 申請状況の文字列
	 */
    public String getStageString() {
		String stageString = "";
		if (stage.equals(Stage.ACTIVE.getKey())) {
			stageString = "承認済";
		} else if (stage.equals(Stage.REQUESTING.getKey())) {
			stageString = "未承認";
		} else if (stage.equals(Stage.SENT_BACK.getKey())) {
			stageString = "否認";
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
    
    /**
     * スペックシート入力用スタッフIDを取得.
     * 
     * @return スペックシート入力用スタッフID
     */
    public String getStaffIdForSpec() {
    	return user.getDepartment().getStaffId() + "-" + employeeId;
    }
    
    /**
     * 性別名を取得.
     * 
     * @return 性別名
     */
    public String getGenderName() {
    	switch (gender) {
		case 'M':
			return "男性";
		case 'F':
			return "女性";
		case 'O':
			return "その他";
		default:
			return null;
		}
    }
    
    /**
     * 月数から年月数を取得.
     * 
     * @param period 月数
     * @return 年月数
     */
    public String getPeriod(Integer period) {
    	if(period < 12) {
    		return period + "ヶ月";
    	}
    	Integer years = 0;
    	Integer months = 0;
    	if(12 <= period) {
    		years = period / 12;
    		months = period % 12;
    		if(months == 0) {
    			return years + "年";
    		}
    	}
    	return years + "年" + months + "ヶ月";
    }
    
}