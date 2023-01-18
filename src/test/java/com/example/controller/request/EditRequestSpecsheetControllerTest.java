package com.example.controller.request;

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
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.spec.EditSpecsheetWithUpdateService;
import com.example.service.spec.GetSpecsheetByConditionService;
import com.example.service.spec.SendSpecMailService;
import com.example.service.spec.util.SpecStringUtils;
import com.example.service.technique.GetTechnicalSkillsForTagService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditRequestSpecsheetControllerTest {

	@MockBean
	private GetSpecsheetByConditionService getSpecsheetByConditionService;
	@MockBean
	private GetTechnicalSkillsForTagService getTechnicalSkillsForTagService;
	@MockBean
	private EditSpecsheetWithUpdateService editSpecsheetWithUpdateService;
	@MockBean
	private SpecStringUtils specStringUtils;
	@MockBean
	private SendSpecMailService sendSpecMailService;
	@InjectMocks
	private EditRequestSpecsheetController controller;

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
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void TC2_エンジニアが自分以外のuserId指定() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "111")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC3_エンジニアがActiveのspecsheet指定() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC4_エンジニアのspecsheetが存在しない場合() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
			.thenReturn(specsheetList);
		
		assertThatThrownBy(() ->
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.SENT_BACK.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
			.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC5_エンジニアの最新specsheetのバージョンが1異なりstageActive() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(2);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", 
				Stage.REQUESTING.getMessageForRequest() + "失敗\nERROR: この申請は既に"
				+ Stage.of(specsheet.getStage()).getMessageForRequest() + "されました"))
		.andExpect(redirectedUrl("/request"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC6_エンジニアの最新specsheetのバージョンが1異なりstageRequesting() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setVersion(2);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", 
				Stage.REQUESTING.getMessageForRequest() + "失敗\nERROR: この申請は既に"
						+ Stage.of(specsheet.getStage()).getMessageForRequest() + "されました"))
		.andExpect(redirectedUrl(
				"/request/spec?specsheetId="+specsheet.getSpecsheetId()+"&userId=1&stage="+specsheet.getStage()));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC7_エンジニアの最新specsheetのバージョンが1異なりstageDeleted() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.DELETED.getKey());
		specsheet.setVersion(2);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC8_エンジニアの最新specsheetのバージョンが2異なりstageActive() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(3);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", 
				Stage.REQUESTING.getMessageForRequest() + "失敗\nERROR: この申請は既に変更もしくは処理されました"))
		.andExpect(redirectedUrl("/request"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC9_エンジニアの最新specsheetのバージョンが2異なりstageRequesting() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setVersion(3);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", 
				Stage.REQUESTING.getMessageForRequest() + "失敗\nERROR: この申請は既に変更もしくは処理されました"))
		.andExpect(redirectedUrl(
				"/request/spec?specsheetId="+specsheet.getSpecsheetId()+"&userId=1&stage="+specsheet.getStage()));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC10_エンジニアの最新specsheetのバージョンが2異なりstageDeleted() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.DELETED.getKey());
		specsheet.setVersion(3);
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", 
				Stage.REQUESTING.getMessageForRequest() + "失敗\nERROR: この申請は既に変更もしくは処理されました"))
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC11_エンジニアがアクセス_バージョン照合OK_取得したスペックシートが自分のものでない場合() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setUserId(2);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setUpdatedAt(LocalDateTime.now());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		
		assertThatThrownBy(() ->
			mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "2")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
	}
	
	@Test
	public void TC12_管理者がアクセス_バージョン照合OKの正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setUpdatedAt(LocalDateTime.now());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setStage(Stage.REQUESTING.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("htOSSB", htStringBuilderList.get(0).toString()))
		.andExpect(model().attribute("htLanguageSB", htStringBuilderList.get(1).toString()))
		.andExpect(model().attribute("htFrameworkSB", htStringBuilderList.get(2).toString()))
		.andExpect(model().attribute("htLibrarySB", htStringBuilderList.get(3).toString()))
		.andExpect(model().attribute("htMiddlewareSB", htStringBuilderList.get(4).toString()))
		.andExpect(model().attribute("htToolSB", htStringBuilderList.get(5).toString()))
		.andExpect(model().attribute("htProcessSB", htStringBuilderList.get(6).toString()))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("request/request-edit-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC13_エンジニアがアクセス_バージョン照合OKの正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setUserId(1);
		specsheet.setStage(Stage.SENT_BACK.getKey());
		specsheet.setUpdatedAt(LocalDateTime.now());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setStage(Stage.SENT_BACK.getKey());
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(get("/request/spec/edit")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.SENT_BACK.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("htOSSB", htStringBuilderList.get(0).toString()))
		.andExpect(model().attribute("htLanguageSB", htStringBuilderList.get(1).toString()))
		.andExpect(model().attribute("htFrameworkSB", htStringBuilderList.get(2).toString()))
		.andExpect(model().attribute("htLibrarySB", htStringBuilderList.get(3).toString()))
		.andExpect(model().attribute("htMiddlewareSB", htStringBuilderList.get(4).toString()))
		.andExpect(model().attribute("htToolSB", htStringBuilderList.get(5).toString()))
		.andExpect(model().attribute("htProcessSB", htStringBuilderList.get(6).toString()))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("request/request-edit-spec"));
		
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC14_営業が編集() throws Exception {
		Collection<GrantedAuthority> authorityList = salesLoginUser.getAuthorityList();
		User user = salesLoginUser.getUser();
		Department department = new Department();
		department.setName("sales");
		user.setDepartment(department);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void TC15_管理者がRequestingで編集() {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC16_エンジニアが自分以外のuserIdで編集() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "111")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC17_エンジニアがActiveで編集() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC18_管理者が編集_入力値null() throws Exception {
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
		form.setRawStage(Stage.REQUESTING.getKey());
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
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
				Stage.of(form.getStage()).getMessageForRequest()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("htOSSB", htStringBuilderList.get(0).toString()))
		.andExpect(model().attribute("htLanguageSB", htStringBuilderList.get(1).toString()))
		.andExpect(model().attribute("htFrameworkSB", htStringBuilderList.get(2).toString()))
		.andExpect(model().attribute("htLibrarySB", htStringBuilderList.get(3).toString()))
		.andExpect(model().attribute("htMiddlewareSB", htStringBuilderList.get(4).toString()))
		.andExpect(model().attribute("htToolSB", htStringBuilderList.get(5).toString()))
		.andExpect(model().attribute("htProcessSB", htStringBuilderList.get(6).toString()))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("request/request-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC19_管理者が編集_startedOnが未来() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
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
				Stage.of(form.getStage()).getMessageForRequest()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("htOSSB", htStringBuilderList.get(0).toString()))
		.andExpect(model().attribute("htLanguageSB", htStringBuilderList.get(1).toString()))
		.andExpect(model().attribute("htFrameworkSB", htStringBuilderList.get(2).toString()))
		.andExpect(model().attribute("htLibrarySB", htStringBuilderList.get(3).toString()))
		.andExpect(model().attribute("htMiddlewareSB", htStringBuilderList.get(4).toString()))
		.andExpect(model().attribute("htToolSB", htStringBuilderList.get(5).toString()))
		.andExpect(model().attribute("htProcessSB", htStringBuilderList.get(6).toString()))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("request/request-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC20_管理者が編集_finishedOnがstartedOnより過去() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
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
				Stage.of(form.getStage()).getMessageForRequest()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("htOSSB", htStringBuilderList.get(0).toString()))
		.andExpect(model().attribute("htLanguageSB", htStringBuilderList.get(1).toString()))
		.andExpect(model().attribute("htFrameworkSB", htStringBuilderList.get(2).toString()))
		.andExpect(model().attribute("htLibrarySB", htStringBuilderList.get(3).toString()))
		.andExpect(model().attribute("htMiddlewareSB", htStringBuilderList.get(4).toString()))
		.andExpect(model().attribute("htToolSB", htStringBuilderList.get(5).toString()))
		.andExpect(model().attribute("htProcessSB", htStringBuilderList.get(6).toString()))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("request/request-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC21_管理者が編集_finishedOnがEmpty() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		List<AppSpecsheet> specsheetList = new ArrayList<>();
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(1);
		User searchUser = new User();
		Department searchDepartment = new Department();
		searchDepartment.setStaffId("AP-202");
		searchUser.setDepartment(searchDepartment);
		specsheet.setUser(searchUser);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		specsheetList.add(specsheet);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(getSpecsheetByConditionService.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any()))
		.thenReturn(specsheetList);
		when(specStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(specStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		when(getTechnicalSkillsForTagService.getTechnicalSkillsForTag()).thenReturn(technicalSkillSBList);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
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
				Stage.of(form.getStage()).getMessageForRequest()+"失敗\nERROR: 入力値が不正です"))
		.andExpect(model().attribute("htOSSB", htStringBuilderList.get(0).toString()))
		.andExpect(model().attribute("htLanguageSB", htStringBuilderList.get(1).toString()))
		.andExpect(model().attribute("htFrameworkSB", htStringBuilderList.get(2).toString()))
		.andExpect(model().attribute("htLibrarySB", htStringBuilderList.get(3).toString()))
		.andExpect(model().attribute("htMiddlewareSB", htStringBuilderList.get(4).toString()))
		.andExpect(model().attribute("htToolSB", htStringBuilderList.get(5).toString()))
		.andExpect(model().attribute("htProcessSB", htStringBuilderList.get(6).toString()))
		.andExpect(model().attribute("specsheet", specsheet))
		.andExpect(model().attribute("OSSB", technicalSkillSBList.get(0)))
		.andExpect(model().attribute("languageSB", technicalSkillSBList.get(1)))
		.andExpect(model().attribute("frameworkSB", technicalSkillSBList.get(2)))
		.andExpect(model().attribute("librarySB", technicalSkillSBList.get(3)))
		.andExpect(model().attribute("middlewareSB", technicalSkillSBList.get(4)))
		.andExpect(model().attribute("toolSB", technicalSkillSBList.get(5)))
		.andExpect(model().attribute("processSB", technicalSkillSBList.get(6)))
		.andExpect(view().name("request/request-edit-spec"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(getSpecsheetByConditionService, times(1))
		.getSpecsheetByCondition(any(Integer.class), any(Integer.class), any(), any());
		verify(specStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(specStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(getTechnicalSkillsForTagService, times(1)).getTechnicalSkillsForTag();
	}
	
	@Test
	public void TC22_管理者が編集_チェックOK_returnStageActive() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithUpdateService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class)))
		.thenReturn(Stage.ACTIVE.getKey());
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("message", 
				Stage.of(form.getStage()).getMessageForRequest()+"失敗\nERROR: この申請は既に"
						+ Stage.ACTIVE.getMessageForRequest() + "されました"))
		.andExpect(redirectedUrl("/request"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithUpdateService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
	}
	
	@Test
	public void TC23_管理者が編集_チェックOK_returnStageRequest() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithUpdateService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class)))
		.thenReturn(Stage.REQUESTING.getKey());
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("message", 
				Stage.of(form.getStage()).getMessageForRequest()+"失敗\nERROR: この申請は既に"
						+ Stage.REQUESTING.getMessageForRequest() + "されました"))
		.andExpect(redirectedUrl("/request/spec?specsheetId=1&userId=1&stage=" + Stage.REQUESTING.getKey()));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithUpdateService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
	}
	
	@Test
	public void TC24_管理者が編集_チェックOK_returnStageDeleted() {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithUpdateService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class)))
		.thenReturn(Stage.DELETED.getKey());
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IllegalArgumentException());
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithUpdateService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
	}
	
	@Test
	public void TC25_管理者が編集_正常系() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithUpdateService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class))).thenReturn(null);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("message", 
				"スペックシート申請を" + Stage.of(form.getStage()).getMessageForRequest()+"しました"))
		.andExpect(redirectedUrl("/request"));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithUpdateService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(), any(Integer.class), any(Integer.class), any(Integer.class));
	}
	
	@Test
	public void TC26_エンジニアが編集_正常系() throws Exception {
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
		devExperienceList.add(devExperienceForm);
		form.setDevExperienceList(devExperienceList);
		
		when(specStringUtils.trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class))).thenReturn(form);
		when(editSpecsheetWithUpdateService.editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class))).thenReturn(null);
		
		mockMvc.perform(post("/request/spec/edit/do")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.param("devExperienceList[0].devCount", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("success", 
				"スペックシート申請を" + Stage.of(form.getStage()).getMessageForRequest()+"しました"))
		.andExpect(redirectedUrl("/request/spec?specsheetId=1&userId=1&stage="+form.getStage()));
		
		verify(specStringUtils, times(1)).trimWhitespaceForEditSpecsheetForm(any(EditSpecsheetForm.class));
		verify(editSpecsheetWithUpdateService, times(1)).editSpecsheet(any(EditSpecsheetForm.class), any(LoginUser.class));
	}

}
