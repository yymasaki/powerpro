package com.example.mapper;

import com.example.domain.HadBasicSkill;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HadBasicSkillMapper {
	
	/**
     * 所有基本スキル情報の登録を行う.
     * 
     * @param hadBasicSkillList 所有基本スキルリスト
     * @return 実行回数
     */
	int insertSelectiveList(@Param ("hadBasicSkillList" ) List<HadBasicSkill> hadBasicSkillList);
	
	/**
	 * 所有基本スキル情報の更新を行う.
	 * 
	 * @param hadBasicSkillList 所有基本スキルリスト
	 * @param statusId ステータスID
	 * @return 実行回数
	 */
	int bulkUpdate(@Param ("hadBasicSkillList" ) List<HadBasicSkill> hadBasicSkillList, Integer statusId);
	
	/**
	 * 所有基本スキル, 基本スキルの検索を行う
	 * 
	 * @param statusId ステータスID
	 * @return 所有基本スキルリスト
	 */
	List<HadBasicSkill> selectHadBasicSkillAndBasicSkillByStatusId(@Param("statusId") Integer statusId);
    
    /**
     * 所有基本スキルIDから所有基本スキルの検索を行う.
     * 
     * @param hadBasicSkillId 所有基本スキルID
     * @return 所有基本スキル
     */
    HadBasicSkill selectByPrimaryKey(Integer hadBasicSkillId);
}