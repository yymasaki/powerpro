package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.common.Stage;
import com.example.domain.Status;
import com.example.example.StatusExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.BasicSkillTestSql;
import common_sql.DepartmentTestSql;
import common_sql.EngineerSkillTestSql;
import common_sql.HadBasicSkillTestSql;
import common_sql.HadEngineerSkillTestSql;
import common_sql.HadPersonalityTestSql;
import common_sql.PersonalityTestSql;
import common_sql.StatusTestSql;
import common_sql.UserTestSql;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
public class StatusMapperTest extends TestCase {
	
	@Autowired
	private StatusMapper mapper;
	
	/** DBコネクション情報 */
	private static final  Destination DEST = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						DepartmentTestSql.DEPARTMENT_INSERT,
						UserTestSql.USER_INSERT,
						StatusTestSql.STATUS_INSERT,
						BasicSkillTestSql.BASIC_SKILL_INSERT,
						EngineerSkillTestSql.ENGINEER_SKILL_INSERT,
						PersonalityTestSql.PERSONALITY_INSERT,
						HadBasicSkillTestSql.HAD_BASIC_SKILL_INSERT,
						HadEngineerSkillTestSql.HAD_ENGINEER_SKILLS_INSERT,
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
		Status statusActual1 = mapper.selectByPrimaryKey(1);
		Status statusActual2 = mapper.selectByPrimaryKey(2);
		Status statusActual3 = mapper.selectByPrimaryKey(3);
		
		assertEquals(statusActual1, statusTestData1());
		assertEquals(statusActual2, statusTestData2());
		assertEquals(statusActual3, statusTestData3());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/** selectByStageAndApplicantのテスト. */
	@Test
	public void 申請者名にテストを含むstageが2のstatusを取得() {
		Integer stage = 2;
		String applicant = "テスト";
		Integer startNumber = 0;
		Status actual = mapper.selectByStageAndApplicant(stage, applicant, startNumber).get(0);
		Integer expectedStatusId = 3;
		assertEquals(actual.getStatusId(), expectedStatusId);
		assertEquals(actual.getUsingPc(), "Linux");
		assertEquals(actual.getUsingMobile(), "iPhone");
		assertEquals(actual.getCreator(), "テスト三郎");
	}
	
	/** selectByStageAndUserIdのテスト. */
	@Test
	public void UserIdが4でstageが3のstatusを取得() {
		Integer userId = 4;
		Integer stage = 3;
		Integer startNumber = 0;
		Status actual = mapper.selectByStageAndUserId(stage, userId, startNumber).get(0);
		Integer expectedStatusId = 4;
		assertEquals(actual.getStatusId(), expectedStatusId);
		assertEquals(actual.getUsingPc(), "mac");
		assertEquals(actual.getUsingMobile(), "iPhone");
		assertEquals(actual.getCreator(), "テスト四郎");
	}
	
