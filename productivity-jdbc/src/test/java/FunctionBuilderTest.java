import com.seriousplay.productitity.jdbc.SqlOperator;
import com.seriousplay.productitity.jdbc.query.functions.SqlFunctionBulider;
import org.junit.Test;

import static com.seriousplay.productitity.jdbc.query.functions.SqlFunctionBulider.start;
import static com.seriousplay.productitity.jdbc.query.functions.mysql.MysqlFuntions.ControlFlowFunction.IF;
import static com.seriousplay.productitity.jdbc.query.functions.mysql.MysqlFuntions.ControlFlowFunction.IFNULL;

public class FunctionBuilderTest {
    @Test
    public void testCreateSimpleFunction() {
        SqlFunctionBulider bulider = start(IFNULL)
                .func(start(IF).column("adb", "t4").operator(SqlOperator.EQ).value("1").comma()
                        .value("1").comma()
                        .value("null"))
                .comma()
                .value(5667);
        System.out.println(bulider.toCriterion());
    }
}
