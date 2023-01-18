package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.controller.common.ShowInfomationListController;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.PasswordExpiredUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordExpirationCheckInterceptorTest {
	

	private MockMvc mockMvc;
	
	@Autowired
	private PasswordExpiredUser passwordExpiredUser;
	
	@Autowired
	private AdminloginUser validUser;
	
    @Autowired
    protected WebApplicationContext wac;
    
	@Autowired
	private ShowInfomationListController showInfomationListController;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(showInfomationListController).build();
		//SpringSecurityを適用させる
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void パスワード有効期限切れの場合はログイン後にパスワード設定画面へ遷移する() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = passwordExpiredUser.getAuthorityList();
		User loginUser = passwordExpiredUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/")
				.with(user(new LoginUser(loginUser,authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/pass-edit?validPass=true"));
	}

	@Test
	public void パスワード有効期限切れではない場合は指定パスへ遷移する() throws Exception {
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = validUser.getAuthorityList();
		User loginUser = validUser.getUser();
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/")
				.with(user(new LoginUser(loginUser,authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(view().name("common/top"));
	}

}
