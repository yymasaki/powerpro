package com.example.form;

import javax.validation.constraints.Size;

import com.example.NotSpace;

import lombok.Data;

@Data
public class EditUserForm {

	/** ユーザーID */
	private Integer userId;

	/** 苗字 */
	@NotSpace( message = "苗字を入力してください")
	private String lastName;

	/** 名前 */
	@NotSpace( message = "名前を入力してください")
	private String firstName;

	/** 入社年月 */
	@Size(min=1, message = "入社年月を入力してください")
	private String hiredOn;

	/** 所属ID */
	@NotSpace(message = "所属を選択してください")
	private String departmentId;

	/** メールアドレス */
	@NotSpace(message = "メールアドレスを入力してください")
	private String email;
	
	/** メールアドレスドメイン名 */
	private String domain;

	/** 更新前バージョン */
	private String version;

}
