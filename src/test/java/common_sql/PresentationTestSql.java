package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class PresentationTestSql {

    /** テストデータINSERT用SQL */
    public static final Operation PRESENTATION_INSERT = insertInto("presentations")
            .columns("title", "content", "preferred_date", "presentation_date", "num_favorite", 
            		"num_expectation", "stage", "creator_user_id", "created_at", 
                     "updater_user_id", "version")
            .values( "パワプロエンジニアテスト発表サンプル",
                    "・エンジニアのスキルを可視化するシステム。・社内での利用を想定。・スペックシートをシステム内でしてExcel出力でき、今後PDF化にも対応する予定。現状Excelシートを入力してPDF化し、メールで添付送信する形式からシンプルなフローに移行できる。・管理者、営業はユーザー一覧からエンジニア情報を閲覧することができ、必要なスキルをもったエンジニアを探し出す事ができる。",
                    "11月下旬", "2022-11-25", null, null, "0", 1, "2022-10-31 12:00:00", 1, 1)
            .values("発表サンプル2",
                    "・エンジニアのスキルを可視化するシステム。・社内での利用を想定。・スペックシートをシステム内でしてExcel出力でき、今後PDF化にも対応する予定。現状Excelシートを入力してPDF化し、メールで添付送信する形式からシンプルなフローに移行できる。・管理者、営業はユーザー一覧からエンジニア情報を閲覧することができ、必要なスキルをもったエンジニアを探し出す事ができる。",
                    "11月下旬", "2022-11-25", null, null, "2", 1, "2022-10-31 12:00:00", 1, 1)
            .values("発表サンプル3",
                    "・エンジニアのスキルを可視化するシステム。・社内での利用を想定。・スペックシートをシステム内でしてExcel出力でき、今後PDF化にも対応する予定。現状Excelシートを入力してPDF化し、メールで添付送信する形式からシンプルなフローに移行できる。・管理者、営業はユーザー一覧からエンジニア情報を閲覧することができ、必要なスキルをもったエンジニアを探し出す事ができる。",
                    "11月下旬", "2022-11-25", null, null, "3", 1, "2022-10-31 12:00:00", 1, 1)
            .build();


    /** テストデータDELETE用SQL */
	public static final Operation PRESENTATION_DELETE = deleteAllFrom("presentations");

    /** 主キー採番リセット用SQL */
	public static final Operation PRESENTATION_AUTO_INCREMENT = 
    Operations.sql("ALTER TABLE presentations auto_increment = 1;");
    
}
