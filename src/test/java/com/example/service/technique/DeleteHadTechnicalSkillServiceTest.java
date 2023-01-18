package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.example.domain.HadTechnicalSkill;
import com.example.example.HadTechnicalSkillExample;
import com.example.mapper.HadTechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteHadTechnicalSkillServiceTest {

	@Mock
	private HadTechnicalSkillMapper mapper;
	
	@InjectMocks
	private DeleteHadTechnicalSkillService service;
	
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
	public void 保有テクニカルスキルリストを複数削除_リストサイズ0() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		int result = service.deleteHadTechnicalSkill(hadTechnicalSkillList);
		assertEquals(0, result);
		verify(mapper, times(0)).deleteListByPrimaryKey(any());
	}
	
	@Test
	public void 保有テクニカルスキルリストを複数削除_リストサイズ1() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkillList.add(hadTechnicalSkill);
		when(mapper.deleteListByPrimaryKey(any())).thenReturn(1);
		int result = service.deleteHadTechnicalSkill(hadTechnicalSkillList);
		assertEquals(1, result);
		verify(mapper, times(1)).deleteListByPrimaryKey(any());
	}
	
	@Test
	public void 保有テクニカルスキルリストを条件から削除() {
		HadTechnicalSkillExample example = new HadTechnicalSkillExample();
		when(mapper.deleteByExample(any(HadTechnicalSkillExample.class))).thenReturn(2);
		int result = service.deleteByExample(example);
		assertEquals(2, result);
		verify(mapper, times(1)).deleteByExample(any(HadTechnicalSkillExample.class));
	}

}
