package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.domain.Status;
import com.example.mapper.StatusMapper;

@Service
@Transactional
public class GetStatusAndSkillsService {
	
	@Autowired
	private StatusMapper mapper;
	
	/**
	 * ユーザーIDとstageからステータス情報を受け取る.
	 * 
	 * @param userId ユーザーID
	 * @param stage1 データ情報
	 * @param stage2 データ情報
	 * @return ステータス情報
	 */
	public Status getStatusAndSkillsWithoutPersonalitiesByStages(Integer userId, Integer stage1, Integer stage2) {
		List<Status> statusList = mapper.selectStatusAndSkillsWithoutPersonalitiesByUserIdAndStages(userId, stage1, stage2);
		if(CollectionUtils.isEmpty(statusList)) {
			return null;
		}
		return statusList.get(0);			
	}
	
	/**
	 * ユーザーIDとstageからステータス情報を受け取る.
	 * 
	 * @param userId ユーザーID
	 * @param stage1 データ情報
	 * @param stage2 データ情報
	 * @return ステータス情報
	 */
	public Status getStatusAndSkillsByStages(Integer userId, Integer stage1, Integer stage2) {
		List<Status> statusList = mapper.selectStatusAndSkillsByUserIdAndStages(userId, stage1, stage2);
		if(CollectionUtils.isEmpty(statusList)) {
			return null;
		}
		return statusList.get(0);			
	}
	
	/**
	 * ステータスIDとstageからステータス情報を受け取る.
	 * 
	 * @param statusId ステータスID
	 * @param stage データ情報
	 * @return ステータス情報
	 */
	public Status getStatusAndSkillsByPrimaryKey(Integer statusId) {
		return mapper.selectStatusAndSkillsByPrimaryKey(statusId);
	}
	
}
