package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.AppSpecsheet;
import com.example.example.AppSpecsheetExample;

@Mapper
public interface AppSpecsheetMapper {
	
	/**
	 * 条件からスペックシート検索するメソッド.
	 * 
	 * @param example 検索条件
	 * @return スペックシートリスト
	 */
	List<AppSpecsheet> selectByExample(AppSpecsheetExample example);
	
	/**
	 * カスタム条件からスペックシートを検索するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param stage ステージ
	 * @param specStageList スペックシートの検索条件としてのステージリスト
	 * @param htStageList 保有テクニカルスキルの検索条件としてのステージリスト
	 * @return スペックシートリスト(最大2件)
	 */
	List<AppSpecsheet> selectByCondition(
			@Param("specsheetId") Integer specsheetId, @Param("userId") Integer userId, 
			@Param("specStageList") List<Integer> specStageList, @Param("htStageList") List<Integer> htStageList);
	
	/**
	 * 申請トップ画面（管理者画面）にてスペックシート更新が選択された際に検索を行う.
	 * 
	 * @param stage 申請段階
	 * @param applicant　申請者名
	 * @param startNumber　開始番号
	 * @return スペックシートリスト 
	 */
	List<AppSpecsheet> selectByStageAndApplicant(@Param("stage") Integer stage, 
			@Param("applicant") String applicant, @Param("startNumber") Integer startNumber);
	
	/**
	 * 申請トップ画面（ユーザー画面）にてスペックシート更新が選択された際に検索を行う.
	 * 
	 * @param stage 申請段階
	 * @param userId　ユーザーid
	 * @param startNumber　開始番号
	 * @return スペックシートリスト
	 */
	List<AppSpecsheet> selectByStageAndUserId(@Param("stage") Integer stage, 
			@Param("userId") Integer userId, @Param("startNumber") Integer startNumber);
	
	/**
	 * スペックシートダウンロードの際に選択した開発経験のみを検索するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param devExperienceIdList 開発経験Idリスト
	 * @param version バージョン
	 * @return スペックシートリスト
	 */
	List<AppSpecsheet> selectByPrimaryKeyAndDevExperience(
			@Param("specsheetId") Integer specsheetId, @Param("userId") Integer userId, 
			@Param("devExperienceIdList") List<Integer> devExperienceIdList, @Param("version") Integer version);
	
	/**
	 * スペックシートを挿入するメソッド.
	 * 
	 * @param specsheet 
	 * @return 挿入された行数
	 */
	int insert(AppSpecsheet specsheet);
	
	/**
	 * スペックシートを更新するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @return 更新件数
	 */
	int updateByPrimaryKey(AppSpecsheet specsheet);
	
	/**
	 * スペックシートのステージを更新するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @return 更新件数
	 */
	int updateStageByPrimaryKey(AppSpecsheet specsheet);
	
	/**
	 * PKに一致する使用テクニカルスキル、開発経験とスペックシートを削除するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @return 削除件数
	 */
	int deleteByPrimaryKey(Integer specsheetId);
	
    int countByExample(AppSpecsheetExample example);

    /**
     * テスト時検証用select.
     * 
     * @param specsheetId スペックシートID
     * @return スペックシート
     */
    AppSpecsheet selectByPrimaryKey(Integer specsheetId);
    
//    int deleteByExample(AppSpecsheetExample example);
//
//    int insertSelective(AppSpecsheet record);
//
//    int updateByExampleSelective(@Param("record") AppSpecsheet record, @Param("example") AppSpecsheetExample example);
//
//    int updateByExample(@Param("record") AppSpecsheet record, @Param("example") AppSpecsheetExample example);
//
//    int updateByPrimaryKeySelective(AppSpecsheet record);
}