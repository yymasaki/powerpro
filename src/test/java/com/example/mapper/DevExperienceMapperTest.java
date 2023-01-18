package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.example.domain.DevExperience;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.AppSpecsheetTestSql;
import common_sql.DepartmentTestSql;
import common_sql.DevExperienceTestSql;
import common_sql.UserTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class DevExperienceMapperTest {
	
	@Autowired
	private DevExperienceMapper mapper;
	
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
						DevExperienceTestSql.DEV_EXPERIENCE_INSERT);
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
	public void TC1_specsheetIdから取得() {
		List<DevExperience> devExperienceList = mapper.selectBySpecsheetId(1);
		assertEquals(devExperienceList.size(), 10);
		assertEquals(devExperienceList.get(0).getDevExperienceId(), 9);
		assertEquals(devExperienceList.get(9).getDevExperienceId(), 1);
	}
	
	@Test
	public void TC2_データを2件挿入() {
		List<DevExperience> devExperienceList = new ArrayList<>();
		DevExperience devExperience = new DevExperience();
		devExperience.setDevExperienceId(101);
		devExperience.setSpecsheetId(1);
		devExperience.setProjectName("テストプロジェクト11");
		devExperience.setStartedOn(LocalDate.of(2020, 4, 1));
		devExperience.setFinishedOn(LocalDate.of(2020, 7, 1));
		devExperience.setTeamMembers(3);
		devExperience.setProjectMembers(5);
		devExperience.setRole("PG");
		devExperience.setProjectDetails("詳細");
		devExperience.setTasks("タスク");
		devExperience.setAcquired("知見");
		devExperience.setStage(Stage.ACTIVE.getKey());
		devExperienceList.add(devExperience);
		DevExperience devExperience2 = new DevExperience();
		devExperience2.setDevExperienceId(102);
		devExperience2.setSpecsheetId(1);
		devExperience2.setProjectName("テストプロジェクト12");
		devExperience2.setStartedOn(LocalDate.of(2020,2, 1));
		devExperience2.setFinishedOn(LocalDate.of(2020, 6, 1));
		devExperience2.setTeamMembers(3);
		devExperience2.setProjectMembers(5);
		devExperience2.setRole("PG");
		devExperience2.setProjectDetails("詳細");
		devExperience2.setTasks("タスク");
		devExperience2.setAcquired("知見");
		devExperience2.setStage(Stage.ACTIVE.getKey());
		devExperienceList.add(devExperience2);
		int result = mapper.insertList(devExperienceList);
		List<DevExperience> resultList = mapper.selectBySpecsheetId(1);
		assertEquals(result, 2);
		assertEquals(resultList.size(), 12);
	}
	
	@Test
	public void TC3_データを2件更新() {
		List<DevExperience> devExperienceList = new ArrayList<>();
		DevExperience devExperience = new DevExperience();
		devExperience.setDevExperienceId(1);
		devExperience.setSpecsheetId(1);
		devExperience.setProjectName("テストプロジェクト1");
		devExperience.setStartedOn(LocalDate.of(2020, 1, 1));
		devExperience.setFinishedOn(LocalDate.of(2020, 2, 1));
		devExperience.setTeamMembers(3);
		devExperience.setProjectMembers(5);
		devExperience.setRole("PG");
		devExperience.setProjectDetails("詳細");
		devExperience.setTasks("タスク");
		devExperience.setAcquired("知見");
		devExperience.setStage(Stage.ACTIVE.getKey());
		devExperienceList.add(devExperience);
		DevExperience devExperience2 = new DevExperience();
		devExperience2.setDevExperienceId(2);
		devExperience2.setSpecsheetId(1);
		devExperience2.setProjectName("テストプロジェクト2");
		devExperience2.setStartedOn(LocalDate.of(2020,5, 1));
		devExperience2.setFinishedOn(LocalDate.of(2020, 6, 1));
		devExperience2.setTeamMembers(3);
		devExperience2.setProjectMembers(6);
		devExperience2.setRole("PG");
		devExperience2.setProjectDetails("詳細");
		devExperience2.setTasks("タスク");
		devExperience2.setAcquired("知見");
		devExperience2.setStage(Stage.ACTIVE.getKey());
		devExperienceList.add(devExperience2);
		int result = mapper.updateListByPrimaryKey(devExperienceList);
		assertEquals(result, 2);
		List<DevExperience> resultList = mapper.selectBySpecsheetId(1);
		List<DevExperience> resultList2 = resultList.stream()
				.filter(d -> d.getDevExperienceId().equals(1))
				.collect(Collectors.toList());
		assertEquals(resultList2.get(0).getProjectName(), "テストプロジェクト1");
		List<DevExperience> resultList3 = resultList.stream()
				.filter(d -> d.getDevExperienceId().equals(2))
				.collect(Collectors.toList());
		assertEquals(resultList3.get(0).getProjectMembers(), 6);
	}
	
	@Test
	public void TC4_データを2件削除() {
		List<DevExperience> devExperienceList = new ArrayList<>();
		DevExperience devExperience = new DevExperience();
		devExperience.setDevExperienceId(1);
		devExperienceList.add(devExperience);
		DevExperience devExperience2 = new DevExperience();
		devExperience2.setDevExperienceId(2);
		devExperienceList.add(devExperience2);
		int result = mapper.deleteListByPrimaryKey(devExperienceList);
		assertEquals(result, 2);
		List<DevExperience> resultList = mapper.selectBySpecsheetId(1);
		assertEquals(resultList.size(), 8);
	}

}
