package com.example.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ResetPasswordForm {

	/** パスワード */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$", message = "パスワード形式が不正です")
	private String password;

	/** 確認用パスワード */
	private String checkPassword;

	/** ユーザーID */
	@Size(min = 1)
	private String userId;

	/** ユーザー名 */
	@Size(min = 1)
	private String name;

}
