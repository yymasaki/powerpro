package com.example.controller.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.example.HadTechnicalSkillExample;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.spec.DeleteSpecsheetService;
import com.example.service.spec.GetNewestSpecsheetService;
import com.example.service.spec.SendSpecMailService;
import com.example.service.spec.UpdateSpecsheetService;
import com.example.service.technique.DeleteHadTechnicalSkillService;
import com.example.service.technique.GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageService;
import com.example.service.technique.UpdateHadTechnicalSkillStageService;
import com.example.service.technique.UpdateTechnicalSkillService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessRequestSpecsheetControllerTest {
	
	@MockBean
	private UpdateSpecsheetService updateSpecsheetService;
	@MockBean
	private DeleteSpecsheetService deleteSpecsheetService;
	@MockBean
	private GetNewestSpecsheetService getNewestSpecsheetService;
	@MockBean
	private ShowRequestSpecsheetController showRequestSpecsheetController;
	@MockBean
	private GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageService getTechnicalSkillListBySpecsheetIdAndStageService;
	@MockBean
	private UpdateTechnicalSkillService updateTechnicalSkillService;
	@MockBean
	private UpdateHadTechnicalSkillStageService updateHadTechnicalSkillStageService;
	@MockBean
	private DeleteHadTechnicalSkillService deleteHadTechnicalSkillService;
	@MockBean
	private SendSpecMailService sendSpecMailService;
	@InjectMocks
	private ProcessRequestSpecsheetController controller;

	@Autowired
	private WebLoginUser webLoginUser;
	@Autowired
	private SalesLoginUser salesLoginUser;
	@Autowired
	private AdminloginUser adminLoginUser;
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
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void TC1_?????????????????????() throws Exception {
		Collection<GrantedAuthority> authorityList = salesLoginUser.getAuthorityList();
		User user = salesLoginUser.getUser();
		Department department = new Department();
		department.setName("sales");
		user.setDepartment(department);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().is(403));
	}
	
	@Test
	public void TC2_????????????????????????() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC3_????????????????????????_????????????REQUESTING() {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.REQUESTING.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC4_????????????????????????_????????????SENTBACK_?????????????????????isEmpty() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);

		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setGender('M');
		User specUser = new User();
		specUser.setName("web");
		Department specDepartment = new Department();
		specDepartment.setName("web");
		specUser.setDepartment(specDepartment);
		specsheet.setUser(specUser);
		specsheet.setStage(Stage.SENT_BACK.getKey());
		List<DevExperience> devExperienceList = new ArrayList<>();
		specsheet.setDevExperienceList(devExperienceList);
		
		when(showRequestSpecsheetController.showRequestSpecsheet(
				any(Integer.class), any(Integer.class), any(Integer.class), any(Model.class), 
				any(RedirectAttributes.class), any(LoginUser.class)))
		.thenReturn("request/request-spec");
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.SENT_BACK.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))
				.requestAttr("specsheet", specsheet))
		.andExpect(model().errorCount(1))
		.andExpect(model().attributeHasFieldErrors("processSpecsheetForm", "adminComment"))
				.andExpect(model().attribute("message", "??????????????????\nERROR: ????????????????????????"))
		.andExpect(view().name("request/request-spec"));
		
		verify(showRequestSpecsheetController, times(1)).showRequestSpecsheet(
				any(Integer.class), any(Integer.class), any(Integer.class), any(Model.class), 
				any(RedirectAttributes.class), any(LoginUser.class));
	}
	
	@Test
	public void TC5_????????????????????????_????????????SENTBACK_?????????????????????notEmpty_noUpdate_newestACTIVE() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.ACTIVE.getKey());
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(0);
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "comment")
				.param("stage", Stage.SENT_BACK.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
				.andExpect(flash().attribute("message", "??????????????????\nERROR: ??????????????????????????????????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC6_????????????????????????_????????????ACTIVE_?????????????????????isEmpty_noUpdate_newestDELETED() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.DELETED.getKey());
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(0);
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "????????????\nERROR: ??????????????????????????????????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC7_????????????????????????_????????????ACTIVE_?????????????????????isEmpty_noUpdate_newestREQUESTING() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.REQUESTING.getKey());
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(0);
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "????????????\nERROR: ?????????????????????????????????????????????"))
		.andExpect(redirectedUrl(
				"/request/spec?specsheetId="+specsheet.getSpecsheetId()+"&userId=1&stage="+specsheet.getStage()));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC8_????????????????????????_????????????ACTIVE_?????????????????????isEmpty_noUpdate_newestSENTBACK() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.SENT_BACK.getKey());
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(0);
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
				.andExpect(flash().attribute("message", "????????????\nERROR: ????????????????????????????????????????????????"))
		.andExpect(redirectedUrl(
				"/request/spec?specsheetId="+specsheet.getSpecsheetId()+"&userId=1&stage="+specsheet.getStage()));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC9_????????????????????????_????????????ACTIVE_?????????????????????isEmpty_noUpdate_newestTEMPORARY() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.TEMPORARY.getKey());
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(0);
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "????????????\nERROR: ????????????????????????????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC10_????????????????????????_????????????ACTIVE_?????????????????????isEmpty_Update_?????????() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(new TechnicalSkill());
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(1);
		when(getTechnicalSkillListBySpecsheetIdAndStageService
				.getBySpecsheetIdAndUserIdAndStage(any(Integer.class), any(Integer.class), any()))
		.thenReturn(technicalSkillList);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "??????????????????????????????"+Stage.ACTIVE.getMessageForRequest()+"????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getTechnicalSkillListBySpecsheetIdAndStageService, times(1))
		.getBySpecsheetIdAndUserIdAndStage(any(Integer.class), any(Integer.class), any());
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteByExample(any(HadTechnicalSkillExample.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(), any(Integer.class), any(Integer.class), any(Integer.class));
	}
	
	@Test
	public void TC11_????????????????????????_????????????ACTIVE_?????????????????????isEmpty_Update_????????????????????????????????????_?????????() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(1);
		when(getTechnicalSkillListBySpecsheetIdAndStageService
				.getBySpecsheetIdAndUserIdAndStage(any(Integer.class), any(Integer.class), any()))
		.thenReturn(technicalSkillList);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "??????????????????????????????"+Stage.ACTIVE.getMessageForRequest()+"????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(getTechnicalSkillListBySpecsheetIdAndStageService, times(1))
		.getBySpecsheetIdAndUserIdAndStage(any(Integer.class), any(Integer.class), any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteByExample(any(HadTechnicalSkillExample.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(), any(Integer.class), any(Integer.class), any(Integer.class));
	}
	
	@Test
	public void TC12_????????????????????????_????????????SENTBACK_?????????????????????notEmpty_Update_?????????() throws Exception {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		when(updateSpecsheetService.updateSpecsheetStage(any(AppSpecsheet.class))).thenReturn(1);
		
		mockMvc.perform(post("/request/spec/process")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "comment")
				.param("stage", Stage.SENT_BACK.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "??????????????????????????????"+Stage.SENT_BACK.getMessageForRequest()+"????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(updateSpecsheetService, times(1)).updateSpecsheetStage(any(AppSpecsheet.class));
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(), any(Integer.class), any(Integer.class), any(Integer.class));
	}
	
	@Test
	public void TC13_??????????????????() {
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User user = adminLoginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/cancel")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.DELETED.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC14_????????????????????????_?????????userId???????????????() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/cancel")
				.param("specsheetId", "1")
				.param("userId", "111")
				.param("adminComment", "")
				.param("stage", Stage.DELETED.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
	}
	
	@Test
	public void TC15_????????????????????????_version??????NG_newStageACTIVE() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setVersion(2);
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/cancel")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.DELETED.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "????????????\nERROR: ??????????????????????????????????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC16_????????????????????????_version??????NG_newStageSENTBACK() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.SENT_BACK.getKey());
		specsheet.setVersion(2);
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/cancel")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.DELETED.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
				.andExpect(flash().attribute("message", "????????????\nERROR: ????????????????????????????????????????????????"))
		.andExpect(redirectedUrl(
				"/request/spec?specsheetId="+specsheet.getSpecsheetId()+"&userId=1&stage="+specsheet.getStage()));
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC17_????????????????????????_version??????NG_newStageDELETED() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.DELETED.getKey());
		specsheet.setVersion(2);
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/request/spec/cancel")
					.param("specsheetId", "1")
					.param("userId", "1")
					.param("adminComment", "")
					.param("stage", Stage.DELETED.getKey().toString())
					.param("version", "1")
					.with(csrf())
					.with(user(new LoginUser(user, authorityList))))
		).hasCause(new IllegalArgumentException());
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC18_????????????????????????_version??????OK_?????????() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setVersion(1);
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		mockMvc.perform(post("/request/spec/cancel")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.DELETED.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(flash().attribute("message", "???????????????????????????????????????????????????"))
		.andExpect(redirectedUrl("/request"));
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(deleteSpecsheetService, times(1)).deleteSpecsheet(any(Integer.class));
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteByExample(any(HadTechnicalSkillExample.class));
	}

}
