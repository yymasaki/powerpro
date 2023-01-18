package com.example.service.spec;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.common.Stage;
import com.example.domain.User;
import com.example.service.user.GetSpecificUserService;

@Service
@Transactional
public class SendSpecMailService {

	@Autowired
	private GetSpecificUserService getSpecificUserService;
	
	@Autowired
	private MailSender sender;

	@Autowired
	private HttpServletRequest request;
	
	/**
	 * スペックシート変更の旨をエンジニアにメールするメソッド.
	 * 
	 * @param process 処理内容
	 * @param adminComment 管理者コメント
	 * @param specsheetId スペックシートID
	 * @param userId 対象ユーザーID
	 * @param stage ステージ
	 */
	public void sendSpecMail(String process, String adminComment, Integer specsheetId, Integer userId, Integer stage) {
		User user = getSpecificUserService.get(userId);
		String url = request.getRequestURL().toString();
		if(Stage.SENT_BACK.getKey().equals(stage)) {
			url = url.replace(request.getRequestURI(), "/request/spec?specsheetId="+specsheetId+"&userId="+userId+"&stage="+stage);
		}else {
			url = url.replace(request.getRequestURI(), "/skill/spec?userId=" + user.getUserId());
		}
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("powerpro2022.rakuspertners@gmail.com");
		msg.setTo(user.getEmail());
		msg.setSubject("【パワフルプロエンジニア】スペックシートについて");
		
		StringBuilder content = new StringBuilder();
		content.append(user.getName()+"さんのスペックシートが管理者によって変更されました。\n"
				+ "処理内容：" + process + "\n");
		if (StringUtils.hasText(adminComment)) {
			content.append("管理者コメント：" + adminComment + "\n");
		}
		content.append("詳細は以下のURLよりご確認ください。\n" + url);
		
		msg.setText(content.toString());
		sender.send(msg);
		
	}

}
