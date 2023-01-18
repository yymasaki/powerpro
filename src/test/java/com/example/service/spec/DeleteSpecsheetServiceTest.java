package com.example.service.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.example.mapper.AppSpecsheetMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteSpecsheetServiceTest {

	@Mock
	private AppSpecsheetMapper mapper;
	
	@InjectMocks
	private DeleteSpecsheetService service;
	
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
	public void スペックシートを削除() {
		Integer specsheetId = 1;
		when(mapper.deleteByPrimaryKey(any(Integer.class))).thenReturn(1);
		int result = service.deleteSpecsheet(specsheetId);
		assertEquals(result, 1);
		verify(mapper, times(1)).deleteByPrimaryKey(any(Integer.class));
	}

}
