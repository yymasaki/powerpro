package com.example.mapper;

import com.example.domain.Personality;
import com.example.example.PersonalityExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalityMapper {

    /**
     * 性格情報の入ったリストの検索を行う.
     * 
     * @param example 性格検索条件
     * @return 性格リスト
     */
    List<Personality> selectByExample(PersonalityExample example);

    /**
     * 性格IDから性格の検索を行う.
     * 
     * @param personalityId 性格ID
     * @return 性格
     */
    Personality selectByPrimaryKey(Integer personalityId);
}