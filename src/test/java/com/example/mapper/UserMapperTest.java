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
		// ?????????????????????????????????INSERT????????????????????????
		assertEquals("JUnit???????????????", target.selectByPrimaryKey(1).getName());
		assertEquals("JUnit???????????????", target.selectByPrimaryKey(2).getName());
		assertEquals("JUnit???????????????", target.selectByPrimaryKey(3).getName());
		assertEquals("JUnit???????????????", target.selectByPrimaryKey(4).getName());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void ????????????????????????????????????????????????????????????stage???0??????????????????????????????() {
		List<Integer> skillIdList = new ArrayList<>();
		skillIdList.add(1);
		skillIdList.add(2);
		skillIdList.add(3);
		User user = new User();
		user.setName("??????");
		user.setDepartmentId(1);
		List<User> users = target.selectUsersWithTechnicalSkills(user,null);
		assertEquals("JUnit???????????????", users.get(0).getName());
	}

	@Test
	public void ????????????????????????stage???0??????????????????????????????() {
		User user = new User();
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		List<User> users = target.selectUsersWithTechnicalSkills(user, null);
		assertEquals("JUnit???????????????", users.get(0).getName());
	}

	@Test
	public void ?????????email????????????????????????stage????????????????????????????????????() {
		String email = "junit.taro";
		String stage = "0";
		User user = target.selectByEmailAndStage(email, stage).get(0);
		String userName = "JUnit???????????????";
		assertEquals(userName, user.getName());
	}

	@Test
	public void example??????????????????????????????????????????() {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo("junit.taro");
		example.createCriteria().andStageEqualTo("0");
		User user = target.selectByExample(example).get(0);
		String userName = "JUnit???????????????";
		assertEquals(userName, user.getName());
	}

	@Test
	public void ID??????????????????????????????() {
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(1);
		User user = target.selectByUserId(example);
		String userName = "JUnit???????????????";
		assertEquals(userName, user.getName());
	}

	@Test
	public void ?????????????????????????????????() {
		User user = new User();
		user.setName("JUnit???????????????");
		user.setHiredOn(LocalDate.of(2020, 1, 1));
		user.setDepartmentId(1);
		user.setEmail("junit.hanako");
		user.setPassword("password");
		user.setStage(1);
		LocalDateTime now = LocalDateTime.now();
		user.setUpdatedAt(now);
		user.setUpdater("???????????????");
		user.setCreatedAt(now);
		user.setCreator("???????????????");
		user.setVersion(1);
		// ???????????????(stage=1)?????????insert
		target.insertSelective(user);
		// ??????
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo("junit.hanako");
		User userCheck = target.selectByExample(example).get(0);
		assertEquals(user.getName(), userCheck.getName());
		assertEquals(Integer.valueOf(1), userCheck.getStage());
	}

	@Test
	public void example??????????????????????????????????????????() {
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(1);
		User userBefore = target.selectByExample(example).get(0);
		assertEquals("JUnit???????????????", userBefore.getName());
		User updateUser = new User();
		updateUser.setName("JUnit?????????????????????");
		target.updateByExampleSelective(updateUser, example);
		User userAfter = target.selectByExample(example).get(0);
		assertEquals("JUnit?????????????????????", userAfter.getName());
		
	}

	@Test
	public void ????????????????????????????????????????????????????????????() {
		User userUpdate = new User();
		userUpdate.setEmail("junit.ziro");
		userUpdate.setStage(0);
		// update
		target.updateNewestTemporaryUser(userUpdate);
		// ??????
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(2);
		User userCheck = target.selectByExample(example).get(0);
		assertEquals("JUnit???????????????", userCheck.getName());
		assertEquals(Integer.valueOf(0), userCheck.getStage());
	}

	@Test
	public void stage???0?????????????????????????????????() {
		List<User> userList = target.selectAllValidUsers();
		assertEquals(4, userList.size());
	}

	@Test
	public void ?????????????????????????????????() {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo("junit.ziro").andStageEqualTo("1");
		int actual = target.deleteByExample(example);
		assertEquals(1, actual);
	}
}
