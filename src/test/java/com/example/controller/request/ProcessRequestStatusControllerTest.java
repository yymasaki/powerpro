package com.example.controller.request;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;
import java.util.List;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.example.service.status.UpdateStatusService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessRequestStatusControllerTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private UpdateStatusService updateStatusService;
	
	@MockBean
	private GetStatusService getStatusService;
	
	@MockBean
	private DeleteStatusService deleteStatusService;
	
	@MockBean
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@InjectMocks
	private EditRequestStatusController target;
	
	@Autowired
    private AdminloginUser adminTestUser;
	
	@Autowired
	private WebLoginUser webTestUser;
	
	@Autowired
	private SalesLoginUser salesTestUser;
	
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
	public void 管理者がリクエストの承認を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.ACTIVE.getKey();
		Integer version = 1;
		String adminComment = "テスト";
		
		//戻り値の設定
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
				.with(user(loginUser))
				.with(csrf())
				.param("stage", String.valueOf(stage))
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment);
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request"))
				.andExpect(flash().attribute("message", is("ステータス申請を承認しました")))
				.andReturn();
		
		//サービス確認
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(sendMailAboutStatusByAdminService, times(1)).sendMailAboutStatusByAdmin(userId, stage, adminComment, statusId, "申請"+Stage.of(stage).getMessageForRequest());
	}
	
	@Test
	public void 営業が処理しようとしたときの403エラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
				.with(user(loginUser))
				.with(csrf());
		
		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	public void ユーザーが他のユーザーの処理を行う時のエラー() throws Throwable{
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param用データ
			Integer userId = 9999;

			// param,path等の設定
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId));

			try {
				mockMvc.perform(getRequest).andReturn();
			} catch (NestedServletException e) {
				assertNotNull(e);
				assertNotNull(e.getCause());
				assertTrue(e.getCause() instanceof IllegalArgumentException);
				throw e.getCause();
			}
		});
		
	}
	
	@Test
	public void ステージが1の状態で処理を行う時のエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param用データ
			Integer userId = 1;

			// param,path等の設定
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId))
					.param("stage", String.valueOf(Stage.TEMPORARY.getKey()));

			try {
				mockMvc.perform(getRequest).andReturn();
			} catch (NestedServletException e) {
				assertNotNull(e);
				assertNotNull(e.getCause());
				assertTrue(e.getCause() instanceof IllegalArgumentException);
				throw e.getCause();
			}
		});
		
	}
	
	@Test
	public void ステージが2の状態で処理を行う時のエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param用データ
			Integer userId = 1;

			// param,path等の設定
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId))
					.param("stage", String.valueOf(Stage.REQUESTING.getKey()));

			try {
				mockMvc.perform(getRequest).andReturn();
			} catch (NestedServletException e) {
				assertNotNull(e);
				assertNotNull(e.getCause());
				assertTrue(e.getCause() instanceof IllegalArgumentException);
				throw e.getCause();
			}
		});
		
	}
	
	@Test
	public void バージョンが異なる場合で処理を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.ACTIVE.getKey();
		Integer version = 9999;
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("stage", String.valueOf(stage))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andExpect(model().attribute("statusId", is(String.valueOf(statusId))))
				.andExpect(flash().attribute("alertMessage", is("処理失敗\nERROR:この申請内容は既に処理されました")))
				.andReturn();
	}
	
	@Test
	public void ユーザーが申請取り消しを行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.DELETED.getKey();
		Integer version = 1;
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("stage", String.valueOf(stage))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));
		
		mockMvc.perform(getRequest)
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/request"))
		.andExpect(flash().attribute("message", is("ステータス申請を取消しました")))
		.andReturn();
		
		verify(deleteStatusService, times(1)).deleteStatusByPrimaryKey(statusId);
	}
	
	@Test
	public void 管理者が申請の否認を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.SENT_BACK.getKey();
		Integer version = 1;
		String adminComment = "テスト";
		
		//戻り値の設定
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
				.with(user(loginUser))
				.with(csrf())
				.param("stage", String.valueOf(stage))
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment);
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request"))
				.andExpect(flash().attribute("message", "ステータス申請を差し戻ししました"))
				.andReturn();
		
		//サービス確認
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(sendMailAboutStatusByAdminService, times(1)).sendMailAboutStatusByAdmin(userId, stage, adminComment, statusId, "申請"+Stage.of(stage).getMessageForRequest());
	}
	
	@Test
	public void 管理者が申請の否認を行う際の管理者コメントが空白の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.SENT_BACK.getKey();
		Integer version = 1;
		String adminComment = "";
		
		//戻り値の設定
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/process")
				.with(user(loginUser))
				.with(csrf())
				.param("stage", String.valueOf(stage))
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment);
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("forward:/request/status"))
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1字以上200字以内で書いて下さい";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	public Status statusTestData() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setStage(0);
		status.setVersion(1);
		return status;
	}

}
