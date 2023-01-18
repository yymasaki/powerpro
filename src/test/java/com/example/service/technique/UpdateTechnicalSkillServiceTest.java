package com.example.service.technique;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
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

import com.example.common.Stage;
import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTechnicalSkillServiceTest {
	
	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private UpdateTechnicalSkillService service;

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
	public void テクニカルスキルのリクエストユーザーとステージをリストで更新() {
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setRequestUserId(1);
		technicalSkill.setStage(Stage.ACTIVE.getKey());
		technicalSkill.setUpdater("tester");
		technicalSkill.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		technicalSkillList.add(technicalSkill);
		service.updateRequestUserIdAndStageByPrimaryKeyAsList(technicalSkillList);
		verify(mapper, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(
				any(), any(Integer.class), any(String.class), any(String.class), any(LocalDateTime.class));
	}

}
