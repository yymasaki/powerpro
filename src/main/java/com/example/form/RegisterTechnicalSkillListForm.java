package com.example.form;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class RegisterTechnicalSkillListForm {
	
	/**テクニカルスキル情報のリスト*/
	@Valid
	private List<TechnicalSkillForm> technicalSkillList;
}
