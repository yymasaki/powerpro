package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTechnicalSkillByNameAndCategoryServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;

	@InjectMocks
	private GetTechnicalSkillByNameAndCategoryService service;

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
	public void 名前とカテゴリからテクニカルスキルを取得する() {
		String name = "mac";
		Integer category = 1;

		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setName(name);
		technicalSkill.setCategory(category);
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(technicalSkill);

		// mapperのモック作成,
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);

		TechnicalSkill actualTechnicalSkill = service.getTechinicalSkillByNameAndCategory(name, category);
		System.out.println(actualTechnicalSkill);
		assertEquals(name, actualTechnicalSkill.getName());
		assertEquals(category, actualTechnicalSkill.getCategory());

	}

	@Test
	public void 名前とカテゴリから取得できるテクニカルスキルがない() {
		String name = "mac";
		Integer category = 1;

		List<TechnicalSkill> technicalSkillList = new ArrayList<>();

		// mapperのモック作成,
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);

		TechnicalSkill actualTechnicalSkill = service.getTechinicalSkillByNameAndCategory(name, category);
		assertNull(actualTechnicalSkill);

	}

}
