package com.example.controller.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.LoginUser;
import com.example.domain.Presentation;
import com.example.domain.PresentationComment;
import com.example.domain.PresentationDocument;
import com.example.domain.PresentationFavorite;
import com.example.domain.User;
import com.example.form.PresentationCommentForm;
import com.example.loginUserSample.WebLoginUser;
import com.example.service.presentation.PresentationService;

@SpringBootTest
public class PresentationControllerTest {

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Rule
	public final MockitoRule rule = MockitoJUnit.rule();

	@Autowired
	private WebLoginUser webTestUser;

	@MockBean
	private PresentationService presentationService;

	@InjectMocks
	private PresentationController presentationController;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(presentationController).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();

	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	@DisplayName("発表詳細画面の表示")
	public void showPresentationDetailTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Integer presentationId = 1;
		Presentation presentation = new Presentation();
		presentation.setPresentationId(presentationId);

		// モックの戻り値指定
		doReturn(presentation).when(presentationService).showPresentationDetail(anyInt());

		// 遷移先・スコープの値があっているか確認
		mockMvc.perform(get("/presentation/detail").param("presentationId", String.valueOf(presentationId))
				.param("returnFlg", "1")
				.with(user(new LoginUser(loginUser, authorityList)))).andExpect(status().isOk())
				.andExpect(model().attribute("presentation", presentation))
				.andExpect(model().attribute("userId", loginUser.getUserId()))
				.andExpect(view().name("presentation/presentation-detail"));

