package com.example.service.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import com.example.domain.HadBasicSkill;
import com.example.mapper.HadBasicSkillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetHadBasicSkillListServiceTest {

	@Mock
	private HadBasicSkillMapper mapper;
	
	@InjectMocks
	private GetHadBasicSkillListService target;
	
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
	public void 所有基本スキルリストの取得() {
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
		
		when(mapper.selectHadBasicSkillAndBasicSkillByStatusId(statusId)).thenReturn(hadBasicSkillList);
		
		List<HadBasicSkill> actual = target.getHadBasicSkillList(statusId);
		assertEquals(hadBasicSkillList, actual);
		assertEquals(3, actual.size());
	}
	
	@Test
	public void 所有基本リストのnull取得() {
		Integer statusId = 1;
		List<HadBasicSkill> hadBasicSkillList = new ArrayList<>();
		when(mapper.selectHadBasicSkillAndBasicSkillByStatusId(statusId)).thenReturn(hadBasicSkillList);
		
		List<HadBasicSkill> actual = target.getHadBasicSkillList(statusId);
		assertNull(actual);
	}

}
