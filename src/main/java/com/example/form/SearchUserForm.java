package com.example.form;

import java.util.Objects;

import lombok.Data;

@Data
public class SearchUserForm {

	/** 所属ID */
	private String departmentId;

	/** 名前 */
	private String name;

	/** 入社年月 */
	private String hiredOn;

	/** 保有スキル */
	private String[] skills;

	/**
	 * 検索値の入力有無を確認する.
	 * 
	 * @return 入力あり：true, 入力なし:false
	 */
	public boolean existValues() {
		if (Objects.isNull(this.name) && Objects.isNull(this.departmentId) && Objects.isNull(this.hiredOn)
				&& Objects.isNull(this.skills)) {
			return false;
		}
		if (this.name.isEmpty() && this.departmentId.isEmpty() && this.hiredOn.isEmpty()
				&& Objects.isNull(this.skills)) {
			return false;
		}
		if ("null".equals(this.name) && "null".equals(this.departmentId) && "null".equals(this.hiredOn)
				&& Objects.isNull(this.skills)) {
			return false;
		}
		return true;
	}

}
