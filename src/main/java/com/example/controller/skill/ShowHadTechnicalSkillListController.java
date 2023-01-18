package com.example.controller.skill;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.Category;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.User;
import com.example.service.status.GetStatusService;
import com.example.service.technique.GetHadTechnicalSkillListService;
import com.example.service.user.GetSpecificUserService;

@Controller
@RequestMapping("/skill")
public class ShowHadTechnicalSkillListController {

	@Autowired
	private GetHadTechnicalSkillListService getHadTechnicalSkillListService;

	@Autowired
	private GetStatusService getStatusService;

	@Autowired
	private GetSpecificUserService getSpecificUserService;

	/**
	 * 
	 * 所有テクニカルスキル詳細画面を表示する.
	 * 
	 * @param loginUser ログインユーザー
	 * @param model     モデルスコープ
	 * @param userId    ユーザーid(管理者もしくは営業がログイン者の場合、getで受け取る)
	 * @return 所有テクニカルスキル詳細画面
	 */
	@RequestMapping("/technique")
	public String showHadTechnicalSkillListController(@AuthenticationPrincipal LoginUser loginUser, Model model,
			Integer userId) {

		Integer engineerId = null;

		// 管理者ログインの場合
		if (loginUser.getAuthorities().toString().equals("[ROLE_ADMIN]")
				|| loginUser.getAuthorities().toString().equals("[ROLE_SALES]")) {
			engineerId = userId;
			// エンジニアログインの場合
		} else {
			engineerId = loginUser.getUser().getUserId();
		}

		Status status = getStatusService.getStatusByStages(engineerId, 0, null);
		model.addAttribute("status", status);
		User user = getSpecificUserService.get(engineerId);
		model.addAttribute("user", user);
		System.out.println(user);

		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		hadTechnicalSkillList = getHadTechnicalSkillListService.getHadTechnicalSkillListOfStage0or1or2(engineerId);

		if (hadTechnicalSkillList.size() == 0) {
			model.addAttribute("noHadTechnicalSkill", "登録されたテクニカルスキルはありません");
			return "skill/skill-technique";
		}

		List<HadTechnicalSkill> osList = new ArrayList<>();
		List<HadTechnicalSkill> languageList = new ArrayList<>();
		List<HadTechnicalSkill> frameworkList = new ArrayList<>();
		List<HadTechnicalSkill> libraryList = new ArrayList<>();
		List<HadTechnicalSkill> middlewareList = new ArrayList<>();
		List<HadTechnicalSkill> toolList = new ArrayList<>();
		List<HadTechnicalSkill> devProcessList = new ArrayList<>();

		hadTechnicalSkillList.forEach(ht -> {
			if (ht.getTechnicalSkill().getCategory() == Category.OS.getKey()) {
				osList.add(ht);
			} else if (ht.getTechnicalSkill().getCategory() == Category.LANGUAGE.getKey()) {
				languageList.add(ht);
			} else if (ht.getTechnicalSkill().getCategory() == Category.FRAMEWORK.getKey()) {
				frameworkList.add(ht);
			} else if (ht.getTechnicalSkill().getCategory() == Category.LIBRARY.getKey()) {
				libraryList.add(ht);
			} else if (ht.getTechnicalSkill().getCategory() == Category.MIDDLEWARE.getKey()) {
				middlewareList.add(ht);
			} else if (ht.getTechnicalSkill().getCategory() == Category.TOOL.getKey()) {
				toolList.add(ht);
			} else {
				devProcessList.add(ht);
			}
		});
		model.addAttribute("latestUpdatedAt", hadTechnicalSkillList.get(0).getUpdatedAt());
		model.addAttribute("osList", osList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("frameworkList", frameworkList);
		model.addAttribute("libraryList", libraryList);
		model.addAttribute("middlewareList", middlewareList);
		model.addAttribute("toolList", toolList);
		model.addAttribute("devProcessList", devProcessList);
		return "skill/skill-technique";
	}
}
