package com.example.controller.skill;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.HadBasicSkill;
import com.example.domain.HadEngineerSkill;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.form.StatusForm;
import com.example.service.status.AddHadBasicSkillService;
import com.example.service.status.AddHadEngineerSkillService;
import com.example.service.status.AddHadPersonalityService;
import com.example.service.status.AddStatusService;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetBasicSkillListService;
import com.example.service.status.GetEngineerSkillListService;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateHadBasicSkillService;
import com.example.service.status.UpdateHadEngineerSkillService;
import com.example.service.status.UpdateHadPersonalityService;
import com.example.service.status.UpdateStatusService;
import com.example.service.user.GetSpecificUserService;


@Controller
@RequestMapping(value="/skill/status/edit")
public class EditStatusController {
	
	@Autowired
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@Autowired
	private GetStatusService getStatusService;
	
	@Autowired
	private GetBasicSkillListService getBasicSkillService;
	
	@Autowired
	private GetEngineerSkillListService getEngineerSkillService;
	
	@Autowired
	private GetPersonalityService getPersonalityService;
	
	@Autowired
	private AddStatusService addStatusService;
	
	@Autowired
	private AddHadBasicSkillService addHadBasicSkillService;
	
	@Autowired
	private AddHadEngineerSkillService addHadEngineerSkillService;

	@Autowired
	private AddHadPersonalityService addHadPersonalityService;
	
	@Autowired
	private GetSpecificUserService getSpecificUserService;
	
	@Autowired
	private UpdateStatusService updateStatusService;
	
	@Autowired
	private UpdateHadBasicSkillService updateHadBasicSkillService;
	
	@Autowired
	private UpdateHadEngineerSkillService updateHadEngineerSkillService;
	
	@Autowired
	private UpdateHadPersonalityService updateHadPersonalityService;
	
	@Autowired
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@Autowired
	private DeleteStatusService deleteStatusService;
	
	@ModelAttribute
	public StatusForm setUpForm(){
		return new StatusForm();
	}
	
	/**
	 * ??????????????????????????????????????????.
	 * 
	 * @param model ???????????????????????????
	 * @return ?????????????????????
	 */
	@RequestMapping(value="")
	public String showEditStatus(Model model, @AuthenticationPrincipal LoginUser loginUser, StatusForm statusForm, RedirectAttributes redirectAttributes) {
		//???????????????????????????????????????
		Status status = new Status();
		//???????????????
		String role = loginUser.getAuthorities().toString();
		//????????????ID?????????
		Integer userId = statusForm.getUserId();
		User user = new User();
		//???????????????????????????userId??????????????????????????????????????????userId???????????????
		if(!role.equals("[ROLE_ADMIN]")) {
			user = loginUser.getUser();
			userId = user.getUserId();
		}else {
			user = getSpecificUserService.get(userId);
		}
		//?????????????????????????????????????????????????????????????????????(??????????????????????????????????????????)
		if(Objects.nonNull(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey()))) {
			throw new IllegalArgumentException();
		}
		
		//????????????????????????????????????
		Status tempoStatus = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		if(Objects.nonNull(tempoStatus)) {
			//model.addAttribute("tempoStatus", tempoStatus);
			//???????????????????????????
			LocalDateTime oneDaylater = tempoStatus.getUpdatedAt();
			//??????????????????1???????????????
			oneDaylater = oneDaylater.plusDays(1);
			//?????????????????????????????????????????????24??????????????????????????????????????????
			if(oneDaylater.compareTo(LocalDateTime.now()) < 0 ) {
				deleteStatusService.deleteStatusByPrimaryKey(tempoStatus.getStatusId());
				model.addAttribute("alertMessage", "??????????????????????????????1??????????????????????????????????????????");
			}
		}
		
