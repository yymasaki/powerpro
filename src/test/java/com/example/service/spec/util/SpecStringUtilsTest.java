package com.example.service.spec.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.common.Category;
import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.TechnicalSkill;
import com.example.domain.UsedTechnicalSkill;
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecStringUtilsTest {
	
	private AppSpecsheet specsheet = new AppSpecsheet();
	private List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
	private TechnicalSkill technicalSkill1 = new TechnicalSkill();
	private TechnicalSkill technicalSkill2 = new TechnicalSkill();
	private TechnicalSkill technicalSkill3 = new TechnicalSkill();
	private TechnicalSkill technicalSkill4 = new TechnicalSkill();
	private TechnicalSkill technicalSkill5 = new TechnicalSkill();
	private TechnicalSkill technicalSkill6 = new TechnicalSkill();
	private TechnicalSkill technicalSkill7 = new TechnicalSkill();
	private TechnicalSkill technicalSkill8 = new TechnicalSkill();
	private List<DevExperience> devExperienceList = new ArrayList<>();
	private List<UsedTechnicalSkill> usedTechnicalSkillList = new ArrayList<>();
	private List<UsedTechnicalSkill> usedTechnicalSkillList2 = new ArrayList<>();
	private EditSpecsheetForm form = new EditSpecsheetForm();
	
	@Autowired
	private SpecStringUtils service;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkill8.setName("h");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

//devideHadTechnicalSkillsForTag????????????
	@Test
	public void ??????????????????????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void OS?????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void ???????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void ??????????????????????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void ????????????????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void ???????????????????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void ??????????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill7 = new HadTechnicalSkill();
		hadTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		hadTechnicalSkillList.add(hadTechnicalSkill7);
		HadTechnicalSkill hadTechnicalSkill8 = new HadTechnicalSkill();
		hadTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		hadTechnicalSkillList.add(hadTechnicalSkill8);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "");
		assertEquals(result.get(6).toString(), "g,h");
	}
	
	@Test
	public void ???????????????????????????????????????????????????() {
		HadTechnicalSkill hadTechnicalSkill1 = new HadTechnicalSkill();
		hadTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		hadTechnicalSkillList.add(hadTechnicalSkill1);
		HadTechnicalSkill hadTechnicalSkill2 = new HadTechnicalSkill();
		hadTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		hadTechnicalSkillList.add(hadTechnicalSkill2);
		HadTechnicalSkill hadTechnicalSkill3 = new HadTechnicalSkill();
		hadTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		hadTechnicalSkillList.add(hadTechnicalSkill3);
		HadTechnicalSkill hadTechnicalSkill4 = new HadTechnicalSkill();
		hadTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		hadTechnicalSkillList.add(hadTechnicalSkill4);
		HadTechnicalSkill hadTechnicalSkill5 = new HadTechnicalSkill();
		hadTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		hadTechnicalSkillList.add(hadTechnicalSkill5);
		HadTechnicalSkill hadTechnicalSkill6 = new HadTechnicalSkill();
		hadTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		hadTechnicalSkillList.add(hadTechnicalSkill6);
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "a");
		assertEquals(result.get(1).toString(), "b");
		assertEquals(result.get(2).toString(), "c");
		assertEquals(result.get(3).toString(), "d");
		assertEquals(result.get(4).toString(), "e");
		assertEquals(result.get(5).toString(), "f");
		assertEquals(result.get(6).toString(), "");
	}
	
	@Test
	public void ?????????????????????????????????????????????() {
		specsheet.setHadTechnicalSkillList(hadTechnicalSkillList);
		List<StringBuilder> result = service.divideHadTechnicalSkillsForTag(specsheet);
		assertEquals(result.size(), 7);
		assertEquals(result.get(0).toString(), "");
		assertEquals(result.get(1).toString(), "");
		assertEquals(result.get(2).toString(), "");
		assertEquals(result.get(3).toString(), "");
		assertEquals(result.get(4).toString(), "");
		assertEquals(result.get(5).toString(), "");
		assertEquals(result.get(6).toString(), "");
	}
	
