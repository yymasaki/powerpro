package com.example.service.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Information;
import com.example.mapper.InformationMapper;

@SpringBootTest
public class GetInformationListServiceTest {

	
	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private GetInformationListService target;
	
	@Mock
	private InformationMapper informationMapper;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void お知らせを取得する_表示分のみ() {
		int userId=1;
		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime()); 
		int offset =0;
		List<Information> informationList=new ArrayList<>();
		target.getInformationList(userId, offset);
		//List<Information>型の変数が返ってくることを定義
		when(informationMapper.selectByDepartmentIdAndStageAndCurrentDate(userId, currentDate, offset)).thenReturn(informationList);
		//一回だけ呼ばれるかチェック
		verify(informationMapper,times(1)).selectByDepartmentIdAndStageAndCurrentDate(userId, currentDate, offset); 
		//List<Information> informationListが返ってきているか確認
		assertEquals(informationMapper.selectByDepartmentIdAndStageAndCurrentDate(userId, currentDate, offset),informationList);
	
	}
	
	@Test
	public void お知らせを取得する_全件() {
		int userId=1;
		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime()); 
		List<Information> informationList=new ArrayList<>();
		target.getAllInformationList(userId);
		//List<Information>型の変数が返ってくることを定義
		when(informationMapper.selectAll(userId, currentDate)).thenReturn(informationList);
		//一回だけ呼ばれるかチェック
		verify(informationMapper,times(1)).selectAll(userId, currentDate);
		//List<Information> informationListが返ってきているか確認
		assertEquals(informationMapper.selectAll(userId, currentDate),informationList);
	}

}
