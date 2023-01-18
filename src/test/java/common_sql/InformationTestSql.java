package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class InformationTestSql {
	
	public static final Operation INFORMATION_DELETE = deleteAllFrom("informations");
	public static final Operation INFORMATION_AUTO_INCREMENT = sql("ALTER TABLE `informations` auto_increment = 1");
	
	public static Operation INFORMATION_INSERT =
			insertInto("informations")
					.columns("information_id", "create_user_id", "title", "content", "start_posted_on", "end_posted_on",
							"stage")
					.values(1, 1, "管理者用サンプルタイトル", "管理者用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(2, 1, "エンジニア、管理者用サンプルタイトル", "エンジニア、管理者用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(3, 1, "営業、管理者用サンプルタイトル", "営業、管理者用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(4, 1, "全部署用サンプルタイトル", "全部署用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(5, 1, "全部署用サンプルタイトル", "全部署用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(6, 1, "全部署用サンプルタイトル", "全部署用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(7, 1, "全部署用サンプルタイトル", "全部署用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.values(8, 1, "全部署用サンプルタイトル", "全部署用サンプル本文", "2020-06-20", "2024-12-30", "0")
					.build();

}
