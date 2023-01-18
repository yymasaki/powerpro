package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.example.common.Stage;
import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageServiceTest {
	
	@Mock
	TechnicalSkillMapper mapper;
	@InjectMocks
	GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageService service;

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
	public void スペックシートIDと保有テクニカルスキルとステージからテクニカルスキルを取得() {
		List<Integer> specStageList = new ArrayList<>();
		specStageList.add(Stage.REQUESTING.getKey());
		
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(new TechnicalSkill());
		when(mapper.selectBySpecsheetIdAndUserIdAndStage(any(Integer.class), any(Integer.class), any())).thenReturn(technicalSkillList);
		
		List<TechnicalSkill> result = service.getBySpecsheetIdAndUserIdAndStage(1, 1, specStageList);
		assertEquals(1, result.size());
		verify(mapper, times(1)).selectBySpecsheetIdAndUserIdAndStage(any(Integer.class), any(Integer.class), any());
	}

}
