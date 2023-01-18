package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class StatusTestSql {

	public static final Operation STATUS_INSERT =
			sequenceOf(
	            insertInto("statuses")
	                .columns("status_id","user_id","using_pc","using_mobile","stage","creator", "created_at", "updater","updated_at","version")
	                .values(1, 1, "mac", "iPhone", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(2, 2, "Windows", "Android", "1", "テスト次郎", "2020-05-13 12:00:00.000000", "テスト次郎", "2020-05-13 12:00:00.000000", 1)
	                .values(3, 3, "Linux", "iPhone", "2", "テスト三郎", "2020-05-13 12:00:00.000000", "テスト三郎", "2020-05-13 12:00:00.000000", 1)
	                .values(4, 4, "mac", "iPhone", "3", "テスト四郎", "2020-05-13 12:00:00.000000", "テスト三郎", "2020-05-13 12:00:00.000000", 1)
	                .build());

	public static final Operation STATUS_DELETE = deleteAllFrom("statuses");
	public static final Operation STATUS_AUTO_INCREMENT = sql("ALTER TABLE statuses AUTO_INCREMENT = 1");
}
