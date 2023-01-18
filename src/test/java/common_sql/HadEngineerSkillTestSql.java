package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class HadEngineerSkillTestSql {

	
	public static final Operation HAD_ENGINEER_SKILLS_INSERT=
	        sequenceOf(
	            insertInto("had_engineer_skills")
	                .columns("had_engineer_skill_id", "status_id", "engineer_skill_id", "score")
	                .values(1,1,1,90)
	                .values(2,1,2,50)
	                .values(3,1,3,40)
	                .values(4,1,4,40)
	                .values(5,1,5,60)
	                .values(6,1,6,50)
	                .values(7,1,7,60)
	                .build());
	
	public static final Operation HAD_ENGINEER_SKILL_DELETE = deleteAllFrom("had_engineer_skills");
	public static final Operation HAD_ENGINEER_SKILL_AUTO_INCREMENT = sql("ALTER TABLE had_engineer_skills AUTO_INCREMENT = 1");
}
