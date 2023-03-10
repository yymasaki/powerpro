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
	 * ?????????????????????????????????????????????????????????.
	 * 
	 * @param model
	 * @return ??????????????????????????????????????????
	 */
	@RequestMapping("")
	public String showRegisterTechnicalSkill(Model model) {
		
		// ???????????????????????????????????????
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
	 * ??????????????????????????????????????????????????????.
	 * 
	 * @param form
	 * @param model
	 * @param loginUser
	 * @param redirectAttributes
	 * @return ??????????????????????????????????????????
	 */
	@PostMapping("/do")
	public String registerTechnicalSkill(@Validated RegisterTechnicalSkillListForm form, BindingResult result,
			Model model, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
		System.out.println("???????????????"+result);
		if (result.hasErrors()) {
			return showRegisterTechnicalSkill(model);
		}
		
		form = trimAllWhitespaceForRegisterTechnicalSkillForm(form);
		List<TechnicalSkillForm> formItemList = form.getTechnicalSkillList();
		
		boolean approval=false;
		boolean request = false;
		String failMessage="";
		
		// DB????????????????????????????????????????????????????????????????????????????????????????????????(????????????????????????????????????)
		for(TechnicalSkillForm item:formItemList) {
			TechnicalSkill checkTechnicalSkill
			=getTechnicalSkillByNameAndCategoryService.getTechinicalSkillByNameAndCategory(item.getName(), item.getCategory());
			if(Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==0) {
				approval=true;
				failMessage="????????????\nERROR:?????????????????????????????????????????????";
			}else if(Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==2) {
				request=true;
				failMessage="????????????\nERROR:?????????????????????????????????????????????????????????";
			}
		}
		
		//???????????????????????????????????????????????????????????????
		if(approval||request) {
		if(approval&&request) {
			failMessage="????????????\nERROR:????????????????????????????????????????????????????????????";			
		}		
		redirectAttributes.addFlashAttribute("failMessage",failMessage);
		return "redirect:/skill/item/register";
		}
		
		//form???????????????????????????????????????????????????????????????
		List<TechnicalSkillForm> lowerCaseList = new ArrayList<>();
		for(TechnicalSkillForm item : formItemList) {
			lowerCaseList.add(new TechnicalSkillForm(item.getName(),item.getCategory()));
		}
		
		//?????????????????????????????????????????????????????????
		lowerCaseList.stream()
		.forEach(item->{item.setName(item.getName().toLowerCase());});
		//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		boolean check=(lowerCaseList.size()== new HashSet<>(lowerCaseList).size());
		if(!check) {
			redirectAttributes.addFlashAttribute("failMessage", "????????????\nERROR:???????????????????????????????????????????????????");
			return "redirect:/skill/item/register";
		}
		
		// ?????????????????????????????????
		List<TechnicalSkill> technicalSkillList = new ArrayList<>();
		// formItemList????????????????????????????????????????????????????????????????????????????????????
		formItemList.stream().forEach(formItem -> {
			TechnicalSkill ts = new TechnicalSkill();
			ts.setName(formItem.getName());
			ts.setCategory(formItem.getCategory());
			technicalSkillList.add(ts);
		});

		User user = loginUser.getUser();
	
		// ?????????????????????????????????????????????
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
		redirectAttributes.addFlashAttribute("message", "??????????????????????????????????????????????????????");
		return "redirect:/skill/item/register";
	}

	/**
	 * ???????????????????????????????????????????????????.
	 * 
	 * @param form
	 * @return ???????????????????????????
	 */
	@ResponseBody
	@RequestMapping(value = "/check-item", method = RequestMethod.GET)
	public Map<String, String> checkTechnicalSkill(TechnicalSkillForm form) {

		Map<String, String> map = new HashMap<>();
		String checkMessage = null;

		// ???????????????????????????????????????????????????
		form.setName(StringUtils.trimAllWhitespace(form.getName()));

		TechnicalSkill checkTechnicalSkill = getTechnicalSkillByNameAndCategoryService
				.getTechinicalSkillByNameAndCategory(form.getName(), form.getCategory());
		// DB????????????????????????????????????????????????????????????????????????????????????????????????(????????????????????????????????????)
		if (Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==0) {
			checkMessage = "???????????????????????????????????????????????????";
		} else if(Objects.nonNull(checkTechnicalSkill)&&checkTechnicalSkill.getStage()==2)  {
			checkMessage = "????????????????????????????????????????????????";
		}else {
			checkMessage = "";
		}

		map.put("checkMessage", checkMessage);
		return map;
	}

	/**
	 * ?????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param form ????????????????????????????????????????????????
	 * @return ????????????????????????????????????????????????
	 */
	public RegisterTechnicalSkillListForm trimAllWhitespaceForRegisterTechnicalSkillForm(
			RegisterTechnicalSkillListForm form) {
		form.getTechnicalSkillList().stream().forEach(technicalSkillform -> {
			technicalSkillform.setName(StringUtils.trimAllWhitespace(technicalSkillform.getName()));
		});
		return form;

	}

}