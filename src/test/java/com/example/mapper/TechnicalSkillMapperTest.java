package com.example.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
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

import com.example.common.Stage;
import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.AppSpecsheetTestSql;
import common_sql.DepartmentTestSql;
import common_sql.DevExperienceTestSql;
import common_sql.HadTechnicalSkillTestSql;
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
public class TechnicalSkillMapperTest {
	
	@Autowired
	private TechnicalSkillMapper mapper;
		
	@Autowired
	private static TechnicalSkillTestSql technicalSkillTestSql;	
		
	@Autowired
	private static UserTestSql userTestSql;
	
	@Autowired
	private static DepartmentTestSql departmentTestSql;
	
	@Autowired
	private static AllDeleteAndIncrementTestSql allDeleteAndIncrementTestSql;
 			
	/**DB接続情報*/
	static Destination dest = new DriverManagerDestination("jdbc:mysql://localhost:3306/powerpro_exam",
			"root", "mysqlmysql");

	@SuppressWarnings("static-access")
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		//DBデータを削除、自走採番をリセット、テストデータを挿入する
		DbSetup technicalSkill_dbSetup = new DbSetup(dest, 
				Operations.sequenceOf(
				allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
				departmentTestSql.DEPARTMENT_INSERT,
				userTestSql.USER_INSERT,
				technicalSkillTestSql.TECHNICAL_SKILL_INSERT,
				AppSpecsheetTestSql.APP_SPECSHEET_INSERT,
				DevExperienceTestSql.DEV_EXPERIENCE_INSERT,
				UsedTechnicalSkillTestSql.USED_TECHNICAL_SKILL_INSERT,
				HadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_INSERT
				));

