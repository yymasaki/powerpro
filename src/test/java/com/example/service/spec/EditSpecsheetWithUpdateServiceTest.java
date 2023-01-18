package com.example.service.spec;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.example.domain.UsedTechnicalSkill;
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
public class EditSpecsheetWithUpdateServiceTest {
	
	private EditSpecsheetForm form = new EditSpecsheetForm();
	private String[] hadTechnicalSkillList = new String[7];
	private List<DevExperienceForm> devExperienceFormList = new ArrayList<>();
	private DevExperienceForm devExperienceForm1 = new DevExperienceForm();
	private DevExperienceForm devExperienceForm2 = new DevExperienceForm();
	private List<String> utStringList1 = new ArrayList<>();
	private List<String> utStringList2 = new ArrayList<>();
	private List<List<String>> usedTechnicalSkillList = new ArrayList<>();
	
	private User user = new User();
	private Collection<GrantedAuthority> authorityList = new ArrayList<>();
	private LoginUser loginUser = null;
	private AppSpecsheet specsheet = new AppSpecsheet();
	
	private List<DevExperience> existingDevExperienceList = new ArrayList<>();
	private DevExperience existingDevExperience1 = new DevExperience();
	private DevExperience existingDevExperience2 = new DevExperience();
	private List<DevExperience> unregisteredDevExperienceList = new ArrayList<>();
	private DevExperience unregisteredDevExperience = new DevExperience();
	private List<DevExperience> insertedDevExperienceList = new ArrayList<>();
	private DevExperience insertedDevExperience = new DevExperience();
	
	private List<TechnicalSkill> existingTechnicalSkillList = new ArrayList<>();
	private List<TechnicalSkill> insertedTechnicalSkillList  = new ArrayList<>();
	
	private List<HadTechnicalSkill> existingHadTechnicalSkillList = new ArrayList<>();
	
	private List<UsedTechnicalSkill> existingUsedTechnicalSkillList1= new ArrayList<>();
	private List<UsedTechnicalSkill> existingUsedTechnicalSkillList2 = new ArrayList<>();
	
	@Mock
	private GetNewestSpecsheetService getNewestSpecsheetService;
	@Mock
	private UpdateSpecsheetService updateSpecsheetService;
	@Mock
	private GetDevExperienceService getDevExperienceService;
	@Mock
	private AddDevExperienceService addDevExperienceService;
	@Mock
	private UpdateDevExperienceService updateDevExperienceService;
	@Mock
	private DeleteDevExperieceService deleteDevExperieceService;
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
	@Mock
	private DeleteUsedTechnicalSkillService deleteUsedTechnicalSkillService;
	
	@InjectMocks
	private EditSpecsheetWithUpdateService service;

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
	public void TC1_管理者がspecの更新をはじめ全てのmockを使用する正常系() {
		
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
		form.setAdminComment("管理者コメント");
		form.setStage(Stage.ACTIVE.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		
		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		String creatorAndUpdater = user.getName();
		LocalDateTime createdAndUpdatedAt = LocalDateTime.of(2020, 7, 7, 11, 11, 11);
		
	//specsheet
		specsheet.setVersion(1);
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
		
	//devEcperience
		//registeredDev
		devExperienceForm1.setDevExperienceId(1);
		devExperienceForm1.setProjectName("project1");
		devExperienceForm1.setTeamMembers(3);
		devExperienceForm1.setProjectMembers(6);
		devExperienceForm1.setRole("役割");
		devExperienceForm1.setProjectDetails("詳細");
		devExperienceForm1.setTasks("タスク");
		devExperienceForm1.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm1);
		//unregisteredDev
		devExperienceForm2.setProjectName("project2");
		devExperienceForm2.setStartedOn("2020-05-01");
		devExperienceForm2.setFinishedOn("2020-05-01");
		devExperienceForm2.setTeamMembers(3);
		devExperienceForm2.setProjectMembers(6);
		devExperienceForm2.setRole("役割");
		devExperienceForm2.setProjectDetails("詳細");
		devExperienceForm2.setTasks("タスク");
		devExperienceForm2.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm2);
		form.setDevExperienceList(devExperienceFormList);
		
		existingDevExperience1.setDevExperienceId(devExperienceForm1.getDevExperienceId());
		existingDevExperienceList.add(existingDevExperience1);
		existingDevExperience2.setDevExperienceId(2);
		existingDevExperienceList.add(existingDevExperience2);
		
		
		//equal devExperienceForm2
		unregisteredDevExperience.setSpecsheetId(specsheet.getSpecsheetId());
		unregisteredDevExperience.setProjectName(devExperienceForm2.getProjectName());
		unregisteredDevExperience.setStartedOn(LocalDate.parse(devExperienceForm2.getStartedOn()));
		unregisteredDevExperience.setFinishedOn(LocalDate.parse(devExperienceForm2.getFinishedOn()));
		unregisteredDevExperience.setTeamMembers(devExperienceForm2.getTeamMembers());
		unregisteredDevExperience.setProjectMembers(devExperienceForm2.getProjectMembers());
		unregisteredDevExperience.setRole(devExperienceForm2.getRole());
		unregisteredDevExperience.setProjectDetails(devExperienceForm2.getProjectDetails());
		unregisteredDevExperience.setTasks(devExperienceForm2.getTasks());
		unregisteredDevExperience.setAcquired(devExperienceForm2.getAcquired());
		unregisteredDevExperience.setStage(Stage.ACTIVE.getKey());
		unregisteredDevExperienceList.add(unregisteredDevExperience);
		
		insertedDevExperience = unregisteredDevExperience;
		insertedDevExperience.setDevExperienceId(4);
		insertedDevExperienceList.add(insertedDevExperience);
		
