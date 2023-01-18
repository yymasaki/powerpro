package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.assertEquals;

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

import com.example.domain.HadEngineerSkill;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.EngineerSkillTestSql;
import common_sql.HadEngineerSkillTestSql;
import common_sql.StatusTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class, 
	  DbUnitTestExecutionListener.class
	})
public class HadEngineerSkillMapperTest {
	
	@Autowired
	private HadEngineerSkillMapper hadEngineerSkillMapper;


	/** DBコネクション情報 */
	private static final  Destination DEST = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						EngineerSkillTestSql.ENGINEER_SKILL_INSERT,
						HadEngineerSkillTestSql.HAD_ENGINEER_SKILLS_INSERT,
						StatusTestSql.STATUS_INSERT);
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
		HadEngineerSkill hadEngineerSkillActual1 = hadEngineerSkillMapper.selectByPrimaryKey(1);
		HadEngineerSkill hadEngineerSkillActual2 = hadEngineerSkillMapper.selectByPrimaryKey(2);
		HadEngineerSkill hadEngineerSkillActual3 = hadEngineerSkillMapper.selectByPrimaryKey(3);
		
		assertEquals(hadEngineerSkillActual1, hadEngineerSkillTestData1());
		assertEquals(hadEngineerSkillActual2, hadEngineerSkillTestData2());
		assertEquals(hadEngineerSkillActual3, hadEngineerSkillTestData3());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	@Test
	public void データインサートを行う() {
		List<HadEngineerSkill> hadEngineerSkillList = new ArrayList<>();
		Integer statusId = 1;
		HadEngineerSkill hadEngineerSkill = new HadEngineerSkill();
		hadEngineerSkill.setHadEngineerSkillId(8);
		hadEngineerSkill.setEngineerSkillId(1);
		hadEngineerSkill.setStatusId(statusId);
		hadEngineerSkill.setScore(80);
		hadEngineerSkillList.add(hadEngineerSkill);	
		int count = hadEngineerSkillMapper.insertSelectiveList(hadEngineerSkillList);
		assertEquals(count, hadEngineerSkillList.size());
		//selectで確認
		HadEngineerSkill hadEngineerSkillInDb = hadEngineerSkillMapper.selectByPrimaryKey(8);
		//エンジニアスキルID,所有エンジニアスキルIDで確認
		assertEquals(hadEngineerSkill.getHadEngineerSkillId(), hadEngineerSkillInDb.getHadEngineerSkillId());
		assertEquals(hadEngineerSkill.getEngineerSkillId(), hadEngineerSkillInDb.getEngineerSkillId());
	}
	
	@Test
	public void データアップデートを行う() {
		List<HadEngineerSkill> hadEngineerSkillList = new ArrayList<>();
		Integer statusId = 1;
		HadEngineerSkill hadEngineerSkill = new HadEngineerSkill();
		hadEngineerSkill.setHadEngineerSkillId(1);
		hadEngineerSkill.setEngineerSkillId(1);
		hadEngineerSkill.setStatusId(statusId);
		hadEngineerSkill.setScore(80);
		hadEngineerSkillList.add(hadEngineerSkill);	
		int count = hadEngineerSkillMapper.bulkUpdate(hadEngineerSkillList, statusId);
		assertEquals(count, hadEngineerSkillList.size());
	}
	
	/**
	 * 所有エンジニアスキルテストデータ1.
	 * 
	 * @return 所有エンジニアスキルデータ
	 */
	public HadEngineerSkill hadEngineerSkillTestData1(){
		HadEngineerSkill hadEngineerSkill = new HadEngineerSkill();
		hadEngineerSkill.setHadEngineerSkillId(1);
		hadEngineerSkill.setEngineerSkillId(1);
		hadEngineerSkill.setStatusId(1);
		hadEngineerSkill.setScore(90);
		return hadEngineerSkill;
	}
	
	/**
	 * 所有エンジニアスキルテストデータ2.
	 * 
	 * @return 所有エンジニアスキルデータ
	 */
	public HadEngineerSkill hadEngineerSkillTestData2(){
		HadEngineerSkill hadEngineerSkill = new HadEngineerSkill();
		hadEngineerSkill.setHadEngineerSkillId(2);
		hadEngineerSkill.setEngineerSkillId(2);
		hadEngineerSkill.setStatusId(1);
		hadEngineerSkill.setScore(50);
		return hadEngineerSkill;
	}
	
	/**
	 * 所有エンジニアスキルテストデータ3.
	 * 
	 * @return 所有エンジニアスキルデータ
	 */
	public HadEngineerSkill hadEngineerSkillTestData3(){
		HadEngineerSkill hadEngineerSkill = new HadEngineerSkill();
		hadEngineerSkill.setHadEngineerSkillId(3);
		hadEngineerSkill.setEngineerSkillId(3);
		hadEngineerSkill.setStatusId(1);
		hadEngineerSkill.setScore(40);
		return hadEngineerSkill;
	}
}
