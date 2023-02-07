package com.example.service.presentation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.common.Stage;
import com.example.domain.EditRequestComment;
import com.example.domain.Presentation;
import com.example.domain.User;
import com.example.mapper.EditRequestCommentMapper;
import com.example.mapper.PresentationMapper;
import com.example.mapper.RequestPresentationMapper;

@Service
@Transactional
public class RequestPresentationService {

	@Autowired
	private RequestPresentationMapper requestPresentationMapper;

	@Autowired
	private PresentationMapper presentationMapper;

	@Autowired
	private EditRequestCommentMapper editRequestCommentMapper;

	@Autowired
	private MailSender mailSender;

	/**
	 * 応募状況画面を表示する
	 * 
	 * @return
	 */
	public List<Presentation> requestPresentations() {
		List<Presentation> presentationList = requestPresentationMapper.requestPresentations();
		setPresentationFavorite(presentationList);

		return presentationList;
	}

	/**
	 * 応募一覧画面で検索する
	 * 
	 * @param searchPresentation
	 * @return
	 */
	public List<Presentation> searchRequestPresentations(Presentation searchPresentation) {
		List<Presentation> presentationList = requestPresentationMapper.searchRequestPresentations(searchPresentation);
		setPresentationFavorite(presentationList);
		return presentationList;
	}

	/**
	 * 発表会のいいね数をカウントし合計をセット
	 * (12/5 小渕追加 申請状況を追加する処理)
	 * 
	 * @param presentationList
	 * @return
	 */
	public void setPresentationFavorite(List<Presentation> presentationList) {
		presentationList.stream()
				.forEach(
						presentation -> presentation
								.setFavoriteCount(presentationMapper
										.countPresentationFavorite(presentation.getPresentationId())));
		presentationList.stream()
				.forEach(
						presentation -> presentation
								.putCurrentStage());
	}

	/** 申請中の発表会を取得し返す */
	public Presentation showRequestPresentation(Integer presentationId) {
		Optional<Presentation> optionalRequestedPresentation = requestPresentationMapper
				.selectRequestPresentation(presentationId);
		Presentation requestedPresentation = optionalRequestedPresentation.orElseGet(Presentation::new);
		requestedPresentation.setFavoriteCount(presentationMapper.countPresentationFavorite(presentationId));
		requestedPresentation.putCurrentStage();
		return requestedPresentation;
	}

	/**
	 * 発表会状況一覧の日付確定をした時にpresentationDate、stage、updaterUserIdが更新される
	 */
	public void updateStageAndPresentationDate(Integer presentationId, Integer stage, Integer updaterUserId,
			String date) {
		Presentation presentation = new Presentation();
		presentation.setPresentationId(presentationId);
		presentation.setStage(stage);
		LocalDate presentationDate = LocalDate.parse(date);
		presentation.setPresentationDate(presentationDate);
		presentation.setUpdaterUserId(updaterUserId);
		requestPresentationMapper.updatePresentation(presentation);
	}

	/**
	 * 申請中の発表会に対して管理者が反応した際の処理。基本はstageの更新。修正依頼をかけた場合はコメントの挿入処理を追加。
	 * 
	 * @param requestedPresentation
	 * @param editRequestComment
	 */
	public void reactedRequestPresentationByAdmin(Presentation requestedPresentation,
			EditRequestComment editRequestComment) {
		requestPresentationMapper.updatePresentation(requestedPresentation);

		// 修正依頼の場合は追加で下記処理が必要
		boolean hasComment = StringUtils.hasText(editRequestComment.getEditRequestComment());
		if (hasComment) {
			editRequestCommentMapper.insertAdminComment(editRequestComment);
		}

		// メール送信
		sendMailAfterAdminPushButton(requestedPresentation.getPresenterList(), requestedPresentation.getStage());
	}

	/**
	 * 申請中発表会詳細画面において管理者がアクション後にメールを送信
	 * 
	 * @param userList
	 * @param stage
	 */
	public void sendMailAfterAdminPushButton(List<User> userList, Integer stage) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("powerpro2022.rakuspertners@gmail.com");
		// 発表メンバーのメールアドレスを格納する配列
		String[] to = userList.stream()
				.map(User::getEmail).toArray(String[]::new);
		msg.setTo(to);
		msg.setSubject("【パワフルプロエンジニア】応募発表会について");
		StringBuilder stageOfText = new StringBuilder(Stage.of(stage).getMessageForRequest());
		StringBuilder textOfFirst = new StringBuilder("""
				※このメールは自動送信メールです。
				応募された発表会は管理者により
				""");
		StringBuilder endOfText = new StringBuilder("""
				されました。
				ログインしてご確認ください。
				""");
		StringBuilder msgText = new StringBuilder();
		msgText.append(textOfFirst).append(stageOfText).append(endOfText);
		msg.setText(msgText.toString());
		mailSender.send(msg);
	}
}
