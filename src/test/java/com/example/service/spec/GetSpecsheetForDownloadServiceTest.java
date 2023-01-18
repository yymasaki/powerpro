package com.example.service.spec;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.example.domain.AppSpecsheet;
import com.example.mapper.AppSpecsheetMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetSpecsheetForDownloadServiceTest {
	
	private Integer specsheetId = 1;
	private Integer userId = 1;
	private List<Integer> devExperienceIdList = new ArrayList<>();
	private Integer version = 1;
	private List<AppSpecsheet> specsheetList = new ArrayList<>();
	
	@Mock
	private AppSpecsheetMapper mapper;
	
	@InjectMocks
	private GetSpecsheetForDownloadService service;

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
	public void ダウンロード用にスペックシート情報を取得() {
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(1);
		specsheetList.add(specsheet);
		when(mapper.selectByPrimaryKeyAndDevExperience(
				any(Integer.class), any(Integer.class), any(), any(Integer.class)))
				.thenReturn(specsheetList);
		AppSpecsheet result = 
				service.getSpecsheetForDownload(specsheetId, userId, devExperienceIdList, version);
		assertEquals(result.getSpecsheetId(), specsheet.getSpecsheetId());
		verify(mapper, times(1)).selectByPrimaryKeyAndDevExperience(
				any(Integer.class), any(Integer.class), any(), any(Integer.class));
	}
	
	@Test
	public void ダウンロード用にスペックシート情報を取得できない場合() {
		when(mapper.selectByPrimaryKeyAndDevExperience(
				any(Integer.class), any(Integer.class), any(), any(Integer.class)))
		.thenReturn(specsheetList);
		AppSpecsheet result = 
				service.getSpecsheetForDownload(specsheetId, userId, devExperienceIdList, version);
		assertNull(result);
		verify(mapper, times(1)).selectByPrimaryKeyAndDevExperience(
				any(Integer.class), any(Integer.class), any(), any(Integer.class));
	}

}
