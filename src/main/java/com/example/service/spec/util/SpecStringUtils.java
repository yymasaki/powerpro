package com.example.service.spec.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Category;
import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.form.DevExperienceForm;
import com.example.form.EditSpecsheetForm;

@Service
@Transactional
public class SpecStringUtils {
	
	/**
	 * 保有テクニカルスキルをタグ表示用に分け、リストを返すメソッド.
	 * 
	 * @param specsheet スペックシート
	 */
	public List<StringBuilder> divideHadTechnicalSkillsForTag(AppSpecsheet specsheet) {
		List<StringBuilder> htStringBuilderList = new ArrayList<>();
		StringBuilder htOSSB = new StringBuilder();
		StringBuilder htLanguageSB = new StringBuilder();
		StringBuilder htFrameworkSB = new StringBuilder();
		StringBuilder htLibrarySB = new StringBuilder();
		StringBuilder htMiddlewareSB = new StringBuilder();
		StringBuilder htToolSB = new StringBuilder();
		StringBuilder htProcessSB = new StringBuilder();
		specsheet.getHadTechnicalSkillList().forEach(ht -> {
			if(ht.getTechnicalSkill().getCategory().equals(Category.OS.getKey())) {
				htOSSB.append(ht.getTechnicalSkill().getName()).append(",");
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.LANGUAGE.getKey())) {
				htLanguageSB.append(ht.getTechnicalSkill().getName()).append(",");
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.FRAMEWORK.getKey())) {
				htFrameworkSB.append(ht.getTechnicalSkill().getName()).append(",");
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.LIBRARY.getKey())) {
				htLibrarySB.append(ht.getTechnicalSkill().getName()).append(",");
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.MIDDLEWARE.getKey())) {
				htMiddlewareSB.append(ht.getTechnicalSkill().getName()).append(",");
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.TOOL.getKey())) {
				htToolSB.append(ht.getTechnicalSkill().getName()).append(",");
			}
			if (ht.getTechnicalSkill().getCategory().equals(Category.PROCESS.getKey())) {
				htProcessSB.append(ht.getTechnicalSkill().getName()).append(",");
			}
		});
		if(htOSSB.length()>0) {
			htOSSB.deleteCharAt(htOSSB.length()-1);
		}
		if(htLanguageSB.length()>0) {
			htLanguageSB.deleteCharAt(htLanguageSB.length()-1);
		}
		if(htFrameworkSB.length()>0) {
			htFrameworkSB.deleteCharAt(htFrameworkSB.length()-1);
		}
		if(htLibrarySB.length()>0) {
			htLibrarySB.deleteCharAt(htLibrarySB.length()-1);
		}
		if(htMiddlewareSB.length()>0) {
			htMiddlewareSB.deleteCharAt(htMiddlewareSB.length()-1);
		}
		if(htToolSB.length()>0) {
			htToolSB.deleteCharAt(htToolSB.length()-1);
		}
		if(htProcessSB.length()>0) {
			htProcessSB.deleteCharAt(htProcessSB.length()-1);
		}
		
		htStringBuilderList.add(htOSSB);
		htStringBuilderList.add(htLanguageSB);
		htStringBuilderList.add(htFrameworkSB);
		htStringBuilderList.add(htLibrarySB);
		htStringBuilderList.add(htMiddlewareSB);
		htStringBuilderList.add(htToolSB);
		htStringBuilderList.add(htProcessSB);
		return htStringBuilderList;
	}
	
	/**
	 * 各開発経験での利用テクニカルスキルをタグ表示用に分け、スペックシートに格納するメソッド.
	 * 
	 * @param specsheet スペックシート
	 */
	public AppSpecsheet divideUsedTechnicalSkillsForTag(AppSpecsheet specsheet) {
		
		List<DevExperience> devExperienceList = new ArrayList<>();
		specsheet.getDevExperienceList().forEach(de -> {
			List<StringBuilder> usedTechnicalSkillSBList = new ArrayList<>();
			StringBuilder utOSSB = new StringBuilder();
			StringBuilder utLanguageSB = new StringBuilder();
			StringBuilder utFrameworkSB = new StringBuilder();
			StringBuilder utLibrarySB = new StringBuilder();
			StringBuilder utMiddlewareSB = new StringBuilder();
			StringBuilder utToolSB = new StringBuilder();
			StringBuilder utProcessSB = new StringBuilder();
			de.getUsedTechnicalSkillList().forEach(ut -> {
				if(ut.getTechnicalSkill().getCategory().equals(Category.OS.getKey())) {
					utOSSB.append(ut.getTechnicalSkill().getName()).append(",");
				}
				if (ut.getTechnicalSkill().getCategory().equals(Category.LANGUAGE.getKey())) {
					utLanguageSB.append(ut.getTechnicalSkill().getName()).append(",");
				}
				if (ut.getTechnicalSkill().getCategory().equals(Category.FRAMEWORK.getKey())) {
					utFrameworkSB.append(ut.getTechnicalSkill().getName()).append(",");
				}
				if (ut.getTechnicalSkill().getCategory().equals(Category.LIBRARY.getKey())) {
					utLibrarySB.append(ut.getTechnicalSkill().getName()).append(",");
				}
				if (ut.getTechnicalSkill().getCategory().equals(Category.MIDDLEWARE.getKey())) {
					utMiddlewareSB.append(ut.getTechnicalSkill().getName()).append(",");
				}
				if (ut.getTechnicalSkill().getCategory().equals(Category.TOOL.getKey())) {
					utToolSB.append(ut.getTechnicalSkill().getName()).append(",");
				}
				if (ut.getTechnicalSkill().getCategory().equals(Category.PROCESS.getKey())) {
					utProcessSB.append(ut.getTechnicalSkill().getName()).append(",");
				}
			});
			if(utOSSB.length()>0) {
				utOSSB.deleteCharAt(utOSSB.length()-1);
			}
			usedTechnicalSkillSBList.add(utOSSB);
			if(utLanguageSB.length()>0) {
				utLanguageSB.deleteCharAt(utLanguageSB.length()-1);
			}
			usedTechnicalSkillSBList.add(utLanguageSB);
			if(utFrameworkSB.length()>0) {
				utFrameworkSB.deleteCharAt(utFrameworkSB.length()-1);
			}
			usedTechnicalSkillSBList.add(utFrameworkSB);
			if(utLibrarySB.length()>0) {
				utLibrarySB.deleteCharAt(utLibrarySB.length()-1);
			}
			usedTechnicalSkillSBList.add(utLibrarySB);
			if(utMiddlewareSB.length()>0) {
				utMiddlewareSB.deleteCharAt(utMiddlewareSB.length()-1);
			}
			usedTechnicalSkillSBList.add(utMiddlewareSB);
			if(utToolSB.length()>0) {
				utToolSB.deleteCharAt(utToolSB.length()-1);
			}
			usedTechnicalSkillSBList.add(utToolSB);
			if(utProcessSB.length()>0) {
				utProcessSB.deleteCharAt(utProcessSB.length()-1);
			}
			usedTechnicalSkillSBList.add(utProcessSB);
			de.setUsedTechnicalSkillSBList(usedTechnicalSkillSBList);
			devExperienceList.add(de);
		});
		specsheet.setDevExperienceList(devExperienceList);
		return specsheet;
	}
	
	/**
	 * フォームの入力値の空白を削除するメソッド.
	 * 
	 * @param form スペックシート編集フォーム
	 * @return スペックシート編集フォーム
	 */
	public EditSpecsheetForm trimWhitespaceForEditSpecsheetForm(EditSpecsheetForm form) {
		String generation = form.getGeneration().strip();
		generation = generation.replace("　", " ").replaceAll(" +", " ");
		form.setGeneration(generation);
		String nearestStation = form.getNearestStation().strip();
		nearestStation = nearestStation.replace("　", " ").replaceAll(" +", " ");
		form.setNearestStation(nearestStation);
		String startBusinessDate = form.getStartBusinessDate().strip();
		startBusinessDate = startBusinessDate.replace("　", " ").replaceAll(" +", " ");
		form.setStartBusinessDate(startBusinessDate);
		String appeal = form.getAppeal().strip();
		appeal = appeal.replace("　", " ").replaceAll(" +", " ");
		form.setAppeal(appeal);
		String effort = form.getEffort().strip();
		effort = effort.replace("　", " ").replaceAll(" +", " ");
		form.setEffort(effort);
		String certification = form.getCertification().strip();
		certification = certification.replace("　", " ").replaceAll(" +", " ");
		form.setCertification(certification);
		String preJob = form.getPreJob().strip();
		preJob = preJob.replace("　", " ").replaceAll(" +", " ");
		form.setPreJob(preJob);
		List<DevExperienceForm> devExperienceList = form.getDevExperienceList();
		if(devExperienceList.size() == 0) {
			return form;
		}
		devExperienceList.forEach(devForm -> {
			String projectName = devForm.getProjectName().strip();
			projectName = projectName.replace("　", " ").replaceAll(" +", " ");
			devForm.setProjectName(projectName);
			String role = devForm.getRole().strip();
			role = role.replace("　", " ").replaceAll(" +", " ");
			devForm.setRole(role);
			String projectDetails = devForm.getProjectDetails().strip();
			projectDetails = projectDetails.replace("　", " ").replaceAll(" +", " ");
			devForm.setProjectDetails(projectDetails);
			String tasks = devForm.getTasks().strip();
			tasks = tasks.replace("　", " ").replaceAll(" +", " ");
			devForm.setTasks(tasks);
			String acquired = devForm.getAcquired().strip();
			acquired = acquired.replace("　", " ").replaceAll(" +", " ");
			devForm.setAcquired(acquired);
		});
		form.setDevExperienceList(devExperienceList);
		return form;
	}

}
