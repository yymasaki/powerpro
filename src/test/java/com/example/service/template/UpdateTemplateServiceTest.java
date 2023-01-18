package com.example.service.template;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Template;
import com.example.domain.TemplateBasicSkill;
import com.example.domain.TemplateEngineerSkill;
import com.example.form.EditTemplateForm;
import com.example.mapper.TemplateBasicSkillMapper;
import com.example.mapper.TemplateEngineerSkillMapper;
import com.example.mapper.TemplateMapper;

@SpringBootTest
// @RunWith(MockitoJUnitRunner.class)
public class UpdateTemplateServiceTest {
	
	@Mock
	private TemplateMapper templateMapper;
	
	@Mock
	private TemplateEngineerSkillMapper templateEngineerSkillMapper;
	
	@Mock
	private TemplateBasicSkillMapper templateBasicSkillMapper;
	
	@InjectMocks
	private UpdateTemplateService target;
	
	/**
	 * editTemplateメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void テンプレートを編集する() throws Exception{
		List<Integer> engineerSkillIdList = Arrays.asList(1,2,3,4,5,6,7);
		List<Integer> templateEngineerSkillScoreList = Arrays.asList(50,50,50,50,50,50,50);
		List<Integer> basicSkillIdList = Arrays.asList(1,2,3,4,5,6);
		List<Integer> templateBasicSkillScoreList = Arrays.asList(3,3,3,3,3,3);
		
		EditTemplateForm form = new EditTemplateForm();
		form.setTemplateId(1);
		form.setDepartmentId(1);
		form.setName("テストテンプレート1改");
		form.setEngineerSkillIdList(engineerSkillIdList);
		form.setTemplateEngineerSkillScoreList(templateEngineerSkillScoreList);
		form.setBasicSkillIdList(basicSkillIdList);
		form.setTemplateBasicSkillScoreList(templateBasicSkillScoreList);
		form.setVersion(2);
		
		// モックの作成
		List<Template> templateList = new ArrayList<>();
		templateList.add(new Template());
		
		List <TemplateEngineerSkill> templateEngineerSkillList = new ArrayList<>();
		for (int i = 0; i < engineerSkillIdList.size(); i++) {
			templateEngineerSkillList.add(new TemplateEngineerSkill());
		}
		
		List<TemplateBasicSkill> templateBasicSkillList = new ArrayList<>();
		for (int i = 0; i < basicSkillIdList.size(); i++) {
			templateBasicSkillList.add(new TemplateBasicSkill());
		}
		
		when(templateMapper.selectByExample(any())).thenReturn(templateList);
		when(templateEngineerSkillMapper.selectByExample(any())).thenReturn(templateEngineerSkillList);
		when(templateBasicSkillMapper.selectByExample(any())).thenReturn(templateBasicSkillList);
		String userName = "Webエンジニア次郎";
		target.editTemplate(form, userName);
		
		verify(templateMapper,times(1)).selectByExample(any());
		verify(templateMapper,times(1)).updateByExample(any(), any());
		verify(templateEngineerSkillMapper,times(7)).selectByExample(any());
		verify(templateEngineerSkillMapper,times(7)).updateByExample(any(), any());
		verify(templateBasicSkillMapper,times(6)).selectByExample(any());
		verify(templateBasicSkillMapper,times(6)).updateByExample(any(), any());
	}

}
