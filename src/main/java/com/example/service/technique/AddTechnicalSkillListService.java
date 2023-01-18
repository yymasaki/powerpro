package com.example.service.technique;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class AddTechnicalSkillListService {
	
	@Autowired
	public TechnicalSkillMapper mapper;
	
	/**
	 * 複数件のテクニカルスキル情報を一括登録する.
	 * @param itemList テクニカルスキルリスト
	 * @return IDが格納されたテクニカルスキルリスト
	 */
	public List<TechnicalSkill> addTechnicalSkillList(List<TechnicalSkill> technicalSkillList) {
		if(technicalSkillList.size() != 0) {
			mapper.insertTechnicalSkillList(technicalSkillList);
			return technicalSkillList;
		}
		return new ArrayList<>();
	}
		
}
