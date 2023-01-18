package com.example.service.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Stage;
import com.example.example.StatusExample;
import com.example.mapper.StatusMapper;

@Transactional
@Service
public class DeleteStatusService {
	
	@Autowired
	private StatusMapper mapper;
	
	/**
	 * ステータスを削除する.
	 * 
	 * @param statusId ステータスID
	 */
	public void deleteStatusByPrimaryKey(Integer statusId) {
		mapper.deleteByPrimaryKey(statusId);
	}
	
	/**
	 * ユーザーIDからステータスの削除を行う.
	 * 
	 * @param userId ユーザーID
	 */
	public void deleteStatusByUserId(Integer userId) {
		StatusExample example = new StatusExample();
		example.createCriteria()
					.andUserIdEqualTo(userId)
					.andStageEqualTo(String.valueOf(Stage.ACTIVE.getKey()));
		mapper.deleteByExample(example);
	}
}
