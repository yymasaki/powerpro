package com.example.controller.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.example.domain.Template;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.template.CheckTemplateVersionService;
import com.example.service.template.DeleteTemplateService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTemplateControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private AdminloginUser adminLoginUser;
	
    @Autowired
    protected WebApplicationContext context;
	
	@MockBean
	private DeleteTemplateService deleteTemplateService;
	
	@MockBean
	private CheckTemplateVersionService checkTemplateVersionService;	
	
	@Autowired
	private DeleteTemplateController target;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		// SpringMvcの動作を再現
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		// SpringSecurityを適用させる 
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();			
	}


	@Test
	public void テンプレートを削除する() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template/delete")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "1")
				.param("version", "1");		
		// モックの生成
		Template template = new Template();
		template.setTemplateId(1);
		template.setVersion(1);
		List<Template> templateListForVersionCheckMock = Arrays.asList(template);
		
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(templateListForVersionCheckMock);
		when(deleteTemplateService.deleteTemplate(anyInt())).thenReturn(1);
		
		// リダイレクトが正常にされたかどうかの検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/skill/template"))
				.andReturn();
		
		FlashMap flashMap = result.getFlashMap();
		String actual = (String) flashMap.get("deleteCompleted");
		String expected = "テンプレートの削除が完了しました";
		// アラートメッセージの検証
		assertEquals(actual, expected);
		
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(checkTemplateVersionService, times(1)).checkTemplateVersion(anyInt(), anyInt());
		verify(deleteTemplateService, times(1)).deleteTemplate(anyInt());
	}
	
	@Test
	public void 既に他の管理者に削除されているため削除に失敗する() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminLoginUser.getAuthorityList();
		User loginUser = adminLoginUser.getUser();
		
		// URL,html,paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/skill/template/delete")
				.with(user(new LoginUser(loginUser, authorityList)))
				.param("templateId", "1")
				.param("version", "1");		
		// モックの生成
		when(checkTemplateVersionService.checkTemplateVersion(anyInt(), anyInt())).thenReturn(null);
		
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
		verify(deleteTemplateService, times(0)).deleteTemplate(anyInt());
	}
}
