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
public class DeleteUserService {

	@Autowired
	private UserMapper mapper;

	/**
	 * ユーザー情報を削除する.
	 * 
	 * @param userId ユーザーID
	 */
	public void delete(Integer userId, String updater) {
		User user = new User();
		user.setStage(9);
		user.setUpdater(updater);
		user.setUpdatedAt(LocalDateTime.now());
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(userId).andStageEqualTo("0");
		mapper.updateByExampleSelective(user, example);
	}

}
