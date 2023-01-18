package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.domain.HadPersonality;
import com.example.mapper.HadPersonalityMapper;

@Service
@Transactional
public class GetHadPersonaliyListService {
	
	@Autowired
	private HadPersonalityMapper mapper;
	
	/**
	 * 所有性格リストを取得する.
	 * 
	 * @param userId ユーザーID
	 * @param stage 状況
	 * @return ステータス情報
	 */
	public List<HadPersonality> getHadPersonalityList(Integer statusId) {
		List<HadPersonality> hadPersonalityList = mapper.selectHadPersonalityAndPersonality(statusId);
		if(CollectionUtils.isEmpty(hadPersonalityList)) {
			return null;
		}
		return hadPersonalityList;
	}
}
