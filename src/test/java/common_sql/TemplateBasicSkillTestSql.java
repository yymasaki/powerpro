package common_sql;

import static com.ninja_squad.dbsetup.Operations.*;
import org.springframework.stereotype.Component;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class TemplateBasicSkillTestSql {
	
	/** INSERT文 */
	public static final Operation TEMPLATE_BASIC_SKILLS_INSERT =
			insertInto("template_basic_skills")
			.columns("template_basic_skill_id","template_id","basic_skill_id","score")
			.values(1,1,1,1).values(2,1,2,2).values(3,1,3,3).values(4,1,4,3).values(5,1,5,4).values(6,1,6,5)
			.values(7,2,1,4).values(8,2,2,4).values(9,2,3,4).values(10,2,4,4).values(11,2,5,4).values(12,2,6,4)
			.values(13,3,1,5).values(14,3,2,5).values(15,3,3,5).values(16,3,4,5).values(17,3,5,5)
			.build();
			
	/** DELETE文 */
	public static final Operation TEMPLATE_BASIC_SKILLS_DELETE = deleteAllFrom("template_basic_skills");
	
	/** 主キー採番リセット用SQL */
	public static final Operation TEMPLATE_BASIC_SKILLS_AUTO_INCREMENT = sql("ALTER TABLE template_basic_skills auto_increment = 1;");
}
