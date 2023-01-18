package common_sql;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import org.springframework.stereotype.Component;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

@Component
public class PresentationDocumentsTestSql {

    /** テストデータINSERT用SQL */
    public static final Operation PRESENTATION_DOCUMENTS_INSERT = insertInto("presentation_documents")
            .columns("presentation_document_id", "document_path", "presentation_id")
            .values(1, "/powerpro/src/main/resources/templates/presentation/sample.pptx", 1)
            .values(2, "/powerpro/src/main/resources/templates/presentation/sample.pptx", 2)
            .values(3, "/powerpro/src/main/resources/templates/presentation/sample.pptx", 3)
            .build();

    /** テストデータDELETE用SQL */
    public static final Operation PRESENTATION_DOCUMENTS_DELETE = deleteAllFrom("presentation_documents");

    /** 主キー採番リセット用SQL */
    public static final Operation PRESENTATION_DOCUMENTS_AUTO_INCREMENT = Operations
            .sql("ALTER TABLE presentation_documents auto_increment = 1;");
}
