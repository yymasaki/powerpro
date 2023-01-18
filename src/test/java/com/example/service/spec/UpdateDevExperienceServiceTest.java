package com.example.service.spec;

import static org.junit.Assert.assertEquals;
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

import com.example.domain.DevExperience;
import com.example.mapper.DevExperienceMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateDevExperienceServiceTest {

	@Mock
	private DevExperienceMapper mapper;
	
	@InjectMocks
	private UpdateDevExperienceService service;
	
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
	public void 開発経験リストを更新() {
		List<DevExperience> devExperienceList = new ArrayList<>();
		DevExperience devExperience = new DevExperience();
		devExperience.setDevExperienceId(1);
		devExperienceList.add(devExperience);
		when(mapper.updateListByPrimaryKey(any())).thenReturn(1);
		int result = service.updateListByPrimaryKey(devExperienceList);
		assertEquals(result, 1);
		verify(mapper, times(1)).updateListByPrimaryKey(any());
	}

}
