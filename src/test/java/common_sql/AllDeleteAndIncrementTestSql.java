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
	 * 19????????????????????????????????????????????????????????????????????????.
	 * 
	 */
	@SuppressWarnings("static-access")
	public static final Operation ALLDELETE_AND_INCREMENT=Operations.sequenceOf(
			//template_basic_skills????????????
			templateBasicSkillTestSql.TEMPLATE_BASIC_SKILLS_DELETE,
			templateBasicSkillTestSql.TEMPLATE_BASIC_SKILLS_AUTO_INCREMENT,		
			//template_engineer_skills????????????
			templateEngineerSkillTestSql.TEMPLATE_ENGINEER_SKILLS_DELETE,
			templateEngineerSkillTestSql.TEMPLATE_ENGINEER_SKILLS_AUTO_INCREMENT,
			//templates????????????
			templateTestSql.TEMPLATES_DELETE,
			templateTestSql.TEMPLATES_AUTO_INCREMENT,
			//used_technical_skills????????????
			usedTechnicalSkillTestSql.USED_TECHNICAL_SKILL_DELETE,
			usedTechnicalSkillTestSql.USED_TECHNICAL_SKILL_AUTO_INCREMENT,
			//dev_experiences????????????
			devExperienceTestSql.DEV_EXPERIENCE_DELETE,
			devExperienceTestSql.DEV_EXPERIENCE_AUTO_INCREMENT,
			//app_specsheets????????????
			appSpecsheetTestSql.APP_SPECSHEET_DELETE,
			appSpecsheetTestSql.APP_SPECSHEET_AUTO_INCREMENT,
			//had_technical_skills????????????
			hadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_DELETE,
			hadTechnicalSkillTestSql.HAD_TECHNICAL_SKILL_AUTO_INCREMENT,
			////technical_skills????????????
			technicalSkillTestSql.TECHNICAL_SKILL_DELETE,
			technicalSkillTestSql.TECHNICAL_SKILL_AUTO_INCREMENT,
			//had_personalities????????????		
			hadPersonalityTestSql.HAD_PERSONALITIES_DELETE,
			hadPersonalityTestSql.HAD_PERSONALITIES_AUTO_INCREMENT,
			//personalities????????????
			personalityTestSql.PERSONALITY_DELETE,
			personalityTestSql.PERSONALITY_AUTO_INCREMENT,
			//had_basic_skills????????????
			hadBasicSkillTestSql.HAD_BASIC_SKILL_DELETE,
			hadBasicSkillTestSql.HAD_BASIC_SKILL_AUTO_INCREMENT,
			//basic_skills????????????
			basicSkillTestSql.BASIC_SKILL_DELETE,
			basicSkillTestSql.BASIC_SKILL_AUTO_INCREMENT,
			//had_engineer_skills????????????
			hadEngineerSkillTestSql.HAD_ENGINEER_SKILL_DELETE,
			hadEngineerSkillTestSql.HAD_ENGINEER_SKILL_AUTO_INCREMENT,
			//engineer_skills????????????
			engineerSkillTestSql.ENGINEER_SKILL_DELETE,
			engineerSkillTestSql.ENGINEER_SKILL_AUTO_INCREMENT,
			//statuses????????????
			statusTestSql.STATUS_DELETE,
			statusTestSql.STATUS_AUTO_INCREMENT,
			//informations_department_link????????????
			information_department_linkTestSql.INFORMATION_DEPARTMENT_LINK_DELETE,
			information_department_linkTestSql.INFORMATION_DEPARTMENT_LINK_AUTO_INCREMENT,
			//informations????????????
			informationTestSql.INFORMATION_DELETE,
			informationTestSql.INFORMATION_AUTO_INCREMENT,
			//users????????????
			userTestSql.USER_DELETE,
			userTestSql.USER_AUTO_CREMENT,
			//departmentls????????????
			departmentTestSql.DEPARTMENT_DELETE,
			departmentTestSql.DEPARTMENT_AUTO_INCREMENT,
			// presentation????????????
			presentationTestSql.PRESENTATION_DELETE,
			presentationTestSql.PRESENTATION_AUTO_INCREMENT,
			// presentationComments????????????
			presentationCommentsTestSql.PRESENTATION_COMMENTS_DELETE,
			presentationCommentsTestSql.PRESENTATION_COMMENTS_AUTO_INCREMENT,
			// presentationDocuments????????????
			presentationDocumentsTestSql.PRESENTATION_DOCUMENTS_DELETE,
			presentationDocumentsTestSql.PRESENTATION_DOCUMENTS_AUTO_INCREMENT,
			// presentationFavorite????????????
			presentationFavoriteTestSql.PRESENTATION_FAVORITE_DELETE,
			presentationFavoriteTestSql.PRESENTATION_FAVORITE_AUTO_INCREMENT,
			// presentationUsersLink????????????
			presentationUsersLinkTestSql.PRESENTATION_USERSLINK_DELETE,
			presentationUsersLinkTestSql.PRESENTATION_USERSLINK_AUTO_INCREMENT,
			// editRequestComments????????????
			editRequestCommentsTestSql.EDIT_REQUEST_COMMENTS_DELETE,
			editRequestCommentsTestSql.EDIT_REQUEST_COMMENTS_AUTO_INCREMENT
			);
}
