package com.example.service.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.AppSpecsheet;
import com.example.form.SearchRequestForm;
import com.example.mapper.AppSpecsheetMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetSpecsheetForRequestServiceTest {
	
	@Mock
	private AppSpecsheetMapper appSpecsheetMapper;
	
	@InjectMocks
	private GetSpecsheetForRequestService target;
	
	/**
	 * departmentIdが5の場合のgetSpecsheetForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが5の場合のgetSpecsheetForRequestメソッドのテスト() {
		Integer departmentId = 5;
		Integer stage = 2;
		String applicant = "テスト三郎";
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant(applicant);
		form.setDepartmentId(departmentId);
		form.setUserId(1);
		form.setPageNumber(1);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		when(appSpecsheetMapper.selectByStageAndApplicant(stage, applicant, startNumber)).thenReturn(specsheetList);
		
		List<AppSpecsheet> actual = target.getSpecsheetForRequest(form, startNumber);
		assertEquals(actual, specsheetList);
	}
	
	/**
	 * departmentIdが1の場合のgetSpecsheetForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが1の場合のgetSpecsheetForRequestメソッドのテスト() {
		Integer departmentId = 1;
		Integer stage = 2;
		Integer userId = 1;
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant("テスト三郎");
		form.setDepartmentId(departmentId);
		form.setUserId(1);
		form.setPageNumber(1);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		when(appSpecsheetMapper.selectByStageAndUserId(stage, userId, startNumber)).thenReturn(specsheetList);
		
		List<AppSpecsheet> actual = target.getSpecsheetForRequest(form, startNumber);
		assertEquals(actual, specsheetList);
	}
	
	/**
	 * departmentIdが2の場合のgetSpecsheetForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが2の場合のgetSpecsheetForRequestメソッドのテスト() {
		Integer departmentId = 2;
		Integer stage = 2;
		Integer userId = 1;
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant("テスト三郎");
		form.setDepartmentId(departmentId);
		form.setUserId(1);
		form.setPageNumber(1);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		when(appSpecsheetMapper.selectByStageAndUserId(stage, userId, startNumber)).thenReturn(specsheetList);
		
		List<AppSpecsheet> actual = target.getSpecsheetForRequest(form, startNumber);
		assertEquals(actual, specsheetList);
	}
	
	/**
	 * departmentIdが3の場合のgetSpecsheetForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが3の場合のgetSpecsheetForRequestメソッドのテスト() {
		Integer departmentId = 3;
		Integer stage = 2;
		Integer userId = 1;
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant("テスト三郎");
		form.setDepartmentId(departmentId);
		form.setUserId(1);
		form.setPageNumber(1);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		when(appSpecsheetMapper.selectByStageAndUserId(stage, userId, startNumber)).thenReturn(specsheetList);
		
		List<AppSpecsheet> actual = target.getSpecsheetForRequest(form, startNumber);
		assertEquals(actual, specsheetList);
	}
	
}
