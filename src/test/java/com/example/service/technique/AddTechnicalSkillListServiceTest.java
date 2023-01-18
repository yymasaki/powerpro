package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AddTechnicalSkillListServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;

	@InjectMocks
	private AddTechnicalSkillListService service;

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
	public void テクニカルスキルリストを挿入する() {
		LocalDateTime current = LocalDateTime.now();
		TechnicalSkill technicalSkill = new TechnicalSkill(null, "hoge", 1, 7, 0, "ほげ子", current, "ほげ子", current, 1);
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(technicalSkill);

		List<TechnicalSkill> actualTechnicalSkillList = service.addTechnicalSkillList(technicalSkillList);

		assertEquals(technicalSkill, actualTechnicalSkillList.get(0));
		verify(mapper, times(1)).insertTechnicalSkillList(technicalSkillList);

	}

	@Test
	public void テクニカルスキルリストを挿入する際引数の中身がない場合() {
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();

		List<TechnicalSkill> actualTechnicalSkillList = service.addTechnicalSkillList(technicalSkillList);

		assertEquals(technicalSkillList, actualTechnicalSkillList);
		verify(mapper, times(0)).insertTechnicalSkillList(technicalSkillList);

	}

}
