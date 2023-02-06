package com.example.service.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.domain.HadBasicSkill;
import com.example.mapper.HadBasicSkillMapper;

@Service
@Transactional
public class GetHadBasicSkillListService {

	@Autowired
	private HadBasicSkillMapper mapper;
	
	/**
	 * 所有している基本スキル情報と基本スキル情報の検索を行う.
	 * 
	 * @param statusId ステータスID
	 * @return 所有基本スキルリスト
	 */
	public List<HadBasicSkill> getHadBasicSkillList(Integer statusId){
		List<HadBasicSkill> hadBasicSkillList = mapper.selectHadBasicSkillAndBasicSkillByStatusId(statusId);
		if (CollectionUtils.isEmpty(hadBasicSkillList)) {
			return new ArrayList<HadBasicSkill>();
		}
		return hadBasicSkillList;
	}
}
