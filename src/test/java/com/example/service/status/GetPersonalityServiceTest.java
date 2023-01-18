package com.example.service.status;

import static org.junit.Assert.assertEquals;
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

import com.example.domain.Personality;
import com.example.mapper.PersonalityMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetPersonalityServiceTest {

	@Mock
	private PersonalityMapper mapper;

	@InjectMocks
	private GetPersonalityService target;

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
	public void 性格リストの取得() {
		List<Personality> personalityList = new ArrayList<>();

		Personality personality1 = new Personality();
		personality1.setPersonalityId(1);
		personality1.setName("テスト1");
		personalityList.add(personality1);

		Personality personality2 = new Personality();
		personality2.setPersonalityId(2);
		personality2.setName("テスト2");
		personalityList.add(personality2);

		Personality personality3 = new Personality();
		personality3.setPersonalityId(3);
		personality3.setName("テスト3");
		personalityList.add(personality3);

		when(mapper.selectByExample(any())).thenReturn(personalityList);

		List<Personality> actual = target.getPersonality();
		assertEquals(personalityList, actual);
		assertEquals(3, actual.size());
	}
}
