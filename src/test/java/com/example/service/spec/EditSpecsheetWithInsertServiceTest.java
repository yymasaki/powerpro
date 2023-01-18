package com.example.service.spec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.common.Category;
import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.example.HadTechnicalSkillExample;
import com.example.example.TechnicalSkillExample;
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;
import com.example.service.technique.AddHadTechnicalSkillService;
import com.example.service.technique.AddTechnicalSkillListService;
import com.example.service.technique.DeleteHadTechnicalSkillService;
import com.example.service.technique.GetHadTechnicalSkillListService;
import com.example.service.technique.GetTechnicalSkillListService;
import com.example.service.technique.UpdateHadTechnicalSkillStageService;
import com.example.service.technique.UpdateTechnicalSkillService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditSpecsheetWithInsertServiceTest {
	
	private EditSpecsheetForm form = new EditSpecsheetForm();
	private String[] hadTechnicalSkillList = new String[7];
	private List<DevExperienceForm> devExperienceFormList = new ArrayList<>();
	private DevExperienceForm devExperienceForm1 = new DevExperienceForm();
	private List<String> utStringList1 = new ArrayList<>();
	private List<List<String>> usedTechnicalSkillList = new ArrayList<>();
	
	private User user = new User();
	private Collection<GrantedAuthority> authorityList = new ArrayList<>();
	private LoginUser loginUser = null;
	private AppSpecsheet newestSpecsheet = new AppSpecsheet();
	private AppSpecsheet specsheet = new AppSpecsheet();
	
	private List<DevExperience> insertedDevExperienceList = new ArrayList<>();
	private DevExperience insertedDevExperience = new DevExperience();
	
	private List<TechnicalSkill> existingTechnicalSkillList = new ArrayList<>();
	private List<TechnicalSkill> insertedTechnicalSkillList  = new ArrayList<>();
	
	private List<HadTechnicalSkill> existingHadTechnicalSkillList = new ArrayList<>();
	
	@Mock
	private GetNewestSpecsheetService getNewestSpecsheetService;
	@Mock
	private AddSpecsheetService addSpecsheetService;
	@Mock
	private AddDevExperienceService addDevExperienceService;
	@Mock
	private GetTechnicalSkillListService getTechnicalSkillListService;
	@Mock
	private AddTechnicalSkillListService addTechnicalSkillListService;
	@Mock
	private UpdateTechnicalSkillService updateTechnicalSkillService;
	@Mock
	private GetHadTechnicalSkillListService getHadTechnicalSkillListService;
	@Mock
	private AddHadTechnicalSkillService addHadTechnicalSkillService;
	@Mock
	private UpdateHadTechnicalSkillStageService updateHadTechnicalSkillStageService;
	@Mock
	private DeleteHadTechnicalSkillService deleteHadTechnicalSkillService;
	@Mock
	private AddUsedTechnicalSkillService addUsedTechnicalSkillService;
	
	@InjectMocks
	private EditSpecsheetWithInsertService service;

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
	
	/**
	 * 管理者がACTIVEとして登録<br>
	 * 事前に登録された情報がない新規登録<br>
	 * 開発経験以下は全ての処理を実行
	 */
	@Test
	public void TC1() {
		
	//prepare
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(15);
		form.setAppeal("アピール");
		form.setEffort("エフォート");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.ACTIVE.getKey());

		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		String creatorAndUpdater = user.getName();
		LocalDateTime createdAndUpdatedAt = LocalDateTime.of(2020, 7, 7, 11, 11, 11);
		
	//specsheet
		specsheet.setSpecsheetId(1);
		
		when(addSpecsheetService.addSpecsheet(any(AppSpecsheet.class))).thenReturn(specsheet);
		
	//devEcperience
		devExperienceForm1.setProjectName("project1");
		devExperienceForm1.setTeamMembers(3);
		devExperienceForm1.setProjectMembers(6);
		devExperienceForm1.setRole("役割");
		devExperienceForm1.setProjectDetails("詳細");
		devExperienceForm1.setTasks("タスク");
		devExperienceForm1.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm1);
		form.setDevExperienceList(devExperienceFormList);
		
		insertedDevExperience.setDevExperienceId(1);
		insertedDevExperienceList.add(insertedDevExperience);
		
		when(addDevExperienceService.addDevExperienceList(any())).thenReturn(insertedDevExperienceList);
		
	//technicalSkill
		hadTechnicalSkillList[0] = "A,b";
		hadTechnicalSkillList[1] = "g,h";
		hadTechnicalSkillList[2] = "m,n";
		hadTechnicalSkillList[3] = "s,t";
		hadTechnicalSkillList[4] = "y,Z";
		hadTechnicalSkillList[5] = "e,F";
		hadTechnicalSkillList[6] = "K,l";
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		
		utStringList1.add("A,c");
		utStringList1.add("g,i");
		utStringList1.add("m,o");
		utStringList1.add("s,u");
		utStringList1.add("y,A");
		utStringList1.add("e,g");
		utStringList1.add("K,m");
		usedTechnicalSkillList.add(utStringList1);
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("A");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill1.setStage(Stage.REQUESTING.getKey());
		technicalSkill1.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setTechnicalSkillId(2);
		technicalSkill2.setName("f");
		technicalSkill2.setCategory(Category.OS.getKey());
		technicalSkill2.setStage(Stage.ACTIVE.getKey());
		technicalSkill2.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setTechnicalSkillId(3);
		technicalSkill3.setName("g");
		technicalSkill3.setCategory(Category.LANGUAGE.getKey());
		technicalSkill3.setStage(Stage.REQUESTING.getKey());
		technicalSkill3.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setTechnicalSkillId(4);
		technicalSkill4.setName("l");
		technicalSkill4.setCategory(Category.LANGUAGE.getKey());
		technicalSkill4.setStage(Stage.ACTIVE.getKey());
		technicalSkill4.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setTechnicalSkillId(5);
		technicalSkill5.setName("m");
		technicalSkill5.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill5.setStage(Stage.ACTIVE.getKey());
		technicalSkill5.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setTechnicalSkillId(6);
		technicalSkill6.setName("r");
		technicalSkill6.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill6.setStage(Stage.ACTIVE.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("s");
		technicalSkill7.setCategory(Category.LIBRARY.getKey());
		technicalSkill7.setStage(Stage.ACTIVE.getKey());
		technicalSkill7.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setTechnicalSkillId(8);
		technicalSkill8.setName("x");
		technicalSkill8.setCategory(Category.LIBRARY.getKey());
		technicalSkill8.setStage(Stage.ACTIVE.getKey());
		technicalSkill8.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill8);
		TechnicalSkill technicalSkill9 = new TechnicalSkill();
		technicalSkill9.setTechnicalSkillId(9);
		technicalSkill9.setName("y");
		technicalSkill9.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill9.setStage(Stage.ACTIVE.getKey());
		technicalSkill9.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill9);
		TechnicalSkill technicalSkill10 = new TechnicalSkill();
		technicalSkill10.setTechnicalSkillId(10);
		technicalSkill10.setName("d");
		technicalSkill10.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill10.setStage(Stage.ACTIVE.getKey());
		technicalSkill10.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill10);
		TechnicalSkill technicalSkill11 = new TechnicalSkill();
		technicalSkill11.setTechnicalSkillId(11);
		technicalSkill11.setName("e");
		technicalSkill11.setCategory(Category.TOOL.getKey());
		technicalSkill11.setStage(Stage.ACTIVE.getKey());
		technicalSkill11.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill11);
		TechnicalSkill technicalSkill12 = new TechnicalSkill();
		technicalSkill12.setTechnicalSkillId(12);
		technicalSkill12.setName("j");
		technicalSkill12.setCategory(Category.TOOL.getKey());
		technicalSkill12.setStage(Stage.ACTIVE.getKey());
		technicalSkill12.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill12);
		TechnicalSkill technicalSkill13 = new TechnicalSkill();
		technicalSkill13.setTechnicalSkillId(13);
		technicalSkill13.setName("K");
		technicalSkill13.setCategory(Category.PROCESS.getKey());
		technicalSkill13.setStage(Stage.ACTIVE.getKey());
		technicalSkill13.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill13);
		TechnicalSkill technicalSkill14 = new TechnicalSkill();
		technicalSkill14.setTechnicalSkillId(14);
		technicalSkill14.setName("p");
		technicalSkill14.setCategory(Category.PROCESS.getKey());
		technicalSkill14.setStage(Stage.ACTIVE.getKey());
		technicalSkill14.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill14);
		
		TechnicalSkill insertedTechnicalSkill1 = new TechnicalSkill(
				15, "b", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill1);
		TechnicalSkill insertedTechnicalSkill2 = new TechnicalSkill(
				16, "h", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill2);
		TechnicalSkill insertedTechnicalSkill3 = new TechnicalSkill(
				17, "n", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill3);
		TechnicalSkill insertedTechnicalSkill4 = new TechnicalSkill(
				18, "t", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill4);
		TechnicalSkill insertedTechnicalSkill5 = new TechnicalSkill(
				19, "Z", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill5);
		TechnicalSkill insertedTechnicalSkill6 = new TechnicalSkill(
				20, "f", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill6);
		TechnicalSkill insertedTechnicalSkill7 = new TechnicalSkill(
				21, "l", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill7);
		TechnicalSkill insertedTechnicalSkill8 = new TechnicalSkill(
				22, "c", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill8);
		TechnicalSkill insertedTechnicalSkill9 = new TechnicalSkill(
				23, "i", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill9);
		TechnicalSkill insertedTechnicalSkill10 = new TechnicalSkill(
				24, "o", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill10);
		TechnicalSkill insertedTechnicalSkill11 = new TechnicalSkill(
				25, "u", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill11);
		TechnicalSkill insertedTechnicalSkill12 = new TechnicalSkill(
				26, "A", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill12);
		TechnicalSkill insertedTechnicalSkill13 = new TechnicalSkill(
				27, "g", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill13);
		TechnicalSkill insertedTechnicalSkill14 = new TechnicalSkill(
				28, "m", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill14);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
			.thenReturn(existingTechnicalSkillList);
		when(addTechnicalSkillListService.addTechnicalSkillList(any())).thenReturn(insertedTechnicalSkillList);
		
	//hadTechnicalSkill
		HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
		existingHadTechnicalSkill1.setTechnicalSkillId(1);
		existingHadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
		HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
		existingHadTechnicalSkill2.setTechnicalSkillId(2);
		existingHadTechnicalSkill2.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
		HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
		existingHadTechnicalSkill3.setTechnicalSkillId(3);
		existingHadTechnicalSkill3.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
		HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
		existingHadTechnicalSkill4.setTechnicalSkillId(4);
		existingHadTechnicalSkill4.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
		HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
		existingHadTechnicalSkill5.setTechnicalSkillId(5);
		existingHadTechnicalSkill5.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
		HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
		existingHadTechnicalSkill6.setTechnicalSkillId(6);
		existingHadTechnicalSkill6.setStage(Stage.TEMPORARY.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
		HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
		existingHadTechnicalSkill7.setTechnicalSkillId(7);
		existingHadTechnicalSkill7.setStage(Stage.TEMPORARY.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
		HadTechnicalSkill existingHadTechnicalSkill8 = new HadTechnicalSkill();
		existingHadTechnicalSkill8.setTechnicalSkillId(8);
		existingHadTechnicalSkill8.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill8);
		HadTechnicalSkill existingHadTechnicalSkill9 = new HadTechnicalSkill();
		existingHadTechnicalSkill9.setTechnicalSkillId(9);
		existingHadTechnicalSkill9.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill9);
		HadTechnicalSkill existingHadTechnicalSkill10 = new HadTechnicalSkill();
		existingHadTechnicalSkill10.setTechnicalSkillId(10);
		existingHadTechnicalSkill10.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill10);
		HadTechnicalSkill existingHadTechnicalSkill11 = new HadTechnicalSkill();
		existingHadTechnicalSkill11.setTechnicalSkillId(11);
		existingHadTechnicalSkill11.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill11);
		HadTechnicalSkill existingHadTechnicalSkill12 = new HadTechnicalSkill();
		existingHadTechnicalSkill12.setTechnicalSkillId(12);
		existingHadTechnicalSkill12.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill12);
		HadTechnicalSkill existingHadTechnicalSkill13 = new HadTechnicalSkill();
		existingHadTechnicalSkill13.setTechnicalSkillId(13);
		existingHadTechnicalSkill13.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill13);
		HadTechnicalSkill existingHadTechnicalSkill14 = new HadTechnicalSkill();
		existingHadTechnicalSkill14.setTechnicalSkillId(14);
		existingHadTechnicalSkill14.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill14);
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class)))
			.thenReturn(existingHadTechnicalSkillList);
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(addSpecsheetService, times(1)).addSpecsheet(any(AppSpecsheet.class));
		verify(addDevExperienceService, times(1)).addDevExperienceList(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(addTechnicalSkillListService, times(1)).addTechnicalSkillList(any());
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(addHadTechnicalSkillService, times(1)).insertList(any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
		verify(addUsedTechnicalSkillService, times(1)).addUsedTechnicalSkillList(any());
		
	}
	
	/**
	 * 管理者がACTIVEとして登録<br>
	 * 事前に登録された情報がない新規登録<br>
	 * 開発経験以下は全ての処理を実行<br>
	 * genderなし
	 */
	@Test
	public void TC1_2() {
		
		//prepare
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(15);
		form.setAppeal("アピール");
		form.setEffort("エフォート");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.ACTIVE.getKey());
		
		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		String creatorAndUpdater = user.getName();
		LocalDateTime createdAndUpdatedAt = LocalDateTime.of(2020, 7, 7, 11, 11, 11);
		
		//specsheet
		specsheet.setSpecsheetId(1);
		
		when(addSpecsheetService.addSpecsheet(any(AppSpecsheet.class))).thenReturn(specsheet);
		
		//devEcperience
		devExperienceForm1.setProjectName("project1");
		devExperienceForm1.setStartedOn("2020-07-01");
		devExperienceForm1.setFinishedOn("2020-07-01");
		devExperienceForm1.setTeamMembers(3);
		devExperienceForm1.setProjectMembers(6);
		devExperienceForm1.setRole("役割");
		devExperienceForm1.setProjectDetails("詳細");
		devExperienceForm1.setTasks("タスク");
		devExperienceForm1.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm1);
		form.setDevExperienceList(devExperienceFormList);
		
		insertedDevExperience.setDevExperienceId(1);
		insertedDevExperienceList.add(insertedDevExperience);
		
		when(addDevExperienceService.addDevExperienceList(any())).thenReturn(insertedDevExperienceList);
		
		//technicalSkill
		hadTechnicalSkillList[0] = "A,b";
		hadTechnicalSkillList[1] = "g,h";
		hadTechnicalSkillList[2] = "m,n";
		hadTechnicalSkillList[3] = "s,t";
		hadTechnicalSkillList[4] = "y,Z";
		hadTechnicalSkillList[5] = "e,F";
		hadTechnicalSkillList[6] = "K,l";
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		
		utStringList1.add("A,c");
		utStringList1.add("g,i");
		utStringList1.add("m,o");
		utStringList1.add("s,u");
		utStringList1.add("y,A");
		utStringList1.add("e,g");
		utStringList1.add("K,m");
		usedTechnicalSkillList.add(utStringList1);
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("A");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill1.setStage(Stage.REQUESTING.getKey());
		technicalSkill1.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setTechnicalSkillId(2);
		technicalSkill2.setName("f");
		technicalSkill2.setCategory(Category.OS.getKey());
		technicalSkill2.setStage(Stage.ACTIVE.getKey());
		technicalSkill2.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setTechnicalSkillId(3);
		technicalSkill3.setName("g");
		technicalSkill3.setCategory(Category.LANGUAGE.getKey());
		technicalSkill3.setStage(Stage.REQUESTING.getKey());
		technicalSkill3.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setTechnicalSkillId(4);
		technicalSkill4.setName("l");
		technicalSkill4.setCategory(Category.LANGUAGE.getKey());
		technicalSkill4.setStage(Stage.ACTIVE.getKey());
		technicalSkill4.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setTechnicalSkillId(5);
		technicalSkill5.setName("m");
		technicalSkill5.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill5.setStage(Stage.ACTIVE.getKey());
		technicalSkill5.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setTechnicalSkillId(6);
		technicalSkill6.setName("r");
		technicalSkill6.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill6.setStage(Stage.ACTIVE.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("s");
		technicalSkill7.setCategory(Category.LIBRARY.getKey());
		technicalSkill7.setStage(Stage.ACTIVE.getKey());
		technicalSkill7.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setTechnicalSkillId(8);
		technicalSkill8.setName("x");
		technicalSkill8.setCategory(Category.LIBRARY.getKey());
		technicalSkill8.setStage(Stage.ACTIVE.getKey());
		technicalSkill8.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill8);
		TechnicalSkill technicalSkill9 = new TechnicalSkill();
		technicalSkill9.setTechnicalSkillId(9);
		technicalSkill9.setName("y");
		technicalSkill9.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill9.setStage(Stage.ACTIVE.getKey());
		technicalSkill9.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill9);
		TechnicalSkill technicalSkill10 = new TechnicalSkill();
		technicalSkill10.setTechnicalSkillId(10);
		technicalSkill10.setName("d");
		technicalSkill10.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill10.setStage(Stage.ACTIVE.getKey());
		technicalSkill10.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill10);
		TechnicalSkill technicalSkill11 = new TechnicalSkill();
		technicalSkill11.setTechnicalSkillId(11);
		technicalSkill11.setName("e");
		technicalSkill11.setCategory(Category.TOOL.getKey());
		technicalSkill11.setStage(Stage.ACTIVE.getKey());
		technicalSkill11.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill11);
		TechnicalSkill technicalSkill12 = new TechnicalSkill();
		technicalSkill12.setTechnicalSkillId(12);
		technicalSkill12.setName("j");
		technicalSkill12.setCategory(Category.TOOL.getKey());
		technicalSkill12.setStage(Stage.ACTIVE.getKey());
		technicalSkill12.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill12);
		TechnicalSkill technicalSkill13 = new TechnicalSkill();
		technicalSkill13.setTechnicalSkillId(13);
		technicalSkill13.setName("K");
		technicalSkill13.setCategory(Category.PROCESS.getKey());
		technicalSkill13.setStage(Stage.ACTIVE.getKey());
		technicalSkill13.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill13);
		TechnicalSkill technicalSkill14 = new TechnicalSkill();
		technicalSkill14.setTechnicalSkillId(14);
		technicalSkill14.setName("p");
		technicalSkill14.setCategory(Category.PROCESS.getKey());
		technicalSkill14.setStage(Stage.ACTIVE.getKey());
		technicalSkill14.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill14);
		
		TechnicalSkill insertedTechnicalSkill1 = new TechnicalSkill(
				15, "b", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill1);
		TechnicalSkill insertedTechnicalSkill2 = new TechnicalSkill(
				16, "h", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill2);
		TechnicalSkill insertedTechnicalSkill3 = new TechnicalSkill(
				17, "n", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill3);
		TechnicalSkill insertedTechnicalSkill4 = new TechnicalSkill(
				18, "t", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill4);
		TechnicalSkill insertedTechnicalSkill5 = new TechnicalSkill(
				19, "Z", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill5);
		TechnicalSkill insertedTechnicalSkill6 = new TechnicalSkill(
				20, "f", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill6);
		TechnicalSkill insertedTechnicalSkill7 = new TechnicalSkill(
				21, "l", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill7);
		TechnicalSkill insertedTechnicalSkill8 = new TechnicalSkill(
				22, "c", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill8);
		TechnicalSkill insertedTechnicalSkill9 = new TechnicalSkill(
				23, "i", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill9);
		TechnicalSkill insertedTechnicalSkill10 = new TechnicalSkill(
				24, "o", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill10);
		TechnicalSkill insertedTechnicalSkill11 = new TechnicalSkill(
				25, "u", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill11);
		TechnicalSkill insertedTechnicalSkill12 = new TechnicalSkill(
				26, "A", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill12);
		TechnicalSkill insertedTechnicalSkill13 = new TechnicalSkill(
				27, "g", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill13);
		TechnicalSkill insertedTechnicalSkill14 = new TechnicalSkill(
				28, "m", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill14);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
		.thenReturn(existingTechnicalSkillList);
		when(addTechnicalSkillListService.addTechnicalSkillList(any())).thenReturn(insertedTechnicalSkillList);
		
		//hadTechnicalSkill
		HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
		existingHadTechnicalSkill1.setTechnicalSkillId(1);
		existingHadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
		HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
		existingHadTechnicalSkill2.setTechnicalSkillId(2);
		existingHadTechnicalSkill2.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
		HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
		existingHadTechnicalSkill3.setTechnicalSkillId(3);
		existingHadTechnicalSkill3.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
		HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
		existingHadTechnicalSkill4.setTechnicalSkillId(4);
		existingHadTechnicalSkill4.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
		HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
		existingHadTechnicalSkill5.setTechnicalSkillId(5);
		existingHadTechnicalSkill5.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
		HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
		existingHadTechnicalSkill6.setTechnicalSkillId(6);
		existingHadTechnicalSkill6.setStage(Stage.TEMPORARY.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
		HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
		existingHadTechnicalSkill7.setTechnicalSkillId(7);
		existingHadTechnicalSkill7.setStage(Stage.TEMPORARY.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
		HadTechnicalSkill existingHadTechnicalSkill8 = new HadTechnicalSkill();
		existingHadTechnicalSkill8.setTechnicalSkillId(8);
		existingHadTechnicalSkill8.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill8);
		HadTechnicalSkill existingHadTechnicalSkill9 = new HadTechnicalSkill();
		existingHadTechnicalSkill9.setTechnicalSkillId(9);
		existingHadTechnicalSkill9.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill9);
		HadTechnicalSkill existingHadTechnicalSkill10 = new HadTechnicalSkill();
		existingHadTechnicalSkill10.setTechnicalSkillId(10);
		existingHadTechnicalSkill10.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill10);
		HadTechnicalSkill existingHadTechnicalSkill11 = new HadTechnicalSkill();
		existingHadTechnicalSkill11.setTechnicalSkillId(11);
		existingHadTechnicalSkill11.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill11);
		HadTechnicalSkill existingHadTechnicalSkill12 = new HadTechnicalSkill();
		existingHadTechnicalSkill12.setTechnicalSkillId(12);
		existingHadTechnicalSkill12.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill12);
		HadTechnicalSkill existingHadTechnicalSkill13 = new HadTechnicalSkill();
		existingHadTechnicalSkill13.setTechnicalSkillId(13);
		existingHadTechnicalSkill13.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill13);
		HadTechnicalSkill existingHadTechnicalSkill14 = new HadTechnicalSkill();
		existingHadTechnicalSkill14.setTechnicalSkillId(14);
		existingHadTechnicalSkill14.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill14);
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class)))
		.thenReturn(existingHadTechnicalSkillList);
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(addSpecsheetService, times(1)).addSpecsheet(any(AppSpecsheet.class));
		verify(addDevExperienceService, times(1)).addDevExperienceList(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(addTechnicalSkillListService, times(1)).addTechnicalSkillList(any());
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(addHadTechnicalSkillService, times(1)).insertList(any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
		verify(addUsedTechnicalSkillService, times(1)).addUsedTechnicalSkillList(any());
		
	}
	
	@Test
	public void TC2_version照合失敗_newStageActive() {
		form.setSpecsheetId(1);
		form.setUserId(1);
		form.setGender("M");
		form.setVersion(2);
		
		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		newestSpecsheet.setVersion(3);
		newestSpecsheet.setStage(Stage.ACTIVE.getKey());
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(newestSpecsheet);
		Integer result = service.editSpecsheet(form, loginUser);
		assertEquals(result, newestSpecsheet.getStage());
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		
	}
	
	/**
	 * エンジニアがREQUESTINGとして登録<br>
	 * 事前に登録された情報がある更新としての登録<br>
	 * テクニカルスキルに関してはhadTechは要素がないものがあるリストでusedTechは孫要素がないものがあるリスト<br>
	 * 保有テクニカルスキルは削除のみ実行、使用テクニカルスキルに関しては全処理実行する
	 */
	@Test
	public void TC3() {
		
	//prepare
		form.setSpecsheetId(1);
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(15);
		form.setAppeal("アピール");
		form.setEffort("エフォート");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.REQUESTING.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		
		user.setUserId(1);
		user.setName("tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_WEB"));
		loginUser = new LoginUser(user, authorityList);
		
	//specsheet
		newestSpecsheet.setVersion(2);
		newestSpecsheet.setStage(Stage.TEMPORARY.getKey());
		specsheet.setSpecsheetId(2);
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(newestSpecsheet);
		when(addSpecsheetService.addSpecsheet(any(AppSpecsheet.class))).thenReturn(specsheet);
		
	//devExperience
		devExperienceForm1.setProjectName("project1");
		devExperienceForm1.setStartedOn("2020-07-01");
		devExperienceForm1.setFinishedOn("2020-07-01");
		devExperienceForm1.setTeamMembers(3);
		devExperienceForm1.setProjectMembers(6);
		devExperienceForm1.setRole("役割");
		devExperienceForm1.setProjectDetails("詳細");
		devExperienceForm1.setTasks("タスク");
		devExperienceForm1.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm1);
		form.setDevExperienceList(devExperienceFormList);
		
		insertedDevExperience.setDevExperienceId(2);
		insertedDevExperienceList.add(insertedDevExperience);
		
		when(addDevExperienceService.addDevExperienceList(any())).thenReturn(insertedDevExperienceList);
		
	//technicalSkill
		hadTechnicalSkillList[0] = "A";
		hadTechnicalSkillList[1] = "";
		hadTechnicalSkillList[2] = "c";
		hadTechnicalSkillList[3] = "d";
		hadTechnicalSkillList[4] = "e";
		hadTechnicalSkillList[5] = "";
		hadTechnicalSkillList[6] = "";
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		
		utStringList1.add("A");
		utStringList1.add("b");
		utStringList1.add("");
		utStringList1.add("d");
		utStringList1.add("e");
		utStringList1.add("f");
		utStringList1.add("g");
		usedTechnicalSkillList.add(utStringList1);
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("A");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill1.setStage(Stage.REQUESTING.getKey());
		technicalSkill1.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setTechnicalSkillId(2);
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkill2.setStage(Stage.ACTIVE.getKey());
		technicalSkill2.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setTechnicalSkillId(3);
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill3.setStage(Stage.REQUESTING.getKey());
		technicalSkill3.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setTechnicalSkillId(4);
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkill4.setStage(Stage.TEMPORARY.getKey());
		technicalSkill4.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setTechnicalSkillId(5);
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill5.setStage(Stage.ACTIVE.getKey());
		technicalSkill5.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setTechnicalSkillId(6);
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkill6.setStage(Stage.TEMPORARY.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkill7.setStage(Stage.ACTIVE.getKey());
		technicalSkill7.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill7);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
			.thenReturn(existingTechnicalSkillList);
		
	//hadTechnicalSkill
		HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
		existingHadTechnicalSkill1.setTechnicalSkillId(1);
		existingHadTechnicalSkill1.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
		HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
		existingHadTechnicalSkill2.setTechnicalSkillId(2);
		existingHadTechnicalSkill2.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
		HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
		existingHadTechnicalSkill3.setTechnicalSkillId(3);
		existingHadTechnicalSkill3.setStage(Stage.DELETED.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
		HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
		existingHadTechnicalSkill4.setTechnicalSkillId(4);
		existingHadTechnicalSkill4.setStage(Stage.ACTIVE.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
		HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
		existingHadTechnicalSkill5.setTechnicalSkillId(5);
		existingHadTechnicalSkill5.setStage(Stage.DELETED.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
		HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
		existingHadTechnicalSkill6.setTechnicalSkillId(6);
		existingHadTechnicalSkill6.setStage(Stage.DELETED.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
		HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
		existingHadTechnicalSkill7.setTechnicalSkillId(7);
		existingHadTechnicalSkill7.setStage(Stage.TEMPORARY.getKey());
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
		
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class)))
			.thenReturn(existingHadTechnicalSkillList);
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(addSpecsheetService, times(1)).addSpecsheet(any(AppSpecsheet.class));
		verify(addDevExperienceService, times(1)).addDevExperienceList(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(updateHadTechnicalSkillStageService, times(2)).updateStageByList(any());
		verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
		verify(addUsedTechnicalSkillService, times(1)).addUsedTechnicalSkillList(any());
		
	}
	
	/**
	 * エンジニアがTEMPORARYとして登録<br>
	 * 事前に登録された情報がある更新としての登録<br>
	 * テクニカルスキルに関してはhadTechはリストなしでusedTechは子要素がないものがあるリスト<br>
	 * 保有テクニカルスキルは何もせず使用テクニカルスキルに関しては全処理実行する
	 */
	@Test
	public void TC4() {
		
		//prepare
		form.setSpecsheetId(1);
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(15);
		form.setAppeal("アピール");
		form.setEffort("エフォート");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.TEMPORARY.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		
		user.setUserId(1);
		user.setName("tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_WEB"));
		loginUser = new LoginUser(user, authorityList);
		
		//specsheet
		newestSpecsheet.setVersion(1);
		specsheet.setSpecsheetId(2);
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(newestSpecsheet);
		when(addSpecsheetService.addSpecsheet(any(AppSpecsheet.class))).thenReturn(specsheet);
		
		//devExperience
		devExperienceForm1.setProjectName("project1");
		devExperienceForm1.setStartedOn("2020-07-01");
		devExperienceForm1.setFinishedOn("2020-07-01");
		devExperienceForm1.setTeamMembers(3);
		devExperienceForm1.setProjectMembers(6);
		devExperienceForm1.setRole("役割");
		devExperienceForm1.setProjectDetails("詳細");
		devExperienceForm1.setTasks("タスク");
		devExperienceForm1.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm1);
		form.setDevExperienceList(devExperienceFormList);
		
		insertedDevExperience.setDevExperienceId(2);
		insertedDevExperienceList.add(insertedDevExperience);
		
		when(addDevExperienceService.addDevExperienceList(any())).thenReturn(insertedDevExperienceList);
		
		//technicalSkill
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("A");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill1.setStage(Stage.REQUESTING.getKey());
		technicalSkill1.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setTechnicalSkillId(2);
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkill2.setStage(Stage.ACTIVE.getKey());
		technicalSkill2.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill2);
		TechnicalSkill technicalSkill3 = new TechnicalSkill();
		technicalSkill3.setTechnicalSkillId(3);
		technicalSkill3.setName("c");
		technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
		technicalSkill3.setStage(Stage.REQUESTING.getKey());
		technicalSkill3.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill3);
		TechnicalSkill technicalSkill4 = new TechnicalSkill();
		technicalSkill4.setTechnicalSkillId(4);
		technicalSkill4.setName("d");
		technicalSkill4.setCategory(Category.LIBRARY.getKey());
		technicalSkill4.setStage(Stage.TEMPORARY.getKey());
		technicalSkill4.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill4);
		TechnicalSkill technicalSkill5 = new TechnicalSkill();
		technicalSkill5.setTechnicalSkillId(5);
		technicalSkill5.setName("e");
		technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
		technicalSkill5.setStage(Stage.ACTIVE.getKey());
		technicalSkill5.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill5);
		TechnicalSkill technicalSkill6 = new TechnicalSkill();
		technicalSkill6.setTechnicalSkillId(6);
		technicalSkill6.setName("f");
		technicalSkill6.setCategory(Category.TOOL.getKey());
		technicalSkill6.setStage(Stage.TEMPORARY.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkill7.setStage(Stage.ACTIVE.getKey());
		technicalSkill7.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill7);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
		.thenReturn(existingTechnicalSkillList);
		
		//hadTechnicalSkill
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class)))
		.thenReturn(existingHadTechnicalSkillList);
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(addSpecsheetService, times(1)).addSpecsheet(any(AppSpecsheet.class));
		verify(addDevExperienceService, times(1)).addDevExperienceList(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		
	}
	
	/**
	 * 管理者がACTIVEとして登録<br>
	 * スペックシート以外の各要素はないものとして処理を行わない
	 */
	@Test
	public void TC5() {
		
	//prepare
		form.setUserId(1);
		form.setEmployeeId(1111);
		form.setGeneration("20代前半");
		form.setGender("M");
		form.setNearestStation("新宿駅");
		form.setStartBusinessDate("応相談");
		form.setEngineerPeriod(10);
		form.setSePeriod(3);
		form.setPgPeriod(7);
		form.setItPeriod(15);
		form.setAppeal("アピール");
		form.setEffort("エフォート");
		form.setCertification("資格");
		form.setPreJob("前職");
		form.setStage(Stage.ACTIVE.getKey());

		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
	//specsheet
		specsheet.setSpecsheetId(1);
		
		when(addSpecsheetService.addSpecsheet(any(AppSpecsheet.class))).thenReturn(specsheet);
		
	//devEcperience
		form.setDevExperienceList(devExperienceFormList);
		
	//technicalSkill
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("a");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill1.setStage(Stage.REQUESTING.getKey());
		technicalSkill1.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill1);
				
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
			.thenReturn(existingTechnicalSkillList);
		
	//hadTechnicalSkill
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class)))
			.thenReturn(existingHadTechnicalSkillList);
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(addSpecsheetService, times(1)).addSpecsheet(any(AppSpecsheet.class));
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		
	}

}
