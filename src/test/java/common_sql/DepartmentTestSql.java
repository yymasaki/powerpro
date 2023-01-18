package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class DepartmentTestSql {
	
	/**departmentsテーブルにテストデータを挿入する*/	
	public static final Operation DEPARTMENT_INSERT = insertInto("departments")
			.columns("department_id","name","staff_id","stage","creator", "created_at", "updater","updated_at","version")
			.values(1,"Webエンジニア","AP-202",0,"テスト太郎","2020-05-13 12:00:00.000000","テスト太郎","2020-05-13 12:00:00.000000",1)
			.values(2,"インフラエンジニア","CL-202",0,"テスト太郎","2020-05-13 12:00:00.000000","テスト太郎","2020-05-13 12:00:00.000000",1)
			.values(3,"機械学習エンジニア","ML-202",0,"テスト太郎","2020-05-13 12:00:00.000000","テスト太郎","2020-05-13 12:00:00.000000",1)
			.values(4,"営業","sales",0,"テスト太郎","2020-05-13 12:00:00.000000","テスト太郎","2020-05-13 12:00:00.000000",1)
			.values(5,"管理者","admin",0,"テスト太郎","2020-05-13 12:00:00.000000","テスト太郎","2020-05-13 12:00:00.000000",1)
			.values(9,"仮ユーザー","pending",0,"テスト太郎","2020-05-13 12:00:00.000000","テスト太郎","2020-05-13 12:00:00.000000",1)
			.build();
	
	/**departmentsテーブルを全件削除*/
	public static final Operation DEPARTMENT_DELETE = deleteAllFrom("departments");
	
	/**departmentsテーブルの自動採番をリセット*/
	public static final Operation DEPARTMENT_AUTO_INCREMENT = Operations.sql("ALTER TABLE `departments` auto_increment = 1;");
	
	

}
