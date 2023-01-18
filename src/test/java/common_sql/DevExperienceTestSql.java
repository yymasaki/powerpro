package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class DevExperienceTestSql {
	
	/** テストデータINSERT用SQL */
	public static final Operation DEV_EXPERIENCE_INSERT = 
			insertInto("dev_experiences")
			.columns("dev_experience_id","specsheet_id","project_name","started_on","finished_on",
					"team_members","project_members","role","project_details","tasks","acquired","stage")
			.values(1,1,"テストプロジェクト","2020-01-01","2020-02-01",3,5,"PG","詳細","タスク","知見",0)
			.values(2,1,"テストプロジェクト2","2020-05-01","2020-06-01",3,5,"PG","詳細","タスク","知見",0)
			.values(3,1,"テストプロジェクト3","2020-02-01","2020-06-01",3,5,"PG","詳細","タスク","知見",0)
			.values(4,1,"テストプロジェクト4","2020-02-01","2020-04-01",3,5,"PG","詳細","タスク","知見",0)
			.values(5,1,"テストプロジェクト5","2020-03-01","2020-04-01",3,5,"PG","詳細","タスク","知見",0)
			.values(6,1,"テストプロジェクト6","2020-05-01","2020-07-01",3,5,"PG","詳細","タスク","知見",0)
			.values(7,1,"テストプロジェクト7","2020-04-01","2020-04-01",3,5,"PG","詳細","タスク","知見",0)
			.values(8,1,"テストプロジェクト8","2020-02-01","2020-06-01",3,5,"PG","詳細","タスク","知見",0)
			.values(9,1,"テストプロジェクト9","2020-07-01","2020-07-01",3,5,"PG","詳細","タスク","知見",0)
			.values(10,1,"テストプロジェクト10","2020-03-01","2020-07-01",3,5,"PG","詳細","タスク","知見",0)
			.values(11,2,"テストプロジェクト","2020-01-01","2020-02-01",3,5,"PG","詳細","タスク","知見",0)
			.values(12,2,"テストプロジェクト2","2020-05-01","2020-06-01",3,5,"PG","詳細","タスク","知見",0)
			.values(13,2,"テストプロジェクト3","2020-02-01","2020-06-01",3,5,"PG","詳細","タスク","知見",0)
			.values(14,2,"テストプロジェクト4","2020-02-01","2020-04-01",3,5,"PG","詳細","タスク","知見",0)
			.values(15,2,"テストプロジェクト5","2020-03-01","2020-04-01",3,5,"PG","詳細","タスク","知見",0)
			.values(16,2,"テストプロジェクト6","2020-05-01","2020-07-01",3,5,"PG","詳細","タスク","知見",0)
			.values(17,2,"テストプロジェクト7","2020-04-01","2020-04-01",3,5,"PG","詳細","タスク","知見",0)
			.values(18,2,"テストプロジェクト8","2020-02-01","2020-06-01",3,5,"PG","詳細","タスク","知見",0)
			.values(19,2,"テストプロジェクト9","2020-07-01","2020-07-01",3,5,"PG","詳細","タスク","知見",0)
			.values(20,2,"テストプロジェクト10","2020-03-01","2020-07-01",3,5,"PG","詳細","タスク","知見",0)
			.build();
	
	/** テストデータDELETE用SQL */
	public static final Operation DEV_EXPERIENCE_DELETE = deleteAllFrom("dev_experiences");
	
	/** 主キー採番リセット用SQL */
	public static final Operation DEV_EXPERIENCE_AUTO_INCREMENT = 
			Operations.sql("ALTER TABLE dev_experiences auto_increment = 1;");

}
