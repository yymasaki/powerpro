package com.example.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class GetAllUsersService {
	
	@Autowired
	private UserMapper mapper;
	
	/**
	 * 全ユーザー情報を取得する.
	 * 
	 * @return ユーザー情報一覧
	 */
	public List<User> getAllUsers() {
		return mapper.selectAllValidUsers();
	}
}
