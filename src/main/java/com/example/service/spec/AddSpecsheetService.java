package com.example.service.spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class AddSpecsheetService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	/**
	 * スペックシートを登録するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @return 挿入したIdを格納したスペックシート
	 */
	public AppSpecsheet addSpecsheet(AppSpecsheet specsheet) {
		appSpecsheetMapper.insert(specsheet);
		return specsheet;
	}

}
