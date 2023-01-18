package com.example.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class UpdatePasswordService {

	@Autowired
	private UserMapper mapper;

	/**
	 * パスワードを更新する.
	 * 
	 * @param userId   ユーザーID
	 * @param password パスワード
	 */
	public void update(Integer userId, String password, String updater) {
		User user = new User();
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(password));
		user.setUpdater(updater);
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(userId);
		mapper.updateByExampleSelective(user, example);
	}
}
