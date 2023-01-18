package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.example.AppSpecsheetExample;
import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class GetNewestSpecsheetService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	/**
	 * 最新versionのスペックシートを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return スペックシート
	 */
	public AppSpecsheet getNewestSpecsheet(Integer userId) {
		AppSpecsheetExample example = new AppSpecsheetExample();
		example.createCriteria().andUserIdEqualTo(userId);
		example.setOrderByClause("version desc");
		List<AppSpecsheet> specsheetList = appSpecsheetMapper.selectByExample(example);
		if(specsheetList.size() == 0) {
			return null;
		}
		return specsheetList.get(0);
	}

}
