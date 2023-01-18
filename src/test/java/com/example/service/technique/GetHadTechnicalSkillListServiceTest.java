package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.HadTechnicalSkill;
import com.example.mapper.HadTechnicalSkillMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GetHadTechnicalSkillListServiceTest {
	
	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private GetHadTechnicalSkillListService target;
	
	@Mock
	private HadTechnicalSkillMapper mapper;

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

	/**
	 * mapperメソッドが1回のみ呼ばれ、返り値がhadTechnicalSkillListなら成功
	 */
	@Test
	public void 所有テクニカルスキルを取得する() {
		Integer userId=1;
		List<HadTechnicalSkill>  hadTechnicalSkillList=new ArrayList<>();
		target.getHadTechnicalSkillListOfStage0or1or2(userId);
		verify(mapper,timeout(1)).selectByUserIdAndStage0or1or2(userId); 
		assertEquals(hadTechnicalSkillList, mapper.selectByUserIdAndStage0or1or2(userId));
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void 条件から取得() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		when(mapper.selectByUserId(any(Integer.class))).thenReturn(hadTechnicalSkillList);
		List<HadTechnicalSkill> result = target.getByUserId(1);
		assertEquals(hadTechnicalSkillList, result);
		verify(mapper, times(1)).selectByUserId(any(Integer.class));
	}

}
