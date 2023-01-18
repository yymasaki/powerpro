package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageService {
	
	@Autowired
	private TechnicalSkillMapper mapper;
	
	/**
	 * スペックシートIDとユーザーIDとステージからテクニカルスキルを取得するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param stage ステージ
	 * @return テクニカルスキルリスト
	 */
	public List<TechnicalSkill> getBySpecsheetIdAndUserIdAndStage(
			Integer specsheetId, Integer userId, List<Integer> specStageList) {
		return mapper.selectBySpecsheetIdAndUserIdAndStage(specsheetId, userId, specStageList);
	}

}
