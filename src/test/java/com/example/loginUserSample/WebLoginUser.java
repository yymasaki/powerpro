package com.example.loginUserSample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.domain.User;

@Component
public class WebLoginUser {
	
	/**
	 * 権限リストを取得する.
	 * @return　権限リスト
	 */
	public Collection<GrantedAuthority> getAuthorityList(){
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_WEB"));
		return authorityList;
	}
	
	/**
	 * ユーザ情報を取得する.
	 * @return ユーザ
	 */ 
	public User getUser() {
		User user=new User();
		user.setUserId(1);
		user.setName("Webエンジニア次郎");
		user.setEmail("web.ziro");
		user.setPassword("$2a$10$/Qwwh8ycD4QXgOqbgQNw3OYXni5XTWx.30I9bC6NSk7xQUT.OmhOm");
		user.setSelectedTemplateId(1);
		user.setUpdatedPasswordAt(LocalDate.now());
		user.setDepartmentId(1);
		return user;
	}
}
