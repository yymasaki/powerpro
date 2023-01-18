package com.example.controller.skill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.common.Stage;
import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.example.HadTechnicalSkillExample;
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;
import com.example.service.spec.DeleteSpecsheetService;
import com.example.service.spec.EditSpecsheetWithInsertService;
import com.example.service.spec.EditSpecsheetWithUpdateService;
import com.example.service.spec.GetSpecsheetByConditionService;
import com.example.service.spec.SendSpecMailService;
import com.example.service.spec.util.SpecStringUtils;
import com.example.service.technique.DeleteHadTechnicalSkillService;
import com.example.service.technique.GetTechnicalSkillsForTagService;
import com.example.service.technique.UpdateHadTechnicalSkillStageService;
import com.example.service.user.GetSpecificUserService;

@Controller
@RequestMapping("/skill/spec")
public class EditSpecsheetController {
	
	@Autowired
	private GetSpecificUserService getSpecificUserService;
	@Autowired
	private GetSpecsheetByConditionService getSpecsheetByConditionService;
	@Autowired
	private EditSpecsheetWithInsertService editSpecsheetWithInsertService;
	@Autowired
	private EditSpecsheetWithUpdateService editSpecsheetWithUpdateService;
	@Autowired
	private DeleteSpecsheetService deleteSpecsheetService;
	@Autowired
	private UpdateHadTechnicalSkillStageService updateHadTechnicalSkillStageService;
	@Autowired
	private DeleteHadTechnicalSkillService deleteHadTechnicalSkillService;
	@Autowired
	private GetTechnicalSkillsForTagService getTechnicalSkillsForTagService;
	@Autowired
	private SpecStringUtils specStringUtils;
	@Autowired
	private SendSpecMailService sendSpecMailService;
	
	@ModelAttribute
	public EditSpecsheetForm setUpEditSpecsheetForm() {
		return new EditSpecsheetForm();
	}
	
