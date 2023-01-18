package com.example.mapper;

import com.example.domain.BasicSkill;
import com.example.example.BasicSkillExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BasicSkillMapper {
	
    /**
     * 基本スキルのリストを検索する.
     * 
     * @param example 基本スキル検索条件
     * @return 基本スキルリスト
     */
    List<BasicSkill> selectByExample(BasicSkillExample example);
    
    /**
     * 基本スキルIDから基本スキルを検索する.
     * 
     * @param basicSkillId 基本スキルID
     * @return 基本スキル
     */
    BasicSkill selectByPrimaryKey(Integer basicSkillId);
}