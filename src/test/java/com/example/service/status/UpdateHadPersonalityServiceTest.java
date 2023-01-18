package com.example.service.status;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.HadPersonality;
import com.example.mapper.HadPersonalityMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateHadPersonalityServiceTest {
	
	@Mock
	private HadPersonalityMapper mapper;
	
	@InjectMocks
	private UpdateHadPersonalityService target;

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
	public void 所有性格の登録と更新を行う() {
		List<HadPersonality> hadPersonalityList = new ArrayList<>();
		List<HadPersonality> hadPersonalityListInDb = new ArrayList<>();
		Integer statusId = 1;
		
		HadPersonality hadPersonality1 = hadPersonalityTestData1();
		hadPersonalityList.add(hadPersonality1);
		hadPersonalityListInDb.add(hadPersonality1);
		
		HadPersonality hadPersonality2 = hadPersonalityTestData2();
		hadPersonalityList.add(hadPersonality2);
		hadPersonalityListInDb.add(hadPersonality2);
		
		HadPersonality hadPersonality3 = hadPersonalityTestData3();
		hadPersonalityList.add(hadPersonality3);
		
		List<Integer> personalityIdList = new ArrayList<>();
		personalityIdList.add(1);
		personalityIdList.add(2);
		personalityIdList.add(3);
		
		when(mapper.selectByStatusId(statusId)).thenReturn(hadPersonalityListInDb);
		target.upsertHadPersonality(personalityIdList, statusId);
	}
	
	@Test
	public void 所有性格がない状態での更新を行う() {
		List<Integer> personalityIdList = new ArrayList<>();
		target.upsertHadPersonality(personalityIdList, 1);
	}
	
	/**
	 * 所有性格のテストデータ1
	 * 
	 * @return 所有性格
	 */
	public HadPersonality hadPersonalityTestData1() {
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(1);
		hadPersonality.setPersonalityId(1);
		return hadPersonality;
	}
	
	/**
	 * 所有性格のテストデータ2
	 * 
	 * @return 所有性格
	 */
	public HadPersonality hadPersonalityTestData2() {
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(1);
		hadPersonality.setPersonalityId(2);
		return hadPersonality;
	}
	
	/**
	 * 所有性格のテストデータ3
	 * 
	 * @return 所有性格
	 */
	public HadPersonality hadPersonalityTestData3() {
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(1);
		hadPersonality.setPersonalityId(3);
		return hadPersonality;
	}

}
