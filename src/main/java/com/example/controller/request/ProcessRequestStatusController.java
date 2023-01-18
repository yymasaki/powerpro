package com.example.controller.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.form.StatusForm;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateStatusService;

@Controller
@RequestMapping(value="/request/status")
public class ProcessRequestStatusController {
	
	@Autowired
	private UpdateStatusService updateStatusService;
	
	@Autowired
	private GetStatusService getStatusService;
	
	@Autowired
	private DeleteStatusService deleteStatusService;
	
	@Autowired
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@ModelAttribute
	public StatusForm setUpForm() {
		return new StatusForm();
	}
	
	/**
	 * ステータスの処理を行う.
	 * 
	 * @param loginUser ログイン情報
	 * @param userId ユーザーID
	 * @param typeOfButton ボタンの種類
	 * @return リダイレクトでステータス申請詳細画面
	 */
	@PostMapping(value="/process")
	public String processRequestStatus(StatusForm statusForm, BindingResult result, RedirectAttributes redirectAttributes ,@AuthenticationPrincipal LoginUser loginUser, Model model) {
		String role = loginUser.getAuthorities().toString();
		Integer userId = statusForm.getUserId();
		Integer stage = statusForm.getStage();
		Integer statusId = statusForm.getStatusId();
		//権限の確認とユーザーIDの確認
		if(!(role.equals("[ROLE_ADMIN]"))
				&& !(loginUser.getUser().getUserId().equals(userId))) {
			throw new IllegalArgumentException();
		}
		//ステージが1または2の時エラー
		if(stage == Stage.TEMPORARY.getKey() || stage == Stage.REQUESTING.getKey()) {
			throw new IllegalArgumentException();
		}
		if(stage == Stage.SENT_BACK.getKey()) {
			//管理者コメントの空白を消す
			statusForm.setAdminComment(statusForm.getAdminComment().strip());
			if(statusForm.getAdminComment().isEmpty()) {
				result.rejectValue("adminComment", null, "1字以上200字以内で書いて下さい");
			}
		}
		
		if(result.hasErrors()) {
			return "forward:/request/status";
		}
		
		Status status = getStatusService.getStatusByPrimaryKey(statusId);		
		//編集画面の表示までに更新が行われたかの確認
		if(status.getVersion() != statusForm.getVersion()) {
			redirectAttributes.addAttribute("statusId", statusId);
			redirectAttributes.addFlashAttribute("alertMessage", "処理失敗\nERROR:この申請内容は既に処理されました");
			return "redirect:/request/status";
		}
		//userIdからステータスの検索を行う
		status = getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey());
		//ステージ9の場合ステータス削除
		if(stage == Stage.DELETED.getKey()) {
			deleteStatusService.deleteStatusByPrimaryKey(statusId);
		}else{
			if(stage == Stage.ACTIVE.getKey()) {
				deleteStatusService.deleteStatusByUserId(userId);
			}
			//ステータス情報を変更する
			status.setAdminComment(statusForm.getAdminComment()); 
			status.setStage(stage);
			status.setUpdater(loginUser.getUser().getName());
			updateStatusService.updateStatus(status);		
			//メール送信
			sendMailAboutStatusByAdminService.sendMailAboutStatusByAdmin(userId, stage, status.getAdminComment(), statusId, "申請"+Stage.of(stage).getMessageForRequest());			
		}
		
		redirectAttributes.addFlashAttribute("message", "ステータス申請を" + Stage.of(stage).getMessageForRequest() + "しました");
		return "redirect:/request";
	}
}
