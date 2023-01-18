package com.example.controller.skill;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.HadBasicSkill;
import com.example.domain.HadPersonality;
import com.example.service.status.GetHadBasicSkillListService;
import com.example.service.status.GetHadPersonaliyListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HadBasicAndPersonalityRestControllerTest {
	
	@Mock
	private GetHadPersonaliyListService getHadPersonaliyListService;
	
	@Mock
	private GetHadBasicSkillListService getHadBasicSkillListService;
	
	@InjectMocks
	private HadBasicAndPersonalityRestController target;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 所有基本スキルリストの入ったmapの取得を行う() throws Exception {
		//paramに必要
		Integer statusId = 1;
		Map<String, List<HadBasicSkill>> expectedMap = new HashMap<>();
		expectedMap.put("hadBasicSkillList", hadBasicSkillListTestData());
		//戻り値を設定
		when(getHadBasicSkillListService.getHadBasicSkillList(statusId)).thenReturn(hadBasicSkillListTestData()); 
		Map<String, List<HadBasicSkill>> actualMap = target.getHadBasicSkill(statusId);
		assertEquals(actualMap, expectedMap);
		//サービス確認
		verify(getHadBasicSkillListService, times(1)).getHadBasicSkillList(statusId);
	}
	
	@Test
	public void 所有性格リストの入ったmap取得を行う() throws Exception {
		//paramに必要
		Integer statusId = 1;
		Map<String, List<HadPersonality>> expectedMap = new HashMap<>();
		expectedMap.put("hadPersonalityList", hadPersonalityListTestData());
		//戻り値を設定
		when(getHadPersonaliyListService.getHadPersonalityList(statusId)).thenReturn(hadPersonalityListTestData()); 
		Map<String, List<HadPersonality>> actualMap = target.getHadPersonality(statusId);
		assertEquals(actualMap, expectedMap);
		//サービス確認
		verify(getHadPersonaliyListService, times(1)).getHadPersonalityList(statusId);
	}
	
	public List<HadBasicSkill> hadBasicSkillListTestData(){
		List<HadBasicSkill> hadBasicSkillList = new ArrayList<>();
		HadBasicSkill hadBasicSkill = new HadBasicSkill();
		hadBasicSkill.setHadBasicSkillId(1);
		hadBasicSkillList.add(hadBasicSkill);
		return hadBasicSkillList;
	}
	
	public List<HadPersonality> hadPersonalityListTestData(){
		List<HadPersonality> hadPersonalityList = new ArrayList<>();
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(1);
		hadPersonalityList.add(hadPersonality);
		return hadPersonalityList;
	}

}
