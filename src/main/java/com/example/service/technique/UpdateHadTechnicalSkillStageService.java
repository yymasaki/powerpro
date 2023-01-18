package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadTechnicalSkill;
import com.example.example.HadTechnicalSkillExample;
import com.example.mapper.HadTechnicalSkillMapper;

@Service
@Transactional
public class UpdateHadTechnicalSkillStageService {
	
	@Autowired
	private HadTechnicalSkillMapper mapper;
	
	/**
	 * 保有テクニカルスキルのステージを更新するメソッド.
	 * 
	 * @param hadTechnicalSkill 保有テクニカルスキル
	 * @param example 条件
	 * @return 更新件数
	 */
	public int updateStage(HadTechnicalSkill hadTechnicalSkill, HadTechnicalSkillExample example) {
		return mapper.updateStageByExample(hadTechnicalSkill, example);
	}
	
	/**
     * 
     * stageを複数件更新するメソッド.
     * 
     * @param hadTechnicalSkillList 保有テクニカルスキルリスト
     * @return　更新件数
     */
    public int updateStageByList(List<HadTechnicalSkill> hadTechnicalSkillList) {
    	if(hadTechnicalSkillList.size() == 0) {
    		return 0;
    	}
    	HadTechnicalSkill ht = hadTechnicalSkillList.get(0);
    	return mapper.updateStageByHadTechnicalSkillList(
    			hadTechnicalSkillList, ht.getStage(), ht.getUpdater(), ht.getUpdatedAt());
    }

}
