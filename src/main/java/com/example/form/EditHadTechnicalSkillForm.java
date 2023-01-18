package com.example.form;

import java.util.List;

import com.example.domain.HadTechnicalSkill;
import lombok.Data;

@Data
public class EditHadTechnicalSkillForm {

	/**所有テクニカルスキルリスト*/
	private List<HadTechnicalSkill> hadTechnicalSkillList;

}
