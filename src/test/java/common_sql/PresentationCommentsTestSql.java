package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class PresentationCommentsTestSql {
    
    /** テストデータpresentation_commentsテーブルINSERT用SQL */
    public static final Operation PRESENTATION_COMMENTS_INSERT =     
    insertInto("presentation_comments")
    .columns("comment", "creator_user_id", "created_at", "stage", "presentation_id")
    .values("エンジニアからのテストコメント1", 1, "2022-10-31 12:00:00", "0", 1)
    .values("エンジニアからのテストコメント2", 2, "2022-10-31 12:00:00", "0", 1)
    .values("エンジニアからのテストコメント3", 3, "2022-10-31 12:00:00", "0", 1)
    .values("エンジニアからのテストコメント1", 1, "2022-10-31 12:00:00", "0", 2)
    .values("エンジニアからのテストコメント2", 2, "2022-10-31 12:00:00", "0", 2)
    .values("エンジニアからのテストコメント1", 1, "2022-10-31 12:00:00", "0", 3)
    .values("エンジニアからのテストコメント2", 2, "2022-10-31 12:00:00", "0", 3)
    .build();

     /** テストデータDELETE用SQL */
	public static final Operation PRESENTATION_COMMENTS_DELETE = deleteAllFrom("presentation_comments");

    /** 主キー採番リセット用SQL */
	public static final Operation PRESENTATION_COMMENTS_AUTO_INCREMENT = 
    Operations.sql("ALTER TABLE presentation_comments auto_increment = 1;");
}
