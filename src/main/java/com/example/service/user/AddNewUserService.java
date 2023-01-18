package com.example.service.user;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.form.AddUserForm;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class AddNewUserService {

	@Autowired
	private UserMapper mapper;

	/**
	 * 新規ユーザー登録する.
	 * 
	 * @param form    ユーザー情報
	 * @param creater ユーザー情報作成者
	 */
	public void add(AddUserForm form, String creater) {
		User user = new User();
		String name = form.getLastName() + "　" + form.getFirstName();
		user.setName(name);
		String[] hiredYearAndMonth = form.getHiredOn().split("-");
		Integer hiredYear = Integer.parseInt(hiredYearAndMonth[0]);
		Integer hiredMonth = Integer.parseInt(hiredYearAndMonth[1]);
		user.setHiredOn(LocalDate.of(hiredYear, hiredMonth, 1));
		user.setDepartmentId(Integer.parseInt(form.getDepartmentId()));
		user.setEmail(form.getEmail()+form.getDomain());
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setStage(0);
		user.setCreator(creater);
		user.setUpdater(creater);
		user.setVersion(1);
		mapper.insertSelective(user);
	}
}