	/**
	 * スペックシート編集画面へ遷移するメソッド.
	 * 
	 * @param specsheetId スペックシートID
	 * @param userId ユーザーID
	 * @param model リクエストスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return スペックシート編集画面
	 */
	@RequestMapping("/edit")
	public String showEditSpecsheet(Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		//正当なリクエストかチェック
		String role = loginUser.getAuthorities().toString();
		if(role.equals("[ROLE_SALES]")) {
			return "redirect:/skill/spec?userId=" + userId;
		}
		if(!role.equals("[ROLE_ADMIN]")) {
			userId = loginUser.getUser().getUserId();
		}
		
		List<Integer> specStageList = new ArrayList<>();
		specStageList.add(Stage.ACTIVE.getKey());
		specStageList.add(Stage.TEMPORARY.getKey());
		specStageList.add(Stage.REQUESTING.getKey());
		specStageList.add(Stage.SENT_BACK.getKey());
		List<Integer> htStageList = new ArrayList<>();
		htStageList.add(Stage.ACTIVE.getKey());
		if(role.equals("[ROLE_ADMIN]")) {
			htStageList.add(Stage.DELETED.getKey());
		}else {
			htStageList.add(Stage.TEMPORARY.getKey());
		}
		//selectでactive,temporary,requesting,sent backをversion新しい順で2つとる
		List<AppSpecsheet> specsheetList =  new ArrayList<>();
		specsheetList = getSpecsheetByConditionService.getSpecsheetByCondition(0, userId, specStageList, htStageList);
		
		AppSpecsheet specsheet = null;
		List<AppSpecsheet> specsheetIllegalList = new ArrayList<>();
		List<AppSpecsheet> specsheetActiveList = new ArrayList<>();
		List<AppSpecsheet> specsheetTempoList = new ArrayList<>();
		//requesting,sent backがあったら500エラー
		specsheetList.stream()
			.filter(((Predicate<AppSpecsheet>)(spec -> Stage.REQUESTING.getKey().equals(spec.getStage())))
					.or((Predicate<AppSpecsheet>)(spec -> Stage.SENT_BACK.getKey().equals(spec.getStage()))))
			.forEach(spec -> specsheetIllegalList.add(spec));
		if(specsheetIllegalList.size() != 0) {
			throw new IllegalArgumentException();
		}
		specsheetList.stream()
		.filter(spec -> Stage.ACTIVE.getKey().equals(spec.getStage()))
		.sorted(Comparator.comparing(AppSpecsheet::getUpdatedAt).reversed())
		.forEach(spec -> specsheetActiveList.add(spec));
		if(specsheetActiveList.size() != 0) {
			specsheet = specsheetActiveList.get(0);
		}
		specsheetList.stream()
		.filter(spec -> !"[ROLE_ADMIN]".equals(role))
		.filter(spec -> Stage.TEMPORARY.getKey().equals(spec.getStage()))
		.forEach(spec -> specsheetTempoList.add(spec));
		
		//temporaryのデータは24時間経過していたら削除
		if(specsheetTempoList.size() != 0) {
			AppSpecsheet specTempo = specsheetTempoList.get(0);
			LocalDateTime oneDaylater = specTempo.getUpdatedAt();
			oneDaylater = oneDaylater.plusDays(1);
			if(LocalDateTime.now().compareTo(oneDaylater) < 0) {
				specsheet = specTempo;
				specsheet.setDevExperienceList(
						specsheet.getDevExperienceList().stream()
						.sorted(Comparator.comparing(DevExperience::getFinishedOn, Comparator.nullsFirst(Comparator.reverseOrder()))
								.thenComparing(Comparator.comparing(DevExperience::getStartedOn, 
										Comparator.nullsFirst(Comparator.naturalOrder()))))
						.collect(Collectors.toList()));
			}else {
				//保有テクニカルスキルの状態を元に戻す(9→0、追加したものをdelete)
				HadTechnicalSkill hadTechnicalSkill = new HadTechnicalSkill();
				hadTechnicalSkill.setStage(Stage.ACTIVE.getKey());
				hadTechnicalSkill.setUpdater(loginUser.getUser().getName());
				hadTechnicalSkill.setUpdatedAt(LocalDateTime.now());
				HadTechnicalSkillExample htExample = new HadTechnicalSkillExample();
				htExample.createCriteria().andStageEqualTo(Stage.DELETED.getKey().toString());
				updateHadTechnicalSkillStageService.updateStage(hadTechnicalSkill, htExample);
				
				HadTechnicalSkillExample htExample2 = new HadTechnicalSkillExample();
				htExample2.createCriteria()
				.andUserIdEqualTo(loginUser.getUser().getUserId())
				.andStageEqualTo(Stage.TEMPORARY.getKey().toString());
				deleteHadTechnicalSkillService.deleteByExample(htExample2);
				
				deleteSpecsheetService.deleteSpecsheet(specTempo.getSpecsheetId());
				model.addAttribute("message", "一時保存データは更新後24時間が経過したため削除されました");
			}
		}
		
		if(Objects.nonNull(specsheet)) {
			List<StringBuilder> htStringBuilderList = specStringUtils.divideHadTechnicalSkillsForTag(specsheet);
			model.addAttribute("htOSSB", htStringBuilderList.get(0).toString());
			model.addAttribute("htLanguageSB", htStringBuilderList.get(1).toString());
			model.addAttribute("htFrameworkSB", htStringBuilderList.get(2).toString());
			model.addAttribute("htLibrarySB", htStringBuilderList.get(3).toString());
			model.addAttribute("htMiddlewareSB", htStringBuilderList.get(4).toString());
			model.addAttribute("htToolSB", htStringBuilderList.get(5).toString());
			model.addAttribute("htProcessSB", htStringBuilderList.get(6).toString());
			specsheet = specStringUtils.divideUsedTechnicalSkillsForTag(specsheet);
		} else {
			specsheet = new AppSpecsheet();
			specsheet.setUserId(userId);
			specsheet.setUser(getSpecificUserService.get(userId));
		}
		model.addAttribute("specsheet", specsheet);
		
		List<StringBuilder> technicalSkillSBList = getTechnicalSkillsForTagService.getTechnicalSkillsForTag();
		model.addAttribute("OSSB", technicalSkillSBList.get(0));
		model.addAttribute("languageSB", technicalSkillSBList.get(1));
		model.addAttribute("frameworkSB", technicalSkillSBList.get(2));
		model.addAttribute("librarySB", technicalSkillSBList.get(3));
		model.addAttribute("middlewareSB", technicalSkillSBList.get(4));
		model.addAttribute("toolSB", technicalSkillSBList.get(5));
		model.addAttribute("processSB", technicalSkillSBList.get(6));
		System.out.println("OSSB : "+technicalSkillSBList.get(0));
				
		return "skill/skill-edit-spec";
	}
	
