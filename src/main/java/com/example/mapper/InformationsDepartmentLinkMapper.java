package com.example.mapper;

import com.example.domain.InformationsDepartmentLink;
import com.example.example.InformationsDepartmentLinkExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InformationsDepartmentLinkMapper {

    int countByExample(InformationsDepartmentLinkExample example);


    int deleteByExample(InformationsDepartmentLinkExample example);


    int deleteByPrimaryKey(Integer informationsDepartmentLinkId);


    /**
     * 
     * お知らせと部署関係を登録する
     * 
     * @param informationsDepartmentLinkList　お知らせと部署関係リスト
     * @return
     */
    int insert(@Param("informationsDepartmentLinkList")List<InformationsDepartmentLink> informationsDepartmentLinkList);


    int insertSelective(InformationsDepartmentLink record);


    List<InformationsDepartmentLink> selectByExample(InformationsDepartmentLinkExample example);


    InformationsDepartmentLink selectByPrimaryKey(Integer informationsDepartmentLinkId);


    int updateByExampleSelective(@Param("record") InformationsDepartmentLink record, @Param("example") InformationsDepartmentLinkExample example);


    int updateByExample(@Param("record") InformationsDepartmentLink record, @Param("example") InformationsDepartmentLinkExample example);


    int updateByPrimaryKeySelective(InformationsDepartmentLink record);


    int updateByPrimaryKey(InformationsDepartmentLink record);
}