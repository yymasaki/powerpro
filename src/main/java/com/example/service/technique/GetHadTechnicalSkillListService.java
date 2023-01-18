package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadTechnicalSkill;
import com.example.mapper.HadTechnicalSkillMapper;

@Transactional
@Service
public class GetHadTechnicalSkillListService {
	
	@Autowired
	private HadTechnicalSkillMapper hadTechnicalSkillMapper;
	
	/**
	 * 
	 * 最新のstage=0の所有テクニカルスキルリストを取得する
	 * 
	 * @param userId ユーザーid
	 * @return 所有テクニカルスキルリスト
	 */
	public List<HadTechnicalSkill> getHadTechnicalSkillListOfStage0or1or2(Integer userId){
		return hadTechnicalSkillMapper.selectByUserIdAndStage0or1or2(userId);
	}
	
	/**
     * 条件から取得するメソッド.
     * 
     * @param example 条件
     * @return 保有テクニカルスキルリスト
     */
	public List<HadTechnicalSkill> getByUserId(Integer userId){
		return hadTechnicalSkillMapper.selectByUserId(userId);
	}

}
