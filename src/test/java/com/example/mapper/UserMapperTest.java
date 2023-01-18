package com.example.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.Theories;
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
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.domain.User;
import com.example.example.UserExample;
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
import junit.framework.TestCase;

@RunWith(Theories.class)
@MybatisTest
@TestExecutionListeners({ 
	DependencyInjectionTestExecutionListener.class, 
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest extends TestCase {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();


	@Autowired
	private UserMapper target;
	
	@Autowired
	private static UserTestSql userTestSql;
	
	@Autowired
	private static AllDeleteAndIncrementTestSql allDeleteAndIncrementTestSql;
	
	@Autowired
	private static DepartmentTestSql departmentTestSql;
	
	@Autowired
	private static TechnicalSkillTestSql technicalSkillTestSql;
	
	@Autowired
	private static HadTechnicalSkillTestSql hadTechnicalSkillTestSql;
	
	public static final  Destination dest = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
			"root", "mysqlmysql");
	
	@BeforeAll
	@SuppressWarnings("static-access")
	public static void setUpBeforeClass() throws Exception {
		DbSetup users_dbSetup = new DbSetup(dest, 
				Operations.sequenceOf(
						allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						departmentTestSql.DEPARTMENT_INSERT,
						userTestSql.USER_INSERT,
						technicalSkillTestSql.TECHNICAL_SKILL_INSERT,
						hadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_INSERT
				));
		users_dbSetup.launch();
	}

	@AfterAll
	@SuppressWarnings("static-access")
	public static void tearDownAfterClass() throws Exception {
		DbSetup users_dbSetup = new DbSetup(dest, allDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		users_dbSetup.launch();
	}

	@BeforeEach
	public void setUp() throws Exception {
		// テスト用データが正しくINSERTされているか確認
		assertEquals("JUnitテスト太郎", target.selectByPrimaryKey(1).getName());
		assertEquals("JUnitテスト次郎", target.selectByPrimaryKey(2).getName());
		assertEquals("JUnitテスト三郎", target.selectByPrimaryKey(3).getName());
		assertEquals("JUnitテスト四郎", target.selectByPrimaryKey(4).getName());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 保有スキルとユーザー名一部と所属によってstageが0のユーザー情報を取得() {
		List<Integer> skillIdList = new ArrayList<>();
		skillIdList.add(1);
		skillIdList.add(2);
		skillIdList.add(3);
		User user = new User();
		user.setName("太郎");
		user.setDepartmentId(1);
		List<User> users = target.selectUsersWithTechnicalSkills(user,null);
		assertEquals("JUnitテスト太郎", users.get(0).getName());
	}

	@Test
	public void 入社年月によってstageが0のユーザー情報を取得() {
		User user = new User();
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		List<User> users = target.selectUsersWithTechnicalSkills(user, null);
		assertEquals("JUnitテスト太郎", users.get(0).getName());
	}

	@Test
	public void 登録済email情報をもとに有効stageのユーザー情報を取得する() {
		String email = "junit.taro";
		String stage = "0";
		User user = target.selectByEmailAndStage(email, stage).get(0);
		String userName = "JUnitテスト太郎";
		assertEquals(userName, user.getName());
	}

	@Test
	public void exampleに合致するユーザー情報を取得() {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo("junit.taro");
		example.createCriteria().andStageEqualTo("0");
		User user = target.selectByExample(example).get(0);
		String userName = "JUnitテスト太郎";
		assertEquals(userName, user.getName());
	}

	@Test
	public void IDでユーザー情報を取得() {
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(1);
		User user = target.selectByUserId(example);
		String userName = "JUnitテスト太郎";
		assertEquals(userName, user.getName());
	}

	@Test
	public void 新規ユーザーを登録する() {
		User user = new User();
		user.setName("JUnitテスト花子");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setDepartmentId(1);
		user.setEmail("junit.hanako");
		user.setPassword("password");
		user.setStage(1);
		LocalDateTime now = LocalDateTime.now();
		user.setUpdatedAt(now);
		user.setUpdater("テスト次郎");
		user.setCreatedAt(now);
		user.setCreator("テスト次郎");
		user.setVersion(1);
		// 仮ユーザー(stage=1)としてinsert
		target.insertSelective(user);
		// 確認
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo("junit.hanako");
		User userCheck = target.selectByExample(example).get(0);
		assertEquals(user.getName(), userCheck.getName());
		assertEquals(Integer.valueOf(1), userCheck.getStage());
	}

	@Test
	public void exampleに該当するユーザー情報を更新() {
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(1);
		User userBefore = target.selectByExample(example).get(0);
		assertEquals("JUnitテスト太郎", userBefore.getName());
		User updateUser = new User();
		updateUser.setName("JUnitテスト太郎更新");
		target.updateByExampleSelective(updateUser, example);
		User userAfter = target.selectByExample(example).get(0);
		assertEquals("JUnitテスト太郎更新", userAfter.getName());
		
	}

	@Test
	public void 仮ユーザー情報を更新し有効ユーザーとする() {
		User userUpdate = new User();
		userUpdate.setEmail("junit.ziro");
		userUpdate.setStage(0);
		// update
		target.updateNewestTemporaryUser(userUpdate);
		// 確認
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(2);
		User userCheck = target.selectByExample(example).get(0);
		assertEquals("JUnitテスト次郎", userCheck.getName());
		assertEquals(Integer.valueOf(0), userCheck.getStage());
	}

	@Test
	public void stageが0の全ユーザーを取得する() {
		List<User> userList = target.selectAllValidUsers();
		assertEquals(4, userList.size());
	}

	@Test
	public void 特定ユーザーを削除する() {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo("junit.ziro").andStageEqualTo("1");
		int actual = target.deleteByExample(example);
		assertEquals(1, actual);
	}
}
