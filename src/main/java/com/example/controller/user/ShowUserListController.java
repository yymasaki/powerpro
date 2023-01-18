package com.example.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.example.TechnicalSkillExample;
import com.example.form.SearchUserForm;
import com.example.service.technique.GetTechnicalSkillListService;
import com.example.service.user.GetAllUsersService;
import com.example.service.user.GetUsersService;

@Controller
@RequestMapping("/user")
public class ShowUserListController {

	@Autowired
	private GetTechnicalSkillListService getTechnicalSkillListService;

	@Autowired
	private GetUsersService getUsersService;

	@Autowired
	private GetAllUsersService getAllUsersService;

	@Autowired
	private HttpSession session;

	/** 1ページに表示するユーザー数 */
	private static final int VIEW_SIZE = 20;

	@ModelAttribute
	public SearchUserForm setupForm() {
		return new SearchUserForm();
	}

	/**
	 * ユーザー一覧表示する.
	 * 
	 * @param form  検索条件
	 * @param page  ページ番号
	 * @param model リクエストスコープ
	 * @return ユーザー一覧画面
	 */
	@RequestMapping("")
	public String showUserList(SearchUserForm form, Integer page, boolean pageBack, Model model) {

		TechnicalSkillExample tecExample = new TechnicalSkillExample();
		tecExample.createCriteria().andStageEqualTo("0");
		tecExample.setOrderByClause("name");
		model.addAttribute("technicalSkills", getTechnicalSkillListService.getTechnicalSkillList(tecExample));

		if (Objects.nonNull(form.getSkills()) && "null".equals(form.getSkills()[0])) {
			form.setSkills(null);
		}
		boolean searching = form.existValues();

		List<User> allUsers;
		if (searching) {
			allUsers = getUsersService.get(form);
			session.setAttribute("userCount", "検索結果：" + allUsers.size() + "名");
		} else if (pageBack) {
			SearchUserForm searchValuesBefore = (SearchUserForm) session.getAttribute("searchValue");
			allUsers = getUsersService.get(searchValuesBefore);
			page = (Integer) session.getAttribute("page");
		} else {
			allUsers = getAllUsersService.getAllUsers();
			session.removeAttribute("userCount");
		}

		if (allUsers.isEmpty()) {
			model.addAttribute("nonUsers", "検索条件に一致するエンジニアが存在しません");
		}

		if (Objects.isNull(page)) {
			page = 1;
		}

		// ページング
		Page<User> users = createPaging(page, VIEW_SIZE, allUsers);
		model.addAttribute("users", users);
		List<Integer> pagingNumbers = createPagingNumbers(users);
		

		// オートコンプリート
		if (Objects.isNull(session.getAttribute("autocompleteUsers"))) {
			StringBuilder autocomplete = new StringBuilder();
			allUsers.stream().map(user -> "\"" + user.getName() + "\",").forEach(name -> autocomplete.append(name));
			session.setAttribute("autocompleteUsers", autocomplete);
		}

		// 現在のページ番号・検索条件を保存しておく
		if (!pageBack) {
			session.setAttribute("page", page);
			session.setAttribute("searchValue", form);
		}
		session.setAttribute("pagingNumbers", pagingNumbers);

		return "user/user-list";
	}

	/**
	 * ページングボタン用の数字リストを作成する.
	 * 
	 * @param questionList 質問一覧
	 * @return ページング番号リスト
	 */
	public List<Integer> createPagingNumbers(Page<User> userList) {
		int totalPages = userList.getTotalPages();
		List<Integer> pageNumbers = new ArrayList<>();
		pageNumbers.add(1);
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

	/**
	 * ページング処理.
	 * 
	 * @param page     ページ数
	 * @param size     1ページ当の表示数
	 * @param userList 全ユーザー情報
	 * @return ユーザー一覧（1ページ分）
	 */
	public Page<User> createPaging(int page, int size, List<User> userList) {
		page--;
		int indexOfTopData = page * size;

		List<User> subList;
		int toIndex = Math.min(indexOfTopData + size, userList.size());
		subList = userList.subList(indexOfTopData, toIndex);

		Page<User> userListOnThisPage = new PageImpl<User>(subList, PageRequest.of(page, size), userList.size());
		return userListOnThisPage;
	}

}
