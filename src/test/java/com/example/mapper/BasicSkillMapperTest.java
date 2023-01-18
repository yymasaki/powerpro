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

import com.example.domain.BasicSkill;
import com.example.example.BasicSkillExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.BasicSkillTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
public class BasicSkillMapperTest {
	
	@Autowired
	private BasicSkillMapper mapper;
	
	/** DBコネクション情報 */
	private static final  Destination dest = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						BasicSkillTestSql.BASIC_SKILL_INSERT);
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
		BasicSkill actual1 = mapper.selectByPrimaryKey(1);
		BasicSkill actual2 = mapper.selectByPrimaryKey(2);
		BasicSkill actual3 = mapper.selectByPrimaryKey(3);
		assertEquals(actual1, basicSkillTestData1());
		assertEquals(actual2, basicSkillTestData2());
		assertEquals(actual3, basicSkillTestData3());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 基本スキルの取得() {
		BasicSkillExample ex = new BasicSkillExample();
		ex.createCriteria().andStageEqualTo("0").andDepartmentIdEqualTo(1);
		List<BasicSkill> basicSkillListInDb = mapper.selectByExample(ex);
		assertEquals(basicSkillListInDb.get(0), basicSkillTestData1());
		assertEquals(basicSkillListInDb.get(1), basicSkillTestData2());
		assertEquals(basicSkillListInDb.get(2), basicSkillTestData3());
	}
	
	/**
	 * 比較用基本スキルデータ１
	 * 
	 * @return 基本スキルデータ
	 */
	public static BasicSkill basicSkillTestData1() {
		BasicSkill basicSkill = new BasicSkill();
		basicSkill.setBasicSkillId(1);
		basicSkill.setName("サンプル1");
		basicSkill.setDepartmentId(1);
		basicSkill.setStage(0);
		basicSkill.setCreator("テスト太郎");
		basicSkill.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		basicSkill.setUpdater("テスト太郎");
		basicSkill.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		basicSkill.setVersion(1);
		return basicSkill;
	}
	
	/**
	 * 比較用基本スキルデータ2
	 * 
	 * @return 基本スキルデータ
	 */
	public static BasicSkill basicSkillTestData2() {
		BasicSkill basicSkill = new BasicSkill();
		basicSkill.setBasicSkillId(2);
		basicSkill.setName("サンプル2");
		basicSkill.setDepartmentId(1);
		basicSkill.setStage(0);
		basicSkill.setCreator("テスト太郎");
		basicSkill.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		basicSkill.setUpdater("テスト太郎");
		basicSkill.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		basicSkill.setVersion(1);
		return basicSkill;
	}
	
	/**
	 * 比較用基本スキルデータ3
	 * 
	 * @return 基本スキルデータ
	 */
	public static BasicSkill basicSkillTestData3() {
		BasicSkill basicSkill = new BasicSkill();
		basicSkill.setBasicSkillId(3);
		basicSkill.setName("サンプル3");
		basicSkill.setDepartmentId(1);
		basicSkill.setStage(0);
		basicSkill.setCreator("テスト太郎");
		basicSkill.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		basicSkill.setUpdater("テスト太郎");
		basicSkill.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		basicSkill.setVersion(1);
		return basicSkill;
	}
}
