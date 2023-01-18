package com.example.service.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SendFormUrlByEmailService {

	@Autowired
	private MailSender sender;

	@Autowired
	private HttpServletRequest request;

	/**
	 * メールでURLを送信する .
	 * 
	 * @param context フォーム画面URLのコンテキスト
	 * @param path    フォーム画面のPATH
	 * @param param   PATHに仕込むリクエストパラメータ
	 * @param email   宛先メールアドレス
	 */
	public void sendUrl(String path, String param, String email) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("powerpro2022.rakuspertners@gmail.com");
		msg.setTo(email);
		if ("/register".equals(path)) {
			msg.setSubject("【パワフルプロエンジニア】会員登録について");
		}
		
		if ("/pass/reset".equals(path)) {
			msg.setSubject("【パワフルプロエンジニア】パスワード再設定について");
		}

		// 処理時のURLを取得
		String urlArray[] = request.getRequestURL().toString().split("/");
		StringBuilder text = new StringBuilder("以下URLから手続きしてください\nhttp://").append(urlArray[2]).append(path).append("?id=").append(param);
		msg.setText(text.toString());
		sender.send(msg);
	}
}
