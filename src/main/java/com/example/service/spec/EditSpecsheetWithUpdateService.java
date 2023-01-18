package com.example.service.spec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.domain.UsedTechnicalSkill;
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

@Service
@Transactional
public class EditSpecsheetWithUpdateService {
	@Autowired
	private GetNewestSpecsheetService getNewestSpecsheetService;
	@Autowired
	private UpdateSpecsheetService updateSpecsheetService;
	@Autowired
	private GetDevExperienceService getDevExperienceService;
	@Autowired
	private AddDevExperienceService addDevExperienceService;
	@Autowired
	private UpdateDevExperienceService updateDevExperienceService;
	@Autowired
	private DeleteDevExperieceService deleteDevExperieceService;
	@Autowired
	private GetTechnicalSkillListService getTechnicalSkillListService;
	@Autowired
	private AddTechnicalSkillListService addTechnicalSkillListService;
	@Autowired
	private UpdateTechnicalSkillService updateTechnicalSkillService;
	@Autowired
	private GetHadTechnicalSkillListService getHadTechnicalSkillListService;
	@Autowired
	private AddHadTechnicalSkillService addHadTechnicalSkillService;
	@Autowired
	private UpdateHadTechnicalSkillStageService updateHadTechnicalSkillStageService;
	@Autowired
	private DeleteHadTechnicalSkillService deleteHadTechnicalSkillService;
	@Autowired
	private AddUsedTechnicalSkillService addUsedTechnicalSkillService;
	@Autowired
	private DeleteUsedTechnicalSkillService deleteUsedTechnicalSkillService;
	
