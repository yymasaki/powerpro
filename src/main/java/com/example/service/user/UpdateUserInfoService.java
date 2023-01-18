package com.example.service.user;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.form.EditUserForm;
import com.example.mapper.UserMapper;

@Service
@Transactional
public class UpdateUserInfoService {

	@Autowired
	private UserMapper mapper;

	/**
	 * 入力情報に従ってユーザー情報を更新する.
	 * 
	 * @param form ユーザー入力情報
	 */
	public boolean update(EditUserForm form, String updater) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setName(form.getLastName() + "　" + form.getFirstName());
		user.setDepartmentId(Integer.parseInt(form.getDepartmentId()));
		String[] hiredYearAndMonth = form.getHiredOn().split("-");
		Integer hiredYear = Integer.parseInt(hiredYearAndMonth[0]);
		Integer hiredMonth = Integer.parseInt(hiredYearAndMonth[1]);
		user.setHiredOn(LocalDate.of(hiredYear, hiredMonth, 1));
		user.setUpdater(updater);
		UserExample example = new UserExample();
		example.createCriteria().andUserIdEqualTo(form.getUserId());
		int currentVersion = mapper.selectByPrimaryKey(form.getUserId()).getVersion();
		if(currentVersion!=Integer.parseInt(form.getVersion())) {
			return false;
		}
		mapper.updateByExampleSelective(user, example);
		return true;
	}

}
