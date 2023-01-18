package com.example.service.status;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Status;
import com.example.mapper.StatusMapper;

@Service
@Transactional
public class AddStatusService {
	
	@Autowired
	private StatusMapper mapper;
	
	/**
	 * ステータスの登録を行う.
	 * 
	 * @param status ステータス情報
	 * @param stage 状態
	 */
	public void addStatus(Status status, Integer version) {
		status.setStatusId(null);
		LocalDateTime currentDateTime = LocalDateTime.now();
		status.setCreatedAt(currentDateTime);
		status.setUpdatedAt(currentDateTime);
		if(Objects.isNull(version)) {
			status.setVersion(1);
		}else {
			status.setVersion(version + 1);			
		}
		mapper.insertSelective(status);
	}
}
