package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadTechnicalSkill;
import com.example.mapper.HadTechnicalSkillMapper;

@Service
@Transactional
public class UpdateHadTechnicalSkillService {
	
	@Autowired
	private HadTechnicalSkillMapper hadTechnicalSkillMapper;
	
	/**
	 * 
	 * 保有テクニカルスキルの利用期間を複数件更新するメソッド.
	 * 
	 * @param hadTechnicalSkillList　保有テクニカルスキルリスト
	 * @param userId ユーザーid
	 * @return 更新件数
	 */
	public int updateUsingPeriodByHadTechnicalSkillList(
			List<HadTechnicalSkill> hadTechnicalSkillList,Integer userId) {
		return hadTechnicalSkillMapper.updateUsingPeriodByHadTechnicalSkillList(hadTechnicalSkillList,userId);
	}

}
