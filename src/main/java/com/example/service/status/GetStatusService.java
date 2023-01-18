package com.example.service.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.domain.Status;
import com.example.example.StatusExample;
import com.example.mapper.StatusMapper;

@Transactional
@Service
public class GetStatusService {
	
	@Autowired
	private StatusMapper mapper; 
	
	/**
	 * ステージかつユーザーIDが一致するステータスを取得する.
	 * 
	 * @param userId ユーザーID
	 * @param stage1 ステージ
	 * @param stage2 ステージ
	 * @return ステータス
	 */
	public Status getStatusByStages(Integer userId, Integer stage1, Integer stage2) {
		StatusExample statusExample = new StatusExample();
		List<String>stageList = new ArrayList<>();
		stageList.add(stage1.toString());
		if(Objects.nonNull(stage2)) {
			stageList.add(stage2.toString());			
		}
		statusExample.createCriteria().andStageIn(stageList).andUserIdEqualTo(userId);
		statusExample.setOrderByClause("version desc");
		List<Status> statusList = mapper.selectByExample(statusExample);
		if(CollectionUtils.isEmpty(statusList)) {
			return null;
		}
		return statusList.get(0);
	}
	
	public Status getStatusByPrimaryKey(Integer statusId) {
		return mapper.selectByPrimaryKey(statusId);
	}
	
}
