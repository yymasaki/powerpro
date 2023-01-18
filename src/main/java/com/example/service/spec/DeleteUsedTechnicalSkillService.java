package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.UsedTechnicalSkill;
import com.example.mapper.UsedTechnicalSkillMapper;

@Service
@Transactional
public class DeleteUsedTechnicalSkillService {
	
	@Autowired
	private UsedTechnicalSkillMapper usedTechnicalSkillMapper;
	
	/**
	 * 複数の利用テクニカルスキルを物理削除するメソッド.
	 * 
	 * @param usedTechnicalSkillList 利用テクニカルスキルリスト
	 * @return 削除件数
	 */
	public int deleteUsedTechnicalSkill(List<UsedTechnicalSkill> usedTechnicalSkillList) {
		return usedTechnicalSkillMapper.deleteListByPrimaryKey(usedTechnicalSkillList);
	}

}
