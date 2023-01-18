package com.example.service.spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class UpdateSpecsheetService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	/**
	 * スペックシートを更新するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @return 更新件数
	 */
	public int updateSpecsheet(AppSpecsheet specsheet) {
		return appSpecsheetMapper.updateByPrimaryKey(specsheet);
	}

	/**
	 * スペックシートのステージを更新するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @return 更新件数
	 */
	public int updateSpecsheetStage(AppSpecsheet specsheet) {
		return appSpecsheetMapper.updateStageByPrimaryKey(specsheet);
	}

}
