package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetTechnicalSkillByNameAndCategoryService {

	@Autowired
	TechnicalSkillMapper mapper;

	/**
	 * 名前とカテゴリ名からテクニカルスキルを1件取得する.
	 * 
	 * @param name     スキル名
	 * @param category カテゴリ
	 * @return テクニカルスキル
	 */
	public TechnicalSkill getTechinicalSkillByNameAndCategory(String name, Integer category) {
		TechnicalSkillExample example = new TechnicalSkillExample();
		example.createCriteria().andNameEqualTo(name).andCategoryEqualTo(category);
		List<TechnicalSkill> technicalSkillList = mapper.selectByExample(example);
		if (technicalSkillList.size() == 0) {
			return null;
		}
		return technicalSkillList.get(0);
	}

}