		 technicalSkill_dbSetup.launch();
	}

	@SuppressWarnings("static-access")
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		// DBデータを削除、自動採番をリセットする
		DbSetup technical_skill_delete_dbSetup = new DbSetup(dest, allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		
		technical_skill_delete_dbSetup.launch();
	
	}

	@BeforeEach
	public void setUp() throws Exception {
		//テストデータの確認
		LocalDateTime createdAt = LocalDateTime.of(2020, 7, 2, 12, 00, 00);
		TechnicalSkill technicalSkill1=new TechnicalSkill(1, "testItem1", 1, 1, 2, "Webエンジニア次郎", createdAt, "Webエンジニア次郎",createdAt, 1);
		TechnicalSkill technicalSkill2=new TechnicalSkill(2, "testItem2", 2, 1, 2, "Webエンジニア次郎", createdAt, "Webエンジニア次郎",createdAt, 1);
		TechnicalSkill technicalSkill3=new TechnicalSkill(3, "testItem3", 3, 1, 2, "Webエンジニア次郎", createdAt, "Webエンジニア次郎",createdAt, 1);
		TechnicalSkill actual1=mapper.selectByPrimaryKey(1);
		TechnicalSkill actual2=mapper.selectByPrimaryKey(2);
		TechnicalSkill actual3=mapper.selectByPrimaryKey(3);
		assertEquals(actual1, technicalSkill1);
		assertEquals(actual2, technicalSkill2);
		assertEquals(actual3, technicalSkill3);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void テクニカルスキルをリストで挿入する() {
		String name = "hoge";
		String name2 = "hoge2";
		String userName = "ほげ子";
		LocalDateTime createdAt = LocalDateTime.of(2020, 7, 1, 12, 00, 00);

		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, 1, 7, 0, userName, createdAt, userName, createdAt, 1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill(null,name2, 2, 7, 0, userName, createdAt, userName, createdAt,
				1);
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(technicalSkill);
		technicalSkillList.add(technicalSkill2);

		int actual = mapper.insertTechnicalSkillList(technicalSkillList);
		assertEquals(actual, technicalSkillList.size());

		// select文でDBに挿入された情報の確認
		TechnicalSkillExample example = new TechnicalSkillExample();
		example.createCriteria().andNameEqualTo(name).andCategoryEqualTo(1);
		TechnicalSkill actualTechnicalSkill = mapper.selectByExample(example).get(0);

		TechnicalSkillExample example2 = new TechnicalSkillExample();
		example2.createCriteria().andNameEqualTo(name2).andCategoryEqualTo(2);
		TechnicalSkill actualTechnicalSkill2 = mapper.selectByExample(example2).get(0);

		assertEquals(actualTechnicalSkill, technicalSkill);
		assertEquals(actualTechnicalSkill2, technicalSkill2);

	}

	@Test
	public void 主キーからテクニカルスキルを取得する() {		
		Integer technicalSkillId = 1;
		String name="testItem1";

		TechnicalSkill technicalSkill = mapper.selectByPrimaryKey(technicalSkillId);
		assertEquals(technicalSkill.getName(), name);
	}

	@Test
	public void 主キーからテクニカルスキル_ユーザ_部署を取得する() {		
		Integer technicalSkillId = 1;
		Integer userId = 1;
		String departmentName = "Webエンジニア";

		TechnicalSkill actual = mapper.selectByTechnicalSkillId(technicalSkillId);
		assertEquals(actual.getTechnicalSkillId(), technicalSkillId);
		assertEquals(actual.getUser().getUserId(), userId);
		assertEquals(actual.getUser().getDepartment().getName(), departmentName);
	}

	@Test
	public void 指定条件からテクニカルスキルを取得する() {
		String name = "testItem1";
		Integer category = 1;
		TechnicalSkillExample example = new TechnicalSkillExample();
		example.createCriteria().andNameEqualTo(name).andCategoryEqualTo(category);
		List<TechnicalSkill> actualList = mapper.selectByExample(example);
		assertEquals(actualList.get(0).getName(), name);
		assertEquals(actualList.get(0).getCategory(), category);
	}

	@Test
	public void テクニカルスキルIDリストとバージョンリストからテクニカルスキルリストを取得する() {
		Integer technicalSkillId = 1;
		Integer technicalSkillId2 = 2;
		Integer version = 1;
		Integer version2 = 1;
		List<Integer> technicalSkillIdList = new ArrayList<>();
		List<Integer> versionList = new ArrayList<>();
		technicalSkillIdList.add(technicalSkillId);
		technicalSkillIdList.add(technicalSkillId2);
		versionList.add(version);
		versionList.add(version2);
		List<TechnicalSkill> actualList = mapper.selectByTechinicalSkillIdListAndVersionList(technicalSkillIdList,
				versionList);
		assertEquals(actualList.get(0).getTechnicalSkillId(), technicalSkillId);
		assertEquals(actualList.get(1).getTechnicalSkillId(), technicalSkillId2);
		assertEquals(actualList.get(0).getVersion(), version);
		assertEquals(actualList.get(1).getVersion(), version2);

	}

	@Test
	public void テクニカルスキルを更新する() {

		Integer technicalSkillId = 1;
		String name = "testItem1";
		LocalDateTime createdAt = LocalDateTime.of(2020, 7, 2, 12, 00, 00);
		LocalDateTime updatedAt = LocalDateTime.of(2020, 7, 3, 12, 00, 00);

		TechnicalSkill technicalSkill = new TechnicalSkill(null,name, 1, 1, 0, "Webエンジニア次郎", createdAt, "テスト管理者", updatedAt,2);
		technicalSkill.setTechnicalSkillId(technicalSkillId);

		int actual = mapper.updateByPrimaryKeySelective(technicalSkill);
		assertEquals(actual, 1);

		// select文で更新が反映されたか確認
		TechnicalSkill actualTechnicalSkill = mapper.selectByPrimaryKey(technicalSkillId);
		assertEquals(actualTechnicalSkill, technicalSkill);

	}

	@Test
	public void テクニカルスキルのステージを更新する() {
		Integer technicalSkillId = 1;
		Integer technicalSkillId2 = 2;

		String name = "testItem1";
		String name2 = "testItem2";
		String creator = "Webエンジニア次郎";
		String updater = "テスト管理者";
		LocalDateTime createdAt = LocalDateTime.of(2020, 7, 2, 12, 00, 00);
		LocalDateTime updatedAt = LocalDateTime.of(2020, 7, 3, 12, 00, 00);

		TechnicalSkill technicalSkill = new TechnicalSkill(technicalSkillId,name, 5, 1, 2, creator, createdAt, creator, createdAt, 1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill(technicalSkillId2,name2, 5,1, 2, creator, createdAt, creator, createdAt, 1);

		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(technicalSkill);
		technicalSkillList.add(technicalSkill2);
		int actual = mapper.updateStageByPrimaryKeyAsList(technicalSkillList, Stage.ACTIVE.getKey().toString(), updater,
				updatedAt);
		assertEquals(actual, 2);

		// select文で更新が反映されたか確認
		TechnicalSkill actualTechnicalSkill = mapper.selectByPrimaryKey(technicalSkillId);
		TechnicalSkill actualTechnicalSkill2 = mapper.selectByPrimaryKey(technicalSkillId2);

		assertEquals(actualTechnicalSkill.getStage(), Stage.ACTIVE.getKey());
		assertEquals(actualTechnicalSkill.getUpdater(), updater);
		assertEquals(actualTechnicalSkill.getUpdatedAt(), updatedAt);

		assertEquals(actualTechnicalSkill2.getStage(), Stage.ACTIVE.getKey());
		assertEquals(actualTechnicalSkill2.getUpdater(), updater);
		assertEquals(actualTechnicalSkill2.getUpdatedAt(), updatedAt);

	}
	
	@Test
	public void selectByStageAndApplicantのテスト() {
		Integer stage = 2;
		String applicant = "Web";
		Integer startNumber = 0;
		List<TechnicalSkill> technicalSkillList = mapper.selectByStageAndApplicant(stage, applicant, startNumber);
		assertEquals(technicalSkillList.size(), 5);
		assertEquals(technicalSkillList.get(0).getName(), "testItem1");
		assertEquals(technicalSkillList.get(1).getName(), "testItem2");
		assertEquals(technicalSkillList.get(2).getName(), "testItem3");
		assertEquals(technicalSkillList.get(3).getName(), "testItem4");
		assertEquals(technicalSkillList.get(4).getName(), "testItem5");
	}

	@Test
	public void countByExampleのテスト() {
		TechnicalSkillExample example = new TechnicalSkillExample();
		example.or()
			.andStageEqualTo("2")
			.andCreatorLike("%Web%");
		int count = mapper.countByExample(example);
		assertEquals(count, 5);
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void リクエストユーザーとステージを2件更新() {
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setTechnicalSkillId(2);
		technicalSkillList.add(technicalSkill2);
		int result = mapper.updateRequestUserIdAndStageByPrimaryKeyAsList(
				technicalSkillList, 2, "2", "tester", LocalDateTime.of(2020, 7, 7, 11, 11, 11));
		assertEquals(result, 2);
		TechnicalSkill result1 = mapper.selectByPrimaryKey(1);
		Integer expectedUserId = 2;
		assertEquals(result1.getRequestUserId(), expectedUserId);
		Integer expectedStage = 2;
		assertEquals(result1.getStage(), expectedStage);
		TechnicalSkill result2 = mapper.selectByPrimaryKey(2);
		assertEquals(result2.getRequestUserId(), expectedUserId);
		assertEquals(result2.getStage(), expectedStage);
	}
	
	/**
	 * @author katsuya.fujishima
	 */
	@Test
	public void スペックシートIDとユーザーIDとステージから取得() {
		List<Integer> specStageList = new ArrayList<>();
		specStageList.add(Stage.REQUESTING.getKey());
		
		List<TechnicalSkill> result = mapper.selectBySpecsheetIdAndUserIdAndStage(1, 1, specStageList);
		
		assertEquals(result.size(), 3);
		assertEquals(result.get(0).getName(), "testItem1");
		assertEquals(result.get(1).getName(), "testItem2");
		assertEquals(result.get(2).getName(), "testItem3");
	}

}
