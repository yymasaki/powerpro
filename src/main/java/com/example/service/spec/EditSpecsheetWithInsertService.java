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
public class EditSpecsheetWithInsertService {
	@Autowired
	private GetNewestSpecsheetService getNewestSpecsheetService;
	@Autowired
	private AddSpecsheetService addSpecsheetService;
	@Autowired
	private AddDevExperienceService addDevExperienceService;
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
		//【insert spec】
		AppSpecsheet specsheet = new AppSpecsheet();
		BeanUtils.copyProperties(form, specsheet);
		if (StringUtils.hasText(form.getGender())) {
			specsheet.setGender(form.getGender().charAt(0));
		}
		specsheet.setCreator(creatorAndUpdater);
		specsheet.setUpdater(creatorAndUpdater);
		specsheet.setCreatedAt(createAndUpdateAt);
		specsheet.setUpdatedAt(createAndUpdateAt);
		Integer version = form.getVersion();
		if(Objects.nonNull(version)) {
			specsheet.setVersion(version + 1);
		}else {
			specsheet.setVersion(1);
		}
		//check version
		if(Objects.nonNull(form.getSpecsheetId())) {
			AppSpecsheet newestSpecsheet = getNewestSpecsheetService.getNewestSpecsheet(form.getUserId());
			if(!form.getVersion().equals(newestSpecsheet.getVersion()) 
					&& !Stage.TEMPORARY.getKey().equals(newestSpecsheet.getStage())) {
				return newestSpecsheet.getStage();
			}
		}
		System.out.println("specsheet before insert : "+specsheet);
		specsheet = addSpecsheetService.addSpecsheet(specsheet);
		System.out.println("specsheet after insert : "+specsheet);
		
//devExperience
		//【insert devExperience】
		Integer insertedSpecsheetId = specsheet.getSpecsheetId();
		List<DevExperience> inputDevExperienceList = new ArrayList<>();
		List<DevExperienceForm> devExperienceFormList = form.getDevExperienceList();
		devExperienceFormList.stream().forEach(def -> {
			DevExperience devExperience = new DevExperience();
			BeanUtils.copyProperties(def, devExperience);
			devExperience.setSpecsheetId(insertedSpecsheetId);
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
		List<DevExperience> insertedDevExperienceList = new ArrayList<>();
		if(inputDevExperienceList.size() != 0) {
			insertedDevExperienceList = addDevExperienceService.addDevExperienceList(inputDevExperienceList);
			System.out.println("insertedDevExperienceList : "+insertedDevExperienceList);
		}
		
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
								insertedDevExperienceList.get(i).getDevExperienceId());
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
		System.out.println("registeredHadTechnicalSkillList : "+registeredHadTechnicalSkillList);
		
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
		//the case of spec's ACTIVE, hadTechnicalSkill stage 1→0 (when engineer inserted temporary)
		if(Stage.ACTIVE.getKey().equals(form.getStage())) {
			HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
			hadTechnicalSkill.setStage(form.getStage());
			hadTechnicalSkill.setUpdater(creatorAndUpdater);
			hadTechnicalSkill.setUpdatedAt(createAndUpdateAt);
			HadTechnicalSkillExample htExample2 = new HadTechnicalSkillExample();
			htExample2.createCriteria()
			.andUserIdEqualTo(form.getUserId())
			.andStageEqualTo(Stage.TEMPORARY.getKey().toString());
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
		updateHadTechnicalSkillStageService.updateStageByList(inputDELETEDhadTechnicalSkillList);
		System.out.println("inputDELETEDhadTechnicalSkillList : "+inputDELETEDhadTechnicalSkillList);
		
		//【delete or update unnecessary hadTechnicalSkill】
		//collect unnecessary hadTechnicalSkills
		List<HadTechnicalSkill> unnecessaryHadTechnicalSkillList = new ArrayList<>();
		existingHadTechnicalSkillList.stream()
		.filter(existing -> registeredHadTechnicalSkillList.stream()
				.allMatch(registered -> !registered.getTechnicalSkillId().equals(existing.getTechnicalSkillId())))
		.forEach(existing -> unnecessaryHadTechnicalSkillList.add(existing));
		System.out.println("unnecessaryHadTechnicalSkillList : "+unnecessaryHadTechnicalSkillList);
		
		//delete or update unnecessary hadTechnicalSkillList
		if(unnecessaryHadTechnicalSkillList.size() != 0) {
			if(Stage.ACTIVE.getKey().equals(form.getStage())) {
				System.out.println("unnecessaryHadTechnicalSkillList : "+unnecessaryHadTechnicalSkillList);
				//collect hadTechnicalSkillList for delete
				List<HadTechnicalSkill> unnecessaryHadTechnicalSkillListForDelete =
						unnecessaryHadTechnicalSkillList.stream()
						.filter(((Predicate<HadTechnicalSkill>)(ht -> ht.getStage().equals(Stage.ACTIVE.getKey())))
								.or((Predicate<HadTechnicalSkill>)(ht -> ht.getStage().equals(Stage.DELETED.getKey()))))
						.collect(Collectors.toList());
				
				deleteHadTechnicalSkillService.deleteHadTechnicalSkill(unnecessaryHadTechnicalSkillListForDelete);
				System.out.println("case of ACTIVE, delete unnecessaryHadTechnicalSkillListForDelete : "
						+unnecessaryHadTechnicalSkillListForDelete);
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
		//【insert usedTechnicalSkill】
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
		
		//insert usedTechnicalSkill
		if(inputUsedTechnicalSkillList.size() != 0) {
			addUsedTechnicalSkillService.addUsedTechnicalSkillList(inputUsedTechnicalSkillList);
		}
		
		return null;
	}

}
