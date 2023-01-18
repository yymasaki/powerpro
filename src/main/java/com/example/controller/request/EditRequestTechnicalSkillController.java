package com.example.controller.request;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Category;
import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.form.EditRequestTechnicalSkillForm;
import com.example.service.technique.GetRequestTechnicalSkillService;
import com.example.service.technique.GetTechnicalSkillByNameAndCategoryService;
import com.example.service.technique.GetTechnicalSkillService;
import com.example.service.technique.UpdateRequestTechnicalSkillService;

@Controller
@RequestMapping("/request/item/edit")
public class EditRequestTechnicalSkillController {

	@Autowired
	private GetRequestTechnicalSkillService getRequestTechnicalSkillService;

	@Autowired
	private GetTechnicalSkillService getTechnicalSkillService;

	@Autowired
	private UpdateRequestTechnicalSkillService updateService;

	@Autowired
	private GetTechnicalSkillByNameAndCategoryService getTechnicalSkillByNameAndCategoryService;

	@ModelAttribute
	public EditRequestTechnicalSkillForm setUpEditRequestItemForm() {
		return new EditRequestTechnicalSkillForm();
	}
	
	/**
	 * スキル申請内容修正画面を表示する.
	 * 
	 * @param form
	 * @param model
	 * @return スキル申請詳細画面を表示する
	 */
	@RequestMapping("")
	public String showRequestTechnicalSkillEdit(Integer technicalSkillId, Integer version, Model model
			,RedirectAttributes redirectAttributes) {

		TechnicalSkill technicalSkill = getRequestTechnicalSkillService.getRequestTechnicalSkill(technicalSkillId);
		// DBの最新情報のバージョン番号をチェック,管理者同士の競合を想定
		if (version != technicalSkill.getVersion()) {
			if (technicalSkill.getStage() == Stage.ACTIVE.getKey()) {
				redirectAttributes.addFlashAttribute("failMessage", "承認失敗\nERROR:この申請内容は既に承認されました");
			}
			return "redirect:/request";
		}

		model.addAttribute("technicalSkill", technicalSkill);

		// セレクトボックスの表示内容
		Map<Integer, String> skillMap = new LinkedHashMap<>();
		skillMap.put(Category.OS.getKey(), Category.OS.getName());
		skillMap.put(Category.LANGUAGE.getKey(), Category.LANGUAGE.getName());
		skillMap.put(Category.FRAMEWORK.getKey(), Category.FRAMEWORK.getName());
		skillMap.put(Category.LIBRARY.getKey(), Category.LIBRARY.getName());
		skillMap.put(Category.MIDDLEWARE.getKey(), Category.MIDDLEWARE.getName());
		skillMap.put(Category.TOOL.getKey(), Category.TOOL.getName());
		skillMap.put(Category.PROCESS.getKey(), Category.PROCESS.getName());
		model.addAttribute("skillMap", skillMap);

		return "request/request-edit-item";
	}

	/**
	 * 修正した内容を承認処理する.
	 * 
	 * @param form
	 * @param loginUser
	 * @return 申請トップ画面
	 */
	@PostMapping("/do")
	public String editRequestTechnicalSkill(@Validated EditRequestTechnicalSkillForm form, BindingResult result,
		@AuthenticationPrincipal LoginUser loginUser, Model model,RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return showRequestTechnicalSkillEdit(form.getTechnicalSkillId(),form.getVersion(),model,redirectAttributes);
		}
		
		form.setName(StringUtils.trimAllWhitespace(form.getName()));
		boolean approval=false;
		boolean request = false;
		String failMessage="";
				
		TechnicalSkill checkTechnicalSkill=getTechnicalSkillByNameAndCategoryService
				.getTechinicalSkillByNameAndCategory(form.getName(), form.getCategory());
		// DB上に同一カテゴリ・同一名称のテクニカルスキル情報があるかチェック(大文字、小文字区別する)
		if (Objects.nonNull(checkTechnicalSkill)
				&&form.getName().equals(checkTechnicalSkill.getName())
				&&checkTechnicalSkill.getStage()==0) {
				approval=true;
				failMessage="承認失敗\nERROR:この内容は既に登録されています";
		}else if(Objects.nonNull(checkTechnicalSkill)
				&&form.getName().equals(checkTechnicalSkill.getName())
				&&checkTechnicalSkill.getStage()==2)  {
				request=true;
				failMessage="承認失敗\nERROR:この内容は申請中の内容と重複しています";
		}
		
