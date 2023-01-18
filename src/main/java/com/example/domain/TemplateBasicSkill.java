package com.example.domain;

import lombok.Data;

@Data
public class TemplateBasicSkill {
    /** テンプレートスキルid */
    private Integer templateBasicSkillId;
    /** テンプレートid */
    private Integer templateId;
    /** 基本スキルid */
    private Integer basicSkillId;
    /** 点数（1〜5） */
    private Integer score;
    /** 基本スキル */
    private BasicSkill basicSkill;
}