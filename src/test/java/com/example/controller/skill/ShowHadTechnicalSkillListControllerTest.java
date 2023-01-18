package com.example.controller.skill;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.Department;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.GetStatusService;
import com.example.service.technique.GetHadTechnicalSkillListService;
import com.example.service.user.GetSpecificUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowHadTechnicalSkillListControllerTest {
	
	private MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private WebLoginUser WebLoginUser;	
    
    @Autowired
    private AdminloginUser adminloginUser;	
    
    @Autowired
    private SalesLoginUser salesLoginUser;	
    
	@MockBean
	private GetHadTechnicalSkillListService getHadTechnicalSkillListService;
	
	@MockBean
	private GetStatusService getStatusService;
	
	@MockBean
	private GetSpecificUserService getSpecificUserService;

	@InjectMocks
	private ShowHadTechnicalSkillListController target;

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
	public void 所有テクニカルスキル詳細画面を表示する_ログイン者WEB_スキルの登録0件の場合() throws Exception {

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=WebLoginUser.getAuthorityList();
		User loginUser=WebLoginUser.getUser();
		Department department=new Department();
		department.setName("WEBアプリケーション開発");
		loginUser.setDepartment(department);
		
		Status status=new Status();
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		
		//サービスを呼んだときの引数定義
		doReturn(status).when(getStatusService).getStatusByStages(any(), any(), any());
		doReturn(loginUser).when(getSpecificUserService).get(any());
		doReturn(hadTechnicalSkillList).when(getHadTechnicalSkillListService).getHadTechnicalSkillListOfStage0or1or2(any());
		
		//テスト対象メソッド実行
		MvcResult result=mockMvc.perform(get("/skill/technique")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("userId", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("skill/skill-technique"))
		.andReturn();
		
		//modelの中身を検証
		assertEquals(result.getModelAndView().getModel().get("noHadTechnicalSkill"), "登録されたテクニカルスキルはありません");
		assertEquals(result.getModelAndView().getModel().get("user"), loginUser);
		assertEquals(result.getModelAndView().getModel().get("status"), status);
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(1)).getHadTechnicalSkillListOfStage0or1or2(any());
		verify(getStatusService, times(1)).getStatusByStages(any(), any(), any());
	    verify(getSpecificUserService, times(1)).get(any());
	}
	
	@Test
	public void 所有テクニカルスキル詳細画面を表示する_ログイン者WEB_スキルの登録がある場合() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=WebLoginUser.getAuthorityList();
		User loginUser=WebLoginUser.getUser();
		Department department=new Department();
		department.setName("WEBアプリケーション開発");
		loginUser.setDepartment(department);
		
		Status status=new Status();
		
		//全カテゴリを格納する
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill1=new HadTechnicalSkill();
		TechnicalSkill technicalSkill1=new TechnicalSkill();
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
		technicalSkill1.setCategory(1);
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkill1.setUpdatedAt(LocalDateTime.now());
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		osList.add(hadTechnicalSkill1);
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
		
		//サービスを呼んだときの引数定義
		doReturn(status).when(getStatusService).getStatusByStages(any(), any(), any());
		doReturn(loginUser).when(getSpecificUserService).get(any());
		doReturn(hadTechnicalSkillList).when(getHadTechnicalSkillListService).getHadTechnicalSkillListOfStage0or1or2(any());
		
		//テスト対象メソッド実行
		MvcResult result=mockMvc.perform(get("/skill/technique")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-technique"))
				.andReturn();
		
		//時間比較の準備
		LocalDateTime expectedDate=LocalDateTime.now(); 
		LocalDateTime actualDate=(LocalDateTime) result.getModelAndView().getModel().get("latestUpdatedAt");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		//modelの中身を検証
		assertEquals(result.getModelAndView().getModel().get("user"), loginUser);
		assertEquals(result.getModelAndView().getModel().get("status"), status);
		assertEquals(java.sql.Date.valueOf(actualDate.format(dateTimeFormatter)), java.sql.Date.valueOf(expectedDate.format(dateTimeFormatter)));
		assertEquals(result.getModelAndView().getModel().get("osList"), osList);
		assertEquals(result.getModelAndView().getModel().get("languageList"), languageList);
		assertEquals(result.getModelAndView().getModel().get("frameworkList"), frameworkList);
		assertEquals(result.getModelAndView().getModel().get("libraryList"), libraryList);
		assertEquals(result.getModelAndView().getModel().get("middlewareList"), middlewareList);
		assertEquals(result.getModelAndView().getModel().get("toolList"), toolList);
		assertEquals(result.getModelAndView().getModel().get("devProcessList"), devProcessList);
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(1)).getHadTechnicalSkillListOfStage0or1or2(any());
		verify(getStatusService, times(1)).getStatusByStages(any(), any(), any());
		verify(getSpecificUserService, times(1)).get(any());
	}
	
	
	@Test
	public void 所有テクニカルスキル詳細画面を表示する_ログイン者管理者の場合() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=adminloginUser.getAuthorityList();
		User loginUser=adminloginUser.getUser();
		//エンジニアの情報
		User engineerUser= new User();
		engineerUser.setUserId(1);
		engineerUser.setName("Webエンジニア次郎");
		Department department=new Department();
		department.setName("WEBアプリケーション開発");
		engineerUser.setDepartment(department);
		
		Status status=new Status();
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		
		//サービスを呼んだときの引数定義
		doReturn(status).when(getStatusService).getStatusByStages(any(), any(), any());
		doReturn(engineerUser).when(getSpecificUserService).get(any());
		doReturn(hadTechnicalSkillList).when(getHadTechnicalSkillListService).getHadTechnicalSkillListOfStage0or1or2(any());
		
		//テスト対象メソッド実行
		MvcResult result=mockMvc.perform(get("/skill/technique")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("skill/skill-technique"))
		.andReturn();
		
		//modelの中身を検証
		assertEquals(result.getModelAndView().getModel().get("noHadTechnicalSkill"), "登録されたテクニカルスキルはありません");
		assertEquals(result.getModelAndView().getModel().get("user"), engineerUser);
		assertEquals(result.getModelAndView().getModel().get("status"), status);
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(1)).getHadTechnicalSkillListOfStage0or1or2(any());
		verify(getStatusService, times(1)).getStatusByStages(any(), any(), any());
		verify(getSpecificUserService, times(1)).get(any());
	}
	
	@Test
	public void 所有テクニカルスキル詳細画面を表示する_ログイン者営業の場合() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=salesLoginUser.getAuthorityList();
		User loginUser=salesLoginUser.getUser();
		//エンジニアの情報
		User engineerUser= new User();
		engineerUser.setUserId(1);
		engineerUser.setName("Webエンジニア次郎");
		Department department=new Department();
		department.setName("WEBアプリケーション開発");
		engineerUser.setDepartment(department);
		
		Status status=new Status();
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		
		//サービスを呼んだときの引数定義
		doReturn(status).when(getStatusService).getStatusByStages(any(), any(), any());
		doReturn(engineerUser).when(getSpecificUserService).get(any());
		doReturn(hadTechnicalSkillList).when(getHadTechnicalSkillListService).getHadTechnicalSkillListOfStage0or1or2(any());
		
		//テスト対象メソッド実行
		MvcResult result=mockMvc.perform(get("/skill/technique")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("userId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-technique"))
				.andReturn();
		
		//modelの中身を検証
		assertEquals(result.getModelAndView().getModel().get("noHadTechnicalSkill"), "登録されたテクニカルスキルはありません");
		assertEquals(result.getModelAndView().getModel().get("user"), engineerUser);
		assertEquals(result.getModelAndView().getModel().get("status"), status);
		
		//サービス実行回数検証
		verify(getHadTechnicalSkillListService, times(1)).getHadTechnicalSkillListOfStage0or1or2(any());
		verify(getStatusService, times(1)).getStatusByStages(any(), any(), any());
		verify(getSpecificUserService, times(1)).get(any());
	}

}
