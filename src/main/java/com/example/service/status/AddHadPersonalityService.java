package com.example.service.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.common.Stage;
import com.example.domain.HadPersonality;
import com.example.mapper.HadPersonalityMapper;

@Service
@Transactional
public class AddHadPersonalityService {
	
	@Autowired
	private HadPersonalityMapper hadPersonalityMapper;
	
	/**
	 * 選択された性格リストを登録する.
	 * 
	 * @param hadPersonalityIdList 所有性格IDリスト
	 * @param statusId ステータスID
	 */
	public void addHadPersonality(List<Integer> personalityIdList, Integer statusId) {
		List<HadPersonality> hadPersonalityList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(personalityIdList)) {
			personalityIdList.stream().forEach((personalityId) ->{
				HadPersonality hadPersonality = new HadPersonality();
				hadPersonality.setPersonalityId(personalityId);
				hadPersonality.setStatusId(statusId);
				hadPersonality.setStage(Stage.ACTIVE.getKey());
				hadPersonalityList.add(hadPersonality);
			});
			hadPersonalityMapper.insertSelectiveList(hadPersonalityList);
		}
	}
}
