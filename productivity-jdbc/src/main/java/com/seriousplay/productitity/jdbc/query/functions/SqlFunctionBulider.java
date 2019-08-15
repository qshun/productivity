package com.seriousplay.productitity.jdbc.query.functions;

import com.seriousplay.productitity.jdbc.query.AbstractSqlBuilder;
import com.seriousplay.productitity.jdbc.query.Criterion;

/**
 *
 */
public class SqlFunctionBulider extends AbstractSqlBuilder<SqlFunctionBulider> {
    int functionCount;

    public SqlFunctionBulider() {
        super();
        functionCount = 0;
    }

    public static SqlFunctionBulider start(SqlFunction function) {
        return new SqlFunctionBulider()
                .func(function);
    }

    public SqlFunctionBulider comma() {
        return operator(",");
    }

    SqlFunctionBulider end() {
        sql.append(")");
        return getSelf();
    }

    /**
     * @param
     * @return
     */
    public SqlFunctionBulider func(SqlFunctionBulider sqlFunctionBuilder) {
        if (functionCount++ > 0) {
            this.comma();
        }
        this.sql.append(sqlFunctionBuilder.end().getSql());
        this.parameters.addAll(sqlFunctionBuilder.parameters);
        return getSelf();
    }

    @Override
    public Criterion toCriterion() {
        this.end();
        return super.toCriterion();
    }

    @Override
    public SqlFunctionBulider getSelf() {
        return this;
    }
}
