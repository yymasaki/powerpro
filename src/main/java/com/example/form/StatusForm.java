package com.example.form;

import java.util.List;
import com.example.domain.HadBasicSkill;
import com.example.domain.HadEngineerSkill;
import lombok.Data;

@Data
public class StatusForm {
	/** ステータスID */
	private Integer statusId;
	/** ユーザーID */
	private Integer userId;
    /** 使用PC */
    private String usingPc;
    /** 使用携帯 */
    private String usingMobile;
    /** ユーザーコメント */
    private String userComment;
    /** 管理者コメント */
    private String adminComment;
    /** ステージ */
    private Integer stage;
    /** 所有エンジニアスキルリスト */
    private List<HadEngineerSkill> hadEngineerSkillList;
    /** 所有基本スキルリスト */
    private List<HadBasicSkill> hadBasicSkillList;
    /** 所有性格リスト */
    private List<Integer> personalityIdList;
    /** 更新回数 */
    private Integer version;
}
