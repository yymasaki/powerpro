package com.example.service.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Status;
import com.example.form.SearchRequestForm;
import com.example.mapper.StatusMapper;

@Service
@Transactional
public class GetStatusForRequestService {
	
	@Autowired
	private StatusMapper statusMapper;
	
	/**
	 * 申請状況毎のステータスを取得する.
	 * 
	 * @param form 　申請状況検索フォーム
	 * @return 該当するステータス
	 */
	public List<Status> getStatusForRequest(SearchRequestForm form, Integer startNumber){
		List<Status> statusRequestList = new ArrayList<>();
		if (form.getDepartmentId() == 5) {
			statusRequestList = statusMapper.selectByStageAndApplicant(form.getStage(), form.getApplicant(),startNumber);
		} else if (form.getDepartmentId() == 1 || form.getDepartmentId() == 2 || form.getDepartmentId() == 3) {
			statusRequestList = statusMapper.selectByStageAndUserId(form.getStage(), form.getUserId(),startNumber);
		}
		return statusRequestList;
	}
	
}
