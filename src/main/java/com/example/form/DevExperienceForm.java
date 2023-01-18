package com.example.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevExperienceForm {
	/** 開発経験ID */
    private Integer devExperienceId;
    /** プロジェクト名 */
    @Size(max = 200, message = "200字以内で入力してください")
    private String projectName;
    /** 開始日 */
    private String startedOn;
    /** 終了日 */
    private String finishedOn;
    /** チーム人数 */
    @Max(99999999)
    private Integer teamMembers;
    /** プロジェクト人数 */
    @Max(99999999)
    private Integer projectMembers;
    /** 役割 */
    @Size(max = 200, message = "200字以内で入力してください")
    private String role;
    /** プロジェクト詳細 */
    private String projectDetails;
    /** 担当業務 */
    private String tasks;
    /** 身に付いた事 */
    private String acquired;
    /** ページ内表示位置 */
    private Integer devCount;
}