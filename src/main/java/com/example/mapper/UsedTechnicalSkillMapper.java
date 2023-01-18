package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.UsedTechnicalSkill;

@Mapper
public interface UsedTechnicalSkillMapper {
	
	/**
	 * 複数の利用テクニカルスキルを挿入するメソッド.
	 * 
	 * @param usedTechnicalSkillList 利用テクニカルスキルリスト
	 * @return 挿入件数
	 */
	int insertList(List<UsedTechnicalSkill> usedTechnicalSkillList);
	
	/**
	 * 複数の利用テクニカルスキルを削除するメソッド.
	 * 
	 * @param usedTechnicalSkillList 利用テクニカルスキルリスト
	 * @return 削除件数
	 */
	int deleteListByPrimaryKey(List<UsedTechnicalSkill> usedTechnicalSkillList);
	
	/**
	 * テスト時検証用select.
	 * 
	 * @param usedTechnicalSkillId 主キー
	 * @return 使用テクニカルスキル
	 */
	UsedTechnicalSkill selectByPrimaryKey(Integer usedTechnicalSkillId);
	
//    int countByExample(UsedTechnicalSkillExample example);
//
//    int deleteByExample(UsedTechnicalSkillExample example);
//
//    int deleteByPrimaryKey(Integer usedTechnicalSkillId);
//    
//    int insert(UsedTechnicalSkill record);
//
//    int insertSelective(UsedTechnicalSkill record);
//
//    List<UsedTechnicalSkill> selectByExample(UsedTechnicalSkillExample example);
//
//    int updateByExampleSelective(@Param("record") UsedTechnicalSkill record, @Param("example") UsedTechnicalSkillExample example);
//
//    int updateByExample(@Param("record") UsedTechnicalSkill record, @Param("example") UsedTechnicalSkillExample example);
//
//    int updateByPrimaryKeySelective(UsedTechnicalSkill record);
//
//    int updateByPrimaryKey(UsedTechnicalSkill record);
}