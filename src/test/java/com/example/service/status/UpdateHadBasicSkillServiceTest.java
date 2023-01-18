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
public class UpdateHadBasicSkillServiceTest {
	
	@Mock
	private HadBasicSkillMapper mapper;
	
	@InjectMocks
	private UpdateHadBasicSkillService target;

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
	public void バルクアップデートを行う() {
		List<HadBasicSkill> hadBasicSkillList = new ArrayList<>();
		Integer statusId = 1;
		
		HadBasicSkill hadBasicSkill1 = new HadBasicSkill();
		hadBasicSkill1.setHadBasicSkillId(1);
		hadBasicSkill1.setScore("1");
		hadBasicSkill1.setStatusId(statusId);
		hadBasicSkillList.add(hadBasicSkill1);
		
		HadBasicSkill hadBasicSkill2 = new HadBasicSkill();
		hadBasicSkill2.setHadBasicSkillId(2);
		hadBasicSkill2.setScore("2");
		hadBasicSkill2.setStatusId(statusId);
		hadBasicSkillList.add(hadBasicSkill2);
		
		HadBasicSkill hadBasicSkill3 = new HadBasicSkill();
		hadBasicSkill3.setHadBasicSkillId(3);
		hadBasicSkill3.setScore("3");
		hadBasicSkill3.setStatusId(statusId);
		hadBasicSkillList.add(hadBasicSkill3);
		
		target.updateHadBasicSkill(hadBasicSkillList, statusId);
		verify(mapper, times(1)).bulkUpdate(hadBasicSkillList, statusId);
	}

}
