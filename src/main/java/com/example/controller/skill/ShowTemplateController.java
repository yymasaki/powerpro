package com.example.controller.skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Template;
import com.example.domain.TemplateBasicSkill;
import com.example.domain.User;
import com.example.example.UserExample;
import com.example.form.DeleteTemplateForm;
import com.example.form.SelectTemplateForm;
import com.example.service.status.GetPersonalityService;
import com.example.service.template.GetTemplateBasicSkillListService;
import com.example.service.template.GetTemplateListService;
import com.example.service.template.GetTemplateService;
import com.example.service.user.UpdateUserService;

@Controller
@RequestMapping("/skill/template")
public class ShowTemplateController {
	
	@Autowired
	private GetTemplateService getTemplateService;
	
	@Autowired
	private GetTemplateListService getTemplateListService;
	
	@Autowired
	private GetTemplateBasicSkillListService getTemplateBasicSkillListService;
	
	@Autowired
	private GetPersonalityService getPersonalityService;
	
	@Autowired
	private UpdateUserService updateUserService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public SelectTemplateForm setUpSelectTemplateForm() {
		return new SelectTemplateForm();
	}
	
	@ModelAttribute
	public DeleteTemplateForm setUpDeleteTemplateForm() {
		return new DeleteTemplateForm();
	}
	
	/**
	 * テンプレート選択画面を表示する.
	 * 
	 * @param model リクエストスコープ 
	 * @param form　テンプレート選択フォーム
	 * @param loginUser　ログインユーザー
	 * @return　テンプレート選択画面
	 */
	@RequestMapping("")
	public String showTemplate(Model model,@ModelAttribute("selectTemplateForm") SelectTemplateForm form,@AuthenticationPrincipal LoginUser loginUser,RedirectAttributes flash) {
		
		User user = loginUser.getUser();
		Integer userId = user.getUserId();
		Integer departmentId = user.getDepartmentId();
		Integer selectedTemplateId = user.getSelectedTemplateId();
		Integer templateId = form.getTemplateId();
		
		// セレクトボックス用にtemplateListを取得
		List<Template> templateList = getTemplateListService.getTemplateList(departmentId);
		Integer sizeOfTemplateList = templateList.size();
		
		// URLにtemplateIdを直接入力された際の対策
		if (sizeOfTemplateList != 0 && !Objects.isNull(form.getTemplateId())) {
			boolean isTemplateIdExist = false;
			for (int i = 0; i < sizeOfTemplateList; i++) {
				Template template = templateList.get(i);
				if (template.getTemplateId() == form.getTemplateId()) {
					isTemplateIdExist = true;
				}
			}
			if (!isTemplateIdExist) {
				flash.addFlashAttribute("versionErrorMessage", "取得失敗 ERROR:  URLに不正な値が入力されました");
				return "redirect:/skill/template";
			}
		}
		
		// templateIdの優先順位 form.getTemplateId > user.getSelectedTemplateId > getTemplateListService
		if (sizeOfTemplateList == 0 && departmentId == 5) {
			model.addAttribute("noTemplateListMessage", "表示できるテンプレートが存在しません。テンプレートを登録してください。");
		} else if (sizeOfTemplateList == 0) {
			model.addAttribute("noTemplateListMessage", "表示できるテンプレートが存在しません。管理者がテンプレート登録するまでお待ちください。");
		} else if (Objects.isNull(templateId) && Objects.isNull(selectedTemplateId)) {
			// form.getTemplateIdがnull かつ ログインユーザーがテンプレートを選択していない場合 
			templateId = templateList.get(0).getTemplateId();
		} else if (Objects.isNull(templateId) && !Objects.isNull(selectedTemplateId)) {
			// form.getTemplateIdがnull かつ ログインユーザーがテンプレートを選択している場合
			templateId = selectedTemplateId;
		} 
		
		Template showTemplate = new Template();
		if (!Objects.isNull(templateId)) {
			showTemplate = getTemplateService.getTemplate(templateId);
			form.setTemplateId(templateId);
		}
		
		if (Objects.isNull(showTemplate)) {
			flash.addFlashAttribute("versionErrorMessage", "取得失敗 ERROR: テンプレートは管理者によって削除されました");
			return "redirect:/skill/template";
		}
		List<Personality> personalityList = getPersonalityService.getPersonality();
		
		if (Objects.isNull(selectedTemplateId)) {
			model.addAttribute("selectedTemplateName", "なし");
		} else {
			Template selectedTemplate = getTemplateService.getTemplate(selectedTemplateId);
			model.addAttribute("selectedTemplateName", selectedTemplate.getName());
		}
		
		session.setAttribute("templateId", templateId);
		model.addAttribute("template", showTemplate);
		model.addAttribute("templateList", templateList);
		model.addAttribute("personalityList", personalityList);
		model.addAttribute("userId", userId);
		return "skill/skill-template";
	}

	/**
	 * テンプレートを選択し、その情報をDBに保持する.
	 * 
	 * @param form テンプレート選択フォーム
	 * @param flash フラッシュスコープ
	 * @param loginUser　ログインユーザー
	 * @return テンプレート選択画面
	 */
	@PostMapping("/select")
	public String selectTemplate(SelectTemplateForm form,RedirectAttributes flash,@AuthenticationPrincipal LoginUser loginUser) {
		Integer templateId = form.getTemplateId();
		Template template = getTemplateService.getTemplate(templateId);
		if (Objects.isNull(template)) {
			flash.addFlashAttribute("versionErrorMessage", "取得失敗 ERROR: テンプレートは管理者によって削除されました");
			return "redirect:/skill/template";
		}
		
		User user = loginUser.getUser();
		user.setSelectedTemplateId(templateId);
		
		UserExample example = new UserExample();
		example.or().andUserIdEqualTo(user.getUserId());
		
		updateUserService.editUser(user, example);
		flash.addFlashAttribute("editingCompleted", "テンプレートが選択されました");
		return "redirect:/skill/template";
	}
	
	/**
	 * テンプレートidからテンプレート基本スキルを取得する.
	 * 
	 * @param templateId テンプレートid
	 * @return テンプレート基本スキル（json形式）
	 */
	@ResponseBody
	@RequestMapping(value="/get_template_basic_skill_list", method=RequestMethod.GET)
	public Map<String, List<TemplateBasicSkill>> getTemplateBasicSkillList(Integer templateId){
		Map<String, List<TemplateBasicSkill>> map = new HashMap<>();
		List<TemplateBasicSkill> templateBasicSkillList = getTemplateBasicSkillListService.getTemplateBasicSkillList(templateId);
		map.put("templateBasicSkillList", templateBasicSkillList);
		return map;
	}
}
