package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadEngineerSkill;
import com.example.mapper.HadEngineerSkillMapper;

@Service
@Transactional
public class AddHadEngineerSkillService {
	
	@Autowired
	private HadEngineerSkillMapper hadEngineerSkillMapper;
	
	/**
	 * 所有エンジニアスキルを登録する.
	 * 
	 * @param hadEngineerSkillScoreList 所有エンジニアスキルの点数リスト
	 * @param statusId ステータスID
	 */
	public void addHadEngineerSkill(List<HadEngineerSkill> hadEngineerSkillList, Integer statusId) {
		hadEngineerSkillList.stream().forEach((hadEngineerSkill)->{
			HadEngineerSkill hes = hadEngineerSkill;
			hes.setStatusId(statusId);
			hes.setHadEngineerSkillId(null);
		});
		hadEngineerSkillMapper.insertSelectiveList(hadEngineerSkillList);
	}
}
