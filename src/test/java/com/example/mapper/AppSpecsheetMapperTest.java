package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.example.AppSpecsheetExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.AppSpecsheetTestSql;
import common_sql.DepartmentTestSql;
import common_sql.DevExperienceTestSql;
import common_sql.TechnicalSkillTestSql;
import common_sql.UsedTechnicalSkillTestSql;
import common_sql.UserTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class AppSpecsheetMapperTest {
	
	@Autowired
	private AppSpecsheetMapper mapper;
	
	public static final  Destination dest = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						DepartmentTestSql.DEPARTMENT_INSERT,
						UserTestSql.USER_INSERT,
						AppSpecsheetTestSql.APP_SPECSHEET_INSERT,
						DevExperienceTestSql.DEV_EXPERIENCE_INSERT,
						TechnicalSkillTestSql.TECHNICAL_SKILL_INSERT,
						UsedTechnicalSkillTestSql.USED_TECHNICAL_SKILL_INSERT);
		DbSetup dbSetup = new DbSetup(dest, operation);
		dbSetup.launch();
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		DbSetup dbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		dbSetup.launch();
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void TC1_userId????????????????????????() {
		AppSpecsheetExample example = new AppSpecsheetExample();
		example.createCriteria().andUserIdEqualTo(2);
		example.setOrderByClause("version desc");
		List<AppSpecsheet> specsheetList = mapper.selectByExample(example);
		assertEquals(specsheetList.size(), 1);
		assertEquals(specsheetList.get(0).getSpecsheetId(), 3);
	}
	
	@Test
	public void TC2_userId???stage????????????() {
		List<Integer> specStageList = new ArrayList<>();
		specStageList.add(Stage.ACTIVE.getKey());
		List<Integer> htStageList = new ArrayList<>();
		htStageList.add(Stage.ACTIVE.getKey());
		List<AppSpecsheet> specsheetList = mapper.selectByCondition(0, 1, specStageList, htStageList);
		assertEquals(specsheetList.size(), 2);
		assertEquals(specsheetList.get(0).getSpecsheetId(), 2);
	}
	
	@Test
	public void TC3_PK???????????????????????????() {
		List<Integer> devExperienceIdList = new ArrayList<>();
		devExperienceIdList.add(2);
		List<AppSpecsheet> specsheetList = 
				mapper.selectByPrimaryKeyAndDevExperience(1, 1, devExperienceIdList, 1);
		assertEquals(specsheetList.size(), 1);
		assertEquals(specsheetList.get(0).getSpecsheetId(), 1);
		assertEquals(
				specsheetList.get(0).getDevExperienceList().get(0).getDevExperienceId(), 2);
	}
	
	@Test
	public void TC4_insert() {
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(11);
		specsheet.setUserId(3);
		specsheet.setEmployeeId(3333);
		specsheet.setGeneration("20?????????");
		specsheet.setGender('M');
		specsheet.setNearestStation("?????????");
		specsheet.setStartBusinessDate("?????????");
		specsheet.setEngineerPeriod(10);
		specsheet.setSePeriod(3);
		specsheet.setPgPeriod(7);
		specsheet.setItPeriod(15);
		specsheet.setAppeal("????????????");
		specsheet.setEffort("???????????????");
		specsheet.setCertification("??????");
		specsheet.setPreJob("??????");
		specsheet.setAdminComment("????????????");
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet.setUpdater("tester");
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		specsheet.setVersion(1);
		int result = mapper.insert(specsheet);
		assertEquals(result, 1);
		AppSpecsheetExample example = new AppSpecsheetExample();
		example.createCriteria().andUserIdEqualTo(3);
		example.setOrderByClause("version desc");
		List<AppSpecsheet> specsheetList = mapper.selectByExample(example);
		assertEquals(specsheetList.size(), 1);
		assertEquals(
				specsheetList.get(0).getSpecsheetId(), specsheet.getSpecsheetId());
		assertEquals(
				specsheetList.get(0).getEmployeeId(), specsheet.getEmployeeId());
	}
	
	@Test
	public void TC5_PK?????????????????????????????????() {
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setUserId(1);
		specsheet.setEmployeeId(1111);
		specsheet.setGeneration("20?????????");
		specsheet.setGender('M');
		specsheet.setNearestStation("?????????");
		specsheet.setStartBusinessDate("?????????");
		specsheet.setEngineerPeriod(10);
		specsheet.setSePeriod(3);
		specsheet.setPgPeriod(7);
		specsheet.setItPeriod(15);
		specsheet.setAppeal("????????????");
		specsheet.setEffort("???????????????");
		specsheet.setCertification("??????");
		specsheet.setPreJob("??????");
		specsheet.setAdminComment("????????????");
		specsheet.setStage(Stage.ACTIVE.getKey());
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 7, 8, 11, 11, 11));
		specsheet.setUpdater("tester");
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 8, 11, 11, 11));
		specsheet.setVersion(2);
		int result = mapper.updateByPrimaryKey(specsheet);
		assertEquals(result, 1, "TC5:?????????????????????????????????????????????");
		List<Integer> devExperienceIdList = new ArrayList<>();
		devExperienceIdList.add(11);
		List<AppSpecsheet> specsheetList = 
				mapper.selectByPrimaryKeyAndDevExperience(2, 1, devExperienceIdList, 3);
		assertEquals(specsheetList.size(), 1);
		assertEquals(
				specsheetList.get(0).getSpecsheetId(), specsheet.getSpecsheetId());
		assertEquals(
				specsheetList.get(0).getNearestStation(), specsheet.getNearestStation());
	}
	
	@Test
	public void TC6_PK????????????????????????????????????() {
		AppSpecsheet specsheet = new AppSpecsheet();
		specsheet.setSpecsheetId(2);
		specsheet.setUserId(1);
		specsheet.setAdminComment("????????????");
		specsheet.setStage(Stage.REQUESTING.getKey());
		specsheet.setUpdater("tester");
		specsheet.setUpdatedAt(LocalDateTime.of(2020, 7, 8, 22, 22, 22));
		specsheet.setVersion(2);
		int result = mapper.updateStageByPrimaryKey(specsheet);
		assertEquals(result, 1, "TC6:?????????????????????????????????????????????");
		List<Integer> devExperienceIdList = new ArrayList<>();
		devExperienceIdList.add(11);
		List<AppSpecsheet> specsheetList = 
				mapper.selectByPrimaryKeyAndDevExperience(2, 1, devExperienceIdList, 3);
		assertEquals(specsheetList.size(), 1);
		assertEquals(
				specsheetList.get(0).getSpecsheetId(), specsheet.getSpecsheetId());
		assertEquals(specsheetList.get(0).getStage(), specsheet.getStage());
	}
	
	@Test
	public void TC7_PK?????????????????????????????????() {
		int result = mapper.deleteByPrimaryKey(1);
		assertEquals(result, 1);
		AppSpecsheet result1 = mapper.selectByPrimaryKey(1);
		assertNull(result1);
	}
	
	@Test
	public void selectByStageAndApplicant????????????() {
		Integer stage = 0;
		String applicant = "tester";
		Integer startNumber = 0;
		List<AppSpecsheet> actual = mapper.selectByStageAndApplicant(stage, applicant, startNumber);
		assertEquals(actual.size(), 3);
		assertEquals(actual.get(0).getSpecsheetId(), 1);
		assertEquals(actual.get(1).getSpecsheetId(), 2);
		assertEquals(actual.get(2).getSpecsheetId(), 3);
	}
	
	@Test
	public void selectByStageAndUserId????????????() {
		Integer stage = 0;
		Integer userId = 2;
		Integer startNumber = 0;
		List<AppSpecsheet> actual = mapper.selectByStageAndUserId(stage, userId, startNumber);
		assertEquals(actual.size(), 1);
		assertEquals(actual.get(0).getSpecsheetId(), 3);
	}
	
	@Test
	public void countByExample????????????() {
		AppSpecsheetExample example = new AppSpecsheetExample();
		example.or()
			.andStageEqualTo("0")
			.andCreatorLike("%tester%");
		Integer count = mapper.countByExample(example);
		assertEquals(count, 3);
	}
}
