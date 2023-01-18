package com.example.service.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Information;
import com.example.mapper.InformationMapper;

@Service
@Transactional
@ComponentScan("com.example.controller.common")
public class GetInformationListService {

	@Autowired
	private InformationMapper informationsMapper;

	/**
	 * 
	 * トップページに表示するお知らせを取得する
	 * 
	 * @param userId ユーザーID
	 * @return お知らせ情報のリスト
	 */
	public List<Information> getInformationList(Integer userId, Integer offset) {
		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime());
		List<Information> informationList = informationsMapper.selectByDepartmentIdAndStageAndCurrentDate(userId, currentDate, offset);
		return informationList;
	}

	/**
	 * 
	 * 表示対象のお知らせを全件取得する
	 * 
	 * @param userId
	 * @return お知らせ情報リスト
	 */
	public List<Information> getAllInformationList(Integer userId) {
		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime());
		List<Information> informationList = informationsMapper.selectAll(userId, currentDate);
		return informationList;
	}

}
