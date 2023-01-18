package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Template;
import com.example.example.TemplateExample;

@Mapper
public interface TemplateMapper {
	
	/**
	 * テンプレートidで検索する.
	 * 
	 * @param templateId テンプレートid
	 * @return テンプレート（1件）
	 */
	List<Template> selectByTemplateId(Integer templateId);
	
    int countByExample(TemplateExample example);

    int deleteByExample(TemplateExample example);

    int deleteByPrimaryKey(Integer templateId);

    int insert(Template record);

    int insertSelective(Template record);
    
    /**
     * templateをインサートし、自動採番された主キーを取得する.
     * 
     * @param template テンプレート
     * @return 自動採番されたtemplateId
     */
    int insertReturnId(Template template);
    
    /**
     * テンプレート選択画面のセレクトボックス表示のためのテンプレートリストを取得する.
     * 
     * @param example 
     * @return 管理者 → 全テンプレート
	 * 　　　　 エンジニア → 自身の所属に合致するテンプレート
     */
    List<Template> selectByExample(TemplateExample example);

    Template selectByPrimaryKey(Integer templateId);

    int updateByExampleSelective(@Param("record") Template record, @Param("example") TemplateExample example);

    int updateByExample(@Param("record") Template record, @Param("example") TemplateExample example);

    int updateByPrimaryKeySelective(Template record);

    int updateByPrimaryKey(Template record);
}