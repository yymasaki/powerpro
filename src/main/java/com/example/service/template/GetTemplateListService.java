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
public class GetTemplateListService {
	
	@Autowired
	private TemplateMapper templateMapper;
	
	/**
	 * テンプレート選択画面のセレクトボックス表示のためのテンプレートリストを取得する.
	 * 
	 * @param departmentId 所属id
	 * @return 管理者 → 全テンプレート
	 * 　　　　 エンジニア → 自身の所属に合致するテンプレート
	 */
	public List <Template> getTemplateList(Integer departmentId){
		TemplateExample example = new TemplateExample();
		if (departmentId == 5) {
			// 全件検索のため、何もしない
		} else if (departmentId == 1 || departmentId == 2 || departmentId == 3) {
			example.or().andDepartmentIdEqualTo(departmentId);
		}
		return templateMapper.selectByExample(example);
	}
}
