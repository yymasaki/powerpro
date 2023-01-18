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

//devideHadTechnicalSkillsForTagメソッド
	@Test
	public void 全カテゴリーの保有テクニカルスキルがある場合() {
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
	public void OS保有テクニカルスキルがない場合() {
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
	public void 言語保有テクニカルスキルがない場合() {
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
	public void フレームワーク保有テクニカルスキルがない場合() {
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
	public void ライブラリ保有テクニカルスキルがない場合() {
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
	public void ミドルウェア保有テクニカルスキルがない場合() {
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
	public void ツール保有テクニカルスキルがない場合() {
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
	public void 工程保有テクニカルスキルがない場合() {
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
	public void 保有テクニカルスキルがない場合() {
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
	
//divideUsedTechnicalSkillsForTagメソッド
	@Test
	public void 全カテゴリーの使用テクニカルスキルがある開発経験が2つの場合() {
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
	public void 全カテゴリーの使用テクニカルスキルがある開発経験とOSスキルのない開発経験の場合() {
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
	public void 全カテゴリーの使用テクニカルスキルがある開発経験と言語スキルのない開発経験の場合() {
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
	public void フレームワーク使用テクニカルスキルがない開発経験の場合() {
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
	public void ライブラリ使用テクニカルスキルがない開発経験の場合() {
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
	public void ミドルウェア使用テクニカルスキルがない開発経験の場合() {
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
	public void ツール使用テクニカルスキルがない開発経験の場合() {
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
	public void 工程使用テクニカルスキルがない開発経験の場合() {
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
	public void 使用テクニカルスキルがない開発経験の場合() {
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
	public void 開発経験がない場合() {
		specsheet.setDevExperienceList(devExperienceList);
		AppSpecsheet result = service.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> resultDevList = result.getDevExperienceList();
		assertEquals(resultDevList.size(), 0);
	}
	
//trimWhitespaceForEditSpecsheetFormメソッド
	@Test
	public void 全てのデータで前後と中間に連続スペースがある場合() {
		form.setGeneration("　 20代　 前半　 ");
		form.setNearestStation("　 新宿　 駅　 ");
		form.setStartBusinessDate("　 応　 相談　 ");
		form.setAppeal("　 ア　 ピール　 ");
		form.setEffort("　 努　 力　 ");
		form.setCertification("　 資　 格　 ");
		form.setPreJob("　 前　 職　 ");
		List<DevExperienceForm> devFormList = new ArrayList<>();
		DevExperienceForm devForm1 = new DevExperienceForm();
		devForm1.setProjectName("　 プロ　 ジェクト　 ");
		devForm1.setRole("　 役　 割　 ");
		devForm1.setProjectDetails("　 詳　 細　 ");
		devForm1.setTasks("　 タ　 スク　 ");
		devForm1.setAcquired("　 知　 見　 ");
		devFormList.add(devForm1);
		DevExperienceForm devForm2 = new DevExperienceForm();
		devForm2.setProjectName("　 プロ　 ジェクト　 ");
		devForm2.setRole("　 役　 割　 ");
		devForm2.setProjectDetails("　 詳　 細　 ");
		devForm2.setTasks("　 タ　 スク　 ");
		devForm2.setAcquired("　 知　 見　 ");
		devFormList.add(devForm2);
		form.setDevExperienceList(devFormList);
		EditSpecsheetForm result = service.trimWhitespaceForEditSpecsheetForm(form);
		assertEquals(result.getGeneration(), "20代 前半");
		assertEquals(result.getNearestStation(), "新宿 駅");
		assertEquals(result.getStartBusinessDate(), "応 相談");
		assertEquals(result.getAppeal(), "ア ピール");
		assertEquals(result.getEffort(), "努 力");
		assertEquals(result.getCertification(), "資 格");
		assertEquals(result.getPreJob(), "前 職");
		List<DevExperienceForm> resultDev = result.getDevExperienceList();
		assertEquals(resultDev.get(0).getProjectName(), "プロ ジェクト");
		assertEquals(resultDev.get(0).getRole(), "役 割");
		assertEquals(resultDev.get(0).getProjectDetails(), "詳 細");
		assertEquals(resultDev.get(0).getTasks(), "タ スク");
		assertEquals(resultDev.get(0).getAcquired(), "知 見");
		assertEquals(resultDev.get(1).getProjectName(), "プロ ジェクト");
		assertEquals(resultDev.get(1).getRole(), "役 割");
		assertEquals(resultDev.get(1).getProjectDetails(), "詳 細");
		assertEquals(resultDev.get(1).getTasks(), "タ スク");
		assertEquals(resultDev.get(1).getAcquired(), "知 見");
	}
	
	@Test
	public void 年代の前後にスペースがある場合() {
		form.setGeneration("　 20代前半　 ");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setAppeal("アピール");
		form.setEffort("努力");
		form.setCertification("資格");
		form.setPreJob("前職");
		List<DevExperienceForm> devFormList = new ArrayList<>();
		DevExperienceForm devForm1 = new DevExperienceForm();
		devForm1.setProjectName("プロジェクト");
		devForm1.setRole("役割");
		devForm1.setProjectDetails("詳細");
		devForm1.setTasks("タスク");
		devForm1.setAcquired("知見");
		devFormList.add(devForm1);
		DevExperienceForm devForm2 = new DevExperienceForm();
		devForm2.setProjectName("プロジェクト");
		devForm2.setRole("役割");
		devForm2.setProjectDetails("詳細");
		devForm2.setTasks("タスク");
		devForm2.setAcquired("知見");
		devFormList.add(devForm2);
		form.setDevExperienceList(devFormList);
		EditSpecsheetForm result = service.trimWhitespaceForEditSpecsheetForm(form);
		assertEquals(result.getGeneration(), "20代前半");
		assertEquals(result.getNearestStation(), "新宿駅");
		assertEquals(result.getStartBusinessDate(), "応相談");
		assertEquals(result.getAppeal(), "アピール");
		assertEquals(result.getEffort(), "努力");
		assertEquals(result.getCertification(), "資格");
		assertEquals(result.getPreJob(), "前職");
		List<DevExperienceForm> resultDev = result.getDevExperienceList();
		assertEquals(resultDev.get(0).getProjectName(), "プロジェクト");
		assertEquals(resultDev.get(0).getRole(), "役割");
		assertEquals(resultDev.get(0).getProjectDetails(), "詳細");
		assertEquals(resultDev.get(0).getTasks(), "タスク");
		assertEquals(resultDev.get(0).getAcquired(), "知見");
		assertEquals(resultDev.get(1).getProjectName(), "プロジェクト");
		assertEquals(resultDev.get(1).getRole(), "役割");
		assertEquals(resultDev.get(1).getProjectDetails(), "詳細");
		assertEquals(resultDev.get(1).getTasks(), "タスク");
		assertEquals(resultDev.get(1).getAcquired(), "知見");
	}
	
	@Test
	public void 全てのデータで前後と中間に連続スペースがあり開発経験がない場合() {
		form.setGeneration("　 20代　 前半　 ");
		form.setNearestStation("　 新宿　 駅　 ");
		form.setStartBusinessDate("　 応　 相談　 ");
		form.setAppeal("　 ア　 ピール　 ");
		form.setEffort("　 努　 力　 ");
		form.setCertification("　 資　 格　 ");
		form.setPreJob("　 前　 職　 ");
		List<DevExperienceForm> devFormList = new ArrayList<>();
		form.setDevExperienceList(devFormList);
		EditSpecsheetForm result = service.trimWhitespaceForEditSpecsheetForm(form);
		assertEquals(result.getGeneration(), "20代 前半");
		assertEquals(result.getNearestStation(), "新宿 駅");
		assertEquals(result.getStartBusinessDate(), "応 相談");
		assertEquals(result.getAppeal(), "ア ピール");
		assertEquals(result.getEffort(), "努 力");
		assertEquals(result.getCertification(), "資 格");
		assertEquals(result.getPreJob(), "前 職");
		assertEquals(result.getDevExperienceList().size(), 0);
	}

}
