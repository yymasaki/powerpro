package com.example.service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.TemplateMapper;

@Service
@Transactional
public class DeleteTemplateService {
	
	@Autowired
	private TemplateMapper mapper;
	
	/**
	 * テンプレートを削除する.
	 * template_basic_skillsテーブルとtemplate_engineer_skillsテーブルの
	 * templateIdが紐づいているデータが自動的に削除されるようにテーブルを定義している.
	 * 
	 * @param templateId テンプレートid
	 * @return 削除件数
	 */
	public Integer deleteTemplate(Integer templateId) {
		return mapper.deleteByPrimaryKey(templateId);
	}
}
