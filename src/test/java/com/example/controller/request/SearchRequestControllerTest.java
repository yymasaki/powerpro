package com.example.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.SearchRequestForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.request.GetCountForRequestService;
import com.example.service.request.GetSpecsheetForRequestService;
import com.example.service.request.GetStatusForRequestService;
import com.example.service.request.GetTechnicalSkillForRequestService;
import com.example.service.user.GetAllUsersService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchRequestControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private AdminloginUser adminLoginUser;
	
    @Autowired
    protected WebApplicationContext context;	
	
    @MockBean
	private GetStatusForRequestService getStatusRequestService;

    @MockBean
	private GetSpecsheetForRequestService getAppSpecsheetRequestService;

    @MockBean
	private GetTechnicalSkillForRequestService getNewTechnicalSkillRequestService;

    @MockBean
	private GetCountForRequestService getUnapprovedRequestCountService;	
	
	@MockBean
	private GetAllUsersService getAllUsersService;
	
	@Autowired
	private SearchRequestController target;

	@BeforeEach
	public void setUp() throws Exception {
		// SpringMvcの動作を再現
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		// SpringSecurityを適用させる 
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();		
	}
	
	
	/**
	 * SearchRequestFormのcontent,applicant,stage,pageNumberが空の場合.
	 * 
	 * @throws Exception
	 */
	@Test
	public void 申請トップ画面を初期表示する() throws Exception {
		// モック生成
		Integer countMock = 1;
		Status statusMock = new Status();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");
		statusMock.setStage(2);
		statusMock.setUpdatedAt(LocalDateTime.now());
		statusMock.setUser(mockUser);
		List<Status> statusListMock = Arrays.asList(statusMock);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(countMock);
		when(getStatusRequestService.getStatusForRequest(any(SearchRequestForm.class), anyInt())).thenReturn(statusListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("statusRequestList", statusListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(1)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(1)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(0)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(0)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}
		
	/**
	 * 申請トップ画面でステータス更新が検索された場合（0件）.
	 * 
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void 申請トップ画面_contentが1で検索件数が0件() throws Exception {
		// モック生成
		Integer countMock = 1;
		Status statusMock = new Status();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");
		statusMock.setStage(2);
		statusMock.setUpdatedAt(LocalDateTime.now());
		statusMock.setUser(mockUser);
		List<Status> statusListMock = Arrays.asList(statusMock);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(0, countMock);
		when(getStatusRequestService.getStatusForRequest(any(SearchRequestForm.class), anyInt()))
			.thenReturn(new ArrayList<Status>(),statusListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("content", "1")
				.param("applicant", "サンプル")
				.param("stage", "2")
				.param("pageNumber", "1");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("noDataMessage", "該当するデータがありません"))
			.andExpect(model().attribute("statusRequestList", statusListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(2)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(2)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(0)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(0)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}
	
	/**
	 * 申請トップ画面でスペックシート更新が検索された場合（1件以上ヒット）.
	 * 
	 * @throws Exception
	 */
	@Test
	public void 申請トップ画面_contentが2で検索結果が1件以上() throws Exception {
		// モック生成
		Integer countMock = 1;
		AppSpecsheet specsheetMock = new AppSpecsheet();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");		
		specsheetMock.setStage(2);
		specsheetMock.setUpdatedAt(LocalDateTime.now());
		specsheetMock.setUser(mockUser);
		List<AppSpecsheet> specsheetListMock = Arrays.asList(specsheetMock);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(countMock);
		when(getAppSpecsheetRequestService.getSpecsheetForRequest(any(SearchRequestForm.class), anyInt())).thenReturn(specsheetListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("content", "2")
				.param("applicant", "")
				.param("stage", "2")
				.param("pageNumber", "1");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("appSpecsheetRequestList", specsheetListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(1)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(0)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(1)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(0)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}

	/**
	 * 申請トップ画面でスペックシート更新が検索された場合（0件）.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void 申請トップ画面_contentが2で検索結果が0件() throws Exception {
		// モック生成
		Integer countMock = 1;
		AppSpecsheet specsheetMock = new AppSpecsheet();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");		
		specsheetMock.setStage(2);
		specsheetMock.setUpdatedAt(LocalDateTime.now());
		specsheetMock.setUser(mockUser);
		List<AppSpecsheet> specsheetListMock = Arrays.asList(specsheetMock);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(0,countMock);
		when(getAppSpecsheetRequestService.getSpecsheetForRequest(any(SearchRequestForm.class), anyInt()))
			.thenReturn(new ArrayList<AppSpecsheet>(),specsheetListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("content", "2")
				.param("applicant", "サンプル")
				.param("stage", "2")
				.param("pageNumber", "1");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("noDataMessage", "該当するデータがありません"))
			.andExpect(model().attribute("appSpecsheetRequestList", specsheetListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(2)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(0)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(2)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(0)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}
	
	/**
	 * 申請トップ画面で新規テクニカルスキルが検索された場合（1件以上ヒット）.
	 * 
	 * @throws Exception
	 */
	@Test
	public void 申請トップ画面_contentが3で検索結果が1件以上() throws Exception {
		// モック生成
		Integer countMock = 1;
		TechnicalSkill technicalSkillMock = new TechnicalSkill();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");
		technicalSkillMock.setStage(2);
		technicalSkillMock.setUpdatedAt(LocalDateTime.now());
		technicalSkillMock.setUser(mockUser);
		List<TechnicalSkill> technicalSkillListMock = Arrays.asList(technicalSkillMock);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(countMock);
		when(getNewTechnicalSkillRequestService.getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt())).thenReturn(technicalSkillListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("content", "3")
				.param("applicant", "")
				.param("stage", "2")
				.param("pageNumber", "1");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("technicalSkillRequestList", technicalSkillListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(1)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(0)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(0)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(1)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}
	
	/**
	 * 申請トップ画面で新規テクニカルスキルが検索された場合（0件）.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void 申請トップ画面_contentが3で検索結果が0件() throws Exception {
		// モック生成
		Integer countMock = 1;
		TechnicalSkill technicalSkillMock = new TechnicalSkill();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");
		technicalSkillMock.setStage(2);
		technicalSkillMock.setUpdatedAt(LocalDateTime.now());
		technicalSkillMock.setUser(mockUser);
		List<TechnicalSkill> technicalSkillListMock = Arrays.asList(technicalSkillMock);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(0,countMock);
		when(getNewTechnicalSkillRequestService.getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt()))
			.thenReturn(new ArrayList<TechnicalSkill>() ,technicalSkillListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("content", "3")
				.param("applicant", "サンプル")
				.param("stage", "2")
				.param("pageNumber", "1");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("noDataMessage", "該当するデータがありません"))
			.andExpect(model().attribute("technicalSkillRequestList", technicalSkillListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(2)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(0)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(0)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(2)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}
	
	/**
	 * 7/31追記
	 * 
	 * @throws Exception
	 */
	@Test
	public void 新規テクニカルスキル修正画面から申請トップ画面に戻ってきたとき元の検索条件で表示する() throws Exception {
		// モック生成
		Integer countMock = 1;
		TechnicalSkill technicalSkillMock = new TechnicalSkill();
		// thymeleafエラーが出ないように最低限の項目をセット
		Department departmentMock = new Department();
		User mockUser = new User();
		departmentMock.setName("Web");
		mockUser.setDepartment(departmentMock);
		mockUser.setName("テスト太郎");
		technicalSkillMock.setStage(2);
		technicalSkillMock.setUpdatedAt(LocalDateTime.now());
		technicalSkillMock.setUser(mockUser);
		List<TechnicalSkill> technicalSkillListMock = Arrays.asList(technicalSkillMock);
		
		//sessionスコープの検索条件を作成
		Integer content=3;
		String applicant="";
		Integer stage=2;
		Integer pageNumber=1;
		SearchRequestForm searchRequestForm = new SearchRequestForm();
		searchRequestForm.setContent(content);
		searchRequestForm.setApplicant(applicant);
		searchRequestForm.setStage(stage);
		searchRequestForm.setPageNumber(pageNumber);
		Map<String, Object> sessionAttributes = new HashMap<>();
		sessionAttributes.put("searchRequestForm", searchRequestForm);
		
		when(getUnapprovedRequestCountService.getCountForRequest(any(SearchRequestForm.class))).thenReturn(countMock);
		when(getNewTechnicalSkillRequestService.getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt())).thenReturn(technicalSkillListMock);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request")
				.with(user(new LoginUser(loginUser, authorityList)))
				.sessionAttrs(sessionAttributes)
				.param("pageBack", "true");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("technicalSkillRequestList", technicalSkillListMock))
			.andExpect(model().attributeExists("searchRequestForm"))
			.andExpect(model().attributeExists("pageNumber"))
			.andExpect(model().attributeExists("maxPageNumber"))
			.andExpect(model().attributeExists("searchCount"))
			.andExpect(model().attributeExists("userListForAutocomplete"))
			.andExpect(view().name("request/request"));
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getUnapprovedRequestCountService, times(1)).getCountForRequest(any(SearchRequestForm.class));
		verify(getStatusRequestService, times(0)).getStatusForRequest(any(SearchRequestForm.class), anyInt());
		verify(getAppSpecsheetRequestService, times(0)).getSpecsheetForRequest(any(SearchRequestForm.class), anyInt());
		verify(getNewTechnicalSkillRequestService, times(1)).getTechnicalSkillForRequest(any(SearchRequestForm.class), anyInt());
	}
	
	/**
	 * calcStartNumberメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void calcStartNumberメソッドのテスト() {
		Integer pageNumber1 = 1;
		Integer pageNumber2 = 2;
		Integer pageNumber3 = 3;
		
		Integer actual1 = target.calcStartNumber(pageNumber1);
		Integer actual2 = target.calcStartNumber(pageNumber2);
		Integer actual3 = target.calcStartNumber(pageNumber3);
		
		assertEquals(actual1, 0);
		assertEquals(actual2, 20);
		assertEquals(actual3, 40);
	}
	
	/**
	 * calcStartNumberメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void calcMaxPageNumberメソッドのテスト() {
		Integer searchCount0 = 0;
		Integer searchCount20 = 20;
		Integer searchCount21 = 21;
		
		Integer actual0 = target.calcMaxPageNumber(searchCount0);
		Integer actual20 = target.calcMaxPageNumber(searchCount20);
		Integer actual21 = target.calcMaxPageNumber(searchCount21);
		
		assertEquals(actual0, 1);
		assertEquals(actual20, 1);
		assertEquals(actual21, 2);
	}
	
	/**
	 * getUserListForAutocompleteメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void getUserListForAutocompleteメソッドのテスト() {
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setName("サンプルユーザー1");
		userList.add(user1);
		
		User user2 = new User();
		user2.setName("サンプルユーザー2");
		userList.add(user2);
		
		User user3 = new User();
		user3.setName("サンプルユーザー3");
		userList.add(user3);
		
		when(getAllUsersService.getAllUsers()).thenReturn(userList);
		String expected = "\"サンプルユーザー1\",\"サンプルユーザー2\",\"サンプルユーザー3\"";
		
		StringBuilder actual = target.getUserListForAutocomplete();
		assertEquals(actual.toString(), expected);
	}
}
