package com.example.service.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.example.AppSpecsheetExample;
import com.example.example.StatusExample;
import com.example.example.TechnicalSkillExample;
import com.example.form.SearchRequestForm;
import com.example.mapper.AppSpecsheetMapper;
import com.example.mapper.StatusMapper;
import com.example.mapper.TechnicalSkillMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GetCountForRequestServiceTest {
	
	@Mock
	private StatusMapper statusMapper;
	
	@Mock
	private AppSpecsheetMapper appSpecsheetMapper;
	
	@Mock
	private TechnicalSkillMapper technicalSkillMapper;
	
	@InjectMocks
	private GetCountForRequestService target;
	
	
	/**
	 * 全テストで使用するformの共通部分を作成し、返す.
	 * 
	 * @return
	 */
	public SearchRequestForm setUpSearchRequestFormForTest () {
		SearchRequestForm form = new SearchRequestForm();
		form.setStage(2);
		form.setApplicant("Webエンジニア次郎");
		form.setUserId(1);
		form.setPageNumber(1);
		return form;
	}
	
	/**
	 * content=1 / departmentId=1 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーが管理者の場合にstatusの件数を取得する() {
		Integer content = 1;
		Integer departmentId = 5;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 3;
		when(statusMapper.countByExample(any(StatusExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 3);
		verify(statusMapper,times(1)).countByExample(any(StatusExample.class));
	}
	
	/**
	 * content=1 / departmentId=1 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーがWebエンジニアの場合にstatusの件数を取得する() {
		Integer content = 1;
		Integer departmentId = 1;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 1;
		when(statusMapper.countByExample(any(StatusExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 1);
		verify(statusMapper,times(1)).countByExample(any(StatusExample.class));
	}
	
	/**
	 * content=1 / departmentId=2 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーがCLエンジニアの場合にstatusの件数を取得する() {
		Integer content = 1;
		Integer departmentId = 2;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 1;
		when(statusMapper.countByExample(any(StatusExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 1);
		verify(statusMapper,times(1)).countByExample(any(StatusExample.class));
	}
	
	/**
	 * content=1 / departmentId=3 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーがMLエンジニアの場合にstatusの件数を取得する() {
		Integer content = 1;
		Integer departmentId = 3;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 1;
		when(statusMapper.countByExample(any(StatusExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 1);
		verify(statusMapper,times(1)).countByExample(any(StatusExample.class));
	}
	
	/**
	 * content=2 / departmentId=5 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーが管理者の場合にspecsheetの件数を取得する() {
		Integer content = 2;
		Integer departmentId = 5;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 3;
		when(appSpecsheetMapper.countByExample(any(AppSpecsheetExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 3);
		verify(appSpecsheetMapper,times(1)).countByExample(any(AppSpecsheetExample.class));
	}
	
	/**
	 * content=2 / departmentId=1 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーがWebエンジニアの場合にspecsheetの件数を取得する() {
		Integer content = 2;
		Integer departmentId = 1;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 1;
		when(appSpecsheetMapper.countByExample(any(AppSpecsheetExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 1);
		verify(appSpecsheetMapper,times(1)).countByExample(any(AppSpecsheetExample.class));
	}
	
	/**
	 * content=2 / departmentId=2 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーがCLエンジニアの場合にspecsheetの件数を取得する() {
		Integer content = 2;
		Integer departmentId = 2;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 1;
		when(appSpecsheetMapper.countByExample(any(AppSpecsheetExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 1);
		verify(appSpecsheetMapper,times(1)).countByExample(any(AppSpecsheetExample.class));
	}
	
	/**
	 * content=2 / departmentId=3 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーがMLエンジニアの場合にspecsheetの件数を取得する() {
		Integer content = 2;
		Integer departmentId = 3;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 1;
		when(appSpecsheetMapper.countByExample(any(AppSpecsheetExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 1);
		verify(appSpecsheetMapper,times(1)).countByExample(any(AppSpecsheetExample.class));
	}
	
	/**
	 * content=3 / departmentId=5 の場合のgetCountForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void ログインユーザーが管理者の場合に新規テクニカルスキル申請の件数を取得する() {
		Integer content = 3;
		Integer departmentId = 5;
		
		SearchRequestForm form = setUpSearchRequestFormForTest();
		form.setContent(content);
		form.setDepartmentId(departmentId);
		
		Integer count = 3;
		when(technicalSkillMapper.countByExample(any(TechnicalSkillExample.class))).thenReturn(count);
		Integer actual = target.getCountForRequest(form);
		assertEquals(actual, 3);
		verify(technicalSkillMapper,times(1)).countByExample(any(TechnicalSkillExample.class));
	}
}
