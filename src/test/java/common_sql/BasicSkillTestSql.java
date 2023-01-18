package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class BasicSkillTestSql {
	
	/**
	 * 基本スキルのインサート文 
	 */
	public static final Operation BASIC_SKILL_INSERT =
	        sequenceOf(
	        	Operations.sql("SET FOREIGN_KEY_CHECKS = 0"),
	            insertInto("basic_skills")
	                .columns("basic_skill_id", "name", "department_id", "stage", "creator", "created_at", "updater", "updated_at", "version")
	                .values(1, "サンプル1", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(2, "サンプル2", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(3, "サンプル3", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(4, "サンプル4", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(5, "サンプル5", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(6, "サンプル6", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .build());
	
	/**
	 * 基本スキル削除文
	 */
	public static final Operation BASIC_SKILL_DELETE = deleteAllFrom("basic_skills");
	public static final Operation BASIC_SKILL_AUTO_INCREMENT = sql("ALTER TABLE basic_skills AUTO_INCREMENT = 1");
}
