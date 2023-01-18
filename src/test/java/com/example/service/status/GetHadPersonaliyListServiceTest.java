package com.example.service.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.example.domain.HadPersonality;
import com.example.mapper.HadPersonalityMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetHadPersonaliyListServiceTest {
	
	@Mock
	private HadPersonalityMapper mapper;
	
	@InjectMocks
	private GetHadPersonaliyListService target;

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
	public void 所有性格リストの取得() {
		List<HadPersonality> hadPersonalityList = new ArrayList<>();
		Integer statusId = 1;
		
		HadPersonality hadPersonality1 = new HadPersonality();
		hadPersonality1.setHadPersonalityId(1);
		hadPersonality1.setStage(0);
		hadPersonality1.setStatusId(statusId);
		hadPersonalityList.add(hadPersonality1);
		
		HadPersonality hadPersonality2 = new HadPersonality();
		hadPersonality2.setHadPersonalityId(2);
		hadPersonality2.setStage(0);
		hadPersonality2.setStatusId(statusId);
		hadPersonalityList.add(hadPersonality2);
		
		HadPersonality hadPersonality3 = new HadPersonality();
		hadPersonality3.setHadPersonalityId(3);
		hadPersonality3.setStage(0);
		hadPersonality3.setStatusId(statusId);
		hadPersonalityList.add(hadPersonality3);
		
		when(mapper.selectHadPersonalityAndPersonality(statusId)).thenReturn(hadPersonalityList);
		
		List<HadPersonality> actual = target.getHadPersonalityList(statusId);
		assertEquals(hadPersonalityList, actual);
		assertEquals(3, actual.size());
	}
	
	@Test
	public void 所有性格リストのnull取得() {
		Integer statusId = 1;
		List<HadPersonality> hadPersonalityList = new ArrayList<>();
		when(mapper.selectHadPersonalityAndPersonality(statusId)).thenReturn(hadPersonalityList);
		
		List<HadPersonality> actual = target.getHadPersonalityList(statusId);
		assertNull(actual);
	}
}
