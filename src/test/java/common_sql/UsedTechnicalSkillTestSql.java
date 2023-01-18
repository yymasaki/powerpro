package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class UsedTechnicalSkillTestSql {
	
	/** テストデータINSERT用SQL */
	public static final Operation USED_TECHNICAL_SKILL_INSERT = 
			insertInto("used_technical_skills")
			.columns("used_technical_skill_id","dev_experience_id","technical_skill_id","stage")
			.values(1,1,1,0)
			.values(2,1,2,0)
			.values(3,2,1,0)
			.values(4,2,2,0)
			.build();
	
	/** テストデータDELETE用SQL */
	public static final Operation USED_TECHNICAL_SKILL_DELETE = deleteAllFrom("used_technical_skills");
	
	/** 主キー採番リセット用SQL */
	public static final Operation USED_TECHNICAL_SKILL_AUTO_INCREMENT = 
			Operations.sql("ALTER TABLE used_technical_skills auto_increment = 1;");

}
