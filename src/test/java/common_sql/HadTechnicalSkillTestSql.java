package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class HadTechnicalSkillTestSql {

	public static final Operation HAD_TECHNICAL_SKILL_DELETE=deleteAllFrom("had_technical_skills");
	public static final Operation HAD_TECHNICAL_SKILL_AUTO_INCREMENT = sql("ALTER TABLE `had_technical_skills` auto_increment = 1");
	public static final Operation HAD_TECHNICAL_SKILL_INSERT=
			insertInto("had_technical_skills")
			.columns("had_technical_skill_id","user_id","technical_skill_id","using_period","stage","creator", "created_at", "updater","updated_at")
			.values(1,1,1,5,0,"Webエンジニア次郎","2020-07-02 12:00:00.000000","Webエンジニア次郎","2020-07-02 12:00:00.000000")
			.values(2,1,2,5,0,"Webエンジニア次郎","2020-07-02 12:00:00.000000","Webエンジニア次郎","2020-07-02 12:00:00.000000")
			.values(3,1,3,5,0,"Webエンジニア次郎","2020-07-02 12:00:00.000000","Webエンジニア次郎","2020-07-02 12:00:00.000000")
			.build();
}
