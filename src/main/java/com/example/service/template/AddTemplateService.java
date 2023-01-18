package com.example.service.template;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Stage;
import com.example.domain.Template;
import com.example.domain.TemplateBasicSkill;
import com.example.domain.TemplateEngineerSkill;
import com.example.form.RegisterTemplateForm;
import com.example.mapper.TemplateBasicSkillMapper;
import com.example.mapper.TemplateEngineerSkillMapper;
import com.example.mapper.TemplateMapper;

@Service
@Transactional
public class AddTemplateService {
	
	@Autowired
	private TemplateMapper templateMapper;
	
	@Autowired
	private TemplateEngineerSkillMapper templateEngineerSkillMapper;
	
	@Autowired
	private TemplateBasicSkillMapper templateBasicSkillMapper;
	
	/**
	 * テンプレート登録処理を行う.
	 * 
	 * @param form テンプレート登録画面
	 */
	public Integer registerTemplate(RegisterTemplateForm form,String userName) {
		LocalDateTime today = LocalDateTime.now();
		
		// templatesテーブルへのインサート
		Template template = new Template();
		BeanUtils.copyProperties(form, template);
		template.setStage(Stage.ACTIVE.getKey());
		template.setCreator(userName);
		template.setCreatedAt(today);
		template.setUpdater(userName);
		template.setUpdatedAt(today);
		template.setVersion(1);
		templateMapper.insertReturnId(template);
		
		// template_engineer_skillsテーブルへのインサート
		List<Integer> engineerSkillIdList = form.getEngineerSkillIdList();
		List<Integer> templateEngineerSkillScoreList = form.getTemplateEngineerSkillScoreList();
		for (int i = 0; i < engineerSkillIdList.size(); i++) {
			TemplateEngineerSkill templateEngineerSkill = new TemplateEngineerSkill();
			templateEngineerSkill.setTemplateId(template.getTemplateId());
			templateEngineerSkill.setEngineerSkillId(engineerSkillIdList.get(i));
			templateEngineerSkill.setScore(templateEngineerSkillScoreList.get(i));
			templateEngineerSkillMapper.insert(templateEngineerSkill);
		}

		// template_basic_skillsテーブルへのインサート
		List<Integer> basicSkillIdList = form.getBasicSkillIdList();
		List<Integer> templateBasicSkillScoreList = form.getTemplateBasicSkillScoreList();
		for (int i = 0; i < basicSkillIdList.size(); i++) {
			TemplateBasicSkill templateBasicSkill = new TemplateBasicSkill();
			templateBasicSkill.setTemplateId(template.getTemplateId());
			templateBasicSkill.setBasicSkillId(basicSkillIdList.get(i));
			templateBasicSkill.setScore(templateBasicSkillScoreList.get(i));
			templateBasicSkillMapper.insert(templateBasicSkill);
		}
		
		return template.getTemplateId();
	}
}
