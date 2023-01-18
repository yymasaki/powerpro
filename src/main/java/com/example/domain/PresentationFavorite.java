package com.example.domain;

import lombok.Data;

@Data
public class PresentationFavorite {
    
    /** 発表いいねID */
    public Integer presentationFavoriteId;
    /** ユーザーID */   
    public Integer userId;
    /** 発表ID */
    public Integer presentationId;
}
