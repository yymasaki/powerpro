package com.example.controller.skill;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import java.time.LocalDateTime;
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
import com.example.domain.BasicSkill;
import com.example.domain.EngineerSkill;
import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.AddHadBasicSkillService;
import com.example.service.status.AddHadEngineerSkillService;
import com.example.service.status.AddHadPersonalityService;
import com.example.service.status.AddStatusService;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetBasicSkillListService;
import com.example.service.status.GetEngineerSkillListService;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateHadBasicSkillService;
import com.example.service.status.UpdateHadEngineerSkillService;
import com.example.service.status.UpdateHadPersonalityService;
import com.example.service.status.UpdateStatusService;
import com.example.service.user.GetSpecificUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditStatusControllerTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@MockBean
	private GetStatusService getStatusService;
	
	@MockBean
	private GetBasicSkillListService getBasicSkillService;
	
	@MockBean
	private GetEngineerSkillListService getEngineerSkillService;
	
	@MockBean
	private GetPersonalityService getPersonalityService;
	
	@MockBean
	private AddStatusService addStatusService;
	
	@MockBean
	private AddHadBasicSkillService addHadBasicSkillService;
	
	@MockBean
	private AddHadEngineerSkillService addHadEngineerSkillService;

	@MockBean
	private AddHadPersonalityService addHadPersonalityService;
	
	@MockBean
	private GetSpecificUserService getSpecificUserService;
	
	@MockBean
	private UpdateStatusService updateStatusService;
	
	@MockBean
	private UpdateHadBasicSkillService updateHadBasicSkillService;
	
	@MockBean
	private UpdateHadEngineerSkillService updateHadEngineerSkillService;
	
	@MockBean
	private UpdateHadPersonalityService updateHadPersonalityService;
	
	@MockBean
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@MockBean
	private DeleteStatusService deleteStatusService;
	
	@InjectMocks
	private EditStatusController target;
	
	@Autowired
    private AdminloginUser adminTestUser;
	
	@Autowired
	private SalesLoginUser salesTestUser;
	
	@Autowired
	private WebLoginUser webTestUser;
	
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
	public void ステータスに何もない状態での画面表示() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(1);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 1;
		
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(null); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status/edit")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId));

		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-edit-status"))
				.andExpect(model().attribute("userData", is(user)))
				.andExpect(model().attribute("basicSkillList", is(basicSkillListTestData())))
				.andExpect(model().attribute("engineerSkillList", is(engineerSkillListTestData())))
				.andExpect(model().attribute("personalityList",is(personalityListTestData())))				
				.andReturn();
		
		verify(getBasicSkillService, times(1)).getBasicSkillList(userDepartmentId);
		verify(getEngineerSkillService, times(1)).getEngineerSkillList(userDepartmentId);
		verify(getPersonalityService, times(1)).getPersonality();
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey());		
	}
	
	@Test
	public void 営業者がページ編集画面を表示する場合403エラー() throws Throwable {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		user.setDepartmentId(1);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status/edit")
				.with(user(loginUser));

		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void 申請中か否認されたステータスが存在する場合編集画面を表示させたときのエラー表示() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);
			Integer userId = 1;

			when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey()))
					.thenReturn(statusTestData());

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status/edit")
					.with(user(loginUser)).param("userId", String.valueOf(userId));

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
	public void 管理者ログインでステータスのversion一致() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 4;
		Integer statusId =1;
		Integer version = 1;
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(null); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status/edit")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));

		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-edit-status"))
				.andExpect(model().attribute("userData", is(user)))
				.andExpect(model().attribute("basicSkillList", is(basicSkillListTestData())))
				.andExpect(model().attribute("engineerSkillList", is(engineerSkillListTestData())))
				.andExpect(model().attribute("personalityList", is(personalityListTestData())))				
				.andReturn();
		
		verify(getSpecificUserService, times(1)).get(userId);
		verify(getBasicSkillService, times(1)).getBasicSkillList(userDepartmentId);
		verify(getEngineerSkillService, times(1)).getEngineerSkillList(userDepartmentId);
		verify(getPersonalityService, times(1)).getPersonality();
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey());	
	}
	
	@Test
	public void 一時保存データが存在して作成してから24時間以内の場合の編集画面表示() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 4;
		Integer statusId =1;
		Integer version = 1;
		
		Status statusTempoNewData = statusTempoNewData();
		
		when(getStatusAndSkillsService.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey())).thenReturn(statusTempoNewData); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null)).thenReturn(statusTempoNewData); 
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(null); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status/edit")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));

		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-edit-status"))
				.andExpect(model().attribute("userData", is(user)))
				.andExpect(model().attribute("basicSkillList", is(basicSkillListTestData())))
				.andExpect(model().attribute("engineerSkillList", is(engineerSkillListTestData())))
				.andExpect(model().attribute("personalityList", is(personalityListTestData())))				
				.andReturn();
		
		verify(getSpecificUserService, times(1)).get(userId);
		verify(getBasicSkillService, times(1)).getBasicSkillList(userDepartmentId);
		verify(getEngineerSkillService, times(1)).getEngineerSkillList(userDepartmentId);
		verify(getPersonalityService, times(1)).getPersonality();
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey());	
	}
	
	@Test
	public void 一時保存データが存在して作成してから24時間以降の場合の編集画面表示() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 4;
		Integer statusId =1;
		Integer version = 1;
		
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null)).thenReturn(statusTempoOldData()); 
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(null); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status/edit")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-edit-status"))
				.andExpect(model().attribute("alertMessage", is("一時保存したデータが1日経過したため削除されました")))
				.andExpect(model().attribute("userData", user))
				.andExpect(model().attribute("basicSkillList", basicSkillListTestData()))
				.andExpect(model().attribute("engineerSkillList", engineerSkillListTestData()))
				.andExpect(model().attribute("personalityList", personalityListTestData()))				
				.andReturn();
		
		verify(getSpecificUserService, times(1)).get(userId);
		verify(getBasicSkillService, times(1)).getBasicSkillList(userDepartmentId);
		verify(getEngineerSkillService, times(1)).getEngineerSkillList(userDepartmentId);
		verify(getPersonalityService, times(1)).getPersonality();
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey());	
		verify(deleteStatusService, times(1)).deleteStatusByPrimaryKey(statusId);
	}
	
	@Test
	public void ユーザーがステータスの編集() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer statusId =1;
		Integer version = 1;
		
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("userComment", "こんにちは")
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("editCompleted", is("ステータスを申請しました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
		verify(addStatusService, times(1)).addStatus(any(), any());;
		verify(addHadBasicSkillService, times(1)).addHadBasicSkill(any(), any());
		verify(addHadEngineerSkillService, times(1)).addHadEngineerSkill(any(), any());
		verify(addHadPersonalityService, times(1)).addHadPersonality(any(), any());
	}
	
	@Test
	public void ユーザーが一時保存データある状態でのステータスの編集() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer statusId =1;
		Integer version = 1;
		Integer stage = Stage.ACTIVE.getKey();
		
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("userComment", "こんにちは")
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("editCompleted", is("ステータスを更新しました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
	}
	
	@Test
	public void ユーザーが一時保存を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 1;
		Integer version = 1;
		Integer stage = Stage.TEMPORARY.getKey();
		
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTempoNewData()); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null)).thenReturn(statusTempoNewData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(null); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("userComment", "こんにちは")
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("editCompleted", is("ステータスを一時保存しました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
	}
	
	@Test
	public void 管理者がステータスの編集() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 1;
		Integer statusId =1;
		Integer version = 1;
		Integer stage = Stage.ACTIVE.getKey();
		
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("adminComment", "こんにちは")
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("editCompleted", is("ステータスを更新しました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, stage, null);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
	}
	
	@Test
	public void DB内のステージ0がない状態で管理者がステータスの編集() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 1;
		Integer statusId =1;
		Integer version = 1;
		Integer stage = Stage.ACTIVE.getKey();
		
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(null).thenReturn(statusTestData());
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("adminComment", "こんにちは")
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/skill/status?userId=1"))
		.andExpect(model().attribute("userId", is(String.valueOf(userId))))
		.andExpect(flash().attribute("editCompleted", is("ステータスを更新しました")))
		.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(2)).getStatusByStages(userId, stage, null);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(addStatusService, times(1)).addStatus(any(), any());
		verify(addHadBasicSkillService, times(1)).addHadBasicSkill(any(), any());
		verify(addHadEngineerSkillService, times(1)).addHadEngineerSkill(any(), any());
		verify(addHadPersonalityService, times(1)).addHadPersonality(any(), any());
	}
	
	@Test
	public void 管理者が一時保存データある状態でのステータスの編集() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer userId = 1;
		Integer userDepartmentId = 1;
		Integer statusId =1;
		Integer version = 1;
		Integer stage = Stage.ACTIVE.getKey();
		
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getBasicSkillService.getBasicSkillList(userDepartmentId)).thenReturn(basicSkillListTestData()); 
		when(getEngineerSkillService.getEngineerSkillList(userDepartmentId)).thenReturn(engineerSkillListTestData()); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(null); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("adminComment", "こんにちは")
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("editCompleted", is("ステータスを更新しました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusService, times(1)).getStatusByStages(userId, stage, null);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
	}
	
	@Test
	public void ユーザーが他ユーザーの編集を実行するときのエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			user.setDepartmentId(4);
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer userId = 9999;

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
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
	public void ステージの値が3のエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			user.setDepartmentId(4);
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer userId = 1;

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId))
					.param("stage", String.valueOf(Stage.SENT_BACK.getKey()));

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
	public void ステージの値が9のエラー() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ログイン認証用
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			user.setDepartmentId(4);
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer userId = 1;

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId))
					.param("stage", String.valueOf(Stage.DELETED.getKey()));

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
	public void versionの不一致() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 9999;
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("alertMessage", is("編集失敗\nERROR:この編集内容は既に更新されました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
	}
	
	@Test
	public void 管理者コメントが空欄でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String adminComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment)
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "adminComment"))
				.andExpect(view().name("skill/skill-edit-status"))
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
	public void ユーザーコメントが空欄でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "userComment"))
				.andExpect(view().name("skill/skill-edit-status"))
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
	public void ユーザーコメントが空欄で一時保存を行う() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.TEMPORARY.getKey()))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/skill/status?userId=1"))
				.andExpect(model().attribute("userId", is(String.valueOf(userId))))
				.andExpect(flash().attribute("editCompleted", is("ステータスを一時保存しました")))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(addStatusService, times(1)).addStatus(any(), any());;
		verify(addHadBasicSkillService, times(1)).addHadBasicSkill(any(), any());
		verify(addHadEngineerSkillService, times(1)).addHadEngineerSkill(any(), any());
		verify(addHadPersonalityService, times(1)).addHadPersonality(any(), any());
	}
	
	@Test
	public void 所有基本スキルのスコアが空白でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].hadBasicSkillId", "1");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から5の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有基本スキルのスコアが0でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "0");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から5の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有基本スキルのスコアが6でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "6");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から5の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有エンジニアスキルのスコアが空白でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].hadEngineerSkillId", "1")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "0から100の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void 所有エンジニアスキルのスコアがーでのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "-1")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "0から100の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	@Test
	public void 所有エンジニアスキルのスコアが101でのエラー() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		user.setDepartmentId(4);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Integer userId = 1;
		Integer version = 1;
		String userComment = "テスト";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey())).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "101")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
				.andReturn();
		
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "0から100の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	public Status statusTestData() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		status.setStage(0);
		status.setVersion(1);
		status.setUpdatedAt(LocalDateTime.now());
		return status;
	}
	
	public Status statusTempoOldData() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		status.setStage(1);
		status.setVersion(1);
		status.setUpdatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
		return status;
	}
	
	public Status statusTempoNewData() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		status.setStage(1);
		status.setVersion(1);
		status.setUpdatedAt(LocalDateTime.now());
		return status;
	}
	
	public List<BasicSkill> basicSkillListTestData() {
		List<BasicSkill> basicSkillList = new ArrayList<>();
		BasicSkill basicSkill = new BasicSkill();
		basicSkill.setBasicSkillId(1);
		basicSkill.setName("サンプル1");
		basicSkill.setDepartmentId(1);
		basicSkillList.add(basicSkill);
		return basicSkillList;
	}
	
	public List<EngineerSkill> engineerSkillListTestData() {
		List<EngineerSkill> engineerSkillList = new ArrayList<>();
		EngineerSkill engineerSkill = new EngineerSkill();
		engineerSkill.setEngineerSkillId(1);
		engineerSkill.setName("テスト1");
		engineerSkill.setDepartmentId(1);
		engineerSkillList.add(engineerSkill);
		return engineerSkillList;
	}
	
	public List<Personality> personalityListTestData(){
		List<Personality> personalityList = new ArrayList<>();
		Personality personality1 = new Personality();
		personality1.setPersonalityId(1);
		personality1.setName("テスト1");
		personalityList.add(personality1);
		return personalityList;
	}
	
	public List<String> personalityIdListTestData(){
		List<String> personalityIdList = new ArrayList<>();
		personalityIdList.add("1");
		return personalityIdList;
	}
}
