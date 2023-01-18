package com.example.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class GetSpecificUserService { 

	@Autowired
	private UserMapper mapper;

	/**
	 * ユーザーIDに合致するユーザー情報を取得.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public User get(Integer userId) {
		return mapper.selectByPrimaryKey(userId);
	}

}
