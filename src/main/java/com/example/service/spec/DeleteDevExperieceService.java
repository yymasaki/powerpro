package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.DevExperience;
import com.example.mapper.DevExperienceMapper;

@Service
@Transactional
public class DeleteDevExperieceService {
	
	@Autowired
	private DevExperienceMapper devExperienceMapper;
	
	/**
	 * 複数の開発経験を物理削除するメソッド.
	 * 
	 * 依存関係にある使用テクニカルスキルも一緒に削除
	 * 
	 * @param devExperienceList 開発経験リスト
	 * @return 削除件数
	 */
	public int deleteListByPrimaryKey(List<DevExperience> devExperienceList) {
		return devExperienceMapper.deleteListByPrimaryKey(devExperienceList);
	}

}
