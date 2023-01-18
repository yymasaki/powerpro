package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.domain.Presentation;
import com.example.domain.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.DepartmentTestSql;
import common_sql.EditRequestCommentsTestSql;
import common_sql.PresentationCommentsTestSql;
import common_sql.PresentationDocumentsTestSql;
import common_sql.PresentationFavoriteTestSql;
import common_sql.PresentationTestSql;
import common_sql.PresentationUsersLinkTestSql;
import common_sql.UserTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
class RequestPresentationMapperTest {

	@Autowired
	private RequestPresentationMapper target;

	/** Operationオブジェクトを実行したい順番に繋げる */
	public static Operation insert_ops = sequenceOf(DepartmentTestSql.DEPARTMENT_INSERT, UserTestSql.USER_INSERT,
			PresentationTestSql.PRESENTATION_INSERT, PresentationUsersLinkTestSql.PRESENTATION_USERSLINK_INSERT,
			PresentationCommentsTestSql.PRESENTATION_COMMENTS_INSERT,
			PresentationFavoriteTestSql.PRESENTATION_FAVORITE_INSERT,
			PresentationDocumentsTestSql.PRESENTATION_DOCUMENTS_INSERT,
			EditRequestCommentsTestSql.EDIT_REQUEST_COMMENTS_INSERT);

	// 接続に必要なDestinationオブジェクトを生成
	public static Destination dest = new DriverManagerDestination("jdbc:mysql://localhost:3306/powerpro_exam", "root",
			"mysqlmysql");

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// 関連テーブルのデータを削除する
		DbSetup deleteDbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		// テスト用データをインサートする
		DbSetup insertDbSetup = new DbSetup(dest, insert_ops);
		deleteDbSetup.launch();
		insertDbSetup.launch();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		// 関連テーブルのデータを削除する
		DbSetup deleteDbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		deleteDbSetup.launch();
	}

	@BeforeEach
	void setUp() throws Exception {
		assertEquals("パワプロエンジニアテスト発表サンプル", target.selectRequestPresentation(1).getTitle());
		assertEquals("発表サンプル2", target.selectRequestPresentation(2).getTitle());
		assertEquals("発表サンプル3", target.selectRequestPresentation(3).getTitle());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("該当の発表会を取得")
	void selectRequestPresentationTest() {
		Integer presentationId = 1;
		Presentation presentation = target.selectRequestPresentation(presentationId);
		assertNotNull(presentation);
		assertEquals("パワプロエンジニアテスト発表サンプル", presentation.getTitle(), "タイトルが異なります");
		assertEquals("11月下旬", presentation.getPreferredDate(), "発表見込日が異なります");
	}

	@Test
	@DisplayName("詳細画面のボタン押下後の更新処理")
	void updatePresentationTest() {
		Integer presentationId = 1;
		Presentation expectedPresentation = new Presentation();
		expectedPresentation.setPresentationId(presentationId);
		expectedPresentation.setStage(4);
		expectedPresentation.setUpdaterUserId(2);
		int actualUpdate = target.updatePresentation(expectedPresentation);
		Presentation actulalPresentation = target.selectRequestPresentation(presentationId);
		assertEquals(expectedPresentation.getStage(), actulalPresentation.getStage(), "更新したstageが異なります");
		assertEquals(expectedPresentation.getUpdaterUserId(), actulalPresentation.getUpdaterUserId(), "更新日時が異なります");
		assertEquals(1, actualUpdate, "更新件数が異なります");
	}

	@DisplayName("応募状況一覧画面を取得する")
	void requestPresentationsTest() throws Exception {
		List<Presentation> actual = target.requestPresentations();

		// 取得できた件数を取得(登録件数3件)
		assertEquals(3, actual.size());
	}

	@Test
	@DisplayName("応募状況一覧画面で検索し取得する")
	void searchRequestPresentationsTest() throws Exception {
		// 検索用の値をセット
		Presentation presentation = new Presentation();
		presentation.setStage(0);
		User user = new User();
		user.setDepartmentId(1);
		user.setName("JUnitテスト太郎");
		presentation.setUser(user);
		presentation.setPresentationDate(LocalDate.parse("2022-11-25"));

		// 実測値取得
		List<Presentation> actual = target.searchRequestPresentations(presentation);

		// 実測値の件数と該当の発表会を検証
		assertEquals(1, actual.size());
		assertEquals("パワプロエンジニアテスト発表サンプル", actual.get(0).getTitle());
	}

}
