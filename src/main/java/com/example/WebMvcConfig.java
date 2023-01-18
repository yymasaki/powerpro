package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring-MVC設定
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
// 参考：	https://qiita.com/kyabetsuda/items/78a61bfff859fbc9c63f

	@Autowired
	private HandlerInterceptor passwordExpirationCheckInterceptor;

	/**
	 * InterceptorをSpringに認識させ、動作対象から除外するpathを設定する.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> patterns = new ArrayList<>();
		// ログイン前に要アクセスなpathはInterceptor動作対象外
		patterns.add("/css/**");
		patterns.add("/js/**");
		patterns.add("/img/**");
		patterns.add("/svgs/**");
		patterns.add("/webfonts/**");
		patterns.add("/login");
		patterns.add("/pass");
		patterns.add("/pass/**");
		patterns.add("/register");
		patterns.add("/register/**");
		// パスワード変更画面のpathはInterceptor動作対象外
		patterns.add("/pass-edit");
		patterns.add("/pass-edit/**");
		registry.addInterceptor(passwordExpirationCheckInterceptor).excludePathPatterns(patterns);
	}
}
