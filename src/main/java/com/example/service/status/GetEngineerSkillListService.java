package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.EngineerSkill;
import com.example.example.EngineerSkillExample;
import com.example.mapper.EngineerSkillMapper;

@Service
@Transactional
public class GetEngineerSkillListService {
	
	@Autowired
	private EngineerSkillMapper mapper;
	
	/**
	 * 所属部署のエンジニアスキルを取得する.
	 * 
	 * @param departmentId 所属id
	 * @return エンジニアスキルリスト
	 */
	public List<EngineerSkill> getEngineerSkillList(Integer departmentId){
		EngineerSkillExample example = new EngineerSkillExample();
		example.createCriteria()
			.andDepartmentIdEqualTo(departmentId);
		example.setOrderByClause("engineer_skill_id");
		return mapper.selectByExample(example);
	}
}
