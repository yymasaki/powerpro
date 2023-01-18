package com.example;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * ログイン成功時に進むURL.
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private HttpSession session;
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
		//session内に入っているurlを取得
		String url = (String) session.getAttribute("url");

		if (Objects.isNull(url) || url.equals("http://localhost:8080/common/login")) {
			res.sendRedirect("/");
		}else {
			res.sendRedirect(url);
		}
    }

}
