package com.example.domain;

import lombok.Data;

@Data
public class TemplateEngineerSkill {
    /** テンプレートエンジニアスキルid */
    private Integer templateEngineerSkillId;
    /** テンプレートid */
    private Integer templateId;
    /** エンジニアスキルid */
    private Integer engineerSkillId;
    /** 点数（1〜100） */
    private Integer score;
    /** エンジニアスキル */
    private EngineerSkill engineerSkill;
}