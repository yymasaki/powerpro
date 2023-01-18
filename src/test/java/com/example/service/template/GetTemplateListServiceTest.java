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
public class GetTemplateListServiceTest {
	
	@Mock
	private TemplateMapper mapper;
	
	@InjectMocks
	private GetTemplateListService target;
	
	/**
	 * departmentIdが1の場合のgetTemplateListメソッドのテスト.
	 */
	@Test
	public void departmentIdが1の場合のgetTemplateListメソッドのテスト() {
		List<Template> templateList = new ArrayList<>();
		Template template1 = new Template();
		template1.setTemplateId(1);
		template1.setName("テストテンプレート1");
		templateList.add(template1);
		
		Integer departmentId = 1;
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		
		List<Template> actual = target.getTemplateList(departmentId);
		assertEquals(templateList, actual);
		assertEquals(1, actual.size());
	}
	
	/**
	 * departmentIdが2の場合のgetTemplateListメソッドのテスト.
	 */
	@Test
	public void departmentIdが2の場合のgetTemplateListメソッドのテスト() {
		List<Template> templateList = new ArrayList<>();
		Template template1 = new Template();
		template1.setTemplateId(1);
		template1.setName("テストテンプレート1");
		templateList.add(template1);
		
		Integer departmentId = 2;
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		
		List<Template> actual = target.getTemplateList(departmentId);
		assertEquals(templateList, actual);
		assertEquals(1, actual.size());
	}
	
	/**
	 * departmentIdが3の場合のgetTemplateListメソッドのテスト.
	 */
	@Test
	public void departmentIdが3の場合のgetTemplateListメソッドのテスト() {
		List<Template> templateList = new ArrayList<>();
		
		Template template3 = new Template();
		template3.setTemplateId(3);
		template3.setName("テストテンプレート3");
		templateList.add(template3);
		
		Integer departmentId = 3;
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		
		List<Template> actual = target.getTemplateList(departmentId);
		assertEquals(templateList, actual);
		assertEquals(1, actual.size());
	}
	
	/**
	 * departmentIdが5の場合のgetTemplateListメソッドのテスト.
	 */
	@Test
	public void departmentIdが5の場合のgetTemplateListメソッドのテスト() {
		List<Template> templateList = new ArrayList<>();
		Template template1 = new Template();
		template1.setTemplateId(1);
		template1.setName("テストテンプレート1");
		templateList.add(template1);
		
		Template template2 = new Template();
		template2.setTemplateId(2);
		template2.setName("テストテンプレート2");
		templateList.add(template2);
		
		Template template3 = new Template();
		template3.setTemplateId(3);
		template3.setName("テストテンプレート3");
		templateList.add(template3);
		
		Integer departmentId = 5;
		when(mapper.selectByExample(any(TemplateExample.class))).thenReturn(templateList);
		
		List<Template> actual = target.getTemplateList(departmentId);
		assertEquals(templateList, actual);
		assertEquals(3, actual.size());
	}
}
