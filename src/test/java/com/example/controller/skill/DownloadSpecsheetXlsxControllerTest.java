package com.example.controller.skill;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.DevExperience;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.spec.CreateSpecsheetXlsxService;
import com.example.service.spec.GetSpecsheetForDownloadService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadSpecsheetXlsxControllerTest {
	
	@MockBean
	private GetSpecsheetForDownloadService getSpecsheetForDownloadService;
	@MockBean
	private CreateSpecsheetXlsxService createSpecsheetXlsxService;
	@MockBean
	private ShowSpecsheetController showSpecsheetController;
	@InjectMocks
	private DownloadSpecsheetXlsxController controller;
	
	@Autowired
	private WebLoginUser webLoginUser;
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
	public void TC1_開発経験を11個指定() {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		
		assertThatThrownBy(() ->
			mockMvc.perform(post("/skill/download/xlsx")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("version", "1")
				.param("devExperienceIdList[0]", "1")
				.param("devExperienceIdList[1]", "2")
				.param("devExperienceIdList[2]", "3")
				.param("devExperienceIdList[3]", "4")
				.param("devExperienceIdList[4]", "5")
				.param("devExperienceIdList[5]", "6")
				.param("devExperienceIdList[6]", "7")
				.param("devExperienceIdList[7]", "8")
				.param("devExperienceIdList[8]", "9")
				.param("devExperienceIdList[9]", "10")
				.param("devExperienceIdList[10]", "11")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList)))))
		.hasCause(new IndexOutOfBoundsException());
	}
	
	@Test
	public void TC2_開発経験を10個指定_specsheetがnull() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		user.setDepartment(department);
		user.setName("taro");
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setGender('M');
		specsheet.setUser(user);
		specsheet.setDevExperienceList(new ArrayList<DevExperience>());
		
		when(getSpecsheetForDownloadService
				.getSpecsheetForDownload(any(Integer.class), any(Integer.class), any(), any(Integer.class)))
		.thenReturn(null);
		when(showSpecsheetController.showSpecsheet(any(Integer.class), any(Model.class), any(LoginUser.class)))
		.thenReturn("skill/skill-spec");
		
		mockMvc.perform(post("/skill/download/xlsx")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("version", "1")
				.param("devExperienceIdList[0]", "1")
				.param("devExperienceIdList[1]", "2")
				.param("devExperienceIdList[2]", "3")
				.param("devExperienceIdList[3]", "4")
				.param("devExperienceIdList[4]", "5")
				.param("devExperienceIdList[5]", "6")
				.param("devExperienceIdList[6]", "7")
				.param("devExperienceIdList[7]", "8")
				.param("devExperienceIdList[8]", "9")
				.param("devExperienceIdList[9]", "10")
				.requestAttr("specsheet", specsheet)
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "ダウンロード失敗"))
		.andExpect(view().name("skill/skill-spec"));
		
		verify(getSpecsheetForDownloadService, times(1))
		.getSpecsheetForDownload(any(Integer.class), any(Integer.class), any(), any(Integer.class));
		verify(showSpecsheetController, times(1))
		.showSpecsheet(any(Integer.class), any(Model.class), any(LoginUser.class));
	}
	
	@Test
	public void TC3_開発経験を10個指定_createServiceでException() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		department.setStaffId("AP-202");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setEmployeeId(1111);
		specsheet.setUser(user);
		
		when(getSpecsheetForDownloadService
				.getSpecsheetForDownload(any(Integer.class), any(Integer.class), any(), any(Integer.class)))
		.thenReturn(specsheet);
		when(createSpecsheetXlsxService.createXlsx(any(AppSpecsheet.class))).thenThrow(new Exception());
		
		mockMvc.perform(post("/skill/download/xlsx")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("version", "1")
				.param("devExperienceIdList[0]", "1")
				.param("devExperienceIdList[1]", "2")
				.param("devExperienceIdList[2]", "3")
				.param("devExperienceIdList[3]", "4")
				.param("devExperienceIdList[4]", "5")
				.param("devExperienceIdList[5]", "6")
				.param("devExperienceIdList[6]", "7")
				.param("devExperienceIdList[7]", "8")
				.param("devExperienceIdList[8]", "9")
				.param("devExperienceIdList[9]", "10")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
				.andExpect(view().name("error/X00(500)"));
		
		verify(getSpecsheetForDownloadService, times(1))
		.getSpecsheetForDownload(any(Integer.class), any(Integer.class), any(), any(Integer.class));
		verify(createSpecsheetXlsxService, times(1)).createXlsx(any(AppSpecsheet.class));
	}
	
	@Test
	public void TC4_開発経験を10個指定_正常系() throws Exception {
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User user = webLoginUser.getUser();
		Department department = new Department();
		department.setName("web");
		department.setStaffId("AP-202");
		user.setDepartment(department);
		
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setEmployeeId(1111);
		specsheet.setUser(user);
		
		String path = System.getProperty("user.dir") + "/document/excel/AP_template.xlsx";
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		
		when(getSpecsheetForDownloadService
				.getSpecsheetForDownload(any(Integer.class), any(Integer.class), any(), any(Integer.class)))
		.thenReturn(specsheet);
		when(createSpecsheetXlsxService.createXlsx(any(AppSpecsheet.class))).thenReturn(workbook);
		
		mockMvc.perform(post("/skill/download/xlsx")
				.param("specsheetId", "1")
				.param("userId", "1")
				.param("version", "1")
				.param("devExperienceIdList[0]", "1")
				.param("devExperienceIdList[1]", "2")
				.param("devExperienceIdList[2]", "3")
				.param("devExperienceIdList[3]", "4")
				.param("devExperienceIdList[4]", "5")
				.param("devExperienceIdList[5]", "6")
				.param("devExperienceIdList[6]", "7")
				.param("devExperienceIdList[7]", "8")
				.param("devExperienceIdList[8]", "9")
				.param("devExperienceIdList[9]", "10")
				.with(csrf())
				.with(user(new LoginUser(user, authorityList))))
		.andExpect(status().isOk())
		.andExpect(header().string("content-disposition", "attachment; filename=\"AP-202-1111.xlsx\""))
				.andExpect(content()
						.contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8"))
		.andExpect(content().encoding("UTF-8"));
		
		verify(getSpecsheetForDownloadService, times(1))
		.getSpecsheetForDownload(any(Integer.class), any(Integer.class), any(), any(Integer.class));
		verify(createSpecsheetXlsxService, times(1)).createXlsx(any(AppSpecsheet.class));
	}

}
