package com.example.service.common;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class SetUuidOnUpdaterInUsersInfoService {

	@Autowired
	private UserMapper mapper;

	/**
	 * emailに該当するユーザー情報の更新者をUUIDにする.
	 * 
	 * @param uuid  UUID
	 * @param email メールアドレス
	 * @return 更新成功：true, 更新失敗：false
	 */
	public boolean setUuidAsUpdater(String uuid, String email) {
		User user = new User();
		user.setUpdater(uuid);
		user.setUpdatedAt(LocalDateTime.now());
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo(email);
		return mapper.updateByExampleSelective(user, example);
	}
}
