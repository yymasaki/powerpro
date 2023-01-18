package com.example.service.technique;

import static org.junit.Assert.assertEquals;
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

import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetTechnicalSkillListServiceTest {
	
	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private GetTechnicalSkillListService service;

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
	public void テクニカルスキルをリストで取得() {
		TechnicalSkillExample example = new TechnicalSkillExample();
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(1);
		technicalSkillList.add(technicalSkill);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<TechnicalSkill> resultList = service.getTechnicalSkillList(example);
		assertEquals(1, resultList.size());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

}
