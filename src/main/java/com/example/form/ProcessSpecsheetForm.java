package com.example.form;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSpecsheetForm {
	/**	スペックシートID */
	private Integer specsheetId;
	/**	ユーザーID */
	private Integer userId;
	/**	管理者コメント */
	@Size(max = 500, message = "500字以内で入力してください")
	private String adminComment;
	/**	ステージ */
	private Integer stage;
	/**	バージョン */
	private Integer version;
}
