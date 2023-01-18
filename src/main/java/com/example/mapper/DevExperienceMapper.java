package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.DevExperience;

@Mapper
public interface DevExperienceMapper {
	
	/**
	 * スペックシートIDから検索するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @return 開発経験リスト
	 */
	List<DevExperience> selectBySpecsheetId(Integer specsheetId);
	
	/**
	 * 複数の開発経験を挿入するメソッド.
	 * 
	 * @param devExperienceList 開発経験リスト
	 * @return 挿入件数
	 */
	int insertList(List<DevExperience> devExperienceList);
	
	/**
	 * 複数の開発経験を更新するメソッド.
	 * 
	 * @param devExperienceList 開発経験リスト
	 * @return 更新件数
	 */
	int updateListByPrimaryKey(List<DevExperience> devExperienceList);
	
	/**
	 * 複数の開発経験を削除するメソッド.
	 * 
	 * @param devExperienceList 開発経験リスト
	 * @return 削除件数
	 */
	int deleteListByPrimaryKey(List<DevExperience> devExperienceList);
	
//    int countByExample(DevExperienceExample example);
//
//    int deleteByExample(DevExperienceExample example);
//
//    int deleteByPrimaryKey(Integer devExperienceId);
//
//    int insert(DevExperience record);
//
//    int insertSelective(DevExperience record);
//
//    List<DevExperience> selectByExample(DevExperienceExample example);
//
//    int updateByExampleSelective(@Param("record") DevExperience record, @Param("example") DevExperienceExample example);
//
//    int updateByExample(@Param("record") DevExperience record, @Param("example") DevExperienceExample example);
//
//    int updateByPrimaryKeySelective(DevExperience record);
}