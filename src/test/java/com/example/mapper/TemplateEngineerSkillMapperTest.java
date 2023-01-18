package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.domain.TemplateEngineerSkill;
import com.example.example.TemplateEngineerSkillExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.EngineerSkillTestSql;
import common_sql.TemplateEngineerSkillTestSql;
import common_sql.TemplateTestSql;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class	
})
public class TemplateEngineerSkillMapperTest {
	
	@Autowired
	private TemplateEngineerSkillMapper mapper;
	
	/** Operationオブジェクトを実行したい順番に繋げる */
	static Operation insert_ops = sequenceOf(EngineerSkillTestSql.ENGINEER_SKILL_INSERT,TemplateTestSql.TEMPLATES_INSERT,TemplateEngineerSkillTestSql.TEMPLATE_ENGINEER_SKILLS_INSERT);	
	
	// 接続に必要なDestinationオブジェクトを生成
	static Destination dest = new DriverManagerDestination("jdbc:mysql://localhost:3306/powerpro_exam",
            "root", "mysqlmysql");	
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		// 関連テーブルのデータを削除する
		DbSetup deleteDbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		// ダミーデータをインサートする
		DbSetup insertDbSetup = new DbSetup(dest, insert_ops);
		deleteDbSetup.launch();
		insertDbSetup.launch();
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		// 関連テーブルのデータを削除する
		DbSetup deleteDbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		deleteDbSetup.launch();
	}

	/** templateEngineerSkillIdからTemplateEngineerSkillを取得. */
	@Test
	public void templateEngineerSkillIdが検索条件のselectByExampleのテスト() {
		Integer templateEngineerSkillId = 1;
		TemplateEngineerSkillExample example = new TemplateEngineerSkillExample();
		example.or()
			.andTemplateEngineerSkillIdEqualTo(templateEngineerSkillId);
		TemplateEngineerSkill actual = mapper.selectByExample(example).get(0);
		assertEquals(actual.getTemplateEngineerSkillId(), templateEngineerSkillId);
		Integer expectedTemplateId = 1;
		assertEquals(actual.getTemplateId(), expectedTemplateId);
		Integer expectedSkillId = 1;
		assertEquals(actual.getEngineerSkillId(), expectedSkillId);
		Integer expectedScore = 50;
		assertEquals(actual.getScore(), expectedScore);
	}
	
	/** TemplateEngineerSkillをinsertする. */
	@Test
	public void insertのテスト() {
		Integer templateId = 3;
		Integer engineerSkillId = 7;
		Integer score = 90;
		TemplateEngineerSkill templateEngineerSkill = new TemplateEngineerSkill();
		templateEngineerSkill.setTemplateId(templateId);
		templateEngineerSkill.setEngineerSkillId(engineerSkillId);
		templateEngineerSkill.setScore(score);
		mapper.insert(templateEngineerSkill);
		
		Integer templateEngineerSkillId = 21;
		TemplateEngineerSkillExample example = new TemplateEngineerSkillExample();
		example.or().andTemplateEngineerSkillIdEqualTo(templateEngineerSkillId);
		TemplateEngineerSkill actual = mapper.selectByExample(example).get(0);
		assertEquals(actual.getTemplateEngineerSkillId(), templateEngineerSkillId);
		assertEquals(actual.getTemplateId(), templateId);
		assertEquals(actual.getEngineerSkillId(), engineerSkillId);
		assertEquals(actual.getScore(), score);
	}
	
	/** TemplateEngineerSkillを更新する. */
	@Test
	public void updateByExampleのテスト() {
		Integer templateEngineerSkillId = 1;
		TemplateEngineerSkillExample example = new TemplateEngineerSkillExample();
		example.or()
			.andTemplateEngineerSkillIdEqualTo(templateEngineerSkillId);
		TemplateEngineerSkill beforeUpdateTemplateEngineerSkill = 
				mapper.selectByExample(example).get(0);
		beforeUpdateTemplateEngineerSkill.setScore(100);
		mapper.updateByExample(beforeUpdateTemplateEngineerSkill, example);
		
		TemplateEngineerSkill actual = mapper.selectByExample(example).get(0);
		assertEquals(actual.getScore(), 100);
	}
}
