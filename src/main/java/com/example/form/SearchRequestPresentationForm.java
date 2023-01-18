package com.example.form;

import java.util.Objects;

import lombok.Data;

@Data
public class SearchRequestPresentationForm {

	/** 申請状況 */
	private String stage;
	/** 所属id */
	private String departmentId;
	/** 名前の曖昧検索 */
	private String name;
	/** 発表日(確定) */
	private String presentationDate;

	/**
	 * @return 入力あり：true, 入力なし:false
	 */
	public boolean existValue() {
		if (Objects.isNull(this.stage) && Objects.isNull(this.departmentId) && Objects.isNull(this.name)
				&& Objects.isNull(this.presentationDate)) {
			return false;
		}
		if (this.stage.isEmpty() && this.departmentId.isEmpty() && this.name.isEmpty()
				&& this.presentationDate.isEmpty()) {
			return false;
		}
		if ("null".equals(this.stage) && "null".equals(this.departmentId) && "null".equals(this.name)
				&& "null".equals(this.presentationDate)) {
			return false;
		}
		return true;
	}

	/**
	 * エンジニア用
	 * 
	 * @return 入力あり：true, 入力なし:false
	 */
	public boolean existValueByEngineer() {
		if (Objects.isNull(this.stage)) {
			return false;
		}
		if (this.stage.isEmpty()) {
			return false;
		}
		if ("null".equals(this.stage)) {
			return false;
		}
		return true;
	}

}
