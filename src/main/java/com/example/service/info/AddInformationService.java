package com.example.service.info;

import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Information;
import com.example.domain.LoginUser;
import com.example.form.CreateInformationForm;
import com.example.mapper.InformationMapper;

@Service
@Transactional
public class AddInformationService {

	@Autowired
	private InformationMapper informationsMapper;

	/**
	 * 
	 * お知らせを作成する
	 * 
	 * @param form      お知らせの入力情報
	 * @param loginUser ログインユーザー
	 * @return お知らせId
	 * @throws ParseException
	 */
	public int addInformation(CreateInformationForm form, LoginUser loginUser) {
		System.out.println(form);
		Information information = new Information();
		BeanUtils.copyProperties(form, information);
		LocalDate today = LocalDate.now();
		String startPosted = String.valueOf(today).replace("/", "-");
		String endPosted = form.getEndPostedOn().replace("/", "-");
		java.sql.Date startPostedOn = java.sql.Date.valueOf(startPosted);
		java.sql.Date endPostedOn = java.sql.Date.valueOf(endPosted);
		information.setStartPostedOn(startPostedOn);
		information.setEndPostedOn(endPostedOn);
		information.setStage(0);
		information.setCreateUserId(loginUser.getUser().getUserId());
		informationsMapper.insert(information);
		System.out.println(information);
		return information.getInformationId();
	}

}
