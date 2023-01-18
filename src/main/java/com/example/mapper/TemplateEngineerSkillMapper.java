package com.example.mapper;

import com.example.domain.TemplateEngineerSkill;
import com.example.example.TemplateEngineerSkillExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TemplateEngineerSkillMapper {

    int countByExample(TemplateEngineerSkillExample example);

    int deleteByExample(TemplateEngineerSkillExample example);

    int deleteByPrimaryKey(Integer templateEngineerSkillId);

    int insert(TemplateEngineerSkill record);

    int insertSelective(TemplateEngineerSkill record);

    List<TemplateEngineerSkill> selectByExample(TemplateEngineerSkillExample example);

    TemplateEngineerSkill selectByPrimaryKey(Integer templateEngineerSkillId);

    int updateByExampleSelective(@Param("record") TemplateEngineerSkill record, @Param("example") TemplateEngineerSkillExample example);

    int updateByExample(@Param("record") TemplateEngineerSkill record, @Param("example") TemplateEngineerSkillExample example);

    int updateByPrimaryKeySelective(TemplateEngineerSkill record);

    int updateByPrimaryKey(TemplateEngineerSkill record);
}