package com.example.mapper;

import com.example.domain.EngineerSkill;
import com.example.example.EngineerSkillExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EngineerSkillMapper {

    /**
     * 全てのエンジニアスキルを検索する.
     * 
     * @param example
     * @return エンジニアスキルリスト
     */
    List<EngineerSkill> selectByExample(EngineerSkillExample example);

    /**
     * エンジニアIDからエンジニアスキルを検索する.
     * 
     * @param engineerSkillId
     * @return
     */
    EngineerSkill selectByPrimaryKey(Integer engineerSkillId);
}