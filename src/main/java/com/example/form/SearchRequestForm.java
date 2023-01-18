package com.example.form;

import lombok.Data;

@Data
public class SearchRequestForm {
	/** 申請内容 */
	private Integer content;
	/** 申請状況 */
	private Integer stage;
	/** 申請者名 */
	private String applicant;
	/** 所属id */
	private Integer departmentId;
	/** ユーザーid（エンジニア用） */
	private Integer userId;
	/** ページ数 */
	private Integer pageNumber;
}
