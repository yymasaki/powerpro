package com.example.service.technique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UpdateRequestTechnicalSkillServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private UpdateRequestTechnicalSkillService service;
	
	
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
	public void テクニカルスキルを更新する() {
		LocalDateTime createdAt=LocalDateTime.of(2020,5,13,12,00,00);
		LocalDateTime current=LocalDateTime.now();
		TechnicalSkill technicalSkill=new TechnicalSkill(5,"MySQL", 5, null, 2, "テスト太郎",createdAt, "管理者次郎", current, 2);
		
		when(mapper.updateByPrimaryKeySelective(any(TechnicalSkill.class))).thenReturn(1);
		int actual=service.updateRequestTechnicalSkill(technicalSkill);
		
		assertEquals(1, actual);
		verify(mapper, times(1)).updateByPrimaryKeySelective(any(TechnicalSkill.class));
	}

}
