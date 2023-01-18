package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadTechnicalSkill;
import com.example.mapper.HadTechnicalSkillMapper;

@Transactional
@Service
public class AddHadTechnicalSkillService {

	@Autowired
	private HadTechnicalSkillMapper hadTechnicalSkillMapper;

	/**
	 * 
	 * 所有テクニカルスキルを追加する(申請時)
	 * 
	 * @param hadTechnicalSkill
	 */
	public void insertList(List<HadTechnicalSkill> hadTechnicalSkill) {
			hadTechnicalSkillMapper.insertList(hadTechnicalSkill);
	}
	
}
