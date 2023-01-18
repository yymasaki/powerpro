package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Status;
import com.example.example.StatusExample;

@Mapper
public interface StatusMapper {
	
	/**
	 * ステータスIDからステータスの検索を行う.
	 * 
	 * @param statusId ステータスID
	 * @return ステータス
	 */
	Status selectStatusAndSkillsByPrimaryKey(@Param("statusId") Integer statusId); 
	
	/**
	 * ユーザーIDと申請状況(stage2つ)からステータスと所有しているスキル（基本, エンジニア, 性格）検索を行う.
	 * 
	 * @param userId ユーザーID
	 * @return ステータスリスト
	 */
	List<Status> selectStatusAndSkillsByUserIdAndStages(@Param("userId")  Integer userId, @Param("stage1") Integer stage1, @Param("stage2") Integer stage2);  
	
	/**
	 * ユーザーIDと申請状況(stage2つ)からステータスと所有しているスキル（基本, エンジニア）検索を行う.
	 * 
	 * @param userId ユーザーID
	 * @return ステータスリスト
	 */
	List<Status> selectStatusAndSkillsWithoutPersonalitiesByUserIdAndStages(@Param("userId")  Integer userId, @Param("stage1") Integer stage1, @Param("stage2") Integer stage2);  
	
	/**
	 * 申請者名と申請段階で検索を行う（管理者画面）.
	 * 
	 * @param stage　申請段階
	 * @param applicant　申請者名
	 * @param startNumber 開始番号
	 * @return
	 */
	List <Status> selectByStageAndApplicant(@Param("stage") Integer stage,@Param("applicant") String applicant,@Param("startNumber") Integer startNumber);
	
	/**
	 * ユーザーidと申請状況で検索を行う（エンジニア画面）.
	 * 
	 * @param stage 申請状況
	 * @param userId　ユーザーid
	 * @param startNumber 開始番号
	 * @return
	 */
	List <Status> selectByStageAndUserId(@Param("stage") Integer stage,@Param("userId") Integer userId,@Param("startNumber") Integer startNumber);
	
    int countByExample(StatusExample example);

    /**
     * ステータスIDに一致するステータス,所有基本スキル,エンジニアスキル,性格を削除する.
     * 
     * @param statusId ステータスID
     * @return 実行回数
     */
    int deleteByPrimaryKey(Integer statusId);
    
    /**
     * 条件に一致したステータスを削除する.
     * 
     * @param example 条件
     * @return 実行回数
     */
    int deleteByExample(StatusExample example);

    /**
     * ステータスの登録を行う.
     * 
     * @param record ステータス
     * @return 実行回数
     */
    int insertSelective(Status record);

    /**
     * ステータスリストの検索を行う.
     * 
     * @param example userIdとstage
     * @return ステータスリスト
     */
    List<Status> selectByExample(StatusExample example);

    /**
     * ステータスIDからステータスの検索を行う.
     * 
     * @param statusId ステータスID
     * @return ステータス情報
     */
    Status selectByPrimaryKey(Integer statusId);

    /**
     * ステータス情報の更新を行う.
     * 
     * @param record ステータス
     * @return 実行回数
     */
    int updateByPrimaryKeySelective(Status record);
}