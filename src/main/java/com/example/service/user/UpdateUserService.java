package com.example.service.user;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class UpdateUserService {
	
	@Autowired
	private UserMapper mapper;
	
	/**
	 * ユーザー情報を更新する.
	 */
	public boolean editUser(User user,UserExample example) {
		user.setUpdater(user.getName());
		user.setUpdatedAt(LocalDateTime.now());
		return mapper.updateByExampleSelective(user, example);
	}
}
