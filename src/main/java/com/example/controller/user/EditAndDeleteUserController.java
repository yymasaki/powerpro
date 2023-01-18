package com.example.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.EditUserForm;
import com.example.service.user.DeleteUserService;
import com.example.service.user.GetSpecificUserService;
import com.example.service.user.UpdateUserInfoService;

@Controller
public class EditAndDeleteUserController {

	@Autowired
	private GetSpecificUserService getSpecificUserService;

	@Autowired
	private UpdateUserInfoService updateUserInfoService;

	@Autowired
	private DeleteUserService deleteUserService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public EditUserForm setupForm(@RequestParam Integer userId) {
		User user = getSpecificUserService.get(userId);
		EditUserForm editUserForm = new EditUserForm();
		editUserForm.setUserId(user.getUserId());
		String[] fullName = user.getName().split("　");
		String lastName = fullName[0];
		editUserForm.setLastName(lastName);
		session.setAttribute("lastName", lastName);
		if (fullName.length >= 2) {
			String firstName = fullName[1];
			editUserForm.setFirstName(firstName);
			session.setAttribute("firstName", firstName);
		}
		int year = user.getHiredOn().getYear();
		int month = user.getHiredOn().getMonthValue();
		String hiredOn = "";
		if (month < 10) {
			hiredOn = year + "-0" + month;
		} else {
			hiredOn = year + "-" + month;
		}
		editUserForm.setHiredOn(hiredOn);
		session.setAttribute("hiredOn", hiredOn);

		editUserForm.setDepartmentId(String.valueOf(user.getDepartmentId()));
		session.setAttribute("departmentId", String.valueOf(user.getDepartmentId()));

		String originEmail = user.getEmail();
		session.setAttribute("email", originEmail);
		// メールアドレスをドメイン名で区切る
		int index = originEmail.indexOf("@");
		String email = originEmail.substring(0, index);
		String domain = originEmail.substring(index);
		editUserForm.setEmail(email);
		editUserForm.setDomain(domain);
		editUserForm.setVersion(String.valueOf(user.getVersion()));
		return editUserForm;
	}

	/**
	 * ユーザー編集画面を表示する.
	 * 
	 * @param userId ユーザーID
	 * @param model  リクエストスコープ
	 * @return ユーザー編集画面
	 */
	@RequestMapping("/user/edit")
	public String showEditForm(Integer userId) {
		return "user/user-edit";
	}

	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param form      ユーザー更新情報
	 * @param result    入力チェック
	 * @param flash     フラッシュスコープ
	 * @param loginUser ログイン情報
	 * @return ユーザー編集画面
	 */
	@PostMapping("/user/edit/do")
	public String edit(@Validated EditUserForm form, BindingResult result, RedirectAttributes flash,
			@AuthenticationPrincipal LoginUser loginUser) {
		if (result.hasErrors()) {
			return "user/user-edit";
		}
		// ドメイン名を含めたメールをセット
		String email = form.getEmail() + form.getDomain();
		form.setEmail(email);

		boolean updated = updateUserInfoService.update(form, loginUser.getUser().getName());
		if (updated) {
			flash.addFlashAttribute("editCompleted", "ユーザー情報の更新が完了しました");
		} else {
			flash.addFlashAttribute("editFailure", "登録失敗\n　ERROR: このユーザー情報は既に更新されました");
		}
		return "redirect:/user/edit?userId=" + form.getUserId();
	}

	/**
	 * ユーザー情報を削除する.
	 * 
	 * @param userId    ユーザーID
	 * @param loginUser ログイン情報
	 * @return ユーザー一覧画面
	 */
	@PostMapping("/user/delete")
	public String delete(Integer userId, RedirectAttributes flash, @AuthenticationPrincipal LoginUser loginUser) {
		deleteUserService.delete(userId, loginUser.getUser().getName());
		flash.addFlashAttribute("deleteCompleted", "ユーザーの削除が完了しました");
		return "redirect:/user?pageBack=true";
	}
}
