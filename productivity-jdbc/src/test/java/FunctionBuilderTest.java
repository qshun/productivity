import com.seriousplay.productitity.jdbc.query.CriterionBuilder;
import org.junit.Test;

import java.util.Arrays;

import static com.seriousplay.productitity.jdbc.query.functions.mysql.MysqlFuntions.ControlFlowFunction.IF;
import static com.seriousplay.productitity.jdbc.query.functions.mysql.MysqlFuntions.ControlFlowFunction.IFNULL;

public class FunctionBuilderTest {
    @Test
    public void testCreateSimpleFunction() {
        CriterionBuilder bulider = new CriterionBuilder()
                .func(IFNULL, f -> f.func(IF,
                        f1 -> f1.column("t.a").in(Arrays.asList(566,678)),
                        f2 -> f2.value(1),
                        f3 -> f3.value(null)),
                        f4 -> f4.value("11"));

        System.out.println(bulider.build());
    }
}
