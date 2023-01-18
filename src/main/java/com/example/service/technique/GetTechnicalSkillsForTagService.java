package com.example.service.technique;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Category;
import com.example.common.Stage;
import com.example.domain.TechnicalSkill;
import com.example.example.TechnicalSkillExample;
import com.example.mapper.TechnicalSkillMapper;

@Service
@Transactional
public class GetTechnicalSkillsForTagService {

	@Autowired
	private TechnicalSkillMapper technicalSkillMapper;

	/**
	 * 登録されているテクニカルスキルを取得してタグの候補として返すメソッド.
	 * 
	 * @return タグのvalueとなる文字列のリスト
	 */
	public List<StringBuilder> getTechnicalSkillsForTag() {
		TechnicalSkillExample example = new TechnicalSkillExample();
		example.createCriteria().andStageEqualTo(Stage.ACTIVE.getKey().toString());
		example.setOrderByClause("name");
		List<TechnicalSkill> technicalSkillList = technicalSkillMapper.selectByExample(example);

		List<StringBuilder> technicalSkillSBList = new ArrayList<>();
		StringBuilder OSSB = new StringBuilder();
		StringBuilder languageSB = new StringBuilder();
		StringBuilder frameworkSB = new StringBuilder();
		StringBuilder librarySB = new StringBuilder();
		StringBuilder middlewareSB = new StringBuilder();
		StringBuilder toolSB = new StringBuilder();
		StringBuilder processSB = new StringBuilder();

		technicalSkillList.forEach(ts -> {
			if (ts.getCategory().equals(Category.OS.getKey())) {
				OSSB.append("\"").append(ts.getName()).append("\",");
			}
			if (ts.getCategory().equals(Category.LANGUAGE.getKey())) {
				languageSB.append("\"").append(ts.getName()).append("\",");
			}
			if (ts.getCategory().equals(Category.FRAMEWORK.getKey())) {
				frameworkSB.append("\"").append(ts.getName()).append("\",");
			}
			if (ts.getCategory().equals(Category.LIBRARY.getKey())) {
				librarySB.append("\"").append(ts.getName()).append("\",");
			}
			if (ts.getCategory().equals(Category.MIDDLEWARE.getKey())) {
				middlewareSB.append("\"").append(ts.getName()).append("\",");
			}
			if (ts.getCategory().equals(Category.TOOL.getKey())) {
				toolSB.append("\"").append(ts.getName()).append("\",");
			}
			if (ts.getCategory().equals(Category.PROCESS.getKey())) {
				processSB.append("\"").append(ts.getName()).append("\",");
			}
		});
		if (OSSB.length() > 0) {
			OSSB.deleteCharAt(OSSB.length() - 1);
		}
		if (languageSB.length() > 0) {
			languageSB.deleteCharAt(languageSB.length() - 1);
		}
		if (frameworkSB.length() > 0) {
			frameworkSB.deleteCharAt(frameworkSB.length() - 1);
		}
		if (librarySB.length() > 0) {
			librarySB.deleteCharAt(librarySB.length() - 1);
		}
		if (middlewareSB.length() > 0) {
			middlewareSB.deleteCharAt(middlewareSB.length() - 1);
		}
		if (toolSB.length() > 0) {
			toolSB.deleteCharAt(toolSB.length() - 1);
		}
		if (processSB.length() > 0) {
			processSB.deleteCharAt(processSB.length() - 1);
		}

		technicalSkillSBList.add(OSSB);
		technicalSkillSBList.add(languageSB);
		technicalSkillSBList.add(frameworkSB);
		technicalSkillSBList.add(librarySB);
		technicalSkillSBList.add(middlewareSB);
		technicalSkillSBList.add(toolSB);
		technicalSkillSBList.add(processSB);
		System.out.println(technicalSkillSBList);
		return technicalSkillSBList;
	}

}
