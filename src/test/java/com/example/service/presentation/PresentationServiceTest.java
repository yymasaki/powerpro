package com.example.service.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Presentation;
import com.example.domain.PresentationComment;
import com.example.domain.PresentationFavorite;
import com.example.mapper.PresentationMapper;

@SpringBootTest
public class PresentationServiceTest {

    // private static final short IllegalArgumentException = 0;

    @InjectMocks
    private PresentationService presentationService;

    @Mock
    private PresentationMapper presentationMapper;

    @Mock
    private Presentation presentationMock;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    @DisplayName("発表詳細画面の表示")
    public void showPresentationDetailTest() {
        Integer presentationId = 1;
        Presentation presentation = new Presentation();
        presentation.setPresentationId(presentationId);

        // 戻り値を設定
        doReturn(presentation).when(presentationMapper).showPresentationDetail(anyInt());
        // presentationが返ってきているか確認
        assertEquals(presentationMapper.showPresentationDetail(presentationId), presentation);
        // 一回だけ呼ばれるかチェック
        verify(presentationMapper, times(1)).showPresentationDetail(anyInt());

        // Domainのメソッド呼び出しについてのテストはどのように行うのか？？
        // verify(presentationMock,times(1)).putCurrentStage();

    }

    @Test
    @DisplayName("コメント投稿")
    public void insertPresentationCommentTest() {
        PresentationComment presentationComment = new PresentationComment();

        // doNothing().when(presentationMapper).insertPresentationComment(presentationComment);
        presentationMapper.insertPresentationComment(presentationComment);
        
        // 一回だけ呼ばれるかチェック
        verify(presentationMapper, times(1)).insertPresentationComment(presentationComment);
        
    }

    @Test
    @DisplayName("コメント削除")
    public void deletePresentationCommentTest() {
        Integer presentationCommentId = 1;

        // doNothing().when(presentationMapper).deletePresentationComment(any());
        presentationMapper.deletePresentationComment(presentationCommentId);
        // 一回だけ呼ばれるかチェック
        verify(presentationMapper).deletePresentationComment(presentationCommentId);

    }

    @Test
    @DisplayName("総いいね数取得")
    public void showPresentationFavoriteTest() {
        Integer presentationId = 1;
        PresentationFavorite presentationFavorite = new PresentationFavorite();
        presentationFavorite.setPresentationFavoriteId(1);
        List<PresentationFavorite> favoriteList = new ArrayList<>();
        favoriteList.add(presentationFavorite);

        // 戻り値を設定
        doReturn(favoriteList).when(presentationMapper).showPresentationFavorite(presentationId);
        // presentationが返ってきているか確認
        assertEquals(presentationMapper.showPresentationFavorite(presentationId), favoriteList);
        // 一回だけ呼ばれるかチェック
        verify(presentationMapper, times(1)).showPresentationFavorite(presentationId);

    }

    @Test
    @DisplayName("いいね押下時のインサート")
    public void insertPresentationFavoriteTest() {
        Integer userId = 1;
        Integer presentationId = 1;

        presentationMapper.insertPresentationFavorite(userId, presentationId);
        // 一回だけ呼ばれるかチェック
        verify(presentationMapper, times(1)).insertPresentationFavorite(userId, presentationId);
    }

    @Test
    @DisplayName("いいね解除時のデリート")
    public void deletePresentationFavoriteTest() {
        Integer userId = 1;
        Integer presentationId = 1;

        presentationMapper.deletePresentationFavorite(userId, presentationId);
        // 一回だけ呼ばれるかチェック
        verify(presentationMapper, times(1)).deletePresentationFavorite(userId, presentationId);

    }
}
