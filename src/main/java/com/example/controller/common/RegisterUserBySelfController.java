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

import com.example.form.RegisterUserForm;
import com.example.service.common.AddTemporaryUserService;
import com.example.service.common.CheckDuplicateEmailsService;
import com.example.service.common.DefinitiveUserResistrationService;
import com.example.service.common.GetUserByUpdaterService;
import com.example.service.common.SendFormUrlByEmailService;

@Controller
@RequestMapping("/register")
public class RegisterUserBySelfController {

	@Autowired
	private AddTemporaryUserService addTemporaryUserService;
	@Autowired
	private CheckDuplicateEmailsService checkEmailService;

	@Autowired
	private SendFormUrlByEmailService sendEmailService;

	@Autowired
	private GetUserByUpdaterService getUserByUpdaterService;

	@Autowired
	private DefinitiveUserResistrationService definitiveUserResistrationService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public RegisterUserForm setupForm() {
		return new RegisterUserForm();
	}

	/**
	 * 会員登録用メールアドレス入力画面を表示する .
	 * 
	 * @return メールアドレス入力画面
	 */
	@RequestMapping("/mail")
	public String showEmailForm() {
		return "common/register-mail";
	}

	/**
	 * 会員登録画面のURLをメールで送信する.
	 * 
	 * @param model リクエストスコープ
	 * @param email 新規メールアドレス
	 * @return ログイン画面
	 */
	@PostMapping("/mail/send")
	public String sendMail(Model model, String email, String domain, RedirectAttributes flash) {
		StringBuilder emailAddress = new StringBuilder();
		emailAddress.append(email).append(domain);
		// テストで値を検証するためパラメータをフラッシュスコープに格納する.
		flash.addFlashAttribute("emailAddress", emailAddress.toString());

		if (Objects.isNull(email) || Objects.isNull(domain)) {
			model.addAttribute("errorMessage", "メールアドレスを入力してください");
			return "common/register-mail";
		}
		email = email.replace("　", "").trim();
		if (email.isEmpty()) {
			model.addAttribute("errorMessage", "メールアドレスを入力してください");
			return "common/register-mail";
		}
		boolean duplicateEmail = checkEmailService.checkDuplication(emailAddress.toString());
		if (duplicateEmail) {
			model.addAttribute("errorMessage", "このメールアドレスは登録済です");
			return "common/register-mail";
		}
		StringBuilder uuid = new StringBuilder();
		uuid.append(session.getId()).append(LocalDateTime.now());

		addTemporaryUserService.insert(emailAddress.toString(), uuid.toString());
		sendEmailService.sendUrl("/register", uuid.toString(), emailAddress.toString());
		flash.addFlashAttribute("processCompleted", "メールを送りました。確認してください");

		return "redirect:/login";
	}

	/**
	 * 会員登録画面を表示する.
	 * 
	 * @param id メールアドレス登録時のsessionId
	 * @return 会員登録画面
	 */
	@RequestMapping("")
	public String showRegisterForm(Model model, String id) {
		try {
			model.addAttribute("email", getUserByUpdaterService.getUserByUpdaterAndStage(id, "1").get(0).getEmail());
		} catch (Exception e) {
			model.addAttribute("requestError", "会員登録ページの有効期限が切れています。\n先にメールアドレスを登録してください");
		}
		return "common/register";
	}

	/**
	 * 会員情報を本登録する.
	 * 
	 * @param form   入力されたユーザー情報
	 * @param result 入力チェック
	 * @param flash  フラッシュスコープ
	 * @return ログイン画面
	 */
	@PostMapping("/do")
	public String registerUser(@Validated RegisterUserForm form, BindingResult result, RedirectAttributes flash,
			Model model) {
		// テストで値を検証するためパラメータをフラッシュスコープに格納する.
		flash.addFlashAttribute("registerUserForm", form);

		if (!form.getCheckPassword().equals(form.getPassword())) {
			result.rejectValue("checkPassword", null, "パスワードと確認用パスワードが一致しません");
		}

		if (result.hasErrors()) {
			model.addAttribute("email", form.getEmail());
			return "common/register";
		}
		definitiveUserResistrationService.definitiveResistration(form);
		flash.addFlashAttribute("processCompleted", "会員登録が完了しました");
		return "redirect:/login";
	}

}
