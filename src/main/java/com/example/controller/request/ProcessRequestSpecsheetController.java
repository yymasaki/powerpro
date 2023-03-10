package com.example.controller.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.example.HadTechnicalSkillExample;
import com.example.form.ProcessSpecsheetForm;
import com.example.service.spec.DeleteSpecsheetService;
import com.example.service.spec.GetNewestSpecsheetService;
import com.example.service.spec.SendSpecMailService;
import com.example.service.spec.UpdateSpecsheetService;
import com.example.service.technique.DeleteHadTechnicalSkillService;
import com.example.service.technique.GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageService;
import com.example.service.technique.UpdateHadTechnicalSkillStageService;
import com.example.service.technique.UpdateTechnicalSkillService;

@Controller
@RequestMapping("/request/spec")
public class ProcessRequestSpecsheetController {
	
	@Autowired
	private UpdateSpecsheetService updateSpecsheetService;
	
	@Autowired
	private DeleteSpecsheetService deleteSpecsheetService;
	
	@Autowired
	private GetNewestSpecsheetService getNewestSpecsheetService;

	@Autowired
	private ShowRequestSpecsheetController showRequestSpecsheetController;
	
	@Autowired
	private GetTechnicalSkillListBySpecsheetIdAndUserIdAndStageService getTechnicalSkillListBySpecsheetIdAndStageService;
	
	@Autowired
	private UpdateTechnicalSkillService updateTechnicalSkillService;
	
	@Autowired
	private UpdateHadTechnicalSkillStageService updateHadTechnicalSkillStageService;
	
	@Autowired
	private DeleteHadTechnicalSkillService deleteHadTechnicalSkillService;
	
	@Autowired
	private SendSpecMailService sendSpecMailService;

	@ModelAttribute
	public ProcessSpecsheetForm setUpProcessSpecsheetForm() {
		return new ProcessSpecsheetForm();
	}
	
