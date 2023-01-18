package com.example.service.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

import com.example.domain.LoginUser;
import com.example.domain.Presentation;
import com.example.domain.User;
import com.example.form.PresentationForm;
import com.example.loginUserSample.WebLoginUser;
import com.example.mapper.PresentationMapper;

@SpringBootTest
public class PresentationServiceApplyTest {

	@Mock
	private PresentationMapper presentationMapper;

	@InjectMocks
	private PresentationService target;

	@Autowired
	private WebLoginUser webTestUser;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDowaAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	// Presentationに値セット
	public Presentation setPresentation() {

		Presentation presentation = new Presentation();
		presentation.setContent("テストコンテンツ");
		presentation.setTitle("テスト");
		presentation.setPresentationId(1);
		presentation.setCreatorUserId(1);

		return presentation;
	}

	// PresentationFormに値セット
	public PresentationForm setPresentationForm() {

		PresentationForm form = new PresentationForm();
		form.setFileFlag1("NoFile");
		form.setFileFlag2("NoFile");
		form.setFileFlag3("NoFile");
		form.setUserId1(1);
		form.setUserId2(2);
		return form;
	}

	// Userに値セット
	public User setUser() {

		User user = new User();
		user.setName("テスト太郎");
		user.setUserId(1);
		user.setEmail("abc@def");
		return user;
	}

	@Test
	@DisplayName("発表会TOP表示")
	public void toPresentationTest() {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();
		LoginUser user = new LoginUser(loginUser, authorityList);
		Integer userId = 1;
		Integer presentationId = 1;


		List<Presentation> presentationList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Presentation presentation = new Presentation();
			presentation.setPresentationId(i);
			presentation.setStage(0);
			presentation.putCurrentStage();
			presentationList.add(presentation);
		}

		List<User> presenterList = new ArrayList<>();
		User presenter = setUser();
		presenterList.add(presenter);

		doReturn(presentationList).when(presentationMapper).selectByUserId(userId);
		doReturn(1).when(presentationMapper).countPresentationFavorite(presentationId);

		List<Presentation> actual = target.toPresentation(user);
		assertEquals(presentationList, actual);

		// 呼び出し回数確認
		verify(presentationMapper, times(1)).selectByUserId(userId);
		verify(presentationMapper, times(3)).countPresentationFavorite(anyInt());
	}

	@Test
	@DisplayName("一時保存中のデータ取得して表示")
	public void registerPresentationTest() {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();
		loginUser.setUserId(1);
		LoginUser user = new LoginUser(loginUser, authorityList);
		Integer userId = 1;

		List<Presentation> presentationList = new ArrayList<>();
		Presentation presentation = setPresentation();
		presentationList.add(presentation);

		doReturn(presentationList).when(presentationMapper).selectSavedPresentation(userId);

		Presentation actual = target.getSavedPresentation(user);
		assertEquals(presentation, actual);

		verify(presentationMapper, times(1)).selectSavedPresentation(userId);
	}

	@Test
	@DisplayName("応募フォーム入力内容を一時保存【登録】")
	public void registerpresentationDataTest() {

		// ログイン認証用の情報
		Collection<GrantedAuthority> authorityList = webTestUser.getAuthorityList();
		User loginUser = webTestUser.getUser();
		loginUser.setUserId(1);
		LoginUser user = new LoginUser(loginUser, authorityList);

		PresentationForm form = setPresentationForm();
		List<Presentation> presentationList = new ArrayList<>();
		Presentation presentation = setPresentation();
		presentationList.add(presentation);

		doReturn(presentationList).when(presentationMapper).selectPresentationId(presentation.getPresentationId());

		target.registerPresentationData(form, user);

		verify(presentationMapper, times(1)).selectPresentationId(1);
	}

	
	@Test
	@DisplayName("再応募時のpresentation取得")
	public void getPresentationTest() {

		List<Presentation> presentationList = new ArrayList<>();
		Presentation presentation = setPresentation();
		presentationList.add(presentation);
		Integer presentationId = presentation.getPresentationId();

		doReturn(presentation).when(presentationMapper).showPresentationDetail(presentationId);

		Presentation actual = target.getPresentation(presentationId);
		assertEquals(presentation, actual);

		verify(presentationMapper, times(1)).showPresentationDetail(presentationId);
	}

	@Test
	@DisplayName("名前でユーザー取得")
	public void getUserBynameTest() {
		String name = "テスト";
		List<User> userList = new ArrayList<>();
		User user = setUser();
		userList.add(user);

		doReturn(userList).when(presentationMapper).selectUserByName(name);

		List<User> actual = target.getUserByName(name);
		assertEquals(userList, actual);

		verify(presentationMapper, times(1)).selectUserByName(name);
	}
}
