package com.example.controller.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.user.DeleteUserService;
import com.example.service.user.GetSpecificUserService;
import com.example.service.user.UpdateUserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditAndDeleteUserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private AdminloginUser testUser;

	@Autowired
	protected WebApplicationContext wac;
	
	@InjectMocks
	private EditAndDeleteUserController target;

	@MockBean
	private GetSpecificUserService getSpecificUserService;

	@MockBean
	private UpdateUserInfoService updateUserInfoService;

	@MockBean
	private DeleteUserService deleteUserService;

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
	public void ユーザー編集画面を表示する() throws Exception {
		
		String userId = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/user/edit")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId);
		
		// モック戻り値設定
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("user/user-edit"));
	}
	
	@Test
	public void ユーザー情報を更新する_入社月が一桁の場合() throws Exception {
		String userId = "1";
		String lastName = "テスト";
		String firstName = "太郎";
		String hiredOn = "2020-01-01";
		String departmentId = "1";
		String email = "junit.test";
		String domain = "@rakus.co.jp";
		String version = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/user/edit/do")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId)
				.param("lastName", lastName)
				.param("firstName", firstName)
				.param("hiredOn", hiredOn)
				.param("departmentId", departmentId)
				.param("email", email).param("domain", domain)
				.param("version", version)
				.with(csrf());
		
		// モック戻り値設定
		when(updateUserInfoService.update(any(), any())).thenReturn(true);
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/user/edit?userId="+userId))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String successMessage = (String) flashMap.get("editCompleted");
		assertNotNull(successMessage);	
		String errorMessage =(String) flashMap.get("editFailure");
		assertNull(errorMessage);	
	}
	
	@Test
	public void ユーザー情報を更新する_入社月が二桁の場合() throws Exception {
		String userId = "1";
		String lastName = "テスト";
		String firstName = "太郎";
		String hiredOn = "2020-11-01";
		String departmentId = "1";
		String email = "junit.test";
		String domain = "@rakus.co.jp";
		String version = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/user/edit/do")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId)
				.param("lastName", lastName)
				.param("firstName", firstName)
				.param("hiredOn", hiredOn)
				.param("departmentId", departmentId)
				.param("email", email).param("domain", domain)
				.param("version", version)
				.with(csrf());
		
		// モック戻り値設定
		when(updateUserInfoService.update(any(), any())).thenReturn(true);
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 11, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/user/edit?userId="+userId))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String successMessage = (String) flashMap.get("editCompleted");
		assertNotNull(successMessage);	
		String errorMessage =(String) flashMap.get("editFailure");
		assertNull(errorMessage);	
	}

	@Test
	public void 入力不正の場合はユーザー情報を更新不可() throws Exception {
		String userId = "1";
		String lastName = "";
		String firstName = "";
		String hiredOn = "";
		String departmentId = "";
		String email = "";
		String domain = "";
		String version = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/user/edit/do")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId)
				.param("lastName", lastName)
				.param("firstName", firstName)
				.param("hiredOn", hiredOn)
				.param("departmentId", departmentId)
				.param("email", email)
				.param("domain", domain)
				.param("version", version)
				.with(csrf());
		
		// モック戻り値設定
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("user/user-edit"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editUserForm");
//		assertEquals(object, is(instanceOf(BindingResult.class)));
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> errorMessage = bindingResult.getFieldErrors("lastName");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "苗字を入力してください");
		errorMessage = bindingResult.getFieldErrors("firstName");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "名前を入力してください");
		errorMessage = bindingResult.getFieldErrors("hiredOn");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "入社年月を入力してください");
		errorMessage = bindingResult.getFieldErrors("departmentId");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "所属を選択してください");
		errorMessage = bindingResult.getFieldErrors("email");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "メールアドレスを入力してください");
	}
	
	@Test
	public void 入力不正の場合はユーザー情報を更新不可2() throws Exception {
		String userId = "1";
		//全角スペース
		String lastName = "　";
		//半角スペース
		String firstName = " ";
		String hiredOn = "";
		String departmentId = "";
		String email = "";
		String domain = "";
		String version = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/user/edit/do")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId)
				.param("lastName", lastName)
				.param("firstName", firstName)
				.param("hiredOn", hiredOn)
				.param("departmentId", departmentId)
				.param("email", email)
				.param("domain", domain)
				.param("version", version)
				.with(csrf());
		
		// モック戻り値設定
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("user/user-edit"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editUserForm");
//		assertEquals(object, is(instanceOf(BindingResult.class)));
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> errorMessage = bindingResult.getFieldErrors("lastName");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "苗字を入力してください");
		errorMessage = bindingResult.getFieldErrors("firstName");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "名前を入力してください");
		errorMessage = bindingResult.getFieldErrors("hiredOn");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "入社年月を入力してください");
		errorMessage = bindingResult.getFieldErrors("departmentId");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "所属を選択してください");
		errorMessage = bindingResult.getFieldErrors("email");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "メールアドレスを入力してください");
	}
	
	@Test
	public void versionが変更されていた場合はユーザー情報を更新不可() throws Exception {
		String userId = "1";
		String lastName = "テスト";
		String firstName = "太郎";
		String hiredOn = "2020-01-01";
		String departmentId = "1";
		String email = "junit.test";
		String domain = "@rakus.co.jp";
		String version = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/user/edit/do")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId)
				.param("lastName", lastName)
				.param("firstName", firstName)
				.param("hiredOn", hiredOn)
				.param("departmentId", departmentId)
				.param("email", email)
				.param("domain", domain)
				.param("version", version)
				.with(csrf());
		
		// モック戻り値設定
		when(updateUserInfoService.update(any(), any())).thenReturn(false);
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/user/edit?userId="+userId))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String successMessage = (String) flashMap.get("editCompleted");
		assertNull(successMessage);	
		String errorMessage =(String) flashMap.get("editFailure");
		assertNotNull(errorMessage);	
	}
	
	@Test
	public void ユーザー情報を削除する() throws Exception {
		String userId = "1";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/user/delete")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", userId)
				.with(csrf());
		
		// モック戻り値設定
		User user = new User();
		user.setUserId(1);
		user.setName("テスト　太郎");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setEmail("junit.test@rakus.co.jp");
		user.setVersion(1);
		Department department = new Department();
		department.setName("WEB");
		department.setDepartmentId(1);
		user.setDepartment(department);
		when(getSpecificUserService.get(Integer.valueOf(userId))).thenReturn(user);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/user?pageBack=true"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String successMessage = (String) flashMap.get("deleteCompleted");
		assertNotNull(successMessage);	
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(deleteUserService, times(1)).delete(any(), any());
	}
}
