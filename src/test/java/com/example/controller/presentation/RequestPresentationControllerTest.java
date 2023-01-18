package com.example.controller.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.LoginUser;
import com.example.domain.Presentation;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.presentation.RequestPresentationService;

@SpringBootTest
class RequestPresentationControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private AdminloginUser adminloginUser;

	@MockBean
	private RequestPresentationService requestPresentationService;

	@InjectMocks
	private RequestPresentationController target;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		// SpringSecurityを適用させる
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("応募状況一覧画面の表示")
	void requestPresentationByAdminUser() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User adminUser = adminloginUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/presentation")
				.with(user(new LoginUser(adminUser, authorityList)));

		// モック戻り値設定
		List<Presentation> presentationList = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Presentation presentation = new Presentation();
			List<User> userList = new ArrayList<>();
			presentation.setPresentationId(i);
			presentation.setTitle("テストタイトル" + i);
			presentation.setContent("・テストコンテンツ" + i);
			presentation.setPreferredDate("11月下旬");
			String date = "2022-11-17";
			LocalDate localdate = LocalDate.parse(date);
			presentation.setPresentationDate(localdate);
			presentation.setStage(2);
			presentation.setCreatorUserId(1);
			String dateTime = "2022-11-17T12:00:00.00";
			LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
			presentation.setCreatedAt(localDateTime);
			presentation.setUpdaterUserId(1);
			presentation.setVersion(1);

			User user = new User();
			user.setUserId(i);
			user.setName("テスト" + i);
			userList.add(user);
			presentation.setPresenterList(userList);
			presentationList.add(presentation);
		}
		// 検索内容格納用
		Presentation searchPresentation = new Presentation();

		when(requestPresentationService.requestPresentations()).thenReturn(presentationList);

		// パスの有効性確認
		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("presentation/request-presentation"))
				.andReturn();

		// Mock化されたオブジェクトの呼び出し検証
		verify(requestPresentationService, times(1)).requestPresentations();
		verify(requestPresentationService, times(0)).searchRequestPresentations(searchPresentation);

		// modelスコープ内の値を確認
		ModelAndView mav = result.getModelAndView();
		assertEquals(presentationList, mav.getModel().get("presentationList"));
	}