	/**
	 * ??????????????????????????????????????????????????????????????????.
	 * 
	 * @param form ????????????????????????????????????????????????
	 * @param result ?????????????????????????????????
	 * @param model ???????????????????????????
	 * @param redirectAttributes ???????????????????????????
	 * @param loginUser ??????????????????????????????
	 * @return ?????????????????????
	 */
	@PostMapping(value = "/process")
	public String processRequestSpecsheet(@Validated ProcessSpecsheetForm form, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		String role = loginUser.getAuthorities().toString();
		if(!role.equals("[ROLE_ADMIN]")) {
			System.out.println("???????????????????????????????????????");
			throw new IllegalArgumentException();
		}
		if(!form.getStage().equals(Stage.ACTIVE.getKey()) && !form.getStage().equals(Stage.SENT_BACK.getKey())) {
			System.out.println("????????????????????????????????????????????????");
			throw new IllegalArgumentException();
		}
		
		Integer stage = form.getStage();
		String adminComment = null;
		if (StringUtils.hasText(form.getAdminComment())) {
			adminComment = StringUtils.trimWhitespace(form.getAdminComment());
		}
		if (form.getStage().equals(Stage.SENT_BACK.getKey()) && !StringUtils.hasText(adminComment)) {
			result.rejectValue("adminComment", null, "?????????????????????");
		}
		if(result.hasErrors()) {
			model.addAttribute("message", 
					Stage.of(stage).getMessageForRequest() + "??????\nERROR: ????????????????????????");
			return showRequestSpecsheetController.showRequestSpecsheet(
					form.getSpecsheetId(), form.getUserId(), Stage.REQUESTING.getKey(), model, redirectAttributes, loginUser);
		}
		
		AppSpecsheet specsheet = new AppSpecsheet();
		BeanUtils.copyProperties(form, specsheet);
		specsheet.setUpdater(loginUser.getUser().getName());
		specsheet.setUpdatedAt(LocalDateTime.now());
		int updateCount = updateSpecsheetService.updateSpecsheetStage(specsheet);
		if(updateCount == 0) {
			AppSpecsheet newestSpecsheet = 
					getNewestSpecsheetService.getNewestSpecsheet(form.getUserId());
			String message = Stage.of(stage).getMessageForRequest() + "??????\nERROR: ?????????????????????" 
					+ Stage.of(newestSpecsheet.getStage()).getMessageForRequest() + "???????????????";
			redirectAttributes.addFlashAttribute("message", message);
			switch (Stage.of(newestSpecsheet.getStage())) {
			case ACTIVE:
			case DELETED:
			default:
				return "redirect:/request";
			case REQUESTING:
			case SENT_BACK:
				return "redirect:/request/spec?specsheetId="
					+ newestSpecsheet.getSpecsheetId() + "&userId=" + form.getUserId()
					+ "&stage=" + newestSpecsheet.getStage();
			}
		}
		
		//??????????????????????????????
		if(Stage.ACTIVE.getKey().equals(stage)) {
			List<Integer> specStageList = new ArrayList<>();
			specStageList.add(Stage.TEMPORARY.getKey());
			specStageList.add(Stage.REQUESTING.getKey());
			specStageList.add(Stage.SENT_BACK.getKey());
			List<TechnicalSkill> technicalSkillList = 
					getTechnicalSkillListBySpecsheetIdAndStageService
					.getBySpecsheetIdAndUserIdAndStage(specsheet.getSpecsheetId(), specsheet.getUserId(), specStageList);
			if(technicalSkillList.size() != 0) {
				technicalSkillList.get(0).setStage(stage);
				technicalSkillList.get(0).setUpdatedAt(LocalDateTime.now());
				technicalSkillList.get(0).setUpdater(loginUser.getUser().getName());
				updateTechnicalSkillService.updateRequestUserIdAndStageByPrimaryKeyAsList(technicalSkillList);
			}
		}
		
		//REQUESTING or SENT_BACK ???????????????????????????????????????
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkill.setStage(stage);
		hadTechnicalSkill.setUpdater(loginUser.getUser().getName());
		hadTechnicalSkill.setUpdatedAt(LocalDateTime.now());
		HadTechnicalSkillExample example = new HadTechnicalSkillExample();
		example.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.REQUESTING.getKey().toString());
		example.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.SENT_BACK.getKey().toString());
		updateHadTechnicalSkillStageService.updateStage(hadTechnicalSkill, example);
		
		//???????????????DELETED??????????????????????????????????????????
		if(Stage.ACTIVE.getKey().equals(stage)) {
			HadTechnicalSkillExample example2 = new HadTechnicalSkillExample();
			example2.createCriteria()
			.andUserIdEqualTo(form.getUserId())
			.andStageEqualTo(Stage.DELETED.getKey().toString());
			deleteHadTechnicalSkillService.deleteByExample(example2);
		}
		
		sendSpecMailService.sendSpecMail(
				"??????"+Stage.of(stage).getMessageForRequest(), adminComment, form.getSpecsheetId(), form.getUserId(), stage);
		
		redirectAttributes.addFlashAttribute("message", 
				"??????????????????????????????"+Stage.of(stage).getMessageForRequest()+"????????????");
		return "redirect:/request";
	}
	
	/**
	 * ?????????????????????????????????.
	 * 
	 * @param form ????????????????????????????????????????????????
	 * @param redirectAttributes ???????????????????????????
	 * @param loginUser ??????????????????????????????
	 * @return ?????????????????????
	 */
	@PostMapping(value = "/cancel")
	public String cancelRequestSpecsheet(ProcessSpecsheetForm form, 
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		String role = loginUser.getAuthorities().toString();
		System.out.println(form);
		if(role.equals("[ROLE_ADMIN]") 
				|| !form.getUserId().equals(loginUser.getUser().getUserId())) {
			System.out.println("???????????????????????????????????????");
			throw new IllegalArgumentException();
		}
		
		//version??????
		AppSpecsheet newestSpecsheet = 
				getNewestSpecsheetService.getNewestSpecsheet(form.getUserId());
		if(!form.getVersion().equals(newestSpecsheet.getVersion())) {
			String message = Stage.of(form.getStage()).getMessageForRequest() + "??????\nERROR: ?????????????????????" 
					+ Stage.of(newestSpecsheet.getStage()).getMessageForRequest() + "???????????????";
			redirectAttributes.addFlashAttribute("message", message);
			switch (Stage.of(newestSpecsheet.getStage())) {
			case ACTIVE:
				return "redirect:/request";
			case SENT_BACK:
				return "redirect:/request/spec?specsheetId="
					+ newestSpecsheet.getSpecsheetId() + "&userId=" + form.getUserId()
					+ "&stage=" + newestSpecsheet.getStage();
			default:
				throw new IllegalArgumentException();
			}
		}
		
		deleteSpecsheetService.deleteSpecsheet(form.getSpecsheetId());
		
		//????????????????????????????????????????????????
		HadTechnicalSkillExample example = new HadTechnicalSkillExample();
		example.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.REQUESTING.getKey().toString());
		example.or().andUserIdEqualTo(form.getUserId()).andStageEqualTo(Stage.SENT_BACK.getKey().toString());
		deleteHadTechnicalSkillService.deleteByExample(example);
		
		//DELETED????????????????????????????????????ACTIVE?????????
		HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
		hadTechnicalSkill.setStage(Stage.ACTIVE.getKey());
		hadTechnicalSkill.setUpdater(loginUser.getUser().getName());
		hadTechnicalSkill.setUpdatedAt(LocalDateTime.now());
		HadTechnicalSkillExample htExample = new HadTechnicalSkillExample();
		htExample.createCriteria().andStageEqualTo(Stage.DELETED.getKey().toString());
		updateHadTechnicalSkillStageService.updateStage(hadTechnicalSkill, htExample);
		
		redirectAttributes.addFlashAttribute("message", "???????????????????????????????????????????????????");
		return "redirect:/request";
	}

}
