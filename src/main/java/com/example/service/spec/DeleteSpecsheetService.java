package com.example.service.spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class DeleteSpecsheetService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	/**
	 * スペックシートを物理削除するメソッド.
	 * 
	 * 依存関係にある開発経験と使用テクニカルスキルも一緒に削除
	 * 
	 * @param specsheetId スペックシートID
	 * @return 削除件数
	 */
	public int deleteSpecsheet(Integer specsheetId) {
		return appSpecsheetMapper.deleteByPrimaryKey(specsheetId);
	}

}
