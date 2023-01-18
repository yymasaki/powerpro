package com.example.form;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadSpecsheetForm {
	/** スペックシートID */
	private Integer specsheetId;
	/** ユーザーID */
	private Integer userId;
	/** 開発経験リスト */
	private List<Integer> devExperienceIdList;
	/** バージョン */
	private Integer version;
}
