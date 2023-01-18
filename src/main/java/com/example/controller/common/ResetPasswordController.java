package com.example.controller.common;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.ResetPasswordForm;
import com.example.service.common.GetUserByUpdaterService;
import com.example.service.common.SendFormUrlByEmailService;
import com.example.service.common.SetUuidOnUpdaterInUsersInfoService;
import com.example.service.common.UpdatePasswordService;

@Controller
@RequestMapping("/pass")
public class ResetPasswordController {

	@Autowired
	private SetUuidOnUpdaterInUsersInfoService setUuidOnUpdaterInUsersInfoService;

	@Autowired
	private SendFormUrlByEmailService sendEmailService;

	@Autowired
	private GetUserByUpdaterService getUserByUpdaterService;

	@Autowired
	private UpdatePasswordService updatePasswordService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public ResetPasswordForm setupForm() {
		return new ResetPasswordForm();
	}

	/**
	 * メールアドレス入力画面を表示する.
	 * 
	 * @return メールアドレス入力画面
	 */
	@RequestMapping("/mail")
	public String showEmailForm() {
		return "common/reset-pass-mail";
	}

	/**
	 * パスワード再設定用URLをメールで送る.
	 * 
	 * @param model リクエストスコープ
	 * @param email メールアドレス
	 * @param flash フラッシュスコープ
	 * @return ログイン画面（不正入力時はメールアドレス入力画面に戻る）
	 */
	@PostMapping("/mail/send")
	public String sendMail(Model model, String email, String domain, RedirectAttributes flash) {

		StringBuilder emailAddress = new StringBuilder();
		emailAddress.append(email).append(domain);
		// テストでのparam検証用
		flash.addFlashAttribute("email", emailAddress.toString());

		if (Objects.isNull(email) || Objects.isNull(domain)) {
			model.addAttribute("errorMessage", "メールアドレスを入力してください");
			return "common/reset-pass-mail";
		}
		email = email.replace("　", "").trim();
		if (email.isEmpty()) {
			model.addAttribute("errorMessage", "メールアドレスを入力してください");
			return "common/reset-pass-mail";
		}

		StringBuilder uuid = new StringBuilder();
		uuid.append(session.getId()).append(LocalDateTime.now());
		Boolean updateCompleted = setUuidOnUpdaterInUsersInfoService.setUuidAsUpdater(uuid.toString(), emailAddress.toString());
		if (!updateCompleted) {
			model.addAttribute("errorMessage", "このメールアドレスは登録されていません");
			return "common/reset-pass-mail";
		}

		sendEmailService.sendUrl("/pass/reset", uuid.toString(), emailAddress.toString());
		flash.addFlashAttribute("processCompleted", "メールを送りました。確認してください");
		
		return "redirect:/login";
	}

	/**
	 * パスワード再設定画面を表示する.
	 * 
	 * @param id    UUID
	 * @param model リクエストスコープ
	 * @return パスワード再設定画面
	 */

	@RequestMapping("/reset")
	public String showResetForm(String id, Model model) {
		// テストでのparam検証用
		model.addAttribute("paramId", id);
		try {
			User user = getUserByUpdaterService.getUserByUpdaterAndStage(id, "0").get(0);
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("name", user.getName());
		} catch (Exception e) {
			model.addAttribute("requestError", "ページの有効期限が切れています。");
		}
		return "common/reset-pass";
	}

	/**
	 * パスワードを再設定する.
	 * 
	 * @param form   入力情報
	 * @param result 入力チェック
	 * @param flash  フラッシュスコープ
	 * @param model  リクエストスコープ
	 * @return ログイン画面（不正入力時はパスワード再設定画面に戻る）
	 */
	@PostMapping("/reset/do")
	public String resetPassword(@Validated ResetPasswordForm form, BindingResult result, RedirectAttributes flash,
			Model model) {
		// テスト用にparamを格納
		flash.addFlashAttribute("resetPasswordForm", form);

		if (!form.getCheckPassword().equals(form.getPassword())) {
			result.rejectValue("checkPassword", null, "パスワードと確認用パスワードが一致しません");
		}

		if (result.hasErrors()) {
			model.addAttribute("userId", form.getUserId());
			model.addAttribute("name", form.getName());
			return "common/reset-pass";
		}

		updatePasswordService.update(Integer.parseInt(form.getUserId()), form.getPassword(), form.getName());
		flash.addFlashAttribute("processCompleted", "パスワードを再設定しました");
		return "redirect:/login";
	}
}
