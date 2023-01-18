package com.example.service.spec;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import com.example.domain.AppSpecsheet;
import com.example.example.AppSpecsheetExample;
import com.example.mapper.AppSpecsheetMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetNewestSpecsheetServiceTest {

	private Integer userId;
	private List<AppSpecsheet> specsheetList = new ArrayList<>();

	@Mock
	private AppSpecsheetMapper mapper;
	
	@InjectMocks
	private GetNewestSpecsheetService service;
	
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
	public void userIdが1のデータを取得() {
		userId = 1;
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheet.setUserId(1);
		specsheet.setEmployeeId(1111);
		specsheet.setGeneration("20代前半");
		specsheet.setGender('M');
		specsheet.setNearestStation("新宿駅");
		specsheet.setStartBusinessDate("応相談");
		specsheet.setEngineerPeriod(10);
		specsheet.setSePeriod(3);
		specsheet.setPgPeriod(7);
		specsheet.setItPeriod(15);
		specsheet.setAppeal("アピール");
		specsheet.setEffort("エフォート");
		specsheet.setCertification("資格");
		specsheet.setPreJob("前職");
		specsheet.setAdminComment("コメント");
		specsheet.setStage(0);
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet.setUpdater("tester");
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet.setVersion(1);
		specsheetList.add(specsheet);
		when(mapper.selectByExample(any(AppSpecsheetExample.class))).thenReturn(specsheetList);
		AppSpecsheet result = service.getNewestSpecsheet(userId);
		assertEquals(result.getSpecsheetId(), specsheet.getSpecsheetId());
		verify(mapper, times(1)).selectByExample(any(AppSpecsheetExample.class));
	}

	@Test
	public void userIdが3の場合nullを返す() {
		userId = 3;
		when(mapper.selectByExample(any(AppSpecsheetExample.class))).thenReturn(specsheetList);
		AppSpecsheet result = service.getNewestSpecsheet(userId);
		assertNull(result);
		verify(mapper, times(1)).selectByExample(any(AppSpecsheetExample.class));
	}

}
