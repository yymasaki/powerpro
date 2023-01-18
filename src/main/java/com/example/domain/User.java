package com.example.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	/** ユーザーID */
	private Integer userId;
	/** 名前 */
	private String name;
	/** 入社年月 */
	private LocalDate hiredOn;
	/** 所属ID */
	private Integer departmentId;
	/** メールアドレス */
	private String email;
	/** パスワード */
	private String password;
	/** 選択したテンプレートのid */
	private Integer selectedTemplateId;
	/** 状態 */
	private Integer stage;
	/** パスワード最終更新日 */
	private LocalDate updatedPasswordAt;
	/** データ作成者 */
	private String creator;
	/** データ作成日 */
	private LocalDateTime createdAt;
	/** データ最終更新者 */
	private String updater;
	/** データ最終更新日 */
	private LocalDateTime updatedAt;
	/** 更新バージョン */
	private Integer version;
	/** 部署 */
	private Department department;

}