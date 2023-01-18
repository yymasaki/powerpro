package com.example.form;

import com.example.NotSpace;

import lombok.Data;

@Data
public class PresentationCommentForm {

    /** 発表コメント */
    @NotSpace(message = "コメントを入力してください")
    private String presentationComment;

    /** 発表ID */
    private Integer presentationCommentPresentationId;
    /** 画面上部に表示する戻るリンクのフラグ */
    private Integer returnFlg;

}
