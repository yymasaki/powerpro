package com.example.service.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.example.mapper.StatusMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteStatusServiceTest {
	
	@Mock
	private StatusMapper mapper;
	
	@InjectMocks
	private DeleteStatusService target;

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
	public void ステータスIDからステータスの削除() {
		Integer statusId = 1;
		target.deleteStatusByPrimaryKey(statusId);
		verify(mapper, times(1)).deleteByPrimaryKey(statusId);
	}
	
	@Test
	public void ステータス条件からステータスの削除() {
		Integer userId = 1;
		target.deleteStatusByUserId(userId);
		verify(mapper, times(1)).deleteByExample(any());
	}

}
