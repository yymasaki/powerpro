package com.example.service.status;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

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
public class UpdateStatusServiceTest {
	
	@Mock
	private StatusMapper mapper;
	
	@InjectMocks
	private UpdateStatusService target;

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
	public void ステータスのアップデートを行う() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("mac");
		status.setUsingMobile("iPhone");
		status.setCreator("テスト太郎");
		status.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setUpdater("テスト太郎");
		status.setStage(0);
		status.setVersion(1);
		
		target.updateStatus(status);
		verify(mapper, times(1)).updateByPrimaryKeySelective(status);
	}

}
