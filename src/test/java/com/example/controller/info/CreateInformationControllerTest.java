package com.example.controller.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import java.util.Collection;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
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

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.CreateInformationForm;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.info.AddInformationService;
import com.example.service.info.AddInformationsDepartmentLinkService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateInformationControllerTest {

	private MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private AdminloginUser adminloginUser;	
    
    @Autowired
    private WebLoginUser webLoginUser;
    
    @Autowired
    private SalesLoginUser salesLoginUser;	
	
	@Rule
	public final MockitoRule rule = MockitoJUnit.rule();

	@MockBean
	private AddInformationService addInformationService;

	@MockBean
	private AddInformationsDepartmentLinkService addInformationsDepartmentLinkService;

	@InjectMocks
	private CreateInformationController target;

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
	public void お知らせ作成画面を表示する_管理者ログイン() throws Exception {

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=adminloginUser.getAuthorityList();
		User loginUser=adminloginUser.getUser();
		
		mockMvc.perform(get("/info/create")
		.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isOk())
		.andExpect(view().name("info/info-create"));
	}
	
	@Test
	public void お知らせ作成画面を表示する_WEBログイン() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=webLoginUser.getAuthorityList();
		User loginUser=webLoginUser.getUser();
		
		mockMvc.perform(get("/info/create")
				.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void お知らせ作成画面を表示する_SALEログイン() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=salesLoginUser.getAuthorityList();
		User loginUser=salesLoginUser.getUser();
		
		mockMvc.perform(get("/info/create")
				.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isForbidden());
	}

	/**
	 * @author namikitsubasa
	 */
	@Test
	public void お知らせを作成する_成功() throws Exception {

		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=adminloginUser.getAuthorityList();
		User loginUser=adminloginUser.getUser();

		int informationId=2;
		//addInformationService.addInformationの返り値はinformationIdとなる
		doReturn(informationId).when(addInformationService).addInformation(any(),any());
		
		//入力値をformに格納及び、テスト対象のメソッドを実行する
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/info/create/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("title", "タイトル")
				.param("content", "コンテンツ")
				.param("startPostedOn", "2020/07/20")
				.param("endPostedOn", "2020/07/30")
				.param("departmentId", "1,2");

		//実行の結果を検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isFound())
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/"))
				.andReturn();
		
		//入力値がmodelに入っているか検証
		FlashMap flashMap=result.getFlashMap();
		CreateInformationForm actualform=(CreateInformationForm)flashMap.get("CreateInformationForm");
				
		String actualMessage=(String) flashMap.get("message");

				assertEquals(actualform.getTitle(), "タイトル");
				assertEquals(actualform.getContent(), "コンテンツ");
				assertEquals(actualform.getEndPostedOn(), "2020/07/30");
				assertEquals(actualform.getDepartmentId(), "1,2");
				assertEquals(actualMessage, "お知らせの作成が完了しました");
				
		//サービスが予定の回数だけ呼ばれているか検証
		verify(addInformationService, times(1)).addInformation(any(),any());
	    verify(addInformationsDepartmentLinkService, times(1)).addInformationsDepartmentLink(any());
	}
	
	/**
	 * @author namikitsubasa
	 */
	@Test
	public void お知らせを作成する_バリデーション_nullが含まれる() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=adminloginUser.getAuthorityList();
		User loginUser=adminloginUser.getUser();
		
		//入力値をformに格納及び、テスト対象のメソッドを実行する
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/info/create/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("title", "")
				.param("content", "コンテンツ")
				.param("startPostedOn", "2020/07/20")
				.param("endPostedOn", "2020/07/30")
				.param("departmentId", "1,2");
		
		//実行の結果を検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("info/info-create"))
				.andExpect(model().errorCount(1))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.createInformationForm");
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> list = bindingResult.getFieldErrors("title");
		assertEquals(list.get(0).getDefaultMessage(), "タイトルを入力してください");
		
		//サービスが予定の回数だけ呼ばれているか検証
		verify(addInformationService, times(0)).addInformation(any(),any());
		verify(addInformationsDepartmentLinkService, times(0)).addInformationsDepartmentLink(any());
	}
	
	/**
	 * @author namikitsubasa
	 */
	@Test
	public void お知らせを作成する_バリデーション_スペースのみの情報がある() throws Exception {
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=adminloginUser.getAuthorityList();
		User loginUser=adminloginUser.getUser();
		
		//入力値をformに格納及び、テスト対象のメソッドを実行する
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/info/create/do")
				.with(csrf())
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("title", " ")
				.param("content", "	　")
				.param("startPostedOn", "2020/07/20")
				.param("endPostedOn", "2020/07/30")
				.param("departmentId", "1,2");
		
		//実行の結果を検証
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(SecurityMockMvcResultMatchers.authenticated())
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("info/info-create"))
				.andExpect(model().errorCount(2))
				.andReturn();
		
		//エラーメッセージが格納されているか検証
		Object object =  result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.createInformationForm");
		BindingResult bindingResult = (BindingResult) object;
		List<FieldError> list = bindingResult.getFieldErrors("title");
		assertEquals(list.get(0).getDefaultMessage(), "タイトルを入力してください");
		
		//サービスが予定の回数だけ呼ばれているか検証
		verify(addInformationService, times(0)).addInformation(any(),any());
		verify(addInformationsDepartmentLinkService, times(0)).addInformationsDepartmentLink(any());
	}
	

}
