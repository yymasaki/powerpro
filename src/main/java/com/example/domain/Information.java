package com.example.domain;


import java.sql.Date;

import lombok.Data;

@Data
public class Information {
    
	/**お知らせID*/
    private Integer informationId;

    /**作成者のID*/
    private Integer createUserId;

    /**タイトル*/
    private String title;

    /**掲載日*/
    private Date startPostedOn;

    /**掲載終了日*/
    private Date endPostedOn;

    /**ステージ*/
    private Integer stage;

    /**コンテンツ*/
    private String content;

}