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

import com.example.domain.Template;
import com.example.example.TemplateExample;
import com.example.mapper.TemplateMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTemplateForRegisterServiceTest {
	
	@Mock
	private TemplateMapper mapper;
	
	@InjectMocks
	private GetTemplateForRegisterService target;
	
	@Test
	public void getTemplateForRegisterメソッドのテスト() {
		Integer departmentId = 1;
		String name = "テストテンプレート1";
		
		List<Template> templateList = new ArrayList<>();
		Template template = new Template();
		template.setTemplateId(1);
		template.setName(name);
		template.setDepartmentId(departmentId);
		templateList.add(template); 
		
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		List<Template> actual = target.getTemplateForRegister(departmentId, name);
		assertEquals(templateList, actual);
	}

}