//divideUsedTechnicalSkillsForTag????????????
	@Test
	public void ???????????????????????????????????????????????????????????????????????????2????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		DevExperience devExperience2 = new DevExperience();
		devExperience2.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience2);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 2);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		assertEquals(resultDevList.get(1).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
		List<StringBuilder> resultSBList2 = resultDevList.get(1).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList2.get(0).toString(), "a");
		assertEquals(resultSBList2.get(1).toString(), "b");
		assertEquals(resultSBList2.get(2).toString(), "c");
		assertEquals(resultSBList2.get(3).toString(), "d");
		assertEquals(resultSBList2.get(4).toString(), "e");
		assertEquals(resultSBList2.get(5).toString(), "f");
		assertEquals(resultSBList2.get(6).toString(), "g,h");
	}
	
	@Test
	public void ???????????????????????????????????????????????????????????????????????????OS???????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		usedTechnicalSkillList2.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		usedTechnicalSkillList2.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		usedTechnicalSkillList2.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		usedTechnicalSkillList2.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		usedTechnicalSkillList2.add(usedTechnicalSkill6);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		usedTechnicalSkillList2.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		usedTechnicalSkillList2.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		DevExperience devExperience2 = new DevExperience();
		devExperience2.setUsedTechnicalSkillList(usedTechnicalSkillList2);
		devExperienceList.add(devExperience2);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 2);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		assertEquals(resultDevList.get(1).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
		List<StringBuilder> resultSBList2 = resultDevList.get(1).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList2.get(0).toString(), "");
		assertEquals(resultSBList2.get(1).toString(), "b");
		assertEquals(resultSBList2.get(2).toString(), "c");
		assertEquals(resultSBList2.get(3).toString(), "d");
		assertEquals(resultSBList2.get(4).toString(), "e");
		assertEquals(resultSBList2.get(5).toString(), "f");
		assertEquals(resultSBList2.get(6).toString(), "g,h");
	}
	
	@Test
	public void ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		usedTechnicalSkillList2.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList2.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		usedTechnicalSkillList2.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		usedTechnicalSkillList2.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		usedTechnicalSkillList2.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		usedTechnicalSkillList2.add(usedTechnicalSkill6);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		usedTechnicalSkillList2.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		usedTechnicalSkillList2.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		DevExperience devExperience2 = new DevExperience();
		devExperience2.setUsedTechnicalSkillList(usedTechnicalSkillList2);
		devExperienceList.add(devExperience2);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 2);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		assertEquals(resultDevList.get(1).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
		List<StringBuilder> resultSBList2 = resultDevList.get(1).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList2.get(0).toString(), "a");
		assertEquals(resultSBList2.get(1).toString(), "b");
		assertEquals(resultSBList2.get(2).toString(), "c");
		assertEquals(resultSBList2.get(3).toString(), "d");
		assertEquals(resultSBList2.get(4).toString(), "e");
		assertEquals(resultSBList2.get(5).toString(), "f");
		assertEquals(resultSBList2.get(6).toString(), "g,h");
	}
	
	@Test
	public void ?????????????????????????????????????????????????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 1);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
	}
	
	@Test
	public void ???????????????????????????????????????????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 1);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
	}
	
	@Test
	public void ??????????????????????????????????????????????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 1);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
	}
	
	@Test
	public void ?????????????????????????????????????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill7 = new UsedTechnicalSkill();
		usedTechnicalSkill7.setTechnicalSkill(technicalSkill7);
		usedTechnicalSkillList.add(usedTechnicalSkill7);
		UsedTechnicalSkill usedTechnicalSkill8 = new UsedTechnicalSkill();
		usedTechnicalSkill8.setTechnicalSkill(technicalSkill8);
		usedTechnicalSkillList.add(usedTechnicalSkill8);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 1);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "");
		assertEquals(resultSBList1.get(6).toString(), "g,h");
	}
	
	@Test
	public void ??????????????????????????????????????????????????????????????????() {
		UsedTechnicalSkill usedTechnicalSkill1 = new UsedTechnicalSkill();
		usedTechnicalSkill1.setTechnicalSkill(technicalSkill1);
		usedTechnicalSkillList.add(usedTechnicalSkill1);
		UsedTechnicalSkill usedTechnicalSkill2 = new UsedTechnicalSkill();
		usedTechnicalSkill2.setTechnicalSkill(technicalSkill2);
		usedTechnicalSkillList.add(usedTechnicalSkill2);
		UsedTechnicalSkill usedTechnicalSkill3 = new UsedTechnicalSkill();
		usedTechnicalSkill3.setTechnicalSkill(technicalSkill3);
		usedTechnicalSkillList.add(usedTechnicalSkill3);
		UsedTechnicalSkill usedTechnicalSkill4 = new UsedTechnicalSkill();
		usedTechnicalSkill4.setTechnicalSkill(technicalSkill4);
		usedTechnicalSkillList.add(usedTechnicalSkill4);
		UsedTechnicalSkill usedTechnicalSkill5 = new UsedTechnicalSkill();
		usedTechnicalSkill5.setTechnicalSkill(technicalSkill5);
		usedTechnicalSkillList.add(usedTechnicalSkill5);
		UsedTechnicalSkill usedTechnicalSkill6 = new UsedTechnicalSkill();
		usedTechnicalSkill6.setTechnicalSkill(technicalSkill6);
		usedTechnicalSkillList.add(usedTechnicalSkill6);
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 1);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "a");
		assertEquals(resultSBList1.get(1).toString(), "b");
		assertEquals(resultSBList1.get(2).toString(), "c");
		assertEquals(resultSBList1.get(3).toString(), "d");
		assertEquals(resultSBList1.get(4).toString(), "e");
		assertEquals(resultSBList1.get(5).toString(), "f");
		assertEquals(resultSBList1.get(6).toString(), "");
	}
	
	@Test
	public void ????????????????????????????????????????????????????????????() {
		DevExperience devExperience1 = new DevExperience();
		devExperience1.setUsedTechnicalSkillList(usedTechnicalSkillList);
		devExperienceList.add(devExperience1);
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 1);
		assertEquals(resultDevList.get(0).getUsedTechnicalSkillSBList().size(), 7);
		List<StringBuilder> resultSBList1 = resultDevList.get(0).getUsedTechnicalSkillSBList();
		assertEquals(resultSBList1.get(0).toString(), "");
		assertEquals(resultSBList1.get(1).toString(), "");
		assertEquals(resultSBList1.get(2).toString(), "");
		assertEquals(resultSBList1.get(3).toString(), "");
		assertEquals(resultSBList1.get(4).toString(), "");
		assertEquals(resultSBList1.get(5).toString(), "");
		assertEquals(resultSBList1.get(6).toString(), "");
	}
	
	@Test
	public void ???????????????????????????() {
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 0);
	}
	
