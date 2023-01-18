package com.example.form;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditSpecsheetForm {
	/** アプリ用スペックシートID */
    private Integer specsheetId;
    /** ユーザーID */
    @NotNull
    private Integer userId;
    /** 社員番号 */
    @Max(99999999)
    private Integer employeeId;
    /** 年代 */
    @Size(max = 10, message = "10字以内で入力してください")
    private String generation;
    /** 性別 */
    @Size(max = 1)
    private String gender;
    /** 最寄駅 */
    @Size(max = 200, message = "200字以内で入力してください")
    private String nearestStation;
    /** 稼働開始日 */
    @Size(max = 200, message = "200字以内で入力してください")
    private String startBusinessDate;
    /** エンジニア歴 */
    @Max(99999999)
    @Min(3)
    private Integer engineerPeriod;
    /** SE歴 */
    @Max(99999999)
    private Integer sePeriod;
    /** PG歴 */
    @Max(99999999)
    private Integer pgPeriod;
    /** IT歴 */
    @Max(99999999)
    @Min(3)
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
    /** バージョン */
    private Integer version;
    /** 元データのステージ */
    private Integer rawStage;
    /** 保有テクニカルスキルリスト */
    private String[] hadTechnicalSkillList;
    /** 開発経験リスト */
    private List<DevExperienceForm> devExperienceList;
    /** 利用テクニカルスキルリスト */
    private List<List<String>> usedTechnicalSkillList;
}
