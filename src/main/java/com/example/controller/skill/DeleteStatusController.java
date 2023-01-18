package com.example.controller.skill;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.LoginUser;
import com.example.domain.Status;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetStatusService;

@Controller
@RequestMapping(value="/skill")
public class DeleteStatusController {
	
	@Autowired
	private GetStatusService getStatusService;
	
	@Autowired
	private DeleteStatusService deleteStatusService;
	
	@Autowired
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	/**
	 * ステータス情報の削除(stage=9)する.
	 * 
	 * @param loginUser ログイン情報
	 * @param userId ユーザーID
	 * @return ステータス詳細画面
	 */
	@PostMapping(value="/status/delete")
	public String deleteStatus(@AuthenticationPrincipal LoginUser loginUser, Integer userId, Integer version, RedirectAttributes redirectAttributes, String adminComment) {
		Status status = getStatusService.getStatusByStages(userId, Stage.ACTIVE.getKey(), null);
		//ユーザーIDの確認
		if(Objects.isNull(status)) {
			throw new IllegalArgumentException();
		}
		
		//バージョンチェック
		if(status.getVersion() != version) {
			redirectAttributes.addFlashAttribute("alertMessage", "削除失敗\\nERROR:この削除内容は既に更新されました");
			redirectAttributes.addAttribute("userId", userId);
			return "redirect:/skill/status";
		}

		deleteStatusService.deleteStatusByPrimaryKey(status.getStatusId());
		//メール送信を行う
		sendMailAboutStatusByAdminService.sendMailAboutStatusByAdmin(userId, Stage.DELETED.getKey(), adminComment, status.getStatusId(), "既存のステータス削除");
		redirectAttributes.addAttribute("userId", userId);
		redirectAttributes.addFlashAttribute("deleteCompleted", "対象のステータスを削除しました");
		return "redirect:/skill/status";
	}
}
