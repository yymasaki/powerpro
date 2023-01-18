package com.example.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.domain.Information;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

import common_sql.AllDeleteAndIncrementTestSql;
import common_sql.DepartmentTestSql;
import common_sql.InformationDepartmentLinkTestSql;
import common_sql.InformationTestSql;
import common_sql.UserTestSql;

@RunWith(SpringRunner.class)
@MybatisTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InformationMapperTest {

	@Autowired
	private static AllDeleteAndIncrementTestSql allDeleteAndIncrementTestSql;

	@Autowired
	private InformationMapper target;

	@Autowired
	protected static InformationTestSql informationTestSql;

	@Autowired
	protected static UserTestSql userTestSql;

	@Autowired
	protected static DepartmentTestSql departmentTestSql;

	@Autowired
	protected static InformationDepartmentLinkTestSql information_department_linkTestSql;

	public static final Destination dest = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
			"root", "mysqlmysql");
	
	@BeforeAll
	@SuppressWarnings("static-access")
	public static void setUpBeforeClass() throws Exception {
		DbSetup information_dbSetup = new DbSetup(dest,
				Operations.sequenceOf(
						allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						departmentTestSql.DEPARTMENT_INSERT,
						userTestSql.USER_INSERT,
						InformationTestSql.INFORMATION_INSERT,
						InformationDepartmentLinkTestSql.INFORMATION_DEPARTMENT_LINK_INSERT));
		information_dbSetup.launch();
	}

	@AfterAll
	@SuppressWarnings("static-access")
	public static void tearDownAfterClass() throws Exception {
		DbSetup delete_dbSetup = new DbSetup(dest, allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		delete_dbSetup.launch();
	}

	@BeforeEach
	public void setUp() throws Exception {
		assertEquals("管理者用サンプルタイトル", target.selectByPrimaryKey(1).getTitle());
		assertEquals("エンジニア、管理者用サンプルタイトル", target.selectByPrimaryKey(2).getTitle());
		assertEquals("営業、管理者用サンプルタイトル", target.selectByPrimaryKey(3).getTitle());
		assertEquals("全部署用サンプルタイトル", target.selectByPrimaryKey(4).getTitle());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * お知らせがinsertされるかテストする.
	 * insert時にinformation_idが帰ってくるので、information_idがnull出ないことも確認.
	 */
	@Test
	@DisplayName("お知らせをインサートする")
	public void information_Insert() {
		java.util.Date date = new java.util.Date();
		java.sql.Date setStartPostedOn = new java.sql.Date(date.getTime());
		String endDate = "2025-07-25";
		Date setEndPostedOn = Date.valueOf(endDate);
		Information information = new Information();
		information.setTitle("タイトル");
		information.setContent("コンテンツ");
		information.setCreateUserId(1);
		information.setStartPostedOn(setStartPostedOn);
		information.setEndPostedOn(setEndPostedOn);
		information.setStage(0);
		target.insert(information);
		assertNotNull(information.getInformationId());

		Information searchedInformation = target.selectByPrimaryKey(9);
		assertEquals(searchedInformation.getTitle(), "タイトル");
		assertEquals(searchedInformation.getContent(), "コンテンツ");
		assertEquals(String.valueOf(searchedInformation.getStartPostedOn()), String.valueOf(setStartPostedOn));
		assertEquals(String.valueOf(searchedInformation.getEndPostedOn()), String.valueOf("2025-07-25"));
	}

	@Test
	@DisplayName("全部署用のお知らせを取得する")
	public void select_infomation() {
		Integer userId = 1;
		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime());
		Integer offset = 0;
		List<Information> informationList = target.selectByDepartmentIdAndStageAndCurrentDate(userId, currentDate,
				offset);
		assertNotNull(informationList);
		// お知らせは作成日(id)順に表示するので、1番目のidは4がくる
		assertEquals(informationList.get(0).getInformationId(), 8);
		assertEquals(informationList.get(0).getTitle(), "全部署用サンプルタイトル");
		assertEquals(informationList.get(0).getContent(), "全部署用サンプル本文");
		assertEquals(informationList.get(0).getStartPostedOn(), Date.valueOf("2020-06-20"));
		assertEquals(informationList.get(0).getEndPostedOn(), Date.valueOf("2024-12-30"));
	}

	@Test
	public void 表示対象のお知らせを全件取得する_ページ番号用() {
		Integer userId = 1;
		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime());
		List<Information> informationList = target.selectAll(userId, currentDate);
		System.out.println(informationList);
		assertNotNull(informationList);
		assertEquals(informationList.size(), 6);
	}

}
