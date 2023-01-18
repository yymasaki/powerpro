package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.EditRequestComment;

@Mapper
public interface EditRequestCommentMapper {

	/** 申請中の発表会に対して管理者からの修正コメントを挿入 */
	void insertAdminComment(EditRequestComment editRequestComment);
}
