package com.example.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.controller.skill.ShowSpecsheetController;
import com.example.domain.AppSpecsheet;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.form.EditSpecsheetForm;
import com.example.form.ProcessSpecsheetForm;
import com.example.service.spec.GetSpecsheetByConditionService;

@Controller
@RequestMapping("/request")
public class ShowRequestSpecsheetController {
	
	@Autowired
	private GetSpecsheetByConditionService getSpecsheetByConditionService;
	
	@Autowired
	private ShowSpecsheetController showSpecsheetController;
	
	@ModelAttribute
	public ProcessSpecsheetForm setUpProcessSpecsheetForm() {
		return new ProcessSpecsheetForm();
	}
	
	@ModelAttribute
	public EditSpecsheetForm setUpEditSpecsheetForm() {
		return new EditSpecsheetForm();
	}
	
	/**
	 * スペックシート申請詳細画面へ遷移するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param stage ステージ
	 * @param model リクエストスコープ
	 * @param redirectAttributes フラッシュスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return スペックシート編集画面
	 */
	@RequestMapping("/spec")
	public String showRequestSpecsheet(Integer specsheetId, Integer userId, Integer stage,
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		String role = loginUser.getAuthorities().toString();
		if(!role.equals("[ROLE_ADMIN]")
				&& !loginUser.getUser().getUserId().equals(userId)) {
			System.out.println("不正なリクエストが行われました。");
			throw new IllegalArgumentException();
		}
		if(!stage.equals(Stage.REQUESTING.getKey()) && !stage.equals(Stage.SENT_BACK.getKey())) {
			System.out.println("不正なリクエストが行われました。");
			throw new IllegalArgumentException();
		}
		
		//スペックシートIDからデータ取得、versionが変わっていてもステージ一致でOK
		List<Integer> htStageList = new ArrayList<>();
		htStageList.add(Stage.ACTIVE.getKey());
		htStageList.add(Stage.REQUESTING.getKey());
		htStageList.add(Stage.SENT_BACK.getKey());
		List<AppSpecsheet> specsheetList = 
				getSpecsheetByConditionService.getSpecsheetByCondition(specsheetId, userId, new ArrayList<Integer>(), htStageList);
		AppSpecsheet specsheet = null;
		if(specsheetList.size() != 0) {
			specsheet = specsheetList.get(0);
			if(!stage.equals(specsheet.getStage())) {
				String message = "表示失敗\nERROR: この申請は既に" 
						+ Stage.of(specsheet.getStage()).getMessageForRequest() + "されました";
				redirectAttributes.addFlashAttribute("message", message);
				return "redirect:/request";
			}
			if(!role.equals("[ROLE_ADMIN]") && !loginUser.getUser().getUserId().equals(specsheet.getUserId())) {
				System.out.println("不正なリクエストが行われました。");
				throw new IllegalArgumentException();
			}
		}
		if(specsheet == null) {
			System.out.println("不正なリクエストが行われました。");
			throw new IllegalArgumentException();
		}
		
		if(Stage.REQUESTING.getKey().equals(specsheet.getStage())) {
			specsheet.setHadTechnicalSkillList(
					specsheet.getHadTechnicalSkillList().stream()
					.filter(((Predicate<HadTechnicalSkill>)(ht -> Stage.ACTIVE.getKey().equals(ht.getStage())))
							.or((Predicate<HadTechnicalSkill>)(ht -> Stage.REQUESTING.getKey().equals(ht.getStage()))))
					.collect(Collectors.toList()));
		}
		if(Stage.SENT_BACK.getKey().equals(specsheet.getStage())) {
			specsheet.setHadTechnicalSkillList(
					specsheet.getHadTechnicalSkillList().stream()
					.filter(((Predicate<HadTechnicalSkill>)(ht -> Stage.ACTIVE.getKey().equals(ht.getStage())))
							.or((Predicate<HadTechnicalSkill>)(ht -> Stage.SENT_BACK.getKey().equals(ht.getStage()))))
					.collect(Collectors.toList()));
		}
		
		showSpecsheetController.devideTechnicalSkillsForSkillSummary(specsheet, model);
		model.addAttribute("specsheet", specsheet);
		
		return "request/request-spec";
	}

}
