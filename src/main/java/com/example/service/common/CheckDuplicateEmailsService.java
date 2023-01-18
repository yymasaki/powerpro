package com.example.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class CheckDuplicateEmailsService {

	@Autowired
	private UserMapper mapper;

	/**
	 * 重複確認する.
	 * 
	 * @param email 新規メールアドレス
	 * @return 重複あり：true, なし：false
	 */
	public boolean checkDuplication(String email) {
		List<User> users = mapper.selectByEmailAndStage(email, "0");
		if (users.isEmpty()) return false;
		return true;
	}

}
