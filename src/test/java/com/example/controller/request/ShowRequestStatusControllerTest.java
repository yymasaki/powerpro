package com.example.controller.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowRequestStatusControllerTest {

	@MockBean
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@MockBean
	private GetPersonalityService getPersonalityService;
	
	@InjectMocks
	private ShowRequestStatusController target;
	
	@Autowired
    private AdminloginUser adminTestUser;
	
	@Autowired
	private WebLoginUser webTestUser;
	
	@Autowired
	private SalesLoginUser salesTestUser;
	
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
	public void ユーザーでステータス詳細画面を表示する() throws Exception {
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		Department department = new Department();
		department.setName("テスト");
		user.setDepartment(department);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		Status status = statusTestData();
		List<Personality> personalityList = personalityListTestData();
		
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityList); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/status")
				.with(user(loginUser))
				.param("statusId", String.valueOf(statusId));

		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-status"))
				.andReturn();
		
		//Model内の情報の取得
		Status actualStatus = (Status) result.getModelAndView().getModel()
				.get("statusData");
		String actualCurrentStage = (String) result.getModelAndView().getModel()
				.get("currentStage");
		@SuppressWarnings({"unchecked" })
		List<Personality> actualPersonalityList = (List<Personality>) result.getModelAndView().getModel()
				.get("personalityList");

		//model確認
		assertEquals(actualStatus, statusTestData());
		assertEquals(actualPersonalityList, personalityList);
		assertEquals(actualCurrentStage, "承認済");
		
		//サービス確認
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByPrimaryKey(statusId);
		verify(getPersonalityService, times(1)).getPersonality();
	}
	
	@Test
	public void 営業でステータス詳細画面を表示する() throws Exception {
		Integer statusId = 1;
		Status status = statusTestData();
		List<Personality> personalityList = personalityListTestData();
		
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityList); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/status")
				.with(user(loginUser));

		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	public void 管理者でステータス詳細画面を表示する() throws Exception {
		Integer statusId = 1;
		Status status = statusTestData();
		List<Personality> personalityList = personalityListTestData();
		
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		Department department = new Department();
		department.setName("テスト");
		user.setDepartment(department);
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityList); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/status")
				.with(user(loginUser))
				.param("statusId", String.valueOf(statusId));

		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-status"))
				.andReturn();
		
		//Model内の情報の取得
		Status actualStatus = (Status) result.getModelAndView().getModel()
				.get("statusData");
		String actualCurrentStage = (String) result.getModelAndView().getModel()
				.get("currentStage");
		@SuppressWarnings({"unchecked" })
		List<Personality> actualPersonalityList = (List<Personality>) result.getModelAndView().getModel()
				.get("personalityList");

		//model確認
		assertEquals(actualStatus, statusTestData());
		assertEquals(actualPersonalityList, personalityList);
		assertEquals(actualCurrentStage, "承認済");
		
		//サービス確認
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByPrimaryKey(statusId);
		verify(getPersonalityService, times(1)).getPersonality();
	}
	
	@Test
	public void ユーザーが他のユーザーのステータス申請画面を表示させたときのエラー表示() throws Exception {
		Integer statusId = 1;
		
		//ログイン認証用
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusFailedTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/status")
				.with(user(loginUser))
				.param("statusId", String.valueOf(statusId));
		
		try{
			mockMvc.perform(getRequest).andReturn();
	    }catch ( NestedServletException e ){
	        assertNotNull( e );
	        assertNotNull( e.getCause() );
	        assertTrue( e.getCause() instanceof IllegalArgumentException );
	    }
	}
	
	public Status statusTestData() {
		User user = new User();
		Department department = new Department();
		department.setName("テスト");
		user.setDepartment(department);
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		status.setStage(0);
		status.setUser(user);
		return status;
	}
	
	public Status statusFailedTestData() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(5000);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		status.setStage(0);
		return status;
	}
	
	public List<Personality> personalityListTestData(){
		List<Personality> personalityList = new ArrayList<>();
		Personality personality1 = new Personality();
		personality1.setPersonalityId(1);
		personality1.setName("テスト1");
		personalityList.add(personality1);
		
		Personality personality2 = new Personality();
		personality2.setPersonalityId(2);
		personality2.setName("テスト2");
		personalityList.add(personality2);
		
		Personality personality3 = new Personality();
		personality3.setPersonalityId(3);
		personality3.setName("テスト3");
		personalityList.add(personality3);
		
		return personalityList;
	}

}
