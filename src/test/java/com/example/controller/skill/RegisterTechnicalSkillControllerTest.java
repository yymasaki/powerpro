package com.example.controller.skill;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import com.example.common.Category;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.RegisterTechnicalSkillListForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.technique.AddTechnicalSkillListService;
import com.example.service.technique.GetTechnicalSkillByNameAndCategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class RegisterTechnicalSkillControllerTest {

	MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    private AdminloginUser testUser;
    
	//モック化する
	@MockBean
	private AddTechnicalSkillListService addTechnicalSkillListService;
	
	@MockBean
	private GetTechnicalSkillByNameAndCategoryService getByNameAndCategoryService;
	
	//モックオブジェクトの挿入対象
	@Autowired
	private RegisterTechnicalSkillController target;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		//springMVCの動作を再現
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		//SpringSecurityを適用させる
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
		
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 新規テクニカルスキル登録画面を表示する() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		MvcResult result = mockMvc.perform(get("/skill/item/register")
				.with(user(new LoginUser(loginUser,authorityList))))
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("skill/skill-register-item"))
				.andReturn();

		Map<Integer, String> expectedSkillMap = new LinkedHashMap<>();
		expectedSkillMap.put(Category.OS.getKey(), Category.OS.getName());
		expectedSkillMap.put(Category.LANGUAGE.getKey(), Category.LANGUAGE.getName());
		expectedSkillMap.put(Category.FRAMEWORK.getKey(), Category.FRAMEWORK.getName());
		expectedSkillMap.put(Category.LIBRARY.getKey(), Category.LIBRARY.getName());
		expectedSkillMap.put(Category.MIDDLEWARE.getKey(), Category.MIDDLEWARE.getName());
		expectedSkillMap.put(Category.TOOL.getKey(), Category.TOOL.getName());
		expectedSkillMap.put(Category.PROCESS.getKey(), Category.PROCESS.getName());

		@SuppressWarnings("unchecked")
		Map<Integer, String> actualSkillMap = (Map<Integer, String>) result.getModelAndView().getModel()
				.get("skillMap");
		
		//メッセージの検証
		assertEquals(actualSkillMap, expectedSkillMap);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void テクニカルスキルを登録する() throws Exception {
		
		String name="testdata";
		String name2="testdata2";
		Integer category=1;
		Integer category2=2;
		Integer adminId=14;
		String adminName="管理者次郎";

		LocalDateTime updatedAt = LocalDateTime.of(2020,7,1,12,00,00);
		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, category, adminId, 0, adminName, updatedAt, adminName,updatedAt,1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill(null,name2, category2, adminId, 0, adminName, updatedAt, adminName,updatedAt,1);
		
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(technicalSkill);
		technicalSkillList.add(technicalSkill2);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();

		//モック生成
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(null);
		when(addTechnicalSkillListService.addTechnicalSkillList(any(List.class))).thenReturn(technicalSkillList);
		
		//URL、html、paramなどを設定
		MockHttpServletRequestBuilder postRequest=
				MockMvcRequestBuilders.post("/skill/item/register/do")
				.with(user(new LoginUser(loginUser,authorityList)))
				.with(csrf())
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("technicalSkillList[1].name",name2)
				.param("technicalSkillList[1].category",String.valueOf(category2))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
		
		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())				
				.andExpect(redirectedUrl("/skill/item/register"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		RegisterTechnicalSkillListForm actualForm=(RegisterTechnicalSkillListForm)flashMap.get("checkForm");
		String actualMessage=(String) flashMap.get("message");
		String flashMessage="テクニカルスキルの登録が完了しました";

		//リクエストパラメータの値を検証
		assertEquals(actualForm.getTechnicalSkillList().get(0).getName(), name);
		assertEquals(actualForm.getTechnicalSkillList().get(0).getCategory(), category);
		assertEquals(actualForm.getTechnicalSkillList().get(1).getName(), name2);
		assertEquals(actualForm.getTechnicalSkillList().get(1).getCategory(), category2);
		
		//メッセージの検証
		assertEquals(actualMessage, flashMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(2)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));
		verify(addTechnicalSkillListService, times(1)).addTechnicalSkillList(any(List.class));
		
	}
	
	
					
	@Test			
	public void テクニカルスキルを登録する_バリデーション_空欄() throws Exception{			
		String name="";		
		Integer category=1;		
		Integer adminId=14;
		String adminName="管理者次郎";
				
		TechnicalSkill technicalSkill=new TechnicalSkill();		
		technicalSkill.setName(name);		
		technicalSkill.setCategory(category);	
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
				
		// URL、html、paramなどを設定,loginUserの情報を渡す		
		MockHttpServletRequestBuilder postRequest 		
		=MockMvcRequestBuilders.post("/skill/item/register/do")	
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
				
				
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)	
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-register-item"))
				.andExpect(model().attributeExists("registerTechnicalSkillListForm"))
		        .andExpect(model().hasErrors())		
		        .andExpect(model().errorCount(1))		
		        .andExpect(		
		                model().attributeHasFieldErrors("registerTechnicalSkillListForm", "technicalSkillList[0].name"))		
				.andReturn();
								
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTechnicalSkillListForm");		
		BindingResult bindingResult = (BindingResult) object;		
		List<FieldError> list = bindingResult.getFieldErrors("technicalSkillList[0].name");		
				
		//エラーメッセージ取得		
		String actualMessage=list.get(0).getDefaultMessage();		
		String expectedMessage = "スキル名を入力してください";		
				
		//エラーメッセージの確認		
		assertEquals(actualMessage, expectedMessage);
		
	}			
				
	@Test			
	public void テクニカルスキルを登録する_バリデーション_半角スペース() throws Exception{			
		String name=" ";		
		Integer category=1;		
		Integer adminId=14;
		String adminName="管理者次郎";
				
		TechnicalSkill technicalSkill=new TechnicalSkill();		
		technicalSkill.setName(name);		
		technicalSkill.setCategory(category);		
				
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// URL、html、paramなどを設定,loginUserの情報を渡す		
		MockHttpServletRequestBuilder postRequest 		
		=MockMvcRequestBuilders.post("/skill/item/register/do")	
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
				
				
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)	
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-register-item"))
				.andExpect(model().attributeExists("registerTechnicalSkillListForm"))
		        .andExpect(model().hasErrors())		
		        .andExpect(model().errorCount(1))		
		        .andExpect(		
		                model().attributeHasFieldErrors("registerTechnicalSkillListForm", "technicalSkillList[0].name"))		
				.andReturn();
								
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTechnicalSkillListForm");		
		BindingResult bindingResult = (BindingResult) object;		
		List<FieldError> list = bindingResult.getFieldErrors("technicalSkillList[0].name");		
				
		//エラーメッセージ取得		
		String actualMessage=list.get(0).getDefaultMessage();		
		String expectedMessage = "スキル名を入力してください";		
				
		//エラーメッセージの確認		
		assertEquals(actualMessage, expectedMessage);
		
	}			
				
	@Test			
	public void テクニカルスキルを登録する_バリデーション_全角スペース() throws Exception{			
		String name="　";		
		Integer category=1;		
		Integer adminId=14;
		String adminName="管理者次郎";
				
		TechnicalSkill technicalSkill=new TechnicalSkill();		
		technicalSkill.setName(name);		
		technicalSkill.setCategory(category);		
				
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// URL、html、paramなどを設定,loginUserの情報を渡す		
		MockHttpServletRequestBuilder postRequest 		
		=MockMvcRequestBuilders.post("/skill/item/register/do")	
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
				
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)	
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(view().name("skill/skill-register-item"))
				.andExpect(model().attributeExists("registerTechnicalSkillListForm"))
		        .andExpect(model().hasErrors())		
		        .andExpect(model().errorCount(1))		
		        .andExpect(		
		                model().attributeHasFieldErrors("registerTechnicalSkillListForm", "technicalSkillList[0].name"))		
				.andReturn();			
				
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.registerTechnicalSkillListForm");		
		BindingResult bindingResult = (BindingResult) object;		
		List<FieldError> list = bindingResult.getFieldErrors("technicalSkillList[0].name");		
				
		//エラーメッセージ取得		
		String actualMessage=list.get(0).getDefaultMessage();		
		String expectedMessage = "スキル名を入力してください";		
				
		//エラーメッセージの確認		
		assertEquals(actualMessage, expectedMessage);
		
	}	
	
	@Test			
	public void テクニカルスキルを登録する_失敗_登録済みのスキル名() throws Exception {			
		String name="testdata";		
		Integer category=1;		
		Integer adminId=14;		
		String adminName="管理者次郎";		
				
		LocalDateTime updatedAt = LocalDateTime.of(2020,7,1,12,00,00);		
		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, category, adminId, 0, adminName, updatedAt, adminName,updatedAt,1);		
				
		//ログイン認証用の情報		
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();		
		User loginUser=testUser.getUser();		
				
		//モック生成		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);		
				
		//URL、html、paramなどを設定		
		MockHttpServletRequestBuilder postRequest=		
				MockMvcRequestBuilders.post("/skill/item/register/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
				
		//リダイレクトの検証		
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)		
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/item/register"))
				.andReturn();
				
		FlashMap flashMap=result.getFlashMap();		
		String actualMessage=(String) flashMap.get("failMessage");		
		String flashMessage="登録失敗\nERROR:この内容は既に登録されています";		
				
		//メッセージの検証		
		assertEquals(actualMessage, flashMessage);
				
		//Mock化されたオブジェクトが呼ばれたかの検証		
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));		
				
	}			
				
	@Test			
	public void テクニカルスキルを登録する_失敗_申請中のスキル名() throws Exception {			
		String name="testdata";		
		Integer category=1;		
		Integer adminId=14;		
		String adminName="管理者次郎";		
				
		LocalDateTime updatedAt = LocalDateTime.of(2020,7,1,12,00,00);		
		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, category, adminId, 2, adminName, updatedAt, adminName,updatedAt,1);		
				
		//ログイン認証用の情報		
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();		
		User loginUser=testUser.getUser();		
				
		//モック生成		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);		
				
		//URL、html、paramなどを設定		
		MockHttpServletRequestBuilder postRequest=		
				MockMvcRequestBuilders.post("/skill/item/register/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
				
		//リダイレクトの検証		
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)		
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/item/register"))
				.andReturn();
				
		FlashMap flashMap=result.getFlashMap();		
		String actualMessage=(String) flashMap.get("failMessage");		
		String flashMessage="登録失敗\nERROR:この内容は申請中の内容と重複しています";		
				
		//メッセージの検証		
		assertEquals(actualMessage, flashMessage);
				
		//Mock化されたオブジェクトが呼ばれたかの検証		
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));		
				
	}			
				
	@Test			
	public void テクニカルスキルを登録する_失敗_登録済と申請中のスキル名() throws Exception {			
		String name="testdata";		
		String name2="testdata2";		
		Integer category=1;		
		Integer adminId=14;		
		String adminName="管理者次郎";		
				
		LocalDateTime updatedAt = LocalDateTime.of(2020,7,1,12,00,00);		
		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, category, adminId, 0, adminName, updatedAt, adminName,updatedAt,1);		
		TechnicalSkill technicalSkill2= new TechnicalSkill(null,name2, category, adminId, 2, adminName, updatedAt, adminName,updatedAt,1);		
				
		//ログイン認証用の情報		
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();		
		User loginUser=testUser.getUser();		
				
		//モック生成		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(name,category)).thenReturn(technicalSkill);		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(name2,category)).thenReturn(technicalSkill2);		
				
		//URL、html、paramなどを設定		
		MockHttpServletRequestBuilder postRequest=		
				MockMvcRequestBuilders.post("/skill/item/register/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("technicalSkillList[1].name",name2)
				.param("technicalSkillList[1].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
				
		//リダイレクトの検証		
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)		
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/item/register"))
				.andReturn();
				
		FlashMap flashMap=result.getFlashMap();		
		String actualMessage=(String) flashMap.get("failMessage");		
		String flashMessage="登録失敗\nERROR:既に登録されている内容、申請中の内容です";		
				
		//メッセージの検証		
		assertEquals(actualMessage, flashMessage);
				
		//Mock化されたオブジェクトが呼ばれたかの検証		
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(name, category);		
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(name2, category);		
				
	}			

	
	@Test
	public void テクニカルスキルを登録する_失敗_同じスキル名を入力() throws Exception {
		
		String name="testdata";
		Integer category=1;
		Integer adminId=14;
		String adminName="管理者次郎";

		LocalDateTime updatedAt = LocalDateTime.of(2020,7,1,12,00,00);
		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, category, adminId, 0, adminName, updatedAt, adminName,updatedAt,1);
		
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(technicalSkill);
		technicalSkillList.add(technicalSkill);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();

		//モック生成
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(null);
		
		//URL、html、paramなどを設定
		MockHttpServletRequestBuilder posttRequest=
				MockMvcRequestBuilders.post("/skill/item/register/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillList[0].name",name)
				.param("technicalSkillList[0].category",String.valueOf(category))
				.param("technicalSkillList[1].name",name)
				.param("technicalSkillList[1].category",String.valueOf(category))
				.param("userName",adminName)
				.param("userId",String.valueOf(adminId));
		
		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(posttRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())				
				.andExpect(redirectedUrl("/skill/item/register"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		String actualMessage=(String) flashMap.get("failMessage");
		String flashMessage="承認失敗\nERROR:入力されたスキル名が重複しています";

		//メッセージの検証
		assertEquals(actualMessage, flashMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(2)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));
		
	}

	@Test
	public void テクニカルスキルの入力値_登録済みのスキル名() throws Exception {
		String name="mac";
		Integer category=1;
		Integer stage=0;
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setStage(stage);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
				= MockMvcRequestBuilders.get("/skill/item/register/check-item")
					.with(user(new LoginUser(loginUser,authorityList)))
					.param("name",name)
					.param("category", String.valueOf(category));
				
		//パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.checkMessage").value("このスキル名は既に登録されています"));
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));

	}
	
	@Test
	public void テクニカルスキルの入力値_申請中のスキル名() throws Exception {
		String name="mac";
		Integer category=1;
		Integer stage=2;
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setStage(stage);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
				= MockMvcRequestBuilders.get("/skill/item/register/check-item")
					.with(user(new LoginUser(loginUser,authorityList)))
					.param("name",name)
					.param("category", String.valueOf(category));
				
		//パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.checkMessage").value("申請中のスキル名と重複しています"));
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));

	}
	
	@Test
	public void テクニカルスキルの入力値チェック_重複なし() throws Exception {
		String name="hogehoge";
		Integer category=1;
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(null);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
				= MockMvcRequestBuilders.get("/skill/item/register/check-item")
					.with(user(new LoginUser(loginUser,authorityList)))
					.param("name",name)
					.param("category", String.valueOf(category));
				
		//パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(SecurityMockMvcResultMatchers.authenticated())
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.checkMessage").value(""));
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));
		
	}
	
	

}
