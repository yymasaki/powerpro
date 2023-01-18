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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.AddUserForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.common.CheckDuplicateEmailsService;
import com.example.service.user.AddNewUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddUserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private AdminloginUser testUser;
	
    @Autowired
    protected WebApplicationContext wac;
    
    @InjectMocks
    private AddUserController target;

	@MockBean
	private CheckDuplicateEmailsService checkDuplicateEmailsService;

	@MockBean
	private AddNewUserService addNewUserService;

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
	public void ユーザー登録画面を表示する() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/user/register")
				.with(user(new LoginUser(loginUser,authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("user/user-register"));
	}
	
	@Test
	public void 新規ユーザーを追加する()  throws Exception {
		
		String lastName = "テスト";
		String firstName = "太郎";
		String hiredOn = "2020-01-01";
		String departmentId = "1";
		String email = "junit.test";
		String domain = "@rakus.co.jp";
		String password = "passwordTest123";
		String checkPassword = "passwordTest123";

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/user/register/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("domain", domain)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		// モック戻り値設定
		when(checkDuplicateEmailsService.checkDuplication(email+domain)).thenReturn(false);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/user/register"))
				.andReturn();
		
		 FlashMap flashMap=result.getFlashMap();
		//リクエストパラメータの値を取得・検証
		 AddUserForm actualParams = (AddUserForm) flashMap.get("addUserFormForJUnitTest");
		 assertEquals(lastName, actualParams.getLastName());
		 assertEquals(firstName, actualParams.getFirstName());
		 assertEquals(hiredOn, actualParams.getHiredOn());
		 assertEquals(departmentId, actualParams.getDepartmentId());
		 assertEquals(email, actualParams.getEmail());
		 assertEquals(password, actualParams.getPassword());
		 assertEquals(checkPassword, actualParams.getCheckPassword());
		 
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("additionCompleted");
		String flashMessage="ユーザーの登録が完了しました";
		assertEquals(flashMessage, actualMessage);		
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(checkDuplicateEmailsService, times(1)).checkDuplication(any());
		verify(addNewUserService, times(1)).add(any(),any());
	}
	
	@Test
	public void 不正入力の場合は新規ユーザーを追加不可1()  throws Exception {
		
		//未入力欄あり
		String lastName = "";
		String firstName = "";
		String hiredOn = "";
		String departmentId = "";
		String email = "";
		String domain = "";
		//パスワード形式不正
		String password = "invelid-password1";
		//確認用パスワード不一致
		String checkPassword = "invalid-password2";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/user/register/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("dmain", domain)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("user/user-register"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.addUserForm");
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
		assertNotNull(errorMessage.get(0).getDefaultMessage());
		errorMessage = bindingResult.getFieldErrors("domain");
		assertNotNull(errorMessage.get(0).getDefaultMessage());
		errorMessage = bindingResult.getFieldErrors("password");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワード形式が不正です");
		errorMessage = bindingResult.getFieldErrors("checkPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワードと確認用パスワードが一致しません");
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("additionCompleted");
		assertNull(actualMessage);		
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(checkDuplicateEmailsService, times(0)).checkDuplication(any());
		verify(addNewUserService, times(0)).add(any(),any());
	}
	
	@Test
	public void 不正入力の場合は新規ユーザーを追加不可2()  throws Exception {
		
		//半角スペース
		String lastName = "   ";
		//全角スペース
		String firstName = "　　　";
		//タブ
		String hiredOn = "	";
		String departmentId = "";
		String email = null;
		String domain = null;
		//パスワード形式不正
		String password = "invelid-password1";
		//確認用パスワード不一致
		String checkPassword = "invalid-password2";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/user/register/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("domain", domain)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("user/user-register"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.addUserForm");
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
		errorMessage = bindingResult.getFieldErrors("domain");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "ドメインを選択してください");
		errorMessage = bindingResult.getFieldErrors("password");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワード形式が不正です");
		errorMessage = bindingResult.getFieldErrors("checkPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワードと確認用パスワードが一致しません");
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("additionCompleted");
		assertNull(actualMessage);		
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(checkDuplicateEmailsService, times(0)).checkDuplication(any());
		verify(addNewUserService, times(0)).add(any(),any());
	}
	
	@Test
	public void メールアドレス重複の場合は新規ユーザーを追加不可()  throws Exception {
		
		String lastName = "テスト";
		String firstName = "太郎";
		String hiredOn = "2020-01-01";
		String departmentId = "1";
		String email = "junit.test";
		String domain = "@rakus.co.jp";
		String password = "passwordTest123";
		String checkPassword = "passwordTest123";
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/user/register/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("domain", domain)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		// モック戻り値設定
		when(checkDuplicateEmailsService.checkDuplication(email+domain)).thenReturn(true);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("user/user-register"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.addUserForm");
		BindingResult bindingResult = (BindingResult) object;
		assertEquals(0, bindingResult.getFieldErrors("lastName").size());
		assertEquals(0,  bindingResult.getFieldErrors("firstName").size());
		assertEquals(0, bindingResult.getFieldErrors("hiredOn").size());
		assertEquals(0,  bindingResult.getFieldErrors("departmentId").size());
		List<FieldError> errorMessage = bindingResult.getFieldErrors("email");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "このメールアドレスは登録済です");
		assertEquals(0,  bindingResult.getFieldErrors("password").size());
		assertEquals(0, bindingResult.getFieldErrors("checkPassword").size());
		
		FlashMap flashMap=result.getFlashMap();
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("additionCompleted");
		assertNull(actualMessage);		
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(checkDuplicateEmailsService, times(1)).checkDuplication(any());
		verify(addNewUserService, times(0)).add(any(),any());
	}
	
	@Test
	public void 非同期通信でメールアドレス重複チェック_重複の場合エラーメッセージを表示() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// 登録用メールアドレス
		String email = "junit.test";
		
		// モック戻り値設定
		when(checkDuplicateEmailsService.checkDuplication(email)).thenReturn(true);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user/email-check-api")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("email",email);
		
		//パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
			    .andExpect(jsonPath("$.duplicateEmail").value("このメールアドレスは既に登録されています"));
	}
	
	@Test
	public void 非同期通信でメールアドレス重複チェック_重複の場合エラーメッセージなし() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// 登録用メールアドレス
		String email = "junit.test";
		
		// モック戻り値設定
		when(checkDuplicateEmailsService.checkDuplication(email)).thenReturn(false);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/user/email-check-api")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("email",email);
		
		//パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.duplicateEmail").isEmpty());
	}


}
