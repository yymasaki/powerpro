package com.example.service.status;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class GetStatusAndSkillsServiceTest {
	
	@Mock
	private StatusMapper mapper;
	
	@InjectMocks
	private GetStatusAndSkillsService target;

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
	public void PKからステータスを取得する() {
		Integer statusId = 1;
		
		Status status = new Status();
		status.setStatusId(statusId);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		
		when(mapper.selectStatusAndSkillsByPrimaryKey(statusId)).thenReturn(status);
		Status actual = target.getStatusAndSkillsByPrimaryKey(statusId);
		assertEquals(status, actual);
	}
	
	@Test
	public void ユーザーIDとステージからステータスを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		statusList.add(status);
		
		when(mapper.selectStatusAndSkillsByUserIdAndStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusList);
		Status actual = target.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), null);
		assertEquals(statusList.get(0), actual);
	}
	
	@Test
	public void ユーザーIDとステージからnullを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		
		when(mapper.selectStatusAndSkillsByUserIdAndStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusList);
		Status actual = target.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), null);
		assertNull(actual);
	}
	
	@Test
	public void ユーザーIDとステージから性格以外のステータスを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("Windows");
		status.setUsingMobile("iPhone");
		statusList.add(status);
		
		when(mapper.selectStatusAndSkillsWithoutPersonalitiesByUserIdAndStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusList);
		Status actual = target.getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), null);
		assertEquals(statusList.get(0), actual);
	}
	
	@Test
	public void ユーザーIDとステージから性格以外のnullを取得する() {
		Integer userId = 1;
		List<Status> statusList = new ArrayList<>();
		
		when(mapper.selectStatusAndSkillsWithoutPersonalitiesByUserIdAndStages(userId, Stage.ACTIVE.getKey(), null)).thenReturn(statusList);
		Status actual = target.getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), null);
		assertNull(actual);
	}

}
