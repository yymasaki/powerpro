package com.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.domain.LoginUser;

/**
 * パスワード有効期限確認用のInterceptor .
 */
public class PasswordExpirationCheckInterceptor implements HandlerInterceptor {

	/** パスワード有効期間 */
	private static final int VALIDITY_PERIOD = 180;


	/**
	 * password期限切れの場合に、Controller処理に入らず、password変更画面へリダイレクトさせる.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

//		Authentication authentication = (Authentication) request.getUserPrincipal();
//		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//		boolean passwordIsValid = ExpirationCheck(LocalDate.now(), loginUser.getUser().getUpdatedPasswordAt());
//		if (!passwordIsValid) {
//			response.sendRedirect(request.getContextPath() + "/pass-edit?validPass=true");
//			return false;
//		}
//		return true;

		// 追加部分
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		// 未ログイン状態だと「authentication.getPrincipal()」がString型を返しキャストエラーが生じるため、条件分岐
		if (authentication.getPrincipal() instanceof String) {
			response.sendRedirect(request.getContextPath() + "common/login"); // この行をコメントアウトするとlocalhost:8080を打つと何も画面に映らなくなる
			return false;
		}
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		boolean passwordIsValid = ExpirationCheck(LocalDate.now(), loginUser.getUser().getUpdatedPasswordAt());
		if (!passwordIsValid) {
			response.sendRedirect(request.getContextPath() + "/pass-edit?validPass=true");
			return false;
		}
		return true;
	}

	/**
	 * パスワードの有効期限確認.
	 * 
	 * @param today     今日
	 * @param updatedAt 最終更新日
	 * @return 有効：true, 無効：false
	 */
	public boolean ExpirationCheck(LocalDate today, LocalDate updatedAt) {
		long diffDays = ChronoUnit.DAYS.between(updatedAt, today);
		if (diffDays >= VALIDITY_PERIOD) {
			return false;
		}
		return true;
	}

}
