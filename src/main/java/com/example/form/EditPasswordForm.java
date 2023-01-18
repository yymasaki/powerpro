package com.example.form;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class EditPasswordForm {

	/** 現パスワード */
	private String currentPassword;

	/** 新パスワード */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$", message = "パスワード形式が不正です")
	private String newPassword;

	/** 確認用パスワード */
	private String checkPassword;

}
