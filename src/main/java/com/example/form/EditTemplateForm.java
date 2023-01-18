package com.example.form;

import java.util.List;

import javax.validation.constraints.Size;

import com.example.NotSpace;

import lombok.Data;

@Data
public class EditTemplateForm {
	/** テンプレートid */
	private Integer templateId;
	/** 所属id */
	private Integer departmentId;
	/** テンプレート名 */
	@NotSpace(message = "テンプレート名を入力してください")
	@Size(max = 30, message = "30文字以内で入力してください")
	private String name;
	/** エンジニアスキルidリスト */
	private List<Integer> engineerSkillIdList;
	/** テンプレートエンジニアスキルスコアリスト */
	private List<Integer> templateEngineerSkillScoreList;
	/** 基本スキルidリスト */
	private List<Integer> basicSkillIdList;
	/** テンプレート基本スキルスコアリスト */
	private List<Integer> templateBasicSkillScoreList;	
	/** バージョン */
	private Integer version;
}
