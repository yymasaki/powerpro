package com.example.service.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.form.SearchUserForm;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class GetUsersService {

	@Autowired
	private UserMapper mapper;

	/**
	 * 検索条件に一致するユーザー情報を取得する.
	 * 
	 * @param form 検索条件
	 * @return ユーザー情報一覧
	 */
	public List<User> get(SearchUserForm form) {
		User user = new User();
		if (!Objects.isNull(form.getDepartmentId()) && !form.getDepartmentId().isEmpty()) {
			user.setDepartmentId(Integer.parseInt(form.getDepartmentId()));
		}

		if (!Objects.isNull(form.getName()) && !form.getName().isEmpty()) {
			String name = form.getName();
			name = name.replaceAll(" ", "　");
			// 前後の空白を削除
			name = name.replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "");
			user.setName(name);
		}

		if (!Objects.isNull(form.getHiredOn()) && !form.getHiredOn().isEmpty()) {
			String[] hiredYearAndMonth = form.getHiredOn().split("-");
			Integer hiredYear = Integer.parseInt(hiredYearAndMonth[0]);
			Integer hiredMonth = Integer.parseInt(hiredYearAndMonth[1]);
			user.setHiredOn(LocalDate.of(hiredYear, hiredMonth, 1));
		}

		List<Integer> skills = null;
		if (!Objects.isNull(form.getSkills())) {
			skills = new ArrayList<>();
			for(String skill : form.getSkills()) {
				skills.add(Integer.parseInt(skill));
			}
		}
		return mapper.selectUsersWithTechnicalSkills(user, skills);
	}

}
