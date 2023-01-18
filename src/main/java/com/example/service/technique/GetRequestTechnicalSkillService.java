package com.example.service.technique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetRequestTechnicalSkillService {

	@Autowired
	public TechnicalSkillMapper mapper;

	/**
	 * テクニカルスキルIDからテクニカルスキル情報,ユーザ情報、部署情報を取得する.
	 * 
	 * @param technicalSkillId テクニカルスキルID
	 * @return テクニカルスキル情報
	 */
	public TechnicalSkill getRequestTechnicalSkill(Integer technicalSkillId) {
		TechnicalSkill item = mapper.selectByTechnicalSkillId(technicalSkillId);
		return item;
	}
}
