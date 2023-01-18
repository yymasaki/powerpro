package com.example.service.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.TechnicalSkill;
import com.example.form.SearchRequestForm;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTechnicalSkillForRequestServiceTest {
	
	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private GetTechnicalSkillForRequestService target;
	
	/**
	 * getTechnicalSkillForRequestメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void departmentIdが5の場合のgetTechnicalSkillForRequestメソッドのテスト() {
		Integer departmentId = 5;
		Integer stage = 2;
		String applicant = "テスト三郎";
		Integer startNumber = 0;
		
		SearchRequestForm form = new SearchRequestForm();
		form.setContent(1);
		form.setStage(stage);
		form.setApplicant(applicant);
		form.setDepartmentId(departmentId);
		form.setUserId(1);
		form.setPageNumber(1);
		
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		when(mapper.selectByStageAndApplicant(stage, applicant, startNumber)).thenReturn(technicalSkillList);
		
		List<TechnicalSkill> actual = target.getTechnicalSkillForRequest(form, startNumber);
		assertEquals(actual, technicalSkillList);
	}

}
