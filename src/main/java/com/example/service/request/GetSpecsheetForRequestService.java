package com.example.service.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.form.SearchRequestForm;
import com.example.mapper.AppSpecsheetMapper;

@Service
@Transactional
public class GetSpecsheetForRequestService {
	
	@Autowired
	private AppSpecsheetMapper appSpecsheetMapper;
	
	
	public List<AppSpecsheet> getSpecsheetForRequest(SearchRequestForm form,Integer startNumber){
		List<AppSpecsheet> appSpecsheetRequestList = new ArrayList<>();
		if (form.getDepartmentId() == 5) {
			appSpecsheetRequestList = appSpecsheetMapper.selectByStageAndApplicant(form.getStage(), form.getApplicant(), startNumber);
		} else if (form.getDepartmentId() == 1 || form.getDepartmentId() == 2 || form.getDepartmentId() == 3) {
			appSpecsheetRequestList = appSpecsheetMapper.selectByStageAndUserId(form.getStage(), form.getUserId(), startNumber);
		} 
		return appSpecsheetRequestList;
	}
}
