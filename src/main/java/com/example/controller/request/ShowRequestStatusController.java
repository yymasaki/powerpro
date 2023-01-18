package com.example.controller.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Status;
import com.example.form.StatusForm;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;

@Controller
@RequestMapping(value="/request")
public class ShowRequestStatusController {
	
	@Autowired
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@Autowired
	private GetPersonalityService getPersonalityService;

	@ModelAttribute
	public StatusForm setUpForm() {
		return new StatusForm();
	}
	
	/**
	 * ステータス申請画面を表示させる.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログイン情報
	 * @param userId ユーザーID
	 * @return ステータス申請画面の表示
	 */
	@RequestMapping(value="/status")
	public String showRequestStatus(Model model,StatusForm statusForm, BindingResult result, @AuthenticationPrincipal LoginUser loginUser, Integer statusId) {
		Status status = getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId);
		Integer userId = status.getUserId();
		String role = loginUser.getAuthorities().toString();
		
		if(!(role.equals("[ROLE_ADMIN]"))
				&& !(loginUser.getUser().getUserId().equals(userId))) {
			throw new IllegalArgumentException();
		}
		
		model.addAttribute("statusData", status);		
		List<Personality> personalityList = getPersonalityService.getPersonality();
		model.addAttribute("personalityList", personalityList);
		model.addAttribute("currentStage", status.getStageString());
		return "request/request-status";
	}
}