		//登録済、もしくは申請中の時エラーメッセージ
		if(approval||request) {
		redirectAttributes.addFlashAttribute("editRequestTechnicalSkillForm",form);
		redirectAttributes.addFlashAttribute("failMessage",failMessage);
		return "redirect:/request";
		}

		// DBの最新情報のバージョン番号をチェック,管理者同士の競合を想定
		TechnicalSkill technicalSkill = getTechnicalSkillService.getTechnicalSkill(form.getTechnicalSkillId());
		if (form.getVersion() != technicalSkill.getVersion()) {
			if (technicalSkill.getStage().equals(Stage.ACTIVE.getKey())) {
				redirectAttributes.addFlashAttribute("failMessage", "承認失敗\nERROR:この申請内容は既に承認されました");
			}
			redirectAttributes.addFlashAttribute("editRequestTechnicalSkillForm",form);
			return "redirect:/request";
		}

		String updater = loginUser.getUser().getName();
		technicalSkill = setTechnicalSkill(form, Stage.ACTIVE.getKey(), technicalSkill, updater);
		updateService.updateRequestTechnicalSkill(technicalSkill);
		
		//テストで値を検証するためパラメータをフラッシュスコープに格納する.
		redirectAttributes.addFlashAttribute("editRequestTechnicalSkillForm",form);
		redirectAttributes.addFlashAttribute("message", "テクニカルスキル申請を"+Stage.ACTIVE.getMessageForRequest()+"しました");
		return "redirect:/request";

	}

	/**
	 * テクニカルスキルに更新情報をセットする.
	 * 
	 * @param form
	 * @return テクニカルスキル
	 */
	public TechnicalSkill setTechnicalSkill(EditRequestTechnicalSkillForm form, Integer stage,
			TechnicalSkill technicalSkill, String updater) {
		technicalSkill.setCategory(form.getCategory());
		technicalSkill.setName(form.getName());
		technicalSkill.setStage(stage);
		technicalSkill.setUpdater(updater);
		LocalDateTime localDateTime = LocalDateTime.now();
		technicalSkill.setUpdatedAt(localDateTime);
		technicalSkill.setVersion(technicalSkill.getVersion() + 1);

		return technicalSkill;
	}

	/**
	 * スキル名が重複した場合メッセージを格納する.
	 * 
	 * @param form
	 * @return チェックメッセージ
	 */
	@ResponseBody
	@RequestMapping(value = "/check-item-edit", method = RequestMethod.GET)
	public Map<String, String> checkTechnicalSkill(EditRequestTechnicalSkillForm form) {

		Map<String, String> map = new HashMap<>();
		String checkMessage = null;

		form.setName(StringUtils.trimAllWhitespace(form.getName()));
		TechnicalSkill checkTechnicalSkill = getTechnicalSkillByNameAndCategoryService
				.getTechinicalSkillByNameAndCategory(form.getName(), form.getCategory());		
		// DB上に同一カテゴリ・同一名称のテクニカルスキル情報があるかチェック(大文字、小文字区別する)
		if (Objects.nonNull(checkTechnicalSkill)
				&&form.getName().equals(checkTechnicalSkill.getName())
				&&checkTechnicalSkill.getStage()==0) {
			checkMessage = "このスキル名は既に登録されています";
		} else if(Objects.nonNull(checkTechnicalSkill)
				&&form.getName().equals(checkTechnicalSkill.getName())
				&&checkTechnicalSkill.getStage()==2)  {
			checkMessage = "申請中のスキル名と重複しています";
		}else {
			checkMessage = "";
		}
		map.put("checkMessage", checkMessage);
		return map;
	}

}
