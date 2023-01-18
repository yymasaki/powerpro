package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.domain.HadPersonality;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.DepartmentTestSql;
import common_sql.HadPersonalityTestSql;
import common_sql.PersonalityTestSql;
import common_sql.StatusTestSql;
import common_sql.UserTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class, 
	  DbUnitTestExecutionListener.class
	})
public class HadPersonalityMapperTest {
	
	@Autowired
	private HadPersonalityMapper hadPersonalityMapper;
	
	/** DBコネクション情報 */
	private static final  Destination DEST = new DriverManagerDestination
	("jdbc:mysql://localhost:3306/powerpro_exam", "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						DepartmentTestSql.DEPARTMENT_INSERT,
						UserTestSql.USER_INSERT,
						StatusTestSql.STATUS_INSERT,
						PersonalityTestSql.PERSONALITY_INSERT,
						HadPersonalityTestSql.HAD_PERSONALITIES_INSERT);
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
		HadPersonality hadPersonalityActual1 = hadPersonalityMapper.selectByPrimaryKey(1);
		HadPersonality hadPersonalityActual2 = hadPersonalityMapper.selectByPrimaryKey(2);
		HadPersonality hadPersonalityActual3 = hadPersonalityMapper.selectByPrimaryKey(3);
		
//		assertEquals(hadPersonalityActual1, is(hadPersonalityTestData1()));
		assertEquals(hadPersonalityActual1, hadPersonalityTestData1());
		assertEquals(hadPersonalityActual2, hadPersonalityTestData2());
		assertEquals(hadPersonalityActual3, hadPersonalityTestData3());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	@Test
	public void 所有性格データインサートを行う() {
		List<HadPersonality> hadPersonalityList = new ArrayList<>();
		Integer statusId = 1;
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(5);
		hadPersonality.setPersonalityId(1);
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(statusId);
		hadPersonalityList.add(hadPersonality);			
		int count = hadPersonalityMapper.insertSelectiveList(hadPersonalityList);
		assertEquals(count, hadPersonalityList.size());
		//selectで確認
		HadPersonality hadPersonalityInDb = hadPersonalityMapper.selectByPrimaryKey(5);
		System.out.println(hadPersonalityInDb);
		//性格ID,所有性格IDで確認
		assertEquals(hadPersonalityList.get(0).getPersonalityId(), hadPersonalityInDb.getPersonalityId());
		assertEquals(hadPersonalityList.get(0).getHadPersonalityId(), hadPersonalityInDb.getHadPersonalityId());
	}
	
	@Test
	public void ステータスIDから検索を行う() {
		Integer statusId = 1;
		List<HadPersonality> hadPersonalityListInDb = hadPersonalityMapper.selectByStatusId(statusId);
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(1);
		hadPersonality.setPersonalityId(1);
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(statusId);
		assertEquals(hadPersonality, hadPersonalityListInDb.get(0));
	}
	
	@Test
	public void ステータスIDから所有性格スキルと性格スキルの検索を行う() {
		Integer statusId = 1;
		List<HadPersonality> hadPersonalityListInDb = hadPersonalityMapper.selectHadPersonalityAndPersonality(statusId);
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(1);
		hadPersonality.setPersonalityId(1);
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(statusId);
		assertEquals(hadPersonality.getHadPersonalityId(), hadPersonalityListInDb.get(0).getHadPersonalityId());
		assertEquals(hadPersonality.getPersonalityId(), hadPersonalityListInDb.get(0).getPersonalityId());
	}
	
	/**
	 * 所有性格テストデータ1.
	 * 
	 * @return 所有性格データ
	 */
	public HadPersonality hadPersonalityTestData1(){
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(1);
		hadPersonality.setPersonalityId(1);
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(1);
		return hadPersonality;
	}
	
	/**
	 * 所有性格テストデータ2.
	 * 
	 * @return 所有性格データ
	 */
	public HadPersonality hadPersonalityTestData2(){
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(2);
		hadPersonality.setPersonalityId(3);
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(1);
		return hadPersonality;
	}
	
	/**
	 * 所有性格テストデータ3.
	 * 
	 * @return 所有性格データ
	 */
	public HadPersonality hadPersonalityTestData3(){
		HadPersonality hadPersonality = new HadPersonality();
		hadPersonality.setHadPersonalityId(3);
		hadPersonality.setPersonalityId(5);
		hadPersonality.setStage(0);
		hadPersonality.setStatusId(1);
		return hadPersonality;
	}
}
