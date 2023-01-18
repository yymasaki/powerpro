package common_sql;

import static com.ninja_squad.dbsetup.Operations.*;
import org.springframework.stereotype.Component;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class TemplateEngineerSkillTestSql {
	
	/** INSERT文 */
	public static final Operation TEMPLATE_ENGINEER_SKILLS_INSERT =
			insertInto("template_engineer_skills")
			.columns("template_engineer_skill_id","template_id","engineer_skill_id","score")
			.values(1,1,1,50).values(2,1,2,50).values(3,1,3,50).values(4,1,4,50).values(5,1,5,50).values(6,1,6,50).values(7,1,7,50)
			.values(8,2,1,70).values(9,2,2,70).values(10,2,3,70).values(11,2,4,70).values(12,2,5,70).values(13,2,6,70).values(14,2,7,70)
			.values(15,3,1,90).values(16,3,2,90).values(17,3,3,90).values(18,3,4,90).values(19,3,5,90).values(20,3,6,90)
			.build();
	
	/** DELETE文 */
	public static final Operation TEMPLATE_ENGINEER_SKILLS_DELETE = deleteAllFrom("template_engineer_skills");
	
	/** 主キー採番リセット用SQL */
	public static final Operation TEMPLATE_ENGINEER_SKILLS_AUTO_INCREMENT = sql("ALTER TABLE template_engineer_skills auto_increment = 1;");
}
