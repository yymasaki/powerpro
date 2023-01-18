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
public class GetTemplateForRegisterService {
	
	@Autowired
	private TemplateMapper templateMapper;
	
	/**
	 * 同じ所属区分の中で同じ名前のテンプレートが存在するかどうかを確認する.
	 * 
	 * @param departmentId 所属id
	 * @param name テンプレート名
	 * @return　テンプレートリスト
	 */
	public List<Template> getTemplateForRegister(Integer departmentId, String name){
		TemplateExample example = new TemplateExample();
		example.or().andDepartmentIdEqualTo(departmentId)
					.andNameEqualTo(name);
		example.setOrderByClause("template_id");
		List<Template> templateList = templateMapper.selectByExample(example);
		return templateList;
	}
}
