package com.example.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;

@Mapper
public interface TechnicalSkillMapper {

	/**
	 * 複数件のテクニカルスキル情報を登録する.
	 * 
	 * @param technicalSkillList テクニカルスキルリスト
	 * @return 挿入件数
	 */
	int insertTechnicalSkillList(@Param("technicalSkillList")List<TechnicalSkill> technicalSkillList);
	
	/**
	 * 申請トップ画面（管理者画面）にて新規テクニカルスキル申請が選択された際に検索を行う.
	 * 
	 * @param stage 申請段階
	 * @param       applicant 申請者名
	 * @param       startNumber 開始番号
	 * @return テクニカルスキルリスト
	 */
	List<TechnicalSkill> selectByStageAndApplicant(@Param("stage") Integer stage,
			@Param("applicant") String applicant, @Param("startNumber") Integer startNumber);

	/**
	 * テクニカルスキルIDからテクニカルスキル情報,ユーザ情報、部署情報を取得する.
	 * 
	 * @return テクニカルスキル情報
	 */
	TechnicalSkill selectByTechnicalSkillId(Integer technicalSkillId);

	/**
	 * テクニカルスキルIDリストとバージョンリストからテクニカルスキルを取得する.
	 * @param technicalSkillIdList　テクニカルスキルIDリスト
	 * @param versionList　　　　　バージョンリスト
	 * @return　テクニカルスキルリスト
	 */
	List <TechnicalSkill> selectByTechinicalSkillIdListAndVersionList(@Param("technicalSkillIdList") List<Integer> technicalSkillIdList
			,@Param("versionList") List<Integer> versionList);
	
	/**
	 * スペックシートIDと保有テクニカルスキルとステージからテクニカルスキルを取得する.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param stage ステージ
	 * @return テクニカルスキルリスト
	 */
	List <TechnicalSkill> selectBySpecsheetIdAndUserIdAndStage(
			@Param("specsheetId") Integer specsheetId, @Param("userId") Integer userId, 
			@Param("specStageList") List<Integer> specStageList);

	int countByExample(TechnicalSkillExample example);

	int deleteByExample(TechnicalSkillExample example);

	int deleteByPrimaryKey(Integer technicalSkillId);

	int insertSelective(TechnicalSkill record);

	/**
	 * 指定条件からテクニカルスキル情報を取得する.
	 * 
	 * @param example
	 * @return テクニカルスキル情報のリスト
	 */
	List<TechnicalSkill> selectByExample(TechnicalSkillExample example);

	/**
	 * 主キー(テクニカルスキルID)からテクニカルスキル情報を取得する.
	 * 
	 * @param technicalSkillId
	 * @return
	 */
	TechnicalSkill selectByPrimaryKey(Integer technicalSkillId);

	int updateByExampleSelective(@Param("record") TechnicalSkill record,
			@Param("example") TechnicalSkillExample example);

	int updateByExample(@Param("record") TechnicalSkill record, @Param("example") TechnicalSkillExample example);

	/**
	 * 主キーのテクニカルスキル情報を更新する.
	 * 
	 * @param record テクニカルスキル情報
	 * @return 更新件数
	 */
	int updateByPrimaryKeySelective(TechnicalSkill record);

	int updateByPrimaryKey(TechnicalSkill record);

	/**
	 * リクエストユーザーIDとステージを複数件更新するメソッド.
	 * 
	 * @param technicalSkillList テクニカルスキルリスト
	 * @param requestUserId      リクエストユーザーID
	 * @param stage              ステージ
	 * @param updater            更新者
	 * @param updateAt           更新日時
	 * @return 更新件数
	 */
	int updateRequestUserIdAndStageByPrimaryKeyAsList(
			@Param("technicalSkillList") List<TechnicalSkill> technicalSkillList,
			@Param("requestUserId") Integer requestUserId, @Param("stage") String stage,
			@Param("updater") String updater, @Param("updatedAt") LocalDateTime updateAt);
	
	/**
	 * ステージを複数件更新するメソッド.
	 * 
	 * @param technicalSkillList テクニカルスキルリスト
	 * @param stage              ステージ
	 * @param updater            更新者
	 * @param updateAt           更新日時
	 * @return 更新件数
	 */
	int updateStageByPrimaryKeyAsList(
			@Param("technicalSkillList") List<TechnicalSkill> technicalSkillList,
			@Param("stage") String stage,
			@Param("updater") String updater, @Param("updatedAt") LocalDateTime updatedAt);
}