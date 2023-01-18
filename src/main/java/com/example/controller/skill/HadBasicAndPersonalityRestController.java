package com.example.controller.skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.HadBasicSkill;
import com.example.domain.HadPersonality;
import com.example.service.status.GetHadBasicSkillListService;
import com.example.service.status.GetHadPersonaliyListService;

@RestController
@RequestMapping(value="")
public class HadBasicAndPersonalityRestController {
	
	@Autowired
	private GetHadPersonaliyListService getHadPersonaliySkillListService;
	
	@Autowired
	private GetHadBasicSkillListService getHadBasicSkillListService;
	
	/**
	 * 所有している性格の表示を行う.
	 * 
	 * @param statusId ステータスID
	 * @return 所有している性格情報が格納されているmap
	 */
	@GetMapping(value="/get_had_personality")
	public Map<String, List<HadPersonality>> getHadPersonality(Integer statusId){
		Map<String, List<HadPersonality>> map = new HashMap<>();
		List<HadPersonality> hadPersonalityList = getHadPersonaliySkillListService.getHadPersonalityList(statusId);
		map.put("hadPersonalityList", hadPersonalityList);
		return map;
	}
	
	/**
	 * 基本スキルのレーダーチャートの表示を行う.
	 * 
	 * @param statusId ステータスID
	 * @return 所有している基本スキル情報が格納されているmap
	 */
	@GetMapping(value="/get_had_basic_skill")
	public Map<String, List<HadBasicSkill>> getHadBasicSkill(Integer statusId){
		Map<String, List<HadBasicSkill>> map = new HashMap<>();
		List<HadBasicSkill> hadBasicSkillList = getHadBasicSkillListService.getHadBasicSkillList(statusId);
		map.put("hadBasicSkillList", hadBasicSkillList);
		return map;
	}
}
