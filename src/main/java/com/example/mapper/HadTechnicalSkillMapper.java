package com.example.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.HadTechnicalSkill;
import com.example.example.HadTechnicalSkillExample;

@Mapper
public interface HadTechnicalSkillMapper {

    /**
     * 
     * 所有テクニカルスキルを追加(申請)する.
     * 
     * @param hadTechnicalSkill　所有テクニカルスキルリスト
     * @return
     */
    int insertList(@Param("hadTechnicalSkill")List<HadTechnicalSkill> hadTechnicalSkill);

    /**
     * 
     * 初期表示する所有テクニカルスキルを取得する.(所有テクニカルスキル編集画面)
     * 
     * @param userId ユーザーid
     * @return 所有テクニカルスキルリスト
     */
    List<HadTechnicalSkill> selectByUserIdAndStage0or1or2(Integer userId);
    
    /**
     * ユーザーIDから取得するメソッド.
     * 
     * @param userId ユーザーID
     * @return 保有テクニカルスキルリスト
     */
    List<HadTechnicalSkill> selectByUserId(Integer userId);
    
    /**
     * 
     * 使用期間を複数件更新するメソッド.
     * 
     * @param hadTechnicalSkillList 保有テクニカルスキルリスト
     * @param updateAt 更新日
     * @param version　バージョン
     * @return　更新件数
     */
    int updateUsingPeriodByHadTechnicalSkillList(
    		@Param("hadTechnicalSkillList") List<HadTechnicalSkill> hadTechnicalSkillList,
    		@Param("userId") Integer userId);
    
    /**
     * 
     * stageを複数件更新するメソッド.
     * 
     * @param hadTechnicalSkillList 保有テクニカルスキルリスト
     * @param stage ステージ
     * @param updater 更新者
     * @param updateAt 更新日時
     * @return　更新件数
     */
    /**
     * @param hadTechnicalSkillList
     * @return
     */
    int updateStageByHadTechnicalSkillList(
    		@Param("hadTechnicalSkillList") List<HadTechnicalSkill> hadTechnicalSkillList, 
    		@Param("stage") Integer stage, @Param("updater") String updater, @Param("updatedAt") LocalDateTime updateAt);
    
    /**
     * 条件に一致したstageを更新するメソッド.
     * 
     * @param record 保有テクニカルスキル
     * @param example 条件
     * @return 更新件数
     */
    int updateStageByExample(@Param("record") HadTechnicalSkill record, @Param("example") HadTechnicalSkillExample example);
    
    /**
     * PKに一致した所有テクニカルスキルを複数件削除するメソッド.
     * 
     * @param hadTechnicalSkillList　所有テクニカルスキルリスト
     * @return 削除件数
     */
    int deleteListByPrimaryKey(List<HadTechnicalSkill> hadTechnicalSkillList);
    
    /**
     * 条件に一致したものを削除するメソッド.
     * 
     * @param example 条件
     * @return 削除件数
     */
    int deleteByExample(HadTechnicalSkillExample example);
    
    int countByExample(HadTechnicalSkillExample example);
    
    int deleteByPrimaryKey(Integer hadTechnicalSkillId);
    
    int insertSelective(HadTechnicalSkill record);

    HadTechnicalSkill selectByPrimaryKey(Integer hadTechnicalSkillId);
   
    int updateByPrimaryKeySelective(HadTechnicalSkill record);

    int updateByPrimaryKey(HadTechnicalSkill record);
}