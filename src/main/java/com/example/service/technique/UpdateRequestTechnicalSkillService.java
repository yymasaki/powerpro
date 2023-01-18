package com.example.service.technique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class UpdateRequestTechnicalSkillService {
	
	@Autowired
	public TechnicalSkillMapper mapper;
	
	/**
	 * テクニカルスキル情報を更新する.
	 * @param technicalSkill テクニカルスキル情報
	 * @return 処理件数
	 */
	public int updateRequestTechnicalSkill(TechnicalSkill technicalSkill) {
		
		return mapper.updateByPrimaryKeySelective(technicalSkill);
	}

}
