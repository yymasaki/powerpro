package com.example.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PresentationComment {
    
    /** 発表コメントID */
    private Integer presentationCommentId;
    /** 発表コメント */
    private String presentationComment;
    /** コメント投稿者ID */
    private Integer presentationCommentCreatorId;
    /** コメント投稿者名*/
    private String presentationCommentCreatorName;
    /** コメント作成年月日時分 */
    private LocalDateTime presentationCommentCreatedAt;
    /** コメント削除年月日時分 */
    private LocalDateTime deleteAt;
    /** ステージ（削除フラグ） */
    private Integer presentationCommentStage; 
    /** 発表ID */
    private Integer presentationCommentPresentationId;

}
