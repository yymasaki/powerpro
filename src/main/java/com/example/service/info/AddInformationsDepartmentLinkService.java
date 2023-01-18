package com.example.service.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.InformationsDepartmentLink;
import com.example.mapper.InformationsDepartmentLinkMapper;

@Service
@Transactional
public class AddInformationsDepartmentLinkService {

	@Autowired
	private InformationsDepartmentLinkMapper informationsDepartmentLinkMapper;
	
	/**
	 * 
	 * お知らせと部署関係を登録する
	 * 
	 * @param informationsDepartmentLink　お知らせと部署関係
	 */
	public void addInformationsDepartmentLink(List<InformationsDepartmentLink> informationsDepartmentLinkList) {
		informationsDepartmentLinkMapper.insert(informationsDepartmentLinkList);
	}
	
}
