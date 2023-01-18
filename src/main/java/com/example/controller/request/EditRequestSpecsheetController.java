package com.example.controller.request;

import java.time.LocalDate;
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
import com.example.domain.HadTechnicalSkill;
import com.example.domain.LoginUser;
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;
import com.example.service.spec.EditSpecsheetWithUpdateService;
import com.example.service.spec.GetSpecsheetByConditionService;
import com.example.service.spec.SendSpecMailService;
import com.example.service.spec.util.SpecStringUtils;
import com.example.service.technique.GetTechnicalSkillsForTagService;

@Controller
@RequestMapping("/request/spec")
public class EditRequestSpecsheetController {

	@Autowired
	private GetSpecsheetByConditionService getSpecsheetByConditionService;

	@Autowired
	private GetTechnicalSkillsForTagService getTechnicalSkillsForTagService;

	@Autowired
	private EditSpecsheetWithUpdateService editSpecsheetWithUpdateService;

	@Autowired
	private SpecStringUtils specStringUtils;

	@Autowired
	private SendSpecMailService sendSpecMailService;

	@ModelAttribute
	public EditSpecsheetForm setUpEditSpecsheetForm() {
		return new EditSpecsheetForm();
	}

	/**
	 * スペックシート申請修正画面へ遷移するメソッド.
	 * 
	 * @param specsheetId        スペックシートID
	 * @param userId             ユーザーID
	 * @param stage              ステージ
	 * @param version            バージョン
	 * @param model              リクエストスコープ
	 * @param redirectAttributes フラッシュスコープ
	 * @param loginUser          ログイン中のユーザー
	 * @return スペックシート申請修正画面
	 */
	@RequestMapping("/edit")
	public String showEditRequestSpecsheet(Integer specsheetId, Integer userId, Integer stage, Integer version,
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		String role = loginUser.getAuthorities().toString();
		Integer loginUserId = loginUser.getUser().getUserId();
		// 正当なリクエストかチェック
		if (!role.equals("[ROLE_ADMIN]") && !loginUserId.equals(userId)) {
			System.out.println("不正なリクエストが行われました。");
			throw new IllegalArgumentException();
		}
		if (!stage.equals(Stage.REQUESTING.getKey()) && !stage.equals(Stage.SENT_BACK.getKey())) {
			System.out.println("不正なリクエストが行われました。");
			throw new IllegalArgumentException();
		}
		// version照合
		List<Integer> htStageList = new ArrayList<>();
		htStageList.add(Stage.ACTIVE.getKey());
		htStageList.add(Stage.REQUESTING.getKey());
		htStageList.add(Stage.SENT_BACK.getKey());
		List<AppSpecsheet> specsheetList = getSpecsheetByConditionService.getSpecsheetByCondition(specsheetId, userId,
				new ArrayList<Integer>(), htStageList);

		AppSpecsheet specsheet = null;

		System.out.println(specsheetList.size());
		if (specsheetList.size() != 0) {
			specsheet = specsheetList.get(0);
			Integer versionPlus1 = version + 1;
			Integer newStage = specsheet.getStage();
			if (versionPlus1.equals(specsheet.getVersion())) {
				String message = Stage.of(stage).getMessageForRequest() + "失敗\nERROR: この申請は既に"
						+ Stage.of(newStage).getMessageForRequest() + "されました";
				redirectAttributes.addFlashAttribute("message", message);
				switch (Stage.of(newStage)) {
					case ACTIVE:
						return "redirect:/request";
					case REQUESTING:
					case SENT_BACK:
						return "redirect:/request/spec?specsheetId="
								+ specsheet.getSpecsheetId() + "&userId=" + userId + "&stage=" + newStage;
					default:
						throw new IllegalArgumentException();
				}
			}
			if (versionPlus1.compareTo(specsheet.getVersion()) < 0) {
				String message = Stage.of(stage).getMessageForRequest()
						+ "失敗\nERROR: この申請は既に変更もしくは処理されました";
				redirectAttributes.addFlashAttribute("message", message);
				switch (Stage.of(newStage)) {
					case ACTIVE:
						return "redirect:/request";
					case REQUESTING:
					case SENT_BACK:
						return "redirect:/request/spec?specsheetId="
								+ specsheet.getSpecsheetId() + "&userId=" + userId + "&stage=" + newStage;
					default:
						return "redirect:/skill/spec?userId=" + userId;
				}
			}
			if (!role.equals("[ROLE_ADMIN]") && !loginUserId.equals(specsheet.getUserId())) {
				System.out.println("不正なリクエストが行われました。");
				throw new IllegalArgumentException();
			}
		}
		if(specsheet == null) {
			System.out.println("不正なリクエストが行われました。");
			throw new IllegalArgumentException();
		}

		if (Stage.REQUESTING.getKey().equals(specsheet.getStage())) {
			specsheet.setHadTechnicalSkillList(
					specsheet.getHadTechnicalSkillList().stream()
							.filter(((Predicate<HadTechnicalSkill>) (ht -> Stage.ACTIVE.getKey().equals(ht.getStage())))
									.or((Predicate<HadTechnicalSkill>) (ht -> Stage.REQUESTING.getKey()
											.equals(ht.getStage()))))
							.collect(Collectors.toList()));
		}
		if (Stage.SENT_BACK.getKey().equals(specsheet.getStage())) {
			specsheet.setHadTechnicalSkillList(
					specsheet.getHadTechnicalSkillList().stream()
							.filter(((Predicate<HadTechnicalSkill>) (ht -> Stage.ACTIVE.getKey().equals(ht.getStage())))
									.or((Predicate<HadTechnicalSkill>) (ht -> Stage.SENT_BACK.getKey()
											.equals(ht.getStage()))))
							.collect(Collectors.toList()));
		}

		List<StringBuilder> htStringBuilderList = specStringUtils.divideHadTechnicalSkillsForTag(specsheet);
		model.addAttribute("htOSSB", htStringBuilderList.get(0).toString());
		model.addAttribute("htLanguageSB", htStringBuilderList.get(1).toString());
		model.addAttribute("htFrameworkSB", htStringBuilderList.get(2).toString());
		model.addAttribute("htLibrarySB", htStringBuilderList.get(3).toString());
		model.addAttribute("htMiddlewareSB", htStringBuilderList.get(4).toString());
		model.addAttribute("htToolSB", htStringBuilderList.get(5).toString());
		model.addAttribute("htProcessSB", htStringBuilderList.get(6).toString());
		specsheet = specStringUtils.divideUsedTechnicalSkillsForTag(specsheet);
		model.addAttribute("specsheet", specsheet);

		List<StringBuilder> technicalSkillSBList = getTechnicalSkillsForTagService.getTechnicalSkillsForTag();
		model.addAttribute("OSSB", technicalSkillSBList.get(0));
		model.addAttribute("languageSB", technicalSkillSBList.get(1));
		model.addAttribute("frameworkSB", technicalSkillSBList.get(2));
		model.addAttribute("librarySB", technicalSkillSBList.get(3));
		model.addAttribute("middlewareSB", technicalSkillSBList.get(4));
		model.addAttribute("toolSB", technicalSkillSBList.get(5));
		model.addAttribute("processSB", technicalSkillSBList.get(6));

		return "request/request-edit-spec";
	}

