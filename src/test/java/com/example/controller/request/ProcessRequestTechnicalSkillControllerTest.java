package com.example.controller.request;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.ApprovalAllTechnicalSkillForm;
import com.example.form.ApprovalTechnicalSkillForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.technique.GetTechnicalSkillByNameAndCategoryService;
import com.example.service.technique.GetTechnicalSkillListByTechnicalSkillIdListAndVersionListService;
import com.example.service.technique.GetTechnicalSkillService;
import com.example.service.technique.UpdateRequestTechnicalSkillService;
import com.example.service.technique.UpdateTechnicalSkillListStageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessRequestTechnicalSkillControllerTest {

	MockMvc mockMvc;
	
    @Autowired
    private AdminloginUser testUser;
	
    @Autowired
    protected WebApplicationContext wac;
	
	@MockBean
	private GetTechnicalSkillService getTechnicalSkillService;
	
	@MockBean
	private UpdateRequestTechnicalSkillService updateService;
	
	@MockBean
	private UpdateTechnicalSkillListStageService updateStageService;
	
	@MockBean
	private GetTechnicalSkillByNameAndCategoryService getByNameAndCategoryService;
	
	@MockBean
	private GetTechnicalSkillListByTechnicalSkillIdListAndVersionListService getTechnicalSkillListByListService;
	
	// ??????????????????????????????????????????
	@Autowired
	private ProcessRequestTechnicalSkillController target;
	
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		//springMVC??????????????????
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		//SpringSecurity??????????????????
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void ?????????????????????????????????????????????()throws Exception {
		Integer technicalSkillId = 1;
		String name="mac";
		Integer category=1;
		Integer version = 1;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setVersion(version);
		technicalSkill.setStage(Stage.REQUESTING.getKey());
		
		//??????????????????????????????
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// ???????????????
		when(getTechnicalSkillService.getTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
		when(updateService.updateRequestTechnicalSkill(any(TechnicalSkill.class))).thenReturn(1);
				
		// URL???html???param???????????????,loginUser??????????????????
				MockHttpServletRequestBuilder postRequest 
				= MockMvcRequestBuilders.post("/request/item/approval")
					.with(csrf())
					.with(user(new LoginUser(loginUser,authorityList)))
					.param("technicalSkillId",String.valueOf(technicalSkillId))
					.param("version", String.valueOf(version));
				
				//???????????????????????????
				MvcResult result = (MvcResult) mockMvc.perform(postRequest)
					.andExpect(SecurityMockMvcResultMatchers.authenticated())
					.andExpect(status().isFound())
					.andExpect(redirectedUrl("/request"))
					.andExpect(model().hasNoErrors())
					.andReturn();
							
				FlashMap flashMap=result.getFlashMap();
				ApprovalTechnicalSkillForm actualForm=(ApprovalTechnicalSkillForm)flashMap.get("approvalTechnicalSkillForm");
				String actualMessage=(String) flashMap.get("message");
				String flashMessage="???????????????????????????????????????????????????";
				
				//???????????????????????????????????????
				assertEquals(actualForm.getTechnicalSkillId(), technicalSkillId);
				assertEquals(actualForm.getVersion(), version);
				
				//???????????????????????????????????????????????????
				assertEquals(actualMessage, flashMessage);
				
				//Mock?????????????????????????????????????????????????????????
				verify(getTechnicalSkillService, times(1)).getTechnicalSkill(any(Integer.class));
				verify(updateService, times(1)).updateRequestTechnicalSkill(any(TechnicalSkill.class));
								
	}
	
	@Test
	public void ?????????????????????????????????????????????_????????????????????????_stage0??????()throws Exception {
		Integer technicalSkillId = 1;
		Integer version = 1;
		Integer testVersion=10;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		//version?????????????????????
		technicalSkill.setVersion(testVersion);
		//??????
		technicalSkill.setStage(Stage.ACTIVE.getKey());
		
		//??????????????????????????????
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// ???????????????
		when(getTechnicalSkillService.getTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
				
		// URL???html???param???????????????
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/approval")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("version", String.valueOf(version));
		
		//???????????????????????????
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
			.andExpect(SecurityMockMvcResultMatchers.authenticated())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/request"))
			.andExpect(model().hasNoErrors())
			.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		String actualMessage=(String) flashMap.get("failMessage");
		String expectedMessage = "????????????\nERROR:????????????????????????????????????????????????";
		
		//model????????????????????????
		assertEquals(actualMessage, expectedMessage);
		
		//Mock?????????????????????????????????????????????????????????
		verify(getTechnicalSkillService, times(1)).getTechnicalSkill(any(Integer.class));
		
	}
	
	@Test
	public void ?????????????????????????????????????????????_????????????????????????_stage0??????()throws Exception {
		Integer technicalSkillId = 1;
		Integer version = 1;
		Integer testVersion=10;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		//version?????????????????????
		technicalSkill.setVersion(testVersion);
		//????????????
		technicalSkill.setStage(Stage.REQUESTING.getKey());
		
		//??????????????????????????????
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();		
		
		// ???????????????
		when(getTechnicalSkillService.getTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
				
		// URL???html???param???????????????
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/approval")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("version", String.valueOf(version));
		
		//???????????????????????????
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
			.andExpect(SecurityMockMvcResultMatchers.authenticated())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/request"))
			.andExpect(model().hasNoErrors())
			.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		String actualMessage=(String) flashMap.get("failMessage");
		
		//model????????????????????????
		assertEquals(actualMessage, null);
		
		//Mock?????????????????????????????????????????????????????????
		verify(getTechnicalSkillService, times(1)).getTechnicalSkill(any(Integer.class));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void ?????????????????????????????????????????????_??????()throws Exception{
		Integer technicalSkillId = 1;
		Integer technicalSkillId2 = 2;
		Integer version = 1;
				
		//??????????????????
		TechnicalSkill technicalSkill = new TechnicalSkill();
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill2.setTechnicalSkillId(technicalSkillId2);
		technicalSkill.setVersion(version);
		technicalSkill2.setVersion(version);
		List <TechnicalSkill> technicalSkillList=new ArrayList<>();
		technicalSkillList.add(technicalSkill);
		technicalSkillList.add(technicalSkill2);
		
		//??????????????????????????????
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();		
		
		// ???????????????
		when(getTechnicalSkillListByListService.getTechnicalSkillListByTechnicalSkillIdListAndVersionList(any(List.class), any(List.class))).thenReturn(technicalSkillList);
		when(updateStageService.updateTechnicalSkillListStage(any(List.class),any(String.class), any(String.class), any(LocalDateTime.class))).thenReturn(2);
				
		// URL???html???param???????????????,loginUser??????????????????
				MockHttpServletRequestBuilder postRequest 
				= MockMvcRequestBuilders.post("/request/item/approval-all")
					.with(csrf())
					.with(user(new LoginUser(loginUser,authorityList)))
					.param("technicalSkillIdList[0]",String.valueOf(technicalSkillId))
					.param("versionList[0]", String.valueOf(version))
					.param("technicalSkillIdList[1]",String.valueOf(technicalSkillId2))
					.param("versionList[1]", String.valueOf(version));
				
				//???????????????????????????
				MvcResult result = (MvcResult) mockMvc.perform(postRequest)
					.andExpect(SecurityMockMvcResultMatchers.authenticated())
					.andExpect(status().isFound())
					.andExpect(redirectedUrl("/request"))
					.andExpect(model().hasNoErrors())
					.andReturn();
								
				FlashMap flashMap=result.getFlashMap();
				//?????????????????????????????????????????????
				ApprovalAllTechnicalSkillForm actualform=(ApprovalAllTechnicalSkillForm)flashMap.get("approvalAllTechnicalSkillForm");			
				//??????????????????????????????
				String actualMessage=(String) flashMap.get("message");
				String flashMessage="?????????????????????????????????????????????????????????";
				
				//?????????????????????????????????????????????
				assertEquals(actualform.getTechnicalSkillIdList().get(0), technicalSkillId);
				assertEquals(actualform.getTechnicalSkillIdList().get(1), technicalSkillId2);
				assertEquals(actualform.getVersionList().get(0), version);
				assertEquals(actualform.getVersionList().get(1), version);
				//???????????????????????????????????????????????????
				assertEquals(actualMessage, flashMessage);
				
				//Mock?????????????????????????????????????????????????????????
				verify(getTechnicalSkillListByListService, times(1)).getTechnicalSkillListByTechnicalSkillIdListAndVersionList(any(List.class), any(List.class));
				verify(updateStageService, times(1)).updateTechnicalSkillListStage(any(List.class),any(String.class), any(String.class), any(LocalDateTime.class));
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void ?????????????????????????????????????????????_??????()throws Exception{
		Integer technicalSkillId = 1;
		Integer technicalSkillId2 = 2;
		Integer version = 1;
				
		//??????????????????
		List <TechnicalSkill> technicalSkillList=new ArrayList<>();
		
		//??????????????????????????????
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();		
		
		// ???????????????
		when(getTechnicalSkillListByListService.getTechnicalSkillListByTechnicalSkillIdListAndVersionList(any(List.class), any(List.class))).thenReturn(technicalSkillList);
				
		// URL???html???param???????????????,loginUser??????????????????
				MockHttpServletRequestBuilder postRequest 
				= MockMvcRequestBuilders.post("/request/item/approval-all")
					.with(csrf())
					.with(user(new LoginUser(loginUser,authorityList)))
					.param("technicalSkillIdList[0]",String.valueOf(technicalSkillId))
					.param("versionList[0]", String.valueOf(version))
					.param("technicalSkillIdList[1]",String.valueOf(technicalSkillId2))
					.param("versionList[1]", String.valueOf(version));
				
				//???????????????????????????
				MvcResult result = (MvcResult) mockMvc.perform(postRequest)
					.andExpect(SecurityMockMvcResultMatchers.authenticated())
					.andExpect(status().isFound())
					.andExpect(redirectedUrl("/request"))
					.andExpect(model().hasNoErrors())
					.andReturn();
								
				FlashMap flashMap=result.getFlashMap();
				//?????????????????????????????????????????????
				ApprovalAllTechnicalSkillForm actualform=(ApprovalAllTechnicalSkillForm)flashMap.get("approvalAllTechnicalSkillForm");			
				//??????????????????????????????
				String actualMessage=(String) flashMap.get("failMessage");
				String flashMessage="????????????\nERROR:????????????????????????????????????????????????";
				System.out.println(actualform);
				
				//?????????????????????????????????????????????
				assertEquals(actualform.getTechnicalSkillIdList().get(0), technicalSkillId);
				assertEquals(actualform.getTechnicalSkillIdList().get(1), technicalSkillId2);
				assertEquals(actualform.getVersionList().get(0), version);
				assertEquals(actualform.getVersionList().get(1), version);
				//???????????????????????????????????????????????????
				assertEquals(actualMessage, flashMessage);
				
				//Mock?????????????????????????????????????????????????????????
				verify(getTechnicalSkillListByListService, times(1)).getTechnicalSkillListByTechnicalSkillIdListAndVersionList(any(List.class), any(List.class));
	
	}


}
