package com.example.domain;

import lombok.Data;

/**
 * 所有基本スキル情報を格納するエンティティ.
 * 
 * @author nonaa
 *
 */
@Data
public class HadBasicSkill {
    /** 所持基本スキルID */
    private Integer hadBasicSkillId;
    /** ステータスID */
    private Integer statusId;
    /** 基本スキルID */
    private Integer basicSkillId;
    private String score;
    /** 基本スキル */
    private BasicSkill basicSkill;
}