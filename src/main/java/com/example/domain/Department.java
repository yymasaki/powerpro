package com.example.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Department {

    /**部署ID*/
    private Integer departmentId;

    /**部署名*/
    private String name;

    /**スタッフID*/
    private String staffId;

    /**コンディション*/
    private Integer stage;

    /**作成者*/
    private String creator;

    /**作成日*/
    private LocalDateTime createdAt;

    /**更新者*/
    private String updater;

    /**更新日*/
    private LocalDateTime updatedAt;

    /**バージョン番号*/
    private Integer version;

}