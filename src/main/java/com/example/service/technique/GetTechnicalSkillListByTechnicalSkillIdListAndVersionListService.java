package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetTechnicalSkillListByTechnicalSkillIdListAndVersionListService {

	@Autowired
	public TechnicalSkillMapper mapper;
	
	/**
	 * テクニカルスキルIDリスト,バージョンリストからテクニカルスキルリストを取得する.
	 * @param technicalSkillIdList　テクニカルスキルIDリスト
	 * @return　テクニカルスキルリスト
	 */
	public List<TechnicalSkill> getTechnicalSkillListByTechnicalSkillIdListAndVersionList(List<Integer> technicalSkillIdList,List<Integer> versionList){
		List<TechnicalSkill> technicalSkillList=mapper.selectByTechinicalSkillIdListAndVersionList(technicalSkillIdList, versionList);
	 if(technicalSkillIdList.size()==0) {
		 return null;
	 }
	 return technicalSkillList;
	}
	
}
