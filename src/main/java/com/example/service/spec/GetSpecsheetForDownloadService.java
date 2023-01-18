package com.example.service.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class GetSpecsheetForDownloadService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	/**
	 * ダウンロード用にスペックシート情報を取得するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param devExperienceIdList 開発経験Idリスト
	 * @param version バージョン
	 * @return スペックシート
	 */
	public AppSpecsheet getSpecsheetForDownload(
			Integer specsheetId, Integer userId, List<Integer> devExperienceIdList, Integer version) {
		List<AppSpecsheet> specsheetList = 
				appSpecsheetMapper.selectByPrimaryKeyAndDevExperience(specsheetId, userId, devExperienceIdList, version);
		if(specsheetList.size() == 0) {
			return null;
		}
		return specsheetList.get(0);
	}

}
