package com.example.controller.skill;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Category;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.form.EditHadTechnicalSkillForm;
import com.example.service.technique.GetHadTechnicalSkillListService;
import com.example.service.technique.UpdateHadTechnicalSkillService;

@Controller
@RequestMapping("/skill")
public class EditHadTechnicalSkillListController {

	@Autowired
	private GetHadTechnicalSkillListService getHadTechnicalSkillListService;

	@Autowired
	private UpdateHadTechnicalSkillService updateHadTechnicalSkillService;

	@ModelAttribute
	public EditHadTechnicalSkillForm setUpForm() {
		return new EditHadTechnicalSkillForm();
	}

	/**
	 * 
	 * 所有テクニカルスキル申請編集画面に遷移する。
	 * 
	 * @param loginUser ログインユーザー
	 * @param model     モデルスコープ
	 * @return 所有テクニカルスキル編集画面
	 */
	@RequestMapping("/technique/edit")
	public String toEditHadTechnicalSkillList(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		List<HadTechnicalSkill> hadTechnicalSkillList = getHadTechnicalSkillListService
				.getHadTechnicalSkillListOfStage0or1or2(loginUser.getUser().getUserId());
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

		if (hadTechnicalSkillList.size() == 0) {
			model.addAttribute("totalSize", 0);
			return "skill/skill-edit-technique";
		}

		model.addAttribute("osList", osList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("frameworkList", frameworkList);
		model.addAttribute("libraryList", libraryList);
		model.addAttribute("middlewareList", middlewareList);
		model.addAttribute("toolList", toolList);
		model.addAttribute("devProcessList", devProcessList);
		model.addAttribute("osListSize", osList.size());
		model.addAttribute("languageListSize", languageList.size());
		model.addAttribute("frameworkListSize", frameworkList.size());
		model.addAttribute("libraryListSize", libraryList.size());
		model.addAttribute("middlewareListSize", middlewareList.size());
		model.addAttribute("toolListSize", toolList.size());
		model.addAttribute("devProcessListSize", devProcessList.size());
		return "skill/skill-edit-technique";
	}

	/**
	 * 
	 * 利用期間を更新する.
	 * 
	 * @param form 入力情報
	 * @param      loginUser ログインユーザー
	 * @param      model モデルスコープ
	 * @return 所有テクニカルスキル詳細画面
	 */
	@PostMapping("/technique/edit/do")
	public String editHadTechnicalSkillList(EditHadTechnicalSkillForm form,
			@AuthenticationPrincipal LoginUser loginUser, Model model, RedirectAttributes redirectAttributes) {

		LocalDateTime currentDateTime = LocalDateTime.now();
		// formの内容をhadTechnicalSkillListに移す
		List<HadTechnicalSkill> hadTechnicalSkillList = receiveHadTechnicalSkillListFromForm(form, loginUser,
				currentDateTime);
		updateHadTechnicalSkillService.updateUsingPeriodByHadTechnicalSkillList(hadTechnicalSkillList,loginUser.getUser().getUserId());
		redirectAttributes.addFlashAttribute("message", "テクニカルスキル利用歴を更新しました");
		//テスト用
		redirectAttributes.addFlashAttribute("EditHadTechnicalSkillForm",form);
		return "redirect:/skill/technique";
	}

	/**
	 * 
	 * formで受け取った情報及び、ユーザーid、作成時刻、stage等をlistに格納する
	 * 
	 * @param form            所有テクニカルスキル編集画面での入力情報
	 * 
	 * @param loginUser       ログインユーザー
	 * @param currentDateTime 現在時刻
	 * @return HadTechnicalSkillList
	 */
	public List<HadTechnicalSkill> receiveHadTechnicalSkillListFromForm(EditHadTechnicalSkillForm form,
			LoginUser loginUser, LocalDateTime currentDateTime) {
		List<HadTechnicalSkill> hadTechnicalSkillList = new ArrayList<>();
		form.getHadTechnicalSkillList().forEach(f -> {
			HadTechnicalSkill ht = new HadTechnicalSkill();
			ht.setHadTechnicalSkillId(f.getHadTechnicalSkillId());
			ht.setUsingPeriod(f.getUsingPeriod());
			ht.setUpdater(loginUser.getUser().getName());
			ht.setUpdatedAt(currentDateTime);
			ht.setTechnicalSkill(f.getTechnicalSkill());
			hadTechnicalSkillList.add(ht);
		});
		return hadTechnicalSkillList;

	}

}
