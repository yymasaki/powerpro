package com.example.controller.request;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.HadBasicSkill;
import com.example.domain.HadEngineerSkill;
import com.example.domain.LoginUser;
import com.example.domain.Personality;
import com.example.domain.Status;
import com.example.form.StatusForm;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateHadBasicSkillService;
import com.example.service.status.UpdateHadEngineerSkillService;
import com.example.service.status.UpdateHadPersonalityService;
import com.example.service.status.UpdateStatusService;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;

@Controller
@RequestMapping(value="/request/status/edit")
public class EditRequestStatusController {


	@Autowired
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@Autowired
	private GetStatusService getStatusService;
	
	@Autowired
	private GetPersonalityService getPersonalityService;
	
	@Autowired
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@Autowired
	private UpdateStatusService updateStatusService;
	
	@Autowired
	private UpdateHadBasicSkillService updateHadBasicSkillService;
	
	@Autowired
	private UpdateHadEngineerSkillService updateHadEngineerSkillService;
	
	@Autowired
	private UpdateHadPersonalityService updateHadPersonalityService; 
	
	@Autowired
	private DeleteStatusService deleteStatusService;
	
	@ModelAttribute
	public StatusForm setUpForm(){
		return new StatusForm();
	}
	
	/**
	 * ステータス申請修正ページの表示を行う.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログイン情報
	 * @return ステータス申請修正ぺ－ジの表示
	 */
	@RequestMapping(value="")
	public String showRequestStatus(Model model, @AuthenticationPrincipal LoginUser loginUser, StatusForm statusForm, RedirectAttributes redirectAttributes) {
		String role = loginUser.getAuthorities().toString();
		Integer statusId = statusForm.getStatusId();		
		Integer userId = statusForm.getUserId();
		//権限の確認とユーザーIDの確認
		if(!(role.equals("[ROLE_ADMIN]"))
				&& !(loginUser.getUser().getUserId().equals(userId))) {
			throw new IllegalArgumentException();
		}
		Status status = getStatusAndSkillsService.getStatusAndSkillsByPrimaryKey(statusId);
		//stage=0or1の場合は申請修正画面で編集を行う.
		if(!(status.getStage() == Stage.REQUESTING.getKey()) && !(status.getStage() == Stage.SENT_BACK.getKey())) {
			throw new IllegalArgumentException();
		}
		
		//編集画面の表示までに更新が行われたかの確認
		if(status.getVersion() != statusForm.getVersion()) {
			redirectAttributes.addAttribute("statusId", statusId);
			redirectAttributes.addFlashAttribute("alertMessage", "承認失敗\nERROR:この申請内容は既に承認されました");
			return "redirect:/request/status";
		}
		model.addAttribute("statusData", status);
		List<Personality> personalityList = getPersonalityService.getPersonality();
		model.addAttribute("personalityList", personalityList);
		model.addAttribute("currentStage", status.getStageString());
		return "request/request-edit-status";
	}
	
