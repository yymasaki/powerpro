package com.example.controller.skill;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.common.Category;
import com.example.common.Stage;
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
import com.example.service.user.GetSpecificUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowSpecsheetControllerTest {
	
	@MockBean
	private GetSpecsheetByConditionService getSpecsheetByConditionService;
	@MockBean
	private GetSpecificUserService getSpecificUserService;
	@InjectMocks
	private ShowSpecsheetController controller;
	
	@Autowired
	private WebLoginUser webLoginUser;
	@Autowired
	private AdminloginUser adminloginUser;
	@Autowired
	private SalesLoginUser salesLoginUser;
	@Autowired
	protected WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private List<AppSpecsheet> specsheetList = new ArrayList<>();
	private AppSpecsheet specsheet = new AppSpecsheet();
	private AppSpecsheet specsheet2 = new AppSpecsheet();
	private List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
	private List<DevExperience> devExperienceList = new ArrayList<>();

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
		hadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(
				new TechnicalSkill(null, "b", Category.LANGUAGE.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkill2.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(
				new TechnicalSkill(null, "c", Category.FRAMEWORK.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkill3.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(
				new TechnicalSkill(null, "d", Category.LIBRARY.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkill4.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(
				new TechnicalSkill(null, "e", Category.MIDDLEWARE.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkill5.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(
				new TechnicalSkill(null, "f", Category.TOOL.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkill6.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(
				new TechnicalSkill(null, "g", Category.PROCESS.getKey(), null, null, null, null, null, null, null));
		hadTechnicalSkill7.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill7);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void TC1_エンジニアがアクセス_未登録の場合() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
			.thenReturn(specsheetList);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(user);
		
		mockMvc.perform(get("/skill/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("userId", "1"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("noRecord", "noRecord"))
		.andExpect(model().attributeExists("specsheet"))
		.andExpect(view().name("skill/skill-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
			.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC2_管理者がアクセス_エンジニアでない所属の人を選択した場合() {
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User user = adminloginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		User searchUser = new User();
		searchUser.setDepartmentId(4);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(searchUser);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/skill/spec")
					.with(user(new LoginUser(user, authorityList)))
					.param("userId", "3"))
		).hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getSpecificUserService, times(1)).get(any(Integer.class));
	}
	
	@Test
	public void TC3_管理者がアクセス_登録のないuserIdを選択した場合() {
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User user = adminloginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(null);
		
		assertThatThrownBy(() ->
		mockMvc.perform(get("/skill/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("userId", "111"))
				).hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getSpecificUserService, times(1)).get(any(Integer.class));
	}
	
	@Test
	public void TC4_エンジニアがアクセス_ACTIVEのみ登録済の場合() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		department.setStaffId("aa");
		user.setDepartment(department);
		user.setName("taro");
		
		specsheet.setGender('M');
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setUser(user);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setDevExperienceList(devExperienceList);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/skill/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("userId", "1"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(view().name("skill/skill-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC5_エンジニアがアクセス_REQUESTINGのみ登録済の場合() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		department.setStaffId("aa");
		user.setDepartment(department);
		user.setName("taro");
		
		specsheet2.setStage(Stage.REQUESTING.getKey());
		specsheetList.add(specsheet2);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(user);
		
		mockMvc.perform(get("/skill/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("userId", "1"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("requestOrSentBackSpec", specsheet2))
		.andExpect(model().attribute("noRecord", "noRecord"))
		.andExpect(model().attributeExists("specsheet"))
		.andExpect(view().name("skill/skill-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getSpecificUserService, times(1)).get(any(Integer.class));
	}
	
	@Test
	public void TC6_営業がアクセス_ACTIVEとSENTBACKを登録済の場合() throws Exception {
		Collection<GrantedAuthority> authorityList = salesLoginUser.getAuthorityList();
		User user = salesLoginUser.getUser();
		Department department = new Department();
		department.setName("sales");
		user.setDepartment(department);
		
		User searchUser = webLoginUser.getUser();
		user.setName("taro");
		Department department2 = new Department();
		department2.setName("web");
		searchUser.setDepartment(department2);
		
		specsheet.setGender('M');
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setUser(searchUser);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setDevExperienceList(devExperienceList);
		specsheetList.add(specsheet);
		specsheet2.setStage(Stage.SENT_BACK.getKey());
		specsheetList.add(specsheet2);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/skill/spec")
				.with(user(new LoginUser(user, authorityList)))
				.param("userId", "1"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("requestOrSentBackSpec", specsheet2))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(view().name("skill/skill-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}

}
