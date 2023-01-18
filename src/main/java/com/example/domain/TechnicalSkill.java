package com.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.common.Category;
import com.example.common.Stage;

import lombok.Data;

@Data
public class TechnicalSkill {

    /**テクニカルスキルID*/
    private Integer technicalSkillId;

    /**テクニカルスキル名*/
    private String name;

    /**カテゴリ名*/
    private Integer category;
    
	/**リクエストユーザーId*/
	private Integer requestUserId;
	
    /**コンディション*/
    private Integer stage;

    /**作成者*/
    private String creator;

    /**作成日*/
    private LocalDateTime createdAt;

    /**更新者*/
    private String updater;

    /**更新日*/
    private LocalDateTime updatedAt;

    /**バージョン番号*/
    private Integer version;
    
    /**ユーザ情報*/
    private User user;

	/**
	 * 文字列で申請状況を取得する（画面表示用）.
	 * @return 申請状況の文字列
	 */
	public String getStageString() {
		String stageString = "";
		if (Stage.ACTIVE.getKey().equals(stage)) {
			stageString = "承認済";
		} else if (Stage.REQUESTING.getKey().equals(stage)) {
			stageString = "未承認";
		} else if (Stage.DELETED.getKey().equals(stage)) {
			stageString = "否認";
		}
		return stageString;
	}
	
    /**
     * フォーマットされた申請日を取得（画面表示用）.
     * ユーザーが申請を編集することを考慮し、元データはupdatedAtを採用
     * 
     * @return フォーマットされた申請日
     */
    public String getApplicationDateString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd");
    	return formatter.format(updatedAt);
    }
    
    /**
     * 文字列でカテゴリを取得する.
     * @return　カテゴリ名
     */
    public String getCategoryName() {
    	String categoryName="";
    	if(Category.OS.getKey().equals(category)) {
    		categoryName="OS";
    	}else if(Category.LANGUAGE.getKey().equals(category)) {
    		categoryName="プログラミング言語";
    	}else if(Category.FRAMEWORK.getKey().equals(category)) {
    		categoryName="フレームワーク";
    	}else if(Category.LIBRARY.getKey().equals(category)) {
    		categoryName="ライブラリ";
    	}else if(Category.MIDDLEWARE.getKey().equals(category)) {
    		categoryName="ミドルウェア";
    	}else if(Category.TOOL.getKey().equals(category)) {
    		categoryName="ツール";
    	}else if(Category.PROCESS.getKey().equals(category)) {
    		categoryName="担当開発工程";
    	}
    	return categoryName;
    }

	public TechnicalSkill(Integer technicalSkillId,String name, Integer category, Integer requestUserId, Integer stage,
			String creator, LocalDateTime createdAt, String updater, LocalDateTime updatedAt, Integer version) {
		super();
		this.technicalSkillId=technicalSkillId;
		this.name = name;
		this.category = category;
		this.requestUserId = requestUserId;
		this.stage = stage;
		this.creator = creator;
		this.createdAt = createdAt;
		this.updater = updater;
		this.updatedAt = updatedAt;
		this.version = version;
	}
	
	public TechnicalSkill() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
    
    

}