package com.example.service.status;

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

import com.example.domain.EngineerSkill;
import com.example.example.EngineerSkillExample;
import com.example.mapper.EngineerSkillMapper;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetEngineerSkillListServiceTest {

	@Mock
	private EngineerSkillMapper mapper;

	@InjectMocks
	private GetEngineerSkillListService target;

	/**
	 * getEngineerSkillListメソッドのテスト.
	 * 
	 * @author yuuki
	 */
	@Test
	public void 所属部署のエンジニアスキルを取得する() {
		List<EngineerSkill> engineerSkillList = new ArrayList<>();
		EngineerSkill engineerSkill1 = new EngineerSkill();
		engineerSkill1.setEngineerSkillId(1);
		engineerSkill1.setName("テスト基本スキル1");
		engineerSkillList.add(engineerSkill1);

		EngineerSkill engineerSkill2 = new EngineerSkill();
		engineerSkill2.setEngineerSkillId(2);
		engineerSkill2.setName("テスト基本スキル2");
		engineerSkillList.add(engineerSkill2);

		EngineerSkill engineerSkill3 = new EngineerSkill();
		engineerSkill3.setEngineerSkillId(3);
		engineerSkill3.setName("テスト基本スキル3");
		engineerSkillList.add(engineerSkill3);

		Integer departmentId = 1;
		when(mapper.selectByExample(any(EngineerSkillExample.class))).thenReturn(engineerSkillList);

		List<EngineerSkill> actual = target.getEngineerSkillList(departmentId);
		assertEquals(engineerSkillList, actual);
		assertEquals(3, actual.size());
	}
}
