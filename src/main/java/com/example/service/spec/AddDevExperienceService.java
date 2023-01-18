package com.example.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.DevExperience;
import com.example.mapper.DevExperienceMapper;

@Service
@Transactional
public class AddDevExperienceService {
	
	@Autowired
	private DevExperienceMapper devExperienceMapper;
	
	/**
	 * 開発経験リストを登録するメソッド.
	 * 
	 * @param devExperienceList
	 * @return 挿入したIDが格納されたdevExperienceList
	 */
	public List<DevExperience> addDevExperienceList(List<DevExperience> devExperienceList) {
		if(devExperienceList.size() != 0) {
			devExperienceMapper.insertList(devExperienceList);
			return devExperienceList;
		}
		return new ArrayList<>();
	}

}
