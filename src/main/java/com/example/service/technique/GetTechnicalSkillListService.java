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
public class GetTechnicalSkillListService {
	
	@Autowired
	private TechnicalSkillMapper technicalSkillMapper;
	
	public List<TechnicalSkill> getTechnicalSkillList(TechnicalSkillExample example){
		
		return technicalSkillMapper.selectByExample(example);
	}

}
