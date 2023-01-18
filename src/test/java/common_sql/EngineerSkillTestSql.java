package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class EngineerSkillTestSql {
	
	public static final Operation ENGINEER_SKILL_INSERT =
	        sequenceOf(
	        	Operations.sql("SET FOREIGN_KEY_CHECKS = 0"),
	            insertInto("engineer_skills")
	                .columns("engineer_skill_id", "name", "department_id", "stage", "creator", "created_at", "updater", "updated_at", "version")
	                .values(1, "プログラム", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(2, "データベース", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(3, "ネットワーク", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(4, "OS", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(5, "ハードウェア", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(6, "セキュリティ", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(7, "ミドルウェア", 1, 0, "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .build());
	
	public static final Operation ENGINEER_SKILL_DELETE = deleteAllFrom("engineer_skills");
	public static final Operation ENGINEER_SKILL_AUTO_INCREMENT = sql("ALTER TABLE engineer_skills AUTO_INCREMENT = 1");
}