	/**
	 * スペックシートを編集するメソッド.
	 * 
	 * @param form スペックシート編集フォーム
	 * @param result エラー格納オブジェクト
	 * @param model リクエストスコープ
	 * @param redirectAttributes フラッシュスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return スペックシート表示画面へリダイレクト
	 */
	@PostMapping("/edit/do")
	public String editSpecsheet(@Validated EditSpecsheetForm form, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		String role = loginUser.getAuthorities().toString();
		Integer loginUserId = loginUser.getUser().getUserId();
		Integer stage = form.getStage();
		
		if(role.equals("[ROLE_ADMIN]") && !Stage.ACTIVE.getKey().equals(stage)) {
			System.out.println("不正な編集が行われました。");
			throw new IllegalArgumentException();
		}
		if(!role.equals("[ROLE_ADMIN]") && !loginUserId.equals(form.getUserId())) {
			System.out.println("不正な編集が行われました。");
			throw new IllegalArgumentException();
		}
		if(!role.equals("[ROLE_ADMIN]") 
				&& !Stage.TEMPORARY.getKey().equals(stage) 
				&& !Stage.REQUESTING.getKey().equals(stage)) {
			System.out.println("不正な編集が行われました。");
			throw new IllegalArgumentException();
		}
		
		form.setDevExperienceList(
				form.getDevExperienceList().stream()
				.sorted(Comparator.comparing(DevExperienceForm::getDevCount))
				.collect(Collectors.toList()));
		model.addAttribute("editSpecsheetForm", form);
		//一時保存でなければ入力値チェック
		form = specStringUtils.trimWhitespaceForEditSpecsheetForm(form);
		form.getDevExperienceList().stream()
				.filter(dev -> StringUtils.hasText(dev.getStartedOn()))
		.forEach(dev -> dev.setStartedOn(dev.getStartedOn() + "-01"));
		form.getDevExperienceList().stream()
				.filter(dev -> StringUtils.hasText(dev.getFinishedOn()))
		.forEach(dev -> dev.setFinishedOn(dev.getFinishedOn() + "-01"));
		String required = "入力は必須です";
		if(Stage.REQUESTING.getKey().equals(stage) || Stage.ACTIVE.getKey().equals(stage)) {
			if(Objects.isNull(form.getEmployeeId())) {
				result.rejectValue("employeeId", null, required);
			}
			if (!StringUtils.hasText(form.getGeneration())) {
				result.rejectValue("generation", null, required);
			}
			if (!StringUtils.hasText(form.getGender())) {
				result.rejectValue("gender", null, required);
			}
			if (!StringUtils.hasText(form.getNearestStation())) {
				result.rejectValue("nearestStation", null, required);
			}
			if (!StringUtils.hasText(form.getStartBusinessDate())) {
				result.rejectValue("startBusinessDate", null, required);
			}
			if (Objects.isNull(form.getEngineerPeriod())) {
				result.rejectValue("engineerPeriod", null, required);
			}
			if (Objects.isNull(form.getSePeriod())) {
				result.rejectValue("sePeriod", null, required);
			}
			if (Objects.isNull(form.getPgPeriod())) {
				result.rejectValue("pgPeriod", null, required);
			}
			if (Objects.isNull(form.getItPeriod())) {
				result.rejectValue("itPeriod", null, required);
			}
			if (!StringUtils.hasText(form.getAppeal())) {
				result.rejectValue("appeal", null, required);
			}
			if (!StringUtils.hasText(form.getEffort())) {
				result.rejectValue("effort", null, required);
			}
			int[] i = { 0 };
			form.getDevExperienceList().forEach(devForm -> {
				System.out.println(devForm);
				if (!StringUtils.hasText(devForm.getProjectName())) {
					result.rejectValue("devExperienceList[" + i[0] + "].projectName", null, required);
				}
				if (!StringUtils.hasText(devForm.getStartedOn())) {
					result.rejectValue("devExperienceList[" + i[0] + "].startedOn", null, required);
				} else {
					LocalDate inputStartedOn = LocalDate.parse(devForm.getStartedOn());
					if (inputStartedOn.compareTo(LocalDate.now()) > 0) {
						result.rejectValue("devExperienceList[" + i[0] + "].startedOn", null, "開始年月は過去の年月を選択してください");
					}
				}
				if (!StringUtils.hasText(devForm.getFinishedOn())) {
					result.rejectValue("devExperienceList[" + i[0] + "].finishedOn", null, required);
				}
				if (StringUtils.hasText(devForm.getStartedOn()) && StringUtils.hasText(devForm.getFinishedOn())) {
					LocalDate inputStartedOn = LocalDate.parse(devForm.getStartedOn());
					LocalDate inputFinishedOn = LocalDate.parse(devForm.getFinishedOn());
					if (inputFinishedOn.compareTo(inputStartedOn) < 0) {
						result.rejectValue("devExperienceList[" + i[0] + "].finishedOn", null, "終了年月は開始年月以降にしてください");
					}
				}
				if (Objects.isNull(devForm.getTeamMembers())) {
					result.rejectValue("devExperienceList[" + i[0] + "].teamMembers", null, required);
				}
				if (Objects.isNull(devForm.getProjectMembers())) {
					result.rejectValue("devExperienceList[" + i[0] + "].projectMembers", null, required);
				}
				if (!StringUtils.hasText(devForm.getRole())) {
					result.rejectValue("devExperienceList[" + i[0] + "].role", null, required);
				}
				if (!StringUtils.hasText(devForm.getProjectDetails())) {
					result.rejectValue("devExperienceList[" + i[0] + "].projectDetails", null, required);
				}
				if (!StringUtils.hasText(devForm.getTasks())) {
					result.rejectValue("devExperienceList[" + i[0] + "].tasks", null, required);
				}
				if (!StringUtils.hasText(devForm.getAcquired())) {
					result.rejectValue("devExperienceList[" + i[0] + "].acquired", null, required);
				}
				i[0]++;
			});
		}
		//入力値チェック終了
		
		if(result.hasErrors()) {
			String message = Stage.of(stage).getMessageForEdit() + "失敗\nERROR: 入力値が不正です";
			model.addAttribute("message", message);
			return showEditSpecsheet(form.getUserId(), model, loginUser);
		}
		
		Integer returnedStage = null;
		if(Objects.isNull(form.getRawStage()) || Stage.ACTIVE.getKey().equals(form.getRawStage())) {
			returnedStage = editSpecsheetWithInsertService.editSpecsheet(form, loginUser);
		}else {
			returnedStage = editSpecsheetWithUpdateService.editSpecsheet(form, loginUser);
		}
		if(Objects.nonNull(returnedStage)) {
			StringBuilder message = new StringBuilder(Stage.of(stage).getMessageForEdit());
			message.append("失敗\nERROR: このスペックシートは既に").append(Stage.of(returnedStage).getMessageForEdit())
					.append("されました");
			redirectAttributes.addFlashAttribute("message", message.toString());
			return "redirect:/skill/spec?userId=" + form.getUserId();
		}
		
		if(role.equals("[ROLE_ADMIN]")) {
			sendSpecMailService.sendSpecMail("既存のスペックシート編集", "", form.getSpecsheetId(), form.getUserId(), stage);
		}
		
		redirectAttributes.addFlashAttribute("success", "スペックシートを"+Stage.of(stage).getMessageForEdit()+"しました");
		return "redirect:/skill/spec?userId=" + form.getUserId();
	}
	
}
