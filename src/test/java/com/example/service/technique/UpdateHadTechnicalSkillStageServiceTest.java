package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.common.Stage;
import com.example.domain.HadTechnicalSkill;
import com.example.example.HadTechnicalSkillExample;
import com.example.mapper.HadTechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateHadTechnicalSkillStageServiceTest {
	
	@Mock
	private HadTechnicalSkillMapper mapper;
	
	@InjectMocks
	private UpdateHadTechnicalSkillStageService service;

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
	public void 保有テクニカルスキルを1件更新() {
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		HadTechnicalSkillExample example = new HadTechnicalSkillExample();
		when(mapper.updateStageByExample(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class)))
		.thenReturn(2);
		int result = service.updateStage(hadTechnicalSkill, example);
		assertEquals(2, result);
		verify(mapper, times(1))
		.updateStageByExample(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
	}
	
	@Test
	public void 保有テクニカルスキルを複数件更新_リストサイズ0() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		int result = service.updateStageByList(hadTechnicalSkillList);
		assertEquals(0, result);
		verify(mapper, times(0))
		.updateStageByHadTechnicalSkillList(any(), any(Integer.class), any(String.class), any(LocalDateTime.class));
	}
	
	@Test
	public void 保有テクニカルスキルを複数件更新_リストサイズ1() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkill.setUserId(1);
		hadTechnicalSkill.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkill.setUpdater("tester");
		hadTechnicalSkill.setUpdatedAt(LocalDateTime.now());
		hadTechnicalSkillList.add(hadTechnicalSkill);
		when(mapper.updateStageByHadTechnicalSkillList(any(), any(Integer.class), any(String.class), any(LocalDateTime.class)))
		.thenReturn(1);
		int result = service.updateStageByList(hadTechnicalSkillList);
		assertEquals(1, result);
		verify(mapper, times(1))
		.updateStageByHadTechnicalSkillList(any(), any(Integer.class), any(String.class), any(LocalDateTime.class));
	}

}