	/**
	 * ステータス申請修正を実施する.
	 * 
	 * @param model リクエストスコープ
	 * @param statusForm1 ステータスフォーム
	 * @param loginUser ログイン情報
	 * @param typeOfButton ボタンの種類(apply or approval)
	 * @param userId ユーザーID
	 * @return ステータスリクエスト申請画面
	 */
	@PostMapping(value="/do")
	public String reviseRequestStatus(@Validated StatusForm statusForm, BindingResult result,@AuthenticationPrincipal LoginUser loginUser,Model model, RedirectAttributes redirectAttributes) {
		Status status = new Status();
		String role = loginUser.getAuthorities().toString();
		Integer userId = statusForm.getUserId();
		Integer stage = statusForm.getStage();
		Integer statusId = statusForm.getStatusId();
		List<Integer> personalityIdList = statusForm.getPersonalityIdList();
		List<HadBasicSkill> hadBasicSkillList = statusForm.getHadBasicSkillList();
		List<HadEngineerSkill> hadEngineerSkillList = statusForm.getHadEngineerSkillList();
		//権限によるエラー
		if(!role.equals("[ROLE_ADMIN]")
				&& !loginUser.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException();
		}
		//ステージが0以外かつ2以外の時エラー
		if(!(stage == Stage.ACTIVE.getKey()) && !(stage == Stage.REQUESTING.getKey())) {
			throw new IllegalArgumentException();
		}
		
		//変更前のステータス
		status = getStatusService.getStatusByPrimaryKey(statusId);
		//編集画面の表示までに更新が行われたかの確認
		if(status.getVersion() != statusForm.getVersion()) {
			redirectAttributes.addAttribute("statusId", statusId);
			redirectAttributes.addFlashAttribute("alertMessage", "編集失敗\nERROR:この申請編集内容は既に更新されました");
			return "redirect:/request/status";
		}
		//変更後の新しいステータスを登録する.
		BeanUtils.copyProperties(statusForm, status);
		
		//validationチェック
		result = validationCheck(statusForm, result, role);
		
		if(result.hasErrors()) {
			return showRequestStatus(model, loginUser, statusForm, redirectAttributes);
		}
		
		status.setUpdater(loginUser.getUser().getName());

		//ステータス情報を登録する(stageによりsetStageを変える)
		if(stage == Stage.ACTIVE.getKey()) {
			deleteStatusService.deleteStatusByUserId(userId);
		}
		updateStatusService.updateStatus(status);
		
		//1行上で登録したステータスを取得する
		status = getStatusService.getStatusByStages(userId, stage, null);
		//基本,エンジニア,性格スキルの登録を行う.
		updateHadBasicSkillService.updateHadBasicSkill(hadBasicSkillList, statusId);
		updateHadEngineerSkillService.updateHadEngineerSkill(hadEngineerSkillList, statusId);
		updateHadPersonalityService.upsertHadPersonality(personalityIdList, statusId);
		
		if(role.equals("[ROLE_ADMIN]")) {
			sendMailAboutStatusByAdminService.sendMailAboutStatusByAdmin(userId, stage, status.getAdminComment(), status.getStatusId(), "申請" + Stage.of(stage).getMessageForRequest());
			redirectAttributes.addFlashAttribute("message", "ステータス申請を" + Stage.of(stage).getMessageForRequest() + "しました");
			return "redirect:/request";
		}
		redirectAttributes.addFlashAttribute("editCompleted", "ステータス申請を" + Stage.of(stage).getMessageForRequest() + "しました");			
		redirectAttributes.addAttribute("statusId", statusId);
		return "redirect:/request/status";
	}
	
	/**
	 * エラーが存在するかどうかの確認
	 * 
	 * @param statusForm ステータスフォーム
	 * @param result エラー
	 * @param role 権限
	 * @return result
	 */
	public BindingResult validationCheck(StatusForm statusForm, BindingResult result, String role) {
		//所有している性格リストが0だとエラー表示させる.
		if(CollectionUtils.isEmpty(statusForm.getPersonalityIdList())) {
			result.rejectValue("personalityIdList", null, "1つ以上性格を選択して下さい");
		}
		
		//所有エンジニアスキルリストのスコアがnullだったらエラー
		for (HadEngineerSkill hadEngineerSkill : statusForm.getHadEngineerSkillList()) {
			if (Objects.isNull(hadEngineerSkill.getScore()) || hadEngineerSkill.getScore() < 0 || hadEngineerSkill.getScore() > 100) {
				result.rejectValue("hadEngineerSkillList", null, "0から100の数字を入力してください");
				break;
			}
		}
		
		//所有基本スキルリストのスコアがnullだったらエラー
		for(HadBasicSkill hadBasicSkill : statusForm.getHadBasicSkillList()) {
			if (Objects.isNull(hadBasicSkill.getScore()) || Integer.parseInt(hadBasicSkill.getScore()) <= 0 || Integer.parseInt(hadBasicSkill.getScore()) > 5) {
				result.rejectValue("hadBasicSkillList", null, "1から5の数字を入力してください");
				break;
			}
		}
		
		//ログイン者が管理者の場合
		if(role.equals("[ROLE_ADMIN]")) {
			//管理者コメントの空白を消す
			statusForm.setAdminComment(StringUtils.trimAllWhitespace(statusForm.getAdminComment()));
			if(statusForm.getAdminComment().isEmpty()) {
				result.rejectValue("adminComment", null, "1字以上200字以内で書いて下さい");
			}
		}else {
			//ユーザーコメントの空白を消す
			statusForm.setUserComment(StringUtils.trimAllWhitespace(statusForm.getUserComment()));
			if(statusForm.getUserComment().isEmpty()) {
				result.rejectValue("userComment", null, "1字以上200字以内で書いて下さい");
			}
		}
		return result;
	}
}
