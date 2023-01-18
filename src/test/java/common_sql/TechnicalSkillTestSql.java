package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class TechnicalSkillTestSql {
	
	/**technical_skillsテーブルにテストデータを挿入 */
	public static final Operation TECHNICAL_SKILL_INSERT = insertInto("technical_skills")
			.columns("technical_skill_id", "name", "category", "request_user_id", "stage", "creator", "created_at","updater", "updated_at", "version")
			.values(1, "testItem1", 1, 1, 2, "Webエンジニア次郎", "2020-07-02 12:00:00.000000", "Webエンジニア次郎","2020-07-02 12:00:00.000000", 1)
			.values(2, "testItem2", 2, 1, 2, "Webエンジニア次郎", "2020-07-02 12:00:00.000000", "Webエンジニア次郎","2020-07-02 12:00:00.000000", 1)
			.values(3, "testItem3", 3, 1, 2, "Webエンジニア次郎", "2020-07-02 12:00:00.000000", "Webエンジニア次郎","2020-07-02 12:00:00.000000", 1)
			.values(4, "testItem4", 3, 1, 2, "Webエンジニア次郎", "2020-07-02 12:00:00.000000", "Webエンジニア次郎","2020-07-02 12:00:00.000000", 1)
			.values(5, "testItem5", 3, 1, 2, "Webエンジニア次郎", "2020-07-02 12:00:00.000000", "Webエンジニア次郎","2020-07-02 12:00:00.000000", 1)
			.build();
	
	/**technical_skillsテーブルを全件削除*/
	public static final Operation TECHNICAL_SKILL_DELETE = deleteAllFrom("technical_skills");
	
	/**technical_skillsテーブルの自動採番をリセット*/
	public static final Operation TECHNICAL_SKILL_AUTO_INCREMENT = Operations.sql("ALTER TABLE `technical_skills` auto_increment = 1;");
	
	

}
