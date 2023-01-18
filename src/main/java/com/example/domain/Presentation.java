package com.example.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.common.Stage;

import lombok.Data;

@Data
public class Presentation {

    /** 発表ID */
    private Integer presentationId;
    /** 発表タイトル */
    private String title;
    /** 発表概要 */
    private String content;
    /** 発表希望日 */
    private String preferredDate;
    /** 発表日 */
    private LocalDate presentationDate;
    /** ステージ */
    private Integer stage;
    /** 作成者のuserId */
    private Integer creatorUserId;
    /** 作成年月日時分 */
    private LocalDateTime createdAt;
    /** 更新者のuserId */
    private Integer updaterUserId;
    /** 更新年月日時分 */
    private LocalDateTime updatedAt;
    /** バージョン（更新ごとに１増える？） */
    private Integer version;
    /** 発表資料リスト */
    private List<PresentationDocument> presentationDocumentList;
    /** 発表コメントリスト */
    private List<PresentationComment> presentationCommentList;
    /** 修正依頼コメントリスト */
    private List<EditRequestComment> editRequestCommentList;

    /** 以下11/1上原追記 */
    /** 発表者リスト */
    private List<User> presenterList;
    /** 現在の状況 */
    private String currentStage;
    /** 発表チームの所属部署（エンジニアの系統） */
    private String presenterDepartment;

    /** 以下11/9小渕追記 */
    /** いいねリスト */
    private List<PresentationFavorite> presentationFavoriteList;
    /** 総いいね数 */
    private Integer favoriteCount;
 
    /** 以下11/11工藤追記 */
    private User user;
    
    public void putCurrentStage(){
        Stage currentStage  = Stage.of(this.stage);
		setCurrentStage(currentStage.getMessageForPresentation());
    }

}
