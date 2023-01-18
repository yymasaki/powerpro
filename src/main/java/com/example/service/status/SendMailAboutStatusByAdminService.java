package com.example.service.status;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Stage;
import com.example.domain.User;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class SendMailAboutStatusByAdminService {
	
	@Autowired
	private MailSender sender;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	public UserMapper mapper;

	/**
	 * 	ユーザにメールを送信する.
	 * 
	 * @param userId 　ユーザID
	 * @param process　申請の処理
	 * @param adminComment　管理者コメント
	 * @param technicalSkillId　テクニカルスキルID
	 */
	public void sendMailAboutStatusByAdmin(Integer userId, Integer stage,String adminComment,Integer statusId, String process) {
		User user=mapper.selectByPrimaryKey(userId);
		String email=user.getEmail();
		//処理時のURLを取得し、メールで張り付けたいリンクに置き変える
		String url = request.getRequestURL().toString();
		url=url.replace("/process","");
		url=url.replace("/edit/do","");
		url=url.replace("/delete","");
		
		if(stage == Stage.ACTIVE.getKey() || stage == Stage.DELETED.getKey()) {
	    	url = url.replace("request", "skill");	
	    }
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("powerpro2022.rakuspertners@gmail.com");
		msg.setTo(email);
	    msg.setSubject("【パワフルプロエンジニア】ステータスについて");
	    
	    StringBuilder text = new StringBuilder();
	    
    	text.append(user.getName()+"さんのステータスが管理者によって変更されました。\n"
    			+"処理内容：" + process +"\n"
    			+"管理者コメント："+adminComment+"\n"
    			+"以下urlより詳細をご確認ください。\n"
    			+url);	    	
	    
	    if(stage == Stage.ACTIVE.getKey() || stage == Stage.DELETED.getKey()) {
	    	text.append("?userId="+userId);  	
	    }else {
	    	text.append("?statusId="+statusId);
	    }
	    msg.setText(text.toString());
		this.sender.send(msg);
	}
}
