package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class HadPersonalityTestSql {
	
	public static final Operation HAD_PERSONALITIES_INSERT=
	        sequenceOf(
	        	insertInto("had_personalities")
	                .columns("had_personality_id", "status_id", "personality_id", "stage")
	                .values(1, 1, 1, "0")
	                .values(2, 1, 3, "0")
	                .values(3, 1, 5, "0")
	                .values(4, 1, 7, "0")
	                .build());
	        
	public static final Operation HAD_PERSONALITIES_DELETE = deleteAllFrom("had_personalities");
	public static final Operation HAD_PERSONALITIES_AUTO_INCREMENT = deleteAllFrom("had_personalities");	
}
