package com.example.service.technique;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
public class GetTechnicalSkillServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;

	@InjectMocks
	private GetTechnicalSkillService service;

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
	public void 主キーからテクニカルスキルを取得する() {
		Integer technicalSkillId = 1;
		String name="testItem1";
		TechnicalSkill technicalSkill = new TechnicalSkill();
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		technicalSkill.setName(name);
		when(mapper.selectByPrimaryKey(any(Integer.class))).thenReturn(technicalSkill);

		TechnicalSkill actual = service.getTechnicalSkill(technicalSkillId);
		assertEquals(technicalSkill, actual);

	}

}
