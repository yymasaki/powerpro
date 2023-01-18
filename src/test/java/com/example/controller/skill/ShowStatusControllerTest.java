package com.example.controller.skill;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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

import com.example.common.Stage;
import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.user.GetSpecificUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowStatusControllerTest {

	@MockBean
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@MockBean
	private GetPersonalityService getPersonalityService;
	
	@MockBean
	private GetSpecificUserService getSpecificUserService;
	
	@MockBean
	private GetStatusService getStatusService;
	
	@InjectMocks
	private ShowStatusController target;
	
	@Autowired
    private AdminloginUser adminTestUser;
	
	@Autowired
	private SalesLoginUser salesTestUser;
	
	@Autowired
	private WebLoginUser webTestUser;
	
	@Autowired
    protected WebApplicationContext wac;
	
	private MockMvc mockMvc;

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
	public void 管理者でステータス詳細画面を表示する() throws Exception {
		//paramに必要
		Integer userId = 1;
		
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		Department department = new Department();
		department.setName("管理者");
		user.setDepartment(department);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//戻り値を設定
		when(getStatusAndSkillsService.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusTestDataStage0()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestDataStage2()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		//パラメータの設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId));

		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-status"))
				.andExpect(model().attribute("userData", is(user)))
				.andExpect(model().attribute("status", is(statusTestDataStage0())))
				.andExpect(model().attribute("statusStage2Or3", is(statusTestDataStage2())))
				.andExpect(model().attribute("personalityList", is(personalityListTestData())))
				.andReturn();
		
		//サービス確認
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByStages(any(Integer.class), any(Integer.class), any());
		verify(getStatusService, times(1)).getStatusByStages(any(Integer.class), any(Integer.class), any());
		verify(getSpecificUserService, times(1)).get(userId);
	}
	
	@Test
	public void 営業でステータス詳細画面を表示する() throws Exception {
		//paramに必要
		Integer userId = 1;
		
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		Department department = new Department();
		department.setName("営業者");
		user.setDepartment(department);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//戻り値を設定
		when(getStatusAndSkillsService.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusTestDataStage0()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestDataStage2()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		//パラメータの設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId));
		
		mockMvc.perform(getRequest)
		.andExpect(status().isOk())
		.andExpect(view().name("skill/skill-status"))
		.andExpect(model().attribute("userData", is(user)))
		.andExpect(model().attribute("status", is(statusTestDataStage0())))
		.andExpect(model().attribute("statusStage2Or3", is(statusTestDataStage2())))
		.andExpect(model().attribute("personalityList", is(personalityListTestData())))
		.andReturn();
		
		//サービス確認
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByStages(any(Integer.class), any(Integer.class), any());
		verify(getStatusService, times(1)).getStatusByStages(any(Integer.class), any(Integer.class), any());
		verify(getSpecificUserService, times(1)).get(userId);
	}
	
	@Test
	public void ユーザーでステータス詳細画面を表示する() throws Exception {
		//paramに必要
		Integer userId = 1;
		
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		Department department = new Department();
		department.setName("Webエンジニア");
		user.setDepartment(department);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//戻り値を設定
		when(getStatusAndSkillsService.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusTestDataStage0()); 
		when(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey())).thenReturn(statusTestDataStage2()); 
		when(getSpecificUserService.get(userId)).thenReturn(user); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		//パラメータの設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/status")
				.with(user(loginUser))
				.param("userId", String.valueOf(userId));

		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-status"))
				.andExpect(model().attribute("userData", is(user)))
				.andExpect(model().attribute("status", is(statusTestDataStage0())))
				.andExpect(model().attribute("statusStage2Or3", is(statusTestDataStage2())))
				.andExpect(model().attribute("personalityList", is(personalityListTestData())))
				.andReturn();
		
		//サービス確認
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByStages(any(Integer.class), any(Integer.class), any());
		verify(getStatusService, times(1)).getStatusByStages(any(Integer.class), any(Integer.class), any());
	}
	
	public Status statusTestDataStage0() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setStage(0);
		return status;
	}
	
	public Status statusTestDataStage2() {
		Status status = new Status();
		status.setStatusId(2);
		status.setUserId(1);
		status.setStage(2);
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

}
