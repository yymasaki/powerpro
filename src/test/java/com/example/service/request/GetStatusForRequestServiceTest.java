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

import com.example.domain.Status;
import com.example.form.SearchRequestForm;
import com.example.mapper.StatusMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetStatusForRequestServiceTest {
	
	@Mock
	private StatusMapper statusMapper;
	
	@InjectMocks
	private GetStatusForRequestService target;
	
	/**
	 * departmentIdが5の場合のgetStatusForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが5の場合のgetStatusForRequestメソッドのテスト() {
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
		
		List<Status> statusList = new ArrayList<>();
		when(statusMapper.selectByStageAndApplicant(stage, applicant, startNumber)).thenReturn(statusList);
		
		List<Status> actual = target.getStatusForRequest(form, startNumber);
		assertEquals(actual, statusList);
	}
	
	
	/**
	 * departmentIdが1の場合のgetStatusForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが1の場合のgetStatusForRequestメソッドのテスト() {
		Integer departmentId = 1;
		Integer stage = 2;
		Integer userId = 1;
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant("テスト三郎");
		form.setDepartmentId(departmentId);
		form.setUserId(userId);
		form.setPageNumber(1);
		
		List<Status> statusList = new ArrayList<>();
		when(statusMapper.selectByStageAndUserId(stage, userId, startNumber)).thenReturn(statusList);
		
		List<Status> actual = target.getStatusForRequest(form, startNumber);
		assertEquals(actual, statusList);
	}
	
	/**
	 * departmentIdが2の場合のgetStatusForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが2の場合のgetStatusForRequestメソッドのテスト() {
		Integer departmentId = 2;
		Integer stage = 2;
		Integer userId = 1;
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant("テスト三郎");
		form.setDepartmentId(departmentId);
		form.setUserId(userId);
		form.setPageNumber(1);
		
		List<Status> statusList = new ArrayList<>();
		when(statusMapper.selectByStageAndUserId(stage, userId, startNumber)).thenReturn(statusList);
		
		List<Status> actual = target.getStatusForRequest(form, startNumber);
		assertEquals(actual, statusList);
	}
	
	/**
	 * departmentIdが3の場合のgetStatusForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが3の場合のgetStatusForRequestメソッドのテスト() {
		Integer departmentId = 3;
		Integer stage = 2;
		Integer userId = 1;
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant("テスト三郎");
		form.setDepartmentId(departmentId);
		form.setUserId(userId);
		form.setPageNumber(1);
		
		List<Status> statusList = new ArrayList<>();
		when(statusMapper.selectByStageAndUserId(stage, userId, startNumber)).thenReturn(statusList);
		
		List<Status> actual = target.getStatusForRequest(form, startNumber);
		assertEquals(actual, statusList);
	}
}
