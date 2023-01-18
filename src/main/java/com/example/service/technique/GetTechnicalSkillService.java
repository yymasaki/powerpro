package com.example.service.technique;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetTechnicalSkillService {

	@Autowired
	public TechnicalSkillMapper mapper;

	/**
	 * テクニカルスキルIDからテクニカルスキル情報を取得する.
	 * 
	 * @param technicalSkillId テクニカルスキルID
	 * @param version          バージョン番号
	 * @return テクニカルスキル情報
	 */
	public TechnicalSkill getTechnicalSkill(Integer technicalSkillId) {
		return mapper.selectByPrimaryKey(technicalSkillId);
	}

}
