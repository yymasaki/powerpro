package com.example.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.common.Stage;
import com.example.domain.Department;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.EditRequestTechnicalSkillForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.technique.GetRequestTechnicalSkillService;
import com.example.service.technique.GetTechnicalSkillByNameAndCategoryService;
import com.example.service.technique.GetTechnicalSkillService;
import com.example.service.technique.UpdateRequestTechnicalSkillService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditRequestTechnicalSkillControllerTest{

	MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    private AdminloginUser testUser;
   
    // モック化する
	@MockBean
	private GetRequestTechnicalSkillService getRequestTechnicalSkillService;

	@MockBean
	private GetTechnicalSkillService getTechnicalSkillService;
	
	@MockBean
	private UpdateRequestTechnicalSkillService updateService;
	
	@MockBean
	private GetTechnicalSkillByNameAndCategoryService getByNameAndCategoryService;
	
	// モックオブジェクトの挿入対象
	@Autowired
	private EditRequestTechnicalSkillController target;

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
	public void テクニカルスキル申請内容修正画面を表示でバージョン一致() throws Exception {

		Integer technicalSkillId = 1;
		Integer version = 1;
		String departmentName="Webエンジニア";
		
		//戻り値を用意
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setVersion(version);
		User user=new User();
		Department department=new Department();
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();

		// モック作成
		when(getRequestTechnicalSkillService.getRequestTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
				
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
			= MockMvcRequestBuilders.get("/request/item/edit")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("technicalSkillId", String.valueOf(technicalSkillId))
				.param("version", String.valueOf(version));

		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("request/request-edit-item"))
				.andReturn();

		Map<Integer, String> expectedSkillMap = new LinkedHashMap<>();
		expectedSkillMap.put(Category.OS.getKey(), Category.OS.getName());
		expectedSkillMap.put(Category.LANGUAGE.getKey(), Category.LANGUAGE.getName());
		expectedSkillMap.put(Category.FRAMEWORK.getKey(), Category.FRAMEWORK.getName());
		expectedSkillMap.put(Category.LIBRARY.getKey(), Category.LIBRARY.getName());
		expectedSkillMap.put(Category.MIDDLEWARE.getKey(), Category.MIDDLEWARE.getName());
		expectedSkillMap.put(Category.TOOL.getKey(), Category.TOOL.getName());
		expectedSkillMap.put(Category.PROCESS.getKey(), Category.PROCESS.getName());

		TechnicalSkill actualTechnicalSkill = (TechnicalSkill) result.getModelAndView().getModel()
				.get("technicalSkill");
		@SuppressWarnings("unchecked")
		Map<Integer, String> actualSkillMap = (Map<Integer, String>) result.getModelAndView().getModel()
				.get("skillMap");

	
		assertEquals(actualTechnicalSkill.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualTechnicalSkill.getVersion(), version);
		assertEquals(actualTechnicalSkill.getUser().getDepartment().getName(), departmentName);
		assertEquals(actualSkillMap, expectedSkillMap);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getRequestTechnicalSkillService, times(1)).getRequestTechnicalSkill(any(Integer.class));

	}

	@Test
	public void テクニカルスキル申請内容修正画面を表示_バージョン不一致_stage0承認() throws Exception {
		Integer technicalSkillId = 1;
		Integer version = 1;
		Integer testVersion=10;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		//違うバージョンをセット
		technicalSkill.setVersion(testVersion);
		technicalSkill.setStage(Stage.ACTIVE.getKey());

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getRequestTechnicalSkillService.getRequestTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/request/item/edit")
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId", String.valueOf(technicalSkillId))
			.param("version", String.valueOf(version));

		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/request"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
				
		String actualMessage=(String) flashMap.get("failMessage");		
		String expectedMessage = "承認失敗\nERROR:この申請内容は既に承認されました";
		
		//メッセージの検証
		assertEquals(actualMessage, expectedMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getRequestTechnicalSkillService, times(1)).getRequestTechnicalSkill(any(Integer.class));

	}
	
	@Test
	public void テクニカルスキル申請内容修正画面を表示_バージョン不一致_stage0以外() throws Exception {
		Integer technicalSkillId = 1;
		Integer version = 1;
		Integer testVersion=10;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		//違うバージョンをセット
		technicalSkill.setVersion(testVersion);
		technicalSkill.setStage(Stage.REQUESTING.getKey());
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();

		// モック作成
		when(getRequestTechnicalSkillService.getRequestTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/request/item/edit")
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId", String.valueOf(technicalSkillId))
			.param("version", String.valueOf(version));

		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/request"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		String actualMessage=(String) flashMap.get("failMessage");		
		
		//格納されたmodelの中身がnullである検証
		assertEquals(actualMessage, null);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getRequestTechnicalSkillService, times(1)).getRequestTechnicalSkill(any(Integer.class));


	}

	@Test
	public void テクニカルスキル申請を修正後承認する_バリデーション_空欄() throws Exception{
		Integer technicalSkillId = 1;
		String name="";
		Integer category=1;
		Integer version = 1;
		String departmentName="Webエンジニア";
		
		//戻り値を用意
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setVersion(version);
		User user=new User();
		Department department=new Department();
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getRequestTechnicalSkillService.getRequestTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
					
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
		
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-edit-item"))
				.andExpect(model().attributeExists("editRequestTechnicalSkillForm"))
		        .andExpect(model().hasErrors())
		        .andExpect(model().errorCount(1))
		        .andExpect(
		                model().attributeHasFieldErrors("editRequestTechnicalSkillForm", "name"))
				.andReturn();
				
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editRequestTechnicalSkillForm");
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> list = bindingResult.getFieldErrors("name");
		
		//エラーメッセージ取得
		String actualMessage=list.get(0).getDefaultMessage();
		String expectedMessage = "スキル名を入力してください";
		
		//エラーメッセージの確認
		assertEquals(actualMessage, expectedMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getRequestTechnicalSkillService, times(1)).getRequestTechnicalSkill(any(Integer.class));		
	}
	
	@Test
	public void テクニカルスキル申請を修正後承認する_バリデーション_半角スペース() throws Exception{
		Integer technicalSkillId = 1;
		String name=" ";
		Integer category=1;
		Integer version = 1;
		String departmentName="Webエンジニア";
		
		//戻り値を用意
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setVersion(version);
		User user=new User();
		Department department=new Department();
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getRequestTechnicalSkillService.getRequestTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));	
		
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-edit-item"))
				.andExpect(model().attributeExists("editRequestTechnicalSkillForm"))
		        .andExpect(model().hasErrors())
		        .andExpect(model().errorCount(1))
		        .andExpect(
		                model().attributeHasFieldErrors("editRequestTechnicalSkillForm", "name"))
				.andReturn();
		
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editRequestTechnicalSkillForm");
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> list = bindingResult.getFieldErrors("name");
		
		//エラーメッセージ取得
		String actualMessage=list.get(0).getDefaultMessage();
		String expectedMessage = "スキル名を入力してください";
		
		//エラーメッセージの確認
		assertEquals(actualMessage, expectedMessage);
		
		verify(getRequestTechnicalSkillService, times(1)).getRequestTechnicalSkill(any(Integer.class));
	}
	
	@Test
	public void テクニカルスキル申請を修正後承認する_バリデーション_全角スペース() throws Exception{
		Integer technicalSkillId = 1;
		String name="　";
		Integer category=1;
		Integer version = 1;
		String departmentName="Webエンジニア";
		
		//戻り値を用意
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setVersion(version);
		User user=new User();
		Department department=new Department();
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getRequestTechnicalSkillService.getRequestTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
			
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(view().name("request/request-edit-item"))
				.andExpect(model().attributeExists("editRequestTechnicalSkillForm"))
		        .andExpect(model().hasErrors())
		        .andExpect(model().errorCount(1))
		        .andExpect(
		                model().attributeHasFieldErrors("editRequestTechnicalSkillForm", "name"))
				.andReturn();
			
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editRequestTechnicalSkillForm");
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> list = bindingResult.getFieldErrors("name");
		
		//エラーメッセージ取得
		String actualMessage=list.get(0).getDefaultMessage();
		String expectedMessage = "スキル名を入力してください";
		
		//エラーメッセージの確認
		assertEquals(actualMessage, expectedMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getRequestTechnicalSkillService, times(1)).getRequestTechnicalSkill(any(Integer.class));
	}
	
	
	@Test
	public void テクニカルスキル申請を修正後承認する_失敗_登録済みのスキル名() throws Exception{
		Integer technicalSkillId = 1;
		String name="mac";
		Integer category=1;
		Integer stage=0;
		Integer version = 1;
		String departmentName="Webエンジニア";
		
		//戻り値を用意
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setStage(stage);
		technicalSkill.setVersion(version);
		User user=new User();
		Department department=new Department();
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);		

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);
				
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
	
		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/request"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		EditRequestTechnicalSkillForm actualform=(EditRequestTechnicalSkillForm)flashMap.get("editRequestTechnicalSkillForm");
				
		String actualMessage=(String) flashMap.get("failMessage");
		String flashMessage="承認失敗\nERROR:この内容は既に登録されています";
		
		//リクエストパラメータの値を検証
		assertEquals(actualform.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualform.getName(), name);
		assertEquals(actualform.getCategory(), category);
		assertEquals(actualform.getVersion(), version);
		//リダイレクトした際のメッセージ検証
		assertEquals(actualMessage, flashMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));

	}
	
	@Test
	public void テクニカルスキル申請を修正後承認する_失敗_申請中のスキル名() throws Exception{
		Integer technicalSkillId = 1;
		String name="mac";
		Integer category=1;
		Integer stage=2;
		Integer version = 1;
		String departmentName="Webエンジニア";
		
		//戻り値を用意
		TechnicalSkill technicalSkill=new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		technicalSkill.setStage(stage);
		technicalSkill.setVersion(version);
		User user=new User();
		Department department=new Department();
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);		

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);
				
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
	
		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/request"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		EditRequestTechnicalSkillForm actualform=(EditRequestTechnicalSkillForm)flashMap.get("editRequestTechnicalSkillForm");
				
		String actualMessage=(String) flashMap.get("failMessage");
		String flashMessage="承認失敗\nERROR:この内容は申請中の内容と重複しています";
		
		//リクエストパラメータの値を検証
		assertEquals(actualform.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualform.getName(), name);
		assertEquals(actualform.getCategory(), category);
		assertEquals(actualform.getVersion(), version);
		//リダイレクトした際のメッセージ検証
		assertEquals(actualMessage, flashMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getByNameAndCategoryService, times(1)).getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class));

	}
	
	
	
	@Test
	public void テクニカルスキル申請を修正後承認する()throws Exception {
				
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
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getTechnicalSkillService.getTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
		when(updateService.updateRequestTechnicalSkill(any(TechnicalSkill.class))).thenReturn(1);
						
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
	
		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/request"))
				.andReturn();
		
		FlashMap flashMap=result.getFlashMap();
		EditRequestTechnicalSkillForm actualform=(EditRequestTechnicalSkillForm)flashMap.get("editRequestTechnicalSkillForm");
				
		String actualMessage=(String) flashMap.get("message");
		String flashMessage="テクニカルスキル申請を承認しました";
		
		//リクエストパラメータの値を検証
		assertEquals(actualform.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualform.getName(), name);
		assertEquals(actualform.getCategory(), category);
		assertEquals(actualform.getVersion(), version);
		//リダイレクトした際のメッセージ検証
		assertEquals(actualMessage, flashMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getTechnicalSkillService, times(1)).getTechnicalSkill(any(Integer.class));
		verify(updateService, times(1)).updateRequestTechnicalSkill(any(TechnicalSkill.class));
		
	}
	
	@Test
	public void テクニカルスキル申請を修正後承認する_バージョン不一致_stage0承認()throws Exception {
		Integer technicalSkillId = 1;
		String name="mac";
		Integer category=1;
		Integer version = 1;
		Integer testVersion=10;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		//version不一致にさせる
		technicalSkill.setVersion(testVersion);
		//承認
		technicalSkill.setStage(Stage.ACTIVE.getKey());
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getTechnicalSkillService.getTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
			
		//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
			.andExpect(SecurityMockMvcResultMatchers.authenticated())
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/request"))
			.andReturn();
		
		FlashMap flashMap=result.getFlashMap();	
		String actualMessage=(String) flashMap.get("failMessage");		
		String expectedMessage = "承認失敗\nERROR:この申請内容は既に承認されました";
				
		EditRequestTechnicalSkillForm actualform
		=(EditRequestTechnicalSkillForm)flashMap.get("editRequestTechnicalSkillForm");
				
		
		//リクエストパラメータの値を検証
		assertEquals(actualform.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualform.getName(), name);
		assertEquals(actualform.getCategory(), category);
		assertEquals(actualform.getVersion(), version);
		//modelのメッセージ照合
		assertEquals(actualMessage, expectedMessage);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getTechnicalSkillService, times(1)).getTechnicalSkill(any(Integer.class));
		
	}
	
	@Test
	public void テクニカルスキル申請を修正後承認する_バージョン不一致_stage0以外()throws Exception {
		Integer technicalSkillId = 1;
		String name="mac";
		Integer category=1;
		Integer version = 1;
		Integer testVersion=10;
		
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		//version不一致にさせる
		technicalSkill.setVersion(testVersion);
		//承認済み以外
		technicalSkill.setStage(Stage.REQUESTING.getKey());
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		// モック作成
		when(getTechnicalSkillService.getTechnicalSkill(any(Integer.class))).thenReturn(technicalSkill);
				
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder postRequest 
		= MockMvcRequestBuilders.post("/request/item/edit/do")
			.with(csrf())
			.with(user(new LoginUser(loginUser,authorityList)))
			.param("technicalSkillId",String.valueOf(technicalSkillId))
			.param("name",name)
			.param("category", String.valueOf(category))
			.param("version", String.valueOf(version));
		
			//リダイレクトの検証
		MvcResult result = (MvcResult) mockMvc.perform(postRequest)
			.andExpect(SecurityMockMvcResultMatchers.authenticated())
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/request"))
			.andReturn();
				
		FlashMap flashMap=result.getFlashMap();	
		String actualMessage=(String) flashMap.get("failMessage");		
						
		EditRequestTechnicalSkillForm actualform
				=(EditRequestTechnicalSkillForm)flashMap.get("editRequestTechnicalSkillForm");
										
		
		//リクエストパラメータの値を検証
		assertEquals(actualform.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualform.getName(), name);
		assertEquals(actualform.getCategory(), category);
		assertEquals(actualform.getVersion(), version);
		//modelのメッセージ照合
		assertEquals(actualMessage, null);
		
		//Mock化されたオブジェクトが呼ばれたかの検証
		verify(getTechnicalSkillService, times(1)).getTechnicalSkill(any(Integer.class));
		
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
		
		//モック作成
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
				= MockMvcRequestBuilders.get("/request/item/edit/check-item-edit")
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
		
		//モック作成
		when(getByNameAndCategoryService.getTechinicalSkillByNameAndCategory(any(String.class), any(Integer.class))).thenReturn(technicalSkill);
		
		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest 
				= MockMvcRequestBuilders.get("/request/item/edit/check-item-edit")
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
				= MockMvcRequestBuilders.get("/request/item/edit/check-item-edit")
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
