package com.example.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class GetUserByUpdaterService {

	@Autowired
	private UserMapper mapper;

	/**
	 * データ更新者とデータ状況でユーザー情報を取得する(最終更新日降順).
	 * 
	 * @param updater 更新者
	 * @return ユーザー情報一覧
	 */
	public List<User> getUserByUpdaterAndStage(String updater, String stage) {
		UserExample example = new UserExample();
		example.createCriteria().andUpdaterEqualTo(updater).andStageEqualTo(stage);
		example.setOrderByClause("updated_at DESC");
		return  mapper.selectByExample(example);
	}
}
