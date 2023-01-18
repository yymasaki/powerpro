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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import com.example.form.ResetPasswordForm;
import com.example.service.common.GetUserByUpdaterService;
import com.example.service.common.SendFormUrlByEmailService;
import com.example.service.common.SetUuidOnUpdaterInUsersInfoService;
import com.example.service.common.UpdatePasswordService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ResetPasswordController.class)
public class ResetPasswordControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private ResetPasswordController target;

	@MockBean
	private SetUuidOnUpdaterInUsersInfoService setUuidOnUpdaterInUsersInfoService;

	@MockBean
	private SendFormUrlByEmailService sendEmailService;

	@MockBean
	private GetUserByUpdaterService getUserByUpdaterService;

	@MockBean
	private UpdatePasswordService updatePasswordService;

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
	public void メールアドレス入力画面を表示する() throws Exception {
		mockMvc.perform(get("/pass/mail"))
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("common/reset-pass-mail"));
	}

	@Test
	public void パスワード再設定用URLをメールで送る() throws Exception {
		StringBuilder email = new StringBuilder("junit.test");
		StringBuilder domain = new StringBuilder("@example.com");
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass/mail/send")
						.param("email", email.toString()).param("domain", domain.toString())
			.with(csrf());

		// モック戻り値指定
		when(setUuidOnUpdaterInUsersInfoService.setUuidAsUpdater(any(), any())).thenReturn(true);
		
		//パスの有効性確認・リダイレクトの検証
		 MvcResult result = (MvcResult) mockMvc.perform(getRequest)
					.andDo(print())
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/login"))
			.andReturn();
		
		 FlashMap flashMap=result.getFlashMap();
		//リクエストパラメータの値を取得・検証
		String actualEmail = (String) flashMap.get("email");
		StringBuilder expectedEmail = email.append(domain);
		assertEquals(expectedEmail.toString(), actualEmail);

		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("processCompleted");
		String flashMessage="メールを送りました。確認してください";
		assertEquals(flashMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(setUuidOnUpdaterInUsersInfoService, times(1)).setUuidAsUpdater(any(),any());
		verify(sendEmailService, times(1)).sendUrl(any(),any(),any());
	}

	@Test
	public void メールアドレス未入力の場合はパスワード再設定のURLをメールで送信不可() throws Exception {
		String email = "";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass/mail/send")
		.param("email", email)
		.with(csrf());
		
		//パスの有効性確認・リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/reset-pass-mail"))
				.andReturn();
		
		//エラーメッセージの取得・検証
		String expectedMessage = "メールアドレスを入力してください";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれていないことを検証
		verify(setUuidOnUpdaterInUsersInfoService, times(0)).setUuidAsUpdater(any(),any());
		verify(sendEmailService, times(0)).sendUrl(any(),any(),any());
	}
	
	@Test
	public void メールアドレスnullの場合はパスワード再設定のURLをメールで送信不可() throws Exception {
		String email = null;
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass/mail/send")
		.param("email", email)
		.with(csrf());
		
		//パスの有効性確認・リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/reset-pass-mail"))
				.andReturn();
		
		//エラーメッセージの取得・検証
		String expectedMessage = "メールアドレスを入力してください";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれていないことを検証
		verify(setUuidOnUpdaterInUsersInfoService, times(0)).setUuidAsUpdater(any(),any());
		verify(sendEmailService, times(0)).sendUrl(any(),any(),any());
	}
	
	@Test
	public void メールアドレス未登録の場合はパスワード再設定のURLをメールで送信不可() throws Exception {
		StringBuilder email = new StringBuilder("junit.test");
		StringBuilder domain = new StringBuilder("@example.com");
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass/mail/send")
						.param("email", email.toString()).param("domain", domain.toString())
		.with(csrf());
		
		// モック戻り値指定
		when(setUuidOnUpdaterInUsersInfoService.setUuidAsUpdater(any(), any())).thenReturn(false);
		
		//パスの有効性確認・リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/reset-pass-mail"))
				.andReturn();
		
		//エラーメッセージの取得・検証
		String expectedMessage = "このメールアドレスは登録されていません";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
		
		//Mock化されたオブジェクトの呼び出し検証
		verify(setUuidOnUpdaterInUsersInfoService, times(1)).setUuidAsUpdater(any(),any());
		verify(sendEmailService, times(0)).sendUrl(any(),any(),any());
	}
	
	@Test
	public void パスワード再設定画面を表示する()  throws Exception {
		
		String paramId = "SampleUuid";
		Integer userId = 1;
		String name = "テスト太郎";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/pass/reset")
		.param("id", paramId);
		
		// モック戻り値指定
		User user = new User();
		user.setUserId(userId);
		user.setName(name);
		List<User> userList = new ArrayList<>();
		userList.add(user);
		when(getUserByUpdaterService.getUserByUpdaterAndStage(any(),any())).thenReturn(userList);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/reset-pass"))
				.andReturn();
		
		//param検証
		String actualParamId = (String) result.getModelAndView().getModel().get("paramId");
		assertEquals(paramId, actualParamId);
		
		//modelスコープの値取得・検証
		Integer actualUserId = (Integer) result.getModelAndView().getModel().get("userId");
		assertEquals(userId, actualUserId);
		String actualName = (String) result.getModelAndView().getModel().get("name");
		assertEquals(name, actualName);

		//エラー文の取得・検証
		String errorMessage = (String) result.getModelAndView().getModel().get("requestError");
		assertNull(errorMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(getUserByUpdaterService, times(1)).getUserByUpdaterAndStage(any(), any());
	}
	
	@Test
	public void ID有効期限切れの場合はパスワード再設定画面を表示不可()  throws Exception {
		
		String paramId = "SampleUuid";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/pass/reset")
		.param("id", paramId);
		
		// モック戻り値指定
		List<User> userList = new ArrayList<>();
		when(getUserByUpdaterService.getUserByUpdaterAndStage(any(),any())).thenReturn(userList);
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("common/reset-pass"))
				.andReturn();
		
		//エラー文の取得・検証
		String errorMessage = (String) result.getModelAndView().getModel().get("requestError");
		assertNotNull(errorMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(getUserByUpdaterService, times(1)).getUserByUpdaterAndStage(any(), any());
	}
	
	@Test
	public void パスワードを再設定する()  throws Exception {
		
		String password = "passwordTest123";
		String checkPassword = password;
		String userId = "1";
		String name = "テスト太郎";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass/reset/do")
		.param("userId", userId)
		.param("name", name)
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
		 ResetPasswordForm actualParams = (ResetPasswordForm) flashMap.get("resetPasswordForm");
		 assertEquals(userId, actualParams.getUserId());
		 assertEquals(name, actualParams.getName());
		 assertEquals(password, actualParams.getPassword());
		 assertEquals(checkPassword, actualParams.getCheckPassword());
		 
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("processCompleted");
		String flashMessage="パスワードを再設定しました";
		assertEquals(flashMessage, actualMessage);		
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(updatePasswordService, times(1)).update(Integer.parseInt(userId), password, name);
	}
	
	@Test
	public void 不正入力の場合パスワード再設定不可()  throws Exception {
		
		// パスワード形式不正
		String password = "password-test1";
		// 確認用パスワード不一致
		String checkPassword = "password-test2";
		// 空文字
		String userId = "";
		String name = "";
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass/reset/do")
		.param("userId", userId)
		.param("name", name)
		.param("password", password)
		.param("checkPassword", checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("common/reset-pass"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.resetPasswordForm");
		List<FieldError> errorMessage = bindingResult.getFieldErrors("password");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワード形式が不正です");
		errorMessage = bindingResult.getFieldErrors("checkPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "パスワードと確認用パスワードが一致しません");
		errorMessage = bindingResult.getFieldErrors("userId");
		assertNotNull(errorMessage.get(0).getDefaultMessage());
		errorMessage = bindingResult.getFieldErrors("name");
		assertNotNull(errorMessage.get(0).getDefaultMessage());
		
		//完了メッセージの取得・検証
		FlashMap flashMap=result.getFlashMap();
		assertNull( flashMap.get("processCompleted"));		
		
		//Mock化されたオブジェクト呼び出し検証
		verify(updatePasswordService, times(0)).update(any(), any(), any());
	}
}
