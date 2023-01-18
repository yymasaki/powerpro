package com.example.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BasicSkill {
    /** 基本スキルID */
    private Integer basicSkillId;
    /** 基本スキル名 */
    private String name;
    /** 部署ID */
    private Integer departmentId;
    /** 状況 */
    private Integer stage;
    /** 作成者 */
    private String creator;
    /** 作成時間 */
    private LocalDateTime createdAt;
    /** 更新者 */
    private String updater;
    /** 更新時間 */
    private LocalDateTime updatedAt;
    /** 更新回数 */
    private Integer version;
    
}