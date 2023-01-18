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
import com.example.mapper.TemplateMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTemplateForEditServiceTest {
	
	@Mock
	private TemplateMapper mapper;
	
	@InjectMocks
	private GetTemplateForEditService target;
	
	/**
	 * @author yuuki
	 */
	@Test
	public void getTemplateForEditのテスト() {
		Integer templateId = 1;
		Integer departmentId = 1;
		String name = "テストテンプレート1";
		
		List<Template> templateList = new ArrayList<>();
		Template template = new Template();
		template.setTemplateId(templateId);
		template.setDepartmentId(departmentId);
		template.setName(name);
		templateList.add(template);
		
		when(mapper.selectByExample(any())).thenReturn(templateList);
		List<Template> actual = target.getTemplateForEdit(templateId, departmentId, name);
		assertEquals(templateList, actual);
	}
}