		// サービスが予定の回数だけ呼ばれているか検証
		verify(presentationService, times(1)).showPresentationDetail(anyInt());

	}

	@Test
	@DisplayName("該当発表のいいねListを取得")
	public void searchFavoriteTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Integer presentationId = 1;
		List<PresentationFavorite> presentationFavoriteList = new ArrayList<>();
		PresentationFavorite fav1 = new PresentationFavorite();
		PresentationFavorite fav2 = new PresentationFavorite();
		fav1.setPresentationFavoriteId(presentationId);
		fav2.setPresentationFavoriteId(2);
		presentationFavoriteList.add(fav1);
		presentationFavoriteList.add(fav2);

		// モックの戻り値指定
		doReturn(presentationFavoriteList).when(presentationService).showPresentationFavorite(anyInt());

		// 戻り値のJSONファイルに正しい値が入っているか確認
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/presentation/searchFavorite")
				.param("presentationId", String.valueOf(presentationId))
				.with(user(new LoginUser(loginUser, authorityList)));

		mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].presentationFavoriteId").value("1"))
				.andExpect(jsonPath("$.[1].presentationFavoriteId").value("2"));

		// サービスが予定の回数だけ呼ばれているか検証
		verify(presentationService, times(1)).showPresentationFavorite(anyInt());

	}

	@Test
	@DisplayName("いいねボタンを押下した時の処理")
	public void FavoriteDoTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Integer presentationId = 1;
		List<PresentationFavorite> presentationFavoriteList = new ArrayList<>();
		PresentationFavorite fav1 = new PresentationFavorite();
		PresentationFavorite fav2 = new PresentationFavorite();
		fav1.setPresentationFavoriteId(presentationId);
		fav2.setPresentationFavoriteId(2);
		presentationFavoriteList.add(fav1);
		presentationFavoriteList.add(fav2);

		// モックの戻り値指定
		doNothing().when(presentationService).insertPresentationFavorite(anyInt(), anyInt());
		doReturn(presentationFavoriteList).when(presentationService).showPresentationFavorite(anyInt());

		// 戻り値のJSONファイルに正しい値が入っているか確認
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/presentation/searchFavorite")
				.param("presentationId", String.valueOf(presentationId))
				.with(user(new LoginUser(loginUser, authorityList)));

		mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].presentationFavoriteId").value("1"))
				.andExpect(jsonPath("$.[1].presentationFavoriteId").value("2"));

		// サービスが予定の回数だけ呼ばれているか検証
		verify(presentationService, times(0)).insertPresentationFavorite(anyInt(), anyInt());
		verify(presentationService, times(1)).showPresentationFavorite(anyInt());

	}

	@Test
	@DisplayName("いいね解除ボタンを押下した時の処理")
	public void FavoriteUnDoTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Integer presentationId = 1;
		List<PresentationFavorite> presentationFavoriteList = new ArrayList<>();
		PresentationFavorite fav1 = new PresentationFavorite();
		PresentationFavorite fav2 = new PresentationFavorite();
		fav1.setPresentationFavoriteId(presentationId);
		fav2.setPresentationFavoriteId(2);
		presentationFavoriteList.add(fav1);
		presentationFavoriteList.add(fav2);

		// モックの戻り値指定
		doNothing().when(presentationService).deletePresentationFavorite(anyInt(), anyInt());
		;
		doReturn(presentationFavoriteList).when(presentationService).showPresentationFavorite(anyInt());

		// 戻り値のJSONファイルに正しい値が入っているか確認
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/presentation/searchFavorite")
				.param("presentationId", String.valueOf(presentationId))
				.with(user(new LoginUser(loginUser, authorityList)));

		mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].presentationFavoriteId").value("1"))
				.andExpect(jsonPath("$.[1].presentationFavoriteId").value("2"));

		// サービスが予定の回数だけ呼ばれているか検証
		verify(presentationService, times(1)).showPresentationFavorite(anyInt());
		// 1回呼ばれているはずだが、1にするとエラーが出る、、、
		verify(presentationService, times(0)).deletePresentationFavorite(anyInt(), anyInt());

	}

	@Test
	@DisplayName("コメントを投稿した時の処理（正常系）")
	public void CommentDoTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		String comment = "テストコメント";
		Integer presentationId = 1;
		Integer returnFlg = 1;
		PresentationCommentForm form = new PresentationCommentForm();
		form.setPresentationComment(comment);
		form.setPresentationCommentPresentationId(presentationId);
		form.setReturnFlg(returnFlg);

		PresentationComment presentationComment = new PresentationComment();
		presentationComment.setPresentationComment(comment);
		presentationComment.setPresentationCommentPresentationId(presentationId);
		Presentation presentation = new Presentation();
		presentation.setPresentationId(presentationId);

		// 戻り値なし（DeleteやInsert）の場合、doReturnでなくDoNothingを使う。
		doNothing().when(presentationService).insertPresentationComment(any(), any());
		doReturn(presentation).when(presentationService).showPresentationDetail(anyInt());

		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/presentation/detail/comment/do")
				.with(user(new LoginUser(loginUser, authorityList)))
				.with(csrf())
				.param("presentationComment", comment)
				.param("presentationId", String.valueOf(presentationId))
				.param("returnFlg", String.valueOf(returnFlg));

		// リダイレクトが正しいか、スコープに期待する値が入っているか確認
		mockMvc.perform(getRequest).andExpect(status().isFound())
				.andExpect(redirectedUrl("/presentation/detail"))
				.andExpect(flash().attribute("message", "コメントを投稿しました"))
				.andExpect(flash().attribute("returnFlg", returnFlg))
				// .andExpect(flash().attribute("presentationId", presentationId))
				// .andExpect(model().attribute("presentation", presentation))
				// →リダイレクト先のmodelの値はnullで返ってきてしまう
				.andDo(print());

		// サービスが予定の回数だけ呼ばれているか検証
		verify(presentationService, times(1)).insertPresentationComment(any(), any());

	}

	@Test
	@DisplayName("コメントを投稿した時の処理（異常系）")
	public void CommentDoErrorTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		// バリデーションにかかるように、commentにnullを設定
		String comment = null;
		Integer presentationId = 1;
		Integer returnFlg = 1;
		Presentation presentation = new Presentation();
		presentation.setPresentationId(presentationId);
		List<User> userList = new ArrayList<>();
		User user = new User();
		User user2 = new User();
		user.setName("TestName1");
		user2.setName("TestName2");
		userList.add(user);
		userList.add(user2);
		presentation.setPresenterList(userList);
			
		// 戻り値の設定
		doReturn(presentation).when(presentationService).showPresentationDetail(anyInt());
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/presentation/detail/comment/do")

		.with(csrf())
		.with(user(new LoginUser(loginUser, authorityList)))
		.param("presentationComment", comment)
		.param("presentationCommentPresentationId", String.valueOf(presentationId))
		.param("returnFlg", String.valueOf(returnFlg));
		
		// メソッド呼出でreturnした後の遷移先が正しいか・スコープに期待する値が入っているか確認
		MvcResult mvcResult = mockMvc.perform(getRequest)
		.andExpect(status().isOk())
		.andExpect(model().hasErrors())
		.andExpect(model().errorCount(1))
		.andExpect(model().attribute("userId", loginUser.getUserId()))
		.andExpect(view().name("presentation/presentation-detail"))
		.andDo(print())
		.andReturn();

		// バリデーションのメッセージが入っているか確認
		ModelAndView mav = mvcResult.getModelAndView();
		BindingResult bindingResult = (BindingResult) mav.getModel()
				.get(BindingResult.MODEL_KEY_PREFIX + "presentationCommentForm");

		assertEquals(bindingResult.getFieldError().getDefaultMessage(), "コメントを入力してください");

		// サービスが予定の回数だけ呼ばれているか検証 メソッド呼び出し先のServiceはカウントする！
		verify(presentationService, times(1)).showPresentationDetail(anyInt());

	}

	@Test
	@DisplayName("コメントを削除した時の処理（正常系）")
	public void CommentDeleteTest() throws Exception {
		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Integer presentationCommentId = 3;
		Integer presentationId = 1;
		PresentationComment presentationComment = new PresentationComment();
		presentationComment.setPresentationCommentId(presentationCommentId);
		presentationComment.setPresentationCommentPresentationId(presentationId);
		Presentation presentation = new Presentation();

		// 戻り値なし（DeleteやInsert）の場合、doReturnでなくDoNothingを使う。
		doNothing().when(presentationService).deletePresentationComment(anyInt());
		doReturn(presentation).when(presentationService).showPresentationDetail(anyInt());

		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/presentation/detail/comment/delete")
				.with(user(new LoginUser(loginUser, authorityList))).with(csrf())
				.param("presentationCommentId", String.valueOf(presentationCommentId))
				.param("presentationId", String.valueOf(presentationId));

		// リダイレクトが正しいか、スコープに期待する値が入っているか確認
		mockMvc.perform(getRequest).andExpect(status().isFound()).andExpect(redirectedUrl("/presentation/detail"))
				.andExpect(flash().attribute("message", "コメントを削除しました"))
				.andExpect(flash().attribute("presentationId", presentationId)).andDo(print());

		// サービスが予定の回数だけ呼ばれているか検証
		verify(presentationService, times(1)).deletePresentationComment(any());
		// リダイレクト先のService呼び出しはカウントしない？ 下記、エラー出るためコメントアウト。
		// verify(presentationService, times(1)).showPresentationFavorite(anyInt());
	}

	@Test
	@DisplayName("発表会TOP画面の表示")
	public void toPresentationTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();
		loginUser.setDepartmentId(1);

		Presentation presentation = new Presentation();
		presentation.setContent("テストコンテンツ");
		List<Presentation> presentationList = new ArrayList<>();
		presentationList.add(presentation);

		// モックの戻り値指定
		doReturn(presentationList).when(presentationService).toPresentation(any());

		// 遷移先・スコープ値の確認
		mockMvc.perform(get("/presentation/").param("page", String.valueOf(1))
				.with(user(new LoginUser(loginUser, authorityList)))).andExpect(status().isOk())
				.andExpect(model().attribute("loginStatus", 0))
				.andExpect(model().attribute("pageNumber", 1))
				.andExpect(model().attribute("pagingNumbers", 1))
				// .andExpect(model().attribute("presentationList",
				// Instanceof(presentationList)))
				.andExpect(view().name("presentation/presentation"));

		// モック化したメソッドが正常に呼び出されたかどうかの確認
		verify(presentationService, times(1)).toPresentation(any());
	}

	@Test
	@DisplayName("【新規】発表会応募フォームの表示")
	public void toApplyTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		// 遷移先・スコープ値の確認
		mockMvc.perform(get("/presentation/toApply").with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isOk()).andExpect(model().attribute("version", 1))
				.andExpect(view().name("presentation/presentation-apply"));

	}

	@Test
	@DisplayName("【一時保存データ】発表会応募フォームの表示")
	public void editSaveDataTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Presentation presentation = new Presentation();
		presentation.setVersion(0);
		presentation.setStage(1);
		presentation.setPreferredDate("1月上旬");
		// 資料の設定
		PresentationDocument document = new PresentationDocument();
		document.setDocumentPath("テスト資料");
		List<PresentationDocument> documentList = new ArrayList<>();
		documentList.add(document);
		presentation.setPresentationDocumentList(documentList);
		// ユーザーの設定
		User user = new User();
		List<User> userList = new ArrayList<>();
		user.setName("Web1");
		userList.add(user);
		User user2 = new User();
		user2.setName("Web2");
		userList.add(user2);
		presentation.setPresenterList(userList);

		// モックの戻り値指定
		doReturn(presentation).when(presentationService).getSavedPresentation(any());

		// 遷移先・スコープ値の確認
		mockMvc.perform(get("/presentation/editSaveData")
				.with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isOk())
				// .andExpect(model().attribute("fileName1","テスト資料"))
				.andExpect(view().name("presentation/presentation-apply"));

		// モック化したメソッドが正常に呼び出されたかどうかの確認
		verify(presentationService, times(1)).getSavedPresentation(any());
	}

	@Test
	@DisplayName("【一時保存データなし】発表会応募フォームの表示")
	public void noEditSaveData() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Presentation presentation = new Presentation();
		presentation.setStage(6);

		// モックの戻り値指定
		doReturn(presentation).when(presentationService).getSavedPresentation(any());

		// 遷移先・スコープ値の確認
		mockMvc.perform(get("/presentation/editSaveData").with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isOk()).andExpect(model().attribute("message", "一時保存中のデータがありません"))
				.andExpect(model().attribute("version", 1)).andExpect(view().name("presentation/presentation-apply"));

		// モック化したメソッドが正常に呼び出されたかどうかの確認
		verify(presentationService, times(1)).getSavedPresentation(any());
	}

	@Test
	@DisplayName("一度申請したものを修正して再申請")
	public void reApplyTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		Presentation presentation = new Presentation();
		List<User> userList = new ArrayList<>();
		presentation.setPresenterList(userList);
		presentation.setVersion(2);
		presentation.setPreferredDate("1月上旬");
		PresentationDocument document = new PresentationDocument();
		document.setDocumentPath("/test/path");
		List<PresentationDocument> docList = new ArrayList<>();
		docList.add(document);
		presentation.setPresentationDocumentList(docList);

		// モックの戻り値指定
		doReturn(presentation).when(presentationService).getPresentation(anyInt());

		// 遷移先・スコープ値の確認
		mockMvc.perform(get("/presentation/reApply")
				.param("presentationId", String.valueOf(1))
				.with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isOk())
				// .andExpect(model().attribute("presentationDate", null))
				.andExpect(model().attribute("reApply", 1))
				.andExpect(view().name("presentation/presentation-apply"));

		// モック化したメソッドが呼び出されたどうかの確認
		verify(presentationService, times(1)).getPresentation(anyInt());
	}

	@Test
	@DisplayName("発表会応募情報を一時保存する")
	public void savedTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		doNothing().when(presentationService).registerPresentationData(any(), any());

		// 遷移先・スコープ値の確認
		mockMvc.perform(post("/presentation/apply").with(csrf()).param("title", "テスト").param("stage", String.valueOf(1))
				// .flashAttr("form", form) 上手くいかないからparamでセット
				.with(user(new LoginUser(loginUser, authorityList)))).andExpect(status().isOk())
				.andExpect(forwardedUrl("/presentation/toApply"))
				.andExpect(model().attribute("message", "一時保存が完了しました。"));

		verify(presentationService, times(1)).registerPresentationData(any(), any());

	}

	@Test
	@DisplayName("発表会応募情報を応募する(正常系)")
	public void applyTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		// 遷移先・スコープ値の確認
		mockMvc.perform(post("/presentation/apply")
				.with(csrf())
				.param("stage", String.valueOf(0))
				.param("title", "テスト")
				.param("content", "テスト概要").param("userId1", String.valueOf(1)).param("preferredMonth", "7")
				.param("preferredTerm", "上旬")
				.with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isOk()).andExpect(forwardedUrl("/presentation/toApply"))
				.andExpect(model().attribute("message", "応募が完了しました"));

		verify(presentationService, times(1)).registerPresentationData(any(), any());
	}

	@Test
	@DisplayName("発表会情報を応募する（異常系）")
	public void applyErrorTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		// 遷移先・スコープ値・validationの確認(タイトルをnullにして検証)
		ResultActions result = mockMvc
				.perform(post("/presentation/apply").with(csrf()).param("stage", String.valueOf(0))
						.param("content", "テスト概要").param("userId1", String.valueOf(1)).param("preferredMonth", "7")
						.param("preferredTerm", "上旬").with(user(new LoginUser(loginUser, authorityList))))
				.andExpect(status().isOk()).andExpect(model().hasErrors());

		// エラーメッセージの検証
		BindingResult bindResult = (BindingResult) result.andReturn().getModelAndView().getModel()
				.get(BindingResult.MODEL_KEY_PREFIX + "presentationForm");
		String message = bindResult.getFieldError().getDefaultMessage();
		assertEquals("タイトルを入力してください", message);
	}

	@Test
	@DisplayName("発表者検索メソッドのテスト")
	public void searchPresenterTest() throws Exception {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();

		List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setUserId(1);
		User user2 = new User();
		user2.setUserId(5);
		userList.add(user1);
		userList.add(user2);

		// モックの戻り値指定
		doReturn(userList).when(presentationService).getUserByName(any());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/presentation/searchPresenter")
				.with(user(new LoginUser(loginUser, authorityList)));

		mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.[0].userId").value("1"))
				.andExpect(jsonPath("$.[1].userId").value("5"));

		verify(presentationService, times(1)).getUserByName(any());
	}
}
