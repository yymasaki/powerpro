package com.example.service.technique;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.common.Stage;
import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UpdateTechnicalSkillListStageServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private UpdateTechnicalSkillListStageService service;
	
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
	public void テクニカルスキルのステージをリストで更新する() {
		LocalDateTime current=LocalDateTime.now();
		LocalDateTime createdAt=LocalDateTime.of(2020,5,13,12,00,00);

				
		TechnicalSkill ts=new TechnicalSkill(3,"Spring boot",3,null,0, "テスト太郎",createdAt, "テスト太郎", createdAt, 3);
		TechnicalSkill ts2=new TechnicalSkill(4,"JQuery",4,null,0, "テスト太郎",createdAt, "テスト太郎", createdAt, 3);
		
		List <TechnicalSkill> technicalSkillList=new ArrayList<>();
		technicalSkillList.add(ts);
		technicalSkillList.add(ts2);
		
		when(mapper.updateStageByPrimaryKeyAsList(technicalSkillList, Stage.REQUESTING.getKey().toString(), "管理者次郎", current))
		.thenReturn(2);

		int actual=service.updateTechnicalSkillListStage(technicalSkillList, Stage.REQUESTING.getKey().toString(), "管理者次郎", current);
		
		assertEquals(2, actual);
		verify(mapper, times(1)).updateStageByPrimaryKeyAsList(technicalSkillList, Stage.REQUESTING.getKey().toString(), "管理者次郎", current);
		
	}

}