	/**
	 * スペックシート編集処理をするメソッド.
	 * 
	 * @param form スペックシート編集フォーム
	 * @param loginUser ログイン中のユーザー
	 */
	public Integer editSpecsheet(EditSpecsheetForm form, LoginUser loginUser) {
		String creatorAndUpdater = loginUser.getUser().getName();
		LocalDateTime createAndUpdateAt = LocalDateTime.now();
		
//specsheet
		//【update spec】
		AppSpecsheet specsheet = getNewestSpecsheetService.getNewestSpecsheet(form.getUserId());
		//check version
		if(!form.getVersion().equals(specsheet.getVersion()) 
				&& !Stage.TEMPORARY.getKey().equals(specsheet.getStage())) {
			return specsheet.getStage();
		}
		BeanUtils.copyProperties(form, specsheet);
		if (StringUtils.hasText(form.getGender())) {
			specsheet.setGender(form.getGender().charAt(0));
		}
		specsheet.setUpdater(creatorAndUpdater);
		specsheet.setUpdatedAt(createAndUpdateAt);
		specsheet.setVersion(form.getVersion());
		int updateSpecCount = updateSpecsheetService.updateSpecsheet(specsheet);
		if(updateSpecCount == 0) {
			specsheet = getNewestSpecsheetService.getNewestSpecsheet(form.getUserId());
			return specsheet.getStage();
		}
		System.out.println("updatedSpecsheet : "+specsheet);
		
//devExperience
		//【divide devExperiences into exist and non】
		//convert devExperienceList(devExperienceForm[]) into List<DevExperience>
		Integer specsheetId = specsheet.getSpecsheetId();
		List<DevExperience> inputDevExperienceList = new ArrayList<>();
		List<DevExperienceForm> devExperienceFormList = form.getDevExperienceList();
		devExperienceFormList.stream().forEach(def -> {
			DevExperience devExperience = new DevExperience();
			System.out.println(def);
			BeanUtils.copyProperties(def, devExperience);
			devExperience.setSpecsheetId(specsheetId);
			if (StringUtils.hasText(def.getStartedOn())) {
				devExperience.setStartedOn(LocalDate.parse(def.getStartedOn()));
			}
			if (StringUtils.hasText(def.getFinishedOn())) {
				devExperience.setFinishedOn(LocalDate.parse(def.getFinishedOn()));
			}
			devExperience.setStage(Stage.ACTIVE.getKey());
			inputDevExperienceList.add(devExperience);
		});
		System.out.println("inputDevExperienceList : "+inputDevExperienceList);
		
		//get existing devExperienceList
		List<DevExperience> existingDevExperienceList = 
				getDevExperienceService.getDevExperienceBySpecsheetId(specsheetId);
		System.out.println("existingDevExperienceList : "+existingDevExperienceList);
		
		//divide devExperiences into exist and non
		List<DevExperience> registeredDevExperienceList = new ArrayList<>();
		List<DevExperience> unregisteredDevExperienceList = new ArrayList<>();;
		inputDevExperienceList.forEach(input ->
			existingDevExperienceList.stream()
				.filter(existing -> existing.getDevExperienceId().equals(input.getDevExperienceId()))
				.forEach(existing -> {
					List<UsedTechnicalSkill> utList = existing.getUsedTechnicalSkillList();
					BeanUtils.copyProperties(input, existing);
					existing.setUsedTechnicalSkillList(utList);
					registeredDevExperienceList.add(existing);
					})
		);
		System.out.println("registeredDevExperienceList : "+registeredDevExperienceList);
		inputDevExperienceList.stream()
		.filter(input -> existingDevExperienceList.stream()
				.allMatch(existing -> !existing.getDevExperienceId().equals(input.getDevExperienceId())))
		.forEach(input -> unregisteredDevExperienceList.add(input)); 
		System.out.println("unregisteredDevExperienceList : "+unregisteredDevExperienceList);
		
		//【insert devExperienceList】
		List<DevExperience> insertedDevExperienceList = new ArrayList<>();
		if(unregisteredDevExperienceList.size() != 0) {
			insertedDevExperienceList = addDevExperienceService.addDevExperienceList(unregisteredDevExperienceList);
		}
		
		//【update devExperienceList】
		if(registeredDevExperienceList.size() != 0) {
			updateDevExperienceService.updateListByPrimaryKey(registeredDevExperienceList);
		}
		
		//【delete unnecessary devExperience】
		//collect unnecessary devExperiences
		List<DevExperience> unnecessaryDevExperienceList = new ArrayList<>();
		existingDevExperienceList.stream()
		.filter(existing -> inputDevExperienceList.stream()
				.allMatch(input -> !existing.getDevExperienceId().equals(input.getDevExperienceId())))
		.forEach(existing -> unnecessaryDevExperienceList.add(existing));
				
		//delete unnecessary devExperienceList
		List<DevExperience> existingDevExperienceListForUt = new ArrayList<>();
		if(unnecessaryDevExperienceList.size() != 0) {
			deleteDevExperieceService.deleteListByPrimaryKey(unnecessaryDevExperienceList);
			//collect unnecessary from existing
			existingDevExperienceList.stream()
			.filter(existing -> unnecessaryDevExperienceList.stream()
					.allMatch(unnecessary -> !unnecessary.getDevExperienceId().equals(existing.getDevExperienceId())))
			.forEach(existing -> existingDevExperienceListForUt.add(existing));
		}else {
			existingDevExperienceList.forEach(exist -> existingDevExperienceListForUt.add(exist));
		}
		System.out.println("existingDevExperienceListForUt : "+existingDevExperienceListForUt);
		
		//set devExperienceId into input from inserted
		List<DevExperience> inputDevExperienceListForUt = Stream.concat(registeredDevExperienceList.stream(), insertedDevExperienceList.stream())
				.distinct()
				.collect(Collectors.toList());
		
//technicalSkill
		//【divide ht's technicalSkills into exist and non】
		//convert hadTechnicalSkillList(String[]) into List<TechnicalSkill>
		List<TechnicalSkill> inputHtTechnicalSkillList = new ArrayList<>();
		if(!Objects.isNull(form.getHadTechnicalSkillList())) {
			for(int i = 0; i < form.getHadTechnicalSkillList().length; i++) {
				if(!form.getHadTechnicalSkillList()[i].isEmpty()) {
					for(String str: Arrays.asList(form.getHadTechnicalSkillList()[i].split(","))) {
						TechnicalSkill technicalSkill = new TechnicalSkill();
						technicalSkill.setName(StringUtils.trimAllWhitespace(str));
						technicalSkill.setCategory(i + 1);
						inputHtTechnicalSkillList.add(technicalSkill);
					}
				}
			}
		}
		inputHtTechnicalSkillList.stream().distinct().collect(Collectors.toList());
		System.out.println("inputHtTechnicalSkillList : "+inputHtTechnicalSkillList);
		
		//get existing technicalSkillList
		TechnicalSkillExample example = new TechnicalSkillExample();
		example.or().andStageEqualTo(Stage.ACTIVE.getKey().toString());
		example.or().andStageEqualTo(Stage.TEMPORARY.getKey().toString());
		example.or().andStageEqualTo(Stage.REQUESTING.getKey().toString());
		List<TechnicalSkill> existingTechnicalSkillList = getTechnicalSkillListService.getTechnicalSkillList(example);
		System.out.println("existingTechnicalSkillList : "+existingTechnicalSkillList);
		
		//divide ht's technicalSkills into exist and non
		List<TechnicalSkill> registeredInputHtTechnicalSkillList = new ArrayList<>();
		List<TechnicalSkill> unregisteredInputHtTechnicalSkillList = new ArrayList<>();
		existingTechnicalSkillList.stream()
		.filter(existing -> inputHtTechnicalSkillList.stream()
				.anyMatch(((Predicate<TechnicalSkill>)(input -> existing.getName().equalsIgnoreCase(input.getName())))
						.and(((Predicate<TechnicalSkill>)(input -> existing.getCategory().equals(input.getCategory()))))))
		.forEach(existing -> registeredInputHtTechnicalSkillList.add(existing));
		System.out.println("registeredInputHtTechnicalSkillList : "+registeredInputHtTechnicalSkillList);
		inputHtTechnicalSkillList.stream()
		.filter(input -> existingTechnicalSkillList.stream()
				.allMatch(((Predicate<TechnicalSkill>)(existing -> !existing.getName().equalsIgnoreCase(input.getName())))
						.or(((Predicate<TechnicalSkill>)(existing -> !existing.getCategory().equals(input.getCategory()))))))
		.forEach(input -> unregisteredInputHtTechnicalSkillList.add(input));
		System.out.println("unregisteredInputHtTechnicalSkillList : "+unregisteredInputHtTechnicalSkillList);
		
		//【divide ut's technicalSkills into exist and non】
		//convert usedTechnicalSkillList(List<List<String>>) into List<TechnicalSkill> and List<UsedTechnicalSkill>
		List<TechnicalSkill> inputUtTechnicalSkillList = new ArrayList<>();
		List<UsedTechnicalSkill> inputUsedTechnicalSkillList = new ArrayList<>();
		for(int i = 0; i < form.getUsedTechnicalSkillList().size(); i++) {
			for(int j = 0; j < form.getUsedTechnicalSkillList().get(i).size(); j++) {
				if(!form.getUsedTechnicalSkillList().get(i).get(j).isEmpty()) {
					for(String str: form.getUsedTechnicalSkillList().get(i).get(j).split(",")) {
						UsedTechnicalSkill usedTechnicalSkill = new UsedTechnicalSkill();
						TechnicalSkill technicalSkill = new TechnicalSkill();
						technicalSkill.setName(StringUtils.trimAllWhitespace(str));
						technicalSkill.setCategory(j + 1);
						inputUtTechnicalSkillList.add(technicalSkill);
						usedTechnicalSkill.setTechnicalSkill(technicalSkill);
						usedTechnicalSkill.setDevExperienceId(
								inputDevExperienceListForUt.get(i).getDevExperienceId());
						inputUsedTechnicalSkillList.add(usedTechnicalSkill);
					}
				}
			}
		}
		inputUtTechnicalSkillList.stream().distinct().collect(Collectors.toList());
		inputUsedTechnicalSkillList.stream().distinct().collect(Collectors.toList());
		System.out.println("inputUtTechnicalSkillList : "+inputUtTechnicalSkillList);
		System.out.println("inputUsedTechnicalSkillList : "+inputUsedTechnicalSkillList);
		
		//divide ut's technicalSkills into exist and non
		List<TechnicalSkill> registeredInputUtTechnicalSkillList = new ArrayList<>();
		List<TechnicalSkill> unregisteredInputUtTechnicalSkillList = new ArrayList<>();
		existingTechnicalSkillList.stream()
		.filter(existing -> inputUtTechnicalSkillList.stream()
				.anyMatch(((Predicate<TechnicalSkill>)(input -> existing.getName().equalsIgnoreCase(input.getName())))
						.and(((Predicate<TechnicalSkill>)(input -> existing.getCategory().equals(input.getCategory()))))))
		.forEach(existing -> registeredInputUtTechnicalSkillList.add(existing));
		System.out.println("registeredInputUtTechnicalSkillList : "+registeredInputUtTechnicalSkillList);
		inputUtTechnicalSkillList.stream()
		.filter(input -> existingTechnicalSkillList.stream()
				.allMatch(((Predicate<TechnicalSkill>)(existing -> !existing.getName().equalsIgnoreCase(input.getName())))
						.or(((Predicate<TechnicalSkill>)(existing -> !existing.getCategory().equals(input.getCategory()))))))
		.forEach(input -> unregisteredInputUtTechnicalSkillList.add(input));
		System.out.println("unregisteredInputUtTechnicalSkillList : "+unregisteredInputUtTechnicalSkillList);
		
		//【insert technicalSkill】
		List<TechnicalSkill> technicalSkillListForInsert = 
			Stream.concat(unregisteredInputHtTechnicalSkillList.stream(), unregisteredInputUtTechnicalSkillList.stream())
			.distinct()
			.collect(Collectors.toList());
		technicalSkillListForInsert.forEach(tech -> {
			tech.setRequestUserId(loginUser.getUser().getUserId());
			tech.setStage(form.getStage());
			tech.setCreator(creatorAndUpdater);
			tech.setCreatedAt(createAndUpdateAt);
			tech.setUpdater(creatorAndUpdater);
			tech.setUpdatedAt(createAndUpdateAt);
			tech.setVersion(1);
		});
		List<TechnicalSkill> insertedTechnicalSkillList = new ArrayList<>();
		if(technicalSkillListForInsert.size() != 0) {
			insertedTechnicalSkillList = addTechnicalSkillListService.addTechnicalSkillList(technicalSkillListForInsert);
		}
		System.out.println("technicalSkillListForInsert : "+technicalSkillListForInsert);
		System.out.println("insertedTechnicalSkillList : "+insertedTechnicalSkillList);
		
		//【update technicalSkill stage】
		List<TechnicalSkill> technicalSkillListForUpdate = new ArrayList<>();
		List<TechnicalSkill> registeredInputTechnicalSkillList = 
				Stream.concat(registeredInputHtTechnicalSkillList.stream(), registeredInputUtTechnicalSkillList.stream())
				.distinct()
				.collect(Collectors.toList());
		
		//the case of spec's ACTIVE, technicalSkill stage 1,2→0
		if(form.getStage().equals(Stage.ACTIVE.getKey())) {
			registeredInputTechnicalSkillList.stream()
			.filter(((Predicate<TechnicalSkill>)(tech -> tech.getStage().equals(Stage.TEMPORARY.getKey())))
					.or((Predicate<TechnicalSkill>)(tech -> tech.getStage().equals(Stage.REQUESTING.getKey()))))
			.forEach(tech -> {
				tech.setStage(Stage.ACTIVE.getKey());
				tech.setUpdater(creatorAndUpdater);
				tech.setUpdatedAt(createAndUpdateAt);
				technicalSkillListForUpdate.add(tech);
			});
			System.out.println("the case of ACTIVE, technicalSkillListForUpdate : "+technicalSkillListForUpdate);
		}
		
		//the case of spec's REQUESTING, technicalSkill stage 1→2
		if(form.getStage().equals(Stage.REQUESTING.getKey())) {
			registeredInputTechnicalSkillList.stream()
			.filter(tech -> tech.getStage().equals(Stage.TEMPORARY.getKey()))
			.forEach(tech -> {
				tech.setRequestUserId(loginUser.getUser().getUserId());
				tech.setStage(Stage.REQUESTING.getKey());
				tech.setUpdater(creatorAndUpdater);
				tech.setUpdatedAt(createAndUpdateAt);
				technicalSkillListForUpdate.add(tech);
			});
			System.out.println("the case of REQUESTING, technicalSkillListForUpdate : "+technicalSkillListForUpdate);
		}
		
		//update technicalSkill stage
		if(technicalSkillListForUpdate.size() != 0) {
			updateTechnicalSkillService.updateRequestUserIdAndStageByPrimaryKeyAsList(technicalSkillListForUpdate);
		}
		
//hadTechnicalSkill
		//【collect input hadTechnicalSkill】
		//set inserted technicalSkillId on ht's unregistered list
		insertedTechnicalSkillList.forEach(inserted -> 
		unregisteredInputHtTechnicalSkillList.stream()
		.filter(unregistered -> unregistered.getName().equalsIgnoreCase(inserted.getName()))
		.filter(unregistered -> unregistered.getCategory().equals(inserted.getCategory()))
		.forEach(unregistered -> unregistered.setTechnicalSkillId(inserted.getTechnicalSkillId()))
				);
		System.out.println("unregisteredInputHtTechnicalSkillList : "+unregisteredInputHtTechnicalSkillList);
		
		//set technicalSkill on hadTechnicalSkillList
		List<TechnicalSkill> technicalSkillListForHadTechnicalSkill = 
			Stream.concat(registeredInputHtTechnicalSkillList.stream(), unregisteredInputHtTechnicalSkillList.stream())
			.collect(Collectors.toList());
		List<HadTechnicalSkill> inputHadTechnicalSkillList = new ArrayList<>();
		technicalSkillListForHadTechnicalSkill.forEach(tech -> {
			HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
			hadTechnicalSkill.setUserId(form.getUserId());
			hadTechnicalSkill.setTechnicalSkillId(tech.getTechnicalSkillId());
			hadTechnicalSkill.setStage(form.getStage());
			hadTechnicalSkill.setCreator(creatorAndUpdater);
			hadTechnicalSkill.setCreatedAt(createAndUpdateAt);
			hadTechnicalSkill.setUpdater(creatorAndUpdater);
			hadTechnicalSkill.setUpdatedAt(createAndUpdateAt);
			hadTechnicalSkill.setTechnicalSkill(tech);
			inputHadTechnicalSkillList.add(hadTechnicalSkill);
		});
		System.out.println("inputHadTechnicalSkillList : "+inputHadTechnicalSkillList);
		
		//get existing hadTechnicalSkillList
		List<HadTechnicalSkill> existingHadTechnicalSkillList = 
				getHadTechnicalSkillListService.getByUserId(form.getUserId());
		System.out.println("existingHadTechnicalSkillList : "+existingHadTechnicalSkillList);
		
		//set stage on inputHadTechnicalSkill
		List<HadTechnicalSkill> registeredHadTechnicalSkillList = new ArrayList<>();
		existingHadTechnicalSkillList.stream().forEach(existing -> 
			inputHadTechnicalSkillList.stream()
			.filter(input -> existing.getTechnicalSkillId().equals(input.getTechnicalSkillId()))
			.forEach(input -> {
				input.setHadTechnicalSkillId(existing.getHadTechnicalSkillId());
				input.setStage(existing.getStage());
				registeredHadTechnicalSkillList.add(input);
			})
		);
		
		//【insert unregistered hadTechnicalSkill】
		//collect unregistered hadTechnicalSkills
		List<HadTechnicalSkill> unregisteredHadTechnicalSkillList = new ArrayList<>();
		inputHadTechnicalSkillList.stream()
		.filter(input -> existingHadTechnicalSkillList.stream()
				.allMatch(existing -> !existing.getTechnicalSkillId().equals(input.getTechnicalSkillId())))
		.forEach(input -> unregisteredHadTechnicalSkillList.add(input));
		
		//insert unregistered hadTechnicalSkillList
		if(unregisteredHadTechnicalSkillList.size() != 0) {
			addHadTechnicalSkillService.insertList(unregisteredHadTechnicalSkillList);
		}
		System.out.println("unregisteredHadTechnicalSkillList : "+unregisteredHadTechnicalSkillList);
		
		//【update hadTechnicalSkill stage】
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkill.setStage(form.getStage());
		hadTechnicalSkill.setUpdater(creatorAndUpdater);
		hadTechnicalSkill.setUpdatedAt(createAndUpdateAt);
		HadTechnicalSkillExample htExample2 = new HadTechnicalSkillExample();
		//the case of spec's ACTIVE, hadTechnicalSkill stage 2,3→0
		if(Stage.ACTIVE.getKey().equals(form.getStage())) {
			htExample2.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.REQUESTING.getKey().toString());
			htExample2.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.SENT_BACK.getKey().toString());
			updateHadTechnicalSkillStageService.updateStage(hadTechnicalSkill, htExample2);
		}
		
		//the case of spec's REQUESTING, hadTechnicalSkill stage 1,3→2
		if(Stage.REQUESTING.getKey().equals(form.getStage())) {
			htExample2.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.TEMPORARY.getKey().toString());
			htExample2.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.SENT_BACK.getKey().toString());
			updateHadTechnicalSkillStageService.updateStage(hadTechnicalSkill, htExample2);
		}
		
		//input hadTechnicalSkill stage 9→0 (registered, and re-input)
		List<HadTechnicalSkill> inputDELETEDhadTechnicalSkillList = new ArrayList<>();
		registeredHadTechnicalSkillList.stream()
		.filter(input -> Stage.DELETED.getKey().equals(input.getStage()))
		.forEach(input -> {
			input.setStage(Stage.ACTIVE.getKey());
			inputDELETEDhadTechnicalSkillList.add(input);
		});
		int result = updateHadTechnicalSkillStageService.updateStageByList(inputDELETEDhadTechnicalSkillList);
		System.out.println(result);
		System.out.println("inputDELETEDhadTechnicalSkillList : "+inputDELETEDhadTechnicalSkillList);
		
		//【delete or update unnecessary hadTechnicalSkill】
		//collect unnecessary hadTechnicalSkills
		List<HadTechnicalSkill> unnecessaryHadTechnicalSkillList = new ArrayList<>();
		existingHadTechnicalSkillList.stream()
		.filter(existing -> registeredHadTechnicalSkillList.stream()
				.allMatch(input -> !input.getTechnicalSkillId().equals(existing.getTechnicalSkillId())))
		.forEach(existing -> unnecessaryHadTechnicalSkillList.add(existing));
		
		//delete or update unnecessary hadTechnicalSkillList
		if(unnecessaryHadTechnicalSkillList.size() != 0) {
			if(Stage.ACTIVE.getKey().equals(form.getStage())) {
				System.out.println("unnecessaryHadTechnicalSkillList : "+unnecessaryHadTechnicalSkillList);
				//collect unnecessary hadTechnicalSkillList for delete
				List<HadTechnicalSkill> stageDELETEDHadTechnicalSkillList = 
						registeredHadTechnicalSkillList.stream()
						.filter(input -> Stage.DELETED.getKey().equals(input.getStage()))
						.collect(Collectors.toList());
				System.out.println("stageDELETEDHadTechnicalSkillList : "+stageDELETEDHadTechnicalSkillList);
				List<HadTechnicalSkill> unnecessaryHadTechnicalSkillListForDelete =
						Stream.concat(unnecessaryHadTechnicalSkillList.stream(), stageDELETEDHadTechnicalSkillList.stream())
						.collect(Collectors.toList());
				
				deleteHadTechnicalSkillService.deleteHadTechnicalSkill(unnecessaryHadTechnicalSkillListForDelete);
				System.out.println("case of ACTIVE, delete unnecessaryHadTechnicalSkillListForDelete : "+unnecessaryHadTechnicalSkillListForDelete);
			}else {
				//delete unnecessary not ACTIVE or DELETED hadTechnicalSkill
				List<HadTechnicalSkill> unnecessaryHadTechnicalSkillListForDelete =
						unnecessaryHadTechnicalSkillList.stream()
						.filter(unnecessary -> !Stage.ACTIVE.getKey().equals(unnecessary.getStage()))
						.filter(unnecessary -> !Stage.DELETED.getKey().equals(unnecessary.getStage()))
						.collect(Collectors.toList());
				deleteHadTechnicalSkillService.deleteHadTechnicalSkill(unnecessaryHadTechnicalSkillListForDelete);
				System.out.println("case of not ACTIVE, delete unnecessaryHadTechnicalSkillListForDelete : "
						+unnecessaryHadTechnicalSkillListForDelete);
				
				//update unnecessary hadTechnicalSkill stage 0→9
				List<HadTechnicalSkill> unnecessaryACTIVEHadTechnicalSkillList = new ArrayList<>();
				unnecessaryHadTechnicalSkillList.stream()
						.filter(unnecessary -> Stage.ACTIVE.getKey().equals(unnecessary.getStage()))
						.forEach(unnecessary -> {
							unnecessary.setStage(Stage.DELETED.getKey());
							unnecessaryACTIVEHadTechnicalSkillList.add(unnecessary);
						});
				updateHadTechnicalSkillStageService.updateStageByList(unnecessaryACTIVEHadTechnicalSkillList);
				System.out.println("case of not ACTIVE, update unnecessaryACTIVEHadTechnicalSkillList : "
						+unnecessaryACTIVEHadTechnicalSkillList);
			}
		}
		
