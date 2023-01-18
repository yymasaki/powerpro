package com.example.service.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.TechnicalSkill;
import com.example.form.SearchRequestForm;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetTechnicalSkillForRequestService {
	
	@Autowired
	private TechnicalSkillMapper technicalSkillMapper;
	
	
	/**
	 * 申請トップ画面にて新規テクニカルスキル申請を選択されたときに情報を取得する.
	 * 
	 * @param form　申請検索フォーム
	 * @param startNumber　開始番号
	 * @return 新規テクニカルスキル申請リスト
	 */
	public List<TechnicalSkill> getTechnicalSkillForRequest(SearchRequestForm form,Integer startNumber){
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		if (form.getDepartmentId() == 5) {
			technicalSkillList = technicalSkillMapper.selectByStageAndApplicant(form.getStage(), form.getApplicant(), startNumber);
		} 
		return technicalSkillList;
	}
}
