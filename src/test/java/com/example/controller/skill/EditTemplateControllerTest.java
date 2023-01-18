package com.example.controller.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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

import com.example.domain.LoginUser;
import com.example.domain.Template;
import com.example.domain.User;
import com.example.form.EditTemplateForm;
import com.example.form.SelectTemplateForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.template.CheckTemplateVersionService;
import com.example.service.template.GetTemplateForEditService;
import com.example.service.template.GetTemplateService;
import com.example.service.template.UpdateTemplateService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditTemplateControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private AdminloginUser adminLoginUser;
	
    @Autowired
    protected WebApplicationContext context;
    
    @MockBean
    private GetTemplateService getTemplateService;
	
	@MockBean
	private GetTemplateForEditService getTemplateForEditService;
	
	@MockBean
	private CheckTemplateVersionService checkTemplateVersionService;
	
	@MockBean
	private UpdateTemplateService updateTemplateService;
	
	@Autowired
	private EditTemplateController target;
	
	@BeforeEach
	public void setUp() throws Exception {
		// SpringMvcの動作を再現
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		// SpringSecurityを適用させる 
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();		
	}

	@Test
	public void テンプレート編集画面を正常に表示する() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template/edit")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "1")
				.param("version", "1");
		
		// モックの生成
		Template template = new Template();
		template.setTemplateId(1);
		template.setVersion(1);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template);
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeExists("editTemplateForm"))
			.andExpect(model().attributeExists("template"))
			.andExpect(view().name("skill/skill-edit-template"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateService , times(1)).getTemplate(anyInt());
	}
	
	@Test
	public void バージョン照合を行いテンプレート選択画面にリダイレクトする() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template/edit")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "1")
				.param("version", "1");
		
		// モックの生成
		Template template = new Template();
		template.setTemplateId(1);
		template.setVersion(2);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();

		FlashMap flashMap = result.getFlashMap();
		String actual = (String) flashMap.get("versionErrorMessage");
		String expected = "取得失敗 ERROR: このテンプレートは更新されています";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateService , times(1)).getTemplate(anyInt());
	}
	
	@Test
	public void テンプレートを編集する_正常処理() throws Exception {
		// EditTemplateFormの要素を作成
		Integer templateId = 1;
		String departmentId = "1";
		String name = "テストテンプレート改";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("5","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		List<Template> templateListForVersionCheckMock = Arrays.asList(templateMock);
		List<Template> templateListMock = new ArrayList<>();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(), anyInt(), anyString())).thenReturn(templateListMock);
		doNothing().when(updateTemplateService).editTemplate(any(EditTemplateForm.class), anyString());
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", String.valueOf(templateId))
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
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
		String actualMessage = (String) flashMap.get("editingCompleted");
		String expectedMessage = "テンプレートの編集が完了しました";
		SelectTemplateForm selectTemplateForm = (SelectTemplateForm) flashMap.get("selectTemplateForm");
		Integer actualTemplateId = selectTemplateForm.getTemplateId();
		
		// アラートメッセージの検証
		assertEquals(actualMessage, expectedMessage);
		assertEquals(actualTemplateId, 1);
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(getTemplateForEditService, times(1)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(1)).editTemplate(any(EditTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを編集する_バージョン照合_他の管理者に更新された場合() throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "テストテンプレート改";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("5","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(null);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
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
		String actual = (String) flashMap.get("versionErrorMessage");
		String expected = "更新失敗 ERROR: テンプレートは管理者によって更新されました";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(getTemplateForEditService, times(0)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを編集する_バージョン照合_他の管理者に削除された場合() throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "テストテンプレート改";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("5","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(null);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(null,templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
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
		String actual = (String) flashMap.get("versionErrorMessage");
		String expected = "更新失敗 ERROR: テンプレートは管理者によって削除されました";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(getTemplateForEditService, times(0)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを編集する_同一所属内に同じ名前のテンプレートが存在するためエラーを返す() throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "テストテンプレート改";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("5","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		templateMock.setVersion(1);
		List<Template> templateListForVersionCheckMock = Arrays.asList(templateMock);
		List<Template> templateListMock = Arrays.asList(templateMock);
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(), anyInt(), anyString())).thenReturn(templateListMock);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("editTemplateForm", "name"))
				.andExpect(view().name("skill/skill-edit-template"))
				.andExpect(model().attributeExists("editTemplateForm"))
				.andExpect(model().attributeExists("template"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "このテンプレート名は既に使用されています";
		
		// エラーメッセージを取得
		assertEquals(actualErrorMessage, expectedErrorMessage);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateService, times(2)).getTemplate(anyInt());
		verify(getTemplateForEditService, times(1)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
	}
	
	@Test
	public void テンプレートを編集する_テンプレートエンジニアスキルに未入力の項目が存在するためエラーを返す () throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "テストテンプレート改";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList(null,"90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("5","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		templateMock.setVersion(1);
		List<Template> templateListForVersionCheckMock = Arrays.asList(templateMock);
		List<Template> templateListMock = new ArrayList<>();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(), anyInt(), anyString())).thenReturn(templateListMock);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("editTemplateForm", "templateEngineerSkillScoreList"))
				.andExpect(view().name("skill/skill-edit-template"))
				.andExpect(model().attributeExists("editTemplateForm"))
				.andExpect(model().attributeExists("template"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "0から100の数字を入力してください";
		
		// エラーメッセージを取得
		assertEquals(actualErrorMessage, expectedErrorMessage);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateForEditService, times(1)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
		verify(getTemplateService, times(2)).getTemplate(anyInt());
	}
	
	@Test
	public void テンプレートを編集する_テンプレート基本スキルに未入力の項目が存在するためエラーを返す () throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "テストテンプレート改";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList(null,"4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		templateMock.setVersion(1);
		List<Template> templateListForVersionCheckMock = Arrays.asList(templateMock);
		List<Template> templateListMock = new ArrayList<>();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(), anyInt(), anyString())).thenReturn(templateListMock);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("editTemplateForm", "templateBasicSkillScoreList"))
				.andExpect(view().name("skill/skill-edit-template"))
				.andExpect(model().attributeExists("editTemplateForm"))
				.andExpect(model().attributeExists("template"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "1から5の数字を入力してください";
		
		// エラーメッセージを取得
		assertEquals(actualErrorMessage, expectedErrorMessage);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateForEditService, times(1)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
		verify(getTemplateService, times(2)).getTemplate(anyInt());
	}
	
	@Test
	public void テンプレートを編集する_テンプレート名が全角スペースのみの場合にエラーを返す() throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "　";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		templateMock.setVersion(1);
		List<Template> templateListForVersionCheckMock = Arrays.asList(templateMock);
		List<Template> templateListMock = new ArrayList<>();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(), anyInt(), anyString())).thenReturn(templateListMock);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("editTemplateForm", "name"))
				.andExpect(view().name("skill/skill-edit-template"))
				.andExpect(model().attributeExists("editTemplateForm"))
				.andExpect(model().attributeExists("template"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "テンプレート名を入力してください";
		
		// エラーメッセージを取得
		assertEquals(actualErrorMessage, expectedErrorMessage);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateForEditService, times(1)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
		verify(getTemplateService, times(2)).getTemplate(anyInt());
	}
	
	@Test
	public void テンプレートを編集する_テンプレート名が31文字以上の場合にエラーを返す() throws Exception {
		// EditTemplateFormの要素を作成
		String templateId = "1";
		String departmentId = "1";
		String name = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめも";
		String version = "1";
		
		// 下記paramsで使用するためのMultiValueMapを準備
		MultiValueMap<String, String> engineerSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateEngineerSkillScoreListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> basicSkillIdListMap = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> templateBasicSkillScoreListMap = new LinkedMultiValueMap<>();
		engineerSkillIdListMap.put("engineerSkillIdList",Arrays.asList("1","2","3","4","5","6","7"));
		templateEngineerSkillScoreListMap.put("templateEngineerSkillScoreList", Arrays.asList("90","90","90","90","90","90","90"));
		basicSkillIdListMap.put("basicSkillIdList", Arrays.asList("1","2","3","4","5","6"));
		templateBasicSkillScoreListMap.put("templateBasicSkillScoreList", Arrays.asList("3","4","5","4","5","4"));
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// モック生成
		Template templateMock = new Template();
		templateMock.setVersion(1);
		List<Template> templateListForVersionCheckMock = Arrays.asList(templateMock);
		List<Template> templateListMock = new ArrayList<>();
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(), anyInt(), anyString())).thenReturn(templateListMock);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(templateMock);
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/skill/template/edit/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", templateId)
				.param("departmentId", departmentId)
				.param("name", name)
				.param("version", version)
				.params(engineerSkillIdListMap)
				.params(templateEngineerSkillScoreListMap)
				.params(basicSkillIdListMap)
				.params(templateBasicSkillScoreListMap);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("editTemplateForm", "name"))
				.andExpect(view().name("skill/skill-edit-template"))
				.andExpect(model().attributeExists("editTemplateForm"))
				.andExpect(model().attributeExists("template"))
				.andReturn();
		
		// エラーメッセージを取得
		BindingResult errorObject = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.editTemplateForm");
		List<FieldError> errorList = errorObject.getFieldErrors();
		String actualErrorMessage = errorList.get(0).getDefaultMessage();
		String expectedErrorMessage = "30文字以内で入力してください";
		
		// エラーメッセージを取得
		assertEquals(actualErrorMessage, expectedErrorMessage);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(getTemplateForEditService, times(1)).getTemplateForEdit(anyInt(), anyInt(), anyString());
		verify(updateTemplateService, times(0)).editTemplate(any(EditTemplateForm.class), anyString());
		verify(getTemplateService, times(2)).getTemplate(anyInt());
	}
	
	
	@Test
	public void templateのsizeが0の場合のcheckDuplicateForEditTemplateメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数の設定
		Integer templateId = 1;
		Integer departmentId = 1;
		String name = "サンプル";
		
		// モック生成
		List<Template> templateList = new ArrayList<>();
		when(getTemplateForEditService.getTemplateForEdit(anyInt(),anyInt(), anyString())).thenReturn(templateList);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/skill/template/edit/check_duplicate")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("templateId", String.valueOf(templateId))
		.param("departmentId", String.valueOf(departmentId))
		.param("name", name);
		
		// パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("このテンプレート名は有効です"));
	}

	@Test
	public void templateのsizeが0ではない場合のcheckDuplicateForEditTemplateメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数の設定
		Integer templateId = 1;
		Integer departmentId = 1;
		String name = "サンプル";
		
		// モック生成
		Template template = new Template();
		List<Template> templateList = Arrays.asList(template);
		when(getTemplateForEditService.getTemplateForEdit(anyInt(),anyInt(), anyString())).thenReturn(templateList);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest 
		= MockMvcRequestBuilders.get("/skill/template/edit/check_duplicate")
		.with(user(new LoginUser(loginUser,authorityList)))
		.param("templateId", String.valueOf(templateId))
		.param("departmentId", String.valueOf(departmentId))
		.param("name", name);
		
		// パスの有効性とエラーメッセージの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("このテンプレート名は既に使用されています"));
	}
}