		when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
			.thenReturn(existingDevExperienceList);
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
		utStringList2.add("A,d");
		utStringList2.add("g,j");
		utStringList2.add("m,p");
		utStringList2.add("s,v");
		utStringList2.add("y,b");
		utStringList2.add("e,h");
		utStringList2.add("K,n");
		usedTechnicalSkillList.add(utStringList2);
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
		TechnicalSkill insertedTechnicalSkill15 = new TechnicalSkill(
				29, "d", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill15);
		TechnicalSkill insertedTechnicalSkill16 = new TechnicalSkill(
				30, "j", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill16);
		TechnicalSkill insertedTechnicalSkill17 = new TechnicalSkill(
				31, "p", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill17);
		TechnicalSkill insertedTechnicalSkill18 = new TechnicalSkill(
				32, "v", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill18);
		TechnicalSkill insertedTechnicalSkill19 = new TechnicalSkill(
				33, "b", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill19);
		TechnicalSkill insertedTechnicalSkill20 = new TechnicalSkill(
				34, "h", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill20);
		TechnicalSkill insertedTechnicalSkill21 = new TechnicalSkill(
				35, "n", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill21);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
			.thenReturn(existingTechnicalSkillList);
		when(addTechnicalSkillListService.addTechnicalSkillList(any())).thenReturn(insertedTechnicalSkillList);
		
