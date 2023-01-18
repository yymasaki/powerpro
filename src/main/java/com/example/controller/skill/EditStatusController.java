package com.example.controller.skill;

import java.time.LocalDateTime;
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
import com.example.domain.Status;
import com.example.domain.User;
import com.example.form.StatusForm;
import com.example.service.status.AddHadBasicSkillService;
import com.example.service.status.AddHadEngineerSkillService;
import com.example.service.status.AddHadPersonalityService;
import com.example.service.status.AddStatusService;
import com.example.service.status.DeleteStatusService;
import com.example.service.status.GetBasicSkillListService;
import com.example.service.status.GetEngineerSkillListService;
import com.example.service.status.GetPersonalityService;
import com.example.service.status.GetStatusAndSkillsService;
import com.example.service.status.GetStatusService;
import com.example.service.status.SendMailAboutStatusByAdminService;
import com.example.service.status.UpdateHadBasicSkillService;
import com.example.service.status.UpdateHadEngineerSkillService;
import com.example.service.status.UpdateHadPersonalityService;
import com.example.service.status.UpdateStatusService;
import com.example.service.user.GetSpecificUserService;


@Controller
@RequestMapping(value="/skill/status/edit")
public class EditStatusController {
	
	@Autowired
	private GetStatusAndSkillsService getStatusAndSkillsService;
	
	@Autowired
	private GetStatusService getStatusService;
	
	@Autowired
	private GetBasicSkillListService getBasicSkillService;
	
	@Autowired
	private GetEngineerSkillListService getEngineerSkillService;
	
	@Autowired
	private GetPersonalityService getPersonalityService;
	
	@Autowired
	private AddStatusService addStatusService;
	
	@Autowired
	private AddHadBasicSkillService addHadBasicSkillService;
	
	@Autowired
	private AddHadEngineerSkillService addHadEngineerSkillService;

	@Autowired
	private AddHadPersonalityService addHadPersonalityService;
	
	@Autowired
	private GetSpecificUserService getSpecificUserService;
	
	@Autowired
	private UpdateStatusService updateStatusService;
	
	@Autowired
	private UpdateHadBasicSkillService updateHadBasicSkillService;
	
	@Autowired
	private UpdateHadEngineerSkillService updateHadEngineerSkillService;
	
	@Autowired
	private UpdateHadPersonalityService updateHadPersonalityService;
	
	@Autowired
	private SendMailAboutStatusByAdminService sendMailAboutStatusByAdminService;
	
	@Autowired
	private DeleteStatusService deleteStatusService;
	
	@ModelAttribute
	public StatusForm setUpForm(){
		return new StatusForm();
	}
	
	/**
	 * ステータス編集画面を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return スキル編集画面
	 */
	@RequestMapping(value="")
	public String showEditStatus(Model model, @AuthenticationPrincipal LoginUser loginUser, StatusForm statusForm, RedirectAttributes redirectAttributes) {
		//表示用ステータスを格納する
		Status status = new Status();
		//権限の取得
		String role = loginUser.getAuthorities().toString();
		//ユーザーIDの取得
		Integer userId = statusForm.getUserId();
		User user = new User();
		//管理者以外の場合はuserIdにログインしているユーザーのuserIdを格納する
		if(!role.equals("[ROLE_ADMIN]")) {
			user = loginUser.getUser();
			userId = user.getUserId();
		}else {
			user = getSpecificUserService.get(userId);
		}
		//申請中または否承認のデータが存在する場合エラー(申請修正画面で編集を行うため)
		if(Objects.nonNull(getStatusService.getStatusByStages(userId, Stage.REQUESTING.getKey(), Stage.SENT_BACK.getKey()))) {
			throw new IllegalArgumentException();
		}
		
		//一時保存データを取得する
		Status tempoStatus = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);
		if(Objects.nonNull(tempoStatus)) {
			//model.addAttribute("tempoStatus", tempoStatus);
			//最新の更新日の取得
			LocalDateTime oneDaylater = tempoStatus.getUpdatedAt();
			//最新更新日の1日後を取得
			oneDaylater = oneDaylater.plusDays(1);
			//一時保存されているステータスが24時間以上たっていたら削除する
			if(oneDaylater.compareTo(LocalDateTime.now()) < 0 ) {
				deleteStatusService.deleteStatusByPrimaryKey(tempoStatus.getStatusId());
				model.addAttribute("alertMessage", "一時保存したデータが1日経過したため削除されました");
			}
		}
		
