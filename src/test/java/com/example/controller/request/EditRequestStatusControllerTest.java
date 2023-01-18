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

import java.util.ArrayList;
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
import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateHadBasicSkillService;
import com.example.service.status.UpdateHadEngineerSkillService;
import com.example.service.status.UpdateHadPersonalityService;
import com.example.service.status.UpdateStatusService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditRequestStatusControllerTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@MockBean
	private GetStatusService getStatusService;
	
	@MockBean
	private GetPersonalityService getPersonalityService;
	
	@MockBean
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@MockBean
	private UpdateStatusService updateStatusService;
	
	@MockBean
	private UpdateHadBasicSkillService updateHadBasicSkillService;
	
	@MockBean
	private UpdateHadEngineerSkillService updateHadEngineerSkillService;
	
	@MockBean
	private UpdateHadPersonalityService updateHadPersonalityService; 
	
	@MockBean
	private DeleteStatusService deleteStatusService;
	
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
	public void ユーザーでステータス申請編集画面表示() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		
		Status status = statusTestData();
		status.setStage(Stage.SENT_BACK.getKey());
		
		//戻り値の設定
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));

		//値のチェック
		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-edit-status"))
				.andExpect(model().attribute("statusData", status))		
     			.andExpect(model().attribute("personalityList", personalityListTestData()))		
				.andReturn();
		
		//サービス確認
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByPrimaryKey(statusId);
		verify(getPersonalityService, times(1)).getPersonality();
	}
	
	@Test
	public void 営業がステータス申請編集画面表示しようとした際の403エラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
				.with(user(loginUser))
				.with(csrf());
		
		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	public void ユーザーが他ユーザーのステータス申請編集画面表示() throws Throwable{
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer userId = 9999;
			
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
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
	public void ステータス0の場合でのステータス申請編集画面表示() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
			User user = adminTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer statusId = 1;
			Status status = statusTestData();
			status.setStage(Stage.ACTIVE.getKey());

			when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status);

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
					.with(user(loginUser)).with(csrf()).param("statusId", String.valueOf(statusId));

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
	public void バージョンが異なる場合でのステータス申請編集画面表示() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
				.with(user(loginUser))
				.with(csrf())
				.param("stage", String.valueOf(Stage.SENT_BACK.getKey()))
				.param("statusId", String.valueOf(statusId))
     			.param("version", String.valueOf(9999));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andReturn();
	}
	
	@Test
	public void ユーザーでステータス申請編集を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.REQUESTING.getKey();
		Integer version = 1;
		String userComment = "テストです";
		
		//戻り値の設定
    	when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");

		//値のチェック
		mockMvc.perform(getRequest)
     			.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andExpect(flash().attribute("editCompleted", "ステータス申請を再申請しました"))
				.andExpect(model().attribute("statusId", is(String.valueOf(statusId))))
				.andReturn();
		
		//サービス確認
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, stage, null);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
	}
	
	@Test
	public void 管理者でステータス申請編集を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.ACTIVE.getKey();
		Integer version = 1;
		String adminComment = "テストです";
		
		//戻り値の設定
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		//値のチェック
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request"))
				.andExpect(flash().attribute("message", is("ステータス申請を承認しました")))
				.andReturn();
				
		//サービス確認
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, stage, null);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
		verify(sendMailAboutStatusByAdminService, times(1)).sendMailAboutStatusByAdmin(userId, stage, adminComment, statusId, "申請" + Stage.of(stage).getMessageForRequest());
		verify(deleteStatusService, times(1)).deleteStatusByUserId(userId);
	}
	
	@Test
	public void ユーザーが他ユーザーのステータス申請を行う時のエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param用データ
			Integer userId = 9999;

			// param,path等の設定
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
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
	public void ステージ1になるようにステータス申請編集する際のエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param用データ
			Integer userId = 1;

			// param,path等の設定
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
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
	public void version不一致でステータス申請編集する際のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 9999;
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("version", String.valueOf(version));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andExpect(model().attribute("statusId", is(String.valueOf(statusId))))
				.andExpect(flash().attribute("alertMessage", is("編集失敗\nERROR:この申請編集内容は既に更新されました")))
				.andReturn();
	}
	
	@Test
	public void 管理者が管理者コメント空白の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String adminComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "adminComment"))
				.andExpect(view().name("request/request-edit-status"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1字以上200字以内で書いて下さい";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void ユーザーがユーザーコメント空白の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");;
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "userComment"))
				.andExpect(view().name("request/request-edit-status"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1字以上200字以内で書いて下さい";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void personalityIdListが空の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "personalityIdList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "1つ以上性格を選択して下さい";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void hadBasicSkillListが空の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].hadBasicSkillId", "1");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "1から5の数字を入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有基本スキルのスコアが0の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "0");
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "1から5の数字を入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有基本スキルのスコアが6の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "6");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
				.andExpect(view().name("request/request-edit-status"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から5の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void hadEngineerSkillListが空の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].hadEngineerSkillId", "1")
				.param("hadBasicSkillList[0].score", "3");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "0から100の数字を入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有エンジニアスキルのスコアがーの場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "-1")
				.param("hadBasicSkillList[0].score", "3");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "0から100の数字を入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有エンジニアスキルのスコアが101の場合のエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param用データ
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path等の設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "101")
				.param("hadBasicSkillList[0].score", "3");
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "0から100の数字を入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	public Status statusTestData() {
		Department department = new Department();
		department.setName("テスト");
		User user = new User();
		user.setDepartment(department);
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setStage(2);
		status.setVersion(1);
		status.setUser(user);
		status.setAdminComment("テストです");
		return status;
	}
	
	public List<Personality> personalityListTestData(){
		List<Personality> personalityList = new ArrayList<>();
		Personality personality = new Personality();
		personality.setPersonalityId(1);
		personality.setName("テスト1");
		personalityList.add(personality);
		return personalityList;
	}
	
	public List<String> personalityIdListTestData(){
		List<String> personalityIdList = new ArrayList<>();
		personalityIdList.add("1");
		return personalityIdList;
	}

}
