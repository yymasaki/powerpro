package com.example.service.common;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class DefinitiveUserResistrationService {

	@Autowired
	private UserMapper mapper;

	/**
	 * ユーザー本登録をする.
	 * 
	 * @param form ユーザー入力情報
	 */
	public void definitiveResistration(RegisterUserForm form) {
		User user = new User();
		String name = form.getLastName() + "　" + form.getFirstName();
		user.setName(name);
		String[] hiredYearAndMonth = form.getHiredOn().split("-");
		Integer hiredYear = Integer.parseInt(hiredYearAndMonth[0]);
		Integer hiredMonth = Integer.parseInt(hiredYearAndMonth[1]);
		user.setHiredOn(LocalDate.of(hiredYear, hiredMonth, 1));
		user.setDepartmentId(Integer.parseInt(form.getDepartmentId()));
		user.setEmail(form.getEmail());
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setStage(0);
		user.setCreator(name);
		user.setUpdater(name);

		mapper.updateNewestTemporaryUser(user);
	}

}
