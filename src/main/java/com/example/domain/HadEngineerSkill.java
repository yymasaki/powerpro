package com.example.domain;

import lombok.Data;

@Data
public class HadEngineerSkill {
    /** 所持エンジニアスキルID */
    private Integer hadEngineerSkillId;
    /** ステータスID */
    private Integer statusId;
    /** エンジニアスキルID */
    private Integer engineerSkillId;
    /** 点数 */
    private Integer score;
    /** エンジニアスキル */
    private EngineerSkill engineerSkill;
}