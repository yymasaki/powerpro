package com.example.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Information;
import com.example.domain.LoginUser;
import com.example.service.info.GetInformationListService;

@Controller
@RequestMapping("/")
public class ShowInfomationListController {

	@Autowired
	private GetInformationListService getInformationListService;

	/**
	 * 
	 * トップページを表示する
	 * 
	 * @param loginUser ログインユーザー
	 * @param model
	 * @return トップページ画面
	 */
	@RequestMapping("")
	public String showInfomationList(@AuthenticationPrincipal LoginUser loginUser, Model model, Integer offset) {

		List<Information> allInformationList = getInformationListService.getAllInformationList(loginUser.getUser().getUserId());
		int allInformation = allInformationList.size();

		// ページの合計
		int numOfPage = (allInformation / 5) + 1;
		if (allInformation != 0 && allInformation % 5 == 0) {
			numOfPage = (allInformation / 5);
		}

		// 閲覧ページ(手動でoffsetを5の倍数以外にするとページ数が変わるため、その場合はoffset=0にする
		int currentPage = 1;
		if (offset == null || offset <= 0 || offset % 5 != 0) {
			offset = 0;
		} else {
			currentPage = offset / 5 + 1;
		}

		// offsetを手動で変更された場合の対応
		if (currentPage > numOfPage) {
			currentPage = numOfPage;
		}

		List<Information> informationList = getInformationListService
				.getInformationList(loginUser.getUser().getUserId(), offset);
		model.addAttribute("informationListSize", informationList.size());

		// 6件づつ情報を取得するが、実際表示するのは5件
		// 6件取得できなかったら次のページはないため、ブラウザ上の「next」を非表示とする。
		if (informationList.size() == 6) {
			informationList.remove(5);
		} else if (informationList.size() == 0) {
			model.addAttribute("noInformation", "お知らせはありません。");
		}

		model.addAttribute("informationList", informationList);
		model.addAttribute("offset", offset);
		model.addAttribute("numOfPage", numOfPage);
		model.addAttribute("currentPage", currentPage);
		return "common/top";
	}

}
