package com.example.controller.skill;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.Category;
import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.form.DownloadSpecsheetForm;
import com.example.form.ProcessSpecsheetForm;
import com.example.service.spec.GetSpecsheetByConditionService;
import com.example.service.user.GetSpecificUserService;

@Controller
@RequestMapping("/skill")
public class ShowSpecsheetController {
	
	@Autowired
	private GetSpecsheetByConditionService getSpecsheetByConditionService;

	@Autowired
	private GetSpecificUserService getSpecificUserService;
	
	@ModelAttribute
	public ProcessSpecsheetForm setUpProcessSpecsheetForm() {
		return new ProcessSpecsheetForm();
	}

	@ModelAttribute
	public DownloadSpecsheetForm setUpDownloadSpecsheetForm() {
		return new DownloadSpecsheetForm();
	}
	
	/**
	 * スペックシート画面に遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param model リクエストスコープ
	 * @return スペックシート画面
	 */
	@RequestMapping("/spec")
	public String showSpecsheet(Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		String role = loginUser.getAuthorities().toString();
		if(!role.equals("[ROLE_SALES]") && !role.equals("[ROLE_ADMIN]")) {
			userId = loginUser.getUser().getUserId();
		}
		
		List<Integer> specStageList = new ArrayList<>();
		specStageList.add(Stage.ACTIVE.getKey());
		specStageList.add(Stage.REQUESTING.getKey());
		specStageList.add(Stage.SENT_BACK.getKey());
		List<Integer> htStageList = new ArrayList<>();
		htStageList.add(Stage.ACTIVE.getKey());
		htStageList.add(Stage.DELETED.getKey());
		List<AppSpecsheet> specsheetList = 
				getSpecsheetByConditionService.getSpecsheetByCondition(0, userId, specStageList, htStageList);
		specsheetList.forEach(System.out::println);
		
		AppSpecsheet specsheet = null;
		AppSpecsheet requestOrSentBackSpec = null;
		List<AppSpecsheet> specsheetActiveList = new ArrayList<>();
		List<AppSpecsheet> specsheetRequestList = new ArrayList<>();
		specsheetList.stream()
		.filter(spec -> Stage.ACTIVE.getKey().equals(spec.getStage()))
		.sorted(Comparator.comparing(AppSpecsheet::getUpdatedAt).reversed())
		.forEach(spec -> specsheetActiveList.add(spec));
		if(specsheetActiveList.size() != 0) {
			specsheet = specsheetActiveList.get(0);
		}
		specsheetList.stream()
		.filter(((Predicate<AppSpecsheet>)(spec -> Stage.REQUESTING.getKey().equals(spec.getStage())))
				.or((Predicate<AppSpecsheet>)(spec -> Stage.SENT_BACK.getKey().equals(spec.getStage()))))
		.forEach(spec -> specsheetRequestList.add(spec));
		if(specsheetRequestList.size() != 0) {
			requestOrSentBackSpec = specsheetRequestList.get(0);
			model.addAttribute("requestOrSentBackSpec", requestOrSentBackSpec);
		}
		
		if(Objects.nonNull(specsheet)) {
			Long time1 = System.nanoTime();
			devideTechnicalSkillsForSkillSummary(specsheet, model);
			System.out.println("devidedByForEach : "+(System.nanoTime()-time1)/1000+"マイクロ秒");
		} else {
			specsheet = new AppSpecsheet();
			specsheet.setUserId(userId);
			specsheet.setUser(getSpecificUserService.get(userId));
			if(Objects.isNull(specsheet.getUser())) {
				System.out.println("存在しないユーザーが検索されました");
				throw new IllegalArgumentException();
			}
			if(specsheet.getUser().getDepartmentId().compareTo(3) > 0) {
				System.out.println("エンジニアではないユーザーが検索されました");
				throw new IllegalArgumentException();
			}
			specsheet.setDevExperienceList(new ArrayList<DevExperience>());
			model.addAttribute("noRecord", "noRecord");
		}
		model.addAttribute("specsheet", specsheet);
		
		return "skill/skill-spec";
	}
	
	/**
	 * スキル要約表示のためにテクニカルスキルをカテゴリーごとに分別するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @param model リクエストスコープ
	 */
	public void devideTechnicalSkillsForSkillSummary(AppSpecsheet specsheet, Model model) {
		List<HadTechnicalSkill> OSList = new ArrayList<>();
		List<HadTechnicalSkill> languageList = new ArrayList<>();
		List<HadTechnicalSkill> frameworkList = new ArrayList<>();
		List<HadTechnicalSkill> libraryList = new ArrayList<>();
		List<HadTechnicalSkill> middlewareList = new ArrayList<>();
		List<HadTechnicalSkill> toolList = new ArrayList<>();
		List<HadTechnicalSkill> processList = new ArrayList<>();
		specsheet.getHadTechnicalSkillList().forEach(System.out::println);
		specsheet.getHadTechnicalSkillList().forEach(ht -> {
			if(ht.getTechnicalSkill().getCategory().equals(Category.OS.getKey())) {
				OSList.add(ht);
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.LANGUAGE.getKey())) {
				languageList.add(ht);
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.FRAMEWORK.getKey())) {
				frameworkList.add(ht);
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.LIBRARY.getKey())) {
				libraryList.add(ht);
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.MIDDLEWARE.getKey())) {
				middlewareList.add(ht);
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.TOOL.getKey())) {
				toolList.add(ht);
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.PROCESS.getKey())) {
				processList.add(ht);
			}
		});
		model.addAttribute("OSList", OSList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("frameworkList", frameworkList);
		System.out.println(frameworkList);
		model.addAttribute("libraryList", libraryList);
		model.addAttribute("middlewareList", middlewareList);
		model.addAttribute("toolList", toolList);
		model.addAttribute("processList", processList);
	}

}
