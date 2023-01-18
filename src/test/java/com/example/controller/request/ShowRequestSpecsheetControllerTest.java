package com.example.controller.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.common.Category;
import com.example.common.Stage;
import com.example.controller.skill.ShowSpecsheetController;
import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.spec.GetSpecsheetByConditionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowRequestSpecsheetControllerTest {
	
	@MockBean
	private GetSpecsheetByConditionService getSpecsheetByConditionService;
	@MockBean
	private ShowSpecsheetController showSpecsheetController;
	@InjectMocks
	private ShowRequestSpecsheetController controller;

	@Autowired
	private WebLoginUser webLoginUser;
	@Autowired
	private SalesLoginUser salesLoginUser;
	@Autowired
	private AdminloginUser adminLoginUser;
	@Autowired
	protected WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private List<AppSpecsheet> specsheetList = new ArrayList<>();
	private AppSpecsheet specsheet = new AppSpecsheet();
	private List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
		
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(
				new TechnicalSkill(null, "a", Category.OS.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(
				new TechnicalSkill(null, "b", Category.LANGUAGE.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(
				new TechnicalSkill(null, "c", Category.FRAMEWORK.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(
				new TechnicalSkill(null, "d", Category.LIBRARY.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(
				new TechnicalSkill(null, "e", Category.MIDDLEWARE.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(
				new TechnicalSkill(null, "f", Category.TOOL.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(
				new TechnicalSkill(null, "g", Category.PROCESS.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkillList.add(hadTechnicalSkill7);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void TC1_営業がアクセス() throws Exception {
		Collection<GrantedAuthority> authorityList = salesLoginUser.getAuthorityList();
		User user = salesLoginUser.getUser();
		Department department = new Department();
		department.setName("sales");
		user.setDepartment(department);
		
			mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "2"))
			.andExpect(status().is(403));
	}
	
	@Test
	public void TC2_エンジニアがアクセス_自分のidでない場合() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "111")
				.param("stage", "2"))
		).hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC3_エンジニアがアクセス_ステージが申請中でない場合() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "0"))
		).hasCause(new IllegalArgumentException());
		
	}
	
	@Test
	public void TC4_エンジニアがアクセス_差し戻しデータ_スペックシートが取得できない場合() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "3"))
		).hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC5_エンジニアがアクセス_申請中データ_取得したスペックシートのステージが異なる場合() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "2"))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "表示失敗\nERROR: この申請は既に承認されました"))
		.andExpect(redirectedUrl("/request"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());	
	}
	
	@Test
	public void TC6_エンジニアがアクセスする正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		specsheet.setUserId(1);
		specsheet.setGender('M');
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "2"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(view().name("request/request-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		
	}
	
	@Test
	public void TC7_管理者がアクセスする正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		specsheet.setUserId(1);
		specsheet.setGender('M');
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		User searchUser = new User();
		Department searchDep = new Department();
		searchUser.setDepartment(searchDep);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "2"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(view().name("request/request-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		
	}
	
	@Test
	public void TC8_管理者がアクセスする正常系2() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		specsheet.setUserId(1);
		specsheet.setGender('M');
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setStage(Stage.SENT_BACK.getKey());
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		User searchUser = new User();
		Department searchDep = new Department();
		searchUser.setDepartment(searchDep);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "3"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(view().name("request/request-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		
	}
	
	@Test
	public void TC9_エンジニアがアクセス_他のspecsheetIdを指定して他の人のspecsheetを表示しようとした場合() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		specsheet.setUserId(2);
		specsheet.setGender('M');
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", "2"))
		).hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		
	}
	
}
