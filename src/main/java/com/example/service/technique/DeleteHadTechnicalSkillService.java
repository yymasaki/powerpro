package com.example.service.technique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.HadTechnicalSkill;
import com.example.example.HadTechnicalSkillExample;
import com.example.mapper.HadTechnicalSkillMapper;

@Service
@Transactional
public class DeleteHadTechnicalSkillService {
	
	@Autowired
	private  HadTechnicalSkillMapper hadTechnicalSkillMapper;
	
	/**
	 * 所有テクニカルスキルを複数件物理削除するメソッド.
	 * 
	 * @param hadTechnicalSkillList 所有テクニカルスキルリスト
	 * @return 削除件数
	 */
	public int deleteHadTechnicalSkill(List<HadTechnicalSkill> hadTechnicalSkillList) {
		if(hadTechnicalSkillList.size() == 0) {
			return 0;
		}
		return hadTechnicalSkillMapper.deleteListByPrimaryKey(hadTechnicalSkillList);
	}
	
	/**
	 * 条件に一致したものを削除するメソッド.
     * 
     * @param example 条件
     * @return 削除件数
	 */
	public int deleteByExample(HadTechnicalSkillExample example) {
		return hadTechnicalSkillMapper.deleteByExample(example);
	}

}
