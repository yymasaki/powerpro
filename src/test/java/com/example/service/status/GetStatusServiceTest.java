package com.example.service.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import com.example.common.Stage;
import com.example.domain.Status;
import com.example.mapper.StatusMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetStatusServiceTest {

	@Mock
	private StatusMapper mapper;
	
	@InjectMocks
	private GetStatusService target;

	
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
	public void ユーザーIDとステージ1つからステータスを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		statusList.add(status);
		
		when(mapper.selectByExample(any())).thenReturn(statusList);
		Status actual = target.getStatusByStages(userId, Stage.ACTIVE.getKey(), null);
		assertEquals(statusList.get(0), actual);
	}
	
	@Test
	public void ユーザーIDとステージ2つからステータスを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		statusList.add(status);
		
		when(mapper.selectByExample(any())).thenReturn(statusList);
		Status actual = target.getStatusByStages(userId, Stage.ACTIVE.getKey(), Stage.REQUESTING.getKey());
		assertEquals(statusList.get(0), actual);
	}
	
	@Test
	public void ユーザーIDとステージからnullを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		when(mapper.selectByExample(any())).thenReturn(statusList);
		Status actual = target.getStatusByStages(userId, Stage.ACTIVE.getKey(), Stage.REQUESTING.getKey());
		assertNull(actual);
	}
	
	@Test
	public void PKからステータスを取得する() {
		Integer statusId = 1;
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		
		when(mapper.selectByPrimaryKey(statusId)).thenReturn(status);
		Status actual = target.getStatusByPrimaryKey(statusId);
		assertEquals(status, actual);
	}
	
	//もう一つあり

}
