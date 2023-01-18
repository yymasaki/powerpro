package com.example.service.spec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import com.example.domain.AppSpecsheet;
import com.example.domain.Department;
import com.example.domain.DevExperience;
import com.example.domain.User;
import com.example.service.spec.util.SpecStringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateSpecsheetXlsxServiceTest {
	
	private AppSpecsheet specsheet = new AppSpecsheet();
	private User user = new User();
	private Department department = new Department();
	private List<DevExperience> devExperienceList = new ArrayList<>();
	private DevExperience devExperience1 = new DevExperience();
	private DevExperience devExperience2 = new DevExperience();
	private DevExperience devExperience3 = new DevExperience();
	private List<StringBuilder> usedTechnicalSkillSBList = new ArrayList<>();
	private List<StringBuilder> htStringBuilderList = new ArrayList<>();
	private StringBuilder skillStringBuilder1 = new StringBuilder("a,b");
	private StringBuilder skillStringBuilder2 = new StringBuilder("c,d");
	private StringBuilder skillStringBuilder3 = new StringBuilder("e,f");
	private StringBuilder skillStringBuilder4 = new StringBuilder("g,h");
	private StringBuilder skillStringBuilder5 = new StringBuilder("i,j");
	private StringBuilder skillStringBuilder6 = new StringBuilder("k,l");
	private StringBuilder skillStringBuilder7 = new StringBuilder("m,n");
	
	@Mock
	private SpecStringUtils SpecStringUtils;
	
	@InjectMocks
	private CreateSpecsheetXlsxService service;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		specsheet.setEmployeeId(1111);
		specsheet.setGeneration("20代前半");
		specsheet.setGender('M');
		specsheet.setNearestStation("横浜駅");
		specsheet.setStartBusinessDate("応相談");
		specsheet.setEngineerPeriod(10);
		specsheet.setSePeriod(3);
		specsheet.setPgPeriod(7);
		specsheet.setItPeriod(15);
		specsheet.setAppeal("アピール");
		specsheet.setEffort("エフォート");
		specsheet.setCertification("資格");
		specsheet.setPreJob("前職");
		
		department.setStaffId("AP-202");
		user.setDepartment(department);
		specsheet.setUser(user);
		
		devExperience1.setStartedOn(LocalDate.of(2020, 7, 1));
		devExperience1.setFinishedOn(LocalDate.of(2020, 7, 1));
		devExperience1.setProjectName("テストプロジェクト");
		devExperience1.setRole("役割");
		devExperience1.setTeamMembers(6);
		devExperience1.setProjectMembers(12);
		devExperience1.setProjectDetails("詳細");
		devExperience1.setTasks("タスク");
		devExperience1.setAcquired("知見");
		usedTechnicalSkillSBList.add(skillStringBuilder1);
		usedTechnicalSkillSBList.add(skillStringBuilder2);
		usedTechnicalSkillSBList.add(skillStringBuilder3);
		usedTechnicalSkillSBList.add(skillStringBuilder4);
		usedTechnicalSkillSBList.add(skillStringBuilder5);
		usedTechnicalSkillSBList.add(skillStringBuilder6);
		usedTechnicalSkillSBList.add(skillStringBuilder7);
		devExperience1.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience1);
		
		devExperience2.setStartedOn(LocalDate.of(2020, 5, 1));
		devExperience2.setFinishedOn(LocalDate.of(2020, 6, 1));
		devExperience2.setProjectName("テストプロジェクト");
		devExperience2.setRole("役割");
		devExperience2.setTeamMembers(6);
		devExperience2.setProjectMembers(12);
		devExperience2.setProjectDetails("詳細");
		devExperience2.setTasks("タスク");
		devExperience2.setAcquired("知見");
		devExperience2.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience2);
		
		devExperience3.setStartedOn(LocalDate.of(2020, 5, 1));
		devExperience3.setFinishedOn(LocalDate.of(2020, 5, 1));
		devExperience3.setProjectName("テストプロジェクト");
		devExperience3.setRole("役割");
		devExperience3.setTeamMembers(6);
		devExperience3.setProjectMembers(12);
		devExperience3.setProjectDetails("詳細");
		devExperience3.setTasks("タスク");
		devExperience3.setAcquired("知見");
		devExperience3.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience3);
		
		specsheet.setDevExperienceList(devExperienceList);
		
		htStringBuilderList.add(skillStringBuilder1);
		htStringBuilderList.add(skillStringBuilder2);
		htStringBuilderList.add(skillStringBuilder3);
		htStringBuilderList.add(skillStringBuilder4);
		htStringBuilderList.add(skillStringBuilder5);
		htStringBuilderList.add(skillStringBuilder6);
		htStringBuilderList.add(skillStringBuilder7);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void 開発経験が3つの時正常にワークブックを返す() throws Exception {
		when(SpecStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(SpecStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		Workbook workbook = service.createXlsx(specsheet);
		Sheet sheet = workbook.getSheet("スペックシート");
		
		//基本情報
		assertEquals(getCell(sheet, 3, getColumnIndex("G")).getStringCellValue(), 
				specsheet.getStaffIdForSpec());
		assertEquals(getCell(sheet, 3, getColumnIndex("R")).getStringCellValue(), 
				specsheet.getGeneration());
		assertEquals(getCell(sheet, 3, getColumnIndex("AA")).getStringCellValue(), 
				specsheet.getGenderName());
		assertEquals(getCell(sheet, 3, getColumnIndex("AH")).getStringCellValue(), 
				specsheet.getNearestStation());
		assertEquals(getCell(sheet, 3, getColumnIndex("AW")).getStringCellValue(), 
				specsheet.getStartBusinessDate());
		assertEquals(getCell(sheet, 4, getColumnIndex("G")).getStringCellValue(), 
				specsheet.getPeriod(specsheet.getEngineerPeriod()));
		assertEquals(getCell(sheet, 4, getColumnIndex("AA")).getStringCellValue(), 
				specsheet.getPeriod(specsheet.getSePeriod()));
		assertEquals(getCell(sheet, 5, getColumnIndex("AA")).getStringCellValue(), 
				specsheet.getPeriod(specsheet.getPgPeriod()));
		assertEquals(getCell(sheet, 4, getColumnIndex("AR")).getStringCellValue(), 
				specsheet.getPeriod(specsheet.getItPeriod()));
		
		//スキル要約
		assertEquals(getCell(sheet, 8, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 9, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 10, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 11, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 12, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 13, getColumnIndex("K")).getStringCellValue(), 
				"k, l");
		assertEquals(getCell(sheet, 14, getColumnIndex("K")).getStringCellValue(), 
				"m、n");
		
		//アピールポイント等
		assertEquals(getCell(sheet, 17, getColumnIndex("B")).getStringCellValue(), 
				specsheet.getAppeal());
		assertEquals(getCell(sheet, 19, getColumnIndex("B")).getStringCellValue(), 
				specsheet.getEffort());
		assertEquals(getCell(sheet, 21, getColumnIndex("B")).getStringCellValue(), 
				specsheet.getCertification());
		assertEquals(getCell(sheet, 23, getColumnIndex("B")).getStringCellValue(), 
				specsheet.getPreJob());
		
		//開発経験
		assertEquals(getCell(sheet, 25, getColumnIndex("G")).getDateCellValue(), 
				Date.valueOf(devExperience1.getStartedOn()));
		assertEquals(getCell(sheet, 26, getColumnIndex("G")).getStringCellValue(), 
				devExperience1.getProjectPeriod());
		assertEquals(getCell(sheet, 25, getColumnIndex("T")).getStringCellValue(), 
				devExperience1.getProjectName());
		assertEquals(getCell(sheet, 25, getColumnIndex("AO")).getStringCellValue(), 
				devExperience1.getRole());
		assertEquals(Integer.valueOf((int) (getCell(sheet, 25, getColumnIndex("AZ")).getNumericCellValue())),
				devExperience1.getTeamMembers());
		assertEquals(Integer.valueOf((int) (getCell(sheet, 26, getColumnIndex("AZ")).getNumericCellValue())),
				devExperience1.getProjectMembers());

		//使用テクニカルスキル
		assertEquals(getCell(sheet, 27, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 27, getColumnIndex("AO")).getStringCellValue(), 
				"m、n");
		assertEquals(getCell(sheet, 28, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 29, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 30, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 31, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 32, getColumnIndex("K")).getStringCellValue(), 
				"k, l");

		//プロジェクト詳細等
		assertEquals(getCell(sheet, 34, getColumnIndex("D")).getStringCellValue(), 
				devExperience1.getProjectDetails());
		assertEquals(getCell(sheet, 36, getColumnIndex("D")).getStringCellValue(), 
				devExperience1.getTasks());
		assertEquals(getCell(sheet, 38, getColumnIndex("D")).getStringCellValue(), 
				devExperience1.getAcquired());
		
		//開発経験2
		assertEquals(getCell(sheet, 39, getColumnIndex("G")).getDateCellValue(), 
				Date.valueOf(devExperience2.getStartedOn()));
		assertEquals(getCell(sheet, 40, getColumnIndex("G")).getStringCellValue(), 
				devExperience2.getProjectPeriod());
		assertEquals(getCell(sheet, 39, getColumnIndex("T")).getStringCellValue(), 
				devExperience2.getProjectName());
		assertEquals(getCell(sheet, 39, getColumnIndex("AO")).getStringCellValue(), 
				devExperience2.getRole());
		assertEquals(Integer.valueOf((int) getCell(sheet, 39, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience2.getTeamMembers());
		assertEquals(Integer.valueOf((int) getCell(sheet, 40, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience2.getProjectMembers());
		
		//使用テクニカルスキル
		assertEquals(getCell(sheet, 41, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 41, getColumnIndex("AO")).getStringCellValue(), 
				"m、n");
		assertEquals(getCell(sheet, 42, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 43, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 44, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 45, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 46, getColumnIndex("K")).getStringCellValue(), 
				"k, l");
		
		//プロジェクト詳細等
		assertEquals(getCell(sheet, 48, getColumnIndex("D")).getStringCellValue(), 
				devExperience2.getProjectDetails());
		assertEquals(getCell(sheet, 50, getColumnIndex("D")).getStringCellValue(), 
				devExperience2.getTasks());
		assertEquals(getCell(sheet, 52, getColumnIndex("D")).getStringCellValue(), 
				devExperience2.getAcquired());
		
		//開発経験3
		assertEquals(getCell(sheet, 53, getColumnIndex("G")).getDateCellValue(), 
				Date.valueOf(devExperience3.getStartedOn()));
		assertEquals(getCell(sheet, 54, getColumnIndex("G")).getStringCellValue(), 
				devExperience3.getProjectPeriod());
		assertEquals(getCell(sheet, 53, getColumnIndex("T")).getStringCellValue(), 
				devExperience3.getProjectName());
		assertEquals(getCell(sheet, 53, getColumnIndex("AO")).getStringCellValue(), 
				devExperience3.getRole());
		assertEquals(Integer.valueOf((int) getCell(sheet, 53, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience3.getTeamMembers());
		assertEquals(Integer.valueOf((int) getCell(sheet, 54, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience3.getProjectMembers());

		//使用テクニカルスキル
		assertEquals(getCell(sheet, 55, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 55, getColumnIndex("AO")).getStringCellValue(), 
				"m、n");
		assertEquals(getCell(sheet, 56, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 57, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 58, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 59, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 60, getColumnIndex("K")).getStringCellValue(), 
				"k, l");
		
		//プロジェクト詳細等
		assertEquals(getCell(sheet, 62, getColumnIndex("D")).getStringCellValue(), 
				devExperience3.getProjectDetails());
		assertEquals(getCell(sheet, 64, getColumnIndex("D")).getStringCellValue(), 
				devExperience3.getTasks());
		assertEquals(getCell(sheet, 66, getColumnIndex("D")).getStringCellValue(), 
				devExperience3.getAcquired());
		
		int[] rowBreaks = sheet.getRowBreaks();
		assertEquals(rowBreaks[0], 23);
		assertEquals(rowBreaks[1], 52);
		
		assertEquals(workbook.getPrintArea(0), "スペックシート!$B$1:$BB$67");
		
		verify(SpecStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(SpecStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
	}
	
	@Test
	public void 開発経験が10個の時正常にワークブックを返す() throws Exception {
		DevExperience devExperience4 = new DevExperience();
		devExperience4.setStartedOn(LocalDate.of(2020, 4, 1));
		devExperience4.setFinishedOn(LocalDate.of(2020, 4, 1));
		devExperience4.setProjectName("テストプロジェクト");
		devExperience4.setRole("役割");
		devExperience4.setTeamMembers(6);
		devExperience4.setProjectMembers(12);
		devExperience4.setProjectDetails("詳細");
		devExperience4.setTasks("タスク");
		devExperience4.setAcquired("知見");
		devExperience4.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience4);
		
		DevExperience devExperience5 = new DevExperience();
		devExperience5.setStartedOn(LocalDate.of(2020, 3, 1));
		devExperience5.setFinishedOn(LocalDate.of(2020, 3, 1));
		devExperience5.setProjectName("テストプロジェクト");
		devExperience5.setRole("役割");
		devExperience5.setTeamMembers(6);
		devExperience5.setProjectMembers(12);
		devExperience5.setProjectDetails("詳細");
		devExperience5.setTasks("タスク");
		devExperience5.setAcquired("知見");
		devExperience5.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience5);
		
		DevExperience devExperience6 = new DevExperience();
		devExperience6.setStartedOn(LocalDate.of(2020, 2, 1));
		devExperience6.setFinishedOn(LocalDate.of(2020, 2, 1));
		devExperience6.setProjectName("テストプロジェクト");
		devExperience6.setRole("役割");
		devExperience6.setTeamMembers(6);
		devExperience6.setProjectMembers(12);
		devExperience6.setProjectDetails("詳細");
		devExperience6.setTasks("タスク");
		devExperience6.setAcquired("知見");
		devExperience6.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience6);
		
		DevExperience devExperience7 = new DevExperience();
		devExperience7.setStartedOn(LocalDate.of(2020, 1, 1));
		devExperience7.setFinishedOn(LocalDate.of(2020, 1, 1));
		devExperience7.setProjectName("テストプロジェクト");
		devExperience7.setRole("役割");
		devExperience7.setTeamMembers(6);
		devExperience7.setProjectMembers(12);
		devExperience7.setProjectDetails("詳細");
		devExperience7.setTasks("タスク");
		devExperience7.setAcquired("知見");
		devExperience7.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience7);
		
		DevExperience devExperience8 = new DevExperience();
		devExperience8.setStartedOn(LocalDate.of(2019, 12, 1));
		devExperience8.setFinishedOn(LocalDate.of(2019, 12, 1));
		devExperience8.setProjectName("テストプロジェクト");
		devExperience8.setRole("役割");
		devExperience8.setTeamMembers(6);
		devExperience8.setProjectMembers(12);
		devExperience8.setProjectDetails("詳細");
		devExperience8.setTasks("タスク");
		devExperience8.setAcquired("知見");
		devExperience8.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience8);
		
		DevExperience devExperience9 = new DevExperience();
		devExperience9.setStartedOn(LocalDate.of(2019, 11, 1));
		devExperience9.setFinishedOn(LocalDate.of(2019, 11, 1));
		devExperience9.setProjectName("テストプロジェクト");
		devExperience9.setRole("役割");
		devExperience9.setTeamMembers(6);
		devExperience9.setProjectMembers(12);
		devExperience9.setProjectDetails("詳細");
		devExperience9.setTasks("タスク");
		devExperience9.setAcquired("知見");
		devExperience9.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience9);
		
		DevExperience devExperience10 = new DevExperience();
		devExperience10.setStartedOn(LocalDate.of(2019, 10, 1));
		devExperience10.setFinishedOn(LocalDate.of(2019, 10, 1));
		devExperience10.setProjectName("テストプロジェクト");
		devExperience10.setRole("役割");
		devExperience10.setTeamMembers(6);
		devExperience10.setProjectMembers(12);
		devExperience10.setProjectDetails("詳細");
		devExperience10.setTasks("タスク");
		devExperience10.setAcquired("知見");
		devExperience10.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
		devExperienceList.add(devExperience10);
		
		when(SpecStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(SpecStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		Workbook workbook = service.createXlsx(specsheet);
		Sheet sheet = workbook.getSheet("スペックシート");
		
		//開発経験4
		assertEquals(getCell(sheet, 67, getColumnIndex("G")).getDateCellValue(), 
				Date.valueOf(devExperience4.getStartedOn()));
		assertEquals(getCell(sheet, 68, getColumnIndex("G")).getStringCellValue(), 
				devExperience4.getProjectPeriod());
		assertEquals(getCell(sheet, 67, getColumnIndex("T")).getStringCellValue(), 
				devExperience4.getProjectName());
		assertEquals(getCell(sheet, 67, getColumnIndex("AO")).getStringCellValue(), 
				devExperience4.getRole());
		assertEquals(Integer.valueOf((int) getCell(sheet, 67, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience4.getTeamMembers());
		assertEquals(Integer.valueOf((int) getCell(sheet, 68, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience4.getProjectMembers());
		
		//使用テクニカルスキル4
		assertEquals(getCell(sheet, 69, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 69, getColumnIndex("AO")).getStringCellValue(), 
				"m、n");
		assertEquals(getCell(sheet, 70, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 71, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 72, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 73, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 74, getColumnIndex("K")).getStringCellValue(), 
				"k, l");
		
		//プロジェクト詳細等4
		assertEquals(getCell(sheet, 76, getColumnIndex("D")).getStringCellValue(), 
				devExperience4.getProjectDetails());
		assertEquals(getCell(sheet, 78, getColumnIndex("D")).getStringCellValue(), 
				devExperience4.getTasks());
		assertEquals(getCell(sheet, 80, getColumnIndex("D")).getStringCellValue(), 
				devExperience4.getAcquired());
		
		//開発経験5
		assertEquals(getCell(sheet, 81, getColumnIndex("G")).getDateCellValue(), 
				Date.valueOf(devExperience5.getStartedOn()));
		assertEquals(getCell(sheet, 82, getColumnIndex("G")).getStringCellValue(), 
				devExperience5.getProjectPeriod());
		assertEquals(getCell(sheet, 81, getColumnIndex("T")).getStringCellValue(), 
				devExperience5.getProjectName());
		assertEquals(getCell(sheet, 81, getColumnIndex("AO")).getStringCellValue(), 
				devExperience5.getRole());
		assertEquals(Integer.valueOf((int) getCell(sheet, 81, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience5.getTeamMembers());
		assertEquals(Integer.valueOf((int) getCell(sheet, 82, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience5.getProjectMembers());
		
		//使用テクニカルスキル5
		assertEquals(getCell(sheet, 83, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 83, getColumnIndex("AO")).getStringCellValue(), 
				"m、n");
		assertEquals(getCell(sheet, 84, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 85, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 86, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 87, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 88, getColumnIndex("K")).getStringCellValue(), 
				"k, l");
		
		//プロジェクト詳細等5
		assertEquals(getCell(sheet, 90, getColumnIndex("D")).getStringCellValue(), 
				devExperience5.getProjectDetails());
		assertEquals(getCell(sheet, 92, getColumnIndex("D")).getStringCellValue(), 
				devExperience5.getTasks());
		assertEquals(getCell(sheet, 94, getColumnIndex("D")).getStringCellValue(), 
				devExperience5.getAcquired());
		
		//開発経験10
		assertEquals(getCell(sheet, 151, getColumnIndex("G")).getDateCellValue(), 
				Date.valueOf(devExperience10.getStartedOn()));
		assertEquals(getCell(sheet, 152, getColumnIndex("G")).getStringCellValue(), 
				devExperience10.getProjectPeriod());
		assertEquals(getCell(sheet, 151, getColumnIndex("T")).getStringCellValue(), 
				devExperience10.getProjectName());
		assertEquals(getCell(sheet, 151, getColumnIndex("AO")).getStringCellValue(), 
				devExperience10.getRole());
		assertEquals(Integer.valueOf((int) getCell(sheet, 151, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience10.getTeamMembers());
		assertEquals(Integer.valueOf((int) getCell(sheet, 152, getColumnIndex("AZ")).getNumericCellValue()),
				devExperience10.getProjectMembers());
		
		//使用テクニカルスキル10
		assertEquals(getCell(sheet, 153, getColumnIndex("K")).getStringCellValue(), 
				"a, b");
		assertEquals(getCell(sheet, 153, getColumnIndex("AO")).getStringCellValue(), 
				"m、n");
		assertEquals(getCell(sheet, 154, getColumnIndex("K")).getStringCellValue(), 
				"c, d");
		assertEquals(getCell(sheet, 155, getColumnIndex("K")).getStringCellValue(), 
				"e, f");
		assertEquals(getCell(sheet, 156, getColumnIndex("K")).getStringCellValue(), 
				"g, h");
		assertEquals(getCell(sheet, 157, getColumnIndex("K")).getStringCellValue(), 
				"i, j");
		assertEquals(getCell(sheet, 158, getColumnIndex("K")).getStringCellValue(), 
				"k, l");
		
		//プロジェクト詳細等10
		assertEquals(getCell(sheet, 160, getColumnIndex("D")).getStringCellValue(), 
				devExperience10.getProjectDetails());
		assertEquals(getCell(sheet, 162, getColumnIndex("D")).getStringCellValue(), 
				devExperience10.getTasks());
		assertEquals(getCell(sheet, 164, getColumnIndex("D")).getStringCellValue(), 
				devExperience10.getAcquired());
		
		int[] rowBreaks = sheet.getRowBreaks();
		assertEquals(rowBreaks[0], 23);
		assertEquals(rowBreaks[1], 52);
		assertEquals(rowBreaks[2], 80);
		assertEquals(rowBreaks[3], 108);
		assertEquals(rowBreaks[4], 136);
		
		assertEquals(workbook.getPrintArea(0), "スペックシート!$B$1:$BB$165");
		
		verify(SpecStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
		verify(SpecStringUtils, times(1)).divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class));
	}
	
	@Test
	public void 開発経験がない場合にnullを返す() throws Exception {
		List<DevExperience> devExperienceList2 = new ArrayList<>();
		specsheet.setDevExperienceList(devExperienceList2);
		
		when(SpecStringUtils.divideHadTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(htStringBuilderList);
		when(SpecStringUtils.divideUsedTechnicalSkillsForTag(any(AppSpecsheet.class))).thenReturn(specsheet);
		Workbook workbook = service.createXlsx(specsheet);
		
		assertNull(workbook);
		verify(SpecStringUtils, times(1)).divideHadTechnicalSkillsForTag(any(AppSpecsheet.class));
	}
	
	/**
     * <p>
     * 引数で指定されたシートの、行番号、列番号で指定したセルを取得して返却する
     * <p>
     * 行番号、列番号は0から開始する
     * <p>
     * Excelテンプレートで該当のセルを操作していない場合、NullPointerExceptionになる
     * @param sheet シート
     * @param rowIndex 行番号
     * @param colIndex 列番号
     * @return セル
     */
    private Cell getCell(Sheet sheet, int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        return row.getCell(colIndex);
    }
	
	/**
	 * 指定した列を表す文字のインデックスを返すメソッド.
	 * 
	 * @param columnAlphabet 列を表す文字
	 * @return 列番号
	 */
	private int getColumnIndex(String columnAlphabet) {
		final String alphabets = "abcdefghijklmnopqrstuvwxyz";
		int columnIndex = 0;
		if(columnAlphabet.length() == 1) {
			columnIndex = alphabets.indexOf(columnAlphabet.toLowerCase());
		}else {
			columnIndex = (alphabets.indexOf(columnAlphabet.toLowerCase().charAt(0)) + 1) * 26 
					+ alphabets.indexOf(columnAlphabet.toLowerCase().charAt(1));
		}
		return columnIndex;
	}

}
