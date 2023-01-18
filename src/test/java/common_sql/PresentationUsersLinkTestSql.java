package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class PresentationUsersLinkTestSql {

    /** テストデータINSERT用SQL */
    public static final Operation PRESENTATION_USERSLINK_INSERT = insertInto("presentation_users_link")
            .columns("presentation_id", "user_id")
            .values(1, 1).values(1, 2).values(1, 3)
            .values(2, 1).values(2, 2).values(2, 3)
            .values(3, 1).values(3, 2).values(3, 3)
            .build();

    /** テストデータDELETE用SQL */
    public static final Operation PRESENTATION_USERSLINK_DELETE = deleteAllFrom("presentation_users_link");

    /** 主キー採番リセット用SQL */
    public static final Operation PRESENTATION_USERSLINK_AUTO_INCREMENT = Operations
            .sql("ALTER TABLE presentation_users_link auto_increment = 1;");
}
