package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.DevExperience;
import com.example.mapper.DevExperienceMapper;

@Service
@Transactional
public class GetDevExperienceService {
	
	@Autowired
	private DevExperienceMapper devExperienceMapper;
	
	/**
	 * スペックシートIDから開発経験を取得するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @return 開発経験リスト
	 */
	public List<DevExperience> getDevExperienceBySpecsheetId(Integer specsheetId) {
		return devExperienceMapper.selectBySpecsheetId(specsheetId);
	}

}
