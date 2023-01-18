package com.example.controller.skill;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
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
import org.springframework.web.util.NestedServletException;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteStatusControllerTest {
	
	@MockBean
	private GetStatusService getStatusService;
	
	@MockBean
	private DeleteStatusService deleteStatusService;
	
	@MockBean
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@InjectMocks
	private DeleteStatusController target;
	
	@Autowired
    private AdminloginUser adminTestUser;
	
	@Autowired
	private WebLoginUser webTestUser;
	
	@Autowired
	private SalesLoginUser salesTestUser;
	
	private MockMvc mockMvc;
	
	@Autowired
    protected WebApplicationContext wac;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 管理者でステータスの削除を行う() throws Exception {
		Integer userId = 1;
		Integer version = 1;
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		when(getStatusService.getStatusByStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/delete")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("version", String.valueOf(version))
				.param("adminComment", "テストです");

		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andReturn();
	}
	
	@Test
	public void ユーザーがステータスの削除を行う時の403エラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/delete")
				.with(user(loginUser))
				.with(csrf());

		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	public void versionが異なる場合	() throws Exception {
		Integer userId = 1;
		Integer version = 9999;
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		when(getStatusService.getStatusByStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/delete")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("version", String.valueOf(version))
				.param("adminComment", "テストです");

		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("alertMessage", is("削除失敗\\nERROR:この削除内容は既に更新されました")))
				.andReturn();
	}
	
	@Test
	public void ステータスがない状態での削除を行う() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Integer version = 1;
			Integer userId = 1;
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
			User user = adminTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/delete")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId))
					.param("version", String.valueOf(version)).param("adminComment", "テストです");

			try {
				mockMvc.perform(getRequest);
			} catch (NestedServletException e) {
				assertNotNull(e);
				assertNotNull(e.getCause());
				assertTrue(e.getCause() instanceof IllegalArgumentException);
				throw e.getCause();
			}
		});
	}
	
	@Test
	public void ユーザーが削除しようとした場合	() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/delete")
				.with(user(loginUser))
				.with(csrf());

		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden());
	}
	
	public Status statusTestData() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		status.setStage(0);
		status.setVersion(1);
		return status;
	}

}
