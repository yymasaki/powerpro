package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.DevExperience;
import com.example.mapper.DevExperienceMapper;

@Service
@Transactional
public class UpdateDevExperienceService {
	
	@Autowired
	private DevExperienceMapper devExperienceMapper;
	
	/**
	 * 複数件の開発経験を更新するメソッド.
	 * 
	 * @param devExperienceList 開発経験リスト
	 * @return 更新件数
	 */
	public int updateListByPrimaryKey(List<DevExperience> devExperienceList) {
		return devExperienceMapper.updateListByPrimaryKey(devExperienceList);
	}

}
