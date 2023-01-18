package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedTechnicalSkill {
    /** 利用テクニカルスキルID */
    private Integer usedTechnicalSkillId;
    /** 開発経験ID */
    private Integer devExperienceId;
    /** テクニカルスキルID */
    private Integer technicalSkillId;
    /** ステージ */
    private Integer stage;
    /** テクニカルスキル */
    private TechnicalSkill technicalSkill;
}