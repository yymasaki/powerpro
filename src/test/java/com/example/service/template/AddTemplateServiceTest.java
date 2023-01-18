package com.example.service.template;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Template;
import com.example.domain.TemplateBasicSkill;
import com.example.domain.TemplateEngineerSkill;
import com.example.form.RegisterTemplateForm;
import com.example.mapper.TemplateBasicSkillMapper;
import com.example.mapper.TemplateEngineerSkillMapper;
import com.example.mapper.TemplateMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AddTemplateServiceTest {
	
	@Mock
	private TemplateMapper templateMapper;
	
	@Mock
	private TemplateEngineerSkillMapper templateEngineerSkillMapper;
	
	@Mock
	private TemplateBasicSkillMapper templateBasicSkillMapper;
	
	@InjectMocks
	private AddTemplateService target;
	
	/**
	 * registerTemplateメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void 新規テンプレート登録をする() {
		List<Integer> engineerSkillIdList = Arrays.asList(1,2,3,4,5,6,7);
		List<Integer> templateEngineerSkillScoreList = Arrays.asList(50,50,50,50,50,50,50);
		List<Integer> basicSkillIdList = Arrays.asList(1,2,3,4,5,6);
		List<Integer> templateBasicSkillScoreList = Arrays.asList(3,3,3,3,3,3);
		
		RegisterTemplateForm form = new RegisterTemplateForm();
		form.setDepartmentId(1);
		form.setName("テストテンプレート4");
		form.setEngineerSkillIdList(engineerSkillIdList);
		form.setTemplateEngineerSkillScoreList(templateEngineerSkillScoreList);
		form.setBasicSkillIdList(basicSkillIdList);
		form.setTemplateBasicSkillScoreList(templateBasicSkillScoreList);
		
		String userName = "Webエンジニア次郎";
		target.registerTemplate(form, userName);
		verify(templateMapper,times(1)).insertReturnId(any(Template.class));
		verify(templateEngineerSkillMapper,times(7)).insert(any(TemplateEngineerSkill.class));
		verify(templateBasicSkillMapper, times(6)).insert(any(TemplateBasicSkill.class));
	}

}
