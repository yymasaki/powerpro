package com.example.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
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
import com.example.domain.HadTechnicalSkill;
import com.example.example.HadTechnicalSkillExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.DepartmentTestSql;
import common_sql.HadTechnicalSkillTestSql;
import common_sql.TechnicalSkillTestSql;
import common_sql.UserTestSql;



@RunWith(SpringRunner.class)
@MybatisTest
@TestExecutionListeners({
DependencyInjectionTestExecutionListener.class,
DbUnitTestExecutionListener.class,
TransactionalTestExecutionListener.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HadTechnicalSkillMapperTest {

	@Autowired
	private HadTechnicalSkillMapper mapper;
	
	@Autowired
	private static HadTechnicalSkillTestSql hadTechnicalSkillTestSql;
	
	@Autowired
	private static TechnicalSkillTestSql technicalSkillTestSql;
	
	@Autowired
	private static UserTestSql userTestSql;
	
	@Autowired
	protected static DepartmentTestSql departmentTestSql;
	
	@Autowired
	private static AllDeleteAndIncrementTestSql allDeleteAndIncrementTestSql;
	
	public static final  Destination dest = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
			"root", "mysqlmysql");
	
	@BeforeAll
	@SuppressWarnings("static-access")
	public static void setUpBeforeClass() throws Exception {
			DbSetup had_technical_skill_dbSetup = new DbSetup(dest, 
					Operations.sequenceOf(
							allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
							departmentTestSql.DEPARTMENT_INSERT,
							userTestSql.USER_INSERT,
							technicalSkillTestSql.TECHNICAL_SKILL_INSERT,
							hadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_INSERT
					));
			had_technical_skill_dbSetup.launch();
	}

	@AfterAll
	@SuppressWarnings("static-access")
	public static void tearDownAfterClass() throws Exception {
		DbSetup delete_auto_increment_dbSetup = new DbSetup(dest, allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		delete_auto_increment_dbSetup.launch();
	}

	@BeforeEach
	public void setUp() throws Exception {
		assertEquals(mapper.selectByPrimaryKey(1).getHadTechnicalSkillId(), 1);
		assertEquals(mapper.selectByPrimaryKey(2).getHadTechnicalSkillId(), 2);
		assertEquals(mapper.selectByPrimaryKey(3).getHadTechnicalSkillId(), 3);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * @author namikitsubasa
	 */
	@Test
	public void 所有テクニカルスキルを取得する() {
		Integer userId=1;
		List<HadTechnicalSkill> hadTechnicalSkill=mapper.selectByUserIdAndStage0or1or2(userId);
		System.out.println(hadTechnicalSkill);
		assertEquals(hadTechnicalSkill.get(0).getUserId(), 1);
		assertEquals(hadTechnicalSkill.get(0).getTechnicalSkill().getTechnicalSkillId(), 1);
		assertEquals(hadTechnicalSkill.get(0).getUsingPeriod(), 5);
		assertEquals(hadTechnicalSkill.get(0).getStage(), 0);
		assertEquals(hadTechnicalSkill.get(1).getUserId(), 1);
		assertEquals(hadTechnicalSkill.get(1).getTechnicalSkill().getTechnicalSkillId(), 2);
		assertEquals(hadTechnicalSkill.get(1).getUsingPeriod(), 5);
		assertEquals(hadTechnicalSkill.get(1).getStage(), 0);
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void ユーザーIDから所有テクニカルスキルを取得する() {
		List<HadTechnicalSkill> hadTechnicalSkill=mapper.selectByUserId(1);
		assertEquals(hadTechnicalSkill.get(0).getUserId(), 1);
		assertEquals(hadTechnicalSkill.get(0).getTechnicalSkill().getTechnicalSkillId(), 1);
		assertEquals(hadTechnicalSkill.get(0).getUsingPeriod(), 5);
		assertEquals(hadTechnicalSkill.get(0).getStage(), 0);
		assertEquals(hadTechnicalSkill.get(1).getUserId(), 1);
		assertEquals(hadTechnicalSkill.get(1).getTechnicalSkill().getTechnicalSkillId(), 2);
		assertEquals(hadTechnicalSkill.get(1).getUsingPeriod(), 5);
		assertEquals(hadTechnicalSkill.get(1).getStage(), 0);
	}
	
	/**
	 * @author namikitsubasa
	 */
	@Test
	public void 所有テクニカルスキルを更新する() {
	int userId = 1;
	LocalDateTime currentDateTime = LocalDateTime.now();
	List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
	HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
	HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
	hadTechnicalSkill1.setHadTechnicalSkillId(2);
	hadTechnicalSkill1.setUsingPeriod(5);
	hadTechnicalSkill1.setUpdatedAt(currentDateTime);
	hadTechnicalSkill1.setUpdater("Webエンジニア次郎");
	hadTechnicalSkillList.add(hadTechnicalSkill1);
	hadTechnicalSkill2.setHadTechnicalSkillId(3);
	hadTechnicalSkill2.setUsingPeriod(8);
	hadTechnicalSkill2.setUpdatedAt(currentDateTime);
	hadTechnicalSkill2.setUpdater("Webエンジニア次郎");
	hadTechnicalSkillList.add(hadTechnicalSkill2);
	int numOfUpdate = mapper.updateUsingPeriodByHadTechnicalSkillList(hadTechnicalSkillList, userId);
	assertEquals(numOfUpdate, 2);
	HadTechnicalSkill hadTechnicalSkillOfId2 =mapper.selectByPrimaryKey(2);
	HadTechnicalSkill hadTechnicalSkillOfId3 =mapper.selectByPrimaryKey(3);
	assertEquals(hadTechnicalSkillOfId2.getUsingPeriod(), 5);
	assertEquals(hadTechnicalSkillOfId2.getUpdater(), "Webエンジニア次郎");
	assertEquals(hadTechnicalSkillOfId3.getUsingPeriod(), 8);
	assertEquals(hadTechnicalSkillOfId3.getUpdater(), "Webエンジニア次郎");
	}

	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void データを2件挿入() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setUserId(1);
		hadTechnicalSkill1.setTechnicalSkillId(1);
		hadTechnicalSkill1.setUsingPeriod(12);
		hadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkill1.setCreator("tester");
		hadTechnicalSkill1.setCreatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		hadTechnicalSkill1.setUpdater("tester");
		hadTechnicalSkill1.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setUserId(1);
		hadTechnicalSkill2.setTechnicalSkillId(2);
		hadTechnicalSkill2.setUsingPeriod(24);
		hadTechnicalSkill2.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkill2.setCreator("tester");
		hadTechnicalSkill2.setCreatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		hadTechnicalSkill2.setUpdater("tester");
		hadTechnicalSkill2.setUpdatedAt(LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		int result = mapper.insertList(hadTechnicalSkillList);
		assertEquals(result, 2, "期待値と実際の結果が異なります");
		List<HadTechnicalSkill> resultList = mapper.selectByUserIdAndStage0or1or2(1);
		List<HadTechnicalSkill> resultList1 = resultList.stream()
				.filter(ht -> ht.getUserId().equals(hadTechnicalSkill1.getUserId()))
				.filter(ht -> ht.getTechnicalSkillId().equals(hadTechnicalSkill1.getTechnicalSkillId()))
				.filter(ht -> ht.getCreator().equals(hadTechnicalSkill1.getCreator()))
				.filter(ht -> ht.getCreatedAt().equals(hadTechnicalSkill1.getCreatedAt()))
				.collect(Collectors.toList());
		assertEquals(resultList1.get(0).getUsingPeriod(), 12, "期待値と実際の結果が異なります");
		List<HadTechnicalSkill> resultList2 = resultList.stream()
				.filter(ht -> ht.getUserId().equals(hadTechnicalSkill2.getUserId()))
				.filter(ht -> ht.getTechnicalSkillId().equals(hadTechnicalSkill2.getTechnicalSkillId()))
				.filter(ht -> ht.getCreator().equals(hadTechnicalSkill2.getCreator()))
				.filter(ht -> ht.getCreatedAt().equals(hadTechnicalSkill2.getCreatedAt()))
				.collect(Collectors.toList());
		assertEquals(resultList2.get(0).getUsingPeriod(), 24, "期待値と実際の結果が異なります");
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void データを2件削除() {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setHadTechnicalSkillId(1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setHadTechnicalSkillId(2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		int result = mapper.deleteListByPrimaryKey(hadTechnicalSkillList);
		assertEquals(result, 2, "期待値と実際の結果が異なります");
		HadTechnicalSkill result1 = mapper.selectByPrimaryKey(1);
		assertEquals(result1, null, "期待値と実際の結果が異なります");
		HadTechnicalSkill result2 = mapper.selectByPrimaryKey(2);
		assertEquals(result2, null, "期待値と実際の結果が異なります");
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void ステージを更新() {
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkill.setStage(Stage.REQUESTING.getKey());
		hadTechnicalSkill.setUpdater("tester");
		hadTechnicalSkill.setUpdatedAt(LocalDateTime.of(2020, 8, 8, 8, 8, 8));
		HadTechnicalSkillExample example = new HadTechnicalSkillExample();
		example.createCriteria().andStageEqualTo(Stage.ACTIVE.getKey().toString());
		int result = mapper.updateStageByExample(hadTechnicalSkill, example);
		assertEquals(result, 3);
		HadTechnicalSkill ht1 = mapper.selectByPrimaryKey(1);
		assertEquals(ht1.getStage(), Stage.REQUESTING.getKey());
		HadTechnicalSkill ht2 = mapper.selectByPrimaryKey(2);
		assertEquals(ht2.getStage(), Stage.REQUESTING.getKey());
		HadTechnicalSkill ht3 = mapper.selectByPrimaryKey(3);
		assertEquals(ht3.getStage(), Stage.REQUESTING.getKey());
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void 条件からデータを削除() {
		HadTechnicalSkillExample example = new HadTechnicalSkillExample();
		example.createCriteria().andStageEqualTo(Stage.ACTIVE.getKey().toString());
		int result = mapper.deleteByExample(example);
		assertEquals(result, 3);
	}

}
