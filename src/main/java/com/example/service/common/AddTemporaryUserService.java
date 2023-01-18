package com.example.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class AddTemporaryUserService {

	@Autowired
	private UserMapper mapper;

	/**
	 * 仮ユーザー登録.
	 * 
	 * @param email メールアドレス
	 * @param uuid  仮登録用ID
	 */
	public void insert(String email, String uuid) {
		// メールアドレスが既に仮ユーザー登録されている場合、古い仮ユーザー情報を削除
		UserExample ex = new UserExample();
		ex.createCriteria().andEmailEqualTo(email).andStageEqualTo("1");
		mapper.deleteByExample(ex);
		// 今回の仮ユーザー登録
		User user = new User();
		user.setDepartmentId(9);
		user.setEmail(email);
		user.setStage(1);
		user.setCreator(uuid);
		user.setUpdater(uuid);
		mapper.insertSelective(user);
	}

}
