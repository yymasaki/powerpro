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

import com.example.domain.Personality;
import com.example.example.PersonalityExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.PersonalityTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
public class PersonalityMapperTest {

	@Autowired
	private PersonalityMapper mapper;
	
	/** DBコネクション情報 */
	private static final  Destination DEST = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Operation operation = 
				sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						PersonalityTestSql.PERSONALITY_INSERT);
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
		Personality actual1 = mapper.selectByPrimaryKey(1);
		Personality actual2 = mapper.selectByPrimaryKey(2);
		Personality actual3 = mapper.selectByPrimaryKey(3);
		assertEquals(actual1, personalityTestData1());
		assertEquals(actual2, personalityTestData2());
		assertEquals(actual3, personalityTestData3());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 性格の検索() {
		PersonalityExample ex = new PersonalityExample();
		ex.createCriteria().andStageEqualTo("0");
		List<Personality> personalityListInDb = mapper.selectByExample(ex);
		
		assertEquals(personalityListInDb.get(0), personalityTestData1());
		assertEquals(personalityListInDb.get(1), personalityTestData2());
		assertEquals(personalityListInDb.get(2), personalityTestData3());
	}
	
	/**
	 * 比較用性格データ1
	 * 
	 * @return 性格データ
	 */
	public Personality personalityTestData1() {
		Personality personality = new Personality();
		personality.setPersonalityId(1);
		personality.setName("短気");
		personality.setType("r");
		personality.setExplanation("エラーが解決できないとすぐイライラします");
		personality.setStage(0);
		personality.setCreator("テスト太郎");
		personality.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		personality.setUpdater("テスト太郎");
		personality.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		personality.setVersion(1);
		return personality;
	}
	
	/**
	 * 比較用性格データ2
	 * 
	 * @return 性格データ
	 */
	public Personality personalityTestData2() {
		Personality personality = new Personality();
		personality.setPersonalityId(2);
		personality.setName("遅刻魔");
		personality.setType("r");
		personality.setExplanation("時間を守るのが苦手で、よく遅刻します");
		personality.setStage(0);
		personality.setCreator("テスト太郎");
		personality.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		personality.setUpdater("テスト太郎");
		personality.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		personality.setVersion(1);
		return personality;
	}
	
	/**
	 * 比較用性格データ3
	 * 
	 * @return 性格データ
	 */
	public Personality personalityTestData3() {
		Personality personality = new Personality();
		personality.setPersonalityId(3);
		personality.setName("調べ上手");
		personality.setType("b");
		personality.setExplanation("適切なワードで検索し、ヒントを見つけるのが早いです");
		personality.setStage(0);
		personality.setCreator("テスト太郎");
		personality.setCreatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		personality.setUpdater("テスト太郎");
		personality.setUpdatedAt(LocalDateTime.of(2020, 5, 13, 12, 00));
		personality.setVersion(1);
		return personality;
	}
}
