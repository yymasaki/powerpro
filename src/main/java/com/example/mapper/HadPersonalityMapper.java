package com.example.mapper;

import com.example.domain.HadPersonality;
import com.example.example.HadPersonalityExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HadPersonalityMapper {
	
	/**
	 * 所有性格の登録を行う.
	 * 
	 * @param hadPersonalityList 所有性格リスト
	 * @return 実行回数
	 */
	int insertSelectiveList(@Param ("hadPersonalityList" ) List<HadPersonality> hadPersonalityList);
	
	/**
	 * 所有性格と性格の検索を行う.
	 * 
	 * @param statusId ステータスID
	 * @return 所有性格リスト
	 */
	List<HadPersonality> selectHadPersonalityAndPersonality(@Param("statusId") Integer statusId);
	
	/**
	 * ステータスIDから所有性格リストの検索を行う.
	 * 
	 * @param statusId ステータスID
	 * @return 所有性格リスト
	 */
	List<HadPersonality> selectByStatusId(@Param("statusId") Integer statusId);
  
    /**
     * 様々な条件に合致した所有性格を削除する.
     * 
     * @param example 所有性格スキル削除条件
     * @return 実行回数
     */
    int deleteByExample(HadPersonalityExample example);

    /**
     * 所有性格IDから所有性格の検索を行う.
     * 
     * @param hadPersonalityId 所有性格ID
     * @return 所有性格
     */
    HadPersonality selectByPrimaryKey(Integer hadPersonalityId);
}