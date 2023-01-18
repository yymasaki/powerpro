package com.example.mapper;

import com.example.domain.HadEngineerSkill;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HadEngineerSkillMapper {
	
	/**
	 * 所有エンジニアスキルの登録を行う.
	 * 
	 * @param hadEngineerSkillList 所有エンジニアスキルリスト
	 * @return 実行回数
	 */
	int insertSelectiveList(@Param ("hadEngineerSkillList" ) List<HadEngineerSkill> hadEngineerSkillList);
	
	/**
	 * 所有エンジニアスキルの更新を行う.
	 * 
	 * @param hadEngineerSkillList 所有エンジニアスキルリスト
	 * @param statusId ステータスID
	 * @return 実行回数
	 */
	int bulkUpdate(@Param("hadEngineerSkillList") List<HadEngineerSkill> hadEngineerSkillList, @Param("statusId") Integer statusId);
	
    /**
     * 所有エンジニアスキルIDから所有エンジニアスキルの検索を行う.
     * 
     * @param hadEngineerSkillId 所有エンジニアスキルID
     * @return 所有エンジニアスキル
     */
    HadEngineerSkill selectByPrimaryKey(Integer hadEngineerSkillId);
}