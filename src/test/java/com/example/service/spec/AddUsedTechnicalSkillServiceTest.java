package com.example.service.spec;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.example.domain.UsedTechnicalSkill;
import com.example.mapper.UsedTechnicalSkillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddUsedTechnicalSkillServiceTest {
	
	@Mock
	private UsedTechnicalSkillMapper mapper;
	
	@InjectMocks
	private AddUsedTechnicalSkillService service;

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
	public void 使用テクニカルスキルリストを登録() {
		List<UsedTechnicalSkill> usedTechnicalSkillList = new ArrayList<>();
		UsedTechnicalSkill usedTechnicalSkill = new UsedTechnicalSkill();
		usedTechnicalSkill.setUsedTechnicalSkillId(1);
		usedTechnicalSkillList.add(usedTechnicalSkill);
		service.addUsedTechnicalSkillList(usedTechnicalSkillList);
		verify(mapper, times(1)).insertList(any());
	}

}
