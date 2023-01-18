package com.example.service.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetSpecsheetByConditionServiceTest {
	
	private Integer specsheetId = 0;
	private Integer userId = 1;
	private List<Integer> specStageList = new ArrayList<>();
	private List<Integer> htStageList = new ArrayList<>();
	private List<AppSpecsheet> specsheetList = new ArrayList<>();
	
	@Mock
	private AppSpecsheetMapper mapper;
	
	@InjectMocks
	private GetSpecsheetByConditionService service;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		specStageList.add(Stage.ACTIVE.getKey());
		htStageList.add(Stage.ACTIVE.getKey());
		AppSpecsheet specsheet1 = new AppSpecsheet();
		specsheet1.setSpecsheetId(1);
		specsheet1.setUserId(1);
		specsheet1.setEmployeeId(1111);
		specsheet1.setGeneration("20代前半");
		specsheet1.setGender('M');
		specsheet1.setNearestStation("新宿駅");
		specsheet1.setStartBusinessDate("応相談");
		specsheet1.setEngineerPeriod(10);
		specsheet1.setSePeriod(3);
		specsheet1.setPgPeriod(7);
		specsheet1.setItPeriod(15);
		specsheet1.setAppeal("アピール");
		specsheet1.setEffort("エフォート");
		specsheet1.setCertification("資格");
		specsheet1.setPreJob("前職");
		specsheet1.setAdminComment("コメント");
		specsheet1.setStage(0);
		specsheet1.setCreator("tester");
		specsheet1.setCreatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet1.setUpdater("tester");
		specsheet1.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet1.setVersion(1);
		specsheetList.add(specsheet1);
		AppSpecsheet specsheet2 = new AppSpecsheet();
		specsheet2.setSpecsheetId(2);
		specsheet2.setUserId(1);
		specsheet2.setEmployeeId(1111);
		specsheet2.setGeneration("20代前半");
		specsheet2.setGender('M');
		specsheet2.setNearestStation("新宿駅");
		specsheet2.setStartBusinessDate("応相談");
		specsheet2.setEngineerPeriod(10);
		specsheet2.setSePeriod(3);
		specsheet2.setPgPeriod(7);
		specsheet2.setItPeriod(15);
		specsheet2.setAppeal("アピール");
		specsheet2.setEffort("エフォート");
		specsheet2.setCertification("資格");
		specsheet2.setPreJob("前職");
		specsheet2.setAdminComment("コメント");
		specsheet2.setStage(0);
		specsheet2.setCreator("tester");
		specsheet2.setCreatedAt(LocalDateTime.of(2020, 7, 7, 22, 22, 22));
		specsheet2.setUpdater("tester");
		specsheet2.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 22, 22, 22));
		specsheet2.setVersion(1);
		specsheetList.add(specsheet2);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 条件からスペックシートを取得する() {
		when(mapper.selectByCondition(specsheetId, userId, specStageList, htStageList)).thenReturn(specsheetList);
		List<AppSpecsheet> resultList = service.getSpecsheetByCondition(specsheetId, userId, specStageList, htStageList);
		assertEquals(resultList.size(), specsheetList.size());
		verify(mapper, times(1)).selectByCondition(specsheetId, userId, specStageList, htStageList);
	}

}
