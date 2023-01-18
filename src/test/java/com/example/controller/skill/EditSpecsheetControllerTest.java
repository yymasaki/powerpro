package com.example.controller.skill;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.example.HadTechnicalSkillExample;
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.spec.DeleteSpecsheetService;
import com.example.service.spec.EditSpecsheetWithInsertService;
import com.example.service.spec.EditSpecsheetWithUpdateService;
import com.example.service.spec.GetSpecsheetByConditionService;
import com.example.service.spec.SendSpecMailService;
import com.example.service.spec.util.SpecStringUtils;
import com.example.service.technique.DeleteHadTechnicalSkillService;
import com.example.service.technique.GetTechnicalSkillsForTagService;
import com.example.service.technique.UpdateHadTechnicalSkillStageService;
import com.example.service.user.GetSpecificUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditSpecsheetControllerTest {
	
	@MockBean
	private GetSpecificUserService getSpecificUserService;
	@MockBean
	private GetSpecsheetByConditionService getSpecsheetByConditionService;
	@MockBean
	private EditSpecsheetWithInsertService editSpecsheetWithInsertService;
	@MockBean
	private EditSpecsheetWithUpdateService editSpecsheetWithUpdateService;
	@MockBean
	private UpdateHadTechnicalSkillStageService updateHadTechnicalSkillStageService;
	@MockBean
	private DeleteHadTechnicalSkillService deleteHadTechnicalSkillService;
	@MockBean
	private DeleteSpecsheetService deleteSpecsheetService;
	@MockBean
	private GetTechnicalSkillsForTagService getTechnicalSkillsForTagService;
	@MockBean
	private SpecStringUtils specStringUtils;
	@MockBean
	private SendSpecMailService sendSpecMailService;
	@InjectMocks
	private EditSpecsheetController controller;
	
	@Autowired
	private WebLoginUser webLoginUser;
	@Autowired
	private SalesLoginUser salesLoginUser;
	@Autowired
	private AdminloginUser adminLoginUser;
	@Autowired
	protected WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private List<StringBuilder> htStringBuilderList = new ArrayList<>();
	private List<StringBuilder> technicalSkillSBList = new ArrayList<>();

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
		
		StringBuilder htOSSB = new StringBuilder("a");
		StringBuilder htLanguageSB = new StringBuilder("b");
		StringBuilder htFrameworkSB = new StringBuilder("c");
		StringBuilder htLibrarySB = new StringBuilder("d");
		StringBuilder htMiddlewareSB = new StringBuilder("e");
		StringBuilder htToolSB = new StringBuilder("f");
		StringBuilder htProcessSB = new StringBuilder("g");
		htStringBuilderList.add(htOSSB);
		htStringBuilderList.add(htLanguageSB);
		htStringBuilderList.add(htFrameworkSB);
		htStringBuilderList.add(htLibrarySB);
		htStringBuilderList.add(htMiddlewareSB);
		htStringBuilderList.add(htToolSB);
		htStringBuilderList.add(htProcessSB);
		
		StringBuilder OSSB = new StringBuilder("a");
		StringBuilder languageSB = new StringBuilder("b");
		StringBuilder frameworkSB = new StringBuilder("c");
		StringBuilder librarySB = new StringBuilder("d");
		StringBuilder middlewareSB = new StringBuilder("e");
		StringBuilder toolSB = new StringBuilder("f");
		StringBuilder processSB = new StringBuilder("g");
		technicalSkillSBList.add(OSSB);
		technicalSkillSBList.add(languageSB);
		technicalSkillSBList.add(frameworkSB);
		technicalSkillSBList.add(librarySB);
		technicalSkillSBList.add(middlewareSB);
		technicalSkillSBList.add(toolSB);
		technicalSkillSBList.add(processSB);
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
		
		mockMvc.perform(get("/skill/spec/edit")
				.param("userId", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
	}
	
	@Test
	public void TC2_管理者がアクセス_specListのサイズ0() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
			.thenReturn(specsheetList);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(searchUser);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(get("/skill/spec/edit")
				.param("userId", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, 1, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, null, searchUser, null, null, null)))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
			.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getSpecificUserService, times(1)).get(any(Integer.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC3_管理者がアクセス_Active2つ() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet1 = new AppSpecsheet();
		specsheet1.setStage(Stage.ACTIVE.getKey());
		specsheet1.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 7, 7));
		specsheetList.add(specsheet1);
		AppSpecsheet specsheet2 = new AppSpecsheet();
		specsheet2.setStage(Stage.ACTIVE.getKey());
		specsheet2.setUpdatedAt(LocalDateTime.of(2020, 7, 15, 7, 7));
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet2.setUser(searchUser);
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkill.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill);
		specsheet2.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheetList.add(specsheet2);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
			.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet2);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(get("/skill/spec/edit")
				.param("userId", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, null, specsheet2.getStage(), null, null, null, 
						specsheet2.getUpdatedAt(), null, searchUser, null, specsheet2.getHadTechnicalSkillList(), null)))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC4_エンジニアがアクセス_ActiveとTemporary() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet1 = new AppSpecsheet();
		specsheet1.setStage(Stage.ACTIVE.getKey());
		specsheet1.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 7, 7));
		specsheetList.add(specsheet1);
		AppSpecsheet specsheet2 = new AppSpecsheet();
		specsheet2.setStage(Stage.TEMPORARY.getKey());
		specsheet2.setUpdatedAt(LocalDateTime.now());
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet2.setUser(searchUser);
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setStage(Stage.TEMPORARY.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		specsheet2.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperience> devExperienceList = new ArrayList<>();
		DevExperience devExperience = new DevExperience();
		devExperienceList.add(devExperience);
		specsheet2.setDevExperienceList(devExperienceList);
		specsheetList.add(specsheet2);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet2);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(get("/skill/spec/edit")
				.param("userId", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, specsheet2.getStage(), null, null, null, specsheet2.getUpdatedAt(), 
						null, searchUser, null, specsheet2.getHadTechnicalSkillList(), specsheet2.getDevExperienceList())))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC5_エンジニアがアクセス_ActiveとTemporary_24h経過による削除() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet1 = new AppSpecsheet();
		specsheet1.setStage(Stage.ACTIVE.getKey());
		specsheet1.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 7, 7));
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet1.setUser(searchUser);
		specsheet1.setHadTechnicalSkillList(new ArrayList<>());
		specsheet1.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet1);
		AppSpecsheet specsheet2 = new AppSpecsheet();
		specsheet2.setSpecsheetId(2);
		specsheet2.setStage(Stage.TEMPORARY.getKey());
		specsheet2.setUpdatedAt(LocalDateTime.of(2020, 7, 11, 7, 7));
		specsheetList.add(specsheet2);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet1);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(get("/skill/spec/edit")
				.param("userId", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "一時保存データは更新後24時間が経過したため削除されました"))
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, specsheet1.getStage(), null, null, null, specsheet1.getUpdatedAt(), 
						null, searchUser, null, specsheet1.getHadTechnicalSkillList(), new ArrayList<DevExperience>())))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
			.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteByExample(any(HadTechnicalSkillExample.class));
		verify(deleteSpecsheetService, times(1)).deleteSpecsheet(any(Integer.class));
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC6_エンジニアがアクセス_ActiveとRequesting() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet1 = new AppSpecsheet();
		specsheet1.setStage(Stage.ACTIVE.getKey());
		specsheetList.add(specsheet1);
		AppSpecsheet specsheet2 = new AppSpecsheet();
		specsheet2.setStage(Stage.REQUESTING.getKey());
		specsheetList.add(specsheet2);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/skill/spec/edit")
				.param("userId", "1")
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());

		verify(getSpecsheetByConditionService, times(1))
			.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC7_営業が編集() throws Exception {
		Collection<GrantedAuthority> authorityList = salesLoginUser.getAuthorityList();
		User user = salesLoginUser.getUser();
		Department department = new Department();
		department.setName("sales");
		user.setDepartment(department);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void TC8_管理者がRequestingで編集() {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/skill/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.ACTIVE.getKey().toString())
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC10_管理者がActiveで編集_hasError() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setUserId(1);
		form.setGeneration("");
		form.setGender("");
		form.setNearestStation("");
		form.setStartBusinessDate("");
		form.setAppeal("");
		form.setEffort("");
		form.setCertification("");
		form.setPreJob("");
		form.setStage(Stage.ACTIVE.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("");
		devExperienceForm.setStartedOn("");
		devExperienceForm.setFinishedOn("");
		devExperienceForm.setRole("");
		devExperienceForm.setProjectDetails("");
		devExperienceForm.setTasks("");
		devExperienceForm.setAcquired("");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(searchUser);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.ACTIVE.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.param("usedTechnicalSkillList[0][0]", "a")
				.param("usedTechnicalSkillList[0][0]", "a")
				.param("usedTechnicalSkillList[0][1]", "b")
				.param("usedTechnicalSkillList[0][2]", "c")
				.param("usedTechnicalSkillList[0][3]", "d")
				.param("usedTechnicalSkillList[0][4]", "e")
				.param("usedTechnicalSkillList[0][5]", "f")
				.param("usedTechnicalSkillList[0][6]", "g")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "employeeId"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "generation"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "gender"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "nearestStation"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "startBusinessDate"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "engineerPeriod"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "sePeriod"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "pgPeriod"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "itPeriod"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "appeal"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "effort"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].projectName"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].startedOn"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].finishedOn"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].teamMembers"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].projectMembers"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].role"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].projectDetails"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].tasks"))
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].acquired"))
		.andExpect(model().attribute("message", 
				Stage.ACTIVE.getMessageForEdit()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, 1, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, null, searchUser, null, null, null)))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1)).getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC11_エンジニアが自分以外のuserId指定() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/skill/spec/edit/do")
				.param("userId", "111")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC12_エンジニアがActiveで編集() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}

	@Test
	public void TC13_エンジニアがTemporaryで編集_version照合なし_元データのstageActiveのreturnStageあり() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setUserId(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		List<DevExperienceForm> devExperienceFormList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevCount(1);
		devExperienceFormList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceFormList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithInsertService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class))).thenReturn(0);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("userId", "1")
				.param("stage", Stage.TEMPORARY.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.ACTIVE.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", 
				Stage.TEMPORARY.getMessageForEdit()+"失敗\nERROR: このスペックシートは既に"
						+ Stage.ACTIVE.getMessageForEdit() + "されました"))
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithInsertService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
	}
	
	@Test
	public void TC14_エンジニアがRequestingで編集_startedOnでhasError() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setSpecsheetId(1);
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(12);
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.REQUESTING.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.REQUESTING.getKey());
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("プロジェクト");
		devExperienceForm.setStartedOn("2100-06");
		devExperienceForm.setFinishedOn("2100-07");
		devExperienceForm.setTeamMembers(3);
		devExperienceForm.setProjectMembers(6);
		devExperienceForm.setRole("役割");
		devExperienceForm.setProjectDetails("詳細");
		devExperienceForm.setTasks("タスク");
		devExperienceForm.setAcquired("知見");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(searchUser);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.REQUESTING.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.param("usedTechnicalSkillList[0][0]", "a")
				.param("usedTechnicalSkillList[0][1]", "b")
				.param("usedTechnicalSkillList[0][2]", "c")
				.param("usedTechnicalSkillList[0][3]", "d")
				.param("usedTechnicalSkillList[0][4]", "e")
				.param("usedTechnicalSkillList[0][5]", "f")
				.param("usedTechnicalSkillList[0][6]", "g")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].startedOn"))
		.andExpect(model().attribute("message", 
				Stage.REQUESTING.getMessageForEdit()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, 1, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, null, searchUser, null, null, null)))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1)).getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC15_エンジニアがRequestingで編集_finishedOnでhasError() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setSpecsheetId(1);
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(12);
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.REQUESTING.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.REQUESTING.getKey());
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("プロジェクト");
		devExperienceForm.setStartedOn("2020-06");
		devExperienceForm.setFinishedOn("2020-05");
		devExperienceForm.setTeamMembers(3);
		devExperienceForm.setProjectMembers(6);
		devExperienceForm.setRole("役割");
		devExperienceForm.setProjectDetails("詳細");
		devExperienceForm.setTasks("タスク");
		devExperienceForm.setAcquired("知見");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(searchUser);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.REQUESTING.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.param("usedTechnicalSkillList[0][0]", "a")
				.param("usedTechnicalSkillList[0][1]", "b")
				.param("usedTechnicalSkillList[0][2]", "c")
				.param("usedTechnicalSkillList[0][3]", "d")
				.param("usedTechnicalSkillList[0][4]", "e")
				.param("usedTechnicalSkillList[0][5]", "f")
				.param("usedTechnicalSkillList[0][6]", "g")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].finishedOn"))
		.andExpect(model().attribute("message", 
				Stage.REQUESTING.getMessageForEdit()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, 1, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, null, searchUser, null, null, null)))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1)).getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC16_エンジニアがRequestingで編集_finishedOnでhasError() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setSpecsheetId(1);
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(12);
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.REQUESTING.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.REQUESTING.getKey());
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("プロジェクト");
		devExperienceForm.setStartedOn("2020-06");
		devExperienceForm.setFinishedOn("");
		devExperienceForm.setTeamMembers(3);
		devExperienceForm.setProjectMembers(6);
		devExperienceForm.setRole("役割");
		devExperienceForm.setProjectDetails("詳細");
		devExperienceForm.setTasks("タスク");
		devExperienceForm.setAcquired("知見");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(searchUser);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.REQUESTING.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.param("usedTechnicalSkillList[0][0]", "a")
				.param("usedTechnicalSkillList[0][1]", "b")
				.param("usedTechnicalSkillList[0][2]", "c")
				.param("usedTechnicalSkillList[0][3]", "d")
				.param("usedTechnicalSkillList[0][4]", "e")
				.param("usedTechnicalSkillList[0][5]", "f")
				.param("usedTechnicalSkillList[0][6]", "g")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("editSpecsheetForm", "devExperienceList[0].finishedOn"))
		.andExpect(model().attribute("message", 
				Stage.REQUESTING.getMessageForEdit()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("specsheet", 
				new AppSpecsheet(null, 1, null, null, null, null, null, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, null, searchUser, null, null, null)))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("skill/skill-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1)).getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC17_エンジニアがRequestingで編集_version照合なし_元データのstageRequestingの正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(12);
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.REQUESTING.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.REQUESTING.getKey());
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("プロジェクト");
		devExperienceForm.setStartedOn("2020-06");
		devExperienceForm.setFinishedOn("2020-07");
		devExperienceForm.setTeamMembers(3);
		devExperienceForm.setProjectMembers(6);
		devExperienceForm.setRole("役割");
		devExperienceForm.setProjectDetails("詳細");
		devExperienceForm.setTasks("タスク");
		devExperienceForm.setAcquired("知見");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithUpdateService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class))).thenReturn(null);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.REQUESTING.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("success", "スペックシートを" + Stage.REQUESTING.getMessageForEdit() + "しました"))
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithUpdateService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
	}
	
	@Test
	public void TC18_管理者がActiveで編集_version照合なし_元データのstageActiveの正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(12);
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.ACTIVE.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("プロジェクト");
		devExperienceForm.setStartedOn("2020-06");
		devExperienceForm.setFinishedOn("2020-07");
		devExperienceForm.setTeamMembers(3);
		devExperienceForm.setProjectMembers(6);
		devExperienceForm.setRole("役割");
		devExperienceForm.setProjectDetails("詳細");
		devExperienceForm.setTasks("タスク");
		devExperienceForm.setAcquired("知見");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithInsertService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class))).thenReturn(null);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("rawStage", Stage.ACTIVE.getKey().toString())
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("success", "スペックシートを" + Stage.ACTIVE.getMessageForEdit() + "しました"))
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithInsertService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(String.class), any(), any(Integer.class), any(Integer.class));
	}
	
	@Test
	public void TC19_管理者がActiveで編集_version照合なし_元データなしの正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		EditSpecsheetForm form = new EditSpecsheetForm();
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(12);
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.ACTIVE.getKey());
		form.setVersion(1);
		String[] hadTechnicalSkillList = new String[7];
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<DevExperienceForm> devExperienceList = new ArrayList<>();
		DevExperienceForm devExperienceForm = new DevExperienceForm();
		devExperienceForm.setDevExperienceId(1);
		devExperienceForm.setProjectName("プロジェクト");
		devExperienceForm.setStartedOn("2020-06");
		devExperienceForm.setFinishedOn("2020-07");
		devExperienceForm.setTeamMembers(3);
		devExperienceForm.setProjectMembers(6);
		devExperienceForm.setRole("役割");
		devExperienceForm.setProjectDetails("詳細");
		devExperienceForm.setTasks("タスク");
		devExperienceForm.setAcquired("知見");
		devExperienceForm.setDevCount(1);
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithInsertService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class))).thenReturn(null);
		
		mockMvc.perform(post("/skill/spec/edit/do")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("success", "スペックシートを" + Stage.ACTIVE.getMessageForEdit() + "しました"))
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithInsertService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(String.class), any(), any(Integer.class), any(Integer.class));
	}

}
