package common_sql;

import static com.ninja_squad.dbsetup.Operations.*;
import org.springframework.stereotype.Component;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class TemplateTestSql {
	
	/** INSERT文 */
	public static final Operation TEMPLATES_INSERT =
			insertInto("templates")
			.columns("template_id","name","department_id","stage","creator","created_at","updater","updated_at","version")
			.values(1,"テストテンプレート1",1,0,"Webエンジニア次郎","2020-07-02 12:00:00.000000","Webエンジニア次郎","2020-07-02 12:00:00.000000",1)
			.values(2,"テストテンプレート2",1,0,"Webエンジニア次郎","2020-07-02 12:00:00.000000","Webエンジニア次郎","2020-07-02 12:00:00.000000",1)
			.values(3,"テストテンプレート3",1,0,"Webエンジニア次郎","2020-07-02 12:00:00.000000","Webエンジニア次郎","2020-07-02 12:00:00.000000",1)
			.build();

	/** DELETE文 */
	public static final Operation TEMPLATES_DELETE = deleteAllFrom("templates");
	
	/** 主キー採番リセット用SQL */
	public static final Operation TEMPLATES_AUTO_INCREMENT = sql("ALTER TABLE templates auto_increment = 1;");
}
