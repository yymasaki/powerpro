package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.UsedTechnicalSkill;
import com.example.mapper.UsedTechnicalSkillMapper;

@Service
@Transactional
public class AddUsedTechnicalSkillService {
	
	@Autowired
	private UsedTechnicalSkillMapper usedTechnicalSkillMapper;
	
	/**
	 * 利用テクニカルスキルリストを登録するメソッド.
	 * 
	 * @param usedTechnicalSkillList
	 */
	public void addUsedTechnicalSkillList(List<UsedTechnicalSkill> usedTechnicalSkillList) {
		usedTechnicalSkillMapper.insertList(usedTechnicalSkillList);
	}

}