	/**
	 * スペックシート申請を修正するメソッド.
	 * 
	 * @param form               スペックシート編集フォーム
	 * @param result             エラー格納オブジェクト
	 * @param model              リクエストスコープ
	 * @param redirectAttributes フラッシュスコープ
	 * @param loginUser          ログイン中のユーザー
	 * @return 申請トップ画面へリダイレクト
	 */
	@PostMapping("/edit/do")
	public String editSpecsheet(@Validated EditSpecsheetForm form, BindingResult result,
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		// 正当なリクエストかチェック
		String role = loginUser.getAuthorities().toString();
		Integer specsheetId = form.getSpecsheetId();
		Integer userId = form.getUserId();
		Integer stage = form.getStage();
		Integer version = form.getVersion();
		if (role.equals("[ROLE_ADMIN]") && !Stage.ACTIVE.getKey().equals(stage)) {
			System.out.println("不正な修正が行われました。");
			throw new IllegalArgumentException();
		}
		if (!role.equals("[ROLE_ADMIN]") && !loginUser.getUser().getUserId().equals(userId)) {
			System.out.println("不正な修正が行われました。");
			throw new IllegalArgumentException();
		}
		if (!role.equals("[ROLE_ADMIN]") && !Stage.REQUESTING.getKey().equals(stage)) {
			System.out.println("不正な修正が行われました。");
			throw new IllegalArgumentException();
		}

		form.setDevExperienceList(
				form.getDevExperienceList().stream()
						.sorted(Comparator.comparing(DevExperienceForm::getDevCount))
						.collect(Collectors.toList()));
		model.addAttribute("editSpecsheetForm", form);
		// 入力値チェック
		form = specStringUtils.trimWhitespaceForEditSpecsheetForm(form);
		String required = "入力は必須です";
		if (Objects.isNull(form.getEmployeeId())) {
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
		List<DevExperienceForm> devExperienceList = form.getDevExperienceList();
		int[] i = { 0 };
		devExperienceList.forEach(devForm -> {
			if (!StringUtils.hasText(devForm.getProjectName())) {
				result.rejectValue("devExperienceList[" + i[0] + "].projectName", null, required);
			}
			if (!StringUtils.hasText(devForm.getStartedOn())) {
				result.rejectValue("devExperienceList[" + i[0] + "].startedOn", null, required);
			} else {
				devForm.setStartedOn(devForm.getStartedOn() + "-01");
				LocalDate inputStartedOn = LocalDate.parse(devForm.getStartedOn());
				if (inputStartedOn.compareTo(LocalDate.now()) > 0) {
					result.rejectValue("devExperienceList[" + i[0] + "].startedOn", null, "開始年月は過去の年月を選択してください");
				}
			}
			if (!StringUtils.hasText(devForm.getFinishedOn())) {
				result.rejectValue("devExperienceList[" + i[0] + "].finishedOn", null, required);
			} else {
				devForm.setFinishedOn(devForm.getFinishedOn() + "-01");
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
		// 入力値チェック終了

		if (result.hasErrors()) {
			model.addAttribute("message",
					Stage.of(form.getStage()).getMessageForRequest() + "失敗\nERROR: 入力値が不正です");
			return showEditRequestSpecsheet(
					specsheetId, userId, form.getRawStage(), version, model, redirectAttributes, loginUser);
		}

		Integer returnedStage = editSpecsheetWithUpdateService.editSpecsheet(form, loginUser);
		if (Objects.nonNull(returnedStage)) {
			String message = Stage.of(stage).getMessageForRequest() + "失敗\nERROR: この申請は既に"
					+ Stage.of(returnedStage).getMessageForRequest() + "されました";
			redirectAttributes.addFlashAttribute("message", message);
			
			switch (Stage.of(returnedStage)) {
				case ACTIVE:
					return "redirect:/request";
				case REQUESTING:
				case SENT_BACK:
					return "redirect:/request/spec?specsheetId=" + specsheetId + "&userId=" + userId + "&stage="
							+ returnedStage;
				default:
					throw new IllegalArgumentException();
			}
		}

		if (stage.equals(Stage.ACTIVE.getKey())) {
			redirectAttributes.addFlashAttribute("message",
					"スペックシート申請を" + Stage.of(stage).getMessageForRequest() + "しました");

			sendSpecMailService.sendSpecMail("申請承認", form.getAdminComment(), specsheetId, userId, stage);

			return "redirect:/request";
		} else {
			redirectAttributes.addFlashAttribute("success",
					"スペックシート申請を" + Stage.of(stage).getMessageForRequest() + "しました");
			return "redirect:/request/spec?specsheetId=" + specsheetId + "&userId=" + userId + "&stage=" + stage;
		}
	}

}