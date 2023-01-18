package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class UpdateTechnicalSkillService {

	@Autowired
	public TechnicalSkillMapper mapper;
	
	/**
	 * リクエストユーザーIDとステージを複数件更新するメソッド.
	 * 
	 * @param technicalSkillList
	 */
	public void updateRequestUserIdAndStageByPrimaryKeyAsList(List<TechnicalSkill> technicalSkillList) {
		mapper.updateRequestUserIdAndStageByPrimaryKeyAsList(
			technicalSkillList, technicalSkillList.get(0).getRequestUserId(), technicalSkillList.get(0).getStage().toString(), 
			technicalSkillList.get(0).getUpdater(), technicalSkillList.get(0).getUpdatedAt());
	}
	
}
