package com.example.domain;

import lombok.Data;

@Data
public class HadPersonality {
    /** 所持性格スキルID */
    private Integer hadPersonalityId;
    /** ステータスID */
    private Integer statusId;
    /** 性格ID */
    private Integer personalityId;
    /** 性格 */
    private Personality personality;
    /** ステージ */
    private Integer stage;
}