	/**　countByExampleのテスト. */
	@Test
	public void countByExampleのテスト() {
		StatusExample example = new StatusExample();
		example.or()
			.andStageEqualTo("2")
			.andCreatorLike("%テスト%");
		int count = mapper.countByExample(example);
		assertEquals(count, 1);
	}

	
	/**
	 * @author nonaka
	 */
	@Test
	public void ステータスIDが1のステータス取得() {
		Integer statusId = 1;
		Status statusInDb = mapper.selectByPrimaryKey(statusId);
		Integer userId = 1;
		assertEquals(statusInDb.getUserId(), userId);
		assertEquals(statusInDb.getUpdater(), "テスト太郎");
		assertEquals(statusInDb.getCreator(), "テスト太郎");
		assertEquals(statusInDb.getUsingMobile(), "iPhone");
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void ステータスIDが1のステータスとそれに関するスキルの取得() {
		Integer statusId = 1;
		Status statusInDb = mapper.selectStatusAndSkillsByPrimaryKey(statusId);
		//ステータスの確認
		assertEquals(statusInDb.getUsingMobile(), "iPhone");
		//所有基本スキルの確認
		assertEquals(statusInDb.getHadBasicSkillList().get(0).getScore(), "3");
		//所有エンジニアスキルの確認
		Integer expectedScore = 90;
		assertEquals(statusInDb.getHadEngineerSkillList().get(0).getScore(), expectedScore);
		//所有性格の確認
		Integer expectedHadPersonalityId = 1;
		assertEquals(statusInDb.getHadPersonalityList().get(0).getHadPersonalityId(), expectedHadPersonalityId);
		//基本スキルの確認
		assertEquals(statusInDb.getHadBasicSkillList().get(0).getBasicSkill().getName(), "サンプル1");
		//エンジニアスキルの確認
		assertEquals(statusInDb.getHadEngineerSkillList().get(0).getEngineerSkill().getName(), "プログラム");
		//性格の確認
		assertEquals(statusInDb.getHadPersonalityList().get(0).getPersonality().getName(), "短気");
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void ユーザーIDが1でステージが0の時のステータスと関するスキルの取得() {
		Integer userId = 1;
		List<Status> statusInDb = mapper.selectStatusAndSkillsByUserIdAndStages(userId, Stage.ACTIVE.getKey(), null);
		//ステータスの確認
		assertEquals(statusInDb.get(0).getUsingMobile(), "iPhone");
		//所有基本スキルの確認
		assertEquals(statusInDb.get(0).getHadBasicSkillList().get(0).getScore(), "3");
		//所有エンジニアスキルの確認
		Integer expectedOfEngineerSkill = 90;
		assertEquals(statusInDb.get(0).getHadEngineerSkillList().get(0).getScore(), expectedOfEngineerSkill);
		//所有性格の確認
		Integer expectedOfPersonalityId = 1;
		assertEquals(statusInDb.get(0).getHadPersonalityList().get(0).getHadPersonalityId(), expectedOfPersonalityId);
		//基本スキルの確認
		assertEquals(statusInDb.get(0).getHadBasicSkillList().get(0).getBasicSkill().getName(), "サンプル1");
		//エンジニアスキルの確認
		assertEquals(statusInDb.get(0).getHadEngineerSkillList().get(0).getEngineerSkill().getName(), "プログラム");
		//性格の確認
		assertEquals(statusInDb.get(0).getHadPersonalityList().get(0).getPersonality().getName(), "短気");
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void ユーザーIDが1でステージが0の時のステータスと性格以外のスキルの取得() {
		Integer userId = 1;
		List<Status> statusInDb = mapper.selectStatusAndSkillsWithoutPersonalitiesByUserIdAndStages(userId, Stage.ACTIVE.getKey(), null);
		//ステータスの確認
		assertEquals(statusInDb.get(0).getUsingMobile(), "iPhone");
		//所有基本スキルの確認
		assertEquals(statusInDb.get(0).getHadBasicSkillList().get(0).getScore(), "3");
		//所有エンジニアスキルの確認
		Integer expectedScoreOfEngineerSkill = 90;
		assertEquals(statusInDb.get(0).getHadEngineerSkillList().get(0).getScore(), expectedScoreOfEngineerSkill);
		//基本スキルの確認
		assertEquals(statusInDb.get(0).getHadBasicSkillList().get(0).getBasicSkill().getName(), "サンプル1");
		//エンジニアスキルの確認
		assertEquals(statusInDb.get(0).getHadEngineerSkillList().get(0).getEngineerSkill().getName(), "プログラム");
		//性格の確認
		assertTrue(statusInDb.get(0).getHadPersonalityList().isEmpty());
	}
	
	/**
	 * @author nonaka 
	 */
	@Test
	public void ステージ0かつユーザーID1のステータスリスト取得() {
		Integer userId = 1;
		StatusExample ex = new StatusExample();
		ex.createCriteria().andStageEqualTo("0").andUserIdEqualTo(userId);
		List<Status> statusInDb = mapper.selectByExample(ex);
		assertEquals(statusInDb.get(0).getUserId(), userId);
		assertEquals(statusInDb.get(0).getUpdater(), "テスト太郎");
		assertEquals(statusInDb.get(0).getCreator(), "テスト太郎");
		assertEquals(statusInDb.get(0).getUsingMobile(), "iPhone");
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void ステータスのアップデートを行う() {
		Status status = new Status();
		status.setStatusId(1);
		status.setStage(3);
		status.setVersion(9999);
		mapper.updateByPrimaryKeySelective(status);
		//アップデートされたかの確認
		Status statusInDb = mapper.selectByPrimaryKey(1);
		assertEquals(status.getVersion(), statusInDb.getVersion());
		assertEquals(status.getStage(), statusInDb.getStage());
	}
	
	/**
	 * @author nonaka 
	 */
	@Test
	public void ステータスIDからステータスの削除を行う() {
		Integer statusId = 1;
		mapper.deleteByPrimaryKey(statusId);
		//デリートされたかの確認
		assertNull(mapper.selectByPrimaryKey(statusId)); 
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void ユーザーIDとステージ条件からステータスの削除を行う() {
		Integer userId = 1;
		StatusExample ex = new StatusExample();
		ex.createCriteria().andStageEqualTo("0").andUserIdEqualTo(userId);
		mapper.deleteByExample(ex);
		assertNull(mapper.selectByPrimaryKey(1));
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void ステータスの登録を行う() {
		Integer statusId = 5;
		Status status = new Status();
		status.setStatusId(statusId);
		status.setUserId(1);
		status.setUsingPc("windows");
		status.setUsingMobile("iPhone");
		status.setCreator("テスト太郎");
		status.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setUpdater("テスト太郎");
		status.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setStage(0);
		status.setVersion(1);
		int count = mapper.insertSelective(status);
		assertEquals(count, 1);
		//インサートされたかの確認
		Status statusInDb = mapper.selectByPrimaryKey(statusId);
		assertEquals(status, statusInDb);
	}
	
	/**
	 * ステータステストデータ1
	 * 
	 * @return ステータスデータ
	 */
	public Status statusTestData1() {
		Status status = new Status();
		status.setStatusId(1);
		status.setUserId(1);
		status.setUsingPc("mac");
		status.setUsingMobile("iPhone");
		status.setCreator("テスト太郎");
		status.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setUpdater("テスト太郎");
		status.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setStage(0);
		status.setVersion(1);
		return status;
	}
	
	/**
	 * ステータステストデータ2
	 * 
	 * @return ステータスデータ
	 */
	public Status statusTestData2() {
		Status status = new Status();
		status.setStatusId(2);
		status.setUserId(2);
		status.setUsingPc("Windows");
		status.setUsingMobile("Android");
		status.setCreator("テスト次郎");
		status.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setUpdater("テスト次郎");
		status.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setStage(1);
		status.setVersion(1);
		return status;
	}
	/**
	 * ステータステストデータ3
	 * 
	 * @return ステータスデータ
	 */
	public Status statusTestData3() {
		Status status = new Status();
		status.setStatusId(3);
		status.setUserId(3);
		status.setUsingPc("Linux");
		status.setUsingMobile("iPhone");
		status.setCreator("テスト三郎");
		status.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setUpdater("テスト三郎");
		status.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		status.setStage(2);
		status.setVersion(1);
		return status;
	}
	
}
