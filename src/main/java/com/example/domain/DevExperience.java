package com.example.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevExperience {
    /** 開発経験ID */
    private Integer devExperienceId;
    /** スペックシートID */
    private Integer specsheetId;
    /** プロジェクト名 */
    private String projectName;
    /** 開始日 */
    private LocalDate startedOn;
    /** 終了日 */
    private LocalDate finishedOn;
    /** チーム人数 */
    private Integer teamMembers;
    /** プロジェクト人数 */
    private Integer projectMembers;
    /** 役割 */
    private String role;
    /** プロジェクト詳細 */
    private String projectDetails;
    /** 担当業務 */
    private String tasks;
    /** 身に付いた事 */
    private String acquired;
    /** ステージ */
    private Integer stage;
    /** 利用テクニカルスキルリスト */
    private List<UsedTechnicalSkill> usedTechnicalSkillList;
    /** 利用テクニカルスキルのテキストリスト(タグ表示用) */
    private List<StringBuilder> usedTechnicalSkillSBList;
    
    /**
     * 開発期間を年月数で取得.
     * 
     * @return 年月数
     */
    public String getProjectPeriod() {
    	int projectPeriod = (int) ChronoUnit.MONTHS.between(startedOn, finishedOn) + 1;
    	AppSpecsheet spec = new AppSpecsheet();
    	return spec.getPeriod(Integer.valueOf(projectPeriod));
    }

}