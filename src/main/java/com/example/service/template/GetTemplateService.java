package com.example.service.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Template;
import com.example.mapper.TemplateMapper;

@Service
@Transactional
public class GetTemplateService {
	
	@Autowired
	private TemplateMapper templateMapper;
	
	/**
	 * テンプレートidからテンプレートを取得する.
	 * 
	 * @param templateId テンプレートid
	 * @return テンプレート
	 */
	public Template getTemplate(Integer templateId) {
		List<Template> templateList = templateMapper.selectByTemplateId(templateId);
		if (templateList.size() == 0) {
			return null;
		}
		return templateList.get(0);
	}
}
