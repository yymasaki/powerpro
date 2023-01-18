package com.example.service.status;

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

import com.example.domain.HadEngineerSkill;
import com.example.mapper.HadEngineerSkillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddHadEngineerSkillServiceTest {
	
	@Mock
	private HadEngineerSkillMapper mapper;
	
	@InjectMocks
	private AddHadEngineerSkillService target;

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
	public void 所有エンジニアスキルを追加する() {
		List<HadEngineerSkill> hadEngineerSkillList = new ArrayList<>();
		Integer statusId = 1;
		for(int i = 1; i <= 7; i++) {
			HadEngineerSkill hadEngineerSkill = new HadEngineerSkill();
			hadEngineerSkill.setEngineerSkillId(i);
			hadEngineerSkill.setScore(80);
			hadEngineerSkill.setStatusId(statusId);
			hadEngineerSkillList.add(hadEngineerSkill);			
		}
		
		target.addHadEngineerSkill(hadEngineerSkillList, statusId);
		verify(mapper, times(1)).insertSelectiveList(hadEngineerSkillList);
	}

}
