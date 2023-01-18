package com.example.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Information;
import com.example.example.InformationExample;

@Mapper
public interface InformationMapper {
	  
	
	/**
	 * 
	 * トップ画面表示のお知らせ情報を取得する.
	 * 
	 * @param userId　ユーザーID
	 * @param currentDate 現在年月日
	 * @param offset　
	 * @return
	 */
	List<Information> selectByDepartmentIdAndStageAndCurrentDate(@Param("userId") Integer userId, @Param("currentDate") Date currentDate,@Param("offset") Integer offset);
	
	
	/**
	 * 
	 * 表示するお知らせを全件取得する
	 * 
	 * @param userId
	 * @param currentDate
	 * @return
	 */
	List<Information> selectAll(@Param("userId") Integer userId, @Param("currentDate") Date currentDate);
	
 
    int countByExample(InformationExample example);


    int deleteByExample(InformationExample example);


    int deleteByPrimaryKey(Integer informationId);


    /**
     * 
     * お知らせ情報を追加する
     * 
     * @param information　お知らせ情報
     * @return
     */
    int insert(Information information);


    int insertSelective(Information record);


    List<Information> selectByExampleWithBLOBs(InformationExample example);


    List<Information> selectByExample(InformationExample example);


    Information selectByPrimaryKey(Integer informationId);


    int updateByExampleSelective(@Param("record") Information record, @Param("example") InformationExample example);


    int updateByExampleWithBLOBs(@Param("record") Information record, @Param("example") InformationExample example);


    int updateByExample(@Param("record") Information record, @Param("example") InformationExample example);


    int updateByPrimaryKeySelective(Information record);


    int updateByPrimaryKeyWithBLOBs(Information record);


    int updateByPrimaryKey(Information record);
}