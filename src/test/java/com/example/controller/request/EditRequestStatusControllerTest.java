package com.example.controller.request;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateHadBasicSkillService;
import com.example.service.status.UpdateHadEngineerSkillService;
import com.example.service.status.UpdateHadPersonalityService;
import com.example.service.status.UpdateStatusService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditRequestStatusControllerTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@MockBean
	private GetStatusService getStatusService;
	
	@MockBean
	private GetPersonalityService getPersonalityService;
	
	@MockBean
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@MockBean
	private UpdateStatusService updateStatusService;
	
	@MockBean
	private UpdateHadBasicSkillService updateHadBasicSkillService;
	
	@MockBean
	private UpdateHadEngineerSkillService updateHadEngineerSkillService;
	
	@MockBean
	private UpdateHadPersonalityService updateHadPersonalityService; 
	
	@MockBean
	private DeleteStatusService deleteStatusService;
	
	@InjectMocks
	private EditRequestStatusController target;
	
	@Autowired
    private AdminloginUser adminTestUser;
	
	@Autowired
	private WebLoginUser webTestUser;
	
	@Autowired
	private SalesLoginUser salesTestUser;
	
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
	public void ??????????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		
		Status status = statusTestData();
		status.setStage(Stage.SENT_BACK.getKey());
		
		//??????????????????
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status); 
		when(getPersonalityService.getPersonality()).thenReturn(personalityListTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("version", String.valueOf(version));

		//??????????????????
		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-edit-status"))
				.andExpect(model().attribute("statusData", status))		
     			.andExpect(model().attribute("personalityList", personalityListTestData()))		
				.andReturn();
		
		//??????????????????
		verify(getStatusAndSkillsService, times(1)).getStatusAndSkillsByPrimaryKey(statusId);
		verify(getPersonalityService, times(1)).getPersonality();
	}
	
	@Test
	public void ????????????????????????????????????????????????????????????????????????403?????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = salesTestUser.getAuthorityList();
		User user = salesTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
				.with(user(loginUser))
				.with(csrf());
		
		mockMvc.perform(getRequest)
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	public void ????????????????????????????????????????????????????????????????????????() throws Throwable{
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ?????????????????????
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer userId = 9999;
			
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
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
	public void ???????????????0??????????????????????????????????????????????????????() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ?????????????????????
			Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
			User user = adminTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			Integer statusId = 1;
			Status status = statusTestData();
			status.setStage(Stage.ACTIVE.getKey());

			when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status);

			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
					.with(user(loginUser)).with(csrf()).param("statusId", String.valueOf(statusId));

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
	public void ??????????????????????????????????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		Integer statusId = 1;
		
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit")
				.with(user(loginUser))
				.with(csrf())
				.param("stage", String.valueOf(Stage.SENT_BACK.getKey()))
				.param("statusId", String.valueOf(statusId))
     			.param("version", String.valueOf(9999));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andReturn();
	}
	
	@Test
	public void ???????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.REQUESTING.getKey();
		Integer version = 1;
		String userComment = "???????????????";
		
		//??????????????????
    	when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");

		//??????????????????
		mockMvc.perform(getRequest)
     			.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andExpect(flash().attribute("editCompleted", "?????????????????????????????????????????????"))
				.andExpect(model().attribute("statusId", is(String.valueOf(statusId))))
				.andReturn();
		
		//??????????????????
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, stage, null);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
	}
	
	@Test
	public void ????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer stage = Stage.ACTIVE.getKey();
		Integer version = 1;
		String adminComment = "???????????????";
		
		//??????????????????
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusService.getStatusByStages(userId, stage, null)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(stage))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		//??????????????????
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request"))
				.andExpect(flash().attribute("message", is("??????????????????????????????????????????")))
				.andReturn();
				
		//??????????????????
		verify(getStatusService, times(1)).getStatusByPrimaryKey(statusId);
		verify(getStatusService, times(1)).getStatusByStages(userId, stage, null);
		verify(updateStatusService, times(1)).updateStatus(any());
		verify(updateHadBasicSkillService, times(1)).updateHadBasicSkill(any(), any());
		verify(updateHadEngineerSkillService, times(1)).updateHadEngineerSkill(any(), any());
		verify(updateHadPersonalityService, times(1)).upsertHadPersonality(any(), any());
		verify(sendMailAboutStatusByAdminService, times(1)).sendMailAboutStatusByAdmin(userId, stage, adminComment, statusId, "??????" + Stage.of(stage).getMessageForRequest());
		verify(deleteStatusService, times(1)).deleteStatusByUserId(userId);
	}
	
	@Test
	public void ??????????????????????????????????????????????????????????????????????????????() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ?????????????????????
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param????????????
			Integer userId = 9999;

			// param,path????????????
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
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
	public void ????????????1??????????????????????????????????????????????????????????????????() throws Throwable {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// ?????????????????????
			Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
			User user = webTestUser.getUser();
			LoginUser loginUser = new LoginUser(user, authorityList);

			// param????????????
			Integer userId = 1;

			// param,path????????????
			MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
					.with(user(loginUser)).with(csrf()).param("userId", String.valueOf(userId))
					.param("stage", String.valueOf(Stage.TEMPORARY.getKey()));

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
	public void version????????????????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 9999;
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("version", String.valueOf(version));
		
		mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/request/status?statusId=1"))
				.andExpect(model().attribute("statusId", is(String.valueOf(statusId))))
				.andExpect(flash().attribute("alertMessage", is("????????????\nERROR:??????????????????????????????????????????????????????")))
				.andReturn();
	}
	
	@Test
	public void ????????????????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = adminTestUser.getAuthorityList();
		User user = adminTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String adminComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.ACTIVE.getKey()))
				.param("version", String.valueOf(version))
				.param("adminComment", adminComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "adminComment"))
				.andExpect(view().name("request/request-edit-status"))
				.andReturn();
		
		// ?????????????????????????????????
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1?????????200??????????????????????????????";
		
		// ?????????????????????????????????
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void ??????????????????????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");;
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "userComment"))
				.andExpect(view().name("request/request-edit-status"))
				.andReturn();
		
		// ?????????????????????????????????
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1?????????200??????????????????????????????";
		
		// ?????????????????????????????????
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void personalityIdList???????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "5");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "personalityIdList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// ?????????????????????????????????
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "1???????????????????????????????????????";
				
				// ?????????????????????????????????
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void hadBasicSkillList???????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].hadBasicSkillId", "1");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// ?????????????????????????????????
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "1??????5????????????????????????????????????";
				
				// ?????????????????????????????????
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void ????????????????????????????????????0?????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "0");
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// ?????????????????????????????????
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "1??????5????????????????????????????????????";
				
				// ?????????????????????????????????
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void ????????????????????????????????????6?????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "100")
				.param("hadBasicSkillList[0].score", "6");
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("statusForm", "hadBasicSkillList"))
				.andExpect(view().name("request/request-edit-status"))
				.andReturn();
		
		// ?????????????????????????????????
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1??????5????????????????????????????????????";
		
		// ?????????????????????????????????
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void hadEngineerSkillList???????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].hadEngineerSkillId", "1")
				.param("hadBasicSkillList[0].score", "3");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// ?????????????????????????????????
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "0??????100????????????????????????????????????";
				
				// ?????????????????????????????????
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void ?????????????????????????????????????????????????????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "-1")
				.param("hadBasicSkillList[0].score", "3");;
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// ?????????????????????????????????
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "0??????100????????????????????????????????????";
				
				// ?????????????????????????????????
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@Test
	public void ?????????????????????????????????????????????101?????????????????????() throws Exception {
		//?????????????????????
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User user = webTestUser.getUser();
		LoginUser loginUser = new LoginUser(user, authorityList);
		
		//param????????????
		Integer userId = 1;
		Integer statusId = 1;
		Integer version = 1;
		String userComment = "?????????";
		
		when(getStatusService.getStatusByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		when(getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId)).thenReturn(statusTestData()); 
		
		//param,path????????????
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/request/status/edit/do")
				.with(user(loginUser))
				.with(csrf())
				.param("userId", String.valueOf(userId))
				.param("statusId", String.valueOf(statusId))
				.param("stage", String.valueOf(Stage.REQUESTING.getKey()))
				.param("version", String.valueOf(version))
				.param("userComment", userComment)
				.param("personalityIdList", personalityIdListTestData().get(0))
				.param("hadEngineerSkillList[0].score", "101")
				.param("hadBasicSkillList[0].score", "3");
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("statusForm", "hadEngineerSkillList"))
						.andExpect(view().name("request/request-edit-status"))
						.andReturn();
				
				// ?????????????????????????????????
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.statusForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "0??????100????????????????????????????????????";
				
				// ?????????????????????????????????
				assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	public Status statusTestData() {
		Department department = new Department();
		department.setName("?????????");
		User user = new User();
		user.setDepartment(department);
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setStage(2);
		status.setVersion(1);
		status.setUser(user);
		status.setAdminComment("???????????????");
		return status;
	}
	
	public List<Personality> personalityListTestData(){
		List<Personality> personalityList = new ArrayList<>();
		Personality personality = new Personality();
		personality.setPersonalityId(1);
		personality.setName("?????????1");
		personalityList.add(personality);
		return personalityList;
	}
	
	public List<String> personalityIdListTestData(){
		List<String> personalityIdList = new ArrayList<>();
		personalityIdList.add("1");
		return personalityIdList;
	}

}
