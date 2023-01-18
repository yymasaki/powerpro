package com.example.controller.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.FlashMap;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.common.AddTemporaryUserService;
import com.example.service.common.CheckDuplicateEmailsService;
import com.example.service.common.DefinitiveUserResistrationService;
import com.example.service.common.GetUserByUpdaterService;
import com.example.service.common.SendFormUrlByEmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RegisterUserBySelfController.class)
public class RegisterUserBySelfControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private RegisterUserBySelfController target;

	@MockBean
	private AddTemporaryUserService addTemporaryUserService;

	@MockBean
	private CheckDuplicateEmailsService checkEmailService;

	@MockBean
	private SendFormUrlByEmailService sendEmailService;

	@MockBean
	private GetUserByUpdaterService getUserByUpdaterService;

	@MockBean
	private DefinitiveUserResistrationService definitiveUserResistrationService;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 会員登録用メールアドレス入力画面を表示する()  throws Exception {
		mockMvc.perform(get("/register/mail"))
		.andExpect(status().isOk())
		.andExpect(model().hasNoErrors())
		.andExpect(view().name("common/register-mail"));
	}

	@Test
	public void 会員登録画面のURLをメールで送信する()  throws Exception {

		String email = "junit.test";
		String domain = "@rakus.co.jp";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/mail/send")
			.param("email", email)
			.param("domain", domain)
			.with(csrf());

		// モック戻り値指定
		when(checkEmailService.checkDuplication(email+domain)).thenReturn(false);
		
		//パスの有効性確認・リダイレクトの検証
		 MvcResult result = (MvcResult) mockMvc.perform(getRequest)
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/login"))
			.andReturn();
		
		 FlashMap flashMap=result.getFlashMap();
		//リクエストパラメータの値を取得・検証
		 String actualEmail=(String)flashMap.get("emailAddress");
		 assertEquals(email+domain, actualEmail);
		 
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("processCompleted");
		String flashMessage="メールを送りました。確認してください";
		assertEquals(flashMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(checkEmailService, times(1)).checkDuplication(email+domain);
		verify(addTemporaryUserService, times(1)).insert(any(), any());
		verify(sendEmailService, times(1)).sendUrl(any(),any(),any());
	}

	@Test
	public void メールアドレス未入力の場合は会員登録画面のURLをメールで送信不可()  throws Exception {
		
		String email = "";
		String domain = "@rakus.co.jp";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/mail/send")
		.param("email", email)
		.param("domain", domain)
		.with(csrf());
		
		// モック戻り値指定
		when(checkEmailService.checkDuplication(email+domain)).thenReturn(false);
		
		//パスの有効性確認・リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/register-mail"))
				.andReturn();
		
		//メッセージの取得・検証
		String expectedMessage = "メールアドレスを入力してください";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(checkEmailService, times(0)).checkDuplication(email+domain);
		verify(addTemporaryUserService, times(0)).insert(any(), any());
		verify(sendEmailService, times(0)).sendUrl(any(),any(),any());
	}
	
	@Test
	public void メールアドレスnullの場合は会員登録画面のURLをメールで送信不可()  throws Exception {
		
		String email = null;
		String domain = "@rakus.co.jp";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/mail/send")
		.param("email", email)
		.param("domain", domain)
		.with(csrf());
		
		// モック戻り値指定
		when(checkEmailService.checkDuplication(email+domain)).thenReturn(false);
		
		//パスの有効性確認・リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/register-mail"))
				.andReturn();
		
		//メッセージの取得・検証
		String expectedMessage = "メールアドレスを入力してください";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(checkEmailService, times(0)).checkDuplication(email+domain);
		verify(addTemporaryUserService, times(0)).insert(any(), any());
		verify(sendEmailService, times(0)).sendUrl(any(),any(),any());
	}

	@Test
	public void メールアドレス登録済の場合は会員登録画面のURLをメールで送信不可()  throws Exception {
		
		String email = "junit.test";
		String domain = "@rakus.co.jp";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/mail/send")
		.param("email", email)
		.param("domain", domain)
		.with(csrf());
		
		// モック戻り値指定
		when(checkEmailService.checkDuplication(email+domain)).thenReturn(true);
		
		//パスの有効性確認・リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/register-mail"))
				.andReturn();
		
		//メッセージの取得・検証
		String expectedMessage = "このメールアドレスは登録済です";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(checkEmailService, times(1)).checkDuplication(email+domain);
		verify(addTemporaryUserService, times(0)).insert(any(), any());
		verify(sendEmailService, times(0)).sendUrl(any(),any(),any());
	}

	@Test
	public void 会員登録画面を表示する()  throws Exception {
		
		String id = "SampleUuid";
		String email = "junit.test@rakus.co.jp";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/register")
		.param("id", id);
		
		// モック戻り値指定
		User user = new User();
		user.setEmail(email);
		List<User> userList = new ArrayList<>();
		userList.add(user);
		when(getUserByUpdaterService.getUserByUpdaterAndStage(any(),any())).thenReturn(userList);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/register"))
				.andReturn();
		
		//メールアドレスの取得・検証
		String actualEmail = (String) result.getModelAndView().getModel().get("email");
		assertEquals(email, actualEmail);

		//エラー文の取得・検証
		String errorMessage = (String) result.getModelAndView().getModel().get("requestError");
		assertNull(errorMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(getUserByUpdaterService, times(1)).getUserByUpdaterAndStage(any(), any());
	}

	@Test
	public void ID有効期限切れの場合は会員登録画面を表示不可()  throws Exception {
		
		String id = "SampleUuid";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/register")
		.param("id", id);
		
		// モック戻り値指定（ID無効の場合は空のリストが返ってくる）
		List<User> userList = new ArrayList<>();
		when(getUserByUpdaterService.getUserByUpdaterAndStage(any(),any())).thenReturn(userList);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/register"))
				.andReturn();
		
		//メールアドレスの取得・検証
		String actualEmail = (String) result.getModelAndView().getModel().get("email");
		assertNull(actualEmail);

		//エラー文の取得・検証
		String errorMessage = (String) result.getModelAndView().getModel().get("requestError");
		assertNotNull(errorMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(getUserByUpdaterService, times(1)).getUserByUpdaterAndStage(any(), any());
	}

	@Test
	public void 会員情報を本登録する()  throws Exception {
		
		String lastName = "テスト";
		String firstName = "太郎";
		String hiredOn = "2020-01-01";
		String departmentId = "1";
		String email = "junit.test@rakus.co.jp";
		String password = "passwordTest123";
		String checkPassword = "passwordTest123";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/do")
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/login"))
				.andReturn();
		
		 FlashMap flashMap=result.getFlashMap();
		//リクエストパラメータの値を取得・検証
		 RegisterUserForm actualParams = (RegisterUserForm) flashMap.get("registerUserForm");
		 assertEquals(lastName, actualParams.getLastName());
		 assertEquals(firstName, actualParams.getFirstName());
		 assertEquals(hiredOn, actualParams.getHiredOn());
		 assertEquals(departmentId, actualParams.getDepartmentId());
		 assertEquals(email, actualParams.getEmail());
		 assertEquals(password, actualParams.getPassword());
		 assertEquals(checkPassword, actualParams.getCheckPassword());
		 
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("processCompleted");
		String flashMessage="会員登録が完了しました";
		assertEquals(flashMessage, actualMessage);		
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(definitiveUserResistrationService, times(1)).definitiveResistration(any());
	}

	@Test
	public void 入力不正の場合は本会員登録不可()  throws Exception {
		//未入力欄あり
		String lastName = "";
		String firstName = "";
		String hiredOn = "";
		String departmentId = "";
		String email = "";
		//パスワード形式不正
		String password = "invelid-password1";
		//確認用パスワード不一致
		String checkPassword = "invalid-password2";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/do")
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("common/register"))
				.andReturn();
		
		//モデルスコープに格納するメールアドレスの取得・検証
		String actualEmail = (String) result.getModelAndView().getModel().get("email");
		assertNotNull(actualEmail);
		
		//エラーメッセージが格納されているか検証
		BindingResult bindingResult = (BindingResult)result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerUserForm");
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
		errorMessage = bindingResult.getFieldErrors("password");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワード形式が不正です");
		errorMessage = bindingResult.getFieldErrors("checkPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワードと確認用パスワードが一致しません");
		
		//Mock化されたオブジェクトが呼ばれていないことを検証
		verify(definitiveUserResistrationService, times(0)).definitiveResistration(any());
	}

	@Test
	public void スペースのみ入力の場合は本会員登録不可()  throws Exception {
		
		// 全角スペース
		String lastName = "　";
		// 半角スペース
		String firstName = " ";
		// タブ
		String hiredOn = "	";
		String departmentId = "";
		String email = "";
		String password = "invelid-password1";
		String checkPassword = "invalid-password2";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/register/do")
		.param("lastName", lastName)
		.param("firstName", firstName)
		.param("hiredOn", hiredOn)
		.param("departmentId", departmentId)
		.param("email", email)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("common/register"))
				.andReturn();
		
		//モデルスコープに格納するメールアドレスの取得・検証
		String actualEmail = (String) result.getModelAndView().getModel().get("email");
		assertNotNull(actualEmail);
		
		//エラーメッセージが格納されているか検証
		BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerUserForm");
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
		errorMessage = bindingResult.getFieldErrors("password");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワード形式が不正です");
		errorMessage = bindingResult.getFieldErrors("checkPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワードと確認用パスワードが一致しません");
		
		//Mock化されたオブジェクトが呼ばれていないことを検証
		verify(definitiveUserResistrationService, times(0)).definitiveResistration(any());
	}
}
