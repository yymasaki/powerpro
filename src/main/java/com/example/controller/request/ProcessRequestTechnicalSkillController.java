package com.example.controller.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.TechnicalSkill;
import com.example.form.ApprovalAllTechnicalSkillForm;
import com.example.form.ApprovalTechnicalSkillForm;
import com.example.form.SearchRequestForm;
import com.example.service.technique.GetTechnicalSkillListByTechnicalSkillIdListAndVersionListService;
import com.example.service.technique.GetTechnicalSkillService;
import com.example.service.technique.UpdateRequestTechnicalSkillService;
import com.example.service.technique.UpdateTechnicalSkillListStageService;

@Controller
@RequestMapping("/request/item")
public class ProcessRequestTechnicalSkillController {
	
	@Autowired
	private GetTechnicalSkillService getTechnicalSkillService;
	
	@Autowired
	private GetTechnicalSkillListByTechnicalSkillIdListAndVersionListService getTechnicalSkillListByListService;

	@Autowired
	private UpdateRequestTechnicalSkillService updateService;
	
	@Autowired
	private UpdateTechnicalSkillListStageService updateStageService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public ApprovalAllTechnicalSkillForm setUpForm() {
		return new ApprovalAllTechnicalSkillForm();
	}
	
	/**
	 * テクニカルスキルの承認処理を行う.
	 * 
	 * @param form
	 * @return 処理毎のパス
	 */
	@PostMapping("/approval")
	public String processRequestTechnicalSkill(ApprovalTechnicalSkillForm form,
			@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {

		TechnicalSkill technicalSkill = getTechnicalSkillService.getTechnicalSkill(form.getTechnicalSkillId());
		if (form.getVersion() != technicalSkill.getVersion()) {
			if (technicalSkill.getStage().equals( Stage.ACTIVE.getKey())) {
				redirectAttributes.addFlashAttribute("failMessage", "承認失敗\nERROR:この申請内容は既に承認されました");
			}
			return "redirect:/request";
		}
		
		String updater=loginUser.getUser().getName();
		updateTechnicalSkill(Stage.ACTIVE.getKey(), updater, technicalSkill);
		
		//承認後の検索結果を保持するために取得
		SearchRequestForm searchForm=(SearchRequestForm) session.getAttribute("searchRequestForm");
		redirectAttributes.addFlashAttribute("searchRequestForm", searchForm);
		
		//テスト用
		redirectAttributes.addFlashAttribute("approvalTechnicalSkillForm", form);
		
		redirectAttributes.addFlashAttribute("message", "テクニカルスキル申請を"+Stage.ACTIVE.getMessageForRequest()+"しました");
		return "redirect:/request";
	}

	/**
	 * テクニカルスキル情報を更新する.
	 * 
	 * @param adminComment   管理者コメント
	 * @param stage          ステージ
	 * @param loginUser      ログインユーザ情報
	 * @param technicalSkill テクニカルスキル情報
	 */
	public int updateTechnicalSkill(Integer stage, String updater,
			TechnicalSkill technicalSkill) {
		technicalSkill.setStage(stage);
		technicalSkill.setUpdater(updater);
		LocalDateTime localDateTime = LocalDateTime.now();
		technicalSkill.setUpdatedAt(localDateTime);
		technicalSkill.setVersion(technicalSkill.getVersion() + 1);

		return updateService.updateRequestTechnicalSkill(technicalSkill);
	}

	/**
	 * テクニカルスキルを一括承認する.
	 * 
	 * @param form
	 * @param model
	 * @return 申請トップ画面
	 */
	@PostMapping("/approval-all")
	public String processAllRequestTechnicalSkill(ApprovalAllTechnicalSkillForm form,RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal LoginUser loginUser) {

		//表示中のテクニカルスキルID、バージョンと一致するリストを取得
		List<TechnicalSkill> technicalSkillRequestList=
				getTechnicalSkillListByListService.getTechnicalSkillListByTechnicalSkillIdListAndVersionList(form.getTechnicalSkillIdList(),form.getVersionList());
		//別の管理者が既に承認処理していた場合
		if(technicalSkillRequestList.size()==0) {
				redirectAttributes.addFlashAttribute("approvalAllTechnicalSkillForm",form);
				redirectAttributes.addFlashAttribute("failMessage", "承認失敗\nERROR:この申請内容は既に承認されました");
				return "redirect:/request";	
		}
			
		String updater = loginUser.getUser().getName();
		LocalDateTime nowTime = LocalDateTime.now();	
		updateStageService.updateTechnicalSkillListStage(technicalSkillRequestList, Stage.ACTIVE.getKey().toString(), updater, nowTime);
		
		//承認後の検索結果を保持するために取得
		SearchRequestForm searchForm=(SearchRequestForm) session.getAttribute("searchRequestForm");
		redirectAttributes.addFlashAttribute("searchRequestForm", searchForm);
		
		//テストで値を検証するためパラメータをフラッシュスコープに格納する.
		redirectAttributes.addFlashAttribute("approvalAllTechnicalSkillForm",form);
		redirectAttributes.addFlashAttribute("message", "テクニカルスキル申請を一括"+Stage.ACTIVE.getMessageForRequest()+"しました");
		return "redirect:/request";

	}

}
