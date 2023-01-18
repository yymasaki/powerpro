package com.example.controller.skill;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.example.domain.User;
import com.example.form.RegisterTechnicalSkillListForm;
import com.example.form.TechnicalSkillForm;
import com.example.service.technique.AddTechnicalSkillListService;
import com.example.service.technique.GetTechnicalSkillByNameAndCategoryService;

@Controller
@RequestMapping("/skill/item/register")
public class RegisterTechnicalSkillController {

	@Autowired
	private AddTechnicalSkillListService addtTechnicalSkillListService;

	@Autowired
	private GetTechnicalSkillByNameAndCategoryService getTechnicalSkillByNameAndCategoryService;

	@ModelAttribute
	public RegisterTechnicalSkillListForm setUpRegisterItemForm() {
		RegisterTechnicalSkillListForm form = new RegisterTechnicalSkillListForm();
		List<TechnicalSkillForm> technicalSkillList = new ArrayList<>();
		technicalSkillList.add(new TechnicalSkillForm());
		form.setTechnicalSkillList(technicalSkillList);
		return form;
	}

	/**
	 * 新規テクニカルスキル登録画面を表示する.
	 * 
	 * @param model
	 * @return 新規テクニカルスキル登録画面
	 */
	@RequestMapping("")
	public String showRegisterTechnicalSkill(Model model) {
		
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
		return "skill/skill-register-item";
	}

	/**
	 * 新規テクニカルスキルの登録処理を行う.
	 * 
	 * @param form
	 * @param model
	 * @param loginUser
	 * @param redirectAttributes
	 * @return 新規テクニカルスキル登録画面
	 */
	@PostMapping("/do")
	public String registerTechnicalSkill(@Validated RegisterTechnicalSkillListForm form, BindingResult result,
			Model model, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
		System.out.println("エラー特定"+result);
		if (result.hasErrors()) {
			return showRegisterTechnicalSkill(model);
		}
		
		form = trimAllWhitespaceForRegisterTechnicalSkillForm(form);
		List<TechnicalSkillForm> formItemList = form.getTechnicalSkillList();
		
		boolean approval=false;
		boolean request = false;
		String failMessage="";
		
		// DB上に同一カテゴリ・同一名称のテクニカルスキル情報があるかチェック(大文字、小文字区別しない)
		for(TechnicalSkillForm item:formItemList) {
			TechnicalSkill checkTechnicalSkill
			=getTechnicalSkillByNameAndCategoryService.getTechinicalSkillByNameAndCategory(item.getName(), item.getCategory());
			if(Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==0) {
				approval=true;
				failMessage="登録失敗\nERROR:この内容は既に登録されています";
			}else if(Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==2) {
				request=true;
				failMessage="登録失敗\nERROR:この内容は申請中の内容と重複しています";
			}
		}
		
		//登録済、もしくは申請中の時エラーメッセージ
		if(approval||request) {
		if(approval&&request) {
			failMessage="登録失敗\nERROR:既に登録されている内容、申請中の内容です";			
		}		
		redirectAttributes.addFlashAttribute("failMessage",failMessage);
		return "redirect:/skill/item/register";
		}
		
		//formの値を重複チェック用の変数に値をコピーする
		List<TechnicalSkillForm> lowerCaseList = new ArrayList<>();
		for(TechnicalSkillForm item : formItemList) {
			lowerCaseList.add(new TechnicalSkillForm(item.getName(),item.getCategory()));
		}
		
		//入力値を比較するために全て小文字に変換
		lowerCaseList.stream()
		.forEach(item->{item.setName(item.getName().toLowerCase());});
		//入力項目が重複している時、フォームの内容をリセットして表示画面にリダイレクト
		boolean check=(lowerCaseList.size()== new HashSet<>(lowerCaseList).size());
		if(!check) {
			redirectAttributes.addFlashAttribute("failMessage", "承認失敗\nERROR:入力されたスキル名が重複しています");
			return "redirect:/skill/item/register";
		}
		
		// 挿入用のドメインリスト
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		// formItemListのスキル名、カテゴリ名をドメインにセットしリストにつめる
		formItemList.stream().forEach(formItem -> {
			TechnicalSkill ts = new TechnicalSkill();
			ts.setName(formItem.getName());
			ts.setCategory(formItem.getCategory());
			technicalSkillList.add(ts);
		});

		User user = loginUser.getUser();
	
		// ドメインに各プロパティをセット
		technicalSkillList.stream().forEach(item -> {
			item.setRequestUserId(user.getUserId());
			item.setStage(Stage.ACTIVE.getKey());
			item.setCreator(user.getName());
			LocalDateTime now = LocalDateTime.now();
			item.setCreatedAt(now);
			item.setUpdater(user.getName());
			item.setUpdatedAt(now);
			item.setVersion(1);
		});

		addtTechnicalSkillListService.addTechnicalSkillList(technicalSkillList);
		redirectAttributes.addFlashAttribute("checkForm", form);
		redirectAttributes.addFlashAttribute("message", "テクニカルスキルの登録が完了しました");
		return "redirect:/skill/item/register";
	}

	/**
	 * 重複チェックのメッセージを格納する.
	 * 
	 * @param form
	 * @return チェックメッセージ
	 */
	@ResponseBody
	@RequestMapping(value = "/check-item", method = RequestMethod.GET)
	public Map<String, String> checkTechnicalSkill(TechnicalSkillForm form) {

		Map<String, String> map = new HashMap<>();
		String checkMessage = null;

		// 入力されたスキル名のスペースを除く
		form.setName(StringUtils.trimAllWhitespace(form.getName()));

		TechnicalSkill checkTechnicalSkill = getTechnicalSkillByNameAndCategoryService
				.getTechinicalSkillByNameAndCategory(form.getName(), form.getCategory());
		// DB上に同一カテゴリ・同一名称のテクニカルスキル情報があるかチェック(大文字、小文字区別しない)
		if (Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==0) {
			checkMessage = "このスキル名は既に登録されています";
		} else if(Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==2)  {
			checkMessage = "申請中のスキル名と重複しています";
		}else {
			checkMessage = "";
		}

		map.put("checkMessage", checkMessage);
		return map;
	}

	/**
	 * テクニカルスキルリストフォームの入力値にある空白を削除
	 * 
	 * @param form テクニカルスキルリストのフォーム
	 * @return テクニカルスキルリストのフォーム
	 */
	public RegisterTechnicalSkillListForm trimAllWhitespaceForRegisterTechnicalSkillForm(
			RegisterTechnicalSkillListForm form) {
		form.getTechnicalSkillList().stream().forEach(technicalSkillform -> {
			technicalSkillform.setName(StringUtils.trimAllWhitespace(technicalSkillform.getName()));
		});
		return form;

	}

}