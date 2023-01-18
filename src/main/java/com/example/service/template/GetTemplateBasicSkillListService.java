package com.example.service.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TemplateBasicSkill;
import com.example.example.TemplateBasicSkillExample;
import com.example.mapper.TemplateBasicSkillMapper;

@Service
@Transactional
public class GetTemplateBasicSkillListService {
	
	@Autowired
	private TemplateBasicSkillMapper mapper;
	
	
	/**
	 * テンプレートidでテンプレート用基本スキルを取得する.
	 * 
	 * @param templateId
	 * @return
	 */
	public List<TemplateBasicSkill> getTemplateBasicSkillList(Integer templateId) {
		TemplateBasicSkillExample example = new TemplateBasicSkillExample();
		example.or().andTemplateIdEqualTo(templateId);
		example.setOrderByClause("template_basic_skill_id");
		return mapper.selectByExample(example);
	}
}
