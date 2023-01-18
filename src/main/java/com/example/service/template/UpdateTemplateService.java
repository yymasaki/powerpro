package com.example.service.template;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Template;
import com.example.domain.TemplateBasicSkill;
import com.example.domain.TemplateEngineerSkill;
import com.example.example.TemplateBasicSkillExample;
import com.example.example.TemplateEngineerSkillExample;
import com.example.example.TemplateExample;
import com.example.form.EditTemplateForm;
import com.example.mapper.TemplateBasicSkillMapper;
import com.example.mapper.TemplateEngineerSkillMapper;
import com.example.mapper.TemplateMapper;

@Service
@Transactional
public class UpdateTemplateService {
	
	@Autowired
	private TemplateMapper templateMapper;
	
	@Autowired
	private TemplateBasicSkillMapper templateBasicSkillMapper;
	
	@Autowired
	private TemplateEngineerSkillMapper templateEngineerSkillMapper;
	
	/**
	 * テンプレートを更新する.
	 * 
	 * @param form テンプレート編集フォーム
	 */
	public void editTemplate(EditTemplateForm form,String userName) {
		LocalDateTime now = LocalDateTime.now();
		Integer currentVersion = form.getVersion();
		
		// templatesテーブルのupdate
		TemplateExample templateExample = new TemplateExample();
		templateExample.or().andTemplateIdEqualTo(form.getTemplateId());
		Template template = templateMapper.selectByExample(templateExample).get(0);
		template.setName(form.getName());
		template.setUpdater(userName);
		template.setUpdatedAt(now);
		template.setVersion(currentVersion + 1);
		templateMapper.updateByExample(template, templateExample);
		
		// template_engineer_skillsテーブルのupdate
		List<Integer> engineerSkillIdList = form.getEngineerSkillIdList();
		List<Integer> templateEngineerSkillScoreList = form.getTemplateEngineerSkillScoreList();
		for (int i = 0, len = engineerSkillIdList.size(); i < len; i++) {
			TemplateEngineerSkillExample templateEngineerSkillExample = new TemplateEngineerSkillExample();
			templateEngineerSkillExample.or()
				.andTemplateEngineerSkillIdEqualTo(engineerSkillIdList.get(i));
			TemplateEngineerSkill templateEngineerSkill = 
					templateEngineerSkillMapper.selectByExample(templateEngineerSkillExample).get(0);
			templateEngineerSkill.setScore(templateEngineerSkillScoreList.get(i));
			templateEngineerSkillMapper.updateByExample(templateEngineerSkill, templateEngineerSkillExample);
		}
		

		// template_basic_skillsテーブルのupdate
		List<Integer> basicSkillIdList = form.getBasicSkillIdList();
		List<Integer> templateBasicSkillScoreList = form.getTemplateBasicSkillScoreList();
		for (int i = 0, len = basicSkillIdList.size(); i < len; i++) {
			TemplateBasicSkillExample templateBasicSkillExample = new TemplateBasicSkillExample();
			templateBasicSkillExample.or()
				.andTemplateBasicSkillIdEqualTo(basicSkillIdList.get(i));
			TemplateBasicSkill templateBasicSkill =
					templateBasicSkillMapper.selectByExample(templateBasicSkillExample).get(0);
			templateBasicSkill.setScore(templateBasicSkillScoreList.get(i));
			templateBasicSkillMapper.updateByExample(templateBasicSkill, templateBasicSkillExample);
		}
	}
}
