package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sql;

import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class InformationDepartmentLinkTestSql {

	public static final Operation INFORMATION_DEPARTMENT_LINK_DELETE = deleteAllFrom("informations_department_link");
	public static final Operation INFORMATION_DEPARTMENT_LINK_AUTO_INCREMENT = sql("ALTER TABLE `informations_department_link` auto_increment = 1");
	public static final Operation INFORMATION_DEPARTMENT_LINK_INSERT =
			insertInto("informations_department_link")
					.columns("informations_department_link_id", "information_id", "department_id")
					.values(1, 1, 4).values(2, 2, 1).values(3, 2, 2).values(4, 2, 3).values(5, 2, 4).values(6, 3, 4)
					.values(7, 3, 5).values(8, 4, 1).values(9, 4, 2).values(10, 4, 3).values(11, 4, 4).values(12, 4, 5)
					.values(13, 5, 1).values(14, 5, 2).values(15, 5, 3).values(16, 5, 4).values(17, 5, 5)
					.values(20, 6, 1).values(21, 6, 2).values(22, 6, 3).values(23, 6, 4).values(24, 6, 5)
					.values(25, 7, 1).values(26, 7, 2).values(27, 7, 3).values(28, 7, 4).values(29, 7, 5)
					.values(30, 8, 1).values(31, 8, 2).values(32, 8, 3).values(33, 8, 4).values(34, 8, 5)
					.build();
	
}