//trimWhitespaceForEditSpecsheetForm????????????
	@Test
	public void ????????????????????????????????????????????????????????????????????????() {
		form.setGeneration("??? 20?????? ????????? ");
		form.setNearestStation("??? ????????? ?????? ");
		form.setStartBusinessDate("??? ?????? ????????? ");
		form.setAppeal("??? ?????? ???????????? ");
		form.setEffort("??? ?????? ?????? ");
		form.setCertification("??? ?????? ?????? ");
		form.setPreJob("??? ?????? ?????? ");
		List<DevExperienceForm> devFormList = new ArrayList<>();
		DevExperienceForm devForm1 = new DevExperienceForm();
		devForm1.setProjectName("??? ????????? ??????????????? ");
		devForm1.setRole("??? ?????? ?????? ");
		devForm1.setProjectDetails("??? ?????? ?????? ");
		devForm1.setTasks("??? ?????? ????????? ");
		devForm1.setAcquired("??? ?????? ?????? ");
		devFormList.add(devForm1);
		DevExperienceForm devForm2 = new DevExperienceForm();
		devForm2.setProjectName("??? ????????? ??????????????? ");
		devForm2.setRole("??? ?????? ?????? ");
		devForm2.setProjectDetails("??? ?????? ?????? ");
		devForm2.setTasks("??? ?????? ????????? ");
		devForm2.setAcquired("??? ?????? ?????? ");
		devFormList.add(devForm2);
		form.setDevExperienceList(devFormList);
		EditSpecsheetForm result = service.trimWhitespaceForEditSpecsheetForm(form);
		assertEquals(result.getGeneration(), "20??? ??????");
		assertEquals(result.getNearestStation(), "?????? ???");
		assertEquals(result.getStartBusinessDate(), "??? ??????");
		assertEquals(result.getAppeal(), "??? ?????????");
		assertEquals(result.getEffort(), "??? ???");
		assertEquals(result.getCertification(), "??? ???");
		assertEquals(result.getPreJob(), "??? ???");
		List<DevExperienceForm> resultDev = result.getDevExperienceList();
		assertEquals(resultDev.get(0).getProjectName(), "?????? ????????????");
		assertEquals(resultDev.get(0).getRole(), "??? ???");
		assertEquals(resultDev.get(0).getProjectDetails(), "??? ???");
		assertEquals(resultDev.get(0).getTasks(), "??? ??????");
		assertEquals(resultDev.get(0).getAcquired(), "??? ???");
		assertEquals(resultDev.get(1).getProjectName(), "?????? ????????????");
		assertEquals(resultDev.get(1).getRole(), "??? ???");
		assertEquals(resultDev.get(1).getProjectDetails(), "??? ???");
		assertEquals(resultDev.get(1).getTasks(), "??? ??????");
		assertEquals(resultDev.get(1).getAcquired(), "??? ???");
	}
	
	@Test
	public void ?????????????????????????????????????????????() {
		form.setGeneration("??? 20???????????? ");
		form.setNearestStation("?????????");
		form.setStartBusinessDate("?????????");
		form.setAppeal("????????????");
		form.setEffort("??????");
		form.setCertification("??????");
		form.setPreJob("??????");
		List<DevExperienceForm> devFormList = new ArrayList<>();
		DevExperienceForm devForm1 = new DevExperienceForm();
		devForm1.setProjectName("??????????????????");
		devForm1.setRole("??????");
		devForm1.setProjectDetails("??????");
		devForm1.setTasks("?????????");
		devForm1.setAcquired("??????");
		devFormList.add(devForm1);
		DevExperienceForm devForm2 = new DevExperienceForm();
		devForm2.setProjectName("??????????????????");
		devForm2.setRole("??????");
		devForm2.setProjectDetails("??????");
		devForm2.setTasks("?????????");
		devForm2.setAcquired("??????");
		devFormList.add(devForm2);
		form.setDevExperienceList(devFormList);
		EditSpecsheetForm result = service.trimWhitespaceForEditSpecsheetForm(form);
		assertEquals(result.getGeneration(), "20?????????");
		assertEquals(result.getNearestStation(), "?????????");
		assertEquals(result.getStartBusinessDate(), "?????????");
		assertEquals(result.getAppeal(), "????????????");
		assertEquals(result.getEffort(), "??????");
		assertEquals(result.getCertification(), "??????");
		assertEquals(result.getPreJob(), "??????");
		List<DevExperienceForm> resultDev = result.getDevExperienceList();
		assertEquals(resultDev.get(0).getProjectName(), "??????????????????");
		assertEquals(resultDev.get(0).getRole(), "??????");
		assertEquals(resultDev.get(0).getProjectDetails(), "??????");
		assertEquals(resultDev.get(0).getTasks(), "?????????");
		assertEquals(resultDev.get(0).getAcquired(), "??????");
		assertEquals(resultDev.get(1).getProjectName(), "??????????????????");
		assertEquals(resultDev.get(1).getRole(), "??????");
		assertEquals(resultDev.get(1).getProjectDetails(), "??????");
		assertEquals(resultDev.get(1).getTasks(), "?????????");
		assertEquals(resultDev.get(1).getAcquired(), "??????");
	}
	
	@Test
	public void ?????????????????????????????????????????????????????????????????????????????????????????????() {
		form.setGeneration("??? 20?????? ????????? ");
		form.setNearestStation("??? ????????? ?????? ");
		form.setStartBusinessDate("??? ?????? ????????? ");
		form.setAppeal("??? ?????? ???????????? ");
		form.setEffort("??? ?????? ?????? ");
		form.setCertification("??? ?????? ?????? ");
		form.setPreJob("??? ?????? ?????? ");
		List<DevExperienceForm> devFormList = new ArrayList<>();
		form.setDevExperienceList(devFormList);
		EditSpecsheetForm result = service.trimWhitespaceForEditSpecsheetForm(form);
		assertEquals(result.getGeneration(), "20??? ??????");
		assertEquals(result.getNearestStation(), "?????? ???");
		assertEquals(result.getStartBusinessDate(), "??? ??????");
		assertEquals(result.getAppeal(), "??? ?????????");
		assertEquals(result.getEffort(), "??? ???");
		assertEquals(result.getCertification(), "??? ???");
		assertEquals(result.getPreJob(), "??? ???");
		assertEquals(result.getDevExperienceList().size(), 0);
	}

}
