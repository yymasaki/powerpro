package com.example.controller.skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.domain.Template;
import com.example.form.EditTemplateForm;
import com.example.form.SelectTemplateForm;
import com.example.service.template.CheckTemplateVersionService;
import com.example.service.template.GetTemplateForEditService;
import com.example.service.template.GetTemplateService;
import com.example.service.template.UpdateTemplateService;

@Controller
@RequestMapping("/skill/template/edit")
public class EditTemplateController {

	@Autowired
	private GetTemplateService getTemplateService;
	
	@Autowired
	private GetTemplateForEditService getTemplateForEditService;
	
	@Autowired
	private CheckTemplateVersionService checkTemplateVersionService;
	
	@Autowired
	private UpdateTemplateService updateTemplateService;
	
	@ModelAttribute
	public EditTemplateForm setUpEditTemplateForm () {
		return new EditTemplateForm();
	}
	
	/**
	 * テンプレート編集画面を表示する.
	 * 
	 * @param templateId テンプレートid
	 * @param model リクエストスコープ
	 * @return テンプレート編集画面
	 */
	@RequestMapping("")
	public String toEditTemplate(Integer templateId,Integer version,Model model,RedirectAttributes flash) {
		Template template = getTemplateService.getTemplate(templateId);
		Integer currentVersion = template.getVersion();
		// version照合
		if (currentVersion != version) {
			flash.addFlashAttribute("versionErrorMessage", "取得失敗 ERROR: このテンプレートは更新されています");
			return "redirect:/skill/template";
		}
		
		model.addAttribute("template", template);
		return "skill/skill-edit-template";
	}
	
	/**
	 * テンプレートを更新する.
	 * 
	 * @param form テンプレート編集フォーム
	 * @param result エラー格納オブジェクト
	 * @param model リクエストスコープ
	 * @param loginUser　ログインユーザー
	 * @return　テンプレート選択画面（リダイレクト）
	 */
	@PostMapping("/do")
	public String editTemplate(@Validated EditTemplateForm form, BindingResult result,
			Model model,@AuthenticationPrincipal LoginUser loginUser,RedirectAttributes flash) {
		Integer templateId = form.getTemplateId();
		Integer version = form.getVersion();
		
		// 排他制御のためにバージョンのチェックを行う
		List<Template> templateListForVersionCheck = checkTemplateVersionService.checkTemplateVersion(templateId, version);
		Template templateForVersionCheck = getTemplateService.getTemplate(templateId);
		if (Objects.isNull(templateListForVersionCheck) && !Objects.isNull(templateForVersionCheck)) {
			// 他の管理者に更新された場合
			flash.addFlashAttribute("versionErrorMessage", "更新失敗 ERROR: テンプレートは管理者によって更新されました");
			return "redirect:/skill/template";
		} else if (Objects.isNull(templateListForVersionCheck) && Objects.isNull(templateForVersionCheck)) {
			// 他の管理者に削除された場合
			flash.addFlashAttribute("versionErrorMessage", "更新失敗 ERROR: テンプレートは管理者によって削除されました");
			return "redirect:/skill/template";
		}
		
		// 同一所属内に現在編集中のテンプレート以外で名前が重複するテンプレートが存在する場合、エラーを返す
		List<Template> templateList = getTemplateForEditService.getTemplateForEdit(templateId, form.getDepartmentId(), form.getName());
		if (templateList.size() != 0) {
			result.rejectValue("name", null, "このテンプレート名は既に使用されています");
		}
		// テンプレートエンジニアスキルスコアリストに未入力のものがある場合、エラーを返す
		List<Integer> templateEngineerSkillScoreList = form.getTemplateEngineerSkillScoreList();
		for (Integer templateEngineerSkillScore : templateEngineerSkillScoreList) {
			if (Objects.isNull(templateEngineerSkillScore)) {
				result.rejectValue("templateEngineerSkillScoreList", null, "0から100の数字を入力してください");
			}
		}
		// テンプレート基本スキルスコアリストに未入力のものがある場合、エラーを返す
		List<Integer> templateBasicSkillScoreList = form.getTemplateBasicSkillScoreList();
		for (Integer templateBasicSkillScore : templateBasicSkillScoreList) {
			if (Objects.isNull(templateBasicSkillScore)) {
				result.rejectValue("templateBasicSkillScoreList", null, "1から5の数字を入力してください");
			}
		}
		
		// 1つでもエラーがあれば編集画面に遷移する
		if (result.hasErrors()) {
			return toEditTemplate(templateId,version,model,flash);
		}
		
		String userName = loginUser.getUser().getName();
		updateTemplateService.editTemplate(form, userName);
		SelectTemplateForm selectTemplateForm = new SelectTemplateForm();
		selectTemplateForm.setTemplateId(templateId);
		
		flash.addFlashAttribute("editingCompleted", "テンプレートの編集が完了しました");
		flash.addFlashAttribute("selectTemplateForm", selectTemplateForm);
		return "redirect:/skill/template";
	}
	
	/**
	 * 同じ所属内、かつ、現在編集中のテンプレートを除いて、同一のテンプレート名が存在するかどうか判定し、メッセージを返す.
	 * 
	 * @param templateId テンプレートid
	 * @param departmentId 所属id
	 * @param name テンプレート名
	 * @return　メッセージ（json形式）
	 */
	@ResponseBody
	@RequestMapping("/check_duplicate")
	public Map<String, String> checkDuplicateForEditTemplate(Integer templateId,Integer departmentId, String name){
		Map<String, String> map = new HashMap<>();
		List<Template> templateList = getTemplateForEditService.getTemplateForEdit(templateId, departmentId, name);
		if (templateList.size() == 0) {
			map.put("message", "このテンプレート名は有効です");
		} else {
			map.put("message", "このテンプレート名は既に使用されています");
		}
		return map;
	}
}
