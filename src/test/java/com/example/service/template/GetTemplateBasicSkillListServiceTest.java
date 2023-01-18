package com.example.service.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.TemplateBasicSkill;
import com.example.example.TemplateBasicSkillExample;
import com.example.mapper.TemplateBasicSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTemplateBasicSkillListServiceTest {

	@Mock
	private TemplateBasicSkillMapper mapper;
	
	@InjectMocks
	private GetTemplateBasicSkillListService target;
	
	@Test
	public void getTemplateBasicSkillListメソッドのテスト() {
		List<TemplateBasicSkill> templateBasicSkillList = new ArrayList<>();
		
		TemplateBasicSkill templatebs1 = new TemplateBasicSkill();
		templatebs1.setTemplateBasicSkillId(1);
		templateBasicSkillList.add(templatebs1);
		
		TemplateBasicSkill templatebs2 = new TemplateBasicSkill();
		templatebs2.setTemplateBasicSkillId(2);
		templateBasicSkillList.add(templatebs2);
		
		TemplateBasicSkill templatebs3 = new TemplateBasicSkill();
		templatebs3.setTemplateBasicSkillId(3);
		templateBasicSkillList.add(templatebs3);
		
		TemplateBasicSkill templatebs4 = new TemplateBasicSkill();
		templatebs4.setTemplateBasicSkillId(4);
		templateBasicSkillList.add(templatebs4);
		
		TemplateBasicSkill templatebs5 = new TemplateBasicSkill();
		templatebs5.setTemplateBasicSkillId(5);
		templateBasicSkillList.add(templatebs5);
		
		TemplateBasicSkill templatebs6 = new TemplateBasicSkill();
		templatebs6.setTemplateBasicSkillId(6);
		templateBasicSkillList.add(templatebs6);
		
		Integer templateId = 1;
		when(mapper.selectByExample(any(TemplateBasicSkillExample.class))).thenReturn(templateBasicSkillList);
		
		List<TemplateBasicSkill> actual = target.getTemplateBasicSkillList(templateId);
		assertEquals(templateBasicSkillList, actual);
		assertEquals(6, actual.size());
	}

}
