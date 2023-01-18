package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class AppSpecsheetTestSql {
	
	/** テストデータINSERT用SQL */
	public static final Operation APP_SPECSHEET_INSERT = 
			insertInto("app_specsheets")
			.columns("specsheet_id","user_id","employee_id","generation","gender","nearest_station",
					"start_business_date","engineer_period","se_period","pg_period","it_period","appeal",
					"effort","certification","pre_job","admin_comment","stage","creator","created_at",
					"updater","updated_at","version")
			.values(1,1,1111,"20代前半","M","新宿駅","応相談",10,3,7,15,"アピール","エフォート","資格","前職","コメント",
					0,"tester","2020-07-07 11:11:11","tester","2020-07-07 11:11:11",1)
			.values(2,1,1111,"20代前半","M","新宿駅","応相談",10,3,7,15,"アピール","エフォート","資格","前職","コメント",
					0,"tester","2020-07-07 22:22:22","tester","2020-07-07 22:22:22",2)
			.values(3,2,2222,"20代前半","M","新宿駅","応相談",10,3,7,15,"アピール","エフォート","資格","前職","コメント",
					0,"tester","2020-07-07 11:11:11","tester","2020-07-07 11:11:11",1)
			.build();
	
	/** テストデータDELETE用SQL */
	public static final Operation APP_SPECSHEET_DELETE = deleteAllFrom("app_specsheets");
	
	/** 主キー採番リセット用SQL */
	public static final Operation APP_SPECSHEET_AUTO_INCREMENT = 
			Operations.sql("ALTER TABLE app_specsheets auto_increment = 1;");

}
