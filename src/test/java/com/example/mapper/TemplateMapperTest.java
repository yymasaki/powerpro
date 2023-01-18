package com.example.mapper;


import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
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

import com.example.common.Stage;
import com.example.domain.Template;
import com.example.example.TemplateExample;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.BasicSkillTestSql;
import common_sql.DepartmentTestSql;
import common_sql.EngineerSkillTestSql;
import common_sql.TemplateBasicSkillTestSql;
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
public class TemplateMapperTest {
	
	@Autowired
	private TemplateMapper mapper;	
	
	/** Operationオブジェクトを実行したい順番に繋げる */
	static Operation insert_ops = sequenceOf(DepartmentTestSql.DEPARTMENT_INSERT,TemplateTestSql.TEMPLATES_INSERT, EngineerSkillTestSql.ENGINEER_SKILL_INSERT,
											BasicSkillTestSql.BASIC_SKILL_INSERT,TemplateEngineerSkillTestSql.
											TEMPLATE_ENGINEER_SKILLS_INSERT,TemplateBasicSkillTestSql.TEMPLATE_BASIC_SKILLS_INSERT);
	
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

	/**
	 * 主キーからテンプレートを1件取得する.
	 */
	@Test
	public void selectByTemplateIdのテスト() {
		Integer templateId = 1;
		String name = "テストテンプレート1";
		Template actual = mapper.selectByTemplateId(templateId).get(0);
		assertEquals(actual.getTemplateId(), templateId);
		assertEquals(actual.getName(), name);
	}
	
	/**
	 * 所属idからテンプレートリストを取得する.
	 * 
	 */
	@Test
	public void departmentIdが検索条件のselectByExampleのテスト() {
		Integer departmentId = 1;
		TemplateExample example = new TemplateExample();
		example.or().andDepartmentIdEqualTo(departmentId);
		example.setOrderByClause("template_id");
		List<Template> actual = mapper.selectByExample(example);
		assertEquals(actual.size(), 3);
		assertEquals(actual.get(0).getName(), "テストテンプレート1");
		assertEquals(actual.get(1).getName(), "テストテンプレート2");
		assertEquals(actual.get(2).getName(), "テストテンプレート3");
	}
	
	/**
	 * 所属idとテンプレート名からテンプレートリストを取得する.
	 */
	@Test
	public void departmentIdとnameが検索条件のselectByExampleのテスト() {
		Integer departmentId = 1;
		String name = "テストテンプレート3";
		TemplateExample example = new TemplateExample();
		example.or()
			.andDepartmentIdEqualTo(departmentId)
			.andNameEqualTo(name);
		example.setOrderByClause("template_id");
		List<Template> actual = mapper.selectByExample(example);
		assertEquals(actual.size(), 1);
		assertEquals(actual.get(0).getTemplateId(), 3);
		assertEquals(actual.get(0).getName(), "テストテンプレート3");
	}
	
	/**
	 * templateIdとversionからテンプレートを取得する.
	 */
	@Test
	public void templateIdとversionが検索条件のselectByExampleのテスト() {
		Integer templateId = 1;
		Integer version = 1;
		TemplateExample example = new TemplateExample();
		example.or().andTemplateIdEqualTo(templateId)
					.andVersionEqualTo(version);
		example.setOrderByClause("template_id");
		List<Template> actual = mapper.selectByExample(example);
		assertEquals(actual.size(), 1);
		assertEquals(actual.get(0).getTemplateId(), 1);
		assertEquals(actual.get(0).getName(), "テストテンプレート1");
	}
	
	/**
	 * Templateをinsertし、自動採番された主キーを取得する.
	 */
	@Test
	public void insertReturnIdのテスト() {
		LocalDateTime today = LocalDateTime.now();
		
		Template template = new Template();
		template.setName("テストテンプレート4");
		template.setDepartmentId(1);
		template.setStage(Stage.ACTIVE.getKey());
		template.setCreator("Webエンジニア次郎");
		template.setCreatedAt(today);
		template.setUpdater("Webエンジニア次郎");
		template.setUpdatedAt(today);
		template.setVersion(1);
		
		mapper.insertReturnId(template);
		assertEquals(template.getTemplateId(), 4);
		
		TemplateExample example = new TemplateExample();
		example.setOrderByClause("template_id");
		List<Template> templateList = mapper.selectByExample(example);
		assertEquals(templateList.size(), 4);
		assertEquals(templateList.get(3).getName(), "テストテンプレート4");
	}
	
	/**
	 * Templateを更新する.
	 */
	@Test
	public void updateByExampleのテスト() {
		LocalDateTime today = LocalDateTime.now();
		Integer templateId = 1;
		
		TemplateExample example = new TemplateExample();
		example.or().andTemplateIdEqualTo(templateId);
		Template beforeUpdateTemplate = mapper.selectByExample(example).get(0);
		Integer version = beforeUpdateTemplate.getVersion();
		
		beforeUpdateTemplate.setName("テストテンプレート1改");
		beforeUpdateTemplate.setUpdatedAt(today);
		beforeUpdateTemplate.setVersion(version + 1);
		mapper.updateByExample(beforeUpdateTemplate, example);
		
		Template actual = mapper.selectByExample(example).get(0);
		assertEquals(actual.getName(), "テストテンプレート1改");
		assertEquals(actual.getVersion(), 2);
	}
	
	
	/**
	 * Templateを削除する
	 */
	@Test
	public void deleteByPrimaryKeyのテスト() {
		Integer templateId = 1;
		mapper.deleteByPrimaryKey(templateId);
		// deleteされたかの確認
		List<Template> templateList = mapper.selectByTemplateId(templateId);
		assertEquals(templateList.size(), 0);
	}
}
