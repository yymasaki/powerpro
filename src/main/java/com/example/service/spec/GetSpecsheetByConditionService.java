package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class GetSpecsheetByConditionService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	/**
	 * カスタム条件からスペックシートを取得するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param specStageList スペックシートの検索条件としてのステージリスト
	 * @param htStageList 保有テクニカルスキルの検索条件としてのステージリスト
     * @return スペックシートリスト(最大2件)
	 */
	public List<AppSpecsheet> getSpecsheetByCondition(
			Integer specsheetId, Integer userId, List<Integer> specStageList, List<Integer> htStageList) {
		return appSpecsheetMapper.selectByCondition(specsheetId, userId, specStageList, htStageList);
	}

}
