package com.example.controller.skill;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.web.servlet.FlashMap;

import com.example.common.Stage;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.EditHadTechnicalSkillForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.technique.GetHadTechnicalSkillListService;
import com.example.service.technique.UpdateHadTechnicalSkillService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditHadTechnicalSkillListControllerTest {
	
	private MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private WebLoginUser webLoginUser;	
    
    @Autowired
    private AdminloginUser adminloginUser;	
    
    @Autowired
    private SalesLoginUser salesLoginUser;	
    
    @MockBean
	private GetHadTechnicalSkillListService getHadTechnicalSkillListService;

	@MockBean
	private UpdateHadTechnicalSkillService updateHadTechnicalSkillService;
    
	@InjectMocks
	private EditHadTechnicalSkillListController target;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 所有テクニカルスキル編集画面を表示する_所有テクニカルスキルがある場合() throws Exception {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill=new HadTechnicalSkill();
		TechnicalSkill technicalSkill=new TechnicalSkill();
		HadTechnicalSkill hadTechnicalSkill2=new HadTechnicalSkill();
		TechnicalSkill technicalSkill2=new TechnicalSkill();
		HadTechnicalSkill hadTechnicalSkill3=new HadTechnicalSkill();
		TechnicalSkill technicalSkill3=new TechnicalSkill();
		HadTechnicalSkill hadTechnicalSkill4=new HadTechnicalSkill();
		TechnicalSkill technicalSkill4=new TechnicalSkill();
		HadTechnicalSkill hadTechnicalSkill5=new HadTechnicalSkill();
		TechnicalSkill technicalSkill5=new TechnicalSkill();
		HadTechnicalSkill hadTechnicalSkill6=new HadTechnicalSkill();
		TechnicalSkill technicalSkill6=new TechnicalSkill();
		HadTechnicalSkill hadTechnicalSkill7=new HadTechnicalSkill();
		TechnicalSkill technicalSkill7=new TechnicalSkill();
		List<HadTechnicalSkill> osList = new ArrayList<>();
		List<HadTechnicalSkill> languageList = new ArrayList<>();
		List<HadTechnicalSkill> frameworkList = new ArrayList<>();
		List<HadTechnicalSkill> libraryList = new ArrayList<>();
		List<HadTechnicalSkill> middlewareList = new ArrayList<>();
		List<HadTechnicalSkill> toolList = new ArrayList<>();
		List<HadTechnicalSkill> devProcessList = new ArrayList<>();
		technicalSkill.setCategory(1);
		hadTechnicalSkill.setUserId(1);
		hadTechnicalSkill.setTechnicalSkillId(1);
		hadTechnicalSkill.setUsingPeriod(12);
		hadTechnicalSkill.setTechnicalSkill(technicalSkill);
		hadTechnicalSkill.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkill.setCreator("tester");
		hadTechnicalSkill.setCreatedAt(LocalDateTime.now());
		hadTechnicalSkill.setUpdater("tester");
		hadTechnicalSkill.setUpdatedAt(LocalDateTime.now());
		hadTechnicalSkillList.add(hadTechnicalSkill);
		osList.add(hadTechnicalSkill);
		technicalSkill2.setCategory(2);
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		languageList.add(hadTechnicalSkill2);
		technicalSkill3.setCategory(3);
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		frameworkList.add(hadTechnicalSkill3);
		technicalSkill4.setCategory(4);
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		libraryList.add(hadTechnicalSkill4);
		technicalSkill5.setCategory(5);
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		middlewareList.add(hadTechnicalSkill5);
		technicalSkill6.setCategory(6);
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		toolList.add(hadTechnicalSkill6);
		technicalSkill7.setCategory(7);
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		devProcessList.add(hadTechnicalSkill7);


		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=webLoginUser.getAuthorityList();
		User loginUser=webLoginUser.getUser();

		
		//サービスを呼んだときの戻り値定義
		doReturn(hadTechnicalSkillList).when(getHadTechnicalSkillListService).getHadTechnicalSkillListOfStage0or1or2(any());
		
		//テスト対象メソッド実行
		mockMvc.perform(get("/skill/technique/edit")
		.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isOk())
		.andExpect(view().name("skill/skill-edit-technique"))
		.andExpect(model().attribute("osList",osList))
		.andExpect(model().attribute("languageList",languageList))
		.andExpect(model().attribute("frameworkList",frameworkList))
		.andExpect(model().attribute("libraryList",libraryList))
		.andExpect(model().attribute("middlewareList",middlewareList))
		.andExpect(model().attribute("toolList",toolList))
		.andExpect(model().attribute("devProcessList",devProcessList))
		.andExpect(model().attribute("osListSize",1))
		.andExpect(model().attribute("languageListSize",1))
		.andExpect(model().attribute("frameworkListSize",1))
		.andExpect(model().attribute("libraryListSize",1))
		.andExpect(model().attribute("middlewareListSize",1))
		.andExpect(model().attribute("toolListSize",1))
		.andExpect(model().attribute("devProcessListSize",1))
		.andExpect(model().size(15));
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(1)).getHadTechnicalSkillListOfStage0or1or2(any());
	}
	
	@Test
	public void 所有テクニカルスキル編集画面を表示する_所有テクニカルスキルが0件の場合() throws Exception {
		
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=webLoginUser.getAuthorityList();
		User loginUser=webLoginUser.getUser();
		
		//サービスを呼んだときの引数定義
		doReturn(hadTechnicalSkillList).when(getHadTechnicalSkillListService).getHadTechnicalSkillListOfStage0or1or2(any());
		
		//テスト対象メソッド実行
		MvcResult result=mockMvc.perform(get("/skill/technique/edit")
		.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isOk())
		.andExpect(model().attribute("totalSize",0))
		.andExpect(model().size(2))
		.andExpect(view().name("skill/skill-edit-technique"))
		.andReturn();
		
		//modelの値検証
		assertEquals(result.getModelAndView().getModel().get("totalSize"), 0);
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(1)).getHadTechnicalSkillListOfStage0or1or2(any());
	}
	
	@Test
	public void 所有テクニカルスキル編集画面を表示する_ログイン者管理者の場合() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=adminloginUser.getAuthorityList();
		User loginUser=adminloginUser.getUser();
		
		//テスト対象メソッド実行
		mockMvc.perform(get("/skill/technique/edit")
				.with(user(new LoginUser(loginUser,authorityList))))
				.andExpect(status().isForbidden())
				.andReturn();
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(0)).getHadTechnicalSkillListOfStage0or1or2(any());
	}
	
	@Test
	public void 所有テクニカルスキル編集画面を表示する_ログイン者営業の場合() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=salesLoginUser.getAuthorityList();
		User loginUser=salesLoginUser.getUser();
		
		//テスト対象メソッド実行
		mockMvc.perform(get("/skill/technique/edit")
				.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isForbidden())
		.andReturn();
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(0)).getHadTechnicalSkillListOfStage0or1or2(any());
	}
	
	@Test
	public void 所有テクニカルスキル利用歴を更新する() throws Exception {

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=webLoginUser.getAuthorityList();
		User loginUser=webLoginUser.getUser();
		
		//テスト対象メソッド実行
		MockHttpServletRequestBuilder getRequest=MockMvcRequestBuilders.post("/skill/technique/edit/do")
	    .with(csrf())
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("hadTechnicalSkillList[0].usingPeriod", "5")
		.param("hadTechnicalSkillList[1].usingPeriod", "8");

		MvcResult result =  mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())				
				.andExpect(redirectedUrl("/skill/technique"))
				.andReturn();
		
	
		//スコープからformオブジェクトを取得
		FlashMap flashMap=result.getFlashMap();
		String actualMessage=(String) flashMap.get("message");
		EditHadTechnicalSkillForm actualForm=(EditHadTechnicalSkillForm) flashMap.get("EditHadTechnicalSkillForm");
		
		//modelの値検証
		assertEquals("テクニカルスキル利用歴を更新しました",actualMessage);
		assertEquals("5", String.valueOf(actualForm.getHadTechnicalSkillList().get(0).getUsingPeriod()));
		assertEquals("8", String.valueOf(actualForm.getHadTechnicalSkillList().get(1).getUsingPeriod()));
		
		//サービス実行回数検証
		verify(updateHadTechnicalSkillService, times(1)).updateUsingPeriodByHadTechnicalSkillList(any(), any());
	}
	

}
