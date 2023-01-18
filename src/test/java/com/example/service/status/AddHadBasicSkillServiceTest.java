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

import com.example.domain.HadBasicSkill;
import com.example.mapper.HadBasicSkillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddHadBasicSkillServiceTest {
	
	@Mock
	private HadBasicSkillMapper mapper;
	
	@InjectMocks
	private AddHadBasicSkillService target;

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
	public void 所有基本スキルを追加する() {
		List<HadBasicSkill> hadBasicSkillList = new ArrayList<>();
		Integer statusId = 1;
		for(int i = 1; i <= 6; i++) {
			HadBasicSkill hadBasicSkill = new HadBasicSkill();
			hadBasicSkill.setBasicSkillId(i);
			hadBasicSkill.setScore("5");
			hadBasicSkill.setStatusId(statusId);
			hadBasicSkillList.add(hadBasicSkill);			
		}
		
		target.addHadBasicSkill(hadBasicSkillList, statusId);
		verify(mapper, times(1)).insertSelectiveList(hadBasicSkillList);
	}
}
