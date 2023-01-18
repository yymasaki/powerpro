package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.example.domain.PresentationComment;
import com.example.domain.PresentationDocument;
import com.example.domain.PresentationFavorite;
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
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class PresentationMapperTest {

    @Autowired
    private PresentationMapper presentationMapper;

    // Operationオブジェクトを実行したい順番に繋げる
    public static Operation insert_ops = sequenceOf(
            DepartmentTestSql.DEPARTMENT_INSERT,
            UserTestSql.USER_INSERT,
            PresentationTestSql.PRESENTATION_INSERT,
            PresentationUsersLinkTestSql.PRESENTATION_USERSLINK_INSERT,
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
    static void tearDownAfterClass() {
        // 関連テーブルのデータを削除する
        DbSetup deleteDbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
        deleteDbSetup.launch();
    }

    @BeforeEach
    void setUp() throws Exception {
        assertEquals("パワプロエンジニアテスト発表サンプル", presentationMapper.showPresentationDetail(1).getTitle());
        assertEquals("発表サンプル2", presentationMapper.showPresentationDetail(2).getTitle());
        assertEquals("発表サンプル3", presentationMapper.showPresentationDetail(3).getTitle());
        System.out.println(presentationMapper.showPresentationDetail(1).getTitle());
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("発表詳細を表示する")
    public void show_Presentation_Detail() throws Exception {
        Integer presentationId = 1;
        Presentation presentation = presentationMapper.showPresentationDetail(presentationId);
        assertAll(
                () -> assertNotNull(presentation),
                () -> assertEquals("パワプロエンジニアテスト発表サンプル", presentation.getTitle()),
                () -> assertEquals(
                        "・エンジニアのスキルを可視化するシステム。・社内での利用を想定。・スペックシートをシステム内でしてExcel出力でき、今後PDF化にも対応する予定。現状Excelシートを入力してPDF化し、メールで添付送信する形式からシンプルなフローに移行できる。・管理者、営業はユーザー一覧からエンジニア情報を閲覧することができ、必要なスキルをもったエンジニアを探し出す事ができる。",
                        presentation.getContent()),
                () -> assertEquals("11月下旬", presentation.getPreferredDate()),
                () -> assertEquals(LocalDate.parse("2022-11-25"), presentation.getPresentationDate()),
                () -> assertEquals(0, presentation.getStage()),
                () -> assertEquals(1, presentation.getVersion()));
    }

    @Test
    @DisplayName("総いいね数の取得")
    public void count_Presentation_Favorite() {
        Integer presentationId = 1;
        Integer favoriteCount = presentationMapper.countPresentationFavorite(presentationId);
        assertNotNull(favoriteCount);
        assertEquals(3, favoriteCount);
    }

    @Test
    @DisplayName("該当いいねListの取得")
    public void select_Presentation_Favorite() {
        Integer presentationId = 1;
        List<PresentationFavorite> favoriteList = presentationMapper.showPresentationFavorite(presentationId);
        assertAll(
        () -> assertNotNull(favoriteList),
        () -> assertEquals(1, favoriteList.get(0).getPresentationFavoriteId()),
        () -> assertEquals(1, favoriteList.get(0).getPresentationId()),
        () -> assertEquals(1, favoriteList.get(0).getUserId())
        );
    }

    @Test
    @DisplayName("いいね押下時のインサート")
    public void insert_Presentation_Favorite() {
        Integer userId = 4;
        Integer presentationId = 1;
        presentationMapper.insertPresentationFavorite(userId, presentationId);

        // 1回いいね押下したのでカウントが４に増える
        Integer favoriteCount = presentationMapper.countPresentationFavorite(presentationId);
        assertNotNull(favoriteCount);
        assertEquals(4, favoriteCount);
    }

    @Test
    @DisplayName("いいね解除時のデリート")
    public void delete_Presentation_Favorite() {
        Integer userId = 4;
        Integer presentationId = 1;
        presentationMapper.deletePresentationFavorite(userId, presentationId);

        // 1回いいね解除したのでカウントが３に減る
        Integer favoriteCount = presentationMapper.countPresentationFavorite(presentationId);
        assertNotNull(favoriteCount);
        assertEquals(3, favoriteCount);
    }

    @Test
    @DisplayName("コメント投稿時のインサート")
    public void insert_Presentation_Comment() {
        PresentationComment presentationComment = new PresentationComment();
        presentationComment.setPresentationComment("単体テストコメント");
        presentationComment.setPresentationCommentCreatorId(4);
        presentationComment.setPresentationCommentPresentationId(1);
        Integer count = presentationMapper.insertPresentationComment(presentationComment);
        // presentaationID:1の総コメント数が４に増える
        Presentation presentation = presentationMapper.showPresentationDetail(1);
        List<PresentationComment> list = presentation.getPresentationCommentList();
        assertEquals(4, list.size());
        // インサートしたレコード数
        assertEquals(1, count);

    }

    @Test
    @DisplayName("コメント削除時のデリート")
    public void delete_Presentation_Comment() {
        Integer presentationCommentId = 1;
        Integer count = presentationMapper.deletePresentationComment(presentationCommentId);
        // presentaationID:3の総コメント数が１に減る
        Presentation presentation = presentationMapper.showPresentationDetail(1);
        List<PresentationComment> list = presentation.getPresentationCommentList();
        assertEquals(2, list.size());
        // インサートしたレコード数
        assertEquals(1, count);
    }

	@Test
	@DisplayName("PresentationのユーザーID検索")
	public void selectByUserIdTest() {
		Integer loginUserId = 1;
		List<Presentation> actual = presentationMapper.selectByUserId(loginUserId);
		// 件数とタイトルが一致しているかの確認
		assertEquals(3, actual.size());
		assertEquals("パワプロエンジニアテスト発表サンプル", actual.get(2).getTitle());
		assertEquals("発表サンプル2", actual.get(1).getTitle());
		assertEquals("発表サンプル3", actual.get(0).getTitle());
	}

	@Test
	@DisplayName("presentation_idの取得")
	public void selectPresentationId() {
		Integer loginUserId = 1;
		List<Presentation> actual = presentationMapper.selectPresentationId(loginUserId);
		assertEquals(1, actual.size());
	}

	@Test
	@DisplayName("名前でユーザー検索")
	public void selectUserByName() {
		String name = "太郎";
		List<User> actual = presentationMapper.selectUserByName(name);
		// 件数とユーザーIDが一致しているかの確認
		assertEquals(1, actual.size());
		assertEquals(1, actual.get(0).getUserId());
	}

	@Test
	@DisplayName("応募情報の登録と一時保存レコードの取得")
	public void insertPresnetationTest() {
		// presentationのインサート
		Presentation presentation = setPresentation();
		presentation.setPresentationId(4);
		Integer count = presentationMapper.insertPresentation(presentation);
		assertEquals(1, count);

		// presentation_users_linkのインサート
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setUserId(4);
		userList.add(user1);
		User user2 = new User();
		user2.setUserId(5);
		userList.add(user2);
		Integer linkCount = presentationMapper.insertPresentationUsersLink(presentation.getPresentationId(), userList);
		assertEquals(2, linkCount);

		// 上記で保存した一時保存presentationの取得・確認
		Integer loginUserId = 1;
		List<Presentation> savedPresentation = presentationMapper.selectSavedPresentation(loginUserId);
		assertEquals(1, savedPresentation.size());
		assertEquals("テストコンテンツ", savedPresentation.get(0).getContent());
		assertEquals(2, savedPresentation.get(0).getPresenterList().size());
		assertEquals("JUnitテスト四郎", savedPresentation.get(0).getPresenterList().get(0).getName());

	}

	@Test
	@DisplayName("presentation_users_linkの削除")
	public void deletePresentationUsersLinkTest() {
		Integer presentationId = 1;
		Integer loginUserId = 1;
		// 削除する前の件数確認
		List<Presentation> before = presentationMapper.selectByUserId(loginUserId);
		assertEquals(3, before.size());

		Integer count = presentationMapper.deletePresentationUsersLink(presentationId);
		assertEquals(3, count);
		// 削除後の件数確認
		List<Presentation> after = presentationMapper.selectByUserId(loginUserId);
		assertEquals(2, after.size());
	}


	@Test
	@DisplayName("Presentationの更新")
	public void updatePresentation() {
		Presentation presentation = setPresentation();
		Integer count = presentationMapper.updatePresentation(presentation);
		Presentation actual = presentationMapper.showPresentationDetail(1);
		// 件数とアップデート内容の確認
		assertEquals(1, count);
		assertNotNull(actual);
		assertEquals("アップデートタイトル", actual.getTitle());
	}

	@Test
	@DisplayName("presentation_documentsをインサート")
	public void insertPresentationDocumentsTest() {
		List<PresentationDocument> documentList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			PresentationDocument document = new PresentationDocument();
			document.setDocumentPath("testpath");
			document.setPresentationDocumentId(i);
			document.setDocumentTablePresentationId(1);
			documentList.add(document);
		}
		Integer presentationId = 1;
		Integer count = presentationMapper.insertPresentationDocuments(presentationId, documentList);
		Presentation presentation = presentationMapper.showPresentationDetail(presentationId);
		// 件数と内容の一致確認
		assertEquals(2, count);
		assertEquals(3, presentation.getPresentationDocumentList().size());
		assertEquals("testpath", presentation.getPresentationDocumentList().get(1).getDocumentPath());
//		assertEquals("testpath", presentation.getPresentationDocumentList().get(2).getDocumentPath());
	}

	@Test
	@DisplayName("presentation_documentsを削除")
	public void deletePresentationDocuments() {
		Integer presentationId = 1;
		Integer count = presentationMapper.deletePresentationDocuments(presentationId);
		assertEquals(1, count);
		// Presentation presentation =
		// presentationMapper.showPresentationDetail(presentationId);
		// assertEquals(3, presentation.getPresentationDocumentList().size());
	}

	// Presentationをセットするメソッド
	public Presentation setPresentation() {
		Presentation presentation = new Presentation();
		presentation.setPresentationId(1);
		presentation.setTitle("アップデートタイトル");
		presentation.setContent("テストコンテンツ");
		presentation.setPreferredDate("11月下旬");
		presentation.setStage(1);
		presentation.setCreatorUserId(1);
		presentation.setVersion(2);
		PresentationDocument document = new PresentationDocument();
		List<PresentationDocument> documentList = new ArrayList<>();
		document.setDocumentPath("testpath");
		document.setDocumentTablePresentationId(1);
		document.setPresentationDocumentId(1);
		documentList.add(document);
		presentation.setPresentationDocumentList(documentList);
		return presentation;
	}



}
