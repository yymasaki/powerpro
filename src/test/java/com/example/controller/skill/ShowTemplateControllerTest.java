package com.example.controller.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Template;
import com.example.domain.TemplateBasicSkill;
import com.example.domain.User;
import com.example.example.UserExample;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.status.GetPersonalityService;
import com.example.service.template.GetTemplateBasicSkillListService;
import com.example.service.template.GetTemplateListService;
import com.example.service.template.GetTemplateService;
import com.example.service.user.UpdateUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowTemplateControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private AdminloginUser adminLoginUser;
	
	@Autowired
	private WebLoginUser webLoginUser;
	
    @Autowired
    protected WebApplicationContext context;
	
	@MockBean
	private GetTemplateService getTemplateService;
	
	@MockBean
	private GetTemplateListService getTemplateListService;
	
	@MockBean
	private GetTemplateBasicSkillListService getTemplateBasicSkillListService;
	
	@MockBean
	private GetPersonalityService getPersonalityService;
	
	@MockBean
	private UpdateUserService updateUserService;
	
	@Autowired
	private ShowTemplateController target;
	
	@BeforeEach
	public void setUp() throws Exception {
		// SpringMvcの動作を再現
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		// SpringSecurityを適用させる 
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
	@Test
	public void テンプレート選択画面を初期表示する() throws Exception {
		// モック生成
		Template template1 = new Template();
		template1.setTemplateId(1);
		List<Template> templateList = Arrays.asList(template1);
		List<Personality> personalityList = new ArrayList<>();
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template1);
		when(getPersonalityService.getPersonality()).thenReturn(personalityList);

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeExists("selectTemplateForm"))
			.andExpect(model().attributeExists("deleteTemplateForm"))
			.andExpect(model().attributeExists("template"))
			.andExpect(model().attributeExists("templateList"))
			.andExpect(model().attributeExists("personalityList"))
			.andExpect(model().attributeExists("userId"))
			.andExpect(view().name("skill/skill-template"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(getPersonalityService, times(1)).getPersonality();
	}

	@Test
	public void プルダウンで選択されたtemplateIdを基にをテンプレート選択画面を表示する() throws Exception {
		// モック生成
		Template template1 = new Template();
		template1.setTemplateId(1);
		List<Template> templateList = Arrays.asList(template1);
		List<Personality> personalityList = new ArrayList<>();
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template1);
		when(getPersonalityService.getPersonality()).thenReturn(personalityList);

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "1");
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("selectedTemplateName", "なし"))
			.andExpect(model().attributeExists("selectTemplateForm"))
			.andExpect(model().attributeExists("deleteTemplateForm"))
			.andExpect(model().attributeExists("template"))
			.andExpect(model().attributeExists("templateList"))
			.andExpect(model().attributeExists("personalityList"))
			.andExpect(model().attributeExists("userId"))
			.andExpect(view().name("skill/skill-template"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(getPersonalityService, times(1)).getPersonality();
	}
	
	@Test
	public void ユーザーが選択しているテンプレートを表示する() throws Exception {
		// モック生成
		Integer templateId = 1;
		Template template1 = new Template();
		template1.setTemplateId(templateId);
		template1.setName("テストテンプレート1");
		List<Template> templateList = Arrays.asList(template1);
		List<Personality> personalityList = new ArrayList<>();
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template1);
		when(getPersonalityService.getPersonality()).thenReturn(personalityList);

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User loginUser = webLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("selectedTemplateName", template1.getName()))
			.andExpect(model().attributeExists("selectTemplateForm"))
			.andExpect(model().attributeExists("deleteTemplateForm"))
			.andExpect(model().attributeExists("template"))
			.andExpect(model().attributeExists("templateList"))
			.andExpect(model().attributeExists("personalityList"))
			.andExpect(model().attributeExists("userId"))
			.andExpect(view().name("skill/skill-template"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(2)).getTemplate(anyInt());
		verify(getPersonalityService, times(1)).getPersonality();
	}
	
	@Test
	public void ログインユーザーが管理者で表示できるテンプレートが1件も存在しない場合() throws Exception {
		// モック生成
		List<Template> templateList = new ArrayList<>();
		List<Personality> personalityList = new ArrayList<>();
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getPersonalityService.getPersonality()).thenReturn(personalityList);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("noTemplateListMessage", "表示できるテンプレートが存在しません。テンプレートを登録してください。"))
			.andExpect(model().attribute("selectedTemplateName", "なし"))
			.andExpect(model().attributeExists("selectTemplateForm"))
			.andExpect(model().attributeExists("deleteTemplateForm"))
			.andExpect(model().attributeExists("template"))
			.andExpect(model().attributeExists("templateList"))
			.andExpect(model().attributeExists("personalityList"))
			.andExpect(model().attributeExists("userId"))
			.andExpect(view().name("skill/skill-template"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(0)).getTemplate(anyInt());
		verify(getPersonalityService, times(1)).getPersonality();			
	}
	
	@Test
	public void ログインユーザーが非管理者で表示できるテンプレートが1件も存在しない場合() throws Exception {
		// モック生成
		List<Template> templateList = new ArrayList<>();
		List<Personality> personalityList = new ArrayList<>();
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getPersonalityService.getPersonality()).thenReturn(personalityList);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User loginUser = webLoginUser.getUser();
		loginUser.setSelectedTemplateId(null);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)));
		
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("noTemplateListMessage", "表示できるテンプレートが存在しません。管理者がテンプレート登録するまでお待ちください。"))
			.andExpect(model().attribute("selectedTemplateName", "なし"))
			.andExpect(model().attributeExists("selectTemplateForm"))
			.andExpect(model().attributeExists("deleteTemplateForm"))
			.andExpect(model().attributeExists("template"))
			.andExpect(model().attributeExists("templateList"))
			.andExpect(model().attributeExists("personalityList"))
			.andExpect(model().attributeExists("userId"))
			.andExpect(view().name("skill/skill-template"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(0)).getTemplate(anyInt());
		verify(getPersonalityService, times(1)).getPersonality();			
	}	
	
	@Test
	public void テンプレート選択画面表示_URLに不正なテンプレートidが入力された場合エラーを返す() throws Exception{
		// モック生成
		Integer templateId = 1;
		Template template1 = new Template();
		template1.setTemplateId(templateId);
		template1.setName("テストテンプレート1");
		List<Template> templateList = Arrays.asList(template1);
		List<Personality> personalityList = new ArrayList<>();
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template1);
		when(getPersonalityService.getPersonality()).thenReturn(personalityList);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User loginUser = webLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "100");
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();
		
		FlashMap flashMap = result.getFlashMap();
		String actual = (String) flashMap.get("versionErrorMessage");
		String expected = "取得失敗 ERROR:  URLに不正な値が入力されました";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(0)).getTemplate(anyInt());
		verify(getPersonalityService, times(0)).getPersonality();
	}
	
	@Test
	public void テンプレート選択画面表示_既に削除されたテンプレートを選択した場合エラーを返す() throws Exception { 
		// モック生成
		Template template1 = new Template();
		template1.setTemplateId(1);
		List<Template> templateList = Arrays.asList(template1);
		
		when(getTemplateListService.getTemplateList(anyInt())).thenReturn(templateList);
		when(getTemplateService.getTemplate(anyInt())).thenReturn(null);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "1");
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();

		FlashMap flashMap = result.getFlashMap();
		String actual = (String) flashMap.get("versionErrorMessage");
		String expected = "取得失敗 ERROR: テンプレートは管理者によって削除されました";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateListService, times(1)).getTemplateList(anyInt());
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(getPersonalityService, times(0)).getPersonality();
	}
	
	@Test
	public void テンプレート選択のDB保持_正常処理() throws Exception {
		// モック生成
		Template template1 = new Template();
		when(getTemplateService.getTemplate(anyInt())).thenReturn(template1);
		when(updateUserService.editUser(any(User.class), any(UserExample.class))).thenReturn(true);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User loginUser = webLoginUser.getUser();
		
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/skill/template/select")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", "1");
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(postRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();
		
		FlashMap flashMap = result.getFlashMap();
		String actual = (String) flashMap.get("editingCompleted");
		String expected = "テンプレートが選択されました";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(updateUserService, times(1)).editUser(any(User.class), any(UserExample.class));
	}
	
	@Test
	public void テンプレート選択のDB保持_既に削除されたテンプレートを選択した場合エラーを返す() throws Exception {
		// モック生成
		when(getTemplateService.getTemplate(anyInt())).thenReturn(null);
		
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webLoginUser.getAuthorityList();
		User loginUser = webLoginUser.getUser();
		
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/skill/template/select")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("templateId", "1");
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(postRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();
		
		FlashMap flashMap = result.getFlashMap();
		String actual = (String) flashMap.get("versionErrorMessage");
		String expected = "取得失敗 ERROR: テンプレートは管理者によって削除されました";
		
		// アラートメッセージの検証
		assertEquals(actual, expected);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(getTemplateService, times(1)).getTemplate(anyInt());
		verify(updateUserService, times(0)).editUser(any(User.class), any(UserExample.class));
	}

	@Test
	public void getTemplateBasicSkillメソッドのテスト() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// 引数を設定
		Integer templateId = 1;
		
		// モック生成
		List<TemplateBasicSkill> templateBasicSkillListMock = new ArrayList<>();
		TemplateBasicSkill templateBasicSkill1 = new TemplateBasicSkill();
		templateBasicSkill1.setTemplateBasicSkillId(1);
		templateBasicSkill1.setTemplateId(templateId);
		templateBasicSkill1.setBasicSkillId(1);
		templateBasicSkill1.setScore(5);
		
		templateBasicSkillListMock.add(templateBasicSkill1);
		
		when(getTemplateBasicSkillListService.getTemplateBasicSkillList(anyInt())).thenReturn(templateBasicSkillListMock);
		
		// URL、html、paramなどを設定,loginUserの情報を渡す
		MockHttpServletRequestBuilder getRequest =
				MockMvcRequestBuilders.get("/skill/template/get_template_basic_skill_list")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", String.valueOf(templateId));

		
		// パスの有効性と取得するオブジェクトの確認
		mockMvc.perform(getRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.templateBasicSkillList[0].templateBasicSkillId").value(1))
			.andExpect(jsonPath("$.templateBasicSkillList[0].templateId").value(1))
			.andExpect(jsonPath("$.templateBasicSkillList[0].basicSkillId").value(1))
			.andExpect(jsonPath("$.templateBasicSkillList[0].score").value(5));
	}
}
