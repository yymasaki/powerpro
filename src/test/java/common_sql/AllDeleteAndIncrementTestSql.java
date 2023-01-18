package common_sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class AllDeleteAndIncrementTestSql {
	
	@Autowired
	private static HadTechnicalSkillTestSql hadTechnicalSkillTestSql;
	
	@Autowired
	private static TechnicalSkillTestSql technicalSkillTestSql;
	
	@Autowired
	private static InformationDepartmentLinkTestSql information_department_linkTestSql;
	
	@Autowired
	private static InformationTestSql informationTestSql;
	
	@Autowired
	private static UserTestSql userTestSql;
	
	@Autowired
	private static DepartmentTestSql departmentTestSql;
	
	@Autowired
	private static DevExperienceTestSql devExperienceTestSql;
	
	@Autowired
	private static AppSpecsheetTestSql appSpecsheetTestSql;
	
	@Autowired
	private static UsedTechnicalSkillTestSql usedTechnicalSkillTestSql;
	
	@Autowired
	private static HadPersonalityTestSql hadPersonalityTestSql;
	
	@Autowired
	private static PersonalityTestSql personalityTestSql;
	
	@Autowired
	private static HadBasicSkillTestSql hadBasicSkillTestSql;
	
	@Autowired
	private static BasicSkillTestSql basicSkillTestSql;
	
	@Autowired
	private static HadEngineerSkillTestSql hadEngineerSkillTestSql;
	
	@Autowired
	private static EngineerSkillTestSql engineerSkillTestSql;
	
	@Autowired
	private static StatusTestSql statusTestSql;
	
	@Autowired
	private static TemplateTestSql templateTestSql;
	
	@Autowired
	private static TemplateBasicSkillTestSql templateBasicSkillTestSql;
	
	@Autowired
	private static TemplateEngineerSkillTestSql templateEngineerSkillTestSql;

	@Autowired
	private static PresentationTestSql presentationTestSql;

	@Autowired
	private static PresentationCommentsTestSql presentationCommentsTestSql;

	@Autowired
	private static PresentationDocumentsTestSql presentationDocumentsTestSql;
	
	@Autowired
	private static PresentationFavoriteTestSql presentationFavoriteTestSql;
	
	@Autowired
	private static PresentationUsersLinkTestSql presentationUsersLinkTestSql;
	
	@Autowired
	private static EditRequestCommentsTestSql editRequestCommentsTestSql;


	/**
	 * 19テーブル全てのデータを削除し、自走採番をリセット.
	 * 
	 */
	@SuppressWarnings("static-access")
	public static final Operation ALLDELETE_AND_INCREMENT=Operations.sequenceOf(
			//template_basic_skillsテーブル
			templateBasicSkillTestSql.TEMPLATE_BASIC_SKILLS_DELETE,
			templateBasicSkillTestSql.TEMPLATE_BASIC_SKILLS_AUTO_INCREMENT,		
			//template_engineer_skillsテーブル
			templateEngineerSkillTestSql.TEMPLATE_ENGINEER_SKILLS_DELETE,
			templateEngineerSkillTestSql.TEMPLATE_ENGINEER_SKILLS_AUTO_INCREMENT,
			//templatesテーブル
			templateTestSql.TEMPLATES_DELETE,
			templateTestSql.TEMPLATES_AUTO_INCREMENT,
			//used_technical_skillsテーブル
			usedTechnicalSkillTestSql.USED_TECHNICAL_SKILL_DELETE,
			usedTechnicalSkillTestSql.USED_TECHNICAL_SKILL_AUTO_INCREMENT,
			//dev_experiencesテーブル
			devExperienceTestSql.DEV_EXPERIENCE_DELETE,
			devExperienceTestSql.DEV_EXPERIENCE_AUTO_INCREMENT,
			//app_specsheetsテーブル
			appSpecsheetTestSql.APP_SPECSHEET_DELETE,
			appSpecsheetTestSql.APP_SPECSHEET_AUTO_INCREMENT,
			//had_technical_skillsテーブル
			hadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_DELETE,
			hadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_AUTO_INCREMENT,
			////technical_skillsテーブル
			technicalSkillTestSql.TECHNICAL_SKILL_DELETE,
			technicalSkillTestSql.TECHNICAL_SKILL_AUTO_INCREMENT,
			//had_personalitiesテーブル		
			hadPersonalityTestSql.HAD_PERSONALITIES_DELETE,
			hadPersonalityTestSql.HAD_PERSONALITIES_AUTO_INCREMENT,
			//personalitiesテーブル
			personalityTestSql.PERSONALITY_DELETE,
			personalityTestSql.PERSONALITY_AUTO_INCREMENT,
			//had_basic_skillsテーブル
			hadBasicSkillTestSql.HAD_BASIC_SKILL_DELETE,
			hadBasicSkillTestSql.HAD_BASIC_SKILL_AUTO_INCREMENT,
			//basic_skillsテーブル
			basicSkillTestSql.BASIC_SKILL_DELETE,
			basicSkillTestSql.BASIC_SKILL_AUTO_INCREMENT,
			//had_engineer_skillsテーブル
			hadEngineerSkillTestSql.HAD_ENGINEER_SKILL_DELETE,
			hadEngineerSkillTestSql.HAD_ENGINEER_SKILL_AUTO_INCREMENT,
			//engineer_skillsテーブル
			engineerSkillTestSql.ENGINEER_SKILL_DELETE,
			engineerSkillTestSql.ENGINEER_SKILL_AUTO_INCREMENT,
			//statusesテーブル
			statusTestSql.STATUS_DELETE,
			statusTestSql.STATUS_AUTO_INCREMENT,
			//informations_department_linkテーブル
			information_department_linkTestSql.INFORMATION_DEPARTMENT_LINK_DELETE,
			information_department_linkTestSql.INFORMATION_DEPARTMENT_LINK_AUTO_INCREMENT,
			//informationsテーブル
			informationTestSql.INFORMATION_DELETE,
			informationTestSql.INFORMATION_AUTO_INCREMENT,
			//usersテーブル
			userTestSql.USER_DELETE,
			userTestSql.USER_AUTO_CREMENT,
			//departmentlsテーブル
			departmentTestSql.DEPARTMENT_DELETE,
			departmentTestSql.DEPARTMENT_AUTO_INCREMENT,
			// presentationテーブル
			presentationTestSql.PRESENTATION_DELETE,
			presentationTestSql.PRESENTATION_AUTO_INCREMENT,
			// presentationCommentsテーブル
			presentationCommentsTestSql.PRESENTATION_COMMENTS_DELETE,
			presentationCommentsTestSql.PRESENTATION_COMMENTS_AUTO_INCREMENT,
			// presentationDocumentsテーブル
			presentationDocumentsTestSql.PRESENTATION_DOCUMENTS_DELETE,
			presentationDocumentsTestSql.PRESENTATION_DOCUMENTS_AUTO_INCREMENT,
			// presentationFavoriteテーブル
			presentationFavoriteTestSql.PRESENTATION_FAVORITE_DELETE,
			presentationFavoriteTestSql.PRESENTATION_FAVORITE_AUTO_INCREMENT,
			// presentationUsersLinkテーブル
			presentationUsersLinkTestSql.PRESENTATION_USERSLINK_DELETE,
			presentationUsersLinkTestSql.PRESENTATION_USERSLINK_AUTO_INCREMENT,
			// editRequestCommentsテーブル
			editRequestCommentsTestSql.EDIT_REQUEST_COMMENTS_DELETE,
			editRequestCommentsTestSql.EDIT_REQUEST_COMMENTS_AUTO_INCREMENT
			);
}
