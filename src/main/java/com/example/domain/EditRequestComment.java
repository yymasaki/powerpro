package com.example.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EditRequestComment {
    
    /** 修正依頼コメントID */
    public Integer editRequestCommentId;
    /** 修正依頼コメント */
    public String editRequestComment;
    /** 修正依頼コメント作成者ID */
    public Integer editRequestCommentCreatorId;
    /** 修正依頼コメント作成者名 */
    public String editRequestCommentCreatorName;
    /** 修正依頼コメント作成年月日時分 */
    public LocalDateTime editRequestCommentCreatedAt;
    /** 発表ID */
    public Integer editRequestCommentPresentationId;

	public EditRequestComment(Integer editRequestCommentId, String editRequestComment,
            Integer editRequestCommentCreatorId, String editRequestCommentCreatorName,
            LocalDateTime editRequestCommentCreatedAt, Integer presentationId) {
        this.editRequestCommentId = editRequestCommentId;
        this.editRequestComment = editRequestComment;
        this.editRequestCommentCreatorId = editRequestCommentCreatorId;
        this.editRequestCommentCreatorName = editRequestCommentCreatorName;
        this.editRequestCommentCreatedAt = editRequestCommentCreatedAt;
        this.editRequestCommentPresentationId = presentationId;
    }

    public EditRequestComment(String comment, Integer creatorUserId, LocalDateTime createdAt, Integer presentationId) {
		this.editRequestComment = comment;
		this.editRequestCommentCreatorId = creatorUserId;
		this.editRequestCommentCreatedAt = createdAt;
        this.editRequestCommentPresentationId = presentationId;
	}

    public EditRequestComment(){}
}
