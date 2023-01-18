package com.example.service.spec;

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

import com.example.domain.DevExperience;
import com.example.mapper.DevExperienceMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDevExperienceServiceTest {
	
	@Mock
	private DevExperienceMapper mapper;
	
	@InjectMocks
	private GetDevExperienceService service;

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
	public void 開発経験リストを取得() {
		List<DevExperience> devExperienceList = new ArrayList<>();
		DevExperience devExperience = new DevExperience();
		devExperience.setDevExperienceId(1);
		devExperienceList.add(devExperience);
		when(mapper.selectBySpecsheetId(any(Integer.class))).thenReturn(devExperienceList);
		List<DevExperience> resultList = service.getDevExperienceBySpecsheetId(1);
		assertEquals(resultList.get(0).getDevExperienceId(), devExperience.getDevExperienceId());
		verify(mapper, times(1)).selectBySpecsheetId(any(Integer.class));
	}

}
