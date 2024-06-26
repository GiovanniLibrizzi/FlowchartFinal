package Database;
import org.jooq.JSON;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.Name;
import org.jooq.impl.TableImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.Record;
import java.time.OffsetDateTime;

public class FlowchartProblems {
    private static final Name FLOWCHART_PROBLEMS = DSL.name("FlowchartProblems");

    public static final TableImpl<Record> FLOWCHART_PROBLEMS_TABLE = new TableImpl<>(FLOWCHART_PROBLEMS) {
        public final org.jooq.TableField<Record, Long> ID = createField("id", SQLDataType.BIGINT);
        public final org.jooq.TableField<Record, OffsetDateTime> CREATED_AT =
                createField("created_at", SQLDataType.TIMESTAMPWITHTIMEZONE);
        public final TableField<Record, String> PROBLEM = createField("problem", SQLDataType.VARCHAR);
        public final TableField<Record, String> ANSWER = createField("answer", SQLDataType.VARCHAR);
        public final TableField<Record, String> HINT = createField("hint", SQLDataType.VARCHAR);
        public final TableField<Record, String> CODE = createField("code", SQLDataType.VARCHAR);
    };

    private FlowchartProblems() {
        // Private constructor to prevent instantiation
    }
}
