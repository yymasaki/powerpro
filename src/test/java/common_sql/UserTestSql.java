package common_sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class UserTestSql {

	/** 主キー採番リセット用SQL */
	public static final Operation USER_AUTO_CREMENT= Operations.sql(
			"ALTER TABLE users auto_increment = 1;");

	/** テストデータINSERT用SQL */
	public final static Operation USER_INSERT=Operations.insertInto("users")
			.columns("name", "hired_on", "department_id", "email", "password", "stage",
					"updated_password_at", "creator", "created_at", "updater", "updated_at", "version")
			// 本ユーザーinsert
			.values("JUnitテスト太郎", "2020-01-01", 1, "junit.taro", "password", 0, "2020-05-13",
					"テスト太郎", "2020-05-13 12:00:00", "テスト太郎", "2020-05-13 12:00:00", 1)
			// 仮ユーザーinsert
			.values("JUnitテスト次郎", "2020-04-01", 1, "junit.ziro", "password", 1, "2020-05-13",
					"テスト太郎", "2020-05-13 12:00:00", "テスト太郎", "2020-05-13 12:00:00", 1)
			// 本ユーザーinsert
			.values("JUnitテスト三郎", "2019-01-01", 1, "junit.saburo", "password", 0, "2020-05-13",
					"テスト太郎", "2020-05-13 12:00:00", "テスト太郎", "2020-05-13 12:00:00", 1)
			// 本ユーザーinsert
			.values("JUnitテスト四郎", "2018-01-01", 1, "junit.shiro", "password", 0, "2020-05-13",
					"テスト太郎", "2020-05-13 12:00:00", "テスト太郎", "2020-05-13 12:00:00", 1)
			.values("JUnitテスト管理者1", "2018-01-01", 5, "junit.kanrisya1", "password", 0, "2020-05-13",
					"テスト太郎", "2020-05-13 12:00:00", "テスト太郎", "2020-05-13 12:00:00", 1)
			.build();
	
	
	/** テストデータDELETE用SQL */
	public static final Operation USER_DELETE=Operations.deleteAllFrom("users");
}
