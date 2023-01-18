package com.example.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Template {
    /** テンプレートid */
    private Integer templateId;
    /** 名前 */
    private String name;
    /** 所属id */
    private Integer departmentId;
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
    /** テンプレートエンジニアスキルリスト */
    private List<TemplateEngineerSkill> templateEngineerSkillList;
    /** テンプレート基本スキルリスト */
    private List<TemplateBasicSkill> templateBasicSkillList;
    /** 性格リスト */
    private List<Personality> personalityList;
    /** 所属 */
    private Department department;
}