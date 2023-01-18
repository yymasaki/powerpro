package com.example.controller.common;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.example.form.EditPasswordForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.common.UpdatePasswordService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditPasswordControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private AdminloginUser testUser;
	
    @Autowired
    protected WebApplicationContext wac;

	@InjectMocks
	private EditPasswordController target;

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
		//SpringSecurityを適用させる
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void パスワード変更画面を表示する() throws Exception {
		System.out.println(target);
		System.out.println(mockMvc);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/pass-edit")
				.with(user(new LoginUser(loginUser,authorityList)));
		
		MvcResult result = mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("common/edit-pass"))
			.andReturn();

		assertNull(result.getModelAndView().getModel().get("validPassword"));
	}
	
	@Test
	public void パスワード有効期限切れの場合メッセージ表示する() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/pass-edit?validPass=true")
				.with(user(new LoginUser(loginUser,authorityList)));
		
		
		MvcResult result = mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("common/edit-pass"))
			.andReturn();
		
		String message = (String) result.getModelAndView().getModel().get("validPassword");
		assertEquals("パスワード有効期限(180日間)切れです。更新してください。", message);
	}

	@Test
	public void パスワードを変更する() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();

		// パスワード更新情報
		String currentPassword ="Aaaaa123";
		String newPassword = "newPassword123";
		String checkPassword = newPassword;
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass-edit/do")
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("currentPassword",currentPassword)
			.param("newPassword", newPassword)
			.param("checkPassword",checkPassword)
			.with(csrf());

		//パスの有効性確認・リダイレクトの検証
		 MvcResult result = (MvcResult) mockMvc.perform(getRequest)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/pass-edit"))
			.andExpect(model().hasNoErrors())
			.andReturn();
		 
		 FlashMap flashMap=result.getFlashMap();
		//リクエストパラメータの値を取得・検証
		 EditPasswordForm actualform=(EditPasswordForm)flashMap.get("editPasswordForm");
		 assertEquals(currentPassword, actualform.getCurrentPassword());
		 assertEquals(newPassword, actualform.getNewPassword());
		 assertEquals(checkPassword, actualform.getCheckPassword());
		 
		//完了メッセージの取得・検証
		String actualMessage=(String) flashMap.get("updateCompleted");
		String flashMessage="パスワード変更が完了しました";
		assertEquals(flashMessage, actualMessage);
		
		//Mock化されたオブジェクトが呼ばれたか検証
		verify(updatePasswordService, times(1)).update(loginUser.getUserId(), newPassword, loginUser.getName());
	}

	@Test
	public void 現在のパスワードが誤っている場合はパスワード変更不可() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// パスワード更新情報
		String currentPassword ="incorrectPassword";
		String newPassword = "newPassword123";
		String checkPassword = newPassword;
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass-edit/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("currentPassword",currentPassword)
		.param("newPassword", newPassword)
		.param("checkPassword",checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("common/edit-pass"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editPasswordForm");
		List<FieldError> errorMessage = bindingResult.getFieldErrors("currentPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "現パスワードが誤っています");
		
		//Mock化されたオブジェクトが呼ばれていないことを確認
		verify(updatePasswordService, times(0)).update(loginUser.getUserId(), newPassword, loginUser.getName());
	}
	
	@Test
	public void 新パスワードと確認用パスワードが一致しない場合はパスワード変更不可() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// パスワード更新情報
		String currentPassword ="incorrectPassword";
		String newPassword = "newPassword123";
		String checkPassword = "newPassword123456789";
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass-edit/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("currentPassword",currentPassword)
		.param("newPassword", newPassword)
		.param("checkPassword",checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("common/edit-pass"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editPasswordForm");
		List<FieldError> errorMessage = bindingResult.getFieldErrors("checkPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "新パスワードと確認用パスワードが一致しません");
		
		//Mock化されたオブジェクトが呼ばれていないことを確認
		verify(updatePasswordService, times(0)).update(loginUser.getUserId(), newPassword, loginUser.getName());
	}
	
	@Test
	public void 新パスワードと現在のパスワードが同じ場合はパスワード変更不可() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// パスワード更新情報
		String currentPassword ="currentPassword123";
		String newPassword = currentPassword;
		String checkPassword = currentPassword;
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.post("/pass-edit/do")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("currentPassword",currentPassword)
		.param("newPassword", newPassword)
		.param("checkPassword",checkPassword)
		.with(csrf());
		
		//パスの有効性確認
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("common/edit-pass"))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editPasswordForm");
		List<FieldError> errorMessage = bindingResult.getFieldErrors("newPassword");
		assertEquals(errorMessage.get(0).getDefaultMessage(), "新パスワードが現在のパスワードと同じです");
		
		//Mock化されたオブジェクトが呼ばれていないことを確認
		verify(updatePasswordService, times(0)).update(loginUser.getUserId(), newPassword, loginUser.getName());
	}

	@Test
	public void 非同期通信で現在のパスワードと入力パスワードの整合チェックをする() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// パスワード更新情報
		String password = "Aaaaa123";
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/pass-edit/pass-check-api")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("password",password);
		
		//パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
			    .andExpect(jsonPath("$.passwordError").isEmpty());
	}
	
	@Test
	public void 非同期通信で現在のパスワードと入力パスワードを整合チェックし誤っている場合() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = testUser.getAuthorityList();
		User loginUser = testUser.getUser();
		
		// パスワード更新情報
		String password = "Aaaaa1234567";
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/pass-edit/pass-check-api")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("password",password);
		
		//パスの有効性とエラーメッセージの確認
		 mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.passwordError").value("現在のパスワードが誤っています"));
	}
}
