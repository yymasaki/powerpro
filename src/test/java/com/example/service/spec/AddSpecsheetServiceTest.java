package com.example.service.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddSpecsheetServiceTest {
	
	@Mock
	private AppSpecsheetMapper mapper;
	
	@InjectMocks
	private AddSpecsheetService service;
	
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
	public void スペックシートを登録() {
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		AppSpecsheet result = service.addSpecsheet(specsheet);
		assertEquals(result.getSpecsheetId(), specsheet.getSpecsheetId());
		verify(mapper, times(1)).insert(any(AppSpecsheet.class));
	}

}
