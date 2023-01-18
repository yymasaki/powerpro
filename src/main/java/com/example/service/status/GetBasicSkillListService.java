package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.BasicSkill;
import com.example.example.BasicSkillExample;
import com.example.mapper.BasicSkillMapper;

@Service
@Transactional
public class GetBasicSkillListService {
	
	@Autowired
	private BasicSkillMapper mapper;
	
	/**
	 * 所属部署の基本スキルを取得する.
	 * 
	 * @param departmentId 所属id
	 * @return 基本スキルリスト
	 */
	public List<BasicSkill> getBasicSkillList(Integer departmentId){
		BasicSkillExample example = new BasicSkillExample();
		example.createCriteria()
			.andDepartmentIdEqualTo(departmentId);
		example.setOrderByClause("basic_skill_id");
		return mapper.selectByExample(example);
	}
}
