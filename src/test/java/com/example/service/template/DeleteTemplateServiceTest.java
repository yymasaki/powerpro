package com.example.service.template;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mapper.TemplateMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DeleteTemplateServiceTest {

	@Mock
	private TemplateMapper mapper;
	
	@InjectMocks
	private DeleteTemplateService target;

	@Test
	public void deleteTemplateのテスト() {
		Integer templateId = 1;
		target.deleteTemplate(templateId);
		verify(mapper, times(1)).deleteByPrimaryKey(templateId);
	}
}
