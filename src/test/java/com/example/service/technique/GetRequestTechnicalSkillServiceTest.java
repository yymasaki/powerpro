package com.example.service.technique;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Department;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetRequestTechnicalSkillServiceTest {

	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private GetRequestTechnicalSkillService service;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 主キーからテクニカルスキル_ユーザ_部署を取得する() {
		Integer technicalSkillId=1;
		Integer userId=1;
		String departmentName="Webエンジニア";
		
		TechnicalSkill technicalSkill=new TechnicalSkill();
		User user=new User();
		Department department=new Department();
		
		user.setUserId(userId);
		department.setName(departmentName);
		user.setDepartment(department);
		technicalSkill.setUser(user);
		technicalSkill.setTechnicalSkillId(technicalSkillId);
		
		when(mapper.selectByTechnicalSkillId(technicalSkillId)).thenReturn(technicalSkill);

		TechnicalSkill actual=service.getRequestTechnicalSkill(technicalSkillId);
		
		assertEquals(technicalSkillId, actual.getTechnicalSkillId());
		assertEquals(userId, actual.getUser().getUserId());
		assertEquals(departmentName, actual.getUser().getDepartment().getName());
	}

}
