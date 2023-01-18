package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Presentation;

@Mapper
public interface RequestPresentationMapper {

	/**
	 * 応募状況画面を表示する
	 * 
	 * @return
	 */
	List<Presentation> requestPresentations();

	/**
	 * 応募一覧で検索する
	 * 
	 * @return
	 */
	List<Presentation> searchRequestPresentations(Presentation searchPresentation);

	/** 申請中(stage = 2)の発表会を取得 */
	Presentation selectRequestPresentation(@Param("presentationId") Integer presentationId);

	/** 該当発表会のstage、もしくは発表日を更新 */
	int updatePresentation(Presentation presentation);

}
