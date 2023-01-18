package com.example.service.technique;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.TechnicalSkill;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTechnicalSkillListByTechnicalSkillIdListAndVersionListServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;

	@InjectMocks
	private GetTechnicalSkillListByTechnicalSkillIdListAndVersionListService service;
	
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
	public void テクニカルスキルIDリストとバージョンリストからテクニカルスキルリストを取得する() {
		List<Integer> technicalSkillIdList = new ArrayList<>();
		List<Integer> versionList = new ArrayList<>();
		Integer technicalSkillId = 1;
		Integer technicalSkillId2 = 2;
		Integer version = 1;
		Integer version2 = 1;

		technicalSkillIdList.add(technicalSkillId);
		technicalSkillIdList.add(technicalSkillId2);
		versionList.add(version);
		versionList.add(version2);
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		TechnicalSkill ts = new TechnicalSkill();
		TechnicalSkill ts2 = new TechnicalSkill();
		ts.setTechnicalSkillId(technicalSkillId);
		ts2.setTechnicalSkillId(technicalSkillId2);
		ts.setVersion(version);
		ts2.setVersion(version2);
		technicalSkillList.add(ts);
		technicalSkillList.add(ts2);

		when(mapper.selectByTechinicalSkillIdListAndVersionList(technicalSkillIdList, versionList))
				.thenReturn(technicalSkillList);

		List<TechnicalSkill> actualList = service
				.getTechnicalSkillListByTechnicalSkillIdListAndVersionList(technicalSkillIdList, versionList);

		assertEquals(technicalSkillId, actualList.get(0).getTechnicalSkillId());
		assertEquals(technicalSkillId2, actualList.get(1).getTechnicalSkillId());
		assertEquals(version, actualList.get(0).getVersion());
		assertEquals(version2, actualList.get(1).getVersion());
	}
	
	@Test
	public void 引数のリストの中身がない場合() {
		List<Integer> technicalSkillIdList = new ArrayList<>();
		List<Integer> versionList = new ArrayList<>();

		List<TechnicalSkill> technicalSkillList = new ArrayList<>();

		when(mapper.selectByTechinicalSkillIdListAndVersionList(technicalSkillIdList, versionList))
				.thenReturn(technicalSkillList);

		List<TechnicalSkill> actualList = service
				.getTechnicalSkillListByTechnicalSkillIdListAndVersionList(technicalSkillIdList, versionList);

		assertNull(actualList);
	}
	
	

}
