package com.example.controller.skill;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.form.ProcessSpecsheetForm;
import com.example.service.spec.DeleteSpecsheetService;
import com.example.service.spec.SendSpecMailService;

@Controller
@RequestMapping("/skill/spec")
public class DeleteSpecsheetController {
	
	@Autowired
	private DeleteSpecsheetService deleteSpecsheetService;
	
	@Autowired
	private ShowSpecsheetController showSpecsheetController;
	
	@Autowired
	private SendSpecMailService sendSpecMailService;

	@ModelAttribute
	public ProcessSpecsheetForm setUpProcessSpecsheetForm() {
		return new ProcessSpecsheetForm();
	}
	
	/**
	 * スペックシートを削除するメソッド.
	 * 
	 * @param form スペックシート更新処理用フォーム
	 * @param result エラー格納オブジェクト
	 * @param model リクエストスコープ
	 * @param redirectAttributes フラッシュスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return スペックシート画面
	 */
	@PostMapping("/delete")
	public String deleteSpecsheet(@Validated ProcessSpecsheetForm form, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		
		if(!Stage.ACTIVE.getKey().equals(form.getStage())) {
			System.out.println("不正な削除が行われました。");
			throw new IllegalArgumentException();
		}
		String adminComment = form.getAdminComment().strip();
		if (!StringUtils.hasText(adminComment)) {
			result.rejectValue("adminComment", null, "入力は必須です");
		}
		if(result.hasErrors()) {
			return showSpecsheetController.showSpecsheet(form.getUserId(), model, loginUser);
		}
		deleteSpecsheetService.deleteSpecsheet(form.getSpecsheetId());
		redirectAttributes.addFlashAttribute("success", "対象のスペックシートを削除しました");
		
		sendSpecMailService.sendSpecMail(
				"既存のスペックシート削除", adminComment, form.getSpecsheetId(), form.getUserId(), form.getStage());
		
		return "redirect:/skill/spec?userId=" + form.getUserId();
	}

}
