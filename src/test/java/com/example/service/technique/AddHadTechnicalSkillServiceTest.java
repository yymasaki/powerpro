package com.example.service.technique;

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

import com.example.domain.HadTechnicalSkill;
import com.example.mapper.HadTechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddHadTechnicalSkillServiceTest {

	@Mock
	private HadTechnicalSkillMapper mapper;
	
	@InjectMocks
	private AddHadTechnicalSkillService service;
	
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
	public void 保有テクニカルスキルリストを登録() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		service.insertList(hadTechnicalSkillList);
		verify(mapper, times(1)).insertList(any());
	}

}
