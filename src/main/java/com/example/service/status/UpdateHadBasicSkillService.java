package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadBasicSkill;
import com.example.mapper.HadBasicSkillMapper;

@Service
@Transactional
public class UpdateHadBasicSkillService {
	
	@Autowired
	private HadBasicSkillMapper hadBasicSkillMapper;
	
	/**
	 * 基本所有スキルを更新する.
	 * 
	 * @param hadBasicSkillScoreList 基本スキルのスコアリスト
	 * @param statusId ステータスID
	 */
	public void updateHadBasicSkill(List<HadBasicSkill> hadBasicSkillList, Integer statusId) {
		hadBasicSkillMapper.bulkUpdate(hadBasicSkillList, statusId);
	}
}