//usedTechnicalSkill
		//【divide usedTechnicalSkills into exist and non】
		//set inserted technicalSkillId on ut's unregistered list
		insertedTechnicalSkillList.forEach(inserted -> 
		unregisteredInputUtTechnicalSkillList.stream()
		.filter(unregistered -> unregistered.getName().equalsIgnoreCase(inserted.getName()))
		.filter(unregistered -> unregistered.getCategory().equals(inserted.getCategory()))
		.forEach(unregistered -> unregistered.setTechnicalSkillId(inserted.getTechnicalSkillId()))
				);

		//set technicalSkill on usedTechnicalSkillList
		List<TechnicalSkill> technicalSkillListForUsedTechnicalSkill = 
			Stream.concat(registeredInputUtTechnicalSkillList.stream(), unregisteredInputUtTechnicalSkillList.stream())
			.collect(Collectors.toList());
		technicalSkillListForUsedTechnicalSkill.forEach(tech -> 
			inputUsedTechnicalSkillList.stream()
			.filter(ut -> ut.getTechnicalSkill().getName().equalsIgnoreCase(tech.getName()))
			.filter(ut -> ut.getTechnicalSkill().getCategory().equals(tech.getCategory()))
			.forEach(ut -> ut.setTechnicalSkillId(tech.getTechnicalSkillId()))
		);
		System.out.println("inputUsedTechnicalSkillList : "+inputUsedTechnicalSkillList);
		
		//divide usedTechnicalSkills into exist and non
		List<UsedTechnicalSkill> existingUsedTechnicalSkillList = new ArrayList<>();
		existingDevExperienceListForUt.forEach(
				existing -> existing.getUsedTechnicalSkillList().forEach(
						existingUt -> existingUsedTechnicalSkillList.add(existingUt)));
		List<UsedTechnicalSkill> registeredUsedTechnicalSkillList = new ArrayList<>();
		List<UsedTechnicalSkill> unregisteredUsedTechnicalSkillList = new ArrayList<>();
		existingUsedTechnicalSkillList.forEach(existingUt ->
			inputUsedTechnicalSkillList.stream()
			.filter(input -> existingUt.getTechnicalSkillId().equals(input.getTechnicalSkillId()))
			.filter(input -> existingUt.getDevExperienceId().equals(input.getDevExperienceId()))
			.forEach(input -> {
				registeredUsedTechnicalSkillList.add(existingUt);
			})
		);
		inputUsedTechnicalSkillList.stream()
		.filter(input -> existingUsedTechnicalSkillList.stream()
				.allMatch(((Predicate<UsedTechnicalSkill>)(existing -> !existing.getDevExperienceId().equals(input.getDevExperienceId())))
						.or((Predicate<UsedTechnicalSkill>)(existing -> !existing.getTechnicalSkillId().equals(input.getTechnicalSkillId())))))
		.forEach(input -> unregisteredUsedTechnicalSkillList.add(input));
		System.out.println("registeredUsedTechnicalSkillList : "+registeredUsedTechnicalSkillList);
		System.out.println("unregisteredUsedTechnicalSkillList : "+unregisteredUsedTechnicalSkillList);
		
		//【insert usedTechnicalSkill】
		if(unregisteredUsedTechnicalSkillList.size() != 0) {
			addUsedTechnicalSkillService.addUsedTechnicalSkillList(unregisteredUsedTechnicalSkillList);
		}
		
		//【delete unnecessary usedTechnicalSkill】
		//collect unnecessary usedTechnicalSkills
		List<UsedTechnicalSkill> unnecessaryUsedTechnicalSkill = new ArrayList<>();
		existingUsedTechnicalSkillList.stream()
		.filter(existing -> inputUsedTechnicalSkillList.stream()
				.allMatch(((Predicate<UsedTechnicalSkill>)(input -> !existing.getDevExperienceId().equals(input.getDevExperienceId())))
						.or((Predicate<UsedTechnicalSkill>)(input -> !existing.getTechnicalSkillId().equals(input.getTechnicalSkillId())))))
		.forEach(existing -> unnecessaryUsedTechnicalSkill.add(existing));
		System.out.println("unnecessaryUsedTechnicalSkill : "+unnecessaryUsedTechnicalSkill);
		if(unnecessaryUsedTechnicalSkill.size() != 0) {
			deleteUsedTechnicalSkillService.deleteUsedTechnicalSkill(unnecessaryUsedTechnicalSkill);
		}
		
		return null;
	}

}
