package com.example.controller.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.form.AddUserForm;
import com.example.service.common.CheckDuplicateEmailsService;
import com.example.service.user.AddNewUserService;

@Controller
public class AddUserController {

	@Autowired
	private CheckDuplicateEmailsService checkDuplicateEmailsService;

	@Autowired
	private AddNewUserService addNewUserService;

	@ModelAttribute
	public AddUserForm setupForm() {
		return new AddUserForm();
	}

	/**
	 * ユーザー登録画面を表示する.
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/user/register")
	public String showForm() {
		return "user/user-register";
	}

	/**
	 * ユーザー登録する.
	 * 
	 * @param form      ユーザー情報
	 * @param result    入力チェック
	 * @param flash     フラッシュスコープ
	 * @param loginUser ログイン情報
	 * @return ユーザー登録画面
	 */
	@PostMapping("/user/register/do")
	public String add(@Validated AddUserForm form, BindingResult result, RedirectAttributes flash,
			@AuthenticationPrincipal LoginUser loginUser) {

		if (!form.getCheckPassword().equals(form.getPassword())) {
			result.rejectValue("checkPassword", null, "パスワードと確認用パスワードが一致しません");
		}

		boolean duplicateEmail = false;
		if (Objects.nonNull(form.getEmail()) && !form.getEmail().isEmpty()) {
			duplicateEmail = checkDuplicateEmailsService.checkDuplication(form.getEmail()+form.getDomain());
		}
		if (duplicateEmail) {
			result.rejectValue("email", null, "このメールアドレスは登録済です");
		}

		if (result.hasErrors()) {
			return "user/user-register";
		}

		addNewUserService.add(form, loginUser.getUser().getName());
		flash.addFlashAttribute("additionCompleted", "ユーザーの登録が完了しました");
		
		//テスト用にparamを格納
		flash.addFlashAttribute("addUserFormForJUnitTest", form);

		return "redirect:/user/register";
	}

	/**
	 * メールアドレス重複チェックをする.
	 * 
	 * @param email メールアドレス
	 * @return メッセージ
	 */
	@ResponseBody
	@RequestMapping("/user/email-check-api")
	public Map<String, String> emailCheck(String email) {
		Map<String, String> map = new HashMap<>();

		boolean duplicateEmail = checkDuplicateEmailsService.checkDuplication(email);
		if (duplicateEmail) {
			map.put("duplicateEmail", "このメールアドレスは既に登録されています");
		} else {
			map.put("duplicateEmail", null);
		}

		return map;
	}

}