	//hadTechnicalSkill
		HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
		existingHadTechnicalSkill1.setTechnicalSkillId(1);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
		HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
		existingHadTechnicalSkill2.setTechnicalSkillId(2);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
		HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
		existingHadTechnicalSkill3.setTechnicalSkillId(3);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
		HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
		existingHadTechnicalSkill4.setTechnicalSkillId(4);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
		HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
		existingHadTechnicalSkill5.setTechnicalSkillId(5);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
		HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
		existingHadTechnicalSkill6.setTechnicalSkillId(6);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
		HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
		existingHadTechnicalSkill7.setTechnicalSkillId(7);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
		HadTechnicalSkill existingHadTechnicalSkill8 = new HadTechnicalSkill();
		existingHadTechnicalSkill8.setTechnicalSkillId(8);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill8);
		HadTechnicalSkill existingHadTechnicalSkill9 = new HadTechnicalSkill();
		existingHadTechnicalSkill9.setTechnicalSkillId(9);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill9);
		HadTechnicalSkill existingHadTechnicalSkill10 = new HadTechnicalSkill();
		existingHadTechnicalSkill10.setTechnicalSkillId(10);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill10);
		HadTechnicalSkill existingHadTechnicalSkill11 = new HadTechnicalSkill();
		existingHadTechnicalSkill11.setTechnicalSkillId(11);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill11);
		HadTechnicalSkill existingHadTechnicalSkill12 = new HadTechnicalSkill();
		existingHadTechnicalSkill12.setTechnicalSkillId(12);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill12);
		HadTechnicalSkill existingHadTechnicalSkill13 = new HadTechnicalSkill();
		existingHadTechnicalSkill13.setTechnicalSkillId(13);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill13);
		HadTechnicalSkill existingHadTechnicalSkill14 = new HadTechnicalSkill();
		existingHadTechnicalSkill14.setTechnicalSkillId(14);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill14);
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
		
	//usedTechnicalSkill
		UsedTechnicalSkill existingUsedTechnicalSkill1 = new UsedTechnicalSkill(null, 1, 1, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill1);
		UsedTechnicalSkill existingUsedTechnicalSkill2 = new UsedTechnicalSkill(null, 1, 2, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill2);
		UsedTechnicalSkill existingUsedTechnicalSkill3 = new UsedTechnicalSkill(null, 1, 3, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill3);
		UsedTechnicalSkill existingUsedTechnicalSkill4 = new UsedTechnicalSkill(null, 1, 4, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill4);
		UsedTechnicalSkill existingUsedTechnicalSkill5 = new UsedTechnicalSkill(null, 1, 5, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill5);
		UsedTechnicalSkill existingUsedTechnicalSkill6 = new UsedTechnicalSkill(null, 1, 6, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill6);
		UsedTechnicalSkill existingUsedTechnicalSkill7 = new UsedTechnicalSkill(null, 1, 7, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill7);
		existingDevExperience1.setUsedTechnicalSkillList(existingUsedTechnicalSkillList1);
		UsedTechnicalSkill existingUsedTechnicalSkill8 = new UsedTechnicalSkill(null, 2, 8, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill8);
		UsedTechnicalSkill existingUsedTechnicalSkill9 = new UsedTechnicalSkill(null, 2, 9, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill9);
		UsedTechnicalSkill existingUsedTechnicalSkill10 = new UsedTechnicalSkill(null, 2, 10, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill10);
		UsedTechnicalSkill existingUsedTechnicalSkill11 = new UsedTechnicalSkill(null, 2, 11, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill11);
		UsedTechnicalSkill existingUsedTechnicalSkill12 = new UsedTechnicalSkill(null, 2, 12, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill12);
		UsedTechnicalSkill existingUsedTechnicalSkill13 = new UsedTechnicalSkill(null, 2, 13, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill13);
		UsedTechnicalSkill existingUsedTechnicalSkill14 = new UsedTechnicalSkill(null, 2, 14, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill14);
		existingDevExperience2.setUsedTechnicalSkillList(existingUsedTechnicalSkillList2);
		
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
		verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
		verify(addDevExperienceService, times(1)).addDevExperienceList(any());
		verify(updateDevExperienceService, times(1)).updateListByPrimaryKey(any());
		verify(deleteDevExperieceService, times(1)).deleteListByPrimaryKey(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(addTechnicalSkillListService, times(1)).addTechnicalSkillList(any());
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(addHadTechnicalSkillService, times(1)).insertList(any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
		verify(addUsedTechnicalSkillService, times(1)).addUsedTechnicalSkillList(any());
		verify(deleteUsedTechnicalSkillService, times(1)).deleteUsedTechnicalSkill(any());
	}
	
	@Test
	public void TC1_2_管理者がspecの更新をはじめ全てのmockを使用する正常系_genderなし() {
		
		//prepare
		form.setSpecsheetId(1);
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
		form.setAdminComment("管理者コメント");
		form.setStage(Stage.ACTIVE.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		
		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		String creatorAndUpdater = user.getName();
		LocalDateTime createdAndUpdatedAt = LocalDateTime.of(2020, 7, 7, 11, 11, 11);
		
		//specsheet
		specsheet.setStage(Stage.TEMPORARY.getKey());
		specsheet.setVersion(2);
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
		
		//devEcperience
		//registeredDev
		devExperienceForm1.setDevExperienceId(1);
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
		//unregisteredDev
		devExperienceForm2.setProjectName("project2");
		devExperienceForm2.setStartedOn("2020-05-01");
		devExperienceForm2.setFinishedOn("2020-05-01");
		devExperienceForm2.setTeamMembers(3);
		devExperienceForm2.setProjectMembers(6);
		devExperienceForm2.setRole("役割");
		devExperienceForm2.setProjectDetails("詳細");
		devExperienceForm2.setTasks("タスク");
		devExperienceForm2.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm2);
		form.setDevExperienceList(devExperienceFormList);
		
		existingDevExperience1.setDevExperienceId(devExperienceForm1.getDevExperienceId());
		existingDevExperienceList.add(existingDevExperience1);
		existingDevExperience2.setDevExperienceId(2);
		existingDevExperienceList.add(existingDevExperience2);
		
		
		//equal devExperienceForm2
		unregisteredDevExperience.setSpecsheetId(specsheet.getSpecsheetId());
		unregisteredDevExperience.setProjectName(devExperienceForm2.getProjectName());
		unregisteredDevExperience.setStartedOn(LocalDate.parse(devExperienceForm2.getStartedOn()));
		unregisteredDevExperience.setFinishedOn(LocalDate.parse(devExperienceForm2.getFinishedOn()));
		unregisteredDevExperience.setTeamMembers(devExperienceForm2.getTeamMembers());
		unregisteredDevExperience.setProjectMembers(devExperienceForm2.getProjectMembers());
		unregisteredDevExperience.setRole(devExperienceForm2.getRole());
		unregisteredDevExperience.setProjectDetails(devExperienceForm2.getProjectDetails());
		unregisteredDevExperience.setTasks(devExperienceForm2.getTasks());
		unregisteredDevExperience.setAcquired(devExperienceForm2.getAcquired());
		unregisteredDevExperience.setStage(Stage.ACTIVE.getKey());
		unregisteredDevExperienceList.add(unregisteredDevExperience);
		
		insertedDevExperience = unregisteredDevExperience;
		insertedDevExperience.setDevExperienceId(4);
		insertedDevExperienceList.add(insertedDevExperience);
		
		when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
		.thenReturn(existingDevExperienceList);
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
		utStringList2.add("A,d");
		utStringList2.add("g,j");
		utStringList2.add("m,p");
		utStringList2.add("s,v");
		utStringList2.add("y,b");
		utStringList2.add("e,h");
		utStringList2.add("K,n");
		usedTechnicalSkillList.add(utStringList2);
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
		TechnicalSkill insertedTechnicalSkill15 = new TechnicalSkill(
				29, "d", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill15);
		TechnicalSkill insertedTechnicalSkill16 = new TechnicalSkill(
				30, "j", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill16);
		TechnicalSkill insertedTechnicalSkill17 = new TechnicalSkill(
				31, "p", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill17);
		TechnicalSkill insertedTechnicalSkill18 = new TechnicalSkill(
				32, "v", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill18);
		TechnicalSkill insertedTechnicalSkill19 = new TechnicalSkill(
				33, "b", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill19);
		TechnicalSkill insertedTechnicalSkill20 = new TechnicalSkill(
				34, "h", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill20);
		TechnicalSkill insertedTechnicalSkill21 = new TechnicalSkill(
				35, "n", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
				creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
		insertedTechnicalSkillList.add(insertedTechnicalSkill21);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
		.thenReturn(existingTechnicalSkillList);
		when(addTechnicalSkillListService.addTechnicalSkillList(any())).thenReturn(insertedTechnicalSkillList);
		
		//hadTechnicalSkill
		HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
		existingHadTechnicalSkill1.setTechnicalSkillId(1);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
		HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
		existingHadTechnicalSkill2.setTechnicalSkillId(2);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
		HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
		existingHadTechnicalSkill3.setTechnicalSkillId(3);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
		HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
		existingHadTechnicalSkill4.setTechnicalSkillId(4);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
		HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
		existingHadTechnicalSkill5.setTechnicalSkillId(5);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
		HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
		existingHadTechnicalSkill6.setTechnicalSkillId(6);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
		HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
		existingHadTechnicalSkill7.setTechnicalSkillId(7);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
		HadTechnicalSkill existingHadTechnicalSkill8 = new HadTechnicalSkill();
		existingHadTechnicalSkill8.setTechnicalSkillId(8);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill8);
		HadTechnicalSkill existingHadTechnicalSkill9 = new HadTechnicalSkill();
		existingHadTechnicalSkill9.setTechnicalSkillId(9);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill9);
		HadTechnicalSkill existingHadTechnicalSkill10 = new HadTechnicalSkill();
		existingHadTechnicalSkill10.setTechnicalSkillId(10);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill10);
		HadTechnicalSkill existingHadTechnicalSkill11 = new HadTechnicalSkill();
		existingHadTechnicalSkill11.setTechnicalSkillId(11);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill11);
		HadTechnicalSkill existingHadTechnicalSkill12 = new HadTechnicalSkill();
		existingHadTechnicalSkill12.setTechnicalSkillId(12);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill12);
		HadTechnicalSkill existingHadTechnicalSkill13 = new HadTechnicalSkill();
		existingHadTechnicalSkill13.setTechnicalSkillId(13);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill13);
		HadTechnicalSkill existingHadTechnicalSkill14 = new HadTechnicalSkill();
		existingHadTechnicalSkill14.setTechnicalSkillId(14);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill14);
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
		
		//usedTechnicalSkill
		UsedTechnicalSkill existingUsedTechnicalSkill1 = new UsedTechnicalSkill(null, 1, 1, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill1);
		UsedTechnicalSkill existingUsedTechnicalSkill2 = new UsedTechnicalSkill(null, 1, 2, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill2);
		UsedTechnicalSkill existingUsedTechnicalSkill3 = new UsedTechnicalSkill(null, 1, 3, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill3);
		UsedTechnicalSkill existingUsedTechnicalSkill4 = new UsedTechnicalSkill(null, 1, 4, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill4);
		UsedTechnicalSkill existingUsedTechnicalSkill5 = new UsedTechnicalSkill(null, 1, 5, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill5);
		UsedTechnicalSkill existingUsedTechnicalSkill6 = new UsedTechnicalSkill(null, 1, 6, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill6);
		UsedTechnicalSkill existingUsedTechnicalSkill7 = new UsedTechnicalSkill(null, 1, 7, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill7);
		existingDevExperience1.setUsedTechnicalSkillList(existingUsedTechnicalSkillList1);
		UsedTechnicalSkill existingUsedTechnicalSkill8 = new UsedTechnicalSkill(null, 2, 8, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill8);
		UsedTechnicalSkill existingUsedTechnicalSkill9 = new UsedTechnicalSkill(null, 2, 9, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill9);
		UsedTechnicalSkill existingUsedTechnicalSkill10 = new UsedTechnicalSkill(null, 2, 10, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill10);
		UsedTechnicalSkill existingUsedTechnicalSkill11 = new UsedTechnicalSkill(null, 2, 11, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill11);
		UsedTechnicalSkill existingUsedTechnicalSkill12 = new UsedTechnicalSkill(null, 2, 12, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill12);
		UsedTechnicalSkill existingUsedTechnicalSkill13 = new UsedTechnicalSkill(null, 2, 13, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill13);
		UsedTechnicalSkill existingUsedTechnicalSkill14 = new UsedTechnicalSkill(null, 2, 14, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList2.add(existingUsedTechnicalSkill14);
		existingDevExperience2.setUsedTechnicalSkillList(existingUsedTechnicalSkillList2);
		
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
		verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
		verify(addDevExperienceService, times(1)).addDevExperienceList(any());
		verify(updateDevExperienceService, times(1)).updateListByPrimaryKey(any());
		verify(deleteDevExperieceService, times(1)).deleteListByPrimaryKey(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(addTechnicalSkillListService, times(1)).addTechnicalSkillList(any());
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(addHadTechnicalSkillService, times(1)).insertList(any());
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
		verify(addUsedTechnicalSkillService, times(1)).addUsedTechnicalSkillList(any());
		verify(deleteUsedTechnicalSkillService, times(1)).deleteUsedTechnicalSkill(any());
	}
	
	@Test
	public void TC2_管理者でスペックシートのバージョン照合が失敗した場合() {
		form.setUserId(1);
		form.setVersion(1);
		
		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		specsheet.setVersion(2);
		specsheet.setStage(Stage.ACTIVE.getKey());
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		Integer result = service.editSpecsheet(form, loginUser);
		assertEquals(result, specsheet.getStage());
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
	}
	
	@Test
	public void TC3_管理者でスペックシートの更新が失敗する場合() {
		form.setUserId(1);
		form.setVersion(1);
		form.setGender("M");
		
		user.setUserId(2);
		user.setName("admin_tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		loginUser = new LoginUser(user, authorityList);
		
		specsheet.setVersion(1);
		specsheet.setStage(Stage.ACTIVE.getKey());
		AppSpecsheet specsheet2 = new AppSpecsheet();
		specsheet2.setStage(Stage.SENT_BACK.getKey());
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet).thenReturn(specsheet2);
		when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(0);
		Integer result = service.editSpecsheet(form, loginUser);
		assertEquals(result, specsheet2.getStage());
		verify(getNewestSpecsheetService, times(2)).getNewestSpecsheet(any(Integer.class));
		verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
	}
	
	/**
	 * 管理者が編集する<br>
	 * 開発経験に関しては未登録のもののみで不要な開発経験は削除する<br>
	 * テクニカルスキルに関してはhadTechは要素がないものがあるリストでusedTechは孫要素がないものがあるリスト<br>
	 * 未登録のものはあるが登録済更新対象はない<br>
	 * 保有テクニカルスキルに関しては全処理実行する<br>
	 * 使用テクニカルスキルに関しては登録処理のみ実行する
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
			form.setAdminComment("管理者コメント");
			form.setStage(Stage.ACTIVE.getKey());
			form.setVersion(1);
			form.setRawStage(Stage.ACTIVE.getKey());
			
			user.setUserId(2);
			user.setName("admin_tester");
			user.setEmail("email");
			user.setPassword("password");
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			loginUser = new LoginUser(user, authorityList);
			
			String creatorAndUpdater = user.getName();
			LocalDateTime createdAndUpdatedAt = LocalDateTime.of(2020, 7, 7, 11, 11, 11);
			
		//specsheet
			specsheet.setVersion(1);
			specsheet.setCreator("tester");
			specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
			
			when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
			
			when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
			
		//devEcperience
			//unregisteredDev
			devExperienceForm2.setProjectName("project2");
			devExperienceForm2.setStartedOn("2020-05-01");
			devExperienceForm2.setFinishedOn("2020-05-01");
			devExperienceForm2.setTeamMembers(3);
			devExperienceForm2.setProjectMembers(6);
			devExperienceForm2.setRole("役割");
			devExperienceForm2.setProjectDetails("詳細");
			devExperienceForm2.setTasks("タスク");
			devExperienceForm2.setAcquired("知見");
			devExperienceFormList.add(devExperienceForm2);
			form.setDevExperienceList(devExperienceFormList);
			
			existingDevExperience1.setDevExperienceId(1);
			existingDevExperienceList.add(existingDevExperience1);
			
			
			//equal devExperienceForm2
			unregisteredDevExperience.setSpecsheetId(specsheet.getSpecsheetId());
			unregisteredDevExperience.setProjectName(devExperienceForm2.getProjectName());
			unregisteredDevExperience.setStartedOn(LocalDate.parse(devExperienceForm2.getStartedOn()));
			unregisteredDevExperience.setFinishedOn(LocalDate.parse(devExperienceForm2.getFinishedOn()));
			unregisteredDevExperience.setTeamMembers(devExperienceForm2.getTeamMembers());
			unregisteredDevExperience.setProjectMembers(devExperienceForm2.getProjectMembers());
			unregisteredDevExperience.setRole(devExperienceForm2.getRole());
			unregisteredDevExperience.setProjectDetails(devExperienceForm2.getProjectDetails());
			unregisteredDevExperience.setTasks(devExperienceForm2.getTasks());
			unregisteredDevExperience.setAcquired(devExperienceForm2.getAcquired());
			unregisteredDevExperience.setStage(Stage.ACTIVE.getKey());
			unregisteredDevExperienceList.add(unregisteredDevExperience);
			
			insertedDevExperience = unregisteredDevExperience;
			insertedDevExperience.setDevExperienceId(2);
			insertedDevExperienceList.add(insertedDevExperience);
			
			when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
				.thenReturn(existingDevExperienceList);
			when(addDevExperienceService.addDevExperienceList(any())).thenReturn(insertedDevExperienceList);
			
		//technicalSkill
			hadTechnicalSkillList[0] = "A";
			hadTechnicalSkillList[1] = "";
			hadTechnicalSkillList[2] = "e";
			hadTechnicalSkillList[3] = "g";
			hadTechnicalSkillList[4] = "i";
			hadTechnicalSkillList[5] = "K";
			hadTechnicalSkillList[6] = "m";
			form.setHadTechnicalSkillList(hadTechnicalSkillList);
			
			utStringList1.add("A");
			utStringList1.add("c");
			utStringList1.add("");
			utStringList1.add("g");
			utStringList1.add("i");
			utStringList1.add("K");
			utStringList1.add("m");
			usedTechnicalSkillList.add(utStringList1);
			form.setUsedTechnicalSkillList(usedTechnicalSkillList);
			
			TechnicalSkill technicalSkill1 = new TechnicalSkill();
			technicalSkill1.setTechnicalSkillId(1);
			technicalSkill1.setName("b");
			technicalSkill1.setCategory(Category.OS.getKey());
			technicalSkill1.setStage(Stage.REQUESTING.getKey());
			technicalSkill1.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill1);
			TechnicalSkill technicalSkill2 = new TechnicalSkill();
			technicalSkill2.setTechnicalSkillId(2);
			technicalSkill2.setName("d");
			technicalSkill2.setCategory(Category.LANGUAGE.getKey());
			technicalSkill2.setStage(Stage.ACTIVE.getKey());
			technicalSkill2.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill2);
			TechnicalSkill technicalSkill3 = new TechnicalSkill();
			technicalSkill3.setTechnicalSkillId(3);
			technicalSkill3.setName("f");
			technicalSkill3.setCategory(Category.FRAMEWORK.getKey());
			technicalSkill3.setStage(Stage.REQUESTING.getKey());
			technicalSkill3.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill3);
			TechnicalSkill technicalSkill4 = new TechnicalSkill();
			technicalSkill4.setTechnicalSkillId(4);
			technicalSkill4.setName("h");
			technicalSkill4.setCategory(Category.LIBRARY.getKey());
			technicalSkill4.setStage(Stage.ACTIVE.getKey());
			technicalSkill4.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill4);
			TechnicalSkill technicalSkill5 = new TechnicalSkill();
			technicalSkill5.setTechnicalSkillId(5);
			technicalSkill5.setName("j");
			technicalSkill5.setCategory(Category.MIDDLEWARE.getKey());
			technicalSkill5.setStage(Stage.ACTIVE.getKey());
			technicalSkill5.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill5);
			TechnicalSkill technicalSkill6 = new TechnicalSkill();
			technicalSkill6.setTechnicalSkillId(6);
			technicalSkill6.setName("l");
			technicalSkill6.setCategory(Category.TOOL.getKey());
			technicalSkill6.setStage(Stage.ACTIVE.getKey());
			technicalSkill6.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill6);
			TechnicalSkill technicalSkill7 = new TechnicalSkill();
			technicalSkill7.setTechnicalSkillId(7);
			technicalSkill7.setName("n");
			technicalSkill7.setCategory(Category.PROCESS.getKey());
			technicalSkill7.setStage(Stage.ACTIVE.getKey());
			technicalSkill7.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill7);
			
			TechnicalSkill insertedTechnicalSkill1 = new TechnicalSkill(
					15, "A", Category.OS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill1);
			TechnicalSkill insertedTechnicalSkill2 = new TechnicalSkill(
					16, "c", Category.LANGUAGE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill2);
			TechnicalSkill insertedTechnicalSkill3 = new TechnicalSkill(
					17, "e", Category.FRAMEWORK.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill3);
			TechnicalSkill insertedTechnicalSkill4 = new TechnicalSkill(
					18, "g", Category.LIBRARY.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill4);
			TechnicalSkill insertedTechnicalSkill5 = new TechnicalSkill(
					19, "i", Category.MIDDLEWARE.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill5);
			TechnicalSkill insertedTechnicalSkill6 = new TechnicalSkill(
					20, "k", Category.TOOL.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill6);
			TechnicalSkill insertedTechnicalSkill7 = new TechnicalSkill(
					21, "m", Category.PROCESS.getKey(), user.getUserId(), Stage.ACTIVE.getKey(), 
					creatorAndUpdater, createdAndUpdatedAt, creatorAndUpdater, createdAndUpdatedAt, 1);
			insertedTechnicalSkillList.add(insertedTechnicalSkill7);
			
			when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
				.thenReturn(existingTechnicalSkillList);
			when(addTechnicalSkillListService.addTechnicalSkillList(any())).thenReturn(insertedTechnicalSkillList);
			
		//hadTechnicalSkill
			HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
			existingHadTechnicalSkill1.setTechnicalSkillId(1);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
			HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
			existingHadTechnicalSkill2.setTechnicalSkillId(2);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
			HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
			existingHadTechnicalSkill3.setTechnicalSkillId(3);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
			HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
			existingHadTechnicalSkill4.setTechnicalSkillId(4);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
			HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
			existingHadTechnicalSkill5.setTechnicalSkillId(5);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
			HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
			existingHadTechnicalSkill6.setTechnicalSkillId(6);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
			HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
			existingHadTechnicalSkill7.setTechnicalSkillId(7);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
			
			when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
			
		//usedTechnicalSkill
			UsedTechnicalSkill existingUsedTechnicalSkill1 = new UsedTechnicalSkill(null, 1, 1, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill1);
			UsedTechnicalSkill existingUsedTechnicalSkill2 = new UsedTechnicalSkill(null, 1, 2, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill2);
			UsedTechnicalSkill existingUsedTechnicalSkill3 = new UsedTechnicalSkill(null, 1, 3, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill3);
			UsedTechnicalSkill existingUsedTechnicalSkill4 = new UsedTechnicalSkill(null, 1, 4, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill4);
			UsedTechnicalSkill existingUsedTechnicalSkill5 = new UsedTechnicalSkill(null, 1, 5, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill5);
			UsedTechnicalSkill existingUsedTechnicalSkill6 = new UsedTechnicalSkill(null, 1, 6, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill6);
			UsedTechnicalSkill existingUsedTechnicalSkill7 = new UsedTechnicalSkill(null, 1, 7, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill7);
			existingDevExperience1.setUsedTechnicalSkillList(existingUsedTechnicalSkillList1);
			
			
			Integer result = service.editSpecsheet(form, loginUser);
			assertNull(result);
			
			verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
			verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
			verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
			verify(addDevExperienceService, times(1)).addDevExperienceList(any());
			verify(deleteDevExperieceService, times(1)).deleteListByPrimaryKey(any());
			verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
			verify(addTechnicalSkillListService, times(1)).addTechnicalSkillList(any());
			verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
			verify(addHadTechnicalSkillService, times(1)).insertList(any());
			verify(updateHadTechnicalSkillStageService, times(1))
			.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
			verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
			verify(addUsedTechnicalSkillService, times(1)).addUsedTechnicalSkillList(any());
		
	}
	
	/**
	 * エンジニア本人が編集する<br>
	 * 開発経験に関しては未登録及び登録済のものはなし<br>
	 * テクニカルスキルに関してはhadTechは更新対象がありusedTechはリストサイズ0<br>
	 * 保有テクニカルスキルと使用テクニカルスキルに関しては処理なし
	 */
	@Test
	public void TC5() {
		
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
		form.setAdminComment("管理者コメント");
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
		specsheet.setVersion(1);
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
		
		//devEcperience
		form.setDevExperienceList(devExperienceFormList);
		
		when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
		.thenReturn(existingDevExperienceList);
		
		//technicalSkill
		hadTechnicalSkillList[0] = "A";
		hadTechnicalSkillList[1] = "";
		hadTechnicalSkillList[2] = "c";
		hadTechnicalSkillList[3] = "d";
		hadTechnicalSkillList[4] = "e";
		hadTechnicalSkillList[5] = "";
		hadTechnicalSkillList[6] = "";
		form.setHadTechnicalSkillList(hadTechnicalSkillList);
		
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("A");
		technicalSkill1.setCategory(Category.OS.getKey());
		technicalSkill1.setStage(Stage.TEMPORARY.getKey());
		technicalSkill1.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill1);
		TechnicalSkill technicalSkill2 = new TechnicalSkill();
		technicalSkill2.setTechnicalSkillId(2);
		technicalSkill2.setName("b");
		technicalSkill2.setCategory(Category.LANGUAGE.getKey());
		technicalSkill2.setStage(Stage.TEMPORARY.getKey());
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
		technicalSkill6.setStage(Stage.ACTIVE.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkill7.setStage(Stage.TEMPORARY.getKey());
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
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
		
		//usedTechnicalSkill
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
		verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
	}
	
	/**
	 * エンジニア本人が編集する<br>
	 * 開発経験に関しては登録済のもののみで更新処理をする<br>
	 * テクニカルスキルに関してはhadTechはリストサイズ0でusedTechは子要素がないものがあるリスト<br>
	 * 未登録のものはなく登録済はrequestingに更新<br>
	 * 保有テクニカルスキルは全処理カットで使用テクニカルスキルに関しては削除処理のみ実行する
	 */
	@Test
	public void TC6() {
		
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
			form.setAdminComment("");
			form.setStage(Stage.ACTIVE.getKey());
			form.setVersion(1);
			form.setRawStage(Stage.ACTIVE.getKey());
			
			user.setUserId(1);
			user.setName("tester");
			user.setEmail("email");
			user.setPassword("password");
			authorityList.add(new SimpleGrantedAuthority("ROLE_WEB"));
			loginUser = new LoginUser(user, authorityList);
			
		//specsheet
			specsheet.setVersion(1);
			specsheet.setCreator("tester");
			specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
			
			when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
			
			when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
			
		//devEcperience
			//registeredDev
			devExperienceForm1.setDevExperienceId(1);
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
			//unregisteredDev
			devExperienceForm2.setProjectName("project2");
			devExperienceForm2.setStartedOn("2020-05-01");
			devExperienceForm2.setFinishedOn("2020-05-01");
			devExperienceForm2.setTeamMembers(3);
			devExperienceForm2.setProjectMembers(6);
			devExperienceForm2.setRole("役割");
			devExperienceForm2.setProjectDetails("詳細");
			devExperienceForm2.setTasks("タスク");
			devExperienceForm2.setAcquired("知見");
			devExperienceFormList.add(devExperienceForm2);
			form.setDevExperienceList(devExperienceFormList);
			
			existingDevExperience1.setDevExperienceId(1);
			existingDevExperienceList.add(existingDevExperience1);
			existingDevExperience2.setDevExperienceId(2);
			existingDevExperienceList.add(existingDevExperience2);
			
			when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
				.thenReturn(existingDevExperienceList);
			
		//technicalSkill
			
			utStringList1.add("A");
			utStringList1.add("b");
			utStringList1.add("c");
			utStringList1.add("d");
			utStringList1.add("e");
			utStringList1.add("f");
			utStringList1.add("g");
			usedTechnicalSkillList.add(utStringList1);
			form.setUsedTechnicalSkillList(usedTechnicalSkillList);
			
			TechnicalSkill technicalSkill1 = new TechnicalSkill();
			technicalSkill1.setTechnicalSkillId(1);
			technicalSkill1.setName("a");
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
			technicalSkill4.setStage(Stage.ACTIVE.getKey());
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
			technicalSkill6.setStage(Stage.ACTIVE.getKey());
			technicalSkill6.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill6);
			TechnicalSkill technicalSkill7 = new TechnicalSkill();
			technicalSkill7.setTechnicalSkillId(7);
			technicalSkill7.setName("g");
			technicalSkill7.setCategory(Category.PROCESS.getKey());
			technicalSkill7.setStage(Stage.ACTIVE.getKey());
			technicalSkill7.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill7);
			TechnicalSkill technicalSkill8 = new TechnicalSkill();
			technicalSkill8.setTechnicalSkillId(8);
			technicalSkill8.setName("z");
			technicalSkill8.setCategory(Category.PROCESS.getKey());
			technicalSkill8.setStage(Stage.ACTIVE.getKey());
			technicalSkill8.setVersion(1);
			existingTechnicalSkillList.add(technicalSkill8);
			
			when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
				.thenReturn(existingTechnicalSkillList);
			
		//hadTechnicalSkill
			HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
			existingHadTechnicalSkill1.setTechnicalSkillId(1);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
			HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
			existingHadTechnicalSkill2.setTechnicalSkillId(2);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
			HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
			existingHadTechnicalSkill3.setTechnicalSkillId(3);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
			HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
			existingHadTechnicalSkill4.setTechnicalSkillId(4);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
			HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
			existingHadTechnicalSkill5.setTechnicalSkillId(5);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
			HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
			existingHadTechnicalSkill6.setTechnicalSkillId(6);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
			HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
			existingHadTechnicalSkill7.setTechnicalSkillId(7);
			existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
			
			when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
			
		//usedTechnicalSkill
			UsedTechnicalSkill existingUsedTechnicalSkill1 = new UsedTechnicalSkill(null, 1, 1, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill1);
			UsedTechnicalSkill existingUsedTechnicalSkill2 = new UsedTechnicalSkill(null, 1, 2, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill2);
			UsedTechnicalSkill existingUsedTechnicalSkill3 = new UsedTechnicalSkill(null, 1, 3, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill3);
			UsedTechnicalSkill existingUsedTechnicalSkill4 = new UsedTechnicalSkill(null, 1, 4, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill4);
			UsedTechnicalSkill existingUsedTechnicalSkill5 = new UsedTechnicalSkill(null, 1, 5, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill5);
			UsedTechnicalSkill existingUsedTechnicalSkill6 = new UsedTechnicalSkill(null, 1, 6, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill6);
			UsedTechnicalSkill existingUsedTechnicalSkill7 = new UsedTechnicalSkill(null, 1, 7, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill7);
			UsedTechnicalSkill existingUsedTechnicalSkill8 = new UsedTechnicalSkill(null, 1, 8, Stage.ACTIVE.getKey(), null);
			existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill8);
			existingDevExperience1.setUsedTechnicalSkillList(existingUsedTechnicalSkillList1);
			
			
			Integer result = service.editSpecsheet(form, loginUser);
			assertNull(result);
			
			verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
			verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
			verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
			verify(updateDevExperienceService, times(1)).updateListByPrimaryKey(any());
			verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
			verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
			verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
			verify(updateHadTechnicalSkillStageService, times(1))
			.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
			verify(deleteUsedTechnicalSkillService, times(1)).deleteUsedTechnicalSkill(any());
		
	}
	
	/**
	 * エンジニア本人が編集する<br>
	 * 開発経験に関しては不要な削除対象なし<br>
	 * テクニカルスキルに関してはhadTechはリストサイズ0でusedTechは子要素がないものがあるリスト<br>
	 * 未登録のものはなく登録済はrequestingに更新<br>
	 * 保有テクニカルスキルは全処理カットで使用テクニカルスキルに関しては削除処理のみ実行する
	 */
	@Test
	public void TC7() {
		
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
		form.setAdminComment("");
		form.setStage(Stage.ACTIVE.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.ACTIVE.getKey());
		
		user.setUserId(1);
		user.setName("tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_WEB"));
		loginUser = new LoginUser(user, authorityList);
		
		//specsheet
		specsheet.setVersion(1);
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
		
		//devEcperience
		//registeredDev
		devExperienceForm1.setDevExperienceId(1);
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
		//unregisteredDev
		devExperienceForm2.setProjectName("project2");
		devExperienceForm2.setStartedOn("2020-05-01");
		devExperienceForm2.setFinishedOn("2020-05-01");
		devExperienceForm2.setTeamMembers(3);
		devExperienceForm2.setProjectMembers(6);
		devExperienceForm2.setRole("役割");
		devExperienceForm2.setProjectDetails("詳細");
		devExperienceForm2.setTasks("タスク");
		devExperienceForm2.setAcquired("知見");
		devExperienceFormList.add(devExperienceForm2);
		form.setDevExperienceList(devExperienceFormList);
		
		existingDevExperience1.setDevExperienceId(1);
		existingDevExperienceList.add(existingDevExperience1);
		
		when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
		.thenReturn(existingDevExperienceList);
		
		//technicalSkill
		
		utStringList1.add("A");
		utStringList1.add("b");
		utStringList1.add("c");
		utStringList1.add("d");
		utStringList1.add("e");
		utStringList1.add("f");
		utStringList1.add("g");
		usedTechnicalSkillList.add(utStringList1);
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("a");
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
		technicalSkill4.setStage(Stage.ACTIVE.getKey());
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
		technicalSkill6.setStage(Stage.ACTIVE.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkill7.setStage(Stage.ACTIVE.getKey());
		technicalSkill7.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setTechnicalSkillId(8);
		technicalSkill8.setName("z");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkill8.setStage(Stage.ACTIVE.getKey());
		technicalSkill8.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill8);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
		.thenReturn(existingTechnicalSkillList);
		
		//hadTechnicalSkill
		HadTechnicalSkill existingHadTechnicalSkill1 = new HadTechnicalSkill();
		existingHadTechnicalSkill1.setTechnicalSkillId(1);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill1);
		HadTechnicalSkill existingHadTechnicalSkill2 = new HadTechnicalSkill();
		existingHadTechnicalSkill2.setTechnicalSkillId(2);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill2);
		HadTechnicalSkill existingHadTechnicalSkill3 = new HadTechnicalSkill();
		existingHadTechnicalSkill3.setTechnicalSkillId(3);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill3);
		HadTechnicalSkill existingHadTechnicalSkill4 = new HadTechnicalSkill();
		existingHadTechnicalSkill4.setTechnicalSkillId(4);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill4);
		HadTechnicalSkill existingHadTechnicalSkill5 = new HadTechnicalSkill();
		existingHadTechnicalSkill5.setTechnicalSkillId(5);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill5);
		HadTechnicalSkill existingHadTechnicalSkill6 = new HadTechnicalSkill();
		existingHadTechnicalSkill6.setTechnicalSkillId(6);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill6);
		HadTechnicalSkill existingHadTechnicalSkill7 = new HadTechnicalSkill();
		existingHadTechnicalSkill7.setTechnicalSkillId(7);
		existingHadTechnicalSkillList.add(existingHadTechnicalSkill7);
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
		
		//usedTechnicalSkill
		UsedTechnicalSkill existingUsedTechnicalSkill1 = new UsedTechnicalSkill(null, 1, 1, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill1);
		UsedTechnicalSkill existingUsedTechnicalSkill2 = new UsedTechnicalSkill(null, 1, 2, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill2);
		UsedTechnicalSkill existingUsedTechnicalSkill3 = new UsedTechnicalSkill(null, 1, 3, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill3);
		UsedTechnicalSkill existingUsedTechnicalSkill4 = new UsedTechnicalSkill(null, 1, 4, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill4);
		UsedTechnicalSkill existingUsedTechnicalSkill5 = new UsedTechnicalSkill(null, 1, 5, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill5);
		UsedTechnicalSkill existingUsedTechnicalSkill6 = new UsedTechnicalSkill(null, 1, 6, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill6);
		UsedTechnicalSkill existingUsedTechnicalSkill7 = new UsedTechnicalSkill(null, 1, 7, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill7);
		UsedTechnicalSkill existingUsedTechnicalSkill8 = new UsedTechnicalSkill(null, 1, 8, Stage.ACTIVE.getKey(), null);
		existingUsedTechnicalSkillList1.add(existingUsedTechnicalSkill8);
		existingDevExperience1.setUsedTechnicalSkillList(existingUsedTechnicalSkillList1);
		
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
		verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
		verify(updateDevExperienceService, times(1)).updateListByPrimaryKey(any());
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(updateTechnicalSkillService, times(1)).updateRequestUserIdAndStageByPrimaryKeyAsList(any());
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(updateHadTechnicalSkillStageService, times(1))
		.updateStage(any(HadTechnicalSkill.class), any(HadTechnicalSkillExample.class));
		verify(deleteHadTechnicalSkillService, times(1)).deleteHadTechnicalSkill(any());
		verify(deleteUsedTechnicalSkillService, times(1)).deleteUsedTechnicalSkill(any());
		
	}
	
	/**
	 * エンジニア本人が一時保存する<br>
	 * 開発経験、使用テクニカルスキルに関してはフォームへの入力なしとして実行する
	 */
	@Test
	public void TC8() {
		
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
		form.setAdminComment("");
		form.setStage(Stage.TEMPORARY.getKey());
		form.setVersion(1);
		form.setRawStage(Stage.TEMPORARY.getKey());
		
		user.setUserId(1);
		user.setName("tester");
		user.setEmail("email");
		user.setPassword("password");
		authorityList.add(new SimpleGrantedAuthority("ROLE_WEB"));
		loginUser = new LoginUser(user, authorityList);
		
		//specsheet
		specsheet.setVersion(1);
		specsheet.setCreator("tester");
		specsheet.setCreatedAt(LocalDateTime.of(2020, 5, 5, 10, 10, 10));
		
		when(getNewestSpecsheetService.getNewestSpecsheet(any(Integer.class))).thenReturn(specsheet);
		
		when(updateSpecsheetService.updateSpecsheet(any(AppSpecsheet.class))).thenReturn(1);
		
		//devEcperience
		form.setDevExperienceList(devExperienceFormList);
		
		when(getDevExperienceService.getDevExperienceBySpecsheetId(any(Integer.class)))
		.thenReturn(existingDevExperienceList);
		
		//technicalSkill
		form.setUsedTechnicalSkillList(usedTechnicalSkillList);
		
		TechnicalSkill technicalSkill1 = new TechnicalSkill();
		technicalSkill1.setTechnicalSkillId(1);
		technicalSkill1.setName("a");
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
		technicalSkill4.setStage(Stage.ACTIVE.getKey());
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
		technicalSkill6.setStage(Stage.ACTIVE.getKey());
		technicalSkill6.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill6);
		TechnicalSkill technicalSkill7 = new TechnicalSkill();
		technicalSkill7.setTechnicalSkillId(7);
		technicalSkill7.setName("g");
		technicalSkill7.setCategory(Category.PROCESS.getKey());
		technicalSkill7.setStage(Stage.ACTIVE.getKey());
		technicalSkill7.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill7);
		TechnicalSkill technicalSkill8 = new TechnicalSkill();
		technicalSkill8.setTechnicalSkillId(8);
		technicalSkill8.setName("z");
		technicalSkill8.setCategory(Category.PROCESS.getKey());
		technicalSkill8.setStage(Stage.ACTIVE.getKey());
		technicalSkill8.setVersion(1);
		existingTechnicalSkillList.add(technicalSkill8);
		
		when(getTechnicalSkillListService.getTechnicalSkillList(any(TechnicalSkillExample.class)))
		.thenReturn(existingTechnicalSkillList);
		
		when(getHadTechnicalSkillListService.getByUserId(any(Integer.class))).thenReturn(existingHadTechnicalSkillList);
		
		Integer result = service.editSpecsheet(form, loginUser);
		assertNull(result);
		
		verify(getNewestSpecsheetService, times(1)).getNewestSpecsheet(any(Integer.class));
		verify(updateSpecsheetService, times(1)).updateSpecsheet(any(AppSpecsheet.class));
		verify(getDevExperienceService, times(1)).getDevExperienceBySpecsheetId(any(Integer.class));
		verify(getTechnicalSkillListService, times(1)).getTechnicalSkillList(any(TechnicalSkillExample.class));
		verify(getHadTechnicalSkillListService, times(1)).getByUserId(any(Integer.class));
		verify(updateHadTechnicalSkillStageService, times(1)).updateStageByList(any());
		
	}

}