//	@Test
//	@DisplayName("検索条件なしの場合は全ての応募一覧が表示される")
//	void searchRequestPresentationByAdminUser() throws Exception {
//		// 検索値を設定
//		String stage = "";
//		String departmentId = "";
//		String userName = "";
//		String presentationDate = "";
//
//		// ログイン認証用の情報
//		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
//		User adminUser = adminloginUser.getUser();
//
//		// URL、html、paramなどを設定
//		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/presentation")
//				.with(user(new LoginUser(adminUser, authorityList)))
//		.param("stage", "")
//		.param("departmentId", "")
//		.param("name", "")
//		.param("presentationDate", "");
//
//		// モック戻り値設定
//		List<Presentation> MockPresentationList = new ArrayList<>();
//		for (int i = 1; i <= 5; i++) {
//			Presentation presentation = new Presentation();
//			List<User> userList = new ArrayList<>();
//			presentation.setPresentationId(i);
//			presentation.setTitle("テストタイトル" + i);
//			presentation.setContent("・テストコンテンツ" + i);
//			presentation.setPreferredDate("11月下旬");
//			String date = "2022-11-17";
//			LocalDate localdate = LocalDate.parse(date);
//			presentation.setPresentationDate(localdate);
//			presentation.setStage(2);
//			presentation.setCreatorUserId(1);
//			String dateTime = "2022-11-17T12:00:00.00";
//			LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
//			presentation.setCreatedAt(localDateTime);
//			presentation.setUpdaterUserId(1);
//			presentation.setVersion(1);
//
//			User user = new User();
//			user.setUserId(i);
//			user.setName("テスト" + i);
//			userList.add(user);
//			presentation.setPresenterList(userList);
//			MockPresentationList.add(presentation);
//		}
//		
//		// 検索値をセット
//		SearchRequestPresentationForm form = new SearchRequestPresentationForm();
//		form.setStage(stage);
//		form.setDepartmentId(departmentId);
//		form.setName(userName);
//		form.setPresentationDate(presentationDate);
//		
//		when(requestPresentationService.searchRequestPresentations(any())).thenReturn(MockPresentationList);
//
//		// パスの有効性確認
//		MvcResult result = mockMvc.perform(getRequest).andExpect(status().isOk()).andExpect(model().hasNoErrors())
//				.andExpect(view().name("presentation/request-presentation"))
////				.andExpect(model().attribute("presentationList", presentationList))
//				.andReturn();
//
//		// Mock化されたオブジェクトの呼び出し検証
//		verify(requestPresentationService, times(1)).requestPresentations();
//		verify(requestPresentationService, times(0)).searchRequestPresentations();
//
//		// modelスコープ内の値を確認
//		ModelAndView mav = result.getModelAndView();
//		assertEquals(MockPresentationList, mav.getModel().get("presentationList"));
//	}

	@Test
	@DisplayName("検索値に一致した応募一覧を表示する")
	void searchExistingRequestPresentationByAdminUser() throws Exception {
		// 検索値を設定
		String stage = "2";
		String departmentId = "1";
		String userName = "Webエンジニア　次郎";
		String presentationDate = "2022-12-01";

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User adminUser = adminloginUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/presentation")
				.with(user(new LoginUser(adminUser, authorityList)))
				.param("stage", stage)
				.param("departmentId", departmentId)
				.param("name", userName)
				.param("presentationDate", presentationDate);

		// モック戻り値設定
		List<Presentation> mockPresentationList = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Presentation presentation = new Presentation();
			List<User> userList = new ArrayList<>();
			presentation.setPresentationId(i);
			presentation.setTitle("テストタイトル" + i);
			presentation.setContent("・テストコンテンツ" + i);
			presentation.setPreferredDate("11月下旬");
			String date = "2022-12-01";
			LocalDate localdate = LocalDate.parse(date);
			presentation.setPresentationDate(localdate);
			presentation.setStage(2);
			presentation.setCreatorUserId(1);
			String dateTime = "2022-11-17T12:00:00.00";
			LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
			presentation.setCreatedAt(localDateTime);
			presentation.setUpdaterUserId(1);
			presentation.setVersion(1);

			User user = new User();
			user.setUserId(i);
			user.setName("Webエンジニア");
			user.setDepartmentId(i);
			userList.add(user);
			presentation.setPresenterList(userList);
			mockPresentationList.add(presentation);
		}
		when(requestPresentationService.searchRequestPresentations(any())).thenReturn(mockPresentationList);

		// パスの有効性確認
		MvcResult result = mockMvc.perform(getRequest).andExpect(status().isOk()).andExpect(model().hasNoErrors())
				.andExpect(view().name("presentation/request-presentation"))
