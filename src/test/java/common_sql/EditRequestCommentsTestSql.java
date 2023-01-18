package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;


@Component
public class EditRequestCommentsTestSql {
    
     /** テストデータINSERT用SQL */
     public static final Operation EDIT_REQUEST_COMMENTS_INSERT =     
     insertInto("edit_request_comments")
     .columns("comment", "creator_user_id", "created_at", "presentation_id")
     .values("管理者からの修正依頼コメントテスト1", 5, "2022-10-31 12:00:00", 1)
     .values("管理者からの修正依頼コメントテスト2", 5, "2022-10-31 12:00:00", 1)
     .values("管理者からの修正依頼コメントテスト3", 5, "2022-10-31 12:00:00", 1)
     .values("管理者からの修正依頼コメントテスト1", 5, "2022-10-31 12:00:00", 2)
     .values("管理者からの修正依頼コメントテスト1", 5, "2022-10-31 12:00:00", 3)
     .build();
     
      /** テストデータDELETE用SQL */
	public static final Operation EDIT_REQUEST_COMMENTS_DELETE = deleteAllFrom("edit_request_comments");

    /** 主キー採番リセット用SQL */
	public static final Operation EDIT_REQUEST_COMMENTS_AUTO_INCREMENT = 
    Operations.sql("ALTER TABLE edit_request_comments auto_increment = 1;");
        
}
