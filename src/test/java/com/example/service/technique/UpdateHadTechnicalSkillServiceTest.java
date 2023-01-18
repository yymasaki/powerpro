package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mapper.HadTechnicalSkillMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UpdateHadTechnicalSkillServiceTest {

	
	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private UpdateHadTechnicalSkillService target;
	
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
	 * mapperが一回呼ばれていれば成功
	 */
	@Test
	public void 所有テクニカルスキルの利用歴を更新する() {
		when(mapper.updateUsingPeriodByHadTechnicalSkillList(any(), any())).thenReturn(1);
		int numOfUpdate=target.updateUsingPeriodByHadTechnicalSkillList(any(), any());
		assertEquals(1, numOfUpdate);
		verify(mapper,timeout(1)).updateUsingPeriodByHadTechnicalSkillList(any(), any()); 
	}

}
