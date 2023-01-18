package com.example.controller.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import com.example.domain.BasicSkill;
import com.example.domain.EngineerSkill;
import com.example.domain.LoginUser;
import com.example.domain.Template;
import com.example.domain.User;
import com.example.form.RegisterTemplateForm;
import com.example.form.SelectTemplateForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.status.GetBasicSkillListService;
import com.example.service.status.GetEngineerSkillListService;
import com.example.service.template.AddTemplateService;
import com.example.service.template.GetTemplateForRegisterService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterTemplateControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private AdminloginUser adminLoginUser;
	
    @Autowired
    protected WebApplicationContext context;	
    
	@MockBean
	private GetEngineerSkillListService getEngineerSkillService;
	
	@MockBean
	private GetBasicSkillListService getBasicSkillService;
	
	@MockBean
	private GetTemplateForRegisterService getTemplateForRegisterService;
	
	@MockBean
	private AddTemplateService addTemplateService;
	
	@Autowired
	private RegisterTemplateController target;
	
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		// SpringMvcの動作を再現
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		// SpringSecurityを適用させる 
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	@Test
	public void テンプレート登録画面を表示する() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template/register")
				.with(user(new LoginUser(loginUser, authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeExists("registerTemplateForm"))
			.andExpect(view().name("skill/skill-register-template"));
	}
	
	@Test
	public void テンプレートを登録する_正常処理() throws Exception {
		// RegisterTemplateFormの要素を作成
		Integer departmentId = 1;
		String name = "新テストテンプレート";
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("50","50","50","50","50","50","50"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","3","3","3","3","3"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		List<Template> templateList = new ArrayList<>();
		when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
		when(addTemplateService.registerTemplate(any(RegisterTemplateForm.class), anyString())).thenReturn(13);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/register/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("departmentId", String.valueOf(departmentId))
				.param("name", name)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
				
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();
		
		FlashMap flashMap = result.getFlashMap();
		String actualMessage = (String) flashMap.get("additionCompleted");
		String expectedMessage = "テンプレートの登録が完了しました";
		SelectTemplateForm selectTemplateForm = (SelectTemplateForm) flashMap.get("selectTemplateForm");
		Integer actualTemplateId = selectTemplateForm.getTemplateId();
		
		// アラートメッセージの検証
		assertEquals(actualMessage, expectedMessage);
		assertEquals(actualTemplateId, 13);
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateForRegisterService, times(1)).getTemplateForRegister(anyInt(), anyString());
		verify(addTemplateService, times(1)).registerTemplate(any(RegisterTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを登録する_同一所属内に同じ名前のテンプレートが存在するためエラーを返す() throws Exception {
		// RegisterTemplateFormの要素を作成
		Integer departmentId = 1;
		String name = "新テストテンプレート";
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("50","50","50","50","50","50","50"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","3","3","3","3","3"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		List<Template> templateList = new ArrayList<>();
		Template template = new Template();
		templateList.add(template);
		when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/register/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("departmentId", String.valueOf(departmentId))
				.param("name", name)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("registerTemplateForm", "name"))
				.andExpect(view().name("skill/skill-register-template"))
				.andExpect(model().attributeExists("registerTemplateForm"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "このテンプレート名は既に使用されています";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
		// モック化したメソッドの呼び出された回数を検証
		verify(getTemplateForRegisterService, times(1)).getTemplateForRegister(anyInt(), anyString());
		verify(addTemplateService, times(0)).registerTemplate(any(RegisterTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを登録する_テンプレートエンジニアスキルに未入力の項目が存在するためエラーを返す() throws Exception {
		// RegisterTemplateFormの要素を作成
		Integer departmentId = 1;
		String name = "新テストテンプレート";
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("50","50","50","50","50", null, null));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","3","3","3","3","3"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		List<Template> templateList = new ArrayList<>();
		when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/register/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("departmentId", String.valueOf(departmentId))
				.param("name", name)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(2))
				.andExpect(model().attributeHasFieldErrors("registerTemplateForm", "templateEngineerSkillScoreList"))
				.andExpect(view().name("skill/skill-register-template"))
				.andExpect(model().attributeExists("registerTemplateForm"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から100の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
		// モック化したメソッドの呼び出された回数を検証
		verify(getTemplateForRegisterService, times(1)).getTemplateForRegister(anyInt(), anyString());
		verify(addTemplateService, times(0)).registerTemplate(any(RegisterTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを登録する_テンプレート基本スキルに未入力の項目が存在するためエラーを返す() throws Exception {
		// RegisterTemplateFormの要素を作成
		Integer departmentId = 1;
		String name = "新テストテンプレート";
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("50","50","50","50","50","50","50"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList(null, null, null,"3","3","3"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		List<Template> templateList = new ArrayList<>();
		when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/register/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("departmentId", String.valueOf(departmentId))
				.param("name", name)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(3))
				.andExpect(model().attributeHasFieldErrors("registerTemplateForm", "templateBasicSkillScoreList"))
				.andExpect(view().name("skill/skill-register-template"))
				.andExpect(model().attributeExists("registerTemplateForm"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から5の数字を入力してください";
		
		// エラーメッセージの確認
		assertEquals(actualErrorMessage, expectedErrorMessage);
		// モック化したメソッドの呼び出された回数を検証
		verify(getTemplateForRegisterService, times(1)).getTemplateForRegister(anyInt(), anyString());
		verify(addTemplateService, times(0)).registerTemplate(any(RegisterTemplateForm.class), anyString());
	}	
	
	@Test
	public void テンプレートを登録する_テンプレート名が全て全角スペースの場合にエラーを返す() throws Exception {
		// RegisterTemplateFormの要素を作成
				Integer departmentId = 1;
				String name = "　　";
				// 下記paramsで使用するためのMultiValueMapを準備
				MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
				MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
				MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
				MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
				
				engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
				templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("50","50","50","50","50","50","50"));
				basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
				templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","3","3","3","3","3"));
				
				// ログイン認証用の情報
				Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
				User loginUser = adminLoginUser.getUser();
				
				// モック生成
				List<Template> templateList = new ArrayList<>();
				
				when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
				
				// URL,html,paramなどを設定
				MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/register/do")
						.with(user(new LoginUser(loginUser, authorityList)))
						.with(csrf())
						.param("departmentId", String.valueOf(departmentId))
						.param("name", name)
						.params(engineerSkillIdListMap)
						.params(templateEngineerSkillScoreListMap)
						.params(basicSkillIdListMap)
						.params(templateBasicSkillScoreListMap);
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("registerTemplateForm", "name"))
						.andExpect(view().name("skill/skill-register-template"))
						.andExpect(model().attributeExists("registerTemplateForm"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTemplateForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "テンプレート名を入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
				// モック化したメソッドの呼び出された回数を検証
				verify(getTemplateForRegisterService, times(1)).getTemplateForRegister(anyInt(), anyString());
				verify(addTemplateService, times(0)).registerTemplate(any(RegisterTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを登録する_テンプレート名が31文字以上の場合にエラーを返す() throws Exception {
		// RegisterTemplateFormの要素を作成
				Integer departmentId = 1;
				String name = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめも";
				// 下記paramsで使用するためのMultiValueMapを準備
				MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
				MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
				MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
				MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
				
				engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
				templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("50","50","50","50","50","50","50"));
				basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
				templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","3","3","3","3","3"));
				
				// ログイン認証用の情報
				Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
				User loginUser = adminLoginUser.getUser();
				
				// モック生成
				List<Template> templateList = new ArrayList<>();
				
				when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
				
				// URL,html,paramなどを設定
				MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/register/do")
						.with(user(new LoginUser(loginUser, authorityList)))
						.with(csrf())
						.param("departmentId", String.valueOf(departmentId))
						.param("name", name)
						.params(engineerSkillIdListMap)
						.params(templateEngineerSkillScoreListMap)
						.params(basicSkillIdListMap)
						.params(templateBasicSkillScoreListMap);
				
				MvcResult result = mockMvc.perform(getRequest)
						.andExpect(status().isOk())
						.andExpect(model().hasErrors())
						.andExpect(model().errorCount(1))
						.andExpect(model().attributeHasFieldErrors("registerTemplateForm", "name"))
						.andExpect(view().name("skill/skill-register-template"))
						.andExpect(model().attributeExists("registerTemplateForm"))
						.andReturn();
				
				// エラーメッセージを取得
				BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTemplateForm");
				List<FieldError> errorList = errorObject.getFieldErrors();
				String actualErrorMessage = errorList.get(0).getDefaultMessage();
				String expectedErrorMessage = "30文字以内で入力してください";
				
				// エラーメッセージの確認
				assertEquals(actualErrorMessage, expectedErrorMessage);
				// モック化したメソッドの呼び出された回数を検証
				verify(getTemplateForRegisterService, times(1)).getTemplateForRegister(anyInt(), anyString());
				verify(addTemplateService, times(0)).registerTemplate(any(RegisterTemplateForm.class), anyString());
	}
	
	@Test
	public void getEngineerSkillListメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数の設定
		Integer departmentId = 1;
		
		// モック生成
		EngineerSkill engineerSkillMock = new EngineerSkill();
		engineerSkillMock.setEngineerSkillId(1);
		engineerSkillMock.setName("サンプル");
		List<EngineerSkill> engineerSkillListMock = Arrays.asList(engineerSkillMock);
		
		when(getEngineerSkillService.getEngineerSkillList(anyInt())).thenReturn(engineerSkillListMock);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/skill/template/register/get_enginner_skill_list")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("departmentId", String.valueOf(departmentId));
		
		// パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.engineerSkillList[0].engineerSkillId").value(1))
			.andExpect(jsonPath("$.engineerSkillList[0].name").value("サンプル"));
	}
	
	@Test
	public void getBasicSkillListメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数の設定
		Integer departmentId = 1;
		
		// モック生成
		BasicSkill basicSkillMock = new BasicSkill();
		basicSkillMock.setBasicSkillId(1);
		basicSkillMock.setName("サンプル");
		List<BasicSkill> basicSkillListMock = Arrays.asList(basicSkillMock);
		
		when(getBasicSkillService.getBasicSkillList(anyInt())).thenReturn(basicSkillListMock);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/skill/template/register/get_basic_skill_list")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("departmentId", String.valueOf(departmentId));
		
		// パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.basicSkillList[0].basicSkillId").value(1))
			.andExpect(jsonPath("$.basicSkillList[0].name").value("サンプル"));
	}
	
	@Test
	public void templateListのsizeが0の場合のcheckDuplicateForRegisterTemplateメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数の設定
		Integer departmentId = 1;
		String name = "サンプル";
		
		// モック生成
		List<Template> templateList = new ArrayList<>();
		when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/skill/template/register/check_duplicate")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("departmentId", String.valueOf(departmentId))
		.param("name", name);
		
		// パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("このテンプレート名は有効です"));
	}
	
	@Test
	public void templateListのsizeが0ではない場合のcheckDuplicateForRegisterTemplateメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数の設定
		Integer departmentId = 1;
		String name = "サンプル";
		
		// モック生成
		Template template = new Template();
		List<Template> templateList = Arrays.asList(template);
		when(getTemplateForRegisterService.getTemplateForRegister(anyInt(), anyString())).thenReturn(templateList);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/skill/template/register/check_duplicate")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("departmentId", String.valueOf(departmentId))
		.param("name", name);
		
		// パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("このテンプレート名は既に使用されています"));
	}
}
