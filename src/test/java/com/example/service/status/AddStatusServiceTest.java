package com.example.service.status;

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

import com.example.domain.Status;
import com.example.mapper.StatusMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddStatusServiceTest {
	
	@Mock
	private StatusMapper mapper;
	
	@InjectMocks
	private AddStatusService target;

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
	public void バージョン有のステータスを追加する() {
		Status status = statusTestData();
		target.addStatus(status, 1);
		verify(mapper, times(1)).insertSelective(status);
	}
	
	@Test
	public void バージョン無のステータスを追加する() {
		Status status = statusTestData();
		target.addStatus(status, null);
		verify(mapper, times(1)).insertSelective(status);
	}
	
	public Status statusTestData() {
		Status status = new Status();
		status.setCreator("テスト太郎");
		status.setUpdater("テスト太郎");
		status.setStage(0);
		status.setUserId(1);
		status.setUsingMobile("iPhone");
		status.setUsingPc("mac");
		return status;
	}

}
