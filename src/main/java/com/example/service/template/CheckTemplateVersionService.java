package com.example.service.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Template;
import com.example.example.TemplateExample;
import com.example.mapper.TemplateMapper;

@Service
@Transactional
public class CheckTemplateVersionService {
	
	@Autowired
	private TemplateMapper templateMapper;
	
	/**
	 * テンプレートのバージョンチェックを行う.
	 * 
	 * @param templateId テンプレートid
	 * @param version　バージョン
	 * @return　テンプレート
	 */
	public List<Template> checkTemplateVersion(Integer templateId,Integer version) {
		TemplateExample example = new TemplateExample();
		example.or()
			.andTemplateIdEqualTo(templateId)
			.andVersionEqualTo(version);
		example.setOrderByClause("template_id");
		List<Template> templateList = templateMapper.selectByExample(example);
		if (templateList.size() == 0) {
			return null;
		}
		return templateList;
	}
}
