package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
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

import com.example.domain.EngineerSkill;
import com.example.example.EngineerSkillExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.EngineerSkillTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
public class EngineerSkillMapperTest {
	
	@Autowired
	private EngineerSkillMapper mapper;
	
	/** DBコネクション情報 */
	private static final  Destination DEST = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						EngineerSkillTestSql.ENGINEER_SKILL_INSERT);
		DbSetup dbSetup = new DbSetup(DEST, operation);
		dbSetup.launch();
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		DbSetup dbSetup = new DbSetup(DEST, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		dbSetup.launch();
	}

	@BeforeEach
	public void setUp() throws Exception {
		EngineerSkill actual1 = mapper.selectByPrimaryKey(1);
		EngineerSkill actual2 = mapper.selectByPrimaryKey(2);
		EngineerSkill actual3 = mapper.selectByPrimaryKey(3);
		assertEquals(actual1, engineerSkillTestData1());
		assertEquals(actual2, engineerSkillTestData2());
		assertEquals(actual3, engineerSkillTestData3());
		
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void エンジニアスキルの検索() {
		EngineerSkillExample ex = new EngineerSkillExample();
		ex.createCriteria().andStageEqualTo("0").andDepartmentIdEqualTo(1);
		List<EngineerSkill> engineerSkillListInDb = mapper.selectByExample(ex);
		assertEquals(engineerSkillListInDb.get(0), engineerSkillTestData1());
		assertEquals(engineerSkillListInDb.get(1), engineerSkillTestData2());
		assertEquals(engineerSkillListInDb.get(2), engineerSkillTestData3());
	}
	
	
	/**
	 * 比較用エンジニアスキルデータ1.
	 * 
	 * @return エンジニアスキルデータ
	 */
	public EngineerSkill engineerSkillTestData1() {
		EngineerSkill engineerSkill = new EngineerSkill();
		engineerSkill.setEngineerSkillId(1);
		engineerSkill.setName("プログラム");
		engineerSkill.setDepartmentId(1);
		engineerSkill.setStage(0);
		engineerSkill.setCreator("テスト太郎");
		engineerSkill.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		engineerSkill.setUpdater("テスト太郎");
		engineerSkill.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		engineerSkill.setVersion(1);
		return engineerSkill;
	}
	
	/**
	 * 比較用エンジニアスキルデータ2.
	 * 
	 * @return エンジニアスキルデータ
	 */
	public EngineerSkill engineerSkillTestData2() {
		EngineerSkill engineerSkill = new EngineerSkill();
		engineerSkill.setEngineerSkillId(2);
		engineerSkill.setName("データベース");
		engineerSkill.setDepartmentId(1);
		engineerSkill.setStage(0);
		engineerSkill.setCreator("テスト太郎");
		engineerSkill.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		engineerSkill.setUpdater("テスト太郎");
		engineerSkill.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		engineerSkill.setVersion(1);
		return engineerSkill;
	}
	
	/**
	 * 比較用エンジニアスキルデータ3.
	 * 
	 * @return エンジニアスキルデータ
	 */
	public EngineerSkill engineerSkillTestData3() {
		EngineerSkill engineerSkill = new EngineerSkill();
		engineerSkill.setEngineerSkillId(3);
		engineerSkill.setName("ネットワーク");
		engineerSkill.setDepartmentId(1);
		engineerSkill.setStage(0);
		engineerSkill.setCreator("テスト太郎");
		engineerSkill.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		engineerSkill.setUpdater("テスト太郎");
		engineerSkill.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		engineerSkill.setVersion(1);
		return engineerSkill;
	}
}
