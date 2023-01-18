package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class HadBasicSkillTestSql {
	
	public static final Operation HAD_BASIC_SKILL_INSERT =
	        sequenceOf(
	        	insertInto("had_basic_skills")
	                .columns("had_basic_skill_id", "status_id", "basic_skill_id", "score")
	                .values(1, 1, 1, "3")
	                .values(2, 1, 2, "3")
	                .values(3, 1, 3, "3")
	                .values(4, 1, 4, "3")
	                .values(5, 1, 5, "3")
	                .values(6, 1, 6, "3")
	                .build());
	
	public static final Operation HAD_BASIC_SKILL_DELETE = deleteAllFrom("had_basic_skills");
	public static final Operation HAD_BASIC_SKILL_AUTO_INCREMENT = sql("ALTER TABLE had_basic_skills AUTO_INCREMENT = 1");
}
