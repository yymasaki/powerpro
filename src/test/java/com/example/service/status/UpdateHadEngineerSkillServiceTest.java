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
public class UpdateHadEngineerSkillServiceTest {
	
	@Mock
	private HadEngineerSkillMapper mapper;
	
	@InjectMocks
	private UpdateHadEngineerSkillService target;

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
	public void 所有エンジニアスキルの更新を行う() {
		Integer statusId = 1;
		List<HadEngineerSkill> hadEngineerSkillList = new ArrayList<>();
		HadEngineerSkill hadEngineerSkill1 = new HadEngineerSkill();
		hadEngineerSkill1.setHadEngineerSkillId(1);
		hadEngineerSkill1.setScore(50);
		hadEngineerSkillList.add(hadEngineerSkill1);
		
		HadEngineerSkill hadEngineerSkill2 = new HadEngineerSkill();
		hadEngineerSkill2.setHadEngineerSkillId(2);
		hadEngineerSkill2.setScore(70);
		hadEngineerSkillList.add(hadEngineerSkill2);
		
		HadEngineerSkill hadEngineerSkill3 = new HadEngineerSkill();
		hadEngineerSkill3.setHadEngineerSkillId(3);
		hadEngineerSkill3.setScore(90);
		hadEngineerSkillList.add(hadEngineerSkill3);
		
		target.updateHadEngineerSkill(hadEngineerSkillList, statusId);
		verify(mapper, times(1)).bulkUpdate(hadEngineerSkillList, statusId);
	}

}
