package com.example.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Personality {
    /** 性格ID */
    private Integer personalityId;
    /** 性格名 */
    private String name;
    /** 種類 */
    private String type;
    /** 性格に対する説明 */
    private String explanation;
    /** 状況 */
    private Integer stage;
    /** 作成者 */
    private String creator;
    /** 作成日 */
    private LocalDateTime createdAt;
    /** 更新者 */
    private String updater;
    /** 更新日 */
    private LocalDateTime updatedAt;
    /** 更新回数 */
    private Integer version;
}