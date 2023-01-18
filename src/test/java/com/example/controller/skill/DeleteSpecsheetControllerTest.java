package com.example.controller.skill;

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
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.DevExperience;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.spec.DeleteSpecsheetService;
import com.example.service.spec.SendSpecMailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteSpecsheetControllerTest {
	
	@MockBean
	private DeleteSpecsheetService deleteSpecsheetService;
	@MockBean
	private ShowSpecsheetController showSpecsheetController;
	@MockBean
	private SendSpecMailService sendSpecMailService;
	@InjectMocks
	private DeleteSpecsheetController controller;

	@Autowired
	private WebLoginUser webLoginUser;
	@Autowired
	private AdminloginUser adminloginUser;
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
	public void TC1_エンジニアが削除() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);

		mockMvc.perform(post("/skill/spec/delete")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().is(403));
	}
	
	@Test
	public void TC2_管理者がアクセス_ステージがREQUESTING() {
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User user = adminloginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/skill/spec/delete")
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
	public void TC3_管理者がアクセス_ステージACTIVE_管理者コメントisEmpty() throws Exception {
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User user = adminloginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setName("admin");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setGender('M');
		User specUser = new User();
		specUser.setName("web");
		Department specDepartment = new Department();
		specDepartment.setName("web");
		specUser.setDepartment(specDepartment);
		specsheet.setUser(specUser);
		List<DevExperience> devExperienceList = new ArrayList<>();
		specsheet.setDevExperienceList(devExperienceList);
		
		when(showSpecsheetController.showSpecsheet(any(Integer.class), any(Model.class), any(LoginUser.class)))
		.thenReturn("skill/skill-spec");
		
		mockMvc.perform(post("/skill/spec/delete")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))
				.requestAttr("specsheet", specsheet))
		.andExpect(model().errorCount(1))
		.andExpect(model().attributeHasFieldErrors("processSpecsheetForm", "adminComment"))
		.andExpect(view().name("skill/skill-spec"));
		
		verify(showSpecsheetController, times(1))
		.showSpecsheet(any(Integer.class), any(Model.class), any(LoginUser.class));
	}
	
	@Test
	public void TC4_管理者がアクセス_ステージACTIVE_管理者コメントnotEmpty() throws Exception {
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User user = adminloginUser.getUser();
		Department department = new Department();
		department.setName("admin");
		user.setName("admin");
		user.setDepartment(department);
		
		mockMvc.perform(post("/skill/spec/delete")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("adminComment", "comment")
				.param("stage", Stage.ACTIVE.getKey().toString())
				.param("version", "1")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isFound())
		.andExpect(model().hasNoErrors())
		.andExpect(flash().attribute("success", "対象のスペックシートを削除しました"))
		.andExpect(redirectedUrl("/skill/spec?userId=1"));
		
		verify(deleteSpecsheetService, times(1)).deleteSpecsheet(any(Integer.class));
		verify(sendSpecMailService, times(1))
		.sendSpecMail(any(String.class), any(String.class), any(Integer.class), any(Integer.class), any(Integer.class));
	}

}
