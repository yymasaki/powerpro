package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Presentation;
import com.example.domain.PresentationComment;
import com.example.domain.PresentationDocument;
import com.example.domain.PresentationFavorite;
import com.example.domain.User;

@Mapper
public interface PresentationMapper {

	/**
	 * ログインユーザーのpresentationを取得
	 */
	List<Presentation> selectByUserId(Integer loginUserId);

	Integer countPresentation(Integer loginUserId);

	/**
	 * presentationインサート
	 */
	Integer insertPresentation(Presentation presentation);

    /**
	 * presentation_users_linkインサート
	 */
	Integer insertPresentationUsersLink(Integer presentationId, List<User> userList);
    
	/**
     * presentation_id取得
	 */
    List<Presentation> selectPresentationId(Integer loginUserId);
    
	/**
     * 一時保存レコードの取得
	 */
	List<Presentation> selectSavedPresentation(Integer loginUserId);


	/**
	 * PresentationのUPDATE（一時保存データの応募・再保存） 何が返ってくるの？？
	 * 
	 */
	Integer updatePresentation(Presentation presentation);

	/**
	 * presentation_users_link のdelete
	 */
	Integer deletePresentationUsersLink(Integer presentationId);

	/**
	 * 発表詳細情報を取得
	 * @param presentationId 発表ID
     * @return presentationオブジェクト
	 */
    Presentation showPresentationDetail(@Param("presentationId") Integer presentationId);
    
	/**
	 * 総いいね数のみの取得
	 * @param presentationId 発表ID
	 * @return 総いいね数
	 */
	Integer countPresentationFavorite(@Param("presentationId") Integer presentationId);

    /**
     * 該当発表のいいねListの取得
     * @param presentationId 発表ID
     * @return いいねList
     */
    List<PresentationFavorite> showPresentationFavorite(@Param("presentationId") Integer presentationId);

    /**
     * いいねボタンを押下した時の処理
     * @param userId ユーザーID
     * @param presentationId 発表ID
     */
    Integer insertPresentationFavorite(@Param("userId") Integer userId, @Param("presentationId") Integer presentationId);

    /**
     * いいね解除ボタンを押下したした時の処理
     * @param userId ユーザーID
     * @param presentationId 発表ID
     */
    Integer deletePresentationFavorite(@Param("userId") Integer userId, @Param("presentationId") Integer presentationId);

	/**
     * コメントをインサート
     * @param presentationComment 発表コメント
	 */
	Integer insertPresentationComment(PresentationComment presentationComment);

	/**
     * コメントを削除（stageを変更）
     * @param presentationCommentId 発表コメントID
	 */
	Integer deletePresentationComment(@Param("presentationCommentId") Integer presentationCommentId);

	/**
	 * presentation_documentsをインサート
	 * 
	 */
	Integer insertPresentationDocuments(Integer presentationId, List<PresentationDocument> documentList);

	/**
	 * presentation_documentsをアップデート
	 * 
	 * Integer updatePresentationDocuments(Presentation presentation);
	 */

	/**
	 * presentation_documentsを削除
	 */
	Integer deletePresentationDocuments(Integer presentationId);


	/**
	 * 名前でユーザー検索
	 */
	List<User> selectUserByName(String name);

	/**
	 * presentationDocumentの削除
	 * 
	 */
	Integer deletePresentatinDocumentByName(String fileName, Integer presentationId);

}