//				.andExpect(model().attribute("presentationList", presentationList))
				.andReturn();

		// Mock化されたオブジェクトの呼び出し検証
		verify(requestPresentationService, times(0)).requestPresentations();
		verify(requestPresentationService, times(1)).searchRequestPresentations(any());

		// modelスコープ内の値を確認
		ModelAndView mav = result.getModelAndView();
		assertEquals(mockPresentationList, mav.getModel().get("presentationList"));
	}

	@Test
	@DisplayName("検索値に一致した応募一覧がない場合はメッセージが表示される")
	void searchNotExistingRequestPresentationByAdminUser() throws Exception {
		// 検索値を設定
		String stage = "2";
		String departmentId = "1";
		String userName = "Webエンジニア　次郎";
		String presentationDate = "2022-12-01";

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User adminUser = adminloginUser.getUser();

		// URL、html、paramなどを設定
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/request/presentation")
				.with(user(new LoginUser(adminUser, authorityList)))
				.param("stage", stage)
				.param("departmentId", departmentId).param("name", userName)
				.param("presentationDate", presentationDate);

		// モック戻り値設定
		List<Presentation> presentationList = new ArrayList<>();
		when(requestPresentationService.searchRequestPresentations(any())).thenReturn(presentationList);

		MvcResult result = mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("presentation/request-presentation"))
				.andReturn();

		// Mock化されたオブジェクトの呼び出し検証
		verify(requestPresentationService, times(0)).requestPresentations();
		verify(requestPresentationService, times(1)).searchRequestPresentations(any());

		// modelスコープ内の値を確認
		ModelAndView mav = result.getModelAndView();
		String actualMessage = (String) mav.getModel().get("message");
		assertEquals("検索結果がありませんでした。", actualMessage);
	}

	@Test
	@DisplayName("発表日付を確定した際の処理")
	void dicidePresentationDateByAdmin() throws Exception {
		String presentationId = "1";
		String stage = "0";
		String presentationDate = "2022-12-01";

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User adminUser = adminloginUser.getUser();

		// 戻り値のJSONファイルに正しい値が入っているか確認
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders
				.get("/request/presentation/update-presentation").with(user(new LoginUser(adminUser, authorityList)))
				.param("loginUser", String.valueOf(adminUser.getUserId())).param("presentationId", presentationId)
				.param("stage", stage).param("presentationDate", presentationDate);

		// パスの有効性確認
		mockMvc.perform(getRequest).andExpect(status().isOk());
//				.andExpect(content().contentType("application/json"));

		verify(requestPresentationService, times(1)).updateStageAndPresentationDate(Integer.parseInt(presentationId),
				Integer.parseInt(stage), adminUser.getUserId(), presentationDate);
	}

	@DisplayName("申請発表会詳細画面にアクセスした際の遷移先とスコープのテスト")
	void showRequestPresentationTest() throws Exception {
		Presentation requestedPresentation = new Presentation();
		when(requestPresentationService.showRequestPresentation(anyInt())).thenReturn(requestedPresentation);
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User loginUser = adminloginUser.getUser();

		mockMvc.perform(get("/request/presentation/detail").param("presentationId", "1")
				.with(user(new LoginUser(loginUser, authorityList)))).andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("requestedPresentation", requestedPresentation))
				.andExpect(view().name("/presentation/request-presentation-detail"));
		// モック化したメソッドが正常に呼び出されたかどうかの検証
		verify(requestPresentationService, times(1)).showRequestPresentation(anyInt());
	}

	@Test
	@DisplayName("管理者が申請中の発表会に対して反応した際の遷移先とスコープのテスト_承認ver")
	void reactedRequestPresentationByAdminTestOfApprove() throws Exception {
		Presentation requestedPresentation = new Presentation();
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User loginUser = adminloginUser.getUser();
		when(requestPresentationService.showRequestPresentation(anyInt())).thenReturn(requestedPresentation);
		doNothing().when(requestPresentationService).reactedRequestPresentationByAdmin(any(), any());

		mockMvc.perform(post("/request/presentation/process").param("presentationId", "1").param("stage", "0")
				.param("presentationDate", "2022-11-11").with(csrf())
				.with(user(new LoginUser(loginUser, authorityList)))).andExpect(status().isFound())
				.andExpect(redirectedUrl("/presentation")).andExpect(flash().attribute("message", "0")).andDo(print())
				.andReturn();

		verify(requestPresentationService, times(1)).showRequestPresentation(anyInt());
		verify(requestPresentationService, times(1)).reactedRequestPresentationByAdmin(any(), any());
	}

	@Test
	@DisplayName("管理者が申請中の発表会に対して反応した際の遷移先とスコープのテスト_否認仮承認ver")
	void reactedRequestPresentationByAdminTestOfReject() throws Exception {
		Presentation requestedPresentation = new Presentation();
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User loginUser = adminloginUser.getUser();
		when(requestPresentationService.showRequestPresentation(anyInt())).thenReturn(requestedPresentation);
		doNothing().when(requestPresentationService).reactedRequestPresentationByAdmin(any(), any());

		mockMvc.perform(post("/request/presentation/process").param("presentationId", "1").param("stage", "5")
				.with(csrf()).with(user(new LoginUser(loginUser, authorityList)))).andExpect(status().isFound())
				.andExpect(redirectedUrl("/presentation")).andExpect(flash().attribute("message", "5"));

		verify(requestPresentationService, times(1)).showRequestPresentation(anyInt());
		verify(requestPresentationService, times(1)).reactedRequestPresentationByAdmin(any(), any());
	}

	@Test
	@DisplayName("管理者が申請中の発表会に対して反応した際の遷移先とスコープのテスト_差し戻しver")
	void reactedRequestPresentationByAdminOfModificationRepairTest() throws Exception {
		Presentation requestedPresentation = new Presentation();
		Collection<GrantedAuthority> authorityList = adminloginUser.getAuthorityList();
		User loginUser = adminloginUser.getUser();
		when(requestPresentationService.showRequestPresentation(anyInt())).thenReturn(requestedPresentation);
		doNothing().when(requestPresentationService).reactedRequestPresentationByAdmin(any(), any());

		mockMvc.perform(post("/request/presentation/process").param("presentationId", "1").param("stage", "3")
				.param("adminComment", "テストコメント").with(csrf()).with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isFound()).andExpect(redirectedUrl("/presentation"))
				.andExpect(flash().attribute("message", "3"));

		verify(requestPresentationService, times(1)).showRequestPresentation(anyInt());
		verify(requestPresentationService, times(1)).reactedRequestPresentationByAdmin(any(), any());

	}
}
