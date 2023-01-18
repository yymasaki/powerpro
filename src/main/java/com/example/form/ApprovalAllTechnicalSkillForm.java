package com.example.form;

import java.util.List;

import lombok.Data;

@Data
public class ApprovalAllTechnicalSkillForm {
	
	
	/**テクニカルスキルIDのリスト*/
	private List <Integer> technicalSkillIdList;
	
	/**versionのリスト*/
	private List <Integer> versionList;

}
