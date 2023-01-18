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

import com.example.domain.HadBasicSkill;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.BasicSkillTestSql;
import common_sql.HadBasicSkillTestSql;
import common_sql.StatusTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
public class HadBasicSkillMapperTest {
	
	@Autowired
	private HadBasicSkillMapper hadBasicSkillMapper;
	        
	/** DBコネクション情報 */
	private static final  Destination DEST = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						BasicSkillTestSql.BASIC_SKILL_INSERT,
						HadBasicSkillTestSql.HAD_BASIC_SKILL_INSERT,
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
		HadBasicSkill hadBasicSkillActual1 = hadBasicSkillMapper.selectByPrimaryKey(1);
		HadBasicSkill hadBasicSkillActual2 = hadBasicSkillMapper.selectByPrimaryKey(2);
		HadBasicSkill hadBasicSkillActual3 = hadBasicSkillMapper.selectByPrimaryKey(3);
		
		assertEquals(hadBasicSkillActual1, hadBasicSkillTestData1());
		assertEquals(hadBasicSkillActual2, hadBasicSkillTestData2());
		assertEquals(hadBasicSkillActual3, hadBasicSkillTestData3());
		
	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	@Test
	public void データをインサートする() {
		Integer statusId = 1;
		List<HadBasicSkill> hadBasicSkillList = new ArrayList<>();
		HadBasicSkill hadBasicSkill = new HadBasicSkill();
		hadBasicSkill.setHadBasicSkillId(7);
		hadBasicSkill.setBasicSkillId(1);
		hadBasicSkill.setScore("3");
		hadBasicSkill.setStatusId(statusId);
		hadBasicSkillList.add(hadBasicSkill);
		int count = hadBasicSkillMapper.insertSelectiveList(hadBasicSkillList);
		assertEquals(count, hadBasicSkillList.size());
		//selectで確認
		HadBasicSkill hadBasicSkillInDb = hadBasicSkillMapper.selectByPrimaryKey(7);
		//基本スキルID,所有基本スキルIDで確認
		assertEquals(hadBasicSkill.getHadBasicSkillId(), hadBasicSkillInDb.getHadBasicSkillId());
		assertEquals(hadBasicSkill.getBasicSkillId(), hadBasicSkillInDb.getBasicSkillId());
	}

	@Test
	public void データをアップデートする() {
		Integer statusId = 1;
		List<HadBasicSkill> hadBasicSkillList = new ArrayList<>();
		HadBasicSkill hadBasicSkill = new HadBasicSkill();
		hadBasicSkill.setHadBasicSkillId(1);
		hadBasicSkill.setBasicSkillId(1);
		hadBasicSkill.setScore("1");
		hadBasicSkill.setStatusId(statusId);
		hadBasicSkillList.add(hadBasicSkill);
		int count = hadBasicSkillMapper.bulkUpdate(hadBasicSkillList, statusId);
		assertEquals(count, hadBasicSkillList.size());
		//selectで確認
		List<HadBasicSkill>hadBasicSkillListInDb = hadBasicSkillMapper.selectHadBasicSkillAndBasicSkillByStatusId(1);
		//基本スキルID,所有基本スキルID,スコアで確認
		assertEquals(hadBasicSkill.getHadBasicSkillId(), hadBasicSkillListInDb.get(0).getHadBasicSkillId());
		assertEquals(hadBasicSkill.getBasicSkillId(), hadBasicSkillListInDb.get(0).getBasicSkillId());
		assertEquals(hadBasicSkill.getScore(), hadBasicSkillListInDb.get(0).getScore());
	}

	@Test
	public void ステータスIDからの検索() {
		Integer statusId = 1;
		List<HadBasicSkill> hadBasicSkillListInDb = hadBasicSkillMapper.selectHadBasicSkillAndBasicSkillByStatusId(statusId);
		HadBasicSkill hadBasicSkill = hadBasicSkillTestData1();
		hadBasicSkill.setBasicSkill(BasicSkillMapperTest.basicSkillTestData1());
		assertEquals(hadBasicSkill, hadBasicSkillListInDb.get(0));
	}

	/**
	 * 所有基本スキルテストデータ1.
	 * 
	 * @return 所有基本スキルテストデータ
	 */
	public HadBasicSkill hadBasicSkillTestData1(){
		HadBasicSkill hadBasicSkill = new HadBasicSkill();
		hadBasicSkill.setHadBasicSkillId(1);
		hadBasicSkill.setBasicSkillId(1);
		hadBasicSkill.setScore("3");
		hadBasicSkill.setStatusId(1);
		return hadBasicSkill;
	}
	
	/**
	 * 所有基本スキルテストデータ2.
	 * 
	 * @return 所有基本スキルテストデータ
	 */
	public HadBasicSkill hadBasicSkillTestData2(){
		HadBasicSkill hadBasicSkill = new HadBasicSkill();
		hadBasicSkill.setHadBasicSkillId(2);
		hadBasicSkill.setBasicSkillId(2);
		hadBasicSkill.setScore("3");
		hadBasicSkill.setStatusId(1);
		return hadBasicSkill;
	}
	
	/**
	 * 所有基本スキルテストデータ3.
	 * 
	 * @return 所有基本スキルテストデータ
	 */
	public HadBasicSkill hadBasicSkillTestData3(){
		HadBasicSkill hadBasicSkill = new HadBasicSkill();
		hadBasicSkill.setHadBasicSkillId(3);
		hadBasicSkill.setBasicSkillId(3);
		hadBasicSkill.setScore("3");
		hadBasicSkill.setStatusId(1);
		return hadBasicSkill;
	}
}
