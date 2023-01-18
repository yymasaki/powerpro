package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
import com.example.domain.UsedTechnicalSkill;
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
public class UsedTechnicalSkillMapperTest {
	
	@Autowired
	private UsedTechnicalSkillMapper mapper;
	
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
	public void TC1_データを2件挿入() {
		List<UsedTechnicalSkill> usedTechnicalSkillList = new ArrayList<>();
		UsedTechnicalSkill usedTechnicalSkill = new UsedTechnicalSkill();
		usedTechnicalSkill.setUsedTechnicalSkillId(11);
		usedTechnicalSkill.setDevExperienceId(2);
		usedTechnicalSkill.setTechnicalSkillId(1);
		usedTechnicalSkill.setStage(Stage.ACTIVE.getKey());
		usedTechnicalSkillList.add(usedTechnicalSkill);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setUsedTechnicalSkillId(12);
		usedTechnicalSkill2.setDevExperienceId(2);
		usedTechnicalSkill2.setTechnicalSkillId(2);
		usedTechnicalSkill2.setStage(Stage.ACTIVE.getKey());
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		int result = mapper.insertList(usedTechnicalSkillList);
		assertEquals(result, 2);
	}
	
	@Test
	public void TC2_データを2件削除() {
		List<UsedTechnicalSkill> usedTechnicalSkillList = new ArrayList<>();
		UsedTechnicalSkill usedTechnicalSkill = new UsedTechnicalSkill();
		usedTechnicalSkill.setUsedTechnicalSkillId(1);
		usedTechnicalSkillList.add(usedTechnicalSkill);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setUsedTechnicalSkillId(2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		int result = mapper.deleteListByPrimaryKey(usedTechnicalSkillList);
		assertEquals(result, 2);
		UsedTechnicalSkill result1 = mapper.selectByPrimaryKey(1);
		assertNull(result1);
		UsedTechnicalSkill result2 = mapper.selectByPrimaryKey(2);
		assertNull(result2);
	}

}
