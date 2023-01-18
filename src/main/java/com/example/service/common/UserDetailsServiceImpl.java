package com.example.service.common;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = null;
		try {
			user = userMapper.selectByEmailAndStage(email, "0").get(0);
		} catch (Exception e) {
			throw new UsernameNotFoundException("not found : " + email);
		}

		int departmentId = user.getDepartmentId();
		String role = "";
		if (departmentId == 1) {
			role = "ROLE_WEB";
		} else if (departmentId == 2) {
			role = "ROLE_CL";
		} else if (departmentId == 3) {
			role = "ROLE_ML";
		} else if (departmentId == 4) {
			role = "ROLE_SALES";
		} else if (departmentId == 5) {
			role = "ROLE_ADMIN";
		}
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority(role)); // 権限付与
		System.out.println("login:" + user.getName() + "[" + role + "]");

		return new LoginUser(user, authorityList);
	}
}
