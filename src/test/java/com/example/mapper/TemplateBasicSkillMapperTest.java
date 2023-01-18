package com.example.mapper;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import java.util.List;

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

import com.example.domain.TemplateBasicSkill;
import com.example.example.TemplateBasicSkillExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.BasicSkillTestSql;
import common_sql.TemplateBasicSkillTestSql;
import common_sql.TemplateTestSql;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class	
})
public class TemplateBasicSkillMapperTest extends TestCase {
	
	@Autowired
	private TemplateBasicSkillMapper mapper;
	
	/** Operationオブジェクトを実行したい順番に繋げる */
	static Operation insert_ops = sequenceOf(BasicSkillTestSql.BASIC_SKILL_INSERT,TemplateTestSql.TEMPLATES_INSERT,TemplateBasicSkillTestSql.TEMPLATE_BASIC_SKILLS_INSERT);
	
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


	/** テンプレートidからTemplateBasicSkillを取得. */
	@Test
	public void templateIdが検索条件のselectByExampleのテスト() {
		Integer templateId = 1;
		TemplateBasicSkillExample example = new TemplateBasicSkillExample();
		example.or().andTemplateIdEqualTo(templateId);
		example.setOrderByClause("template_basic_skill_id");
		List<TemplateBasicSkill> actual = mapper.selectByExample(example);
		assertEquals(actual.size(), 6);
		Integer expected0 = 1;
		assertEquals(actual.get(0).getScore(), expected0);
		Integer expected1 = 2;
		assertEquals(actual.get(1).getScore(), expected1);
		Integer expected2 = 3;
		assertEquals(actual.get(2).getScore(), expected2);
		Integer expected3 = 3;
		assertEquals(actual.get(3).getScore(), expected3);
		Integer expected4 = 4;
		assertEquals(actual.get(4).getScore(), expected4);
		Integer expected5 = 5;
		assertEquals(actual.get(5).getScore(), expected5);
	}
	
	/** templateBasicSkillIdからTemplateBasicSkillを取得. */
	@Test
	public void templateBasicSkillIdが検索条件のselectByExampleのテスト() {
		Integer templateBasicSkillId = 1;
		TemplateBasicSkillExample example = new TemplateBasicSkillExample();
		example.or()
			.andTemplateBasicSkillIdEqualTo(templateBasicSkillId);
		TemplateBasicSkill actual = mapper.selectByExample(example).get(0);
		assertEquals(actual.getTemplateBasicSkillId(), templateBasicSkillId);
		Integer expectedTemplateId = 1;
		assertEquals(actual.getTemplateId(), expectedTemplateId);
		Integer expectedBasicSkillId = 1;
		assertEquals(actual.getBasicSkillId(), expectedBasicSkillId);
		Integer expectedScore = 1;
		assertEquals(actual.getScore(), expectedScore);
	}
	
	/** TemplateBasicSkillをinsertする. */
	@Test
	public void insertのテスト() {
		Integer templateId = 3;
		Integer basicSkillId = 6;
		Integer score = 5;
		TemplateBasicSkill templateBasicSkill = new TemplateBasicSkill();
		templateBasicSkill.setTemplateId(templateId);
		templateBasicSkill.setBasicSkillId(basicSkillId);
		templateBasicSkill.setScore(score);
		mapper.insert(templateBasicSkill);
		
		Integer templateBasicSkillId = 18;
		TemplateBasicSkillExample example = new TemplateBasicSkillExample();
		example.or().andTemplateBasicSkillIdEqualTo(templateBasicSkillId);
		TemplateBasicSkill actual = mapper.selectByExample(example).get(0);
		assertEquals(actual.getTemplateBasicSkillId(), templateBasicSkillId);
		Integer expectedTemplateId = 3;
		assertEquals(actual.getTemplateId(), expectedTemplateId);
		Integer expectedSkillId = 6;
		assertEquals(actual.getBasicSkillId(), expectedSkillId);
		Integer expectedScore = 5;
		assertEquals(actual.getScore(), expectedScore);
	}
	
	/** TemplateBasicSkillを更新する. */
	@Test
	public void updateByExampleのテスト() {
		Integer templateBasicSkillId = 1;
		TemplateBasicSkillExample example = new TemplateBasicSkillExample();
		example.or()
			.andTemplateBasicSkillIdEqualTo(templateBasicSkillId);
		TemplateBasicSkill beforeUpdateTemplateBasicSkill =
				mapper.selectByExample(example).get(0);
		beforeUpdateTemplateBasicSkill.setScore(5);
		mapper.updateByExample(beforeUpdateTemplateBasicSkill, example);
		
		TemplateBasicSkill actual = mapper.selectByExample(example).get(0);
		Integer expectedScore = 5;
		assertEquals(actual.getScore(), expectedScore);
	}
}
