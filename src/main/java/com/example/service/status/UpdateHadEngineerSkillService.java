package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadEngineerSkill;
import com.example.mapper.HadEngineerSkillMapper;

@Service
@Transactional
public class UpdateHadEngineerSkillService {
	
	@Autowired
	private HadEngineerSkillMapper hadEngineerSkillMapper;
	
	/**
	 * 基本所有スキルを更新する.
	 * 
	 * @param hadBasicSkillScoreList 基本スキルのスコアリスト
	 * @param statusId ステータスID
	 */
	public void updateHadEngineerSkill(List<HadEngineerSkill> hadEngineerSkillList, Integer statusId) {
		hadEngineerSkillMapper.bulkUpdate(hadEngineerSkillList, statusId);
	}
}
