package com.example.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.domain.TechnicalSkill;
import com.example.domain.User;
import com.example.form.SearchRequestForm;
import com.example.service.request.GetCountForRequestService;
import com.example.service.request.GetSpecsheetForRequestService;
import com.example.service.request.GetStatusForRequestService;
import com.example.service.request.GetTechnicalSkillForRequestService;
import com.example.service.user.GetAllUsersService;

@Controller
@RequestMapping("/request")
public class SearchRequestController {

	@Autowired
	private GetStatusForRequestService getStatusRequestService;

	@Autowired
	private GetSpecsheetForRequestService getAppSpecsheetRequestService;

	@Autowired
	private GetTechnicalSkillForRequestService getNewTechnicalSkillRequestService;

	@Autowired
	private GetCountForRequestService getUnapprovedRequestCountService;

	@Autowired
	private GetAllUsersService getAllUsersService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public SearchRequestForm setUpSearchRequestForm() {
		return new SearchRequestForm();
	}

	/**
	 * 申請トップ画面を表示する.
	 * 
	 * @param loginUser ログインユーザー
	 * @param form 申請検索フォーム
	 * @param model　リクエストスコープ
	 * @return 申請トップ画面
	 */
	@RequestMapping("")
	public String searchRequest(@AuthenticationPrincipal LoginUser loginUser, SearchRequestForm form, Model model,boolean pageBack) {
		SearchRequestForm searchedForm=(SearchRequestForm)session.getAttribute("searchRequestForm");
		if(pageBack) {
			form=searchedForm;
		}
		User user = loginUser.getUser();
		form.setDepartmentId(user.getDepartmentId());
		form.setUserId(user.getUserId());
		// formの項目が不足している時の処理
		if (Objects.isNull(form.getContent())) {
			form.setContent(1);
		}
		if (Objects.isNull(form.getApplicant())) {
			form.setApplicant("");
		}
		if (Objects.isNull(form.getStage())) {
			form.setStage(Stage.REQUESTING.getKey());
		}
		if (Objects.isNull(form.getPageNumber())) {
			form.setPageNumber(1);
		}

		Integer searchCount = getUnapprovedRequestCountService.getCountForRequest(form);
		if (form.getContent() == 1) {
			List<Status> statusRequestList = new ArrayList<>();
			statusRequestList = getStatusRequestService.getStatusForRequest(form, calcStartNumber(form.getPageNumber()));
			if (statusRequestList.size() == 0) {
				// 該当する検索結果がない場合、メッセージと共に全件表示する
				form.setApplicant("");
				statusRequestList = getStatusRequestService.getStatusForRequest(form,
						calcStartNumber(form.getPageNumber()));
				searchCount = getUnapprovedRequestCountService.getCountForRequest(form);
				model.addAttribute("noDataMessage", "該当するデータがありません");
			}
			model.addAttribute("statusRequestList", statusRequestList);
		} else if (form.getContent() == 2) {
			List<AppSpecsheet> appSpecsheetRequestList = new ArrayList<>();
			appSpecsheetRequestList = getAppSpecsheetRequestService.getSpecsheetForRequest(form,
					calcStartNumber(form.getPageNumber()));
			if (appSpecsheetRequestList.size() == 0) {
				form.setApplicant("");
				appSpecsheetRequestList = getAppSpecsheetRequestService.getSpecsheetForRequest(form,
						calcStartNumber(form.getPageNumber()));
				searchCount = getUnapprovedRequestCountService.getCountForRequest(form);
				model.addAttribute("noDataMessage", "該当するデータがありません");
			}
			model.addAttribute("appSpecsheetRequestList", appSpecsheetRequestList);
		} else if (form.getContent() == 3) {
			List<TechnicalSkill> technicalSkillRequestList = new ArrayList<>();
			technicalSkillRequestList = getNewTechnicalSkillRequestService.getTechnicalSkillForRequest(form,
					calcStartNumber(form.getPageNumber()));
			if (technicalSkillRequestList.size() == 0) {
				form.setApplicant("");
				technicalSkillRequestList = getNewTechnicalSkillRequestService.getTechnicalSkillForRequest(form,
						calcStartNumber(form.getPageNumber()));
				searchCount = getUnapprovedRequestCountService.getCountForRequest(form);
				model.addAttribute("noDataMessage", "該当するデータがありません");
			}
			model.addAttribute("technicalSkillRequestList", technicalSkillRequestList);
		}
		model.addAttribute("pageNumber", form.getPageNumber());
		model.addAttribute("maxPageNumber", calcMaxPageNumber(searchCount));
		model.addAttribute("searchCount", searchCount);
		model.addAttribute("userListForAutocomplete", getUserListForAutocomplete());
		session.setAttribute("searchRequestForm", form);
		return "request/request";
	}

	/**
	 * 現在のページ数からデータの開始番号を計算する.
	 * 
	 * @param pageNumber ページ数
	 * @return データの開始番号
	 */
	public Integer calcStartNumber(Integer pageNumber) {
		Integer startNumber = (pageNumber - 1) * 20;
		return startNumber;
	}

	/**
	 * 検索結果数から、最大ページ数を計算する.
	 * 
	 * @param searchCount 検索結果数
	 * @return 最大ページ数
	 */
	public Integer calcMaxPageNumber(Integer searchCount) {
		if (searchCount == 0) {
			return 1;
		} else if (searchCount % 20 == 0) {
			return searchCount / 20;
		} else {
			return (searchCount / 20) + 1;
		}
	}

	/**
	 * オートコンプリート用にJavaScriptの配列の中身を文字列で作る.
	 * 
	 * @return オートコンプリート用JavaScriptの配列の文字列
	 */
	public StringBuilder getUserListForAutocomplete() {
		StringBuilder userListForAutocomplete = new StringBuilder();
		List<User> userList = getAllUsersService.getAllUsers();
		for (int i = 0; i < userList.size(); i++) {
			if (i != 0) {
				userListForAutocomplete.append(",");
			}
			User user = userList.get(i);
			userListForAutocomplete.append("\"");
			userListForAutocomplete.append(user.getName());
			userListForAutocomplete.append("\"");
		}
		return userListForAutocomplete;
	}
}