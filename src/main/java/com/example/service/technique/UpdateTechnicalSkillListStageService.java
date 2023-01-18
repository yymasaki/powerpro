package com.example.service.technique;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class UpdateTechnicalSkillListStageService {
	
	@Autowired
	public TechnicalSkillMapper mapper;

	/**
	 * ステージを複数件更新する.
	 * @param technicalSkillList テクニカルスキルリスト
	 * @param stage　ステージ
	 * @param updater　更新者
	 * @param updatedAt　更新日
	 */
	public int updateTechnicalSkillListStage(List<TechnicalSkill> technicalSkillList,String stage
			,String updater,LocalDateTime updatedAt) {
		return mapper.updateStageByPrimaryKeyAsList(technicalSkillList, stage, updater, updatedAt);
	}
}
