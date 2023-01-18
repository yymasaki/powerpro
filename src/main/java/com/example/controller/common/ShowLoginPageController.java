package com.example.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShowLoginPageController {

	/**
	 * ログインページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param error ログインエラーフラグ
	 * @return ログイン画面
	 */
	@RequestMapping("/login")
	public String showLoginPage(Model model, @RequestParam(required = false) String error, HttpServletRequest req) {
		if (error != null) {
			System.err.println("login failed");
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		
		//セッションが存在しない場合はnullを返す。
	    HttpSession session = req.getSession(false);
	    if (session != null) {
	    	//SPRING_SECURITY_SAVED_REQUESTはデフォルトのkey名
	        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
	        if (savedRequest != null && !(savedRequest.getRedirectUrl().contains("html"))) {
	            // 認証成功後のリダイレクト先
	            String redirectUrl = savedRequest.getRedirectUrl();
	            //セッションスコープにurlを格納.
				session.setAttribute("url", redirectUrl);
	        }
	    }
		return "common/login";
	}
}
