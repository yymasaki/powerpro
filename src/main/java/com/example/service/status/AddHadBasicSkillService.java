package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadBasicSkill;
import com.example.mapper.HadBasicSkillMapper;

@Service
@Transactional
public class AddHadBasicSkillService {
	
	@Autowired
	private HadBasicSkillMapper hadBasicSkillMapper;
	
	/**
	 * 基本所有スキルを登録する.
	 * 
	 * @param hadBasicSkillScoreList 基本スキルのスコアリスト
	 * @param statusId ステータスID
	 */
	public void addHadBasicSkill(List<HadBasicSkill> hadBasicSkillList, Integer statusId) {	
		hadBasicSkillList.stream().forEach((hadBasicSkill)->{
			HadBasicSkill hbs = hadBasicSkill;
			hbs.setStatusId(statusId);
			hbs.setHadBasicSkillId(null);
		});
		hadBasicSkillMapper.insertSelectiveList(hadBasicSkillList);
	}
}
