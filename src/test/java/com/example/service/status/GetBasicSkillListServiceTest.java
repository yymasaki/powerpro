package com.example.service.status;

import static org.junit.Assert.assertEquals;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.BasicSkill;
import com.example.example.BasicSkillExample;
import com.example.mapper.BasicSkillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetBasicSkillListServiceTest {

	@Mock
	private BasicSkillMapper mapper;
	
	@InjectMocks
	private GetBasicSkillListService target;
	
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
	public void 基本スキルの取得() {
		List<BasicSkill> basicSkillList = new ArrayList<>();
		BasicSkill basicSkill1 = new BasicSkill();
		basicSkill1.setBasicSkillId(1);
		basicSkill1.setName("テスト基本スキル1");
		basicSkillList.add(basicSkill1);
		
		BasicSkill basicSkill2 = new BasicSkill();
		basicSkill2.setBasicSkillId(2);
		basicSkill2.setName("テスト基本スキル2");
		basicSkillList.add(basicSkill2);
		
		BasicSkill basicSkill3 = new BasicSkill();
		basicSkill3.setBasicSkillId(3);
		basicSkill3.setName("テスト基本スキル3");
		basicSkillList.add(basicSkill3);
		
		Integer departmentId = 1;
		when(mapper.selectByExample(any(BasicSkillExample.class))).thenReturn(basicSkillList);
		
		List<BasicSkill> actual = target.getBasicSkillList(departmentId);
		assertEquals(basicSkillList, actual);
		assertEquals(3, actual.size());
	}

}
