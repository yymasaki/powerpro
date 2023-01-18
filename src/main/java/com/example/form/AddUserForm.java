package com.example.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.NotSpace;

import lombok.Data;

@Data
public class AddUserForm {

	/** 苗字 */
	@NotSpace(message = "苗字を入力してください")
	private String lastName;

	/** 名前 */
	@NotSpace(message = "名前を入力してください")
	private String firstName;

	/** 入社年月 */
	@NotSpace(message = "入社年月を入力してください")
	private String hiredOn;

	/** 所属ID */
	@Size(min=1, message = "所属を選択してください")
	private String departmentId;

	/** メールアドレス@以前 */
	@NotSpace(message = "メールアドレスを入力してください")
	@Pattern(regexp = "^[a-zA-Z.0-9]{1,30}$", message = "アルファベットと半角ドットのみ入力可能です")
	private String email;

	/** メールアドレス@以後 */
	@NotSpace(message = "ドメインを選択してください")
	private String domain;

	/** パスワード */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$", message = "パスワード形式が不正です")
	private String password;

	/** 確認用パスワード */
	private String checkPassword;

}
