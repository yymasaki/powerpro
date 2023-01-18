package com.example.service.technique;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.common.Category;
import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;
import com.example.mapper.TechnicalSkillMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetTechnicalSkillsForTagServiceTest {
	
	private List<TechnicalSkill> technicalSkillList = new ArrayList<>();
	
	@Mock
	private TechnicalSkillMapper mapper;
	
	@InjectMocks
	private GetTechnicalSkillsForTagService service;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 全カテゴリーのスキルが存在する場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill8);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("\"g\",\"h\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}
	
	@Test
	public void OSスキルが存在しない場合() {
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill8);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("\"g\",\"h\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

	@Test
	public void 言語スキルが存在しない場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill8);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("\"g\",\"h\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

	@Test
	public void フレームワークスキルが存在しない場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill8);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("\"g\",\"h\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

	@Test
	public void ライブラリスキルが存在しない場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill8);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("\"g\",\"h\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

	@Test
	public void ミドルウェアスキルが存在しない場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill8);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("\"g\",\"h\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

	@Test
	public void ツールスキルが存在しない場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkillList.add(technicalSkill7);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("", result.get(5).toString());
		assertEquals("\"g\"",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

	@Test
	public void 工程スキルが存在しない場合() {
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkillList.add(technicalSkill6);
		when(mapper.selectByExample(any(TechnicalSkillExample.class))).thenReturn(technicalSkillList);
		List<StringBuilder> result = service.getTechnicalSkillsForTag();
		assertEquals(7, result.size());
		assertEquals("\"a\"", result.get(0).toString());
		assertEquals("\"b\"", result.get(1).toString());
		assertEquals("\"c\"", result.get(2).toString());
		assertEquals("\"d\"", result.get(3).toString());
		assertEquals("\"e\"", result.get(4).toString());
		assertEquals("\"f\"", result.get(5).toString());
		assertEquals("",  result.get(6).toString());
		verify(mapper, times(1)).selectByExample(any(TechnicalSkillExample.class));
	}

}
