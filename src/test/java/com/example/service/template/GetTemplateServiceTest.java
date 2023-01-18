package com.example.service.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
public class GetTemplateServiceTest {
	
	@Mock
	private TemplateMapper mapper;
	
	@InjectMocks
	private GetTemplateService target;

	@Test
	public void getTemplateメソッドのテスト_sixeが1以上の場合() {
		Integer templateId = 1;
		String name = "テストテンプレート";
		Template template = new Template();
		template.setTemplateId(templateId);
		template.setName(name);
		List<Template> templateList = Arrays.asList(template);
		when(mapper.selectByTemplateId(templateId)).thenReturn(templateList);
		Template actual = target.getTemplate(templateId);
		assertEquals(templateId,actual.getTemplateId());
		assertEquals(name,actual.getName());
	}
	
	@Test
	public void getTemplateメソッドのテスト_sixeが0の場合() {
		Integer templateId = 1;
		List<Template> templateList = new ArrayList<>();
		when(mapper.selectByTemplateId(templateId)).thenReturn(templateList);
		Template actual = target.getTemplate(templateId);
		assertNull(actual);
		
	}

}
