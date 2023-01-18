package com.example.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.example.domain.InformationsDepartmentLink;
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
public class InformationsDepartmentLinkMapperTest {

	@Autowired
	private InformationsDepartmentLinkMapper target;
	
	@Autowired
	private InformationMapper mapper;
	
	@Autowired
	protected static InformationTestSql informationTestSql;
	
	@Autowired
	protected static UserTestSql userTestSql;
	
	@Autowired
	protected static InformationDepartmentLinkTestSql information_department_linkTestSql;
	
	@Autowired
	protected static DepartmentTestSql departmentTestSql;
	
	public static final  Destination dest = new DriverManagerDestination
			("jdbc:mysql://localhost:3306/powerpro_exam",
			"root", "mysqlmysql");
	
	@BeforeAll
	@SuppressWarnings("static-access")
	public static void setUpBeforeClass() throws Exception {
		DbSetup information_dbSetup = new DbSetup(dest, 
				Operations.sequenceOf(
						AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT,
						departmentTestSql.DEPARTMENT_INSERT,
						userTestSql.USER_INSERT,
						InformationTestSql.INFORMATION_INSERT
				));
		information_dbSetup.launch();
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		DbSetup delete_dbSetup = new DbSetup(dest, AllDeleteAndIncrementTestSql.ALLDELETE_AND_INCREMENT);
		delete_dbSetup.launch();
	}

	@BeforeEach
	public void setUp() throws Exception {
		assertEquals(mapper.selectByPrimaryKey(1).getInformationId(), 1);
		assertEquals(mapper.selectByPrimaryKey(2).getInformationId(), 2);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * InformationsDepartmentLinkオブジェクトの数に合わせてinsert件数をチェックする.
	 */
	@Test
	public void お知らせと部署関係のテーブルをinsertする() {
		List<InformationsDepartmentLink> informationsDepartmentLinkList= new ArrayList<>();
		InformationsDepartmentLink informationsDepartmentLink1= new InformationsDepartmentLink();
		InformationsDepartmentLink informationsDepartmentLink2= new InformationsDepartmentLink();
		informationsDepartmentLink1.setInformationId(1);
		informationsDepartmentLink1.setDepartmentId(1);
		informationsDepartmentLink2.setInformationId(2);
		informationsDepartmentLink2.setDepartmentId(2);
		informationsDepartmentLinkList.add(informationsDepartmentLink1);
		informationsDepartmentLinkList.add(informationsDepartmentLink2);
		int numOfInsert=target.insert(informationsDepartmentLinkList);
		assertEquals(numOfInsert, 2);
		InformationsDepartmentLink informationsDepartmentLink=target.selectByPrimaryKey(1);
		assertEquals(informationsDepartmentLink.getInformationsDepartmentLinkId(), 1);
		assertEquals(informationsDepartmentLink.getInformationId(), 1);
		assertEquals(informationsDepartmentLink.getDepartmentId(), 1);
	}
	
}
