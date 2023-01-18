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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.BasicSkill;
import com.example.domain.EngineerSkill;
import com.example.domain.LoginUser;
import com.example.domain.Template;
import com.example.form.RegisterTemplateForm;
import com.example.form.SelectTemplateForm;
import com.example.service.status.GetBasicSkillListService;
import com.example.service.status.GetEngineerSkillListService;
import com.example.service.template.AddTemplateService;
import com.example.service.template.GetTemplateForRegisterService;

@Controller
@RequestMapping("/skill/template/register")
public class RegisterTemplateController {
	
	@Autowired
	private GetEngineerSkillListService getEngineerSkillService;
	
	@Autowired
	private GetBasicSkillListService getBasicSkillService;
	
	@Autowired
	private AddTemplateService addTemplateService;
	
	@Autowired
	private GetTemplateForRegisterService getTemplateForRegisterService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public RegisterTemplateForm setUpRegisterTemplateForm  () {
		return new RegisterTemplateForm();
	}
	
	/**
	 * テンプレート登録画面に遷移する.
	 * 
	 * @param model リクエストスコープ
	 * @return テンプレート登録画面
	 */
	@RequestMapping("")
	public String toRegisterTemplate(Model model) {
		Integer templateId = (Integer) session.getAttribute("templateId");
		model.addAttribute("templateId", templateId);
		return "skill/skill-register-template";
	}
	
	/**
	 * テンプレート登録処理を行う.
	 * 
	 * @param form テンプレート登録フォーム
	 * @return
	 */
	@PostMapping("/do")
	public String registerTemplate(@Validated RegisterTemplateForm form,BindingResult result,
		 @AuthenticationPrincipal LoginUser loginUser, Model model, RedirectAttributes flash) {
		// 同一所属内に同じ名前のテンプレートが存在する場合、エラーを返す
		List<Template> templateList = getTemplateForRegisterService.getTemplateForRegister(form.getDepartmentId(),form.getName());
		if (templateList.size() != 0) {
			result.rejectValue("name", null, "このテンプレート名は既に使用されています");
		}
		
		List<Integer> templateEngineerSkillScoreList = form.getTemplateEngineerSkillScoreList(); 
		for (Integer templateEngineerSkillScore  : templateEngineerSkillScoreList) {
			if (Objects.isNull(templateEngineerSkillScore)) {
				result.rejectValue("templateEngineerSkillScoreList", null, "1から100の数字を入力してください");
			}
		}
		
		List<Integer> templateBasicSkillScoreList = form.getTemplateBasicSkillScoreList();
		for (Integer templateBasicSkillScore : templateBasicSkillScoreList) {
			if (Objects.isNull(templateBasicSkillScore)) {
				result.rejectValue("templateBasicSkillScoreList", null, "1から5の数字を入力してください");
			}
		}
		
		// エラーがある場合、テンプレート登録画面に遷移する
		if (result.hasErrors()) {
			return toRegisterTemplate(model);
		}
		
		String userName = loginUser.getUser().getName();
		Integer templateId = addTemplateService.registerTemplate(form,userName);
		SelectTemplateForm selectTemplateForm = new SelectTemplateForm();
		selectTemplateForm.setTemplateId(templateId);
		
		flash.addFlashAttribute("additionCompleted","テンプレートの登録が完了しました");
		flash.addFlashAttribute("selectTemplateForm", selectTemplateForm);
		return "redirect:/skill/template";
	}
	
	/**
	 * エンジニアスキルを取得する.
	 * 
	 * @param departmentId 所属id
	 * @return エンジニアスキル(json形式)
	 */
	@ResponseBody
	@RequestMapping(value="/get_enginner_skill_list", method=RequestMethod.GET)
	public Map<String, List<EngineerSkill>> getEngineerSkillList(Integer departmentId){
		Map<String, List<EngineerSkill>> map = new HashMap<>();
		List<EngineerSkill> engineerSkillList = getEngineerSkillService.getEngineerSkillList(departmentId);
		map.put("engineerSkillList", engineerSkillList);
		return map;
	}
	
	/**
	 * 基本スキルを取得する.
	 * 
	 * @param departmentId 所属id
	 * @return 基本スキル（json形式）
	 */
	@ResponseBody
	@RequestMapping(value="/get_basic_skill_list", method=RequestMethod.GET)
	public Map<String, List<BasicSkill>> getBasicSkillList(Integer departmentId){
		Map<String, List<BasicSkill>> map = new HashMap<>();
		List<BasicSkill> basicSkillList = getBasicSkillService.getBasicSkillList(departmentId);
		map.put("basicSkillList", basicSkillList);
		return map;
	}
	
	/**
	 * 同じ所属内に同一のテンプレート名が存在するかどうか判定し、メッセージを返す.
	 * 
	 * @param departmentId 所属id
	 * @param name テンプレート名
	 * @return メッセージ(json形式)
	 */
	@ResponseBody
	@RequestMapping(value="/check_duplicate", method=RequestMethod.GET)
	public Map<String, String> checkDuplicateForRegisterTemplate(Integer departmentId, String name){
		Map<String, String> map = new HashMap<>();
		
		List<Template> templateList = getTemplateForRegisterService.getTemplateForRegister(departmentId, name);
		if (templateList.size() == 0) {
			map.put("message", "このテンプレート名は有効です");
		} else {
			map.put("message", "このテンプレート名は既に使用されています");
		}
		return map;
	}
}
