package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.SearchUserForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.technique.GetTechnicalSkillListService;
import com.example.service.user.GetAllUsersService;
import com.example.service.user.GetUsersService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowUserListControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private AdminloginUser testUser;

	@Autowired
	protected WebApplicationContext wac;

	@InjectMocks
	private ShowUserListController target;

	@MockBean
	private GetTechnicalSkillListService getTechnicalSkillListService;

	@MockBean
	private GetUsersService getUsersService;
	
	@MockBean
	private GetAllUsersService getAllUsersService;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		//SpringSecurityを適用させる
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 全ユーザー一覧を表示する() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user")
		.with(user(new LoginUser(loginUser,authorityList)));
		
		// モック戻り値設定
		List<TechnicalSkill> techList = new ArrayList<>();
		when(getTechnicalSkillListService.getTechnicalSkillList(any())).thenReturn(techList);
		List<User> userList = new ArrayList<>();
		for(int i =0; i<=20; i++) {
			User user = new User();
			user.setUserId(i);
			user.setName("テスト"+i);
			user.setHiredOn(LocalDate.of(2020,1,1));
			user.setEmail("junit.test" + i);
			Department department = new Department();
			department.setName("WEB");
			user.setDepartment(department);
			userList.add(user);
		}
		when(getUsersService.get(any())).thenReturn(userList);
		when(getAllUsersService.getAllUsers()).thenReturn(userList);
		
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("user/user-list"))
				.andReturn();
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any());
		verify(getUsersService, times(0)).get(any());
		verify(getAllUsersService, times(1)).getAllUsers();
		
		//スコープ検証
		assertNotNull( result.getModelAndView().getModel().get("technicalSkills"));
	}

	@Test
	public void 他画面から戻ってきた場合は元の検索条件でユーザー一覧を表示する() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// sessionスコープ内の検索条件を作成
		Integer page = 1;
		SearchUserForm searchValue = new SearchUserForm();
		searchValue.setName("テスト太郎");
		Map<String, Object> sessionAttributes = new HashMap<>();
		sessionAttributes.put("page", page);
		sessionAttributes.put("searchValue", searchValue);
		sessionAttributes.put("pagingNumbers", 10);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user")
		.with(user(new LoginUser(loginUser,authorityList)))
		.sessionAttrs(sessionAttributes)
		.param("pageBack", "true");
		
		// モック戻り値設定
		List<TechnicalSkill> techList = new ArrayList<>();
		when(getTechnicalSkillListService.getTechnicalSkillList(any())).thenReturn(techList);
		List<User> userList = new ArrayList<>();
		for(int i =0; i<=20; i++) {
			User user = new User();
			user.setUserId(i);
			user.setName("テスト"+i);
			user.setHiredOn(LocalDate.of(2020,1,1));
			user.setEmail("junit.test" + i);
			Department department = new Department();
			department.setName("WEB");
			user.setDepartment(department);
			userList.add(user);
		}
		when(getUsersService.get(any())).thenReturn(userList);
		when(getAllUsersService.getAllUsers()).thenReturn(userList);
		
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("user/user-list"))
				.andReturn();
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any());
		verify(getUsersService, times(1)).get(any());
		verify(getAllUsersService, times(0)).getAllUsers();
		
		//スコープ検証
		assertNotNull( result.getModelAndView().getModel().get("technicalSkills"));
	}
	
	@Test
	public void 検索条件なしの場合は全ユーザー一覧を表示する() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		String[] skills = {"null"};
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("skills", skills);
		
		// モック戻り値設定
		List<TechnicalSkill> techList = new ArrayList<>();
		when(getTechnicalSkillListService.getTechnicalSkillList(any())).thenReturn(techList);
		List<User> userList = new ArrayList<>();
		for(int i =0; i<=20; i++) {
			User user = new User();
			user.setUserId(i);
			user.setName("テスト"+i);
			user.setHiredOn(LocalDate.of(2020,1,1));
			user.setEmail("junit.test" + i);
			Department department = new Department();
			department.setName("WEB");
			user.setDepartment(department);
			userList.add(user);
		}
		when(getUsersService.get(any())).thenReturn(userList);
		when(getAllUsersService.getAllUsers()).thenReturn(userList);
		
		
		//パスの有効性確認
		@SuppressWarnings("unused")
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("user/user-list"))
				.andReturn();
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any());
		verify(getUsersService, times(0)).get(any());
		verify(getAllUsersService, times(1)).getAllUsers();
	}
	
	@Test
	public void  検索値に一致したユーザー一覧を表示する() throws Exception {
		String departmentId = "1";
		String name = "テスト太郎";
		String hiredOn = "2020-01-01";
		String[] skills = {"1","2"};
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("page", "1")
		.param("departmentId", departmentId)
		.param("name", name)
		.param("hiredOn", hiredOn)
		.param("skills", skills);
		
		// モック戻り値設定
		List<TechnicalSkill> techList = new ArrayList<>();
		when(getTechnicalSkillListService.getTechnicalSkillList(any())).thenReturn(techList);
		List<User> userList = new ArrayList<>();
		for(int i =0; i<=20; i++) {
			User user = new User();
			user.setUserId(i);
			user.setName("テスト"+i);
			user.setHiredOn(LocalDate.of(2020,1,1));
			user.setEmail("junit.test" + i);
			Department department = new Department();
			department.setName("WEB");
			user.setDepartment(department);
			userList.add(user);
		}
		when(getUsersService.get(any())).thenReturn(userList);
		when(getAllUsersService.getAllUsers()).thenReturn(userList);
		
		
		//パスの有効性確認
		@SuppressWarnings("unused")
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("user/user-list"))
				.andReturn();
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any());
		verify(getUsersService, times(1)).get(any());
		verify(getAllUsersService, times(0)).getAllUsers();
	}
	
	@Test
	public void  検索値に一致したユーザー不在の場合はメッセージを表示する() throws Exception {
		String departmentId = "1";
		String name = "テスト太郎";
		String hiredOn = "2020-01-01";
		String[] skills = {"1","2"};
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("page", "1")
		.param("departmentId", departmentId)
		.param("name", name)
		.param("hiredOn", hiredOn)
		.param("skills", skills);
		
		// モック戻り値設定
		List<TechnicalSkill> techList = new ArrayList<>();
		when(getTechnicalSkillListService.getTechnicalSkillList(any())).thenReturn(techList);
		List<User> userList = new ArrayList<>();
		when(getUsersService.get(any())).thenReturn(userList);
		when(getAllUsersService.getAllUsers()).thenReturn(userList);
		
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
		.andExpect(status().isOk())
		.andExpect(model().hasNoErrors())
		.andExpect(view().name("user/user-list"))
		.andReturn();
		
		//メッセージの取得・検証
		String expectedMessage = "検索条件に一致するエンジニアが存在しません";
		String actualMessage = (String) result.getModelAndView().getModel().get("nonUsers");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any());
		verify(getUsersService, times(1)).get(any());
		verify(getAllUsersService, times(0)).getAllUsers();
	}

}
