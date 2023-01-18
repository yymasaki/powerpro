package com.example.service.status;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Status;
import com.example.mapper.StatusMapper;

@Service
@Transactional
public class UpdateStatusService {
	
	@Autowired
	private StatusMapper mapper;
	
	/**
	 * ステータス情報の更新を行う.
	 * 
	 * @param status ステータス
	 */
	public void updateStatus(Status status) {
		status.setVersion(status.getVersion() + 1);
		status.setUpdatedAt(LocalDateTime.now());
		mapper.updateByPrimaryKeySelective(status);
	}
}
