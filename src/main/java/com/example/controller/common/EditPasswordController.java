package com.example.controller.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.EditPasswordForm;
import com.example.service.common.UpdatePasswordService;

@Controller
@RequestMapping("/pass-edit")
public class EditPasswordController {

	@Autowired
	private UpdatePasswordService updatePasswordService;

	@ModelAttribute
	public EditPasswordForm setupForm() {
		return new EditPasswordForm();
	}

	/**
	 * パスワード変更画面を表示する.
	 * 
	 * @return mayumiono
	 */
	@RequestMapping("")
	public String showEditForm(@RequestParam(required = false) boolean validPass, Model model) {
		if (validPass) {
			model.addAttribute("validPassword", "パスワード有効期限(180日間)切れです。更新してください。");
		}
		return "common/edit-pass";
	}

	/**
	 * パスワードを変更する.
	 * 
	 * @param form   新パスワード情報
	 * @param result 入力チェック
	 * @param flash  フラッシュスコープ
	 * @param user   ログイン情報
	 * @return パスワード変更画面
	 */
	@PostMapping("/do")
	public String edit(@Validated EditPasswordForm form, BindingResult result, RedirectAttributes flash,
			@AuthenticationPrincipal LoginUser loginUser) {

		User user = loginUser.getUser();
		// テストで値を検証するためパラメータをフラッシュスコープに格納する.
		flash.addFlashAttribute("editPasswordForm", form);

		if (form.getNewPassword().equals(form.getCurrentPassword())) {
			result.rejectValue("newPassword", null, "新パスワードが現在のパスワードと同じです");
		}

		if (!form.getCheckPassword().equals(form.getNewPassword())) {
			result.rejectValue("checkPassword", null, "新パスワードと確認用パスワードが一致しません");
		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
			result.rejectValue("currentPassword", null, "現パスワードが誤っています");
		}
		if (result.hasErrors()) {
			return "common/edit-pass";
		}
		updatePasswordService.update(user.getUserId(), form.getNewPassword(), user.getName());
		flash.addFlashAttribute("updateCompleted", "パスワード変更が完了しました");
		return "redirect:/pass-edit";
	}

	/**
	 * 現在のパスワードをチェックする.
	 * 
	 * @param password  パスワード
	 * @param loginUser ログイン情報
	 * @return チェック結果
	 */
	@ResponseBody
	@RequestMapping("/pass-check-api")
	public Map<String, String> passCheck(String password, @AuthenticationPrincipal LoginUser loginUser) {
		Map<String, String> map = new HashMap<>();
		User user = loginUser.getUser();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(password, user.getPassword())) {
			map.put("passwordError", "現在のパスワードが誤っています");
		} else {
			map.put("passwordError", null);
		}

		return map;
	}
}
