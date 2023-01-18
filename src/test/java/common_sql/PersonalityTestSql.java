package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

/**
 * 性格のテストSQL文
 * 
 * @author nonaka
 *
 */
@Component
public class PersonalityTestSql {
	
	public static final Operation PERSONALITY_INSERT =
			sequenceOf(
	            insertInto("personalities")
	                .columns("personality_id", "name", "type", "explanation", "stage", "creator", "created_at", "updater", "updated_at", "version")
	                .values(1, "短気", "r", "エラーが解決できないとすぐイライラします", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(2, "遅刻魔", "r", "時間を守るのが苦手で、よく遅刻します", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(3, "調べ上手", "b", "適切なワードで検索し、ヒントを見つけるのが早いです", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(4, "質問◎", "b", "質問するのが上手です", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(5, "ムードメーカー", "g", "チームの雰囲気が良くなります", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(6, "綺麗好き", "g", "少しでも散らかっていると、集中できません", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(7, "気分屋", "b", "モチベーションにムラがあります", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .values(8, "ゆとり世代", "b", "理解不能な言動をすることもありますが、基本的に真面目です", "0", "テスト太郎", "2020-05-13 12:00:00.000000", "テスト太郎", "2020-05-13 12:00:00.000000", 1)
	                .build());

	public static final Operation PERSONALITY_DELETE = deleteAllFrom("personalities");
	public static final Operation PERSONALITY_AUTO_INCREMENT = sql("ALTER TABLE personalities AUTO_INCREMENT = 1");
}
