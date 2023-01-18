package com.example.service.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.example.AppSpecsheetExample;
import com.example.example.StatusExample;
import com.example.example.TechnicalSkillExample;
import com.example.form.SearchRequestForm;
import com.example.mapper.AppSpecsheetMapper;
import com.example.mapper.StatusMapper;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetCountForRequestService {
	
	@Autowired
	private StatusMapper statusMapper;
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	@Autowired
	private TechnicalSkillMapper technicalSkillMapper;
	
	/**
	 * 申請内容、申請状況別の検索数を取得する.
	 * 
	 * @param form 申請状況検索フォーム
	 * @return 該当する申請の数
	 */
	public Integer getCountForRequest(SearchRequestForm form) {
		Integer count = 0;
		if (form.getContent() == 1) { // ステータス更新
			StatusExample example = new StatusExample();
			if (form.getDepartmentId() == 5) { // 管理者
				example.or()
				.andStageEqualTo(String.valueOf(form.getStage()))
				.andCreatorLike("%" + form.getApplicant() + "%");
			} else if (form.getDepartmentId() == 1 || form.getDepartmentId() == 2 || form.getDepartmentId() == 3) { // エンジニア
				example.or()
				.andStageEqualTo(String.valueOf(form.getStage()))
				.andUserIdEqualTo(form.getUserId());
			}
			count = statusMapper.countByExample(example);
		} else if (form.getContent() == 2) { // スペックシート更新
			AppSpecsheetExample example = new AppSpecsheetExample();
			if (form.getDepartmentId() == 5) {
				example.or()
				.andStageEqualTo(String.valueOf(form.getStage()))
				.andCreatorLike("%" + form.getApplicant() + "%");
			} else if (form.getDepartmentId() == 1 || form.getDepartmentId() == 2 || form.getDepartmentId() == 3) {
				example.or()
				.andStageEqualTo(String.valueOf(form.getStage()))
				.andUserIdEqualTo(form.getUserId());
			}			
			count = appSpecsheetMapper.countByExample(example);
		} else if (form.getContent() == 3) {
			TechnicalSkillExample example = new TechnicalSkillExample();
			if (form.getDepartmentId() == 5) {
				example.or()
				.andStageEqualTo(String.valueOf(form.getStage()))
				.andCreatorLike("%" + form.getApplicant() + "%");
			}
			count = technicalSkillMapper.countByExample(example);
		}
		return count;
	}
}

