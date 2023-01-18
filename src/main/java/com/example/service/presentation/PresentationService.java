package com.example.service.presentation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.LoginUser;
import com.example.domain.Presentation;
import com.example.domain.PresentationComment;
import com.example.domain.PresentationDocument;
import com.example.domain.PresentationFavorite;
import com.example.domain.User;
import com.example.form.PresentationForm;
import com.example.mapper.PresentationMapper;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationMapper presentationMapper;

	/**
	 * 発表会TOPに遷移するメソッド
	 * 
	 * @param loginUser
	 * @return presentationList
	 * 
	 */
	public List<Presentation> toPresentation(LoginUser loginUser) {
		List<Presentation> list = presentationMapper.selectByUserId(loginUser.getUser().getUserId());
		// 取得してきたPresentationを格納する用のリスト
		List<Presentation> presentationList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			Presentation presentation = list.get(i);
			presentation.putCurrentStage();
			Integer favorite = presentationMapper.countPresentationFavorite(presentation.getPresentationId());
			presentation.setFavoriteCount(favorite);
			presentationList.add(presentation);
		}

		return presentationList;
	}

	/**
	 * ページング用の発表個数のカウント
	 * 
	 * @param loginUser ログインユーザー
	 * @return 発表の個数
	 */
	public Integer countPresentation(LoginUser loginUser) {
		return presentationMapper.countPresentation(loginUser.getUser().getUserId());
	}

	/**
	 * 発表詳細画面を表示
	 * 
	 * @param presentationId 発表ID
	 * @return presentationオブジェクト
	 */
	public Presentation showPresentationDetail(Integer presentationId) {
		Presentation presentation = presentationMapper.showPresentationDetail(presentationId);
		presentation.putCurrentStage();

		return presentation;
	}

	/**
	 * コメントを投稿
	 * 
	 * @param presentationComment 発表コメント
	 * @param loginUser           ログインユーザー
	 */
	public void insertPresentationComment(PresentationComment presentationComment, LoginUser loginUser) {
		presentationComment.setPresentationCommentCreatorId(loginUser.getUser().getUserId());
		presentationMapper.insertPresentationComment(presentationComment);
	};

	/**
	 * コメントを削除
	 * 
	 * @param presentationCommentId 発表コメントID
	 */
	public void deletePresentationComment(Integer presentationCommentId) {
		presentationMapper.deletePresentationComment(presentationCommentId);
	}

	/**
	 * 応募フォーム入力内容を一時保存 or 登録する
	 * 
	 * @param form
	 * @param loginUser
	 * 
	 */
	public void registerPresentationData(PresentationForm form, LoginUser loginUser) {

		// presentation tableにinsert
		Presentation presentation = new Presentation();
		BeanUtils.copyProperties(form, presentation);
		if (form.getPreferredMonth() != null && form.getPreferredTerm() != null) {
			presentation.setPreferredDate(form.getPreferredMonth() + "月" + form.getPreferredTerm());
		}

		presentation.setUpdaterUserId(loginUser.getUser().getUserId());
		if (presentation.getVersion() == null) {
			presentation.setVersion(1);
		}

		// アップデート(一時保存中のレコード)かインサート（新規登録レコード）か分岐
		if (form.getSavedPresentation() != null) {
			presentationMapper.updatePresentation(presentation);
		} else {
			presentation.setCreatorUserId(loginUser.getUser().getUserId());
			presentationMapper.insertPresentation(presentation);
		}

		// 以下presentation_users_linkにinsertするためform→presentationにユーザをセット
		List<User> userList = new ArrayList<>();
		List<Integer> userIdList = Arrays.asList(form.getUserId1(), form.getUserId2(), form.getUserId3(),
				form.getUserId4(), form.getUserId5()); // formのIDをListにセット

		for (Integer userId : userIdList) {
			if (userId != null) {
				User user = new User();
				user.setUserId(userId);
				userList.add(user);
			}
		}

		// 新しくインサートしたpresentationのID持ってくる
		Integer presentationId = presentationMapper.selectPresentationId(loginUser.getUser().getUserId()).get(0)
				.getPresentationId();

		// 発表者が１人以上入力されていた場合の処理。
		// 一時保存中(再申請)のデータであれば一度全部消去して再度全部インサート、新規登録のものであればインサートのみ。
		if (userList.size() >= 1) {
			if (form.getSavedPresentation() != null) {
				presentationMapper.deletePresentationUsersLink(form.getPresentationId());
				presentationMapper.insertPresentationUsersLink(form.getPresentationId(), userList);
			} else {
				presentationMapper.insertPresentationUsersLink(presentationId, userList);
			}
		}

		// 以下presentation_documentsに関する記述
		List<PresentationDocument> documentList = new ArrayList<>();
		if (!(form.getFileFlag1()).equals("NoFile")) {
			Path filePath = uploadAction(form.getFile1());
			PresentationDocument document = new PresentationDocument();
			document.setDocumentPath(filePath.toString());
			documentList.add(document);
		}
		if (!(form.getFileFlag2()).equals("NoFile")) {
			Path filePath = uploadAction(form.getFile2());
			PresentationDocument document = new PresentationDocument();
			document.setDocumentPath(filePath.toString());
			documentList.add(document);
		}
		if (!(form.getFileFlag3()).equals("NoFile")) {
			Path filePath = uploadAction(form.getFile3());
			PresentationDocument document = new PresentationDocument();
			document.setDocumentPath(filePath.toString());
			documentList.add(document);
		}

		// 発表資料が１つ以上登録されていた場合の処理
		// 一時保存中のデータであれば一度全部消去して再度全部インサート、新規登録のものであればインサートのみ
		if (documentList.size() >= 1) {
			if (form.getSavedPresentation() != null && form.getSavedPresentation() == 1) {
				presentationMapper.deletePresentationDocuments(form.getPresentationId());
				presentationMapper.insertPresentationDocuments(form.getPresentationId(), documentList);
			} else if (form.getSavedPresentation() != null && form.getSavedPresentation() == 2) {
				presentationMapper.insertPresentationDocuments(form.getPresentationId(), documentList);
			} else {
				presentationMapper.insertPresentationDocuments(presentationId, documentList); // !!!!!
			}
		}
	}

	/**
	 * fileのアップロード処理
	 * 
	 * @param multipartFile
	 * @return filePath
	 */
	public Path uploadAction(MultipartFile multipartFile) {

		String fileName = multipartFile.getOriginalFilename();
		// 格納先のフォルダ 現時点ではpowerproプロジェクトのdocumentフォルダ内presntationフォルダに格納する
		Path filePath = Paths.get("C:/env/springworkspace/powerpro/document/presentation/" + fileName);

		// アップロードファイルを保存するためのコード。今回は保存しない。
//        try {
//            byte[] bytes = multipartFile.getBytes();
//            OutputStream stream = Files.newOutputStream(filePath);
//            stream.write(bytes);
//            return filePath;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
		return filePath;
	}

	/**
	 * 一時保存中のデータを取得して表示
	 * 
	 * @param loginUser
	 * @return presentation
	 */
	public Presentation getSavedPresentation(LoginUser loginUser) {

		Presentation presentation = new Presentation();
		try {
			presentation = presentationMapper.selectSavedPresentation(loginUser.getUser().getUserId()).get(0);
		} catch (Exception e) {
			e.getStackTrace();
			presentation.setStage(6); // データ有無のフラグ
		}
		return presentation;
	}


	/**
	 * 総いいね数を取得
	 * 
	 * @param presentationId 発表ID
	 * @return userIdを含めたいいねオブジェクトのList
	 */
	public List<PresentationFavorite> showPresentationFavorite(Integer presentationId) {
		List<PresentationFavorite> presentationFavoriteList = presentationMapper
				.showPresentationFavorite(presentationId);
		return presentationFavoriteList;
	}

	/**
	 * いいね押下時のインサート
	 * 
	 * @param userId         ユーザーID
	 * @param presentationId 発表ID
	 */
	public void insertPresentationFavorite(Integer userId, Integer presentationId) {
		presentationMapper.insertPresentationFavorite(userId, presentationId);
	}

	/**
	 * いいね解除時のデリート
	 * 
	 * @param userId         ユーザーID
	 * @param presentationId 発表ID
	 */
	public void deletePresentationFavorite(Integer userId, Integer presentationId) {
		presentationMapper.deletePresentationFavorite(userId, presentationId);
	}

	/**
	 * 再応募時にpresentation取得 共通化できたら、あとでする
	 */
	public Presentation getPresentation(Integer presentationId) {
		Presentation presentation = presentationMapper.showPresentationDetail(presentationId);

//		// 必要なPresentationDocumentだけ取り出してセットし直す(SQL結合の結果不要なレコードが取れてしまうが故の処理)
//		List<PresentationDocument> list = presentation.getPresentationDocumentList();
//		List<PresentationDocument> documentList = new ArrayList<>(); // 必要なものをセットするためのlist
//		for (int i = 0; i < list.size(); i++) {
//			PresentationDocument document = list.get(i);
//			if (document.getPresentationDocumentId() != null) {
//				documentList.add(document);
//			}
//		}
//		presentation.setPresentationDocumentList(documentList);
		return presentation;
	}

	/** 名前でユーザー取ってくる */
	public List<User> getUserByName(String name) {
		List<User> userList = presentationMapper.selectUserByName(name);
		return userList;
	}

	/** PresentationDocumentの削除 */
	public Integer deletePresentationDocument(String fileNrame, Integer presentationId) {

		Integer count = presentationMapper.deletePresentatinDocumentByName(fileNrame, presentationId);
		return count;
	}
}
