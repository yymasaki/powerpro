package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class PresentationFavoriteTestSql {
    

    /** テストデータpresentation_favoritesテーブルINSERT用SQL */
    public static final Operation PRESENTATION_FAVORITE_INSERT = 
    insertInto("presentation_favorites")
    .columns("presentation_favorite_id", "user_id", "presentation_id")
    .values(1, 1, 1)
    .values(2, 2, 1)
    .values(3, 3, 1)
    .values(4, 1, 2)
    .values(5, 2, 2)
    .values(6, 3, 2)
    .values(7, 1, 2)
    .build();

     /** テストデータDELETE用SQL */
	public static final Operation PRESENTATION_FAVORITE_DELETE = deleteAllFrom("presentation_favorites");

    /** 主キー採番リセット用SQL */
	public static final Operation PRESENTATION_FAVORITE_AUTO_INCREMENT = 
    Operations.sql("ALTER TABLE presentation_favorites auto_increment = 1;");
}
