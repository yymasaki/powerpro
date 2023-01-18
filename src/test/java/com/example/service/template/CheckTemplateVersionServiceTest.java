package com.example.service.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
public class CheckTemplateVersionServiceTest {
	
	@Mock
	private TemplateMapper mapper;
	
	@InjectMocks
	private CheckTemplateVersionService target;
	
	/**
	 * templateListのsize()が1以上の場合のテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void templateListのsizeが1以上の場合のcheckTemplateVersionメソッドのテスト() {
		Integer templateId = 1;
		String name = "テストテンプレート1";
		Integer version = 1;
		
		List<Template> templateList = new ArrayList<>();
		Template template = new Template();
		template.setTemplateId(templateId);
		template.setName(name);
		template.setVersion(version);
		templateList.add(template);
		
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		List<Template> actual = target.checkTemplateVersion(templateId, version);
		assertEquals(templateList, actual);
		assertEquals(1, actual.size());
	}
	
	/**
	 * templateListのsize()が0の場合のテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void templateListのsizeが0の場合のcheckTemplateVersionメソッドのテスト() {
		Integer templateId = 1;
		Integer version = 1;
		List<Template> templateList = new ArrayList<>();
		
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		List<Template> actual = target.checkTemplateVersion(templateId, version);
		assertNull(actual);
	}
}
