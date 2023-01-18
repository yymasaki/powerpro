package com.example.form;




import com.example.NotSpace;

import lombok.Data;

@Data
public class TechnicalSkillForm {

    /**スキル名*/
	@NotSpace(message="スキル名を入力してください")
    private String name;

    /**カテゴリ*/
    private Integer category;

	public TechnicalSkillForm(String name, Integer category) {
		super();
		this.name = name;
		this.category = category;
	}

	public TechnicalSkillForm() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
