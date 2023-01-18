package com.example.mapper;

import com.example.domain.TemplateBasicSkill;
import com.example.example.TemplateBasicSkillExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TemplateBasicSkillMapper {

    int countByExample(TemplateBasicSkillExample example);

    int deleteByExample(TemplateBasicSkillExample example);

    int deleteByPrimaryKey(Integer templateBasicSkillId);

    int insert(TemplateBasicSkill record);

    int insertSelective(TemplateBasicSkill record);

    List<TemplateBasicSkill> selectByExample(TemplateBasicSkillExample example);

    TemplateBasicSkill selectByPrimaryKey(Integer templateBasicSkillId);

    int updateByExampleSelective(@Param("record") TemplateBasicSkill record, @Param("example") TemplateBasicSkillExample example);

    int updateByExample(@Param("record") TemplateBasicSkill record, @Param("example") TemplateBasicSkillExample example);

    int updateByPrimaryKeySelective(TemplateBasicSkill record);

    int updateByPrimaryKey(TemplateBasicSkill record);
}