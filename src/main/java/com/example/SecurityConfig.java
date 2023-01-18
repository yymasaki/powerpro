package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.service.common.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * bcryptアルゴリズムでハッシュ化する実装を返す.
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * パスワード有効期限チェック用のInterceptorクラスをbean定義する.
	 * 
	 * @return パスワード有効期限チェック用のInterceptorオブジェクト
	 * @throws Exception
	 */
	@Bean
	public HandlerInterceptor interceptor() throws Exception {
		return new PasswordExpirationCheckInterceptor();
	}

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private SuccessHandler successHandler;

	/**
	 * 認証ユーザを取得する「UserDetailsService」、パスワード照合時に使う「PasswordEncoder」の設定
	 */
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/**
	 * 追加部分
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login
				.loginPage("/login") // ログイン画面を表示するパス
				.loginProcessingUrl("/login-do") // ログイン可否判定するパス（HTMLの入力フォームでth:action()内に指定）
				.failureUrl("/login?error=true") // ログイン失敗時に遷移させるパス
				.successHandler(successHandler) // ログイン成功時に遷移させるパス
				.usernameParameter("email") // ログインユーザー名（ログイン画面のHTML上の<input name="**">とそろえる）
				.passwordParameter("password") // ログインパスワード（ログイン画面のHTML上の<input name="**">とそろえる）
				.permitAll()
		).logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウト処理をするパス
				.logoutSuccessUrl("/") // ログアウト成功時に遷移させるパス
				.deleteCookies("JSESSIONID").invalidateHttpSession(true)
		).authorizeHttpRequests(authz -> authz
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.mvcMatchers("/css/**", "/js/**", "/img/**", "/svgs/**", "/webfonts/**", "/favicon.ico").permitAll() // 静的リソースに対してセキュリティの設定を無効にする。
				.mvcMatchers("/login", "/pass", "/pass/**", "/register", "/register/**").permitAll() // ログイン前にアクセス可とするパス群
				.mvcMatchers("/user").hasAnyRole("SALES", "ADMIN") // 営業と管理者がログインしている場合のみアクセス可
				.mvcMatchers("/user/**", "/skill/item/**", "/request/item/**", "/info/**", "/skill/status/delete",
						"/skill/spec/delete", "/skill/template/edit**", "/skill/template/register**")
				.hasRole("ADMIN") // 管理者がログインしている場合のみアクセス可
				.mvcMatchers("/request/presentation", "/request/presentation/**")
				.hasAnyRole("ADMIN", "WEB", "CL", "ML", "SALES") // 管理者・営業・エンジニアがログインしていればアクセス可
				.mvcMatchers("/request", "/request/**", "/skill/status/edit/**", "/skill/spec/edit/do",
						"/skill/template")
				.hasAnyRole("ADMIN", "WEB", "CL", "ML") // 管理者・エンジニアがログインしている場合のみアクセス可
				.mvcMatchers("/skill/technique/edit/**", "/skill/template/select").hasAnyRole("WEB", "CL", "ML") // エンジニアがログインしている場合のみアクセス可
				.anyRequest().authenticated()); // 上記以外のパスは、ログイン以前のアクセス不可とする
		return http.build();
	}

}
