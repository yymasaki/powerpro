package com.example.controller.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.user.GetSpecificUserService;

@Controller
@RequestMapping(value="/skill")
public class ShowStatusController {
	
	@Autowired
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@Autowired
	private GetPersonalityService getPersonalityService;
	
	@Autowired
	private GetSpecificUserService getSpecificUserService;
	
	@Autowired
	private GetStatusService getStatusService;
	
	/**
	 * ステータス詳細画面に遷移する.
	 * 
	 * @param model リクエストスコープ
	 * @return ステータス詳細画面
	 */
	@RequestMapping(value="/status")
	public String showSkillStatusDetail(Model model, @AuthenticationPrincipal LoginUser loginUser, Integer userId) {
		//表示するステータスを格納する.
		Status status = new Status();
		//申請中,否認されたデータを格納する.
		Status statusStage2Or3 = new Status();
		//権限の取得
		String role = loginUser.getAuthorities().toString();
		//ユーザー権限が管理者,営業以外ならuserIdをLoginUserから持ってくる
		if(!role.equals("[ROLE_ADMIN]") && !role.equals("[ROLE_SALES]")) {
			userId = loginUser.getUser().getUserId();		
			model.addAttribute("userData", loginUser.getUser());
		}else {
			//ユーザーIDからユーザーを取得する
			User user = getSpecificUserService.get(userId);			
			model.addAttribute("userData", user);
		}
		//表示するステータスを取得する
		status = getStatusAndSkillsService.getStatusAndSkillsByStages(userId, Stage.ACTIVE.getKey(), null);
		//申請中,否認されたステータスを取得する
		statusStage2Or3 = getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey());
		model.addAttribute("status", status);
		model.addAttribute("statusStage2Or3", statusStage2Or3);
		model.addAttribute("personalityList", getPersonalityService.getPersonality());
		return "skill/skill-status";
	}
}
