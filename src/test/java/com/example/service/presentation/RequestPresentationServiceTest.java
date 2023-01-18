package com.example.service.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.example.domain.EditRequestComment;
import com.example.domain.Presentation;
import com.example.domain.User;
import com.example.mapper.EditRequestCommentMapper;
import com.example.mapper.PresentationMapper;
import com.example.mapper.RequestPresentationMapper;

@SpringBootTest
class RequestPresentationServiceTest {

	@Mock
	private RequestPresentationMapper requestPresentationMapper;

	@Mock
	private EditRequestCommentMapper editRequestCommentMapper;

	@Mock
	private PresentationMapper presentationMapper;

	@Mock
	private MailSender mailSender;

	@Mock
	private HttpServletRequest httpServletRequest;

	@InjectMocks
	private RequestPresentationService target;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("応募状況一覧を取得し返す")
	void requestPresentationsTest() {
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
			presentation.setFavoriteCount(1);

			User user = new User();
			user.setUserId(i);
			user.setName("テスト" + i);
			userList.add(user);
			presentation.setPresenterList(userList);
			presentationList.add(presentation);
		}

		when(requestPresentationMapper.requestPresentations()).thenReturn(presentationList);
		// when(presentationMapper.countPresentationFavorite(anyInt()));

		// 実測値との比較
		List<Presentation> actual = target.requestPresentations();
		assertEquals(presentationList, actual);

		// 呼び出し回数の検証
		verify(requestPresentationMapper, times(1)).requestPresentations();
		verify(presentationMapper, times(5)).countPresentationFavorite(anyInt());

	}

	@Test
	@DisplayName("検索条件に一致する発表会応募一覧画面を表示する")
	void searchRequestPresentationsTest() throws Exception {
		// モック戻り値設定
		Presentation presentation = new Presentation();
		User user = new User();
		presentation.setStage(1);
		user.setDepartmentId(1);
		user.setName("テスト太郎");
		presentation.setUser(user);
		String date = "2022-11-17";
		LocalDate localdate = LocalDate.parse(date);
		presentation.setPresentationDate(localdate);
		List<Presentation> presentationList = new ArrayList<>();
		presentationList.add(presentation);

		when(requestPresentationMapper.searchRequestPresentations(presentation)).thenReturn(presentationList);

		// 実測値との比較
		List<Presentation> actual = target.searchRequestPresentations(presentation);
		assertEquals(presentationList, actual);

		// 呼び出し回数の検証
		verify(requestPresentationMapper, times(1)).searchRequestPresentations(presentation);
	}

	@Test
	@DisplayName("発表会日時を決定した際の処理")
	void updatePresentationByAdminTest() throws Exception {
		// 更新項目
		Integer presentationId = 6;
		Integer stage = 0;
		Integer updaterUserId = 1;
		String date = "2022-11-17";

		Presentation presentation = new Presentation();
		presentation.setPresentationId(presentationId);
		presentation.setPresentationDate(LocalDate.parse(date));
		presentation.setStage(stage);
		presentation.setUpdaterUserId(updaterUserId);

		// doNothing().when(requestPresentationMapper).updatePresentation(presentation);

		// 対象メソッドのupdate処理
		target.updateStageAndPresentationDate(presentationId, stage, updaterUserId, date);

		// 呼び出し回数の検証
		verify(requestPresentationMapper, times(1)).updatePresentation(presentation);
	}

	@DisplayName("詳細画面へ返す戻り値のテスト")
	void showRequestPresentationTest() {
		Integer presentationId = 1;
		Integer favoriteCount = 1;
		Presentation presentation = new Presentation();
		presentation.setPresentationId(presentationId);
		presentation.setTitle("テストタイトル");
		presentation.setContent("テスト発表概要");
		presentation.setPreferredDate("11月上旬");
		LocalDate presentationDate = LocalDate.parse("2022-11-11");
		presentation.setPresentationDate(presentationDate);
		presentation.setStage(2);
		presentation.setFavoriteCount(favoriteCount);

		when(requestPresentationMapper.selectRequestPresentation(presentationId)).thenReturn(presentation);
		when(presentationMapper.countPresentationFavorite(presentationId)).thenReturn(favoriteCount);

		Presentation actual = target.showRequestPresentation(presentationId);
		assertEquals(presentation, actual, "取得したPresentationが異なります");
		assertEquals(favoriteCount, actual.getFavoriteCount(), "取得したいいね数が異なります");

		verify(requestPresentationMapper, times(1)).selectRequestPresentation(anyInt());
		verify(presentationMapper, times(1)).countPresentationFavorite(anyInt());
	}

	@Test
	@DisplayName("管理者アクション後のメール送信テスト")
	void sendMailAfterAdminPushButtonTest() {
		Integer stage = 0;
		User user = new User();
		user.setUserId(1);
		user.setName("テスト太郎");
		user.setEmail("test@rakus-partners.co.jp");
		List<User> userList = new ArrayList<>();
		userList.add(user);

		target.sendMailAfterAdminPushButton(userList, stage);

		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}

	@Test
	@DisplayName("申請中発表会に対して管理者がアクションした際のテスト_承認否認仮承認ver")
	void reactedRequestPresentationByAdminTest_承認否認仮承認() {
		Presentation presentation = new Presentation();
		presentation.setPresentationId(1);
		presentation.setTitle("テストタイトル");
		presentation.setContent("テスト発表会概要");
		presentation.setPreferredDate("11月上旬");
		presentation.setStage(0);
		User user = new User();
		user.setUserId(1);
		user.setName("テスト太郎");
		user.setEmail("test@rakus-partners.co.jp");
		List<User> userList = new ArrayList<>();
		userList.add(user);
		presentation.setPresenterList(userList);

		EditRequestComment editRequestComment = new EditRequestComment();
		editRequestComment.setEditRequestCommentPresentationId(1);

		when(requestPresentationMapper.updatePresentation(presentation)).thenReturn(1);
		doNothing().when(editRequestCommentMapper).insertAdminComment(editRequestComment);

		target.reactedRequestPresentationByAdmin(presentation, editRequestComment);

		verify(requestPresentationMapper, times(1)).updatePresentation(presentation);
		verify(editRequestCommentMapper, times(0)).insertAdminComment(editRequestComment);
	}

	@Test
	@DisplayName("申請中発表会に対して管理者がアクションした際のテスト_修正依頼ver")
	void reactedRequestPresentationByAdminTest_修正依頼() {
		Presentation presentation = new Presentation();
		presentation.setPresentationId(1);
		presentation.setTitle("テストタイトル");
		presentation.setContent("テスト発表会概要");
		presentation.setPreferredDate("11月上旬");
		presentation.setStage(0);
		User user = new User();
		user.setUserId(1);
		user.setName("テスト太郎");
		user.setEmail("test@rakus-partners.co.jp");
		List<User> userList = new ArrayList<>();
		userList.add(user);
		presentation.setPresenterList(userList);

		EditRequestComment editRequestComment = new EditRequestComment();
		editRequestComment.setEditRequestCommentId(1);
		editRequestComment.setEditRequestComment("テスト修正依頼コメント");
		editRequestComment.setEditRequestCommentPresentationId(1);

		when(requestPresentationMapper.updatePresentation(presentation)).thenReturn(1);
		doNothing().when(editRequestCommentMapper).insertAdminComment(editRequestComment);

		target.reactedRequestPresentationByAdmin(presentation, editRequestComment);

		verify(requestPresentationMapper, times(1)).updatePresentation(presentation);
		verify(editRequestCommentMapper, times(1)).insertAdminComment(editRequestComment);
	}

}