		//承認済みまたは一時保存中の最新のステータスを取得
		status = getStatusAndSkillsService.getStatusAndSkillsWithoutPersonalitiesByStages(userId, Stage.ACTIVE.getKey(), Stage.TEMPORARY.getKey());
		Integer departmentId = user.getDepartmentId();
		model.addAttribute("userData", user);			
		model.addAttribute("basicSkillList", getBasicSkillService.getBasicSkillList(departmentId));
		model.addAttribute("engineerSkillList", getEngineerSkillService.getEngineerSkillList(departmentId));
		model.addAttribute("statusData", status);
		model.addAttribute("personalityList", getPersonalityService.getPersonality());
		return "skill/skill-edit-status";
	}
	
	/**
	 * ステータス情報,所有性格,所有エンジニアスキル,所有基本スキルを登録,更新する.
	 * 
	 * @param statusForm ステータスフォーム
	 * @param loginUser ログイン情報
	 * @param typeOfButton (apply=申請ボタン, temporalSave=一時保存ボタン)
	 * @return ステータスタブの表示
	 */
	@PostMapping(value="/do")
	public String registerStatus(@Validated StatusForm statusForm, BindingResult result, @AuthenticationPrincipal LoginUser loginUser, Model model,RedirectAttributes redirectAttributes) {	
		Status status = new Status();
		String role = loginUser.getAuthorities().toString();
		Integer statusId = statusForm.getStatusId();
		Integer version = statusForm.getVersion();
		Integer stage = statusForm.getStage();
		Integer userId = statusForm.getUserId();
		String loginName = loginUser.getUser().getName();
		List<HadBasicSkill> hadBasicSkillList = statusForm.getHadBasicSkillList();
		List<HadEngineerSkill> hadEngineerSkillList = statusForm.getHadEngineerSkillList();
		List<Integer> personalityIdList = statusForm.getPersonalityIdList();
		//編集後のデータを格納するオブジェクト.
		Status statusForDb = new Status();
		BeanUtils.copyProperties(statusForm, statusForDb);
		//ユーザーがページに入る際、ログインユーザーIDとユーザーIDが合わない場合
		if(!role.equals("[ROLE_ADMIN]")
				&& !loginUser.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException();
		}
		//stageに3か9があったらエラー
		if(stage == Stage.SENT_BACK.getKey() || stage == Stage.DELETED.getKey()) {
			throw new IllegalArgumentException();
		}
		//編集前のデータ
		if(Objects.nonNull(statusId)) {
			status = getStatusService.getStatusByPrimaryKey(statusId);
			//編集画面の表示までに更新が行われたかの確認
			if(status.getVersion() != version) {
				redirectAttributes.addAttribute("userId", userId);
				redirectAttributes.addFlashAttribute("alertMessage", "編集失敗\nERROR:この編集内容は既に更新されました");
				return "redirect:/skill/status";
			}
		}
		//validationチェック
		result = validationCheck(statusForm, result, role);
		//validationチェックかつ一時保存の時以外
		if(result.hasErrors() && !(stage == Stage.TEMPORARY.getKey())) {
			return showEditStatus(model, loginUser, statusForm, redirectAttributes);
		}
		//一時保存中のデータを呼び出す.
		Status tempoStatus = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), null);	
		//登録,更新用のデータに更新者をセット
		statusForDb.setUpdater(loginName);		
		//ユーザーID,作成者, 更新者をセットする.
		if(role.equals("[ROLE_ADMIN]")) {	
			Status activeStatus = getStatusService.getStatusByStages(userId, stage, null);	
			if(Objects.isNull(activeStatus)) {
				statusForDb.setCreator(loginName);
				addStatusService.addStatus(statusForDb, version);				
				//承認済のステータスデータ(1行上で登録したデータ)を呼び出す.
				activeStatus = getStatusService.getStatusByStages(userId, stage, null);
				statusId = activeStatus.getStatusId();
				//所有基本スキル,エンジニアスキル,性格をステータスIDに紐づけて登録する.
				addSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, statusId);
			}else {
				statusForDb = prepareStatusForUpdate(statusForDb, activeStatus);
				updateStatusService.updateStatus(statusForDb);
				statusId = activeStatus.getStatusId();
        		//所有基本スキル,エンジニアスキル,性格を更新する.
				updateSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, statusId);
			}
			sendMailAboutStatusByAdminService.sendMailAboutStatusByAdmin(userId, stage, statusForDb.getAdminComment(), statusId, "既存のステータス編集");
			
		}else {
			//ステータス情報を登録する.
			if(Objects.isNull(tempoStatus)) {
				statusForDb.setCreator(loginName);
				addStatusService.addStatus(statusForDb, version);				
				//承認済のステータスデータ(1行上で登録したデータ)を呼び出す.
				status = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
				statusId = status.getStatusId();
				//所有基本スキル,エンジニアスキル,性格をステータスIDに紐づけて登録する.
				addSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, statusId);
			}else {
				tempoStatus = getStatusService.getStatusByStages(userId, Stage.TEMPORARY.getKey(), Stage.REQUESTING.getKey());
				statusForDb = prepareStatusForUpdate(statusForDb, tempoStatus);
				updateStatusService.updateStatus(statusForDb);	
				//所有基本スキル,エンジニアスキル,性格を更新する.
				updateSkills(hadBasicSkillList, hadEngineerSkillList, personalityIdList, tempoStatus.getStatusId());
			}
		}
		
		//リダイレクト時、ユーザーIDを渡す.
		redirectAttributes.addAttribute("userId", userId);
		redirectAttributes.addFlashAttribute("editCompleted", "ステータスを" + Stage.of(stage).getMessageForEdit() + "しました");
		return "redirect:/skill/status";
	}
	
	/**
	 * 所有基本,エンジニア,性格を登録する.
	 * 
	 * @param hadBasicSkillList 所有基本スキルリスト
	 * @param hadEngineerSkillList 所有エンジニアスキルリスト
	 * @param personalityIdList 性格IDリスト
	 * @param statusId ステータスID
	 */
	public void addSkills(List<HadBasicSkill>hadBasicSkillList, List<HadEngineerSkill>hadEngineerSkillList, List<Integer>personalityIdList, Integer statusId) {
		addHadBasicSkillService.addHadBasicSkill(hadBasicSkillList, statusId);
		addHadEngineerSkillService.addHadEngineerSkill(hadEngineerSkillList, statusId);
		addHadPersonalityService.addHadPersonality(personalityIdList, statusId);		
	}
	
	/**
	 * 所有基本,エンジニア,性格を更新する.
	 * 
	 * @param hadBasicSkillList 所有基本スキルリスト
	 * @param hadEngineerSkillList 所有エンジニアスキルリスト
	 * @param personalityIdList 性格IDリスト
	 * @param statusId ステータスID
	 */
	public void updateSkills(List<HadBasicSkill>hadBasicSkillList, List<HadEngineerSkill>hadEngineerSkillList, List<Integer>personalityIdList, Integer statusId) {
		updateHadBasicSkillService.updateHadBasicSkill(hadBasicSkillList, statusId);
		updateHadEngineerSkillService.updateHadEngineerSkill(hadEngineerSkillList, statusId);
		updateHadPersonalityService.upsertHadPersonality(personalityIdList, statusId);
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
		if (CollectionUtils.isEmpty(statusForm.getPersonalityIdList())) {
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
	
	/**
	 * 更新用のステータス情報の作成を行う
	 * 
	 * @param inputStatus 入力するステータス
	 * @param outputStatus 出力するステータス
	 * @return 更新用ステータス
	 */
	public Status prepareStatusForUpdate(Status inputStatus, Status outputStatus) {
		inputStatus.setStatusId(outputStatus.getStatusId());
		inputStatus.setCreator(outputStatus.getCreator());
		inputStatus.setCreatedAt(outputStatus.getCreatedAt());
		inputStatus.setVersion(outputStatus.getVersion());
		return inputStatus;
	}
}