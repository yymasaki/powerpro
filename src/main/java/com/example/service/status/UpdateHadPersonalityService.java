package com.example.service.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.common.Stage;
import com.example.domain.HadPersonality;
import com.example.example.HadPersonalityExample;
import com.example.mapper.HadPersonalityMapper;

@Service
@Transactional
public class UpdateHadPersonalityService {
	
	@Autowired
	private HadPersonalityMapper hadPersonalityMapper;
	
	/**
	 * 性格情報の登録,更新を行う.
	 * 
	 * @param hadPersonalityList 所有性格リスト
	 */
	public void upsertHadPersonality(List<Integer> personalityIdList, Integer statusId) {
		//現在データベースに登録されているステータスIDが一致する全てのhadPersonalityを取得する.
		List<HadPersonality> hadPersonalityListInDb = hadPersonalityMapper.selectByStatusId(statusId);
		
		//現在データベースに登録されているpersonalityIdを取得
		List<Integer> personalityIdListInDb = new ArrayList<>();
		hadPersonalityListInDb.stream().forEach((hadPersonality) -> {
			personalityIdListInDb.add(hadPersonality.getPersonalityId());
		});
		
		//DB内のstatusIdが一致する全てのhadPersonalitiesを削除
		if(!CollectionUtils.isEmpty(hadPersonalityListInDb)) {
			HadPersonalityExample exampleForDelete = new HadPersonalityExample();
			exampleForDelete.createCriteria()
			.andPersonalityIdIn(personalityIdListInDb)
			.andStatusIdEqualTo(statusId);
			hadPersonalityMapper.deleteByExample(exampleForDelete);								
		}
		
		if(!(CollectionUtils.isEmpty(personalityIdList))) {
			List<HadPersonality> hadPersonalityListForInsert = new ArrayList<>();
			//insertする要素のみをリストに加える.			
			personalityIdList.stream()
								//personalityId = null(ボタンが押されていない)は加えない
								.filter(personalityId -> Objects.nonNull(personalityId))
								.forEach((personalityId)->{
									HadPersonality hadPersonalityForUpdate = new HadPersonality();
									hadPersonalityForUpdate.setPersonalityId(personalityId);
									hadPersonalityForUpdate.setStatusId(statusId);
									hadPersonalityForUpdate.setStage(Stage.ACTIVE.getKey());
									hadPersonalityListForInsert.add(hadPersonalityForUpdate);
								});
			
			//Formの中身をインサートする
			hadPersonalityMapper.insertSelectiveList(hadPersonalityListForInsert);
			
		}
	}
}
