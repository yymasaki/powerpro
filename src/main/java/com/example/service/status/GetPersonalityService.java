package com.example.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Personality;
import com.example.example.PersonalityExample;
import com.example.mapper.PersonalityMapper;

@Transactional
@Service
public class GetPersonalityService {
	
	@Autowired
	private PersonalityMapper mapper;
	
	/**
	 * stage=0の全ての性格情報を取得する.
	 * 
	 * @return
	 */
	public List<Personality> getPersonality(){
		PersonalityExample pe = new PersonalityExample();
		pe.createCriteria().andStageEqualTo("0");
		pe.setOrderByClause("personality_id");
		return mapper.selectByExample(pe);
	}
}
