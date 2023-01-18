package com.example.controller.skill;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Template;
import com.example.form.DeleteTemplateForm;
import com.example.service.template.CheckTemplateVersionService;
import com.example.service.template.DeleteTemplateService;

@Controller
@RequestMapping("/skill/template/delete")
public class DeleteTemplateController {
	
	@Autowired
	private DeleteTemplateService deleteTemplateService;
	
	@Autowired
	private CheckTemplateVersionService checkTemplateVersionService;
	
	@RequestMapping("")
	public String deleteTemplate(DeleteTemplateForm form,RedirectAttributes flash) {
		Integer templateId = form.getTemplateId();
		Integer version = form.getVersion();
		
		List<Template> templateListForVersionCheck = checkTemplateVersionService.checkTemplateVersion(templateId, version);
		if (Objects.isNull(templateListForVersionCheck)) {
			flash.addFlashAttribute("versionErrorMessage", "更新失敗 ERROR: テンプレートは管理者によって削除されました");
			return "redirect:/skill/template";
		}
		
		deleteTemplateService.deleteTemplate(templateId);
		flash.addFlashAttribute("deleteCompleted", "テンプレートの削除が完了しました");
		return "redirect:/skill/template";
	}
	
}
