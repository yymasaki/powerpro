package com.example.form;




import com.example.NotSpace;

import lombok.Data;

@Data
public class EditRequestTechnicalSkillForm {

	 /**テクニカルスキルID*/
    private Integer technicalSkillId;
    
    /**スキル名*/
    @NotSpace(message="スキル名を入力してください")
    private String name;
    
    /**カテゴリ*/
    private Integer category;
            
    /**バージョン番号*/
    private Integer version;

	public EditRequestTechnicalSkillForm(Integer technicalSkillId, String name, Integer category, Integer version) {
		super();
		this.technicalSkillId = technicalSkillId;
		this.name = name;
		this.category = category;
		this.version = version;
	}

	public EditRequestTechnicalSkillForm() {
		super();
		// TODO Auto-generated constructor stub
	}
    	
    
}
