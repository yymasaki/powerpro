package com.example.controller.presentation;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.EditRequestComment;
import com.example.domain.LoginUser;
import com.example.domain.Presentation;
import com.example.domain.User;
import com.example.form.RequestPresentationForm;
import com.example.form.SearchRequestPresentationForm;
import com.example.service.presentation.RequestPresentationService;

@Controller
@RequestMapping("/request/presentation")
public class RequestPresentationController {

	@Autowired
	private RequestPresentationService requestPresentationService;

	@ModelAttribute
	public RequestPresentationForm setupRequestPresentationForm() {
		return new RequestPresentationForm();
	}

	@ModelAttribute
	public SearchRequestPresentationForm setupSearchRequestPresentationForm() {
		return new SearchRequestPresentationForm();
	}

	/**
	 * 応募状況の一覧画面を表示 & 検索
	 * 
	 * @return
	 */
	@GetMapping(value = "")
	public String requestPresentations(@AuthenticationPrincipal LoginUser loginUser, SearchRequestPresentationForm form,
			Model model) {
		List<Presentation> presentationList;

		Integer departmentId = loginUser.getUser().getDepartmentId();

		// 検索欄の空欄チェック
		boolean existValue;
		if (departmentId == 4 || departmentId == 5) {
			existValue = form.existValue();
		} else {
			existValue = form.existValueByEngineer();// エンジニアは応募状況のみでの検索のため
		}
		
		System.out.println(form);

		// 応募状況一覧の表示
		if (!existValue) {
			presentationList = requestPresentationService.requestPresentations();
		} else { // 検索結果の値を格納 & 検索
			Presentation searchPresentation = new Presentation();
			User user = new User();
			if (!Objects.isNull(form.getStage()) && !form.getStage().isEmpty()) {
				searchPresentation.setStage(Integer.parseInt(form.getStage()));
			}
			if (!Objects.isNull(form.getPresentationDate()) && !form.getPresentationDate().isEmpty()) {
				searchPresentation.setPresentationDate(LocalDate.parse(form.getPresentationDate()));
			}
			if (!Objects.isNull(form.getDepartmentId()) && !form.getDepartmentId().isEmpty()) {
				user.setDepartmentId(Integer.parseInt(form.getDepartmentId()));
			}
			if (!Objects.isNull(form.getName()) && !form.getName().isEmpty()) {
				user.setName(form.getName());
			}
			searchPresentation.setUser(user);
			presentationList = requestPresentationService.searchRequestPresentations(searchPresentation);
		}

		if (presentationList == null || presentationList.size() == 0) {
			model.addAttribute("message", "検索結果がありませんでした。");
		} else {
			model.addAttribute("presentationList", presentationList);
		}
		// System.out.println("カレントステージがはいっているか ： "+presentationList.get(1));
		return "presentation/request-presentation";
	}

	@GetMapping(value = "/top")
	public String presentationTop() {
		return "forward:/presentation";
	}

	/** 日付が確定していない発表を、日付を確定し「承認」にする */
	@ResponseBody
	@GetMapping(value = "/update-presentation")
	public void updateRequestPresentation(@AuthenticationPrincipal LoginUser loginUser, Integer presentationId,
			Integer stage, String presentationDate) {
		requestPresentationService.updateStageAndPresentationDate(presentationId, stage,
				loginUser.getUser().getUserId(), presentationDate);
	}

	/**
	 * 申請中の発表会の詳細画面を表示
	 * 
	 * @param presentationId
	 * @param model
	 * @return request-presentation-detail.html
	 */
	@RequestMapping("/detail")
	public String showRequestPresentation(Integer presentationId, Model model) {
		Presentation requestedPresentation = requestPresentationService.showRequestPresentation(presentationId);

		model.addAttribute("requestedPresentation", requestedPresentation);
		return "/presentation/request-presentation-detail";
	}

	/**
	 * 申請中の発表会に対して管理者が反応した際の処理。
	 * 
	 * @param loginUser
	 * @param form
	 * @param flash
	 * @return 発表会トップ画面
	 */
	@PostMapping("/process")
	public String reactedRequestPresentationByAdmin(@AuthenticationPrincipal LoginUser loginUser,
			RequestPresentationForm form, Model model, RedirectAttributes flash) {

		// FIXME:発表メンバーにメールを送るためだけ（メアドを取得するためだけ）にshowRequestPresentationメソッドを実行している。画面からメンバーそれぞれのメアドを取得できるのがベストだが、、、
		Presentation requestedPresentation = requestPresentationService
				.showRequestPresentation(form.getPresentationId());
		// 承認ボタン押下時の処理
		if (form.getPresentationDate() != null && form.getStage() == Stage.ACTIVE.getKey()) {
			LocalDate presentationDate = LocalDate.parse(form.getPresentationDate());
			requestedPresentation.setPresentationDate(presentationDate);
		}
		requestedPresentation.setStage(form.getStage());
		requestedPresentation.setUpdaterUserId(loginUser.getUser().getUserId());
		EditRequestComment editRequestComment = new EditRequestComment(form.getAdminComment(),
				loginUser.getUser().getUserId(), null, form.getPresentationId());
		requestPresentationService.reactedRequestPresentationByAdmin(requestedPresentation, editRequestComment);
		// トップ画面へstageを送りアラートに表示させる
		flash.addFlashAttribute("message", String.valueOf(form.getStage()));
		return "redirect:/presentation";
	}

}
