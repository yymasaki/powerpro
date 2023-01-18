package com.example.service.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.example.mapper.HadPersonalityMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddHadPersonalityServiceTest {
	
	@Mock
	private HadPersonalityMapper mapper;
	
	@InjectMocks
	private AddHadPersonalityService target;

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
	public void 所有性格を全て追加する() {
		List<Integer> personalityIdList = new ArrayList<>();
		Integer statusId = 1;
		for(int i = 1; i <= 8; i++) {
			personalityIdList.add(i);			
		}
	
		target.addHadPersonality(personalityIdList, statusId);
		verify(mapper, times(1)).insertSelectiveList(any());
	}
	
	@Test
	public void 所有性格なしの状態で追加をする() {
		List<Integer> personalityIdList = new ArrayList<>();
		target.addHadPersonality(personalityIdList, 1);
	}

}