		//????????????????????????????????????????????????????????????????????????
		status = getStatusAndSkillsService.getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		Integer departmentId = user.getDepartmentId();
		model.addAttribute("userData", user);			
		model.addAttribute("basicSkillList", getBasicSkillService.getBasicSkillList(departmentId));
		model.addAttribute("engineerSkillList", getEngineerSkillService.getEngineerSkillList(departmentId));
		model.addAttribute("statusData", status);
		model.addAttribute("personalityList", getPersonalityService.getPersonality());
		return "skill/skill-edit-status";
	}
	
	/**
	 * ?????????????????????,????????????,??????????????????????????????,??????????????????????????????,????????????.
	 * 
	 * @param statusForm ???????????????????????????
	 * @param loginUser ??????????????????
	 * @param typeOfButton (apply=???????????????, temporalSave=?????????????????????)
	 * @return ??????????????????????????????
	 */
	@PostMapping(value="/do")
	public String registerStatus(@Validated StatusForm statusForm, BindingResult result, @AuthenticationPrincipal LoginUser loginUser, Model model,RedirectAttributes redirectAttributes) {	
		Status status = new Status();
		String role = loginUser.getAuthorities().toString();
		Integer statusId = statusForm.getStatusId();
		Integer version = statusForm.getVersion();
		Integer stage = statusForm.getStage();
		Integer userId = statusForm.getUserId();
		String loginName = loginUser.getUser().getName();
		List<HadBasicSkill> hadBasicSkillList = statusForm.getHadBasicSkillList();
		List<HadEngineerSkill> hadEngineerSkillList = statusForm.getHadEngineerSkillList();
		List<Integer> personalityIdList = statusForm.getPersonalityIdList();
		//??????????????????????????????????????????????????????.
		Status statusForDb = new Status();
		BeanUtils.copyProperties(statusForm, statusForDb);
		//???????????????????????????????????????????????????????????????ID???????????????ID?????????????????????
		if(!role.equals("[ROLE_ADMIN]")
				&& !loginUser.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException();
		}
		//stage???3???9????????????????????????
		if(stage == Stage.SENT_BACK.getKey() || stage == Stage.DELETED.getKey()) {
			throw new IllegalArgumentException();
		}
		//?????????????????????
		if(Objects.nonNull(statusId)) {
			status = getStatusService.getStatusByPrimaryKey(statusId);
			//???????????????????????????????????????????????????????????????
			if(status.getVersion() != version) {
				redirectAttributes.addAttribute("userId", userId);
				redirectAttributes.addFlashAttribute("alertMessage", "????????????\nERROR:????????????????????????????????????????????????");
				return "redirect:/skill/status";
			}
		}
		//validation????????????
		result = validationCheck(statusForm, result, role);
		//validation??????????????????????????????????????????
		if(result.hasErrors() && !(stage == Stage.TEMPORARY.getKey())) {
			return showEditStatus(model, loginUser, statusForm, redirectAttributes);
		}
		//??????????????????????????????????????????.
		Status tempoStatus = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);	
		//??????,?????????????????????????????????????????????
		statusForDb.setUpdater(loginName);		
		//????????????ID,?????????, ???????????????????????????.
		if(role.equals("[ROLE_ADMIN]")) {	
			Status activeStatus = getStatusService.getStatusByStages(userId, stage, null);	
			if(Objects.isNull(activeStatus)) {
				statusForDb.setCreator(loginName);
				addStatusService.addStatus(statusForDb, version);				
				//????????????????????????????????????(1??????????????????????????????)???????????????.
				activeStatus = getStatusService.getStatusByStages(userId, stage, null);
				statusId = activeStatus.getStatusId();
				//?????????????????????,????????????????????????,????????????????????????ID???????????????????????????.
				addSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, statusId);
			}else {
				statusForDb = prepareStatusForUpdate(statusForDb, activeStatus);
				updateStatusService.updateStatus(statusForDb);
				statusId = activeStatus.getStatusId();
        		//?????????????????????,????????????????????????,?????????????????????.
				updateSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, statusId);
			}
			sendMailAboutStatusByAdminService.sendMailAboutStatusByAdmin(userId, stage, statusForDb.getAdminComment(), statusId, "??????????????????????????????");
			
		}else {
			//????????????????????????????????????.
			if(Objects.isNull(tempoStatus)) {
				statusForDb.setCreator(loginName);
				addStatusService.addStatus(statusForDb, version);				
				//????????????????????????????????????(1??????????????????????????????)???????????????.
				status = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
				statusId = status.getStatusId();
				//?????????????????????,????????????????????????,????????????????????????ID???????????????????????????.
				addSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, statusId);
			}else {
				tempoStatus = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
				statusForDb = prepareStatusForUpdate(statusForDb, tempoStatus);
				updateStatusService.updateStatus(statusForDb);	
				//?????????????????????,????????????????????????,?????????????????????.
				updateSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, tempoStatus.getStatusId());
			}
		}
		
		//????????????????????????????????????ID?????????.
		redirectAttributes.addAttribute("userId", userId);
		redirectAttributes.addFlashAttribute("editCompleted", "??????????????????" + Stage.of(stage).getMessageForEdit() + "????????????");
		return "redirect:/skill/status";
	}
	
	/**
	 * ????????????,???????????????,?????????????????????.
	 * 
	 * @param hadBasicSkillList ??????????????????????????????
	 * @param hadEngineerSkillList ???????????????????????????????????????
	 * @param personalityIdList ??????ID?????????
	 * @param statusId ???????????????ID
	 */
	public void addSkills(List<HadBasicSkill>hadBasicSkillList, List<HadEngineerSkill>hadEngineerSkillList, List<Integer>personalityIdList, Integer statusId) {
		addHadBasicSkillService.addHadBasicSkill(hadBasicSkillList, statusId);
		addHadEngineerSkillService.addHadEngineerSkill(hadEngineerSkillList, statusId);
		addHadPersonalityService.addHadPersonality(personalityIdList, statusId);		
	}
	
	/**
	 * ????????????,???????????????,?????????????????????.
	 * 
	 * @param hadBasicSkillList ??????????????????????????????
	 * @param hadEngineerSkillList ???????????????????????????????????????
	 * @param personalityIdList ??????ID?????????
	 * @param statusId ???????????????ID
	 */
	public void updateSkills(List<HadBasicSkill>hadBasicSkillList, List<HadEngineerSkill>hadEngineerSkillList, List<Integer>personalityIdList, Integer statusId) {
		updateHadBasicSkillService.updateHadBasicSkill(hadBasicSkillList, statusId);
		updateHadEngineerSkillService.updateHadEngineerSkill(hadEngineerSkillList, statusId);
		updateHadPersonalityService.upsertHadPersonality(personalityIdList, statusId);
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * 
	 * @param statusForm ???????????????????????????
	 * @param result ?????????
	 * @param role ??????
	 * @return result
	 */
	public BindingResult validationCheck(StatusForm statusForm, BindingResult result, String role) {
		//????????????????????????????????????0??????????????????????????????.
		if (CollectionUtils.isEmpty(statusForm.getPersonalityIdList())) {
			result.rejectValue("personalityIdList", null, "1???????????????????????????????????????");
		}
		
		//??????????????????????????????????????????????????????null?????????????????????
		for (HadEngineerSkill hadEngineerSkill : statusForm.getHadEngineerSkillList()) {
			if (Objects.isNull(hadEngineerSkill.getScore()) || hadEngineerSkill.getScore() < 0 || hadEngineerSkill.getScore() > 100) {
				result.rejectValue("hadEngineerSkillList", null, "0??????100????????????????????????????????????");
				break;
			}
		}
		
		//?????????????????????????????????????????????null?????????????????????
		for(HadBasicSkill hadBasicSkill : statusForm.getHadBasicSkillList()) {
			if (Objects.isNull(hadBasicSkill.getScore()) || Integer.parseInt(hadBasicSkill.getScore()) <= 0 || Integer.parseInt(hadBasicSkill.getScore()) > 5) {
				result.rejectValue("hadBasicSkillList", null, "1??????5????????????????????????????????????");
				break;
			}
		}
		
		//????????????????????????????????????
		if(role.equals("[ROLE_ADMIN]")) {
			//???????????????????????????????????????
			statusForm.setAdminComment(StringUtils.trimAllWhitespace(statusForm.getAdminComment()));
			if(statusForm.getAdminComment().isEmpty()) {
				result.rejectValue("adminComment", null, "1?????????200??????????????????????????????");
			}
		}else {
			//??????????????????????????????????????????
			statusForm.setUserComment(StringUtils.trimAllWhitespace(statusForm.getUserComment()));
			if(statusForm.getUserComment().isEmpty()) {
				result.rejectValue("userComment", null, "1?????????200??????????????????????????????");
			}
		}
		return result;
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * 
	 * @param inputStatus ???????????????????????????
	 * @param outputStatus ???????????????????????????
	 * @return ????????????????????????
	 */
	public Status prepareStatusForUpdate(Status inputStatus, Status outputStatus) {
		inputStatus.setStatusId(outputStatus.getStatusId());
		inputStatus.setCreator(outputStatus.getCreator());
		inputStatus.setCreatedAt(outputStatus.getCreatedAt());
		inputStatus.setVersion(outputStatus.getVersion());
		return inputStatus;
	}
